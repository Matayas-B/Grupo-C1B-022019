package backend;

import model.enums.Category;
import model.Menu;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;



/*
1. Customer hace una compra<br/>
2. Customer deposita credito a su cuenta<br/>
3. Supplier extrae dinero de su cuenta<br/>
4. Customer busca y filtra resultados<br/>
5. Sistema corre job para confirmar pedidos, y actualiza el saldo de clientes<br/>
6. Customer puntua servicio
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuTest {

    private LocalDate validate = LocalDate.now();
    private LocalDate validateEnd = LocalDate.now().plusDays(2);




}
