package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.service.RoleServiceImpl;
import web.service.UserServiceImpl;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage(Principal principal, ModelMap model) {
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allRoles", roleService.findAllRoles());
        model.addAttribute("currentUser", userService.findByEmail(principal.getName()));
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping("/update/{id}")
    public String updateUser(Long id, User user, @RequestParam(value = "roles", required = false) Set<Long> roleIds) {
        Set<Role> currentRoles = userService.findById(id).get().getRoles();
        Set<Role> roles = new HashSet<>();
        if (roleIds != null) {
            for (Long i : roleIds) {
                roles.add(roleService.findById(i).get());
                user.setRoles(roles);
            }
        } else {
            user.setRoles(currentRoles);
        }
        userService.saveAndFlush(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        userService.save(user);
        return "redirect:/admin";
    }
}