package backend.controller;

import backend.model.Service;
import backend.repository.IServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    @Autowired
    IServiceRepository serviceRepository;

    @GetMapping(value = "/service")
    public Iterable<Service> getAllServices() {
        return serviceRepository.findAll();
    }

}
