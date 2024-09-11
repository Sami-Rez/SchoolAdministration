package htl.SchoolAdministration.service.teacher;

import jakarta.validation.constraints.NotBlank;

public record CreateTeacherCommand(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String lastName,
        @NotBlank String firstName,
        @NotBlank String birthday) {
}
