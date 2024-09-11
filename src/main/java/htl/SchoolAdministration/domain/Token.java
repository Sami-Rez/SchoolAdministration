package htl.SchoolAdministration.domain;/*
package at.spengergasse.sj2324seedproject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tokens")
public class Token extends AbstractPersistable<Long> {
    @Column(name = "token_value", nullable = false)
    private @NotBlank String value;

    @Column(name = "token_type")
    private @NotBlank TokenType type;

    private String targetClass;

    private LocalDateTime expirationTS;
}
*/
