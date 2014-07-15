package com.explorer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Michael on 11.07.2014.
 */
@Controller
//@RequestMapping("/")
public class AppController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "redirect:/index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

}
