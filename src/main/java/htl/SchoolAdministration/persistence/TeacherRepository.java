package htl.SchoolAdministration.persistence;

import htl.SchoolAdministration.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findAllByLastNameContainsIgnoreCase(String lastName);

    boolean existsByUsername( String username);

    Optional<Teacher> findByKey(String key);

    List<Teacher> findAllByLastNameLikeIgnoreCase(String lastNamePart);

    void deleteByKey(String key);
}
