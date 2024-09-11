package htl.SchoolAdministration.presentation.www;

import htl.SchoolAdministration.presentation.SchoolClassRestController;
import htl.SchoolAdministration.service.SchoolClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SchoolClassRestController.class)
class SchoolClassRestControllerTest {

    private @Autowired MockMvc mockMvc;

    private @MockBean SchoolClassService schoolClassService;

    @BeforeEach
    void setup() {

    }

    @Test
    void ensureGetApiSchoolClassesWork() {
       // mockMvc.perform();

    }
}