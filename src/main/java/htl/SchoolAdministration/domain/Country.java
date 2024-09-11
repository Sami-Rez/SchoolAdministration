package htl.SchoolAdministration.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "countries")


public class Country extends AbstractPersistable<Long> {
    @Column(name = "country name", length = 64)
    private @NotBlank String name;

    @Column(length = 2)
    private @NotBlank String iso2Code;

    @Column(length = 4)
    private @NotBlank String topLevelDomain;

    private @Positive @Min(1) @Max(9999) Integer landCode;


}
