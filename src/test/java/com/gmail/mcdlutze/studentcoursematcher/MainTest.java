package com.gmail.mcdlutze.studentcoursematcher;

import com.gmail.mcdlutze.studentcoursematcher.parser.AbstractParserTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


public class MainTest extends AbstractParserTest {

    private TemporaryFolder temporaryFolder;
    private PrintStream stdout;
    private PrintStream stderr;
    private ByteArrayOutputStream capturedOut;
    private ByteArrayOutputStream capturedErr;

    @Before
    public void setup() throws IOException {
        temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();

        stdout = System.out;
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));

        stderr = System.err;
        capturedErr = new ByteArrayOutputStream();
        System.setErr(new PrintStream(capturedErr));
    }

    @After
    public void teardown() {
        System.setOut(stdout);
        System.setErr(stderr);
    }

    @Test
    public void singleMatchTest() throws IOException {
        File coursesFile = getFile("MainTest/singleMatchTest/courses.csv");
        File qualifications = getFile("MainTest/singleMatchTest/qualifications.csv");
        File preferencesFile = getFile("MainTest/singleMatchTest/preferences.csv");
        File seatTypesFile = getFile("MainTest/singleMatchTest/seatTypes.csv");
        File outputFile = temporaryFolder.newFile();

        String[] commands = {
                "-c", coursesFile.getPath(),
                "-q", qualifications.getPath(),
                "-p", preferencesFile.getPath(),
                "-s", seatTypesFile.getPath(),
                "-o", outputFile.getPath(),
                "-a", "3.5",
        };

        Main.main(commands);

        List<String> expected = Collections.singletonList("student0,course0");
        List<String> actual = Files.readAllLines(outputFile.toPath());
        assertEquals(expected, actual);

        String[] expectedOut = {
                "#1 choice: 1",
                "Non-preferences: 0",
                "Illegal assignments: 0",
        };
        assertArrayEquals(expectedOut, capturedOut.toString().split("\r?\n"));
    }

    @Test
    public void integrationTest() throws IOException {
        File coursesFile = getFile("MainTest/integrationTest/courses.csv");
        File qualifications = getFile("MainTest/integrationTest/qualifications.csv");
        File preferencesFile = getFile("MainTest/integrationTest/preferences.csv");
        File seatTypesFile = getFile("MainTest/integrationTest/seatTypes.csv");
        File outputFile = temporaryFolder.newFile();

        String[] commands = {
                "-c", coursesFile.getPath(),
                "-q", qualifications.getPath(),
                "-p", preferencesFile.getPath(),
                "-s", seatTypesFile.getPath(),
                "-o", outputFile.getPath(),
                "-a", "3.5",
                "-u",
        };

        Main.main(commands);

        String[] expectedOut = {
                "#1 choice: 275",
                "#2 choice: 38",
                "Non-preferences: 0",
                "Illegal assignments: 0",
        };
        assertArrayEquals(expectedOut, capturedOut.toString().split("\r?\n"));

    }

    @Test
    public void helpTest() {
        String[] commands = {"-h"};
        Main.main(commands);

        assertFalse(capturedOut.toString().isEmpty());
        assertTrue(capturedErr.toString().isEmpty());
    }

    @Test
    public void illegalArgumentTest() {
        String[] commands = {"-illegal"};
        Main.main(commands);

        assertFalse(capturedErr.toString().isEmpty());
        assertTrue(capturedOut.toString().isEmpty());
    }

}