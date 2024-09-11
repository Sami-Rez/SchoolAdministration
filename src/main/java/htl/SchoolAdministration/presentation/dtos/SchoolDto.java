package htl.SchoolAdministration.presentation.dtos;

import htl.SchoolAdministration.domain.School;

public record SchoolDto(String name, String homepage) {
    public SchoolDto(School school) {
        this(school.getSchoolName(), school.getHomepage());
    }
}
