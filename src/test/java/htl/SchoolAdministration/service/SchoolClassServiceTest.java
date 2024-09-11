package htl.SchoolAdministration.service;

import htl.SchoolAdministration.TestFixtures;
import htl.SchoolAdministration.persistence.SchoolClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchoolClassServiceTest {
    private SchoolClassService schoolClassService;

    private @Mock SchoolClassRepository schoolClassRepository;

    @BeforeEach
    void setup() {
        assumeThat(schoolClassRepository).isNotNull();
        schoolClassService = new SchoolClassService(schoolClassRepository);
    }

    @Test
    void ensureFetchSchoolClassWithNoArgumentCallFindAll() {
        //given
        Optional<String> searchCriteria = Optional.empty();
        var schoolClass = TestFixtures.ABIF();
        when(schoolClassRepository.findAll()).thenReturn(List.of(schoolClass));

        //when
        var result = schoolClassService.fetchSchoolClasses(searchCriteria);

        //then
        assertThat(result).containsExactly(schoolClass);
        verify(schoolClassRepository).findAll();
        verifyNoMoreInteractions(schoolClassRepository);
    }

    @Test
    void ensureFetchSchoolClassWithValidArgumentCallfindAllBySchoolClassNameContainsIgnoreCase() {
        //given
        Optional<String> searchCriteria = Optional.of("Test");
        var schoolClass = TestFixtures.ABIF();
        when(schoolClassRepository.findAllBySchoolClassNameContainsIgnoreCase(any())).thenReturn(List.of(schoolClass));

        //when
        var result = schoolClassService.fetchSchoolClasses(searchCriteria);

        //then
        assertThat(result).containsExactly(schoolClass);
        verify(schoolClassRepository).findAllBySchoolClassNameContainsIgnoreCase(any());
        verifyNoMoreInteractions(schoolClassRepository);
    }
}
