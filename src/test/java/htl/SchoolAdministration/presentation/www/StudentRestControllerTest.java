package htl.SchoolAdministration.presentation.www;

import com.fasterxml.jackson.databind.ObjectMapper;
import htl.SchoolAdministration.TestFixtures;
import htl.SchoolAdministration.domain.Student;
import htl.SchoolAdministration.presentation.StudentRestController;
import htl.SchoolAdministration.service.student.ReplaceStudentCommand;
import htl.SchoolAdministration.service.student.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static htl.SchoolAdministration.TestFixtures.*;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentRestController.class)
class StudentRestControllerTest {

    private @Autowired MockMvc mockMvc;
    private @MockBean StudentService studentService;
    private @Autowired ObjectMapper mapper;

    @BeforeEach
    void setup() {
        assumeThat(mockMvc).isNotNull();
        assumeThat(studentService).isNotNull();
    }

    @Test
        //status 404 "web server cannot find a resource at a certain URL"
    void ensureGetStudentReturnsNoFoundForNonExistingId() throws Exception {
        long id = 4711L;
        when(studentService.getStudent(eq(id))).thenReturn(Optional.empty());
        var request = get(StudentRestController.ROUTE_ID, id).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void ensureGetStudentReturnsNoContentForMissingData() throws Exception {
        when(studentService.getStudents()).thenReturn(Collections.emptyList());
        var request = get(StudentRestController.BASE_URL).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status()
                        .isNoContent())
                .andDo(print());
    }

    @Test
    void ensureGetStudentsReturnsOkForExistingData() throws Exception {
        var student = student3("Sam", "Rezaei");
        student.setUsername("Rezaei.Sam@spg.at");
        when(studentService.getStudents()).thenReturn(List.of(student));

        var request = get(StudentRestController.BASE_URL).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("Rezaei.Sam@spg.at"))
                .andDo(print());
    }

    @Test
    void ensureGetStudentReturnsOkForExistingDataWithValidLastNamePart() throws Exception {
        String lastNamePart = "Z";
        var student = student3("Sam", "Rezaei");
        student.setUsername("Rezaei.Sam@spg.at");
        when(studentService.searchStudents(lastNamePart)).thenReturn(List.of(student));
        var request = get(StudentRestController.BASE_URL).param("lastNamePart", lastNamePart)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("Rezaei.Sam@spg.at"))
                .andDo(print());
    }

    @Test
    void ensureGetStudentsReturnsNoContentForMissingDataWithValidLastNamePart() throws Exception {
        String lastNamePart = "X";
        var student = student3("Sam", "Rezaei");
        student.setUsername("Rezaei.Sam@spg.at");

        when(studentService.searchStudents(lastNamePart)).thenReturn(Collections.emptyList());
        var request = get(StudentRestController.BASE_URL).param("lastNamePart", lastNamePart)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());
    }


    @Test
    void ensureGetApiStudentsWithEmptyResponseReturnsNoContent() throws Exception {
        //given
        Student student = student3("Sami", "Rezaei");
        when(studentService.fetchStudents(any())).thenReturn(Collections.emptyList());

        //expect
        var request = get("/api/students").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());
    }


    @Test
        // status 200 "success, the client has requested from the server and the server has replied"
    void ensureGetStudentReturnsOkForExistingData() throws Exception {
        var student = TestFixtures.student(HTL(), ABIF());
        student.setUsername("student@spg.at");
        when(studentService.getStudents()).thenReturn(List.of(student));

        var request = get(StudentRestController.BASE_URL).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("student@spg.at"))
                .andDo(print());
    }


    @Test
    void ensureGetStudentReturnsHttpStatusOkAndBodyForNonEmptyDatabase() throws Exception {
        var id = 4711L;
        var request = get(StudentRestController.ROUTE_ID, id).accept(MediaType.APPLICATION_JSON);
        var student = getStudent();
        when(studentService.getStudent(eq(id))).thenReturn(Optional.of(student));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("Sam.Dobar@spg.at"))
                .andExpect(jsonPath("$.lastName").value("Dobar"))
                .andExpect(jsonPath("$.firstName").value("Sam"))
                // .andExpect(jsonPath("$.password").doesNotExist())
                .andDo(print());
    }

    @Test
    void ensureGetStudentsReturnsOkForExistingDataWithValidLastNamePart() throws Exception {
        String lastNamePart = "D";
        var student = getStudent();
        when(studentService.searchStudents(lastNamePart)).thenReturn(List.of(student));

        var request = get(StudentRestController.BASE_URL).param("lastNamePart", lastNamePart)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("Sam.Dobar@spg.at"))
                .andDo(print());
    }

    @Test
        //status 404 "web server cannot find a resource at a certain URL"
    void ensureGetStudentByIdReturnsHttpStatusNotFoundForEmptyDatabase() throws Exception {
        var request = get(StudentRestController.ROUTE_ID, 4711)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    void ensureReplaceStudentReturns() throws Exception {
        Student s = spy(Student.builder()
                .username("sam@spg.at")
                .password("geheim")
                .firstName("Sam")
                .lastName("rez")
                .build());
        String username = "username";
        when(studentService.replaceStudent(eq(4711l), eq(username), any(), any(), any(), any())).thenReturn(s);
        when(s.getId()).thenReturn(4712l);
        ReplaceStudentCommand cmd = new ReplaceStudentCommand(username, "geheim", "Dobar", "Sam", "12.01.1800");
        var request = put(StudentRestController.ROUTE_ID, 4711).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cmd));
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

    }


    private Student getStudent() {
        Student student = new Student();
        student.setUsername("Sam.Dobar@spg.at");
        student.setPassword("geheimer");
        student.setFirstName("Sam");
        student.setLastName("Dobar");
        return student;
    }

}
