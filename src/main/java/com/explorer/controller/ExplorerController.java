package com.explorer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Michael on 01.07.2014.
 */
@Controller
public class ExplorerController {

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String viewFiles() {
        return "files";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "redirect:/view";
    }


}
