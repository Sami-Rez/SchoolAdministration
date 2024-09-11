package htl.SchoolAdministration.persistence;

import htl.SchoolAdministration.TestContainerConfiguration;
import htl.SchoolAdministration.TestFixtures;
import htl.SchoolAdministration.domain.School;
import htl.SchoolAdministration.domain.SchoolClass;
import htl.SchoolAdministration.domain.Student;
import htl.SchoolAdministration.domain.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@DataJpaTest
@Import(TestContainerConfiguration.class)

public class SchoolClassRepositoryTest {
    @Autowired
    private SchoolClassRepository repository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void ensureSaveAndReReadWorks() {
        SchoolClass schoolClass = TestFixtures.ABIF();
        School school = TestFixtures.HTL();
        Student student = TestFixtures.student(school, schoolClass);
        Teacher teacher = TestFixtures.teacher(school, Set.of(schoolClass));

        assumeThat(repository).isNotNull();

        var saved = repository.saveAndFlush(schoolClass);

        assertThat(saved).isSameAs(schoolClass);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void ensureUpdateWorks() {
        SchoolClass schoolClass = TestFixtures.ABIF();
        School school = TestFixtures.HTL();
        Student student = TestFixtures.student(school, schoolClass);
        Teacher teacher = TestFixtures.teacher(school, Set.of(schoolClass));

        // Annahme: Wir haben eine gespeicherte SchoolClass
        SchoolClass savedSchoolClass = repository.saveAndFlush(schoolClass);

        // Änderungen an der SchoolClass vornehmen
        savedSchoolClass.setFloor(4);

        // Aktualisierte SchoolClass speichern
        SchoolClass updatedSchoolClass = repository.saveAndFlush(savedSchoolClass);

        // Überprüfen, ob die Aktualisierung erfolgreich war
        assertThat(updatedSchoolClass).isNotNull().isEqualTo(savedSchoolClass);
    }

    @Test
    void ensureDeleteWorks() {
        SchoolClass schoolClass = TestFixtures.ABIF();
        School school = TestFixtures.HTL();
        Student student = TestFixtures.student(school, schoolClass);
        Teacher teacher = TestFixtures.teacher(school, Set.of(schoolClass));

        // Annahme: wir haben eine gespeicherte SchoolClass
        SchoolClass savedSchoolClass = repository.saveAndFlush(schoolClass);

        // SchoolClass löschen
        repository.delete(savedSchoolClass);

        // Überprüfen, ob die SchoolClass erfolgreich gelöscht wurde
        assertThat(repository.findById(savedSchoolClass.getId())).isEmpty();
    }

    @Test
    void ensureFindAllWorks() {
        SchoolClass schoolClass = TestFixtures.ABIF();
        SchoolClass schoolClass2 = TestFixtures.ABIF();
        School school = TestFixtures.HTL();
        Student student = TestFixtures.student(school, schoolClass);
        Teacher teacher = TestFixtures.teacher(school, Set.of(schoolClass));

        // Annahme: wir haben 2 SchoolClasses in der Datenbank
        repository.saveAndFlush(schoolClass);
        repository.saveAndFlush(schoolClass2);

        // Alle SchoolClasses abrufen
        Iterable<SchoolClass> allSchoolClasses = repository.findAll();

        // Überprüfen, ob alle SchoolClasses zurückgegeben wurden
        assertThat(allSchoolClasses).contains(schoolClass, schoolClass2);
    }

/*    @Test
    void ensureComplexQueryCanBeExecuted() {
        SchoolClass schoolClass = TestFixtures. schoolClass2();
     //   assertThat(repository.complexQuery(school())).isNotNull();
        repository.saveAndFlush(schoolClass2());
        assertThat(repository.complexQuery(school())).isNotNull();
    }*/

  /*  @Test
    void ensureOverviewQueryCanBeExecuted() {
        School school = TestFixtures.school2();
        //assertThat(repository.overview(school)).isNotNull();
        repository.saveAndFlush(school2())
        assertThat(repository.overview(school)).isNotNull();
    }*/

/*    @Test
    void ensureOverviewQueryCanBeExecuted() {
        School school = TestFixtures.school();
        repository.saveAndFlush(schoolClass(school));
        assertThat(repository.overview(school)).isNotNull();
    }*/

}
