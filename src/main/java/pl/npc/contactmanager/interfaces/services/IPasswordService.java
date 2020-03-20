package pl.npc.contactmanager.interfaces.services;

public interface IPasswordService {
    public byte[] getPasswordHash(String password, byte[] salt);
    public byte[] generateSalt();
    public boolean isPasswordValid(String password);
}
