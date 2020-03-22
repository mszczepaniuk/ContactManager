package pl.npc.contactmanager.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.npc.contactmanager.entities.User;
import pl.npc.contactmanager.interfaces.services.ITokenService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

// Na podstawie: https://github.com/auth0/java-jwt
@Service
public class JWTTokenService implements ITokenService {

    @Value("${contactmanager.token.secret}")
    private String secretKey;
    @Value("${contactmanager.token.validtime}")
    private int validTimeInMinutes;
    @Value("${contactmanager.token.issuer}")
    private String issuer;

    @Override
    public String getAccessToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withSubject(Long.toString(user.getId()))
                .withExpiresAt(getExpirationDate())
                .withIssuer(issuer)
                .sign(algorithm);
        return token;
    }

    @Override
    public String getSubClaim(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }

    @Override
    public boolean isTokenValid(String token) {
        JWTVerificationException ex = null;
        try {
            getSubClaim(token);
        } catch (JWTVerificationException exception) {
            ex = exception;
        }
        return ex == null;
    }

    private Date getExpirationDate() {
        LocalDateTime localDate = LocalDateTime.now();
        return Date.from(localDate.plusMinutes(validTimeInMinutes).atZone(ZoneId.systemDefault()).toInstant());
    }
}