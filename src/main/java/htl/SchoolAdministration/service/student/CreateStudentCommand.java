package htl.SchoolAdministration.service.student;

import jakarta.validation.constraints.NotBlank;

public record CreateStudentCommand(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String lastName,
        @NotBlank String firstName,
        @NotBlank String birthday) {
}
