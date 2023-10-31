package space.glowberry.homeworkcollectbackend.Entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class RandomTokenGenerator {

    @Value("${settings.token_length}")
    private int DEFAULT_TOKEN_LENGTH;

    public String generateToken(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[length];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token);
    }

    public String generateToken() {
        return generateToken(DEFAULT_TOKEN_LENGTH);
    }
}
