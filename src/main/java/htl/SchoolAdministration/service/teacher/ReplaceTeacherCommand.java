package htl.SchoolAdministration.service.teacher;

public record ReplaceTeacherCommand(
        String username,
        String password,
        String firstName,
        String lastName,
        String birthday) {
}
