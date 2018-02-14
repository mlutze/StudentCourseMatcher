package com.gmail.mcdlutze.studentcoursematcher.parser;

import com.gmail.mcdlutze.studentcoursematcher.exception.FileFormatException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PreferencesFileParserTest extends AbstractParserTest {

    public PreferencesFileParser parser;

    @Test
    public void singleStudentTest() throws IOException {
        parser = new PreferencesFileParser(false);
        File file = getFile("parser/PreferencesFileParserTest/singleStudent.csv");
        Map<String, List<String>> students = parser.parsePreferencesFile(file);

        assertEquals(1, students.size());

        Map.Entry<String, List<String>> expected =
                new AbstractMap.SimpleEntry<>("1234", Arrays.asList("111", "222", "333"));
        Map.Entry<String, List<String>> actual = students.entrySet().stream().findFirst().get();

        assertEquals(expected, actual);

    }

    @Test
    public void multipleStudentTest() throws IOException {
        parser = new PreferencesFileParser(false);
        File file = getFile("parser/PreferencesFileParserTest/multipleStudents.csv");
        Map<String, List<String>> students = parser.parsePreferencesFile(file);

        assertEquals(3, students.size());
    }

    @Test(expected = FileFormatException.class)
    public void duplicateEntryTest() throws IOException {
        parser = new PreferencesFileParser(false);
        File file = getFile("parser/PreferencesFileParserTest/duplicateEntry.csv");
        parser.parsePreferencesFile(file);
    }

    @Test(expected = FileFormatException.class)
    public void duplicatePreferenceTest() throws IOException {
        parser = new PreferencesFileParser(false);
        File file = getFile("parser/PreferencesFileParserTest/duplicatePreference.csv");
        parser.parsePreferencesFile(file);
    }

    @Test(expected = FileFormatException.class)
    public void emptyPreferenceForbiddenTest() throws IOException {
        parser = new PreferencesFileParser(false);
        File file = getFile("parser/PreferencesFileParserTest/emptyPreference.csv");
        parser.parsePreferencesFile(file);
    }

    @Test
    public void emptyPreferenceAllowedTest() throws IOException {
        parser = new PreferencesFileParser(true);
        File file = getFile("parser/PreferencesFileParserTest/emptyPreference.csv");
        parser.parsePreferencesFile(file);
    }

}
