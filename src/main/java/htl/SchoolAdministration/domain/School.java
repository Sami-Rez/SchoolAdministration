package htl.SchoolAdministration.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;

//@Data
@NoArgsConstructor

@Getter
@Setter
@Entity
@ToString(exclude = {"classes", "students", "teachers"})

@Table(name = "school")
public class School extends AbstractPersistable<Long> {

    public static final int LENGTH_DIRECTOR = 96;

    @Column(name = "school_name")
    private @NotNull String schoolName;

    @Column(name = "homepage")
    private @NotNull String homepage;

    @Column(name = "address")
    private @NotNull Address address;

    @Column(name = "director", length = LENGTH_DIRECTOR)
    private @NotBlank String director;

    @OneToMany(mappedBy = "school", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Teacher> teachers = new ArrayList<>();


   @OneToMany(mappedBy = "school", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Student> students = new ArrayList<>();


    @OneToMany(mappedBy = "school", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SchoolClass> classes = new ArrayList<>();

    @Builder
    public School(String schoolName, String homepage, Address address, String director,
                  List<Teacher> teachers, List<Student> students, List<SchoolClass> classes) {
        this.schoolName = "HTL-Spengergasse";
        this.homepage = homepage;
        this.address = address;
        this.director = director;
        this.teachers = (teachers != null) ? new ArrayList<>(teachers) : new ArrayList<>();
        this.students = (students != null) ? new ArrayList<>(students) : new ArrayList<>();
        this.classes = (classes != null) ? new ArrayList<>(classes) : new ArrayList<>();

    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        teacher.setSchool(this);
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setSchool(this);
    }

    public void addClasses(SchoolClass schoolClass) {
        classes.add(schoolClass);
    }

    public List<Teacher> getTeachers() {
        return new ArrayList<>(teachers);
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public List<SchoolClass> getClasses() {
        return new ArrayList<>(classes);
    }

}
