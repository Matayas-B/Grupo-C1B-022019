package backend.controller;

import backend.controller.requests.NewServiceRequest;
import backend.model.Service;
import backend.model.SupplierUser;
import backend.model.exception.InsufficientFundsException;
import backend.model.exception.ServiceNotFoundException;
import backend.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SupplierController {

    @Autowired
    private SupplierService supplierService = new SupplierService();

    @RequestMapping(value = "/supplier", method = RequestMethod.POST)
    public SupplierUser createSupplier(@RequestBody SupplierUser supplier) {
        try {
            return supplierService.createSupplier(supplier);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An user with that email alreay exists", ex);
        }
    }

    @RequestMapping(value = "/supplier", method = RequestMethod.GET)
    public Iterable<SupplierUser> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @RequestMapping(value = "/supplier", method = RequestMethod.DELETE)
    public void deleteSupplier(long id) {
        try {
            supplierService.deleteSupplier(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User does not exist", ex);
        }
    }

    @RequestMapping(value = "/supplier/extractMoney", method = RequestMethod.GET)
    public int extractMoney(long supplierId, int money) {
        try {
            return supplierService.extractMoney(supplierId, money);
        } catch (InsufficientFundsException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User does not exist", ex);
        }
    }

    @RequestMapping(value = "/supplier/addService", method = RequestMethod.POST)
    public void addService(@RequestBody NewServiceRequest newServiceRequest) {
        try {
            supplierService.addService(newServiceRequest);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service already exists", ex);
        }
    }

    @RequestMapping(value = "/supplier/getSupplierService", method = RequestMethod.GET)
    public Service getSupplierService(long supplierId) {
        try {
            return supplierService.getSupplierService(supplierId);
        } catch (ServiceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User does not exist", ex);
        }
    }

    @RequestMapping(value = "/supplier/deleteService", method = RequestMethod.GET)
    public void deleteService(long supplierId) {
        try {
            supplierService.deleteService(supplierId);
        } catch (ServiceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User does not exist", ex);
        }
    }
}
