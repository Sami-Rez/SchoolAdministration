package htl.SchoolAdministration.service;

import htl.SchoolAdministration.domain.SchoolClass;
import htl.SchoolAdministration.persistence.SchoolClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
@RequiredArgsConstructor
@Service
public class SchoolClassService {
    private final SchoolClassRepository repository;

    //liefert alle wenn keine Argumenten vorhanden sind. Und wenn doch welche Argumente
    // gegeben sind, liefert alle entsprechenden Daten.
    public List<SchoolClass> fetchSchoolClasses(Optional<String> namePart) {
        return namePart.map(repository::findAllBySchoolClassNameContainsIgnoreCase)
                .orElseGet(repository::findAll);
    }

}

