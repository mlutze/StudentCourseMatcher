package com.gmail.mcdlutze.studentcoursematcher.parser;

import com.gmail.mcdlutze.studentcoursematcher.exception.FileFormatException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class QualificationsFileParser {
    public Map<String, Set<String>> parseQualificationsFile(File file) throws IOException {
        Map<String, Set<String>> students = new HashMap<>();
        try (CSVParser parser = new CSVParser(new FileReader(file), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : parser) {
                String id = record.get(0);

                String idHeader =
                        parser.getHeaderMap().entrySet().stream().filter(e -> e.getValue().equals(0)).findFirst().get()
                                .getKey();
                Map<String, String> recordMap = record.toMap();
                recordMap.remove(idHeader);

                Set<String> qualifications = new HashSet<>();

                for (Map.Entry<String, String> entry : recordMap.entrySet()) {
                    String trueFalse = entry.getValue();
                    if (parseBoolean(trueFalse)) {
                        qualifications.add(entry.getKey());
                    }
                }

                if (students.put(id, qualifications) != null) {
                    throw new FileFormatException("Duplicate entry in qualifications file: " + id);
                }
            }
        }
        return students;
    }

    private boolean parseBoolean(String string) throws FileFormatException {

        if (Pattern.compile("true", Pattern.CASE_INSENSITIVE).matcher(string).matches()) {
            return true;
        } else if (Pattern.compile("false", Pattern.CASE_INSENSITIVE).matcher(string).matches()) {
            return false;
        } else {
            throw new FileFormatException("Non-boolean in qualifications file");
        }
    }
}
