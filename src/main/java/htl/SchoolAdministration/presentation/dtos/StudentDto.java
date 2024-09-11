package htl.SchoolAdministration.presentation.dtos;

import htl.SchoolAdministration.domain.Student;

public record StudentDto(String username, String firstName, String lastName, String birthday) {

    public StudentDto(Student student) {
        this(student.getUsername(),
                student.getFirstName(),
                student.getLastName(),
                student.getBirthday());
    }
}
