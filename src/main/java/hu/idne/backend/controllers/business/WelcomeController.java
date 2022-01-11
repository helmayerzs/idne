package hu.idne.backend.controllers.business;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping(path = "/")
    public String index() {
        return "Welcome to Idne resource server!";
    }
}
