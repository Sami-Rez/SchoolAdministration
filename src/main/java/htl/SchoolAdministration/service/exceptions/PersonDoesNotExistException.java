package htl.SchoolAdministration.service.exceptions;

import lombok.Getter;

@Getter
public class PersonDoesNotExistException extends RuntimeException {

    private final String key;
    public PersonDoesNotExistException(String key, String message) {
        super(message);
        this.key = key;
    }
    public static PersonDoesNotExistException forKey(String key) {
        String message = "Person with key %s does not exist anymore!".formatted(key);
        return new PersonDoesNotExistException(key, message);
    }
}




