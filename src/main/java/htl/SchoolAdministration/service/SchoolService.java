package htl.SchoolAdministration.service;

import htl.SchoolAdministration.domain.School;
import htl.SchoolAdministration.persistence.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2

@Service
@Transactional
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<School> fetchSchools(Optional<String> namePart) {
        return namePart.map(schoolRepository::findAllBySchoolNameContainsIgnoreCase)
                .orElseGet(schoolRepository::findAll);
    }
}
