package htl.SchoolAdministration.presentation.www;

import com.fasterxml.jackson.databind.ObjectMapper;
import htl.SchoolAdministration.domain.Teacher;
import htl.SchoolAdministration.presentation.TeacherRestController;
import htl.SchoolAdministration.service.teacher.ReplaceTeacherCommand;
import htl.SchoolAdministration.service.teacher.TeacherService;
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

import static htl.SchoolAdministration.TestFixtures.teacher3;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeacherRestController.class)
public class TeacherRestControllerTest {

    private @Autowired MockMvc mockMvc;
    private @MockBean TeacherService teacherService;
    private @Autowired ObjectMapper mapper;

    @BeforeEach
    void setup() {
        assumeThat(mockMvc).isNotNull();
        assumeThat(teacherService).isNotNull();
    }


    @Test
        //status 404 "web server cannot find a resource at a certain URL"
    void ensureGetTeacherReturnsNoFoundForNonExistingId() throws Exception {
        long id = 4711L;
        when(teacherService.getTeacher(eq(id))).thenReturn(Optional.empty());
        var request = get(TeacherRestController.ROUTE_ID, id).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void ensureGetTeacherReturnsNoContentForMissingData() throws Exception {
        when(teacherService.getTeachers()).thenReturn(Collections.emptyList());
        var request = get(TeacherRestController.BASE_URL).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status()
                        .isNoContent())
                .andDo(print());
    }

    @Test
    void ensureGetTeachersReturnsOkForExistingData() throws Exception {
        var teacher = teacher3("Sam", "Rezaei");
        teacher.setUsername("Rezaei.Sam@spg.at");
        when(teacherService.getTeachers()).thenReturn(List.of(teacher));

        var request = get(TeacherRestController.BASE_URL).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("Rezaei.Sam@spg.at"))
                .andDo(print());
    }

    @Test
    void ensureGetTeacherReturnsOkForExistingDataWithValidLastNamePart() throws Exception {
        String lastNamePart = "Z";
        var teacher = teacher3("Sam", "Rezaei");
        teacher.setUsername("Rezaei.Sam@spg.at");
        when(teacherService.searchTeachers(lastNamePart)).thenReturn(List.of(teacher));
        var request = get(TeacherRestController.BASE_URL).param("lastNamePart", lastNamePart)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("Rezaei.Sam@spg.at"))
                .andDo(print());
    }

    @Test
    void ensureGetTeachersReturnsNoContentForMissingDataWithValidLastNamePart() throws Exception {
        String lastNamePart = "X";
        var teacher = teacher3("Sam", "Rezaei");
        teacher.setUsername("Rezaei.Sam@spg.at");

        when(teacherService.searchTeachers(lastNamePart)).thenReturn(Collections.emptyList());
        var request = get(TeacherRestController.BASE_URL).param("lastNamePart", lastNamePart)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void ensureGetApiTeachersWithEmptyResponseReturnsNoContent() throws Exception {
        //given
        Teacher teacher = teacher3("Sami", "Rezaei");
        when(teacherService.fetchTeachers(any())).thenReturn(Collections.emptyList());

        //expect
        var request = get("/api/teachers").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andDo(print());
    }


    @Test
        // status 200 "success, the client has requested from the server and the server has replied"
    void ensureGetTeacherReturnsOkForExistingData() throws Exception {
        var teacher = teacher3("Sami", "Rezaei");
        teacher.setUsername("teacher@spg.at");
        when(teacherService.getTeachers()).thenReturn(List.of(teacher));

        var request = get(TeacherRestController.BASE_URL).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("teacher@spg.at"))
                .andDo(print());
    }


    @Test
    void ensureGetTeacherReturnsHttpStatusOkAndBodyForNonEmptyDatabase() throws Exception {
        var id = 4711L;
        var request = get(TeacherRestController.ROUTE_ID, id).accept(MediaType.APPLICATION_JSON);
        var teacher = getTeacher();
        when(teacherService.getTeacher(eq(id))).thenReturn(Optional.of(teacher));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("Rezaei.Sami@spg.at"))
                .andExpect(jsonPath("$.lastName").value("Rezaei"))
                .andExpect(jsonPath("$.firstName").value("Sami"))
                // .andExpect(jsonPath("$.password").doesNotExist())
                .andDo(print());
    }

    @Test
    void ensureGetTeachersReturnsOkForExistingDataWithValidLastNamePart() throws Exception {
        String lastNamePart = "D";
        var teacher = getTeacher();
        when(teacherService.searchTeachers(lastNamePart)).thenReturn(List.of(teacher));

        var request = get(TeacherRestController.BASE_URL).param("lastNamePart", lastNamePart)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("Rezaei.Sami@spg.at"))
                .andDo(print());
    }

    @Test
        //status 404 "web server cannot find a resource at a certain URL"
    void ensureGetTeacherByIdReturnsHttpStatusNotFoundForEmptyDatabase() throws Exception {
        var request = get(TeacherRestController.ROUTE_ID, 4711)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    void ensureReplaceTeacherReturns() throws Exception {
        Teacher s = spy(Teacher.builder()
                .username("sam@spg.at")
                .password("geheimERpa55!SUPEr")
                .firstName("Sam")
                .lastName("rez")
                .build());
        String username = "username";
        when(teacherService.replaceTeacher(eq(4711l), eq(username), any(), any(), any(), any())).thenReturn(s);
        when(s.getId()).thenReturn(4712l);
        ReplaceTeacherCommand cmd = new ReplaceTeacherCommand(username, "SECUrepa55wORt11!supER", "Dobar", "Sam", "12.01.1800");
        var request = put(TeacherRestController.ROUTE_ID, 4711).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cmd));
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

    }

    private Teacher getTeacher() {
        Teacher teacher = new Teacher();
        teacher.setUsername("Rezaei.Sami@spg.at");
        teacher.setPassword("geheimer");
        teacher.setFirstName("Sami");
        teacher.setLastName("Rezaei");
        return teacher;
    }

}
