package com.explorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Michael on 01.07.2014.
 */
@Controller
public class ExplorerController {

    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String viewFiles(ModelMap model) {
        return "files";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "redirect:/view";
    }


}
