package com.javacorner.web;

import static com.javacorner.constants.JavaCornerConstants.INSTRUCTOR;
import static com.javacorner.constants.JavaCornerConstants.KEYWORD;
import static com.javacorner.constants.JavaCornerConstants.LIST_INSTRUCTORS;

import com.javacorner.entity.Instructor;
import com.javacorner.entity.User;
import com.javacorner.service.InstructorService;
import com.javacorner.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/instructors")
public class InstructorController {

    private InstructorService instructorService;
    private UserService userService;

    public InstructorController(InstructorService instructorService, UserService userService) {
        this.instructorService = instructorService;
        this.userService = userService;
    }

    @GetMapping(value = "/index")
    public String instructors (Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<Instructor> instructors = instructorService.findInstructorsByName(keyword);
        model.addAttribute(LIST_INSTRUCTORS, instructors);
        model.addAttribute(KEYWORD, keyword);
        return "instructor-views/instructors";
    }

    @GetMapping(value = "/delete")
    public String deleteInstructor(Long instructorId, String keyword) {
        instructorService.removeInstructor(instructorId);
        return "redirect:/instructors/index?keyword=" + keyword;
    }

    @GetMapping(value = "/formUpdate")
    public String updateInstructor(Model model, Long instructorId) {
        //current Instructor
        Instructor instructor = instructorService.loadInstructorById(instructorId);
        model.addAttribute(INSTRUCTOR, instructor);
        return "instructor-views/formUpdate";
    }

    @PostMapping(value = "/update")
    public String update(Instructor instructor) {
        instructorService.updateInstructor(instructor);
        return "redirect:/instructors/index";
    }

    @GetMapping(value = "/formCreate")
    public String formInstructor(Model model) {
       model.addAttribute(INSTRUCTOR, new Instructor());
       return "instructor-views/formCreate";
    }

    @PostMapping(value = "/save")
    public String save(@Valid Instructor instructor, BindingResult bindingResult) {
    User user = userService.loadUserByEmail(instructor.getUser().getEmail());
    if(user!=null) bindingResult.rejectValue("user.email", null, "There is already an account registered to this email");
    if (bindingResult.hasErrors()) return "instructor-views/formCreate";
    instructorService.createInstructor(instructor.getFirstName(), instructor.getLastName(), instructor.getSummary(), instructor.getUser().getEmail(), instructor.getUser().getPassword());
    return "redirect:/instructors/index";
    }
}
