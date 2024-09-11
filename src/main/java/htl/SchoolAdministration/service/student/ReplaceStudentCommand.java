package htl.SchoolAdministration.service.student;

public record ReplaceStudentCommand(
        String username,
        String password,
        String firstName,
        String lastName,
        String birthday) {
}
