package htl.SchoolAdministration.domain;

import com.querydsl.core.annotations.QueryProjection;

public class SchoolClassProjections {
    public record Overview(String schoolClassName, Department department) {
        @QueryProjection
        public Overview{
            //absichtlich leer gelassen.
        }
    }
}
