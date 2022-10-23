package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

public class MoneyTransferTest {

  @Test
  void shouldTransferFromFirstToSecond() {
    open("http://localhost:9999");
    var loginPage = new LoginPageV1();

    var authInfo = getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCode();
    var dashboardPage = verificationPage.validVerify(verificationCode);

    var firstCardInfo = getFirstCardInfo();
    var secondCardInfo = getSecondCardInfo();
    var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
    var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
    var amount = generateValidAmount(firstCardBalance);
    var expectedBalanceFirstCard = firstCardBalance - amount;
    var expectedBalanceSecondCard = secondCardBalance + amount;
    var transferPage  = dashboardPage.selectCardToTransfer(secondCardInfo);

    dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
    var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
    var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
    assertEquals(expectedBalanceFirstCard,actualBalanceFirstCard);
    assertEquals(expectedBalanceSecondCard,actualBalanceSecondCard);
  }

  @Test
  void shouldGetErrorMessageIfAmountMoreBalance() {
    open("http://localhost:9999");
    var loginPage = new LoginPageV1();

    var authInfo = getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCode();
    var dashboardPage = verificationPage.validVerify(verificationCode);

    var firstCardInfo = getFirstCardInfo();
    var secondCardInfo = getSecondCardInfo();
    var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
    var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
    var amount = generateInvalidAmount(secondCardBalance);
    var transferPage  = dashboardPage.selectCardToTransfer(secondCardInfo);

    TransferPage.makeTransfer(String.valueOf(amount), secondCardInfo);
    transferPage.findErrorMessage("Выполнена попытка перевода суммы, превышающий баланс счета!");
    var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
    var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
    assertEquals(firstCardBalance,actualBalanceFirstCard);
    assertEquals(secondCardBalance,actualBalanceSecondCard);
  }

}


