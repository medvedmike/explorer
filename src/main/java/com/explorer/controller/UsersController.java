package com.explorer.controller;

import com.explorer.domain.User;
import com.explorer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 08.07.2014.
 * Контроллер для управления пользователями
 */
@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    /**
     * отдает страничку для регистрации
     * @param model
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp(ModelMap model) {
        model.addAttribute(new User());
        return "../../register";
    }

    /**
     * отдает страничку для входа в систему
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "index";
    }

    /**
     * Регистрация пользователя
     * @param newUser
     * @param bindingResult
     * @param model
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String register(@Valid User newUser, BindingResult bindingResult, ModelMap model) {
        List<ObjectError> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) { //проверка на ошибки в заполнении полей
            errors.addAll(bindingResult.getFieldErrors());
            model.put("errors", errors);
            return "../../register";
        }
        User user = userService.getUser(newUser.getUsername());
        if (user != null) { //проверка на существование данного логина в системе
            errors.add(new ObjectError("user", "inputError.loginExists"));
            model.put("errors", errors);
            return "../../register";
        }
        userService.saveUser(newUser);
        return "redirect:/home";
    }
}
