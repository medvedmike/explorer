package com.explorer.controller;

import com.explorer.domain.fs.dataprovider.DownloadAbsoluteFileProvider;
import com.explorer.domain.fs.dataprovider.DownloadFileProvider;
import com.explorer.domain.fs.dataprovider.UploadAbsoluteFileProvider;
import com.explorer.domain.fs.dataprovider.UploadFileProvider;
import com.explorer.service.FileSystemService;
import com.explorer.service.SharedPathService;
import com.explorer.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private SharedPathService sharedPathService;

    @Autowired
    private ApplicationContext context;

    @RequestMapping(method = RequestMethod.GET)
    public String sharedList(ModelMap model, Principal principal) throws IOException {
        if (principal != null) {
            model.put("url", "shared");
            model.put("directory", fileSystem.getSharedDirectory("", principal.getName()));
            return "files";
        } else {
            return "redirect:/index";
        }
    }

    @RequestMapping(method = RequestMethod.GET, params = {"path"})
    public String sharedDir(@RequestParam(value = "path", required = true, defaultValue = "") String path,
                                          ModelMap model, Principal principal) throws IOException {
        if (principal != null) {
            model.put("url", "shared");
            model.put("directory", fileSystem.getSharedDirectory(path, principal.getName()));
            return "files";
        } else {
            return "redirect:/index";
        }
    }

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public void downloadFile(@RequestParam(value = "name", required = true) String name,
                             final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        DownloadFileProvider provider = new DownloadAbsoluteFileProvider(name);
        String mimeType = request.getSession().getServletContext().getMimeType(name);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLength((int)provider.getSize());
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", provider.getName()));
        provider.copy(response.getOutputStream());
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String uploadFile(@RequestParam(value = "file") MultipartFile file,
                             @RequestParam(value = "directory") String dir,
                             ModelMap model, final HttpServletRequest request) throws IOException {
        UploadFileProvider provider = new UploadAbsoluteFileProvider(dir, file.getOriginalFilename());
        provider.write(file.getInputStream());
        return "redirect:/shared?path=" + dir;
    }

    @RequestMapping(value = "/share", method = RequestMethod.POST, params = {"username", "path"})
    public String shareFile(@RequestParam(value = "username", required = true) String targetUsername,
                            @RequestParam(value = "path", required = true) String sharedPath,
                            Principal principal, ModelMap model) throws IOException {
        if (principal != null) {
            try {
                sharedPathService.sharePath(principal.getName(), targetUsername, sharedPath);
                model.put("message", "Shared successfully");
                return "redirect:/shared?path=" + sharedPath;
            } catch (UserNotFoundException e) {
                model.put("message", "Share error. User not found");
                return "redirect:/shared?path=" + sharedPath;
            }
        } else {
            return "redirect:/index";
        }
    }

    @RequestMapping(value = "/directory", method = RequestMethod.POST)
    public String mkdir(@RequestParam(value = "name") String name,
                        @RequestParam(value = "directory") String dir,
                        Principal principal) throws IOException {
        fileSystem.mkdirShared(dir, name, principal.getName());
        return "redirect:/shared?path=" + dir;
    }
}
