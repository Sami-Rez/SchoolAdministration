package htl.SchoolAdministration.presentation;

import htl.SchoolAdministration.domain.Teacher;
import htl.SchoolAdministration.presentation.dtos.TeacherDto;
import htl.SchoolAdministration.service.exceptions.PersonAlreadyExistsException;
import htl.SchoolAdministration.service.teacher.CreateTeacherCommand;
import htl.SchoolAdministration.service.teacher.ReplaceTeacherCommand;
import htl.SchoolAdministration.service.teacher.TeacherService;
import htl.SchoolAdministration.service.teacher.UpdateTeacherCommand;
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
@RequestMapping(TeacherRestController.BASE_URL)
public class TeacherRestController {

    public static final String BASE_URL = "/api/teachers";
    private static final String _SLASH = "/";
    private static final String PATHVAR_ID = "{id}";
    public static final String PATH_ID = _SLASH + PATHVAR_ID;
    public static final String ROUTE_ID = BASE_URL + PATH_ID;

    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<TeacherDto>> getTeachers(@RequestParam Optional<String> lastNamePart) {
        List<Teacher> teachers = lastNamePart.map(teacherService::searchTeachers)
                .orElseGet(teacherService::getTeachers);
        return (teachers.isEmpty())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(teachers.stream().map(TeacherDto::new).toList());
    }

    @GetMapping(PATH_ID)
    public ResponseEntity<TeacherDto> getTeacher(@PathVariable @Valid Long id) {
        return teacherService.getTeacher(id)
                .map(TeacherDto::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(PATH_ID)
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody @Valid CreateTeacherCommand cmd) {
        Teacher teacher = teacherService.register(
                cmd.username(),
                cmd.password(),
                cmd.firstName(),
                cmd.lastName(),
                cmd.birthday());
        Link location = linkTo(methodOn(TeacherRestController.class).getTeacher(teacher.getId())).withSelfRel();
        return ResponseEntity.created(location.toUri()).body(new TeacherDto(teacher));
    }

/*    @PostMapping
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody @Valid CreateTeacherCommand cmd) throws URISyntaxException {
        Teacher teacher = teacherService.register(
                cmd.username(),
                cmd.password(),
                cmd.lastName(),
                cmd.firstName(),
                cmd.birthday());
        return ResponseEntity.created(new URI("%s/%d".formatted(TeacherRestController.BASE_URL, teacher.getId())))
                .body(new TeacherDto(teacher));
    }*/

    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyExistsException(PersonAlreadyExistsException uaeEx) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, uaeEx.getMessage());
        problemDetail.setTitle("UserRegistration");
        problemDetail.setProperty("username", uaeEx.getUsername());
        return ResponseEntity.status(status).body(problemDetail);
    }

    @PutMapping(PATH_ID) //idempotent
    public ResponseEntity<TeacherDto> replaceTeacher(@PathVariable Long id, @RequestBody ReplaceTeacherCommand cmd) throws Exception {
        Teacher teacher = teacherService.replaceTeacher(id,
                cmd.username(),
                cmd.password(),
                cmd.firstName(),
                cmd.lastName(),
                cmd.birthday());
        String location = new URI("%s/%d".formatted(TeacherRestController.BASE_URL, teacher.getId())).toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, location)
                .body(new TeacherDto(teacher));
    }

    @PatchMapping(PATH_ID)
    public ResponseEntity<TeacherDto> updateTeacher(String key, @RequestBody UpdateTeacherCommand cmd) throws Exception {
        Teacher teacher = teacherService.updateTeacher(
                cmd.key(),
                cmd.username(),
                cmd.password(),
                cmd.lastName(),
                cmd.firstName());
        String location = new URI("%s/%d".formatted(TeacherRestController.BASE_URL, teacher.getId())).toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, location)
                .body(new TeacherDto(teacher));
    }


/*   @GetMapping
    public List<TeacherDto> fetchTeachers(@RequestParam Optional<String> namePart) {
        return teacherService.fetchTeachers(namePart)
                .stream()
                .map(TeacherDto::new)
                .toList();
    }*/

    @DeleteMapping(PATH_ID) //idempotent
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return null;
    }

}
