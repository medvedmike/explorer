package com.explorer.controller;

import com.explorer.domain.fs.dataprovider.DownloadAbsoluteFileProvider;
import com.explorer.domain.fs.dataprovider.DownloadFileProvider;
import com.explorer.domain.fs.dataprovider.UploadAbsoluteFileProvider;
import com.explorer.domain.fs.dataprovider.UploadFileProvider;
import com.explorer.service.FileSystemService;
import com.explorer.service.exceptions.DirectoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
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
 * Created by Michael on 11.07.2014.
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController {

    @Autowired
    private FileSystemService fileSystem;

    @Autowired
    private ApplicationContext context;

    @RequestMapping(method = RequestMethod.GET)
    public String showDirectory(@RequestParam(value = "path", defaultValue = "") String path,
                              ModelMap model, Principal principal) throws IOException {
        model.put("url", "home");
        model.put("directory", fileSystem.getDirectoryHome(path, principal.getName()));
        return "files";
    }

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public void downloadFile(@RequestParam(value = "name", required = true) String name,
                             final HttpServletRequest request, final HttpServletResponse response,
                             Principal principal) throws IOException {
        DownloadFileProvider provider = new DownloadAbsoluteFileProvider(fileSystem.buildHomePath(name, principal.getName()).toString());
        String mimeType = request.getSession().getServletContext().getMimeType(name);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLengthLong(provider.getSize());
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", provider.getName()));
        provider.copy(response.getOutputStream());
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String uploadFile(@RequestParam(value = "file") MultipartFile file,
                             @RequestParam(value = "directory") String dir,
                             ModelMap model, final HttpServletRequest request,
                             Principal principal) throws IOException {
        if (!file.isEmpty()) {
            UploadFileProvider provider = new UploadAbsoluteFileProvider(fileSystem.buildHomePath(dir, principal.getName()).toString(), file.getOriginalFilename());
            provider.write(file.getInputStream());
            return "redirect:/home?path=" + dir;
        } else {
            model.put("message", ((MessageSource)context.getBean("messageSource")).getMessage("error.empty-file", new Object[0], request.getLocale()));
            return "error";
        }
    }
}