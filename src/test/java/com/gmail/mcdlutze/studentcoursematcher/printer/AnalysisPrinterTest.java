package com.gmail.mcdlutze.studentcoursematcher.printer;

import com.gmail.mcdlutze.studentcoursematcher.analyzer.Analyzer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AnalysisPrinterTest {

    private AnalysisPrinter printer;

    private PrintStream stdout;
    private ByteArrayOutputStream capturedOut;


    @Before
    public void setup() {
        printer = new AnalysisPrinter();

        stdout = System.out;
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));
    }

    @After
    public void teardown() {
        System.setOut(stdout);
    }

    @Test
    public void printerTest() {
        Analyzer.Analysis analysis = new Analyzer.Analysis(Arrays.asList(1, 2), 3, 4);
        String[] expected = {
                "#1 choice: 1",
                "#2 choice: 2",
                "Non-preferences: 3",
                "Illegal assignments: 4"
        };

        printer.printAnalysis(analysis);
        assertArrayEquals(expected, capturedOut.toString().split("\r?\n"));

    }

}