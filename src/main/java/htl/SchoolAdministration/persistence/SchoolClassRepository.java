package htl.SchoolAdministration.persistence;

import htl.SchoolAdministration.domain.School;
import htl.SchoolAdministration.domain.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long>, SchoolClassRepositoryCustom {

    // alle Klassen, die den Teil in ihrem Namen beinhalten werden zurückgeliefert.
    List<SchoolClass> findAllBySchoolClassNameContainsIgnoreCase(String schoolClassName);

    Long countBySchool(School school);
}
