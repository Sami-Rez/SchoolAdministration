
package htl.SchoolAdministration.persistence;

import htl.SchoolAdministration.domain.*;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchoolClassRepositoryCustomImpl extends QuerydslRepositorySupport implements SchoolClassRepositoryCustom {

    private final EntityManager em;
    private final JdbcTemplate jdbcTemplate;
    private final JdbcClient jdbcClient;
    private QSchoolClass schoolClass = QSchoolClass.schoolClass;
    public SchoolClassRepositoryCustomImpl(EntityManager em, JdbcTemplate jdbcTemplate, JdbcClient jdbcClient) {
        super(SchoolClass.class);
        this.em = em;
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcClient = jdbcClient;
    }

   /* @Override*/
    public List<SchoolClass> complexQuery(School school) {
        return from(schoolClass)
                .where(schoolClass.school.eq(school)
                        .and(schoolClass.floor.eq(3))).fetch();
    }

    public List<SchoolClassProjections.Overview> overview(School school) {
        return from(schoolClass)
                .where(schoolClass.school.eq(school)
                        .and(schoolClass.floor.eq(3)))
                .select(new QSchoolClassProjections_Overview(schoolClass.schoolClassName, schoolClass.department))
                .fetch();
    }

}

