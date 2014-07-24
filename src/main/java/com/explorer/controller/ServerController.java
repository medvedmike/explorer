package com.explorer.controller;

import com.explorer.domain.fs.dataprovider.DownloadAbsoluteFileProvider;
import com.explorer.domain.fs.dataprovider.DownloadFileProvider;
import com.explorer.domain.fs.dataprovider.UploadAbsoluteFileProvider;
import com.explorer.domain.fs.dataprovider.UploadFileProvider;
import com.explorer.service.FileSystemService;
import com.explorer.service.SharedPathService;
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
 * Created by Michael on 01.07.2014.
 * Управление файлами на всем сервере (для администратора)
 */
@Controller
@RequestMapping("/server")
public class ServerController implements ControllerExceptionsHandler {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private FileSystemService fileSystem;

    @Autowired
    private SharedPathService sharedPathService;

    /**
     * Просмотр файлов на всем сервере (для администратора)
     * @param path абсолютный путь
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    public String viewFiles(@RequestParam(value = "path", required = false, defaultValue = "") String path,
                            ModelMap model) throws IOException {
        model.put("url", "server");
        model.put("directory", fileSystem.getDirectoryGlobal(path));
        return "files";
    }

    /**
     * Загрузка файла
     * @param name
     * @param request
     * @param response
     * @throws IOException
     */
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

    /**
     * Отправка файла на сервер
     * @param file
     * @param dir
     * @param model
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String uploadFile(@RequestParam(value = "file") MultipartFile file,
                             @RequestParam(value = "path") String dir,
                             ModelMap model, final HttpServletRequest request) throws IOException {
        String mes;
        UploadFileProvider provider = new UploadAbsoluteFileProvider(dir, file.getOriginalFilename());
        provider.write(file.getInputStream());
        mes="&message=message.fileUploaded";
        return "redirect:/server?path=" + dir + mes;
    }

    /**
     * создание папки
     * @param name
     * @param dir
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/directory", method = RequestMethod.POST)
    public String mkdir(@RequestParam(value = "name") String name,
                        @RequestParam(value = "path") String dir,
                        ModelMap model) throws IOException {
        String mes;
        fileSystem.mkdirGlobal(dir, name);
        mes="&message=message.directoryCreated";
        return "redirect:/server?path=" + dir + mes;
    }

    /**
     * Расшаривание файла
     * @param targetUsername
     * @param sharedPath
     * @param principal
     * @param model
     * @return
     */
    @RequestMapping(value = "/share", method = RequestMethod.POST, params = {"username", "path"})
    public String shareFile(@RequestParam(value = "username", required = true) String targetUsername,
                            @RequestParam(value = "path", required = true) String sharedPath,
                            Principal principal, ModelMap model) {
        if (principal != null) {
            String mes;
            sharedPathService.shareGlobalPath(sharedPath, principal.getName(), targetUsername);
            mes="&message=message.shared";
            return "redirect:/server?path=" + sharedPath + mes;
        } else {
            return "redirect:/index";
        }
    }

    @Override
    public String getBaseUrl() {
        return "/server";
    }
}
