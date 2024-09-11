package htl.SchoolAdministration.persistence.converter;

import htl.SchoolAdministration.domain.EmailAddress;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class EmailConverter implements AttributeConverter<EmailAddress, String> {

    @Override
    public String convertToDatabaseColumn(EmailAddress emailAddress) {
        return Optional.ofNullable(emailAddress).map(EmailAddress::getValueForEmail).orElse(null);
    }

    @Override
    public EmailAddress convertToEntityAttribute(String dbValue) {
        return Optional.ofNullable(dbValue).map(EmailAddress::new).orElse(null);
    }

}
