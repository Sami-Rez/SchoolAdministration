package htl.SchoolAdministration.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Embeddable
public class MobileNumber {

    public static final int LENGTH_AREACODE = 4;
    public static final int LENGTH_MOBILENUMBER = 16;

    private @Positive @NotNull Integer countryCode;

    @Column(length = LENGTH_AREACODE)
    private @NotBlank String areaCode;

    @Column(length = LENGTH_MOBILENUMBER)
    private @NotBlank String mobileNumber;

}
