package htl.SchoolAdministration.service.teacher;

public record UpdateTeacherCommand(
        String key,
        String username,
        String password,
        String lastName,
        String firstName,
        String birthday) {
}
