package htl.SchoolAdministration.service;

import htl.SchoolAdministration.domain.Teacher;
import htl.SchoolAdministration.persistence.TeacherRepository;
import htl.SchoolAdministration.service.connector.HttpBinClient;
import htl.SchoolAdministration.service.teacher.TeacherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static htl.SchoolAdministration.TestFixtures.teacher3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {
  // private TeacherService teacherService;

    @InjectMocks
    private TeacherService teacherService;
    private @Mock TeacherRepository teacherRepository;
    private @Mock HttpBinClient httpBinClient;

    @BeforeEach
    void setup() {
        assertThat(teacherRepository).isNotNull();
     //   teacherService = new TeacherService(teacherRepository);
    }

    @Test
    void ensureFetchTeacherWithNoArgumentCallFindAll() {
        //given
        Optional<String> searchCriteria = Optional.empty();
        var teacher = teacher3("Klaus", "Unger");


        when(teacherRepository.findAll()).thenReturn(List.of(teacher));

        //when
        var result = teacherService.fetchTeachers(searchCriteria);

        //then
        assertThat(result).containsExactly(teacher);
        verify(teacherRepository).findAll();
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void ensureFetchTeacherWithValidArgumentCallfindAllByTeacherNameContainsIgnoreCase() {
        //given
        Optional<String> searchCriteria = Optional.of("Test");
        var teacher = teacher3("Klaus", "Unger");
        when(teacherRepository.findAllByLastNameContainsIgnoreCase(any())).thenReturn(List.of(teacher));

        //when
        var result = teacherService.fetchTeachers(searchCriteria);

        //then
        assertThat(result).containsExactly(teacher);
        verify(teacherRepository).findAllByLastNameContainsIgnoreCase(any());
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    @Transactional // Required for testing transactional methods
    public void testRegisterSuccess() {
        // Arrange
        String username = "test@example.com";
        String password = "password123";
        String firstName = "Moris";
        String lastName = "Dobera";
        String birthday = "1990-01-01";
        String expectedKey = "gen1-er32-ate9-dK00-19ey"; // Assuming httpBinClient returns this

        // Mock httpBinClient
        Mockito.when(httpBinClient.retrieveKey()).thenReturn(expectedKey);

        // Act
        Teacher teacher = teacherService.register(username, password, firstName, lastName, birthday);

        // Assert
        assertNotNull(teacher);
        Assertions.assertEquals(username, teacher.getUsername());
        Assertions.assertEquals(expectedKey, teacher.getKey());
    }

    @Test
    @Transactional
    public void testDeleteTeacherExistingId() {
        // Arrange
        Long existingId = 1L; // Assuming a teacher with ID 1 exists

        // Act
        teacherService.deleteTeacher(existingId);

        // Assert (verify deletion indirectly)
        Optional<Teacher> deletedTeacher= teacherService.getTeacher(existingId);
        Assertions.assertFalse(deletedTeacher.isPresent());
    }

    @Test
    void testDeleteTeacherWorks() {
        String username = "testUsername";
        String password = "testPassword";
        String firstName = "Test";
        String lastName = "User";
        String birthday = "2000-01-01";
        String key = "testKey-1i822-091q-22bt-e32m-5s5u";
        //when(httpBinClient.retrieveKey()).thenReturn(key);
        when(teacherRepository.existsByUsername(username)).thenReturn(false);

        // Act
      Teacher teacher = teacherService.register(username, password, firstName, lastName, birthday);
      //  Teacher teacher = TestFixtures.teacher3("Sami", "Rezaei");
        teacher.setKey("testKey-1i822-091q-22bt-e32m-5s5u");
       // teacherRepository.saveAndFlush(teacher);
        assertThat(teacherService.getTeachers().contains(teacher));
        teacherService.deleteTeacher("testKey-1i822-091q-22bt-e32m-5s5u");
        assertThat(teacherService.getTeachers().isEmpty());
    }


    @Test
    public void testRegisterNewTeachers() {
        // Arrange
        String username = "testUsername";
        String password = "testPassword";
        String firstName = "Test";
        String lastName = "User";
        String birthday = "2000-01-01";
        String key = "testKey";
        when(httpBinClient.retrieveKey()).thenReturn(key);
        when(teacherRepository.existsByUsername(username)).thenReturn(false);

        // Act
        Teacher teacher = teacherService.register(username, password, firstName, lastName, birthday);

        // Assert
        assertNotNull(teacher); // Überprüfe, ob das Teacher-Objekt nicht null ist
        assertEquals(username, teacher.getUsername()); // Überprüfe, ob der Benutzername korrekt gesetzt wurde
        assertEquals(password, teacher.getPassword()); // Überprüfe, ob das Passwort korrekt gesetzt wurde
        assertEquals(firstName, teacher.getFirstName()); // Überprüfe, ob der Vorname korrekt gesetzt wurde
        assertEquals(lastName, teacher.getLastName()); // Überprüfe, ob der Nachname korrekt gesetzt wurde
        assertEquals(birthday, teacher.getBirthday()); // Überprüfe, ob das Geburtsdatum korrekt gesetzt wurde
        assertEquals(key, teacher.getKey()); // Überprüfe, ob der Schlüssel korrekt gesetzt wurde
        verify(teacherRepository, times(1)).existsByUsername(username); // Überprüfe, ob die Methode existsByUsername aufgerufen wurde
        verify(teacherRepository, times(1)).save(teacher); // Überprüfe, ob die Methode save aufgerufen wurde
        verify(httpBinClient, times(1)).retrieveKey(); // Überprüfe, ob die Methode retrieveKey aufgerufen wurde
    }




    @Test
    void register_shouldCreateTeacherAndReturnIt() {
        when(teacherRepository.existsByUsername(any())).thenReturn(false);
        when(httpBinClient.retrieveKey()).thenReturn("generatedKey");

        Teacher teacher = teacherService.register("username@spg.at", "password", "firstName", "lastName", "birthday");

        assertEquals("username@spg.at", teacher.getUsername());
        assertEquals("generatedKey", teacher.getKey());
        // ... assert other fields

        verify(teacherRepository).save(teacher);
        verify(httpBinClient).retrieveKey();
    }

}
