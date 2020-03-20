package pl.npc.contactmanager.interfaces.services;

import pl.npc.contactmanager.entities.User;

public interface ITokenService {
    String getAccessToken(User user);
    String getSubClaim(String token);
    boolean isTokenValid(String token);
}
