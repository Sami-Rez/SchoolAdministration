package htl.SchoolAdministration.persistence;

import htl.SchoolAdministration.domain.Student;
import htl.SchoolAdministration.domain.StudentProjections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom {
    List<Student> findAllByLastNameContainsIgnoreCase(String lastName);
    boolean existsByUsername(String username);

  //  Optional<Student> findByUsername(EmailAddress username);

    Optional<Student> findByKey(String key);

    List<Student> findAllByLastNameLikeIgnoreCase(String lastNamePart);

    List<StudentProjections.Overview> findAnyByLastNameLikeIgnoreCase(String lastName);

    void deleteByKey(String key);

}
