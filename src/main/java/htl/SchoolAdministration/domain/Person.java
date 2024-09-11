package htl.SchoolAdministration.domain;

import htl.SchoolAdministration.tools.StrongPassword;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)

public abstract class Person extends AbstractPersistable<Long> {
    public static final int LENGTH_FIRSTNAME = 64;
    public static final int LENGTH_PASSWORD = 128;
    public static final int LENGTH_LASTNAME = 32;
    public static final int LENGTH_USERNAME = 64;



    @Column(length = LENGTH_USERNAME)
    private @NotBlank String username;


    @Column(name = "first_name", length = LENGTH_FIRSTNAME, nullable = false)
    private @NotBlank String firstName;

    @Column(name = "last_name", length = LENGTH_LASTNAME, nullable = false)
    private @NotBlank String lastName;

    //Passwort darfl keine kurze Laenge haben.
    @Column(length = LENGTH_PASSWORD, nullable = false)
    @StrongPassword
    private @NotBlank String password;

    @Column(name = "birthday")
    private String birthday;

}
