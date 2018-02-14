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

public class CoursesFileParser {
    public Map<String, Map<String, Integer>> parseCoursesFile(File file) throws IOException {
        Map<String, Map<String, Integer>> courses = new HashMap<>();
        try (CSVParser parser = new CSVParser(new FileReader(file), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : parser) {
                String id = record.get(0);

                String idHeader =
                        parser.getHeaderMap().entrySet().stream().filter(e -> e.getValue().equals(0)).findFirst().get()
                                .getKey();
                Map<String, String> recordMap = record.toMap();
                recordMap.remove(idHeader);


                Map<String, Integer> seatTypes = new HashMap<>();
                for (Map.Entry<String, String> entry : recordMap.entrySet()) {
                    int count;
                    try {
                        count = Integer.parseInt(entry.getValue());
                    } catch (NumberFormatException e) {
                        throw new FileFormatException("Non-integer in courses file", e);
                    }
                    if (count < 0) {
                        throw new FileFormatException("Negative number in courses file");
                    }
                    seatTypes.put(entry.getKey(), count);
                }

                if (courses.put(id, seatTypes) != null) {
                    throw new FileFormatException("Duplicate entry in courses file: " + id);
                }
            }
        }
        return courses;
    }
}
