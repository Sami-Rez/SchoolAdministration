package htl.SchoolAdministration.persistence.converter;

import htl.SchoolAdministration.domain.Gender;
import htl.SchoolAdministration.persistence.exception.DataQualityException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender gender) {
        return (gender == null) ? null : switch (gender) {
            case MALE -> "M";
            case FEMALE -> "F";
            case DIVERS -> "D";
        };
    }

    @Override
    public Gender convertToEntityAttribute(String dbValue) {
        return Optional.ofNullable(dbValue)
                .map(v -> switch (v) {
                    case "M" -> Gender.MALE;
                    case "F" -> Gender.FEMALE;
                    case "D" -> Gender.DIVERS;
                    //default -> throw new IllegalArgumentException("Unknown Value '%s' in Gender".formatted(dbValue));
                    default -> throw DataQualityException.forInvalidEnumDBValue(dbValue, Gender.class);
                }).orElse(null);
    }

}
