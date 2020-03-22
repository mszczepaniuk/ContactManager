package pl.npc.contactmanager.controllers.api;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.npc.contactmanager.dto.bindingmodels.ContactBindingModel;
import pl.npc.contactmanager.dto.viewmodels.BusinessSubcategoriesViewModel;
import pl.npc.contactmanager.dto.viewmodels.ContactViewModel;
import pl.npc.contactmanager.entities.Contact;
import pl.npc.contactmanager.interfaces.services.IContactService;
import pl.npc.contactmanager.interfaces.services.ITokenService;
import pl.npc.contactmanager.util.apierror.ApiErrorUtil;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ContactController {

    private final IContactService contactService;
    private final ITokenService tokenService;

    @Autowired
    public ContactController(IContactService contactService,
                             ITokenService tokenService) {
        this.contactService = contactService;
        this.tokenService = tokenService;
    }

    @GetMapping("/api/v1/contact/business-subcategories")
    public ResponseEntity<String> GetBusinessSubcategories(){
        BusinessSubcategoriesViewModel vm = contactService.getBusinessSubcategories();
        return ResponseEntity.ok(new Gson().toJson(vm));
    }

    @GetMapping("/api/v1/contact/{id}")
    public ResponseEntity<String> GetContact(@PathVariable(name = "id") long id,
                                             @RequestHeader(name="Authorization") String authHeader){
        // Autoryzacja.
        if (!isAuthorizationHeaderInProperFormat(authHeader)){
            String error = new ApiErrorUtil().errorMessageListToJson(new ArrayList<String>(List.of("Brak tokena lub zly format nagłówka.")));
            return ResponseEntity.badRequest().body(error);
        }
        String token = getTokenFromHeader(authHeader);
        if (!tokenService.isTokenValid(token)){
            String error = new ApiErrorUtil().errorMessageListToJson(new ArrayList<String>(List.of("Niepoprawny token.")));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        // Walidacja.
        if (!contactService.doesContactExist(id)){
            String errorJson = new ApiErrorUtil().errorMessageListToJson(new ArrayList<>(List.of("Nie istnieje kontakt o podanym id.")));
            return ResponseEntity.badRequest().body(errorJson);
        }
        ContactViewModel contact = contactService.getFullContactById(id);
        return ResponseEntity.ok(new Gson().toJson(contact));
    }

    @PostMapping("/api/v1/contact")
    public ResponseEntity<String> AddContact(@RequestBody ContactBindingModel bindingModel,
                                             @RequestHeader(name="Authorization") String authHeader){
        // Autoryzacja.
        if (!isAuthorizationHeaderInProperFormat(authHeader)){
            String error = new ApiErrorUtil().errorMessageListToJson(new ArrayList<String>(List.of("Brak tokena lub zly format nagłówka.")));
            return ResponseEntity.badRequest().body(error);
        }
        String token = getTokenFromHeader(authHeader);
        if (!tokenService.isTokenValid(token)){
            String error = new ApiErrorUtil().errorMessageListToJson(new ArrayList<String>(List.of("Niepoprawny token.")));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        Contact contact = contactService.addContact(bindingModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson(contact));
    }

    @PutMapping("/api/v1/contact/{id}")
    public ResponseEntity<String> EditContact(@PathVariable(name = "id") long id,
                                              @RequestBody ContactBindingModel bindingModel,
                                              @RequestHeader(name="Authorization") String authHeader){
        // Autoryzacja.
        if (!isAuthorizationHeaderInProperFormat(authHeader)){
            String error = new ApiErrorUtil().errorMessageListToJson(new ArrayList<String>(List.of("Brak tokena lub zly format nagłówka.")));
            return ResponseEntity.badRequest().body(error);
        }
        String token = getTokenFromHeader(authHeader);
        if (!tokenService.isTokenValid(token)){
            String error = new ApiErrorUtil().errorMessageListToJson(new ArrayList<String>(List.of("Niepoprawny token.")));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        // Walidacja.
        if (!contactService.doesContactExist(id)){
            String errorJson = new ApiErrorUtil().errorMessageListToJson(new ArrayList<>(List.of("Nie istnieje kontakt o podanym id.")));
            return ResponseEntity.badRequest().body(errorJson);
        }
        Contact contact = contactService.editContact(id, bindingModel);
        return ResponseEntity.ok(new Gson().toJson(contact));
    }

    @DeleteMapping("/api/v1/contact/{id}")
    public ResponseEntity<String> DeleteContact(@PathVariable(name = "id") long id,
                                                @RequestHeader(name="Authorization") String authHeader){
        // Autoryzacja, ctrl+c i ctrl+v jak i forma autoryzacji jest spowodawana brakiem czasu na lepsze wykonanie tego w nowej dla mnie technologii.
        if (!isAuthorizationHeaderInProperFormat(authHeader)){
            String error = new ApiErrorUtil().errorMessageListToJson(new ArrayList<String>(List.of("Brak tokena lub zly format nagłówka.")));
            return ResponseEntity.badRequest().body(error);
        }
        String token = getTokenFromHeader(authHeader);
        if (!tokenService.isTokenValid(token)){
            String error = new ApiErrorUtil().errorMessageListToJson(new ArrayList<String>(List.of("Niepoprawny token.")));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        // Walidacja.
        if (!contactService.doesContactExist(id)){
            String errorJson = new ApiErrorUtil().errorMessageListToJson(new ArrayList<>(List.of("Nie istnieje kontakt o podanym id.")));
            return ResponseEntity.badRequest().body(errorJson);
        }
        contactService.deleteContact(id);
        return ResponseEntity.ok(null);
    }

    private boolean isAuthorizationHeaderInProperFormat(String authHeader){
        if (authHeader == null){
            return false;
        }
        String[] arr = authHeader.split(" ");
        if (arr.length == 2 && arr[0].equals("Bearer")){
            return true;
        }
        else{
            return false;
        }
    }

    private String getTokenFromHeader(String authHeader){
        String[] arr = authHeader.split(" ");
        return arr[1];
    }
}
