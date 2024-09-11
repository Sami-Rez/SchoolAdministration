package htl.SchoolAdministration.persistence;

import htl.SchoolAdministration.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentRepositoryCustomImpl extends QuerydslRepositorySupport implements StudentRepositoryCustom {

    private QStudent student = QStudent.student;

    public StudentRepositoryCustomImpl() {
        super(Student.class);
    }

    @Override
    public List<Student> findByStudentClassAndGender(SchoolClass schoolClass, Gender gender) {
        return from(student)
                .where(student.studentClass.eq(schoolClass)
                        .and(student.gender.eq(gender)))
                .fetch();
    }

    @Override
    public List<StudentProjections.Overview> overview(SchoolClass schoolClass, String lastName) {
        return from(student)
                .where(student.studentClass.eq(schoolClass)
                        .and(student.lastName.eq(lastName)))
                        .select(new QStudentProjections_Overview(student.lastName, student.studentClass))
                .fetch();
    }

    @Override
    public List<Student> findStudentsByGenderAndBirthdayAndReligion(Gender gender, String birthday, Religion religion) {
        return from(student)
                .where(student.gender.eq(gender)
                        .and(student.birthday.eq(birthday))
                        .and(student.religion.eq(religion)))
                .fetch();
    }

}


