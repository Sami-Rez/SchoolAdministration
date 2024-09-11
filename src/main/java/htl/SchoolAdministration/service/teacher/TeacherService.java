package htl.SchoolAdministration.service.teacher;

import htl.SchoolAdministration.domain.*;
import htl.SchoolAdministration.persistence.TeacherRepository;
import htl.SchoolAdministration.service.DomainAttributeSupport;
import htl.SchoolAdministration.service.connector.HttpBinClient;
import htl.SchoolAdministration.service.exceptions.PersonAlreadyExistsException;
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
public class TeacherService implements DomainAttributeSupport {
    private final TeacherRepository repository;
   private final HttpBinClient httpBinClient;

    public List<Teacher> fetchTeachers(Optional<String> namePart) {
        log.info("starting to fetch data for TeacherService.fetchTeachers");

        return namePart.map(repository::findAllByLastNameContainsIgnoreCase)
                .orElseGet(repository::findAll);
    }

    @Transactional(readOnly = true)
    public List<Teacher> getTeachers() {
        return repository.findAll();
    }

   @Transactional(readOnly = true)
    public Optional<Teacher> getTeacher(Long id) {
        return repository.findById(id);
    }
@Transactional(readOnly = true)
    public List<Teacher> searchTeachers(String lastNamePart) {
        return repository.findAllByLastNameContainsIgnoreCase(lastNamePart);
    }
@Transactional
    public void deleteTeacher(String key) {
        repository.deleteByKey(key);
    }

    @Transactional
    public void deleteTeacher(Long id) {
        repository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public Optional<Teacher> getTeacherByKey(String key) {
        return repository.findByKey(key);
    }

    @Transactional
    public Teacher register(String username, String password, String firstName, String lastName, String birthday) {
        log.debug("Check if teacher '{}' exists in DB", username);
        var exists = repository.existsByUsername(username);

        if (exists) {
            log.warn("Teacher '{}' already exists -> throw Exception", username);
            throw PersonAlreadyExistsException.forExistingUsername(username);  // redirect macht der Controller
        }
        String key = httpBinClient.retrieveKey();
        Teacher teacher = Teacher.builder()
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .birthday(birthday)
                .key(key)
                .teachingSubject(Subject.NVS)
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

        repository.save(teacher);
        log.info("Teacher '{} (id={}, key={})' successfully saved", username, teacher.getId());

       // String token = "abc";   //TODO: generate token
       // log.info("Create confirmation token '{}' and linke it to student '{}'", token, username);

        //TODO send confirmation email
        log.debug("Send registration confirmation email for student '{}'", username);
        return teacher;
    }

    @Transactional // (readOnly = false)
    public Teacher replaceTeacher(Long id, String username, String password, String firstName, String lastName, String birthday) {
        // check username
        log.debug("Check if username is already defined");
        if (repository.existsByUsername((username))) {
            log.warn("Person with username '{}' already exists! Redirect to forget password page");
        }
        return repository.findById(id)
                .map(teacher -> {
                    //check username
                    teacher.setUsername(username);
                    teacher.setPassword(password);
                    teacher.setFirstName(firstName);
                    teacher.setLastName(lastName);
                    teacher.setBirthday(birthday);
                    return teacher;
                })
                // .map(repository::save)
                .orElseGet(() -> updateTeacher(username, password, lastName, firstName, birthday));
    }



    @Transactional
    public Teacher updateTeacher(String key, String password, String firstName, String lastName,
                                 String birthday) {
        return repository.findByKey(key)
                .map(teacher -> {
                    setAttributeIfNotBlank(key, teacher::setKey);
                    setAttributeIfNotBlank(password, teacher::setPassword);
                    setAttributeIfNotBlank(firstName, teacher::setFirstName);
                    setAttributeIfNotBlank(lastName, teacher::setLastName);
                    setAttributeIfNotBlank(birthday, teacher::setBirthday);
                    return teacher;
                })
                .orElseThrow(() -> new IllegalStateException("Person with key %s does not exist anymore!".formatted(key)));
    }






}
