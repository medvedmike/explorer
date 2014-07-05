package com.explorer.controller;

import com.explorer.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Michael on 01.07.2014.
 */
@Controller
public class ExplorerController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private FileSystemService fileSystem;

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public String viewFiles(@RequestParam(value = "directory", required = false) String directoryName, ModelMap model) {
        if (directoryName != null && directoryName.compareTo("") == 0) {
            directoryName = null;
        }
        model.put("content", fileSystem.getDirectoryContent(directoryName));
        model.put("directory", fileSystem.getDirectoryInfo(directoryName));
        return "files";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "redirect:/files";
    }


}
