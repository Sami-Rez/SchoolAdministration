package htl.SchoolAdministration.presentation.dtos;

import htl.SchoolAdministration.domain.SchoolClass;

public record SchoolClassDto(String className, Integer floor) {

    public SchoolClassDto(SchoolClass schoolClass) {
        this(schoolClass.getSchoolClassName(), schoolClass.getFloor());
    }

}
