package com.explorer.controller;

import com.explorer.domain.fs.dataprovider.DownloadFileProvider;
import com.explorer.domain.fs.dataprovider.UploadFileProvider;
import com.explorer.service.FileSystemService;
import com.explorer.service.FilesService;
import com.explorer.service.PermissionManager;
import com.explorer.service.SharedPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
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
 * Управление файлами в домашних каталогах пользователей
 * Контроллер обрабатывает просмотр, загрузку файлов, скачивание файлов и создание дирректорий в домашнем каталоге пользователя
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController implements ControllerExceptionsHandler {

    @Autowired
    private FileSystemService fileSystem;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private SharedPathService sharedPathService;

    @Autowired
    private FilesService filesService;

    @Autowired
    private PermissionManager permissionManager;


    /**
     * Отображение файлов из домашней дирректории пользователя
     * Метод обрабатывает запросы на просмотр файлов в папках по относительному пути (отностельно
     * домашнего каталога пользователя)
     * @param path относительный путь к папке
     * @param model
     * @param principal
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showDirectory(@RequestParam(value = "path", defaultValue = "") String path,
                              ModelMap model, Principal principal) throws IOException {
        if (principal != null) {
            model.put("url", "home");
            model.put("directory", fileSystem.getDirectoryHome(path, principal.getName()));
            return "files";
        } else
            return "redirect:/index";
    }

    /**
     * Загрузка файла из каталога пользователя
     * @param name имя файла
     * @param request
     * @param response
     * @param principal
     * @throws IOException
     */
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public void downloadFile(@RequestParam(value = "name", required = true) String name,
                             final HttpServletRequest request, final HttpServletResponse response,
                             Principal principal) throws IOException {
        if (principal != null) {
            DownloadFileProvider provider = filesService.getDownloadHomeFileProvider(name, principal.getName());
            String mimeType = request.getSession().getServletContext().getMimeType(name);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setContentLength((int) provider.getSize());
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", provider.getName()));
            provider.copy(response.getOutputStream());
        } else
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * Отправка файла на сервер
     * @param file файл
     * @param dir относительный путь к папке назначения (относительно каталога текущего пользователя)
     * @param model
     * @param request
     * @param principal
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String uploadFile(@RequestParam(value = "file") MultipartFile file,
                             @RequestParam(value = "path") String dir,
                             ModelMap model, final HttpServletRequest request,
                             Principal principal) throws IOException {
        if (principal != null) {
            String mes;
            UploadFileProvider provider = filesService.getUploadHomeFileProvider(dir,
                    principal.getName(), file.getOriginalFilename());
            provider.write(file.getInputStream(), permissionManager.getDefaultFile());
            mes="&message=message.fileUploaded";
            return "redirect:/home?path=" + dir + mes;
        } else
            return "redirect:/index";
    }

    /**
     * Предоставление доступа к дирректории назодящейся в каталоге пользователя
     * @param targetUsername имя целевого пользователя
     * @param sharedPath путь к папке
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
            sharedPathService.shareHomePath(sharedPath, principal.getName(), targetUsername);
            mes="&message=message.shared";
            return "redirect:/home?path=" + sharedPath + mes;
        } else
            return "redirect:/index";
    }

    /**
     * Создание папки в каталоге пользователя
     * @param name имя новой папки
     * @param dir целевая папка
     * @param principal
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/directory", method = RequestMethod.POST)
    public String mkdir(@RequestParam(value = "name") String name,
                        @RequestParam(value = "path") String dir,
                        Principal principal) throws IOException {
        String mes;
        String username = principal.getName();
        fileSystem.mkdirHome(dir, username, name);
        mes="&message=message.directoryCreated";
        return "redirect:/home?path=" + dir + mes;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET, params = {"path"})
    public String delete(@RequestParam(value = "path") String path,
                         @RequestParam(value = "current", defaultValue = "") String current,
                         Principal principal) throws IOException {
        if (fileSystem.deleteHome(path, principal.getName())) {
            return "redirect:/home?path=" + current + "&message=message.deleted";
        } else {
            return "redirect:/home?path=" + current + "&error=error.delete";
        }
    }

    @Override
    public String getBaseUrl() {
        return "/home";
    }
}
