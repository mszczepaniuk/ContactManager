package pl.npc.contactmanager.controllers.api;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.npc.contactmanager.dto.bindingmodels.LoginUserBindingModel;
import pl.npc.contactmanager.dto.bindingmodels.RegisterUserBindingModel;
import pl.npc.contactmanager.dto.viewmodels.AuthenticateViewModel;
import pl.npc.contactmanager.entities.User;
import pl.npc.contactmanager.interfaces.services.IPasswordService;
import pl.npc.contactmanager.interfaces.services.ITokenService;
import pl.npc.contactmanager.interfaces.services.IUserService;
import pl.npc.contactmanager.util.apierror.ApiErrorUtil;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private final IUserService userService;
    private final IPasswordService passwordService;
    private final ITokenService tokenService;

    @Autowired
    public UserController(IUserService userService,
                          IPasswordService passwordService,
                          ITokenService tokenService) {
        this.userService = userService;
        this.passwordService = passwordService;
        this.tokenService = tokenService;
    }

    @PostMapping("/api/v1/user/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserBindingModel bindingModel) {
        List<String> errorMessages = new ArrayList<>();

        // Walidacja.
        boolean success = true;
        if (!passwordService.isPasswordValid(bindingModel.getPassword())) {
            errorMessages.add("Hasło powinno mieć conajmniej 6 znaków i 1 cyfrę.");
            success = false;
        }
        if (!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
            errorMessages.add("Hasła do siebie nie pasują.");
            success = false;
        }
        if (userService.isUsernameTaken(bindingModel.getUsername())) {
            errorMessages.add("Nazwa użytkownika jest zajęta.");
            success = false;
        }

        if (success) {
            User user = userService.register(bindingModel.getUsername(), bindingModel.getConfirmPassword());
            String token = tokenService.getAccessToken(user);

            AuthenticateViewModel vm = new AuthenticateViewModel();
            vm.setAccessToken(token);
            vm.setUserId(user.getId());
            return ResponseEntity.ok(new Gson().toJson(vm));
        } else {
            String jsonString = new ApiErrorUtil().errorMessageListToJson(errorMessages);
            return ResponseEntity.badRequest().body(jsonString);
        }
    }

    @PostMapping("/api/v1/user/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginUserBindingModel bindingModel) {
        List<String> errorMessages = new ArrayList<>();

        // Walidacja.
        if (!userService.isUsernameTaken(bindingModel.getUsername())
                || !userService.isPasswordCorrect(bindingModel.getUsername(), bindingModel.getPassword())) {
            errorMessages.add("Błędna nazwa lub hasło.");
            String jsonString = new ApiErrorUtil().errorMessageListToJson(errorMessages);
            return ResponseEntity.badRequest().body(jsonString);
        }

        User user = userService.getByUsername(bindingModel.getUsername());
        String token = tokenService.getAccessToken(user);

        AuthenticateViewModel vm = new AuthenticateViewModel();
        vm.setAccessToken(token);
        vm.setUserId(user.getId());
        return ResponseEntity.ok(new Gson().toJson(vm));
    }
}
