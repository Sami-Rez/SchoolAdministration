package htl.SchoolAdministration.service.exceptions;

import lombok.Getter;

public class PersonAlreadyExistsException extends RuntimeException {

    @Getter
    private final String username;
    public PersonAlreadyExistsException(String username, String message) {
        super(message);
        this.username = username;
    }

    public static PersonAlreadyExistsException forExistingUsername(String username) {
        String message = "Person with username %s already exists!".formatted(username);
        return new PersonAlreadyExistsException(username, message);
    }
}
