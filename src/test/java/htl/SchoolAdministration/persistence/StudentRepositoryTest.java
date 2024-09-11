package htl.SchoolAdministration.persistence;

import htl.SchoolAdministration.TestContainerConfiguration;
import htl.SchoolAdministration.TestFixtures;
import htl.SchoolAdministration.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static htl.SchoolAdministration.TestFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@DataJpaTest
@Import(TestContainerConfiguration.class)
class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Test
    void ensureSaveAndReReadWorks() {
        School school = TestFixtures.HTL();
        SchoolClass schoolClass = ABIF();
        Student student = student(school, schoolClass);

        assumeThat(repository).isNotNull();

        schoolRepository.save(school);
        schoolClassRepository.save(schoolClass);
        var saved = repository.saveAndFlush(student);

        assertThat(saved).isNotNull();
        assertThat(saved).isSameAs(student);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void ensureUpdateWorks() {
        School school = TestFixtures.HTL();
        SchoolClass schoolClass = ABIF();
        Student student = student3("Sami", "Rezaei");

        schoolRepository.save(school);
        schoolClassRepository.save(schoolClass);

        Student savedStudent = repository.saveAndFlush(student);

        // Änderungen am Student vornehmen
        savedStudent.setFirstName("UpdatedFirstName");

        // Aktualisierten Student speichern
        Student updatedStudent = repository.saveAndFlush(savedStudent);

        // Überprüfen, ob die Aktualisierung erfolgreich war
        assertThat(updatedStudent).isNotNull().isEqualTo(savedStudent);
    }

    @Test
    void ensureDeleteWorks() {
        School school = TestFixtures.HTL();
        SchoolClass schoolClass = ABIF();
        Student student = student(school, schoolClass);

        schoolRepository.save(school);
        schoolClassRepository.save(schoolClass);
        repository.saveAndFlush(student);

        // Student löschen
        repository.delete(student);

        // Überprüfen, ob der Student erfolgreich gelöscht wurde
        assertThat(repository.findById(student.getId())).isEmpty();
    }

    @Test
    void ensureFindAllWorks() {
        School HTL = TestFixtures.HTL();
        SchoolClass ABIF = ABIF();
        Student student = student3("Sami", "Rezaei");
        Student student2 = student3("Michael", "Dobrera");

        HTL.addClasses(ABIF);
        HTL.addStudent(student);
        HTL.addStudent(student2);
        ABIF.setSchool(HTL);
        ABIF.addStudent(student);
        ABIF.addStudent(student2);

        schoolRepository.save(HTL);
        schoolClassRepository.save(ABIF);

        repository.saveAndFlush(student);
        repository.saveAndFlush(student2);

        // Alle Students abrufen
        Iterable<Student> allStudents = repository.findAll();

        // Überprüfen, ob alle Students zurückgegeben wurden
        assertThat(allStudents).contains(student, student2);
    }

    @Test
    void ensureComplexQueryCanBeExecuted() {

        School HTL = TestFixtures.HTL();
        schoolRepository.saveAndFlush(HTL);
        SchoolClass ABIF= ABIF();
        ABIF.setSchool(HTL);

        schoolClassRepository.saveAndFlush(ABIF);

        Student student1 = student3("Sara", "Drogber");
        Student student2 = student3("Miriam", "Korolik");
        Student student3 = student3("Sami", "Rezaei");
        student1.setSchool(HTL);
        student1.setStudentClass(ABIF);
        student1.setGender(Gender.FEMALE);
        student2.setSchool(HTL);
        student2.setStudentClass(ABIF);
        student2.setGender(Gender.FEMALE);
        student3.setSchool(HTL);
        student3.setStudentClass(ABIF);
        student3.setGender(Gender.MALE);

        repository.saveAll(List.of(student1, student2, student3));

        // Act
        List<Student> femaleStudents = repository.findByStudentClassAndGender(ABIF, Gender.FEMALE);

        // Assert
        assertThat(femaleStudents).isNotNull();
        assertThat(femaleStudents.size()).isEqualTo(2);
        assertThat(femaleStudents).contains(student1, student2);
    }

    @Test
    void ensureOverviewQueryCanBeExecuted() {

        School HTL = TestFixtures.HTL();
        schoolRepository.saveAndFlush(HTL);
        SchoolClass ABIF= ABIF();
        ABIF.setSchool(HTL);
        schoolClassRepository.saveAndFlush(ABIF);

        Student student = student3("Sami", "Rezaei");
        student.setSchool(HTL);
        student.setStudentClass(ABIF);
        student.setLastName(("MyLastName"));
        repository.saveAndFlush(student);

        List<StudentProjections.Overview> overview = repository.overview(ABIF, "MyLastName");
        assertThat(repository.overview(student.getStudentClass(), student.getLastName())).isNotNull();

    }

    @Test
    void ensureFindStudentsByGenderAndBirthdayAndReligionReturnsCorrectResults() {

        School HTL = TestFixtures.HTL();
        schoolRepository.saveAndFlush(HTL);
        SchoolClass ABIF= ABIF();
        ABIF.setSchool(HTL);
        schoolClassRepository.saveAndFlush(ABIF);

        Student student1 = student3("Sara", "Drogber");
        Student student2 = student3("Miriam", "Korolik");
        Student student3 = student3("Sami", "Rezaei");
        student1.setGender(Gender.FEMALE);
        student1.setBirthday("2000, 5,15");
        student1.setReligion(Religion.BUDDHISM);
        student1.setSchool(HTL);
        student1.setStudentClass(ABIF);

        student2.setGender(Gender.FEMALE);
        student2.setBirthday("1999, 10, 20");
        student2.setReligion(Religion.JUDAISM);
        student2.setSchool(HTL);
        student2.setStudentClass(ABIF);

        student3.setGender(Gender.MALE);
        student3.setBirthday("2001, 3, 8");
        student3.setReligion(Religion.CHRISTIANITY);
        student3.setSchool(HTL);
        student3.setStudentClass(ABIF);
        repository.saveAll(List.of(student1, student2, student3));

        List<Student> femaleBuddhistStudents = repository.findStudentsByGenderAndBirthdayAndReligion(Gender.FEMALE,"2000, 5,15", Religion.BUDDHISM);

        assertThat(femaleBuddhistStudents).isNotNull();
        assertThat(femaleBuddhistStudents.size()).isEqualTo(1);
        assertThat(femaleBuddhistStudents.get(0)).isEqualTo(student1);
    }


}




























































