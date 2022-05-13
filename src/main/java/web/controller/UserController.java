package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.service.UserServiceImpl;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;


    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

//    @GetMapping(value = "/login")
//    public String loginPage() {
////        model.addAttribute("title", "Форма входа");
//        return "login1";
//    }

    @GetMapping
    public String userPage(Principal principal, Model model) {
        model.addAttribute("currentUser", userService.findByEmail(principal.getName()));
        return "user";
    }
}