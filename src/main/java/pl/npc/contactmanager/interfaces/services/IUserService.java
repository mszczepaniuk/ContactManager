package pl.npc.contactmanager.interfaces.services;

import pl.npc.contactmanager.entities.User;

public interface IUserService {
    public User register(String username, String password);
    public boolean isPasswordCorrect(String username, String password);
    public boolean isUsernameTaken(String username);
    public User getByUsername(String username);
}
