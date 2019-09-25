package backend;

import model.CustomerUser;
import model.SupplierUser;
import model.enums.Category;
import model.enums.OfficeDays;
import model.enums.OfficeHours;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/*
1. Compra:

    a. Selecciona un servicio, y selecciona un menuid
    b. Compra
    c. Se genera una purchase, y se guarda dentro de la lista de purchases del cliente
    d. Se descuenta dinero de la cuenta del cliente
    e. Se acredita dinero en la cuenta del supplier
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerPurchaseTest {

    @Test
    public void PurchasingMenuFromServiceShouldCreatePurchase() {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com","1161635613","Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com","1111111111","Yrigoyen 313");

        supplier.addService("Burguer King", "Test", "Rivadavia 101", "Las mejores hamburguesas, lejos!","burguerking@gmail.com", "011 51515151",
                new ArrayList<OfficeDays>(Arrays.asList(OfficeDays.values())),
                new ArrayList<OfficeHours>(Arrays.asList(OfficeHours.values())),
                10);
        supplier.addMenu(1, "Whopper", "Hamburguesa de la ostia", Category.Hamburguesa, 10, LocalDate.now(), LocalDate.now(), OfficeHours.Afternoon, 15, 50, 1, 50, 100);

        // Act
        customer.purchase("Burguer King", 1);

        // Assert
        // Genera purchase dentro de la lista de customer
        // Descuenta dinero de la cuenta de customer
        // Acredita dinero en cuenta de supplier


    }
}
