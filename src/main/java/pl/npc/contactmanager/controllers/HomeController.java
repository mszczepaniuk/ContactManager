package pl.npc.contactmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;
import pl.npc.contactmanager.interfaces.services.IContactService;
import pl.npc.contactmanager.interfaces.services.ITokenService;
import pl.npc.contactmanager.interfaces.services.IUserService;


@Controller
public class HomeController {

    private final IContactService contactService;
    private final ITokenService tokenService;
    private final IUserService userService;

    @Autowired
    public HomeController(IContactService contactService,
                          ITokenService tokenService,
                          IUserService userService) {

        this.contactService = contactService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    // Nazwa ciasteczka musi być stała, nie wiem jak wstrzyknąć z application.properties.
    @GetMapping("/")
    public String index(@CookieValue(name = "contactmanager_authentication", required = false) String authToken,
                        Integer page,
                        Model model) {
        // Walidacja numeru strony.
        if (page == null || page < 1) {
            page = 1;
        }
        long test = contactService.getAmountOfPages();
        if (page > contactService.getAmountOfPages()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Tworzenie modelu.
        model.addAttribute("basicContacts", contactService.getPageOfBasicsContacts(page));
        model.addAttribute("isUserLoggedIn", isUserLoggedIn(authToken));
        model.addAttribute("isThereAPreviousPage", isThereAPreviousPage(page));
        model.addAttribute("isThereANextPage", isThereANextPage(page));
        model.addAttribute("page", page);
        return "index";
    }

    private boolean isUserLoggedIn(String authToken) {
        if (authToken == null || !tokenService.isTokenValid(authToken)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isThereAPreviousPage(int page) {
        if (page > 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isThereANextPage(int page) {
        if (page == contactService.getAmountOfPages()) {
            return false;
        } else {
            return true;
        }
    }
}
