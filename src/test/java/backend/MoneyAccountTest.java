package backend;


import exception.CurrencyMenuException;
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


        account.addMoney( 50);
        assertEquals(50, account.getAccount());
    }

    @Test (expected = CurrencyMenuException.class)
    public void moneyExtractionException(){

        account.extract(50);
    }

    @Test
    public void moneyExtraction(){

        account.addMoney(50);
        account.extract(30);
        assertEquals(20, account.getAccount());
    }
}
