package com.gmail.mcdlutze.studentcoursematcher.parser;

import com.gmail.mcdlutze.studentcoursematcher.exception.FileFormatException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class QualificationsFileParserTest extends AbstractParserTest {

    private QualificationsFileParser parser;

    @Before
    public void setup() {
        parser = new QualificationsFileParser();
    }

    @Test
    public void singleStudentTest() throws IOException {
        File file = getFile("parser/QualificationsFileParserTest/singleStudent.csv");
        Map<String, Set<String>> students = parser.parseQualificationsFile(file);

        assertEquals(1, students.size());

        Map.Entry<String, Set<String>> expected = new AbstractMap.SimpleEntry<>("1234", Collections.singleton("qual3"));
        Map.Entry<String, Set<String>> actual = students.entrySet().stream().findFirst().get();
        assertEquals(expected, actual);
    }

    @Test(expected = FileFormatException.class)
    public void invalidValueTest() throws IOException {
        File file = getFile("parser/QualificationsFileParserTest/invalidValue.csv");
        parser.parseQualificationsFile(file);
    }

    @Test
    public void multipleStudentTest() throws IOException {
        File file = getFile("parser/QualificationsFileParserTest/multipleStudents.csv");
        Map<String, Set<String>> students = parser.parseQualificationsFile(file);

        assertEquals(3, students.size());

    }

    @Test(expected = FileFormatException.class)
    public void duplicateEntryTest() throws IOException {
        File file = getFile("parser/QualificationsFileParserTest/duplicateEntry.csv");
        parser.parseQualificationsFile(file);
    }

}
