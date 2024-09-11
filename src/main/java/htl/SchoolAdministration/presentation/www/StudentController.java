
package htl.SchoolAdministration.presentation.www;

import htl.SchoolAdministration.domain.Student;
import htl.SchoolAdministration.service.exceptions.PersonAlreadyExistsException;
import htl.SchoolAdministration.service.exceptions.PersonDoesNotExistException;
import htl.SchoolAdministration.service.student.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping(StudentController.BASE_ROUTE)
public class StudentController implements RedirectForwardSupport {

    protected static final String BASE_ROUTE = "/students";
    private final StudentService studentService;

    @GetMapping()
    public String getStudents(Model model) {
        List<Student> students = studentService.getStudents();
        model.addAttribute("students", students);
        return "students/list";
    }

    @GetMapping("/new")
    public ModelAndView showNewStudentForm() {
        var mav = new ModelAndView();
        mav.addObject("form", CreateStudentForm.create());
        mav.setViewName("students/new");
        return mav;
    }

    @PostMapping("/new")
    public String handleNewStudentFormSubmission(
            @Valid @ModelAttribute(name = "form") CreateStudentForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "students/new";
        }
        try {
            studentService.register(form.username(), form.password(), form.firstName(), form.lastName(), form.birthday());
            return "redirect:/students";
        } catch (PersonAlreadyExistsException e) {
            // Fehlermeldung hinzufügen und zur students/new Seite zurückleiten
            bindingResult.rejectValue("username", e.getMessage());
            return "students/new";
        }
    }

    @GetMapping("/edit/{key}")
    public String showEditStudentForm(@PathVariable String key, Model model) {
        return studentService.getStudentByKey(key)
                .map(EditStudentForm::create)
                .map(form -> model.addAttribute("form", form))
                .map(__ -> "students/edit")
                .orElse(redirect(BASE_ROUTE));
    }

    @PostMapping("/edit/{key}")
    public String handleEditStudentFormSubmission(@PathVariable String key, @Valid @ModelAttribute(name = "form")
    EditStudentForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "students/edit";
        }
        studentService.updateStudent(key, form.password(), form.firstName(), form.lastName(), form.birthday());
        return redirect(BASE_ROUTE);
    }

    @GetMapping("/delete/{key}")
    public String deleteStudent(@PathVariable String key) {
        studentService.deleteStudentByKey(key);
        return redirect(BASE_ROUTE);
    }

    @GetMapping("/show/{key}")
    public String showStudent(@PathVariable String key, Model model) {
        return studentService.getStudentByKey(key)
                .map(student -> model.addAttribute("student", student))
                .map(__ -> "students/show")
                .orElseThrow(() -> PersonDoesNotExistException.forKey(key));
    }

    @ExceptionHandler(PersonDoesNotExistException.class)
    public String handlePersonDoesNotExistException(PersonDoesNotExistException e, RedirectAttributes redirectAttributes) {
        log.warn("{} occured for key: {}", e.getClass().getSimpleName(), e.getKey());
        redirectAttributes.addFlashAttribute("nonExistingKey", e.getKey());
        redirectAttributes.addFlashAttribute("message", e.getMessage());
        return redirect(BASE_ROUTE);
    }
}
