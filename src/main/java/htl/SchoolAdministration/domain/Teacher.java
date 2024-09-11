package htl.SchoolAdministration.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"school", "classes"})
@Table(name = "teacher")
//@Builder


public class Teacher extends Person {
    public static final int LENGTH_EMAI_ADDRESS = 64;
    public static final int LENGTH_USERNAME = 64;
    private static final int LENGTH_KEY = 40;


    @Embedded
    private @Valid @NotNull Address address;

    //Es soll möglich sein, dass ein Teacher mehr als ein MobileNumber besitzt.
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "countryCode", column = @Column(name = "mobile_country_code", nullable = false)),
            @AttributeOverride(name = "areaCode", column = @Column(name = "mobile_area_code", nullable = false)),
            @AttributeOverride(name = "mobileNumber", column = @Column(name = "mobile_number",
                    length = MobileNumber.LENGTH_MOBILENUMBER, nullable = false))
    })

    private @NotNull @Valid MobileNumber mobileNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "countryCode", column = @Column(name = "school_mobile_country_code")),
            @AttributeOverride(name = "areaCode", column = @Column(name = "school_mobile_area_code")),
            @AttributeOverride(name = "mobileNumber", column = @Column(name = "school_mobile_number",
                    length = MobileNumber.LENGTH_MOBILENUMBER))
    })
    private @Valid MobileNumber schoolMobileNumber;

    @Enumerated(EnumType.STRING)
    private @NotNull Subject teachingSubject;

    @Enumerated(EnumType.STRING)
    private @NotNull Gender gender;

    @Enumerated(EnumType.STRING)
    private @NotNull Religion religion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "teacher_classes",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "school_class_id"))
    private List<SchoolClass> classes;

    @Column(name = "teacher_key", nullable = false, length = LENGTH_KEY)
    private @NotBlank String key;


    // Set von Außen nicht erfuellbar
    @Setter(AccessLevel.PRIVATE)
    @ElementCollection
    @CollectionTable(name = "teacher_emails", joinColumns = @JoinColumn( foreignKey = @ForeignKey(name = "FK_teacher_email_2_teachers")))
    private Set<EmailAddress> emailAddresses;

    @Builder
    public Teacher(String username, String firstName, String lastName, String password,
                   String birthday, Address address, MobileNumber mobileNumber,
                   Subject teachingSubject, Gender gender, Religion religion,Set<EmailAddress> emailAddresses, Set<SchoolClass> classes, School school, String key) {
        super(username, firstName, lastName, password, birthday);
        this.address = address;
        this.mobileNumber = (mobileNumber);
        this.teachingSubject = teachingSubject;
        this.gender = gender;
        this.religion = religion;
        this.emailAddresses = (emailAddresses != null) ? new HashSet<>(emailAddresses) : new HashSet<>(3);
        this.classes = (classes != null) ? new ArrayList<>(classes) : new ArrayList<>();
        this.school = school;
        this.key = key;
    }

    public Teacher addEmails(EmailAddress... emailAddresses) {
        Collections.addAll(this.emailAddresses, emailAddresses);
        return this;
    }

    public Set<EmailAddress> getEmails() {
        return Collections.unmodifiableSet(emailAddresses);
    }

    public Teacher clearMails() {
        emailAddresses.clear();
        return this;
    }

    public Teacher removeEmail(EmailAddress emailAddress) {
        emailAddresses.remove(emailAddress);
        return this;
    }

    public Teacher addClasses(SchoolClass schoolClass) {
        classes.add(schoolClass);
        return this;
    }

    public Teacher clearClasses() {
        classes.clear();
        return this;
    }

    public Teacher removeClass(SchoolClass schoolClass) {
        classes.remove(schoolClass);
        return this;
    }

}
