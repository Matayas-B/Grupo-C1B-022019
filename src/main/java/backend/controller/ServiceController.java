package backend.controller;

import backend.controller.requests.MenuRequest;
import backend.model.Menu;
import backend.model.Service;
import backend.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    ServiceService serviceService = new ServiceService();

    @GetMapping
    public Iterable<Service> getAllValidServices() {
        return serviceService.getAllValidServices();
    }

    @GetMapping(value = "/getMenus")
    public Iterable<Menu> getAllValidMenusForService(long serviceId) {
        return serviceService.getAllValidMenusForService(serviceId);
    }

    @GetMapping(value = "/getMenu")
    public Menu getMenuById(int menuId) {
        return serviceService.getMenuById(menuId);
    }

    @PostMapping(value = "/addMenu")
    @PreAuthorize("hasRole('SUPPLIER')")
    public void addMenuToService(@Valid @RequestBody MenuRequest menuRequest) {
        serviceService.addMenuToService(menuRequest);
    }

    @PostMapping(value = "/updateMenu")
    @PreAuthorize("hasRole('SUPPLIER')")
    public void updateMenuFromService(@Valid @RequestBody MenuRequest menuRequest) {
        serviceService.updateMenuFromService(menuRequest);
    }

    @GetMapping(value = "/deleteMenu")
    @PreAuthorize("hasRole('SUPPLIER')")
    public void deleteMenuFromService(long serviceId, long menuId) {
        serviceService.deleteMenuFromService(serviceId, menuId);
    }
}
