package backend.service;

import backend.controller.requests.NewMenuRequest;
import backend.model.Menu;
import backend.model.Service;
import backend.model.exception.InvalidServiceException;
import backend.model.exception.ServiceNotFoundException;
import backend.repository.IServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    IServiceRepository serviceRepository;

    public Iterable<Service> getAllValidServices() {
        return StreamSupport.stream(serviceRepository.findAll().spliterator(), false).filter(Service::isValidService).collect(Collectors.toList());
    }

    public Iterable<Menu> getAllValidMenusForService(long serviceId) {
        backend.model.Service service = serviceRepository.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        return service.getValidMenus();
    }

    public void addMenuToService(NewMenuRequest newMenuRequest) {
        backend.model.Service service = serviceRepository.findById(newMenuRequest.getServiceId()).orElseThrow(ServiceNotFoundException::new);
        if (!service.isValidService())
            throw new InvalidServiceException();

        service.addMenu(newMenuRequest.getName(), newMenuRequest.getDescription(), newMenuRequest.getCategory(), newMenuRequest.getDeliveryFee(), newMenuRequest.getStartDate(), newMenuRequest.getEndDate(), newMenuRequest.getDeliveryHours(), newMenuRequest.getAverageDeliveryMinutes(), newMenuRequest.getPrice(), newMenuRequest.getMinQuantity(), newMenuRequest.getMinQuantityPrice(), newMenuRequest.getMaxDailySales());
        serviceRepository.save(service);
    }

    public void deleteMenuFromService(long serviceId, long menuId) {
        backend.model.Service service = serviceRepository.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        service.deleteMenu(menuId);
        serviceRepository.save(service);
    }
}
