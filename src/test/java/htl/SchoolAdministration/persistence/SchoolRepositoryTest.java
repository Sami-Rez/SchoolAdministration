package htl.SchoolAdministration.persistence;

import htl.SchoolAdministration.TestContainerConfiguration;
import htl.SchoolAdministration.domain.School;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static htl.SchoolAdministration.TestFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

@DataJpaTest
@Import(TestContainerConfiguration.class)
class SchoolRepositoryTest {

    private @Autowired SchoolRepository repository;

    @Test
    void ensureSaveAndReReadWorks() {

        //eine School Erstellen:
        School school = HTL();

        assumeThat(repository).isNotNull();

        var saved = repository.saveAndFlush(school);

        // das Gespeicherte muss dasselbe Objekt sein wie die erzeugte Schule
        // Id darf nicht Null sein
        assertThat(saved).isNotNull().isSameAs(school);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void ensureUpdateWorks() {
        // Schule
        School savedSchool = repository.saveAndFlush(HTL());

        // Änderungen an der Schule vornehmen
        savedSchool.setDirector("NewDirector");

        // Aktualisierte Schule speichern
        School updatedSchool = repository.saveAndFlush(savedSchool);

        // Überprüfen, ob die Aktualisierung erfolgreich war
        assertThat(updatedSchool).isNotNull().isEqualTo(savedSchool);
    }

    @Test
    void ensureDeleteWorks() {
        // Schule
        School savedSchool = repository.saveAndFlush(HTL());

        // Schule löschen
        repository.delete(savedSchool);

        // Überprüfen, ob die Schule erfolgreich gelöscht wurde
        assertThat(repository.findById(savedSchool.getId())).isEmpty();
    }

    @Test
    void ensureFindAllWorks() {

        // Annahme: wir haben 2 Schulen in der Datenbank
        School school1 = repository.saveAndFlush(HTL());
        School school2 = repository.saveAndFlush(school2());

        // Alle Schulen abrufen
        Iterable<School> allSchools = repository.findAll();

        // Überprüfen, ob alle Schulen zurückgegeben wurden
        assertThat(allSchools).containsExactly(school1, school2);
    }

}



