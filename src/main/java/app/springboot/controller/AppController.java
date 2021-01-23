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
    @GetMapping(value = "/show")
    public String userPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.getByUserName(authentication.getName()));
        model.addAttribute("roles", userService.getByUserName(authentication.getName()).getRoles());
        return "show";
    }

    //получая такой запрос мы получаем id интересующего нас пользователя,
    //находим его и отправляем с моделью на страницу show (Admin увидит любого)
    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roles", userService.getById(id).getRoles());
        return "show";
    }

    //метод отправляет на страницу со всеми пользователями
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }

    //отправляем в форму new пустрого user'a
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    //обработка метода POST с полученным user'ом, получение ролей, назначение их user'у
    @PostMapping("/users")
    public String create(@RequestParam(name="roleAdmin", required = false) boolean isAdmin,
                         @RequestParam(name = "roleUser", required = false) boolean isUser,
                         @ModelAttribute("user") User user) {
        user.setRoles(getRolesFromForms(isAdmin, isUser));
        userService.save(user);

        return "redirect:/users";
    }

    // получение запроса на апгрейд, отправка user'a на update
    @GetMapping("/{id}/update")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getById(id));
        return "update";
    }

    //получение обновленного пользователя из формы
    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("user") User user,
                       @PathVariable("id") Long id,
                       @RequestParam(name="roleAdmin", required = false) boolean isAdmin,
                       @RequestParam(name = "roleUser", required = false) boolean isUser) {
        user.setRoles(getRolesFromForms(isAdmin, isUser));
        userService.save(user);
        return "redirect:/users";
    }

    //удаление по id
    @DeleteMapping("/{id}")
    public String delite(@PathVariable("id") Long id) {
        userService.removeById(id);
        return "redirect:/users";
    }


    //вспомогательный метод
    private Set<Role> getRolesFromForms(boolean isAdmin, boolean isUser) {
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
