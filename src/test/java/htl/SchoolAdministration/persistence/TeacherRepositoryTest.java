package htl.SchoolAdministration.persistence;

import htl.SchoolAdministration.TestContainerConfiguration;
import htl.SchoolAdministration.TestFixtures;
import htl.SchoolAdministration.domain.School;
import htl.SchoolAdministration.domain.SchoolClass;
import htl.SchoolAdministration.domain.Teacher;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static htl.SchoolAdministration.TestFixtures.teacher;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestContainerConfiguration.class)

public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository repository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Test
    void ensureSaveAndReadWorks() {
        School school = TestFixtures.HTL();
        SchoolClass schoolClass = TestFixtures.ABIF();
        schoolClass.setSchool(school);
        Teacher teacher = teacher(school, Set.of(schoolClass));

        schoolRepository.saveAndFlush(school);
        schoolClassRepository.saveAndFlush(schoolClass);

        var saved = repository.saveAndFlush(teacher);

        schoolClass.addTeacher(teacher);

        Assumptions.assumeThat(repository).isNotNull();

        assertThat(saved).isSameAs(teacher);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getAddress().getCountry().getId()).isNotNull();
    }

    @Test
    void ensureUpdateWorks() {
        School school = TestFixtures.HTL();
        SchoolClass schoolClass = TestFixtures.ABIF();

        Teacher teacher = teacher(school, Set.of(schoolClass));

        schoolRepository.saveAndFlush(school);
        schoolClassRepository.saveAndFlush(schoolClass);
        repository.saveAndFlush(teacher(school, Set.of(schoolClass)));

        //  Nachnamen des Lehrers ändern
        teacher.setLastName("UpdatedLastName");
        repository.saveAndFlush(teacher);

        //  nach dem aktualisierten Lehrer in der Datenbank suchen
        Optional<Teacher> updatedTeacher = repository.findById(teacher.getId());

        // Überprüfen, ob der Lehrer gefunden wurde und der Nachname aktualisiert wurde
        assertThat(updatedTeacher).isPresent();
        updatedTeacher.ifPresent(t -> assertThat(t.getLastName()).isEqualTo("UpdatedLastName"));
    }

    @Test
    void ensureDeleteWorks() {

        School HTL = TestFixtures.HTL();
        schoolRepository.saveAndFlush(HTL);
        SchoolClass ABIF = TestFixtures.ABIF();
        ABIF.setSchool(HTL);
        schoolClassRepository.saveAndFlush(ABIF);
        Teacher teacher = TestFixtures.teacher2();
        teacher.setSchool(HTL);
        teacher.setClasses(List.of(ABIF));
        repository.saveAndFlush(teacher);

        //  den Lehrer aus der Datenbank löschen
        repository.delete(teacher);

        // Überprüfen, ob der Lehrer nicht mehr in der Datenbank existiert

        assertThat(repository.findById(teacher.getId())).isEmpty();
    }

    @Test
    void ensureFindAllWorks() {

        School HTL = TestFixtures.HTL();
        schoolRepository.saveAndFlush(HTL);
        SchoolClass ABIF = TestFixtures.ABIF();
        ABIF.setSchool(HTL);
        schoolClassRepository.saveAndFlush(ABIF);

        Teacher teacher = TestFixtures.teacher2();
        teacher.setSchool(HTL);
        teacher.setClasses(List.of(ABIF));
        repository.saveAndFlush(teacher);

        Teacher teacher2 = TestFixtures.teacher2();
        teacher2.setSchool(HTL);
        teacher2.setClasses(List.of(ABIF));
        repository.saveAndFlush(teacher2);

        //  nach allen Lehrern in der Datenbank suchen
        List<Teacher> allTeachers = repository.findAll();

        // Überprüfen, ob die Liste nicht leer ist und die erwartete Anzahl von Lehrern enthält
        assertThat(allTeachers).isNotEmpty().hasSize(2);
    }

}
