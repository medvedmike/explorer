package com.explorer.controller;

import com.explorer.service.exceptions.DirectoryAlreadyExistsException;
import com.explorer.service.exceptions.DirectoryNotFoundException;
import com.explorer.service.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Michael on 23.07.2014.
 * Интерфейс, содержит в себе дефолтные реализации обработчиков исключений.
 * Путь по которому происходит редирект при возникновении исключения, обработчики получают через метод getBaseUrl(),
 * который переобпределяется в классах наследниках.
 */
public interface ControllerExceptionsHandler {

    /**
     * Метод получения адреса для редиректа
     * @return
     */
    public String getBaseUrl();

    @ExceptionHandler(DirectoryAlreadyExistsException.class)
    public default ModelAndView onDirectoryAlreadyExists(DirectoryAlreadyExistsException ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "error.directoryExists");
        mav.addObject("path", request.getParameter("path"));
        mav.setViewName("redirect:".concat(getBaseUrl()));
        return mav;
    }

    @ExceptionHandler(DirectoryNotFoundException.class)
    public default ModelAndView onDirectoryNotFound(DirectoryNotFoundException ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "error.directoryNotFound");
        mav.setViewName("redirect:".concat(getBaseUrl()));
        return mav;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public default ModelAndView onUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "error.userNotFound");
        mav.addObject("path", request.getParameter("path"));
        mav.setViewName("redirect:".concat(getBaseUrl()));
        return mav;
    }

    @ExceptionHandler(FileAlreadyExistsException.class)
    public default ModelAndView onFileAlreadyExists(FileAlreadyExistsException ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "error.fileExists");
        mav.addObject("path", request.getParameter("path"));
        mav.setViewName("redirect:".concat(getBaseUrl()));
        return mav;
    }

    @ExceptionHandler(java.nio.file.AccessDeniedException.class)
    public default ModelAndView onAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", "error.accessDenied");
        Path p = Paths.get(request.getParameter("path"));
        mav.addObject("path", p == null ? "" :
            p.getParent() == null ? "" : p.getParent().toString());
        mav.setViewName("redirect:".concat(getBaseUrl()));
        return mav;
    }
}
