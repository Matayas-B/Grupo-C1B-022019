package backend.service;

import backend.model.CustomerUser;
import backend.repository.ICustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private ICustomerRepository customerRepository;

    private CustomerUser customer;

    @Test
    public void createCustomerShouldSaveCustomerToTheDB() throws Exception {
        // Arrange
        customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        CustomerUser customerToSave = new CustomerUser("Matayas", "Beca", "hbeca@gmail.com", "1161635613", "Canale 3134");
        Mockito.when(customerRepository.save(any(CustomerUser.class))).thenReturn(customerToSave);
        Mockito.when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

        // Act
        CustomerUser newCustomer = customerService.createCustomer(customerToSave);

        // Assert
        assertEquals(newCustomer.getName(), "Matayas");
    }
}
