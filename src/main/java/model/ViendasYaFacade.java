package model;

import java.util.ArrayList;
import java.util.List;

public class ViendasYaFacade {

    public List<SupplierUser> suppliers = new ArrayList<SupplierUser>();
    public List<CustomerUser> customers = new ArrayList<CustomerUser>();

    public List<Service> services = new ArrayList<Service>();

    public void purchase(String serviceName, int menuId) throws Exception {
        Service currentService = services.stream().findFirst(s => currentService.getServiceName().equals(serviceName));
        for (Service service: services ) {
            if (service.getServiceName() == serviceName)
                currentService = service;
        }

        if (currentService == null)
            throw new Exception("Service does not longer exists.");
    }
}