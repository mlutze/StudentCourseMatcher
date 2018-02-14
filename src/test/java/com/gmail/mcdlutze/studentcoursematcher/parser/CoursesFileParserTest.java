package com.gmail.mcdlutze.studentcoursematcher.parser;

import com.gmail.mcdlutze.studentcoursematcher.exception.FileFormatException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CoursesFileParserTest extends AbstractParserTest {

    private CoursesFileParser parser;

    @Before
    public void setup() {
        parser = new CoursesFileParser();
    }

    @Test
    public void singleCourseTest() throws IOException {
        File file = getFile("parser/CoursesFileParserTest/singleCourse.csv");
        Map<String, Map<String, Integer>> courses = parser.parseCoursesFile(file);

        assertEquals(1, courses.size());

        Map<String, Integer> expectedValue = new HashMap<>();
        expectedValue.put("type1", 4);
        expectedValue.put("type2", 5);
        expectedValue.put("type3", 6);

        Map.Entry<String, Map<String, Integer>> expected = new AbstractMap.SimpleEntry<>("1234", expectedValue);
        Map.Entry<String, Map<String, Integer>> actual = courses.entrySet().stream().findFirst().get();

        assertEquals(expected, actual);

    }

    @Test(expected = FileFormatException.class)
    public void nonIntegerValueTest() throws IOException {
        File file = getFile("parser/CoursesFileParserTest/nonIntegerValue.csv");
        parser.parseCoursesFile(file);
    }

    @Test(expected = FileFormatException.class)
    public void negativeValueTest() throws IOException {
        File file = getFile("parser/CoursesFileParserTest/negativeValue.csv");
        parser.parseCoursesFile(file);
    }

    @Test(expected = FileFormatException.class)
    public void duplicateEntryTest() throws IOException {
        File file = getFile("parser/CoursesFileParserTest/duplicateEntry.csv");
        parser.parseCoursesFile(file);
    }
}
