package pl.npc.contactmanager.services;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import pl.npc.contactmanager.entities.User;
import pl.npc.contactmanager.interfaces.repositories.IUserRepository;
import pl.npc.contactmanager.interfaces.services.IPasswordService;
import pl.npc.contactmanager.interfaces.services.IUserService;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepo;
    private final IPasswordService passwordService;

    @Autowired
    public UserService(IUserRepository userRepo,
                       IPasswordService passwordService) {
        this.userRepo = userRepo;
        this.passwordService = passwordService;
    }

    @Override
    public User register(String username, String password) {
        byte[] salt = passwordService.generateSalt();
        byte[] hashedPassword = passwordService.getPasswordHash(password, salt);
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        user.setCreationDate(new Date());
        userRepo.save(user);
        return user;
    }

    @Override
    public boolean isPasswordCorrect(String username, String password) {
        User user = findUserByUsername(username).get();
        byte[] providedPasswordHash = passwordService.getPasswordHash(password, user.getSalt());

        if (Arrays.equals(user.getPassword(), providedPasswordHash)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return findUserByUsername(username).isPresent();
    }

    @Override
    public User getByUsername(String username) {
        return findUserByUsername(username).get();
    }

    private Optional<User> findUserByUsername(String username) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id").withIgnoreNullValues();
        User exampleUser = new User();
        exampleUser.setUsername(username);
        Example<User> example = Example.of(exampleUser, matcher);
        return userRepo.findOne(example);
    }
}
