package htl.SchoolAdministration.service.student;

import htl.SchoolAdministration.domain.*;
import htl.SchoolAdministration.persistence.StudentRepository;
import htl.SchoolAdministration.service.DomainAttributeSupport;
import htl.SchoolAdministration.service.connector.HttpBinClient;
import htl.SchoolAdministration.service.exceptions.PersonAlreadyExistsException;
import htl.SchoolAdministration.service.exceptions.PersonDoesNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2

@Service
//@Transactional
public class StudentService implements DomainAttributeSupport {

    private final StudentRepository repository;
    private final HttpBinClient httpBinClient;

    public List<Student> fetchStudents(Optional<String> namePart) {
        log.info("sarting to fetch data for StudentService.fetchStudents");

        return namePart.map(repository::findAllByLastNameContainsIgnoreCase)
                .orElseGet(repository::findAll);
    }

    @Transactional(readOnly = true)
    public List<Student> getStudents() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Student> getStudent(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Student> searchStudents(String lastNamePart) {
        return repository.findAllByLastNameLikeIgnoreCase(lastNamePart);
    }

    @Transactional
    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Student> getStudentByKey(String key) {
        return repository.findByKey(key);
    }

    @Transactional
    public void deleteStudentByKey(String key) {
        repository.deleteByKey(key);
    }

    @Transactional
    public Student register(String username, String password, String firstName, String lastName, String birthday) {

        log.debug("Checking if student '{}' exists in DB", username);
        var exists = repository.existsByUsername(username);
        if (exists) {
            log.warn("Student '{}' already exists -> throw Exception", username);
            throw PersonAlreadyExistsException.forExistingUsername(username);
        }
        String key = httpBinClient.retrieveKey();
        log.info("Generated key {} for new student '{}'", key, username);

        Student student = Student.builder()
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .birthday(birthday)
                .key(key)
                .religion(Religion.CHRISTIANITY)
                .gender(Gender.FEMALE)
                .mobileNumber(MobileNumber.builder()
                        .countryCode(43)
                        .areaCode("01")
                        .mobileNumber("67801234")
                        .build())
                .address(Address.builder()
                        .strNumber("Schneeweg 10")
                        .zipCode("1220")
                        .city("Vienna")
                        .country(Country.builder()
                                .name("Austria")
                                .iso2Code("AT")
                                .topLevelDomain(".at")
                                .build())
                        .build())
                .build();
        repository.save(student);
        log.info("Student '{}' (id={}, key={}) successfully saved", username, student.getId(), student.getKey());
        log.debug("Send registration confirmation email for student '{}'", username);
        return student;
    }

    @Transactional // (readOnly = false)
    public Student replaceStudent(Long id, String username, String password, String firstName, String lastName, String birthday) {
        log.debug("Check if username is already defined");
        if (repository.existsByUsername(username)) {
            log.warn("Person with username {} already exists! Redirect to forget password page!");
            throw PersonAlreadyExistsException.forExistingUsername(username);
        }

        return repository.findById(id)
                .map(student -> {
                    student.setUsername(username);
                    student.setPassword(password);
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    student.setBirthday(birthday);
                    return student;
                })
                // .map(repository::save) falsch, da wir uns in einem Transaction befinden.
                // Es wird am Ende durch ein Commit/Rolleback entschieden.
                .orElseGet(() -> updateStudent(password, username, lastName, firstName, birthday));
    }

    @Transactional
    public Student updateStudent(String key, String password, String firstName, String lastName, String birthday) {
        return repository.findByKey(key)
                .map(student -> {
                    //student.setUsername(username);
                    student.setKey(key);
                    student.setPassword(password);
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    student.setBirthday(birthday);
                    return student;
                })
                //  .map(repository::save)    save is not necessary while updating
                .orElseThrow(() -> PersonDoesNotExistException.forKey(key));
    }
}


