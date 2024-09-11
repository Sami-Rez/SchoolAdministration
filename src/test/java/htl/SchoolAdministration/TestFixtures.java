package htl.SchoolAdministration;

import htl.SchoolAdministration.domain.*;

import java.util.Set;

public class TestFixtures {

    public static Country austria;
    public static School HTL;
    public static SchoolClass ABIF;
   /* public static Student student;
    public static Teacher teacher;

    Die Beiden hatte ich nicht implementiert. Bei der ProbeMatura bin ich darauf gekommen, dass
    sehr wahrscheinlich das Problem mit endlosen Schleifen, die Teacher, Student, School und SchoolClasses
    gegenseitig verursacht hatten, eigentlich da liegen könnte!
    ich sollte die vier als static deklarieren und dann beim jedem TestFixtures ohne () eingeben!!
    also sowas von TODO
                    */

    public static Country austria() {
        austria = Country.builder()
                .name("Austria")
                .iso2Code("AT")
                .topLevelDomain(".at")
                .build();
        return austria;
    }

    public static Address address(String strNumber, String zipCode, String city, Country country) {
        return Address.builder()
                .strNumber(strNumber)
                .zipCode(zipCode)
                .city(city)
                .country(austria())
                .build();
    }

    public static Address address(String strNumber, String zipCode) {
        return Address.builder()
                .strNumber(strNumber)
                .zipCode(zipCode)
                .city("Vienna")
                .country(austria())
                .build();
    }

    public static School HTL() {
        HTL = School.builder()
                .schoolName("HTL-Spengergasse")
                .homepage("www.spengergasse.at")
                .address(Address.builder()
                        .strNumber("spengergasse 20")
                        .zipCode("1050")
                        .city("Vienna")
                        .country(austria())
                        .build())
                .director("Director Name")
                .build();

        return HTL;
    }

    public static School school2() {
        return School.builder()
                .schoolName("HTL-Spengergasse2")
                .homepage("www.spengergasse2.at")
                .address(Address.builder()
                        .strNumber("spengergasse 200")
                        .zipCode("1050")
                        .city("Vienna")
                        .country(austria())
                        .build())
                .director("Director2 Name")
                .build();
    }

    public static School school3(String schoolName, String homepage) {
        return School.builder()
                .schoolName(schoolName)
                .homepage(homepage)
                .address(Address.builder()
                        .strNumber("spengergasse 200")
                        .zipCode("1050")
                        .city("Vienna")
                        .country(austria())
                        .build())
                .director("Director2 Name")
                .build();
    }

    public static SchoolClass ABIF() {
        ABIF = SchoolClass.builder()
                .schoolClassName("ABIF")
                .department(Department.MULTIMEDIA_DESIGN)
                .floor(3)
                .school(HTL())
                .build();
        return ABIF;
    }

    public static SchoolClass schoolClass2() {
        return SchoolClass.builder()
                .schoolClassName("2AAIF")
                .department(Department.INFORMATICS)
                .floor(3)
                .school(HTL())
                .build();
    }

    public static SchoolClass schoolClass3(String name) {
        return SchoolClass.builder()
                .schoolClassName(name)
                .department(Department.INFORMATICS)
                .floor(3)
                .school(School.builder()
                        .schoolName("HTL-Spengergasse2")
                        .homepage("www.spengergasse2.at")
                        .address(Address.builder()
                                .strNumber("spengergasse 200")
                                .zipCode("1050")
                                .city("Vienna")
                                .country(austria())
                                .build())
                        .director("Director2 Name")
                        .build())
                .build();
    }

    public static Student student(School school, SchoolClass schoolClass) {
        return Student.builder()
                .username("student@spg.at")
                .firstName("Stu")
                .lastName("Dent1")
                .password("!SequrityPassword?1011@")
                .birthday("10.10.1999")
                .gender(Gender.MALE)
                .religion(Religion.BUDDHISM)
                .address(Address.builder()
                        .strNumber("Schneeweg 10")
                        .zipCode("1220")
                        .city("Vienna")
                        .country(austria)
                        .build())
                .mobileNumber(MobileNumber.builder()
                        .countryCode(43)
                        .areaCode("01")
                        .mobileNumber("67801234")
                        .build())
                .school(HTL)
                .emailAddress(new EmailAddress("student@spg.at"))
                .studentClass(ABIF)
                .key("123456789")
                .build();
    }

    public static Student student2(School school, SchoolClass schoolClass) {
        return Student.builder()
                .username("student2@spg.at")
                .firstName("Stu")
                .lastName("Dent2")
                .password("!SequrityPassword?1011@")
                .birthday("10.10.2000")
                .gender(Gender.FEMALE)
                .religion(Religion.JUDAISM)
                .address(Address.builder()
                        .strNumber("KoenigStr 15")
                        .zipCode("1020")
                        .city("Vienna")
                        .country(austria())
                        .build())
                .mobileNumber(MobileNumber.builder()
                        .countryCode(43)
                        .areaCode("01")
                        .mobileNumber("6650123548")
                        .build())
                .school(school)
                .emailAddress(new EmailAddress("student2@spg.at"))
                .studentClass(schoolClass)
                .key("12345454")
                .build();
    }

    public static Student student3(String firstName, String lastName) {
        return Student.builder()
                //     .username(new EmailAddress("%s.%s@spg.at".formatted(lastName, firstName)))
                // .username("%s.%s@spg.at".formatted(lastName, firstName))
                .username("username")
                .firstName(firstName)
                .lastName(lastName)
                .password("!SequrityPassword?1011@")
                .birthday("10.10.2000")
                .gender(Gender.FEMALE)
                .religion(Religion.JUDAISM)
                .address(Address.builder()
                        .strNumber("KoenigStr 15")
                        .zipCode("1020")
                        .city("Vienna")
                        .country(austria)
                        .build())
                .mobileNumber(MobileNumber.builder()
                        .countryCode(43)
                        .areaCode("01")
                        .mobileNumber("6650123548")
                        .build())
                .school(HTL)
                .emailAddress(new EmailAddress("student3@spg.at"))
                .studentClass(ABIF)
                .key("123456789")
                .build();
    }

    public static Student student4(String username, String password, String firstName, String lastName) {
        return Student.builder()
                //    .username("%s.%s@spg.at".formatted(lastName, firstName))
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .birthday("10.10.2000")
                .gender(Gender.FEMALE)
                .religion(Religion.JUDAISM)
                .address(Address.builder()
                        .strNumber("KoenigStr 15")
                        .zipCode("1020")
                        .city("Vienna")
                        .country(austria)
                        .build())
                .mobileNumber(MobileNumber.builder()
                        .countryCode(43)
                        .areaCode("01")
                        .mobileNumber("6650123548")
                        .build())
                .school(HTL())
                .emailAddress(new EmailAddress("student4@spg.at"))
                .studentClass(schoolClass3("5AAIF"))
                .key("123456789")
                .build();
    }

    public static Teacher teacher(School school, Set<SchoolClass> schoolClasses) {
        return Teacher.builder()
                .username("teacher@spg.at")
                .firstName("Tea")
                .lastName("Cher")
                .password("!SequrityPassword?1011@")
                .birthday("01.01.1980")
                .address(Address.builder()
                        .strNumber("DresdnerStr 101")
                        .zipCode("1200")
                        .city("Vienna")
                        .country(austria)
                        .build())
                .mobileNumber(MobileNumber.builder()
                        .countryCode(43)
                        .areaCode("01")
                        .mobileNumber("6601203431")
                        .build())
                .teachingSubject(Subject.NVS)
                .gender(Gender.MALE)
                .religion(Religion.CHRISTIANITY)
                .school(school)
                .classes(schoolClasses)
                .key("123456789")
                .build();
    }

    public static Teacher teacher2() {
        return Teacher.builder()
                .username("teacher2@spg.at")
                .firstName("Tea2")
                .lastName("Cher2")
                .password("!SequrityPassword?1011@")
                .birthday("01.01.1980")
                .address(Address.builder()
                        .strNumber("DaumenStr 12")
                        .zipCode("1210")
                        .city("Vienna")
                        .country(austria)
                        .build())
                .mobileNumber(MobileNumber.builder()
                        .countryCode(43)
                        .areaCode("01")
                        .mobileNumber("6654644412")
                        .build())
                .teachingSubject(Subject.POS)
                .gender(Gender.FEMALE)
                .religion(Religion.BUDDHISM)
                .school(HTL)
                .classes(Set.of(ABIF()))
                .key("123456789")
                .build();
    }

    public static Teacher teacher3(String firstName, String lastName) {
        return Teacher.builder()
                //   .username(new EmailAddress("%s.%s@spg.at".formatted(lastName, firstName)))
                //   .username("%s.%s@spg.at".formatted(lastName, firstName))
                .username("Rezaei.Sam@spg.at")
                .firstName(firstName)
                .lastName(lastName)
                .password("!SequrityPassword?1011@")
                .birthday("01.01.1980")
                .address(Address.builder()
                        .strNumber("DaumenStr 12")
                        .zipCode("1210")
                        .city("Vienna")
                        .country(austria)
                        .build())
                .mobileNumber(MobileNumber.builder()
                        .countryCode(43)
                        .areaCode("01")
                        .mobileNumber("6654644412")
                        .build())
                .teachingSubject(Subject.POS)
                .gender(Gender.FEMALE)
                .religion(Religion.BUDDHISM)
                .school(HTL)
                .classes(Set.of(ABIF))
                .key("123456789")
                .build();
    }

    public static MobileNumber phoneNumber(int countryCode, String areaCode, String mobileNumber) {
        return MobileNumber.builder()
                .countryCode(countryCode)
                .areaCode(areaCode)
                .mobileNumber(mobileNumber)
                .build();
    }

    public static MobileNumber phoneNumber(String mobileNumber) {
        return phoneNumber(43, "1", mobileNumber);
    }
}


