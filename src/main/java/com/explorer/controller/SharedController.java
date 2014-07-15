package com.explorer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Michael on 15.07.2014.
 */
@Controller
@RequestMapping("/shared")
public class SharedController {

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String sharedList() {
        return "list";
    }

    @RequestMapping(method = RequestMethod.GET, params = {"path"})
    public @ResponseBody String sharedDir(@RequestParam(value = "path", required = true) String path) {
        return "concreate dir ".concat(path);
    }
}
