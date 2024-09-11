package htl.SchoolAdministration.tools;

import org.junit.jupiter.api.Test;

import static htl.SchoolAdministration.tools.Guard.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GuardTest {

    @Test
    void ensureNotNullWithNullThrowsException() {
        var ex = assertThrows(
                IllegalArgumentException.class, () -> ensureNotNull(null));
        assertThat(ex).hasMessageContaining("'argument' must not be null");
    }

    @Test
    void ensureNotNullWithNoNullArgumentReturnsArgument() {
        assertThat(ensureNotNull("A")).isEqualTo("A");
    }

    @Test
    void ensureNotNullWithNullAndNameThrowsException() {
        var ex = assertThrows(
                IllegalArgumentException.class, () -> ensureNotNull(null, "ABC"));
        assertThat(ex).hasMessageContaining("'ABC' must not be null");
    }

    @Test
    void ensurePredicateWorks() {
        assertThat(isNotNull.test(null)).isFalse();
        assertThat(isNotNull.test("A")).isTrue();
        assertThat(isNull.test(null)).isTrue();
        assertThat(isNull.test("A")).isFalse();
    }

}