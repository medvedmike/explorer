package com.explorer.domain.fs.dataprovider.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileNotFoundException;

/**
 * Created by Michael on 15.07.2014.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "File not found on this server")
public class FileNotFoundOnServerException extends FileNotFoundException {
}
