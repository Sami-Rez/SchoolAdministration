package htl.SchoolAdministration.tools;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class StrongPasswordValidatorTest {

    private StrongPasswordValidator validator = new StrongPasswordValidator();
    private @Mock ConstraintValidatorContext ctx;


    @ParameterizedTest
    @ValueSource(strings = {
            "@Sample5578dortNicht_23PASSWORD",
            "_SAMPLE8610ULTRAgeheimerPassword@",
            "!samplEngineerPassword1011~MeinPASSWORD",

    })
    void ensureStrongPasswordIsValidateAsValid(String password) {
        assertThat(validator.isValid(password, ctx)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "aaaaaaaa",  //only 8 lowercase letters
            "ABCDEFGHIJK", // only 11 uppercase letters
            "dasiskeinguterpassword", // only lowercase letters
            "12345678987654321", //only numbers
            "passworD1234", // without special characters
            "PASWORD1234", // without special characters
            "Password@_#password" // without numbers
    })
    void ensureNotStrongPasswordsAreValidatedAsNotValid(String password) {
        assertThat(validator.isValid(password, ctx)).isFalse();

    }



}

