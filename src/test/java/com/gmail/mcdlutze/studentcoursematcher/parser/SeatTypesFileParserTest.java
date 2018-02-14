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

public class SeatTypesFileParserTest extends AbstractParserTest {

    private SeatTypesFileParser parser;

    @Before
    public void setup() {
        parser = new SeatTypesFileParser();
    }

    @Test
    public void singleSeatTypeTest() throws IOException {
        File file = getFile("parser/SeatTypesFileParserTest/singleSeatType.csv");
        Map<String, Map<String, Ternean>> seatTypes = parser.parseSeatTypesFile(file);

        assertEquals(1, seatTypes.size());

        Map<String, Ternean> expectedValue = new HashMap<>();
        expectedValue.put("", Ternean.FALSE);
        expectedValue.put("qual1", Ternean.BLANK);
        expectedValue.put("qual2", Ternean.FALSE);

        Map.Entry<String, Map<String, Ternean>> expected = new AbstractMap.SimpleEntry<>("abcd", expectedValue);
        Map.Entry<String, Map<String, Ternean>> actual = seatTypes.entrySet().stream().findFirst().get();

        assertEquals(expected, actual);
    }

    @Test(expected = FileFormatException.class)
    public void trueEmptyTest() throws IOException {
        File file = getFile("parser/SeatTypesFileParserTest/trueEmpty.csv");
        parser.parseSeatTypesFile(file);
    }

    @Test(expected = FileFormatException.class)
    public void invalidValueTest() throws IOException {
        File file = getFile("parser/SeatTypesFileParserTest/invalidValue.csv");
        parser.parseSeatTypesFile(file);
    }

    @Test(expected = FileFormatException.class)
    public void duplicateEntryTest() throws IOException {
        File file = getFile("parser/SeatTypesFileParserTest/duplicateEntry.csv");
        parser.parseSeatTypesFile(file);
    }
}
