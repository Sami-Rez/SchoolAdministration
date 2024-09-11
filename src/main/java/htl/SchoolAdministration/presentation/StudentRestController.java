package htl.SchoolAdministration.presentation;

import htl.SchoolAdministration.domain.Student;
import htl.SchoolAdministration.presentation.dtos.StudentDto;
import htl.SchoolAdministration.service.exceptions.PersonAlreadyExistsException;
import htl.SchoolAdministration.service.student.CreateStudentCommand;
import htl.SchoolAdministration.service.student.ReplaceStudentCommand;
import htl.SchoolAdministration.service.student.StudentService;
import htl.SchoolAdministration.service.student.UpdateStudentCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Slf4j

@RestController
@RequestMapping(StudentRestController.BASE_URL)
public class StudentRestController {
    public static final String BASE_URL = "/api/students";
    private static final String _SlASH = "/";
    private static final String PATHVAR_ID = "{id}";
    public static final String PATH_ID = _SlASH + PATHVAR_ID;
    public static final String ROUTE_ID = BASE_URL + PATH_ID;

    private final StudentService studentService;


    @GetMapping
    public ResponseEntity<List<StudentDto>> getStudents(@RequestParam Optional<String> lastNamePart) {
        List<Student> students = lastNamePart.map(studentService::searchStudents)
                .orElseGet(studentService::getStudents);
        return (students.isEmpty())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(students.stream().map(StudentDto::new).toList());
    }

    @GetMapping(PATH_ID)
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id) {   //@Valid Long id
        return studentService.getStudent(id)
                .map(StudentDto::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(PATH_ID)
    public ResponseEntity<StudentDto> createStudent(@RequestBody @Valid CreateStudentCommand cmd, @PathVariable String id) {
        Student student = studentService.register(
                cmd.username(),
                cmd.password(),
                cmd.firstName(),
                cmd.lastName(),
                cmd.birthday());
        Link location = linkTo(methodOn(StudentRestController.class).getStudent(student.getId())).withSelfRel();  //student.getKey
        return ResponseEntity.created(location.toUri()).body(new StudentDto(student));
    }

    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyExistsException(PersonAlreadyExistsException uaeEx) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, uaeEx.getMessage());
        problemDetail.setTitle("UserRegistration");
        problemDetail.setProperty("username", uaeEx.getUsername());
        return ResponseEntity.status(status).body(problemDetail);
    }

       @PatchMapping(PATH_ID)
    public ResponseEntity<StudentDto> updateStudent(String key, @RequestBody UpdateStudentCommand cmd) throws Exception {
        Student student = studentService.updateStudent(
                cmd.key(),
               // cmd.username(),
                cmd.password(),
                cmd.lastName(),
                cmd.firstName(),
                cmd.birthday());
        String location = new URI("%s/%d".formatted(StudentRestController.BASE_URL, student.getId())).toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, location)
                .body(new StudentDto(student));
    }

    @PutMapping(PATH_ID) //idempotent
    public ResponseEntity<StudentDto> replaceStudent(@PathVariable Long id, @RequestBody ReplaceStudentCommand cmd) throws Exception {

        Student student = studentService.replaceStudent(id,
                cmd.username(),
                cmd.password(),
                cmd.firstName(),
                cmd.lastName(),
                cmd.birthday());
        String location = new URI("%s/%d".formatted(StudentRestController.BASE_URL, student.getId())).toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, location)
                .body(new StudentDto(student));
    }

    @DeleteMapping(PATH_ID) //idempotent
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return null;
    }

  /*      @GetMapping
    public List<StudentDto> fetchStudents(@RequestParam Optional<String> namePart) {
        return studentService.fetchStudents(namePart)
                .stream()
                .map(StudentDto::new)
                .toList();
    }*/

}



