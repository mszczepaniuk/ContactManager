package pl.npc.contactmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.npc.contactmanager.entities.User;
import pl.npc.contactmanager.interfaces.services.ITokenService;
import pl.npc.contactmanager.interfaces.services.IUserService;

@Controller
public class HomeController {

    @Autowired
    public HomeController() {

    }

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
