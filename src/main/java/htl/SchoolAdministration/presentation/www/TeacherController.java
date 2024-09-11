package htl.SchoolAdministration.presentation.www;

import htl.SchoolAdministration.domain.Teacher;
import htl.SchoolAdministration.service.exceptions.PersonAlreadyExistsException;
import htl.SchoolAdministration.service.exceptions.PersonDoesNotExistException;
import htl.SchoolAdministration.service.teacher.TeacherService;
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
@RequestMapping(TeacherController.BASE_ROUTE)

public class TeacherController implements RedirectForwardSupport {

    protected static final String BASE_ROUTE = "/teachers";
    private final TeacherService teacherService;

    @GetMapping()
    public String getTeachers(Model model) {
        List<Teacher> teachers = teacherService.getTeachers();
        model.addAttribute("teachers", teachers);
        return "teachers/list";
    }

    @GetMapping("/new")
    public ModelAndView showNewTeacherForm() {
        var mav = new ModelAndView();
        mav.addObject("form", CreateTeacherForm.create());
        mav.setViewName("teachers/new");
        return mav;
    }

    @PostMapping("/new")
    public String handleNewTeacherFormSubmission(
            @Valid @ModelAttribute(name = "form") CreateTeacherForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "teachers/new";
        }
        try {
            teacherService.register(form.username(), form.password(), form.firstName(), form.lastName(), form.birthday());
            return "redirect:/teachers";
        } catch (PersonAlreadyExistsException e) {
            // Fehlermeldung hinzufügen und zur students/new Seite zurückleiten
            bindingResult.rejectValue("username", e.getMessage());
            return "teachers/new";
        }
    }

    @GetMapping("/edit/{key}")
    public String showEditTeacherForm(@PathVariable String key, Model model) {
        return teacherService.getTeacherByKey(key)
                .map(EditTeacherForm::edit)
                .map(form -> model.addAttribute("form", form))
                .map(__ -> "teachers/edit")
                .orElse(redirect(BASE_ROUTE));
    }

    @PostMapping("/edit/{key}")
    public String handleEditTeacherFormSubmission(@PathVariable String key, @Valid @ModelAttribute(name = "form")
    EditTeacherForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "teachers/edit";
        }
         teacherService.updateTeacher(key, form.password(), form.firstName(), form.lastName(), form.birthday());
        return redirect(BASE_ROUTE);

    }

    @GetMapping("/delete/{key}")
    public String deleteTeacher(@PathVariable String key) {
        teacherService.deleteTeacher(key);
        return redirect(BASE_ROUTE);
    }

    @GetMapping("/show/{key}")
    public String showTeacher(@PathVariable String key, Model model) {
        return teacherService.getTeacherByKey(key)
                .map(teacher -> model.addAttribute("teacher", teacher))
                .map(__ -> "teachers/show")
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
