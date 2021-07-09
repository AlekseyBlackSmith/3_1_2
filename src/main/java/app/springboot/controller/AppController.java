package app.springboot.controller;


import app.springboot.model.User;
import app.springboot.service.RoleService;
import app.springboot.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class AppController {
    private final UserService userService;
    private final RoleService roleService;


    public AppController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String toLogin1() {
        return "login";
    }

    @GetMapping("/login")
    public String toLogin2() {
        return "login";
    }

    @GetMapping(value = "/user")
    public String userPage(Model model,
                           Principal principal) {
        model.addAttribute("authorizedUser", userService.getUserByEmailWithRoles(principal.getName()));
        return "/user";
    }

    @GetMapping("user/{id}")
    public String showUser(@PathVariable("id") Long id,
                           Model model) {
        model.addAttribute("authorizedUser", userService.getUserByIdWithRoles(id));
        return "/user";
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model,
                              Principal principal) {
        model.addAttribute("authorizedUser", userService.getUserByEmailWithRoles(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "/admin";
    }

    @GetMapping("user/new")
    public String newUser(@ModelAttribute("user") User user,
                          Model model,
                          Principal principal) {
        model.addAttribute("authorizedUser", userService.getUserByEmailWithRoles(principal.getName()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "/new";
    }

    @PostMapping("/creatingNewUser")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("rolesSelected") Long[] rolesId) {
        for (Long roleId : rolesId) {
            user.setRole(roleService.getRoleById(roleId));
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("user/{id}/edit")
    public String updateUser(@ModelAttribute("user") User user,
                           @RequestParam("rolesSelected") Long[] rolesId) {
        for (Long roleId : rolesId) {
            user.setRole(roleService.getRoleById(roleId));
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("user/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

}
