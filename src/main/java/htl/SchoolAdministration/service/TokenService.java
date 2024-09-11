package htl.SchoolAdministration.service;/*

package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.domain.Token;
import at.spengergasse.sj2324seedproject.domain.TokenType;
import at.spengergasse.sj2324seedproject.persistence.TokenRepository;
import at.spengergasse.sj2324seedproject.tools.DateTimeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class TokenService {

    private static final char[] ALPHABET = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final SecureRandom RANDOM = new SecureRandom();
    private final TokenRepository tokenRepository;
    private DateTimeFactory dateTimeFactory;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Token createApiKey(Class<?> clazz) {
        return createToken(TokenType.API_KEY, Optional.of(clazz));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Token createREgistrationToken() {
        return createToken(TokenType.REGISTRATION, Optional.empty());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Token createResetPasswordToken() {
        return createToken(TokenType.RESET_PASSWORD, Optional.empty());
    }

    public Token createToken(TokenType type) {
        return createToken(type, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Token createToken(TokenType type, Optional<Class<?>> clazz){
    String value;
        do {
            value = generateValue(type.getLength());
        } while ((tokenRepository.existsByValue(value)));
        LocalDateTime expirationTS = Optional.ofNullable(type.getExpiresInSeconds())
                .map(seconds -> dateTimeFactory.now().plusSeconds(seconds))
                .orElse(null);
        String targetClass = clazz.map(Class::getSimpleName).orElse(null);
        Token token = Token.builder().value(value)
                .expirationTS(expirationTS)
                .type(type)
                .targetClass(targetClass)
                .build();
        tokenRepository.save(token);
        return token;
    }

    @Async
    @Scheduled(fixedDelay = 60 * 1000)
    public void clearnupExpiredTokens() {
        tokenRepository.deleteAllByExpirationTsBefore(dateTimeFactory.now());
    }

    private String generateValue(int length) {
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            char pick = ALPHABET[RANDOM.nextInt(ALPHABET.length)];
            result[i] = pick;
        }
        return new String(result);
    }
}

*/
