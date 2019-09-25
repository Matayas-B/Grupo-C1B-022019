package backend;

import model.MoneyAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoneyAccountTest {

    private MoneyAccount account = new MoneyAccount();

    @Test
    public void addMoneyTest() {
        account.depositMoney(50);
        assertTrue(account.haveEnoughFunds(50));
    }

    @Test (expected = Exception.class)
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
