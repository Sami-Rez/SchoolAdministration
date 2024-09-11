package htl.SchoolAdministration.domain;

import com.querydsl.core.annotations.QueryProjection;

public class StudentProjections {
    public record Overview(String lastName, SchoolClass studentClass) {


        @QueryProjection
        public Overview {
            //absichtlich leer gelassen.
        }
    }

    private Overview overview(String lastName, SchoolClass schoolClass) {
        return new Overview(lastName, schoolClass);
    }
}
