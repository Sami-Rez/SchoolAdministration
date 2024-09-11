package htl.SchoolAdministration.presentation.www;

import htl.SchoolAdministration.domain.Student;
import htl.SchoolAdministration.domain.Teacher;
import htl.SchoolAdministration.service.student.CreateStudentCommand;
import htl.SchoolAdministration.service.teacher.CreateTeacherCommand;

public class RestFixtures {
    public static CreateStudentCommand createStudentCmd(Student student) {
        return new CreateStudentCommand(
                student.getUsername(),
                student.getPassword(),
                student.getFirstName(),
                student.getLastName(),
                student.getBirthday()
        );
    }

    public static CreateTeacherCommand createTeacherCmd(Teacher teacher) {
        return new CreateTeacherCommand(
                teacher.getUsername(),
                teacher.getPassword(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getBirthday()
        );
    }
}
