package htl.SchoolAdministration.controller;

import htl.SchoolAdministration.TestFixtures;
import htl.SchoolAdministration.domain.Teacher;
import htl.SchoolAdministration.presentation.www.TeacherController;
import htl.SchoolAdministration.service.teacher.TeacherService;
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

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    private @Autowired MockMvc mockMvc;
    private @MockBean TeacherService teacherService;

    @Test
    void ensureGetTeachersReturnsProperView() throws Exception {
        Teacher teacher1 = TestFixtures.teacher3("Sami", "Rezaei");
        Teacher teacher2 = TestFixtures.teacher3("Teacher", "Nr2");
        List<Teacher> teachers = List.of(teacher1, teacher2);
        when(teacherService.getTeachers()).thenReturn(teachers);

        //  expect
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("teachers", teachers))
                .andExpect(view().name("teachers/list"))
                .andDo(print());
    }

}