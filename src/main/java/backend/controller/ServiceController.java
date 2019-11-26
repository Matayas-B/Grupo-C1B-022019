package backend.controller;

import backend.controller.requests.MenuRequest;
import backend.model.Menu;
import backend.model.Service;
import backend.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ServiceController {

    @Autowired
    ServiceService serviceService = new ServiceService();

    @GetMapping(value = "/service")
    public Iterable<Service> getAllValidServices() {
        return serviceService.getAllValidServices();
    }

    @GetMapping(value = "/service/getMenus")
    public Iterable<Menu> getAllValidMenusForService(long serviceId) {
        return serviceService.getAllValidMenusForService(serviceId);
    }

    @GetMapping(value = "/service/getMenu")
    public Menu getMenuById(int menuId) {
        return serviceService.getMenuById(menuId);
    }

    @PostMapping(value = "/service/addMenu")
    public void addMenuToService(@Valid @RequestBody MenuRequest menuRequest) {
        serviceService.addMenuToService(menuRequest);
    }

    @PostMapping(value = "/service/updateMenu")
    public void updateMenuFromService(@Valid @RequestBody MenuRequest menuRequest) {
        serviceService.updateMenuFromService(menuRequest);
    }

    @GetMapping(value = "/service/deleteMenu")
    public void deleteMenuFromService(long serviceId, long menuId) {
        serviceService.deleteMenuFromService(serviceId, menuId);
    }
}
