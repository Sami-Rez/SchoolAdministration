package htl.SchoolAdministration.persistence.converter;

import htl.SchoolAdministration.domain.Subject;
import htl.SchoolAdministration.persistence.exception.DataQualityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SubjectConverterTest {
    private SubjectConverter converter = new SubjectConverter();

    @BeforeEach
    void setup() {
        converter = new SubjectConverter();
    }

    @Test
    void ensureConvertToDatabaseColumnWorks() {
        //expect
        assertThat(converter.convertToDatabaseColumn(Subject.AM)).isEqualTo("Angewandte Mathematik");
        assertThat(converter.convertToDatabaseColumn(Subject.E)).isEqualTo("Englisch");
        assertThat(converter.convertToDatabaseColumn(Subject.DE)).isEqualTo("Deutsch");
        assertThat(converter.convertToDatabaseColumn(Subject.WIR)).isEqualTo("Wirtschaft und Recht");
        assertThat(converter.convertToDatabaseColumn(Subject.PRE)).isEqualTo("Projektentwicklung und Management");
        assertThat(converter.convertToDatabaseColumn(Subject.NVS)).isEqualTo("Netzwerktechnik und verteilte Systeme");
        assertThat(converter.convertToDatabaseColumn(Subject.POS)).isEqualTo("Programmieren");
        assertThat(converter.convertToDatabaseColumn(Subject.BWM)).isEqualTo("Betriebswirtschaft und Management");
        assertThat(converter.convertToDatabaseColumn(Subject.DBI)).isEqualTo("Datenbanksysteme");
        assertThat(converter.convertToDatabaseColumn(Subject.AINF)).isEqualTo("Angewandte Informatik");
        assertThat(converter.convertToDatabaseColumn(Subject.COPR)).isEqualTo("Computer Praktikum");
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    void ensureConvertToEntityAttributeWorks() {
        assertThat(converter.convertToEntityAttribute("Angewandte Mathematik")).isEqualTo(Subject.AM);
        assertThat(converter.convertToEntityAttribute("Englisch")).isEqualTo(Subject.E);
        assertThat(converter.convertToEntityAttribute("Deutsch")).isEqualTo(Subject.DE);
        assertThat(converter.convertToEntityAttribute("Wirtschaft und Recht")).isEqualTo(Subject.WIR);
        assertThat(converter.convertToEntityAttribute("Projektentwicklung und Management")).isEqualTo(Subject.PRE);
        assertThat(converter.convertToEntityAttribute("Netzwerktechnik und verteilte Systeme")).isEqualTo(Subject.NVS);
        assertThat(converter.convertToEntityAttribute("Programmieren")).isEqualTo(Subject.POS);
        assertThat(converter.convertToEntityAttribute("Betriebswirtschaft und Management")).isEqualTo(Subject.BWM);
        assertThat(converter.convertToEntityAttribute("Datenbanksysteme")).isEqualTo(Subject.DBI);
        assertThat(converter.convertToEntityAttribute("Angewandte Informatik")).isEqualTo(Subject.AINF);
        assertThat(converter.convertToEntityAttribute("Computer Praktikum")).isEqualTo(Subject.COPR);
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }

    @Test
    void ensureConvertToEntityAttributeThrowsAnExceptionForInvalidValues() {
        var iaEx = Assertions.assertThrows(DataQualityException.class, () -> {
            converter.convertToEntityAttribute("XOS");
        });
        assertThat(iaEx).hasMessage("Unknown dbValue of 'XOS' for enumType 'Subject' ");
    }
}


