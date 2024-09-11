package htl.SchoolAdministration.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
@ToString(exclude = "country")

public class Address {

    public static final int LENGTH_STRNUMBER = 64;
    public static final int LENGTH_ZIPCODE = 16;
    public static final int LENGTH_CITY = 64;

    @Column(length = LENGTH_STRNUMBER, nullable = false)
    private @NotBlank String strNumber;

    @Column(length = LENGTH_ZIPCODE, nullable = false)
    private @NotBlank String zipCode;

    @Column(length = LENGTH_CITY, nullable = false)
    private @NotBlank String city;

    //Viele Adresse können zu einem Land gehören:
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
     @JoinColumn(foreignKey = @ForeignKey(name = "FK_address_2_countries"))
    private Country country;


}
