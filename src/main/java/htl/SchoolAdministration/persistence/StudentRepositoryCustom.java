package htl.SchoolAdministration.persistence;

import htl.SchoolAdministration.domain.*;

import java.util.List;


public interface StudentRepositoryCustom {
   public List<Student> findByStudentClassAndGender(SchoolClass schoolClass, Gender gender);

    public List<StudentProjections.Overview> overview(SchoolClass schoolClass, String lastName);


    public List<Student> findStudentsByGenderAndBirthdayAndReligion(Gender gender, String birthday, Religion religion);
}
