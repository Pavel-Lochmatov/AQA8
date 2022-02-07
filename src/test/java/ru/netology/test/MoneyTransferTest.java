package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MoneyTransferTest {

    @Test
    void shouldTransferLessMoneyThanCurrentBalance() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val smsCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(smsCode);
        val cardInfoFirst = DataHelper.getFirstCard();
        val currentSumCard1 = new DashboardPage().getCardBalance(cardInfoFirst);
        val cardInfoSecond = DataHelper.getSecondCard();
        val currentSumCard2 = new DashboardPage().getCardBalance(cardInfoSecond);
        int refillSum = 200;
        val cardPage = dashboardPage.refillCard1();
        cardPage.transfer(cardInfoSecond, refillSum);
        int endSumCard1 = dashboardPage.getCardBalance(cardInfoFirst);
        int endSumCard2 = dashboardPage.getCardBalance(cardInfoSecond);
        assertEquals(currentSumCard1 + refillSum, endSumCard1);
        assertEquals(currentSumCard2 - refillSum, endSumCard2);
    }

    @Test
    void shouldTransferMoreMoneyThanCurrentBalance() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val smsCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(smsCode);
        val cardInfoFirst = DataHelper.getFirstCard();
        val currentSumCard1 = new DashboardPage().getCardBalance(cardInfoFirst);
        val cardInfoSecond = DataHelper.getSecondCard();
        val currentSumCard2 = new DashboardPage().getCardBalance(cardInfoSecond);
        int refillSum = 10100;
        val cardPage = dashboardPage.refillCard1();
        cardPage.transfer(cardInfoSecond, refillSum);
        int endSumCard1 = dashboardPage.getCardBalance(cardInfoFirst);
        int endSumCard2 = dashboardPage.getCardBalance(cardInfoSecond);
        assertEquals(currentSumCard1 + refillSum, endSumCard1);
        assertEquals(currentSumCard2 - refillSum, endSumCard2);
    }
}

