package htl.SchoolAdministration.presentation;

import htl.SchoolAdministration.persistence.exception.DataQualityException;
import htl.SchoolAdministration.presentation.dtos.SchoolClassDto;
import htl.SchoolAdministration.service.SchoolClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j

@RestController
@RequestMapping("/api/school-classes")
public class SchoolClassRestController {

    private final SchoolClassService schoolClassService;
    @GetMapping
    public HttpEntity<List<SchoolClassDto>> fetchSchoolClasses(@RequestParam Optional<String> namePart) {
        return Optional.of(schoolClassService.fetchSchoolClasses(namePart))
                .filter(schoolClass -> !schoolClass.isEmpty())
                .map(schoolClass -> schoolClass.stream()
                        .map(SchoolClassDto::new).toList())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @ExceptionHandler(DataQualityException.class)
    public HttpEntity<Void> handleDataQualityException(DataQualityException dqEx) {
        log.warn("An DataQualityException occured because of: {}", dqEx.getMessage());
        return ResponseEntity.internalServerError().build();
    }

    /*    @GetMapping
    public List<SchoolClassDto> fetchSchoolClasses(@RequestParam Optional<String> namePart) {
        return schoolClassService.fetchSchoolClasses(namePart)
                .stream()
                .map(SchoolClassDto::new)
                .toList();
    }*/

}
