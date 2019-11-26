package backend.service;

import backend.controller.requests.MenuRequest;
import backend.model.Menu;
import backend.model.Service;
import backend.model.ViendasYaFacade;
import backend.model.exception.InvalidServiceException;
import backend.model.exception.MenuNotFoundException;
import backend.model.exception.ServiceNotFoundException;
import backend.repository.IMenuRepository;
import backend.repository.IServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    IServiceRepository serviceRepository;
    @Autowired
    IMenuRepository menuRepository;

    private ViendasYaFacade viendasYaFacade = new ViendasYaFacade();

    public Iterable<Service> getAllValidServices() {
        return StreamSupport.stream(serviceRepository.findAll().spliterator(), false).filter(Service::isValidService).collect(Collectors.toList());
    }

    public Iterable<Menu> getAllValidMenusForService(long serviceId) {
        backend.model.Service service = serviceRepository.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        return service.getValidMenus();
    }

    public Menu getMenuById(int menuId) {
        return menuRepository.findById(menuId).orElseThrow(MenuNotFoundException::new);
    }

    public void addMenuToService(MenuRequest menuRequest) {
        backend.model.Service service = serviceRepository.findById(menuRequest.getServiceId()).orElseThrow(ServiceNotFoundException::new);
        if (!service.isValidService())
            throw new InvalidServiceException();

        viendasYaFacade.addMenuToService(service, menuRequest.getName(), menuRequest.getDescription(), menuRequest.getCategory(), menuRequest.getDeliveryFee(), menuRequest.getStartDate(), menuRequest.getEndDate(), menuRequest.getDeliveryHours(), menuRequest.getAverageDeliveryMinutes(), menuRequest.getPrice(), menuRequest.getMinQuantity(), menuRequest.getMinQuantityPrice(), menuRequest.getMaxDailySales());
        serviceRepository.save(service);
    }

    public void updateMenuFromService(MenuRequest menuRequest) {
        backend.model.Service service = serviceRepository.findById(menuRequest.getServiceId()).orElseThrow(ServiceNotFoundException::new);
        if (!service.isValidService())
            throw new InvalidServiceException();
        if (!service.existsMenuById(menuRequest.getMenuId()))
            throw new MenuNotFoundException();

        ModelMapper mapper = new ModelMapper();
        Menu menuToUpdate = mapper.map(menuRequest, Menu.class);
        menuRepository.save(menuToUpdate);
    }

    public void deleteMenuFromService(long serviceId, long menuId) {
        backend.model.Service service = serviceRepository.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
        if (!service.isValidService())
            throw new InvalidServiceException();

        service.deleteMenu(menuId);
        menuRepository.deleteById((int)menuId);
        serviceRepository.save(service);
    }
}
