package pl.npc.contactmanager.dto.viewmodels;

import java.util.List;

public class ApiErrorViewModel {
    private List<String> errorMessages;

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
