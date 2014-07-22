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
 */
@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp(ModelMap model) {
        model.addAttribute(new User());
        return "../../register";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String register(@Valid User newUser, BindingResult bindingResult, ModelMap model) {
        List<ObjectError> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            errors.addAll(bindingResult.getFieldErrors());
            return "../../register";
        }
        User user = userService.getUser(newUser.getUsername());
        if (user != null) {
            errors.add(new ObjectError("user", "inputError.loginExists"));
            return "../../register";
        }
        model.put("errors", errors);
        userService.saveUser(newUser);
        return "redirect:/home";
    }
}
