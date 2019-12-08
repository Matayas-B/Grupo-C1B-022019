package backend.controller;

import backend.controller.requests.ServiceRequest;
import backend.model.HistoricalPurchases;
import backend.model.Service;
import backend.model.SupplierUser;
import backend.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('SUPPLIER')")
public class SupplierController {

    @Autowired
    private SupplierService supplierService = new SupplierService();

    @RequestMapping(value = "/supplier", method = RequestMethod.GET)
    public Iterable<SupplierUser> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @RequestMapping(value = "/supplier/extractMoney", method = RequestMethod.GET)
    public int extractMoney(long supplierId, int money) {
        return supplierService.extractMoney(supplierId, money);
    }

    @RequestMapping(value = "/supplier/addService", method = RequestMethod.POST)
    public void addService(@Valid @RequestBody ServiceRequest serviceRequest) throws Exception {
        supplierService.addService(serviceRequest);
    }

    @RequestMapping(value = "/supplier/updateService", method = RequestMethod.POST)
    public void updateService(@Valid @RequestBody ServiceRequest serviceRequest) {
        supplierService.updateService(serviceRequest);
    }

    @RequestMapping(value = "/supplier/getSupplierService", method = RequestMethod.GET)
    public Service getSupplierService(long supplierId) {
        return supplierService.getSupplierService(supplierId);
    }

    @RequestMapping(value = "/supplier/deleteService", method = RequestMethod.GET)
    public void deleteService(long supplierId) {
        supplierService.deleteService(supplierId);
    }

    @GetMapping(value = "/supplier/purchase")
    public Iterable<HistoricalPurchases> getSupplierPurchases(long supplierId) {
        return supplierService.getSupplierPurchases(supplierId);
    }

    @GetMapping(value = "supplier/startDelivery")
    public void startPurchaseDelivery(long purchaseId) {
        supplierService.startPurchaseDelivery(purchaseId);
    }

    @GetMapping(value = "supplier/finishDelivery")
    public void finishPurchaseDelivery(long purchaseId) {
        supplierService.finishPurchaseDelivery(purchaseId);
    }

    @RequestMapping(value = "/supplier/getById", method = RequestMethod.GET)
    public SupplierUser getSupplierById(long supplierId) {
        return supplierService.getSupplierById(supplierId);
    }
}
