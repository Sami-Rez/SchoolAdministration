package htl.SchoolAdministration.service.student;

public record UpdateStudentCommand(
        String key,
        String username,
        String password,
        String lastName,
        String firstName,
        String birthday) {
}
