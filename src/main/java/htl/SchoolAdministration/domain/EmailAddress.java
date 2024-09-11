package htl.SchoolAdministration.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/*public record EmailAddress( @Column(name = "email_address")
        @Email String valueForEmail ) {

    public static EmailAddress of(String address) {
        return new EmailAddress(address);
    }

    public String toString() {
        return valueForEmail;
    }
}*/


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailAddress {
    private String valueForEmail;

    public static EmailAddress of(String address) {
        return new EmailAddress(address);
    }

    public String toString() {
        return valueForEmail;
    }
}
