package pl.npc.contactmanager.services;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.npc.contactmanager.interfaces.services.IPasswordService;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Service
public class PasswordService implements IPasswordService {

    @Value("${password.minlength}")
    private int requiredLength;
    @Value("${password.requiredigit}")
    private boolean requireDigit;

    @Override
    public byte[] getPasswordHash(String password, byte[] salt) {
        HashFunction hashFunction = Hashing.sha512();
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] hash = hashFunction.hashBytes(concatenateByteArrays(passwordBytes, salt)).asBytes();
        return hash;
    }

    // Na podstawie: https://www.baeldung.com/java-password-hashing
    @Override
    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    @Override
    public boolean isPasswordValid(String password) {
        if (requireDigit && !password.matches(".*\\d.*")){
            return false;
        }
        if (password.length() < requiredLength){
            return false;
        }
        return true;
    }

    // Na podstawie: https://stackoverflow.com/questions/5513152/easy-way-to-concatenate-two-byte-arrays/23292834
    private byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

}
