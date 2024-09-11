package htl.SchoolAdministration.tools;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface StrongPassword {
    String message() default "Must be 12 characters and combination of uppercase letters,lowercase letters, numbers and special characters";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
