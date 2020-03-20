package pl.npc.contactmanager.dto.viewmodels;

public class AuthenticateViewModel {
    private String accessToken;
    private long userId;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
