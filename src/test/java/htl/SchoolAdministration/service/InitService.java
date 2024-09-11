package htl.SchoolAdministration.service;

import htl.SchoolAdministration.persistence.SchoolClassRepository;
import htl.SchoolAdministration.persistence.SchoolRepository;
import htl.SchoolAdministration.persistence.StudentRepository;
import htl.SchoolAdministration.persistence.TeacherRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class InitService {
    private final SchoolRepository schoolRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @PostConstruct
    public void init() {

       // School school = HTL();
     /*   SchoolClass schoolClass = TestFixtures.ABIF();
        schoolClass.setSchool(HTL());
        Student student = TestFixtures.student3("Rezaei", "Sami");
        Teacher teacher = TestFixtures.teacher2();
        schoolClass.addStudent(student);
        schoolClass.addTeacher(teacher);
        school.addClasses(schoolClass);
*/
   //     schoolRepository.save(school);
    }
}
