package htl.SchoolAdministration.controller;

import htl.SchoolAdministration.TestFixtures;
import htl.SchoolAdministration.domain.Student;
import htl.SchoolAdministration.presentation.www.StudentController;
import htl.SchoolAdministration.service.student.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    private @Autowired MockMvc mockMvc;
    private @MockBean StudentService studentService;

    @Test
    void ensureGetStudentsReturnsProperView() throws Exception {
        Student student1 = TestFixtures.student3("Sam", "Rezaei");
        Student student2 = TestFixtures.student3("Muster", "Man");
        List<Student> students = List.of(student1, student2);
        when(studentService.getStudents()).thenReturn(students);
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("students", students))
                .andExpect(view().name("students/list"))
                .andDo(print());
    }






}
