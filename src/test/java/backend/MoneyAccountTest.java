package backend;

import model.MoneyAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoneyAccountTest {

    private MoneyAccount account = new MoneyAccount();

    @Test
    public void addmoneyTest() {
        account.depositMoney(50);
        assertEquals(50, account.getFunds());
    }

    @Test (expected = Exception.class)
    public void moneyExtractionException() throws Exception {
        account.extractMoney(50);
    }

    @Test
    public void moneyExtraction() throws Exception {
        account.depositMoney(50);
        account.extractMoney(30);
        assertEquals(20, account.getFunds());
    }
}
