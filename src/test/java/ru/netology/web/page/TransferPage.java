package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private static SelenideElement transferButton = $( "[data-test-id='action-transfer']");
    private static SelenideElement amountInput = $( "[data-test-id='amount'] input");
    private static SelenideElement fromInput = $( "[data-test-id='from'] input");
    private SelenideElement transferHead = $(byText("Пополнение карты"));
    private SelenideElement errorMessage = $( "[data-test-id='error-message']");

    public TransferPage() {
        transferHead.shouldBe(visible);
    }


    public DashboardPage makeValidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }

    public static void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }

    public void findErrorMessage(String expectedTest) {
        errorMessage.shouldHave(exactText(expectedTest), Duration.ofSeconds(15)).shouldBe(visible);
    }

}