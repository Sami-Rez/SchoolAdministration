package htl.SchoolAdministration.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = {"school", "studentList", "teacherList"})
@Table(name = "schoolClass")

public class SchoolClass extends AbstractPersistable<Long> {
    public static final int LENGTH_SCHOOLCLASS_NAME = 128;
    public static final int LENGTH_FLOOR = 4;

    @Column(name = "school_class_name", length = LENGTH_SCHOOLCLASS_NAME)
    private @NotNull String schoolClassName;

    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private @NotNull Department department;

    @Column(name = "floor", length = LENGTH_FLOOR)
    private @NotNull Integer floor;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "studentClass", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Student> studentList;

    @ManyToMany(mappedBy = "classes", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Teacher> teacherList;

    @Builder
    public SchoolClass(String schoolClassName, Department department, Integer floor, School school,
                       List<Student> studentList, List<Teacher> teacherList) {
        this.schoolClassName = schoolClassName;
        this.department = department;
        this.floor = floor;
        this.school = school;
        this.teacherList = (teacherList != null) ? new ArrayList<>(teacherList) : new ArrayList<>();
        this.studentList = (studentList != null) ? new ArrayList<>(studentList) : new ArrayList<>();
    }

    public void addStudent(Student student) {
        studentList.add(student);
        student.setStudentClass(this);
        school.addStudent(student);
    }

    public void addTeacher(Teacher teacher) {
        teacherList.add(teacher);
        teacher.addClasses(this);
        school.addTeacher(teacher);
    }

    public List<Student> getStudentList() {
        return new ArrayList<>(studentList);
    }

    public List<Teacher> getTeacherList() {
        return new ArrayList<>(teacherList);
    }

}
