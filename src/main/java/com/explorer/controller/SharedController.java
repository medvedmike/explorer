package com.explorer.controller;

import com.explorer.domain.fs.accesscontrol.exceptions.UnauthorizedException;
import com.explorer.domain.fs.dataprovider.DownloadAbsoluteFileProvider;
import com.explorer.domain.fs.dataprovider.DownloadFileProvider;
import com.explorer.domain.fs.dataprovider.UploadAbsoluteFileProvider;
import com.explorer.domain.fs.dataprovider.UploadFileProvider;
import com.explorer.service.FileSystemService;
import com.explorer.service.SharedPathService;
import com.explorer.service.exceptions.DirectoryAlreadyExistsException;
import com.explorer.service.exceptions.DirectoryNotFoundException;
import com.explorer.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.security.Principal;

/**
 * Created by Michael on 15.07.2014.
 * Управление файлами в общих папках
 */
@Controller
@RequestMapping("/shared")
public class SharedController implements ControllerExceptionsHandler {

    @Autowired
    private FileSystemService fileSystem;

    @Autowired
    private SharedPathService sharedPathService;

    @Autowired
    private ApplicationContext context;

    /**
     * Просмотр списка доступных путей для данного пользователя
     * @param model
     * @param principal
     * @return
     * @throws IOException
     */
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

    /**
     * Просмотр файлов в расшареной папке
     * @param path
     * @param model
     * @param principal
     * @return
     * @throws IOException
     */
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

    /**
     * Загрузка файла с сервера
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
     * Загрузка файла на сервер
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
        return "redirect:/shared?path=" + dir + mes;
    }

    /**
     * расшаривание файла
     * @param targetUsername
     * @param sharedPath
     * @param principal
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/share", method = RequestMethod.POST, params = {"username", "path"})
    public String shareFile(@RequestParam(value = "username", required = true) String targetUsername,
                            @RequestParam(value = "path", required = true) String sharedPath,
                            Principal principal, ModelMap model) throws IOException {
        if (principal != null) {
            String mes;
            sharedPathService.sharePath(principal.getName(), targetUsername, sharedPath);
            mes="&message=message.shared";
            return "redirect:/shared?path=" + sharedPath + mes;
        } else {
            return "redirect:/index";
        }
    }

    /**
     * Создание директории
     * @param name
     * @param dir
     * @param principal
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/directory", method = RequestMethod.POST)
    public String mkdir(@RequestParam(value = "name") String name,
                        @RequestParam(value = "path") String dir,
                        Principal principal) throws IOException {
        String mes;
        fileSystem.mkdirShared(dir, name, principal.getName());
        mes="&message=message.directoryCreated";
        return "redirect:/shared?path=" + dir + mes;
    }

    /**
     * отдает список папок которые расшарил текущий пользователь
     * @param principal
     * @param model
     * @return
     */
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public String controlShared(Principal principal, ModelMap model) {
        if (principal == null) throw new UnauthorizedException();
        model.put("paths", sharedPathService.getPathsBySourceUsername(principal.getName()));
        return "my-shared";
    }

    /**
     * "отмена" расшаривания
     * @param id
     * @param principal
     * @param model
     * @return
     */
    @RequestMapping(value = "/my/{id}", method = RequestMethod.GET, params = {"del"})
    public String controlShared(@PathVariable("id") Integer id,
                                Principal principal, ModelMap model) {
        sharedPathService.deletePath(id);
        model.put("paths", sharedPathService.getPathsBySourceUsername(principal.getName()));
        return "my-shared";
    }

    @Override
    public String getBaseUrl() {
        return "/shared";
    }
}
