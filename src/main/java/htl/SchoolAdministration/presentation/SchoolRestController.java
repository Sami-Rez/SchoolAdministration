package htl.SchoolAdministration.presentation;

import htl.SchoolAdministration.persistence.exception.DataQualityException;
import htl.SchoolAdministration.presentation.dtos.SchoolDto;
import htl.SchoolAdministration.service.SchoolService;
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
@RequestMapping("/api/schools")
public class SchoolRestController {

    private final SchoolService schoolService;

    @GetMapping
    public HttpEntity<List<SchoolDto>> fetchSchools(@RequestParam Optional<String> namePart) {
        return Optional.of(schoolService.fetchSchools(namePart))
                .filter(schools -> !schools.isEmpty())
                .map(schools -> schools.stream().map(SchoolDto::new).toList())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @ExceptionHandler(DataQualityException.class)
    public HttpEntity<Void> handleDataQualityException(DataQualityException dqEx) {
        log.warn("An DataQualityException occured because of: {}", dqEx.getMessage());
        return ResponseEntity.internalServerError().build();
    }

    /*    @GetMapping
    public List<SchoolDto> fetchSchools(@RequestParam Optional<String> namePart) {
        return schoolService.fetchSchools(namePart)
                .stream()
                .map(SchoolDto::new)
                .toList();
    }*/

}
