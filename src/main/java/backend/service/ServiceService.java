package backend.service;

import backend.controller.requests.NewMenuRequest;
import backend.model.Menu;
import backend.model.Service;
import backend.model.exception.ServiceNotFoundException;
import backend.repository.IServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    IServiceRepository serviceRepository;

    public Iterable<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Iterable<Menu> getAllMenusForService(long serviceId) {
        backend.model.Service service = serviceRepository.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        return service.getMenus();
    }

    public void addMenuToService(NewMenuRequest newMenuRequest) {
        backend.model.Service service = serviceRepository.findById(newMenuRequest.getServiceId()).orElseThrow(ServiceNotFoundException::new);
        service.addMenu(newMenuRequest.getName(), newMenuRequest.getDescription(), newMenuRequest.getCategory(), newMenuRequest.getDeliveryFee(), newMenuRequest.getStartDate(), newMenuRequest.getEndDate(), newMenuRequest.getDeliveryHours(), newMenuRequest.getAverageDeliveryMinutes(), newMenuRequest.getPrice(), newMenuRequest.getMinQuantity(), newMenuRequest.getMinQuantityPrice(), newMenuRequest.getMaxDailySales());
        serviceRepository.save(service);
    }

    public void deleteMenuFromService(long serviceId, long menuId) {
        backend.model.Service service = serviceRepository.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        service.deleteMenu(menuId);
        serviceRepository.save(service);
    }
}
