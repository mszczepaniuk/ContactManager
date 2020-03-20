package pl.npc.contactmanager.dto.bindingmodels;

public class LoginUserBindingModel {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPasword(String password) {
        this.password = password;
    }
}
