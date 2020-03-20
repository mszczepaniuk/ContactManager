package pl.npc.contactmanager.util.apierror;

import com.google.gson.Gson;
import pl.npc.contactmanager.dto.viewmodels.ApiErrorViewModel;

import java.util.List;

public class ApiErrorUtil {
    public String errorMessageListToJson(List<String> errorMessages){
        ApiErrorViewModel vm = new ApiErrorViewModel();
        vm.setErrorMessages(errorMessages);
        return new Gson().toJson(vm);
    }
}
