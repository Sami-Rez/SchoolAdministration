package htl.SchoolAdministration.service;

import htl.SchoolAdministration.TestFixtures;
import htl.SchoolAdministration.domain.Student;
import htl.SchoolAdministration.persistence.StudentRepository;
import htl.SchoolAdministration.service.connector.HttpBinClient;
import htl.SchoolAdministration.service.exceptions.PersonAlreadyExistsException;
import htl.SchoolAdministration.service.student.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class StudentServiceTest {
    private StudentService studentService;

    //  private StudentRepository studentRepository;
    private @Mock StudentRepository studentRepository;
    private @Mock HttpBinClient httpBinClient;

    /*@InjectMocks
    private StudentService service;*/

    @BeforeEach
    void setup() {
        assertThat(studentRepository).isNotNull();
        studentService = new StudentService(studentRepository, httpBinClient);
        //MockitoAnnotations.openMocks(this);

    }

    @Test
    void ensureRegisterWorks() throws Exception{
        String username = "existing@Student.at";
        String password="AVERySEcuRE!pass1283";
        String firstName="Saman";
        String lastName= "Dobera";
        String birthday = "1990-01-01";
        try {
            Student existingStudent = TestFixtures.student4("existing@Student.at", "GEHEImeR!paSSforY00", "Saman", "Dobera");
            studentService.register(username, password, firstName, lastName, birthday);
        } catch (PersonAlreadyExistsException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testRegisterNewStudents() {
        // Arrange
        String username = "testUsername";
        String password = "testPassword";
        String firstName = "Test";
        String lastName = "User";
        String birthday = "2000-01-01";
        String key = "testKey";
        when(httpBinClient.retrieveKey()).thenReturn(key);
        when(studentRepository.existsByUsername(username)).thenReturn(false);

        // Act
        Student student = studentService.register(username, password, firstName, lastName, birthday);

        // Assert
        assertNotNull(student); // Überprüfe, ob das Student-Objekt nicht null ist
        assertEquals(username, student.getUsername()); // Überprüfe, ob der Benutzername korrekt gesetzt wurde
        assertEquals(password, student.getPassword()); // Überprüfe, ob das Passwort korrekt gesetzt wurde
        assertEquals(firstName, student.getFirstName()); // Überprüfe, ob der Vorname korrekt gesetzt wurde
        assertEquals(lastName, student.getLastName()); // Überprüfe, ob der Nachname korrekt gesetzt wurde
        assertEquals(birthday, student.getBirthday()); // Überprüfe, ob das Geburtsdatum korrekt gesetzt wurde
        assertEquals(key, student.getKey()); // Überprüfe, ob der Schlüssel korrekt gesetzt wurde
        verify(studentRepository, times(1)).existsByUsername(username); // Überprüfe, ob die Methode existsByUsername aufgerufen wurde
        verify(studentRepository, times(1)).save(student); // Überprüfe, ob die Methode save aufgerufen wurde
        verify(httpBinClient, times(1)).retrieveKey(); // Überprüfe, ob die Methode retrieveKey aufgerufen wurde
    }




    @Test
    void register_shouldCreateStudentAndReturnIt() {
        when(studentRepository.existsByUsername(any())).thenReturn(false);
        when(httpBinClient.retrieveKey()).thenReturn("generatedKey");

        Student student = studentService.register("username@spg.at", "password", "firstName", "lastName", "birthday");

        assertEquals("username@spg.at", student.getUsername());
        assertEquals("generatedKey", student.getKey());
        // ... assert other fields

        verify(studentRepository).save(student);
        verify(httpBinClient).retrieveKey();
    }


    @Test
    void ensureFetchStudentWithNoArgumentCallFindAll() {
        //given
        Optional<String> searchCriteria = Optional.empty();
        var student = TestFixtures.student3("Rezaei", "Sami");
        when(studentRepository.findAll()).thenReturn(List.of(student));

        //when
        var result = studentService.fetchStudents(searchCriteria);

        //then
        assertThat(result).containsExactly(student);
        verify(studentRepository).findAll();
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void ensureFetchStudentWithValidArgumentCallfindAllByStudentNameContainsIgnoreCase() {
        //given
        Optional<String> searchCriteria = Optional.of("Test");
        var student = TestFixtures.student3("Sami", "Rezaei");
        when(studentRepository.findAllByLastNameContainsIgnoreCase(any())).thenReturn(List.of(student));

        //when
        var result = studentService.fetchStudents(searchCriteria);

        //then
        assertThat(result).containsExactly(student);
        verify(studentRepository).findAllByLastNameContainsIgnoreCase(any());
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    @Transactional // Required for testing transactional methods
    public void testDeleteStudent_ExistingId() {
        // Arrange
        Long existingId = 1L; // Assuming a student with ID 1 exists

        // Mock (optional)
        // If repository.deleteById needs mocking, do it here

        // Act
        studentService.deleteStudent(existingId);

        // Assert (verify deletion indirectly)
        Optional<Student> deletedStudent = studentService.getStudent(existingId);
        Assertions.assertFalse(deletedStudent.isPresent());
    }

    @Test
    @Transactional // Required for testing transactional methods
    public void testRegister_Success() {
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
        Student student = studentService.register(username, password, firstName, lastName, birthday);

        // Assert
        assertNotNull(student);
        assertEquals(username, student.getUsername());
        assertEquals(expectedKey, student.getKey());
    }




}

