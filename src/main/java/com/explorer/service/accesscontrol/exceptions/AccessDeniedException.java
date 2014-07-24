package com.explorer.service.accesscontrol.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Michael on 15.07.2014.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Access denied")
public class AccessDeniedException extends RuntimeException {
}
