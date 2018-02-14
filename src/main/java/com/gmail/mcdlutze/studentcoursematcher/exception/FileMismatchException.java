package com.gmail.mcdlutze.studentcoursematcher.exception;

import java.io.IOException;

public class FileMismatchException extends IOException {
    public FileMismatchException(String message) {
        super(message);
    }
}
