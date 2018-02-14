package com.gmail.mcdlutze.studentcoursematcher.parser;

import java.io.File;


public abstract class AbstractParserTest {

    protected File getFile(String fileName) {
        return new File(getClass().getClassLoader().getResource(fileName).getFile());
    }
}
