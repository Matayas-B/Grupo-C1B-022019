package backend;

import model.Category;
import model.Menu;
import org.assertj.core.api.LocalDateAssert;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.util.locale.LocaleObjectCache;


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

    private Menu menu = new Menu( "carne suave", "carne picada, cebolla, huevo,",
            20, Category.Empanadas, validate, validateEnd, "lunes a viernes de 9 a 18",
            "20 minutos", "20 minutos", 30, 15, 30, 30, 30, 30, 30

    );

    @Test
    public void menuName() {
        assertEquals("carne suave", menu.getName());
    }

    @Test
     public void menuCategory(){
        assertEquals(Category.Empanadas,menu.getCategory());
    }

}
