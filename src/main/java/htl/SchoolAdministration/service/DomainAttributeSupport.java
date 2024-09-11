package htl.SchoolAdministration.service;

import java.util.function.Consumer;

public interface DomainAttributeSupport {
    default void setAttributeIfNotBlank(String value, Consumer<String> setter) {
        if (value != null &&!value.isBlank()) setter.accept(value);
    }
}
