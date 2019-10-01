package backend.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertTrue;

@SpringBootTest
public class MoneyAccountTest {

    private MoneyAccount account;

    @Before
    public void setUp() {
        account = new MoneyAccount();
    }

    @Test
    public void addMoneyTest() {
        account.depositMoney(50);
        assertTrue(account.haveEnoughFunds(50));
    }

    @Test(expected = Exception.class)
    public void extractMoreMoneyThanFundsShouldThrowException() throws Exception {
        account.extractMoney(50);
    }

    @Test
    public void extractMoneyTest() throws Exception {
        account.depositMoney(50);
        account.extractMoney(30);
        assertTrue(account.haveEnoughFunds(20));
    }
}
