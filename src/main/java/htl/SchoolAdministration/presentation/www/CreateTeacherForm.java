package htl.SchoolAdministration.presentation.www;

import htl.SchoolAdministration.domain.Person;
import htl.SchoolAdministration.tools.StrongPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTeacherForm(
        @NotBlank String username,
        @StrongPassword String password,
        @NotBlank @Size(min = 2, max = Person.LENGTH_FIRSTNAME) String firstName,
        @NotBlank @Size(min = 2, max = Person.LENGTH_LASTNAME) String lastName,
        @NotBlank String birthday) {
    public static CreateTeacherForm create() {
        return new CreateTeacherForm(
                "",
                "",
                "",
                "",
                "");
    }

}
