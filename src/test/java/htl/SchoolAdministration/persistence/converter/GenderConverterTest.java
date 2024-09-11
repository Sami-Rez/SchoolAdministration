package htl.SchoolAdministration.persistence.converter;

import htl.SchoolAdministration.domain.Gender;
import htl.SchoolAdministration.persistence.exception.DataQualityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GenderConverterTest {
    private GenderConverter converter;

    @BeforeEach
    void init() {
        converter = new GenderConverter();
    }

    @Test
        // Konvertierung in die eine Richtung testen
    void ensureConvertToDatabaseColumnWorks() {
        //expect
        assertThat(converter.convertToDatabaseColumn(Gender.MALE)).isEqualTo("M");
        assertThat(converter.convertToDatabaseColumn(Gender.FEMALE)).isEqualTo("F");
        assertThat(converter.convertToDatabaseColumn(Gender.DIVERS)).isEqualTo("D");
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
    }

    // Konvertierung in die andere Richtung testen
    @Test
    void ensureConvertToEntityAttributeWorksForValidValues() {
        assertThat(converter.convertToEntityAttribute("M")).isEqualTo(Gender.MALE);
        assertThat(converter.convertToEntityAttribute("F")).isEqualTo(Gender.FEMALE);
        assertThat(converter.convertToEntityAttribute("D")).isEqualTo(Gender.DIVERS);
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }

    // Sonderfall Exception prüfen
    @Test
    void ensureConvertToEntityAttributeThrowsAnExceptionForInvalidValues() {
        var iaEx = Assertions.assertThrows(DataQualityException.class, () -> {
            converter.convertToEntityAttribute("X");
        });
        assertThat(iaEx).hasMessage("Unknown dbValue of 'X' for enumType 'Gender' ");
    }

}