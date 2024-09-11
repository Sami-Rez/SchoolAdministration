package htl.SchoolAdministration.service;

import htl.SchoolAdministration.TestFixtures;
import htl.SchoolAdministration.persistence.SchoolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchoolServiceTest {
    private SchoolService schoolService;

    private @Mock SchoolRepository schoolRepository;

    @BeforeEach
    void setup() {
        assertThat(schoolRepository).isNotNull();
        schoolService = new SchoolService(schoolRepository);
    }

    @Test
    void ensureFetchSchoolWithNoArgumentCallFindAll() {
        //given
        Optional<String> searchCriteria = Optional.empty();
        var school = TestFixtures.HTL();
        when(schoolRepository.findAll()).thenReturn(List.of(school));

        //when
        var result = schoolService.fetchSchools(searchCriteria);

        //then
        assertThat(result).containsExactly(school);
        verify(schoolRepository).findAll();
        verifyNoMoreInteractions(schoolRepository);
    }

    @Test
    void ensureFetchSchoolWithValidArgumentCallfindAllBySchoolNameContainsIgnoreCase() {
        //given
        Optional<String> searchCriteria = Optional.of("Test");
        var school = TestFixtures.HTL();
        when(schoolRepository.findAllBySchoolNameContainsIgnoreCase(any())).thenReturn(List.of(school));

        //when
        var result = schoolService.fetchSchools(searchCriteria);

        //then
        assertThat(result).containsExactly(school);
        verify(schoolRepository).findAllBySchoolNameContainsIgnoreCase(any());
        verifyNoMoreInteractions(schoolRepository);
    }
}


