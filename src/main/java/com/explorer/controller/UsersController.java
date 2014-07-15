package com.explorer.controller;

import com.explorer.domain.Role;
import com.explorer.domain.User;
import com.explorer.service.FileSystemService;
import com.explorer.service.RoleService;
import com.explorer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 08.07.2014.
 */
@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FileSystemService fileSystem;

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
        if (bindingResult.hasErrors()) {
            model.put("errors", bindingResult.getAllErrors());
            return "../../register";
        }
        User user = userService.getUser(newUser.getUsername());
        if (user != null) {
            model.put("errorCode", "inputError.loginExists");
            return "../../register";
        }
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        newUser.setPassword(encoder.encodePassword(newUser.getPassword(), newUser.getUsername()));

        Role userRole = roleService.getRole("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        newUser.setAuthority(roles);

        File dir = fileSystem.createUserDirectory(newUser.getUsername());
        newUser.setHome(dir.getAbsolutePath());

        userService.saveUser(newUser);
        return "redirect:/home";
    }
}
