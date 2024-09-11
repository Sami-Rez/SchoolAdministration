package htl.SchoolAdministration.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"school", "studentClass"})
@Getter
@Setter
@Entity
@Table(name = "students")
public class Student extends Person {

    private static final int LENGTH_KEY = 40;

  /*  @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_student_2_school"))
    @JsonManagedReference*/

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @Embedded
    private @Valid
    @NotNull Address address;

    @Embedded
    private @Valid
    @NotNull MobileNumber mobileNumber;

    @Enumerated(EnumType.STRING)
    private @NotNull Gender gender;

    @Enumerated(EnumType.STRING)
    private @NotNull Religion religion;

      @ManyToOne(optional = true)
      @JoinColumn(foreignKey = @ForeignKey(name = "FK_student_2_class"))
    private SchoolClass studentClass;

    @Column(name = "student_key", nullable = false, length = LENGTH_KEY)
    private @NotBlank String key;

    @ElementCollection
    @CollectionTable(name = "student_emails", joinColumns = @JoinColumn(foreignKey = @ForeignKey(name = "FK_student_emails_2_student")))
    private Set<EmailAddress> emailAddresses;

    @Builder
    public Student(String username, String firstName, String lastName, String password,
                   String birthday, Gender gender, Religion religion, Address address,
                   MobileNumber mobileNumber, EmailAddress emailAddress, School school, SchoolClass studentClass, String key) {
        super(username, firstName, lastName, password, birthday);
        this.gender = gender;
        this.religion = religion;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.emailAddresses = (emailAddresses != null) ? new HashSet<>(emailAddresses) : new HashSet<>(3);
        this.school = school;
        this.studentClass = studentClass;
        this.key = key;
    }

    public Student addEmails(EmailAddress... emailAddresses) {
        Collections.addAll(this.emailAddresses, emailAddresses);
        return this;
    }

    public Set<EmailAddress> getEmails() {
        return Collections.unmodifiableSet(emailAddresses);
    }

    public Student clearMails() {
        emailAddresses.clear();
        return this;
    }

    public Student removeEmail(EmailAddress emailAddress) {
        emailAddresses.remove(emailAddress);
        return this;
    }

}

