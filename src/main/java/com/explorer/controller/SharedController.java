package com.explorer.controller;

import com.explorer.domain.SharedPath;
import com.explorer.service.FileSystemService;
import com.explorer.service.SharedPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.security.Principal;

/**
 * Created by Michael on 15.07.2014.
 */
@Controller
@RequestMapping("/shared")
public class SharedController {

    @Autowired
    private FileSystemService fileSystem;

    @RequestMapping(method = RequestMethod.GET)
    public String sharedList(ModelMap model, Principal principal) throws IOException {
        model.put("url", "shared");
        model.put("directory", fileSystem.getSharedDirectory("", principal.getName()));
        return "files";
    }

    @RequestMapping(method = RequestMethod.GET, params = {"path"})
    public String sharedDir(@RequestParam(value = "path", required = true, defaultValue = "") String path,
                                          ModelMap model, Principal principal) throws IOException {
        model.put("url", "shared");
        model.put("directory", fileSystem.getSharedDirectory(path, principal.getName()));
        return "files";
    }
}
