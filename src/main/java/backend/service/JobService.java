package backend.service;

import backend.model.Purchase;
import backend.repository.IPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private IPurchaseRepository purchaseRepository;
    @Autowired
    private CommunicationService communicationService;

    public void sendCustomersPurchasesEmail() {
        communicationService.sendSimpleEmail("hernan.beca@gmail.com", "This is an automated job from Heroku, ZOQUETE", "Nada, el subject itself. Te quiero.");
        communicationService.sendSimpleEmail("facundovigo@gmail.com", "This is an automated job from Heroku, ZOQUETE", "Nada, el subject itself. Te quiero.");
    }

    public void sendSuppliersSalesEmail() {
        // TODO: need to complete this
    }

    /* Private Methods */

    private List<Purchase> getCustomerPurchases() {
        // TODO: need to complete this
        return null;
    }

    private List<Purchase> getSupplierPurchases() {
        // TODO: need to complete this
        return null;
    }
}
