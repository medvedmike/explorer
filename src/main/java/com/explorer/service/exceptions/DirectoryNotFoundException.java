package com.explorer.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

/**
 * Created by Michael on 11.07.2014.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Directory not found on server")
public class DirectoryNotFoundException extends IOException {
}
