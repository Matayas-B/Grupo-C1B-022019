package ar.edu.unq.desapp.grupoC1B.desappgroupC1Bbackend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
	
	@RequestMapping("/")
    public String index() {
        return "<h1>Testing initial setup for Spring application.</h1>";
    }
}
