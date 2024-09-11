package htl.SchoolAdministration.service.exceptions;

import lombok.Getter;

public class DuplicateUsernameException extends RuntimeException {

    @Getter
    private final String username;
    public DuplicateUsernameException(String username, String message) {
        super(message);
        this.username = username;
    }

    public static DuplicateUsernameException forExistingUsername(String username) {
        String message = "Person with username %s already exists!".formatted(username);
        return new DuplicateUsernameException(username, message);
    }
}
