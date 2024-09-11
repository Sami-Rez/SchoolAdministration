package htl.SchoolAdministration.persistence.converter;

import htl.SchoolAdministration.domain.Subject;
import htl.SchoolAdministration.persistence.exception.DataQualityException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class SubjectConverter implements AttributeConverter<Subject, String> {

    // Switch expression! Liefert einen Rueckgabewert
    @Override
    public String convertToDatabaseColumn(Subject subject) {
        return (subject == null) ? null : switch (subject) {
            case AM -> "Angewandte Mathematik";
            case DE -> "Deutsch";
            case E -> "Englisch";
            case WIR -> "Wirtschaft und Recht";
            case PRE -> "Projektentwicklung und Management";
            case NVS -> "Netzwerktechnik und verteilte Systeme";
            case POS -> "Programmieren";
            case BWM -> "Betriebswirtschaft und Management";
            case DBI -> "Datenbanksysteme";
            case AINF -> "Angewandte Informatik";
            case COPR -> "Computer Praktikum";
        };
    }

    @Override
    public Subject convertToEntityAttribute(String dbValue) {
        return Optional.ofNullable(dbValue)
                .map(v -> switch (v) {
                    case "Angewandte Mathematik" -> Subject.AM;
                    case "Deutsch" -> Subject.DE;
                    case "Englisch" -> Subject.E;
                    case "Wirtschaft und Recht" -> Subject.WIR;
                    case "Projektentwicklung und Management" -> Subject.PRE;
                    case "Netzwerktechnik und verteilte Systeme" -> Subject.NVS;
                    case "Programmieren" -> Subject.POS;
                    case "Betriebswirtschaft und Management" -> Subject.BWM;
                    case "Datenbanksysteme" -> Subject.DBI;
                    case "Angewandte Informatik" -> Subject.AINF;
                    case "Computer Praktikum" -> Subject.COPR;
                    //default -> throw new IllegalArgumentException("Unknown Value '%s' in Subject".formatted(dbValue));
                    default -> throw DataQualityException.forInvalidEnumDBValue(dbValue, Subject.class);
                }).orElse(null);
    }
}

