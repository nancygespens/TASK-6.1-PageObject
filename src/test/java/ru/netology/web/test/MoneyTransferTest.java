package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

  @BeforeEach
  void SetUp() {
    var authInfo = DataHelper.getAuthInfo();
    open("http://localhost:9999/", LoginPageV1.class)
            .validLogin(authInfo)
            .validVerify(DataHelper.getVerificationCodeFor(authInfo));
  }
  @Test
  void checkBalanceTransfer() {
    DashboardPage dashboardPage = new DashboardPage();
    var balanceCard1Before = dashboardPage.getCardBalance(DataHelper.getIdCard1());
    var balanceCard2Before = dashboardPage.getCardBalance(DataHelper.getIdCard2());
    var transferSum = 5_000;
    dashboardPage.transferFromCard2ToCard1((transferSum));
    var balanceCard1After = dashboardPage.getCardBalance(DataHelper.getIdCard1());
    var balanceCard2After = dashboardPage.getCardBalance(DataHelper.getIdCard2());
    assert balanceCard1After == (balanceCard1Before + transferSum);
    assert balanceCard2After == (balanceCard2Before - transferSum);
  }

  @Test
  void transferNotHighThanBalance() {
    DashboardPage dashboardPage = new DashboardPage();
    var balanceCard1Before = dashboardPage.getCardBalance(DataHelper.getIdCard1());
    dashboardPage.transferFromCard1ToCard2((balanceCard1Before * 2));
    var balanceCard1After = dashboardPage.getCardBalance(DataHelper.getIdCard1());
    assert balanceCard1After >= 0;
  }

  @Test
  void balanceNotNegative() {
    DashboardPage dashboardPage = new DashboardPage();
    var balanceCard2Before = dashboardPage.getCardBalance(DataHelper.getIdCard2());
    dashboardPage.transferFromCard2ToCard1((balanceCard2Before * 2));
    var balanceCard2After = dashboardPage.getCardBalance(DataHelper.getIdCard2());
    assert balanceCard2After >= 0;
  }

}
