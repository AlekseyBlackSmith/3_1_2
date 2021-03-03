package app.springboot.controller;


import app.springboot.model.Role;
import app.springboot.model.User;
import app.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AppController {
    private final UserService userService;

    @Autowired
    public AppController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String toLogin1() {
        return "login";
    }

    @GetMapping("/login")
    public String toLogin2() {
        return "login";
    }

    //метод отправляет на страницу с переданным пользователем.
    //подтянется текущий пользователь из аутентификации. (User увидит только себя)
    @GetMapping(value = "/user")
    public String userPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("authorizedUser", userService.getByEmail(authentication.getName()));
        model.addAttribute("roles", userService.getByEmail(authentication.getName()).getRoles());
        return "/user";
    }

    //получая такой запрос мы получаем id интересующего нас пользователя,
    //находим его и отправляем с моделью на страницу show (Admin увидит любого)
    @GetMapping("user/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("authorizedUser", userService.getById(id));
        return "/user";
    }

    //метод отправляет на страницу со всеми пользователями
    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("authorizedUser", userService.getByEmail(authentication.getName()));
        return "/admin";
    }


    //запрос на создание пользователя
    @GetMapping("user/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("authorizedUser", userService.getByEmail(authentication.getName()));
        return "/new";

    }

    //собственно, создание пользователя
    @PostMapping("/admin")
    public String create(@RequestParam(name="roleAdmin", required = false) boolean isAdmin,
                         @RequestParam(name = "roleUser", required = false) boolean isUser,
                         @ModelAttribute("user") User user) {
        user.setRoles(getRolesFromForm(isAdmin, isUser));
        userService.save(user);
        return "redirect:/admin";
    }


    @PostMapping("user/{id}/edit")
    public String editUser(User user,
                       @PathVariable("id") Long id,
                       @RequestParam(name="roleAdmin", required = false) boolean isAdmin,
                       @RequestParam(name = "roleUser", required = false) boolean isUser) {
        user.setRoles(getRolesFromForm(isAdmin, isUser));
        userService.save(user);
        return "redirect:/admin";
    }

//    удаление по id
    @PostMapping("user/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }


//    //вспомогательный метод
    private Set<Role> getRolesFromForm(boolean isAdmin, boolean isUser) {
        Set<Role> newRoles = new HashSet<>();

        if(isAdmin) {
            newRoles.add(new Role(1L, "ROLE_ADMIN"));
        }
        if(isUser) {
            newRoles.add(new Role(2L, "ROLE_USER"));
        }
        if(newRoles.size() == 0) {
            newRoles.add(new Role(2L, "ROLE_USER"));
        }
        return newRoles;
    }

}
