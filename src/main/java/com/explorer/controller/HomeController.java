package com.explorer.controller;

import com.explorer.service.FileSystemService;
import com.explorer.service.exceptions.DirectoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;

/**
 * Created by Michael on 11.07.2014.
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController {

    @Autowired
    private FileSystemService fileSystem;

    @RequestMapping(method = RequestMethod.GET)
    public String showDirectory(@RequestParam(value = "path", defaultValue = "") String path,
                              ModelMap model, Principal principal) throws IOException {
        model.put("url", "home");
        model.put("directory", fileSystem.getDirectoryHome(path, principal.getName()));
        return "files";
    }
}
