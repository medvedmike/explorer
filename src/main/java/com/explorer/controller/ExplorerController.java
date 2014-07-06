package com.explorer.controller;

import com.explorer.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

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

    private static final int BUFFER_SIZE = 4096;

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public void downloadFile(@RequestParam(value = "name", required = true) String name,
                             final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        File file = fileSystem.getFile(name);
        if (file == null) {
            response.sendError(HttpStatus.NOT_FOUND.value());
        } else {
            String mimeType = request.getServletContext().getMimeType(name);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setContentLengthLong(file.length());
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bread = -1;
            while ((bread = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bread);
            }
            outputStream.close();
            inputStream.close();
        }
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String uploadFile(@RequestParam(value = "file") MultipartFile file,
                             @RequestParam(value = "directory") String dir,
                             ModelMap model, final HttpServletRequest request) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                File directory = new File(dir);
                if (!directory.exists() || directory.isFile())
                    throw new FileNotFoundException();
                File serverFile = new File(directory.getAbsolutePath() + File.separator + file.getOriginalFilename());
                if (serverFile.createNewFile()) {
                    BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(serverFile));
                    os.write(bytes);
                    os.close();
                    return "redirect:/files?directory=" + dir;
                } else {
                    model.put("message", ((MessageSource)context.getBean("messageSource")).getMessage("error.create-file", new Object[0], request.getLocale()));
                    return "error";
                }
            } catch (IOException e) {
                model.put("message", ((MessageSource)context.getBean("messageSource")).getMessage("error.internal", new Object[0], request.getLocale()));
                return "error";
            }
        } else {
            model.put("message", ((MessageSource)context.getBean("messageSource")).getMessage("error.empty-file", new Object[0], request.getLocale()));
            return "error";
        }
    }


}
