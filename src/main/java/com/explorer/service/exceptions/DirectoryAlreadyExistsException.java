package com.explorer.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Michael on 21.07.2014.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Directory with this name is already exists on a server")
public class DirectoryAlreadyExistsException extends RuntimeException {
}
