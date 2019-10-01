package backend.controller;

import backend.model.CustomerUser;
import backend.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    private ICustomerRepository ICustomerRepository;

    @RequestMapping("/")
    public String index() {
        return "<h1>Testing initial setup for Spring application.</h1>";
    }

    @RequestMapping (value = "/persona", method= RequestMethod.POST)
    public CustomerUser persistirPersona(@RequestBody CustomerUser persona){
        return ICustomerRepository.save(persona);
    }

    @RequestMapping(value="/persona",method = RequestMethod.GET)
    public Iterable<CustomerUser> buscarTodasLasPersonas(){
        return ICustomerRepository.findAll();
    }

    @RequestMapping(value="/persona",method = RequestMethod.DELETE)
    public void borrarPersona(long id){
        ICustomerRepository.deleteById(id);
    }


}
