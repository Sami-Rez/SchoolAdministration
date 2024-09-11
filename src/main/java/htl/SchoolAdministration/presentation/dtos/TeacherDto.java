package htl.SchoolAdministration.presentation.dtos;

import htl.SchoolAdministration.domain.Teacher;

public record TeacherDto(String username, String firstName, String lastName, String birthday) {

    public TeacherDto(Teacher teacher) {
        // username ist ein reachType EmailAddress

        this(teacher.getUsername(), teacher.getFirstName(), teacher.getLastName(), teacher.getBirthday());

    }
}
