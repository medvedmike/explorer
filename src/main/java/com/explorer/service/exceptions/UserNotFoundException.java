package com.explorer.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Michael on 16.07.2014.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Requested user not found on server")
public class UserNotFoundException extends Exception {
}
