package com.gmail.mcdlutze.studentcoursematcher.exception;

import java.io.IOException;

public class FileFormatException extends IOException {

    public FileFormatException(String message) {
        super(message);
    }

    public FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
