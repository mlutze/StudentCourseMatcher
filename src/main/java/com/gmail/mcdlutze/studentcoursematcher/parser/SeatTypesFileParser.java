package com.gmail.mcdlutze.studentcoursematcher.parser;

import com.gmail.mcdlutze.studentcoursematcher.exception.FileFormatException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SeatTypesFileParser {
    public Map<String, Map<String, Ternean>> parseSeatTypesFile(File file) throws IOException {
        Map<String, Map<String, Ternean>> seatTypes = new HashMap<>();
        try (CSVParser parser = new CSVParser(new FileReader(file), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : parser) {
                String name = record.get(0);
                Ternean empty = parseTernean(record.get(1));
                if (empty.equals(Ternean.TRUE)) {
                    throw new FileFormatException("True value in empty column of seat types file");
                }


                String idHeader =
                        parser.getHeaderMap().entrySet().stream().filter(e -> e.getValue().equals(0)).findFirst().get()
                                .getKey();
                String emptyHeader =
                        parser.getHeaderMap().entrySet().stream().filter(e -> e.getValue().equals(1)).findFirst().get()
                                .getKey();
                Map<String, String> recordMap = record.toMap();
                recordMap.remove(idHeader);
                recordMap.remove(emptyHeader);

                Map<String, Ternean> qualifications = new HashMap<>();
                qualifications.put("", empty);
                for (Map.Entry<String, String> entry : recordMap.entrySet()) {
                    qualifications.put(entry.getKey(), parseTernean(entry.getValue()));
                }

                if (seatTypes.put(name, qualifications) != null) {
                    throw new FileFormatException("Duplicate entry in seat types file: " + name);
                }
            }
        }

        return seatTypes;
    }

    private Ternean parseTernean(String string) throws FileFormatException {
        if (Pattern.compile("false", Pattern.CASE_INSENSITIVE).matcher(string).matches()) {
            return Ternean.FALSE;
        } else if (Pattern.compile("", Pattern.CASE_INSENSITIVE).matcher(string).matches()) {
            return Ternean.BLANK;
        } else if (Pattern.compile("true", Pattern.CASE_INSENSITIVE).matcher(string).matches()) {
            return Ternean.TRUE;
        } else {
            throw new FileFormatException("Non-boolean in seat types file");
        }
    }
}
