package com.gmail.mcdlutze.studentcoursematcher.parser;

import com.gmail.mcdlutze.studentcoursematcher.exception.FileFormatException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PreferencesFileParser {
    private final boolean allowEmptyPreferences;

    public PreferencesFileParser(boolean allowEmptyPreferences) {
        this.allowEmptyPreferences = allowEmptyPreferences;
    }

    public Map<String, List<String>> parsePreferencesFile(File file) throws IOException {
        Map<String, List<String>> students = new HashMap<>();
        try (CSVParser parser = new CSVParser(new FileReader(file), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : parser) {
                String id = record.get(0);

                List<String> preferences = new ArrayList<>(record.size() - 1);
                Set<String> preferenceSet = new HashSet<>();
                for (int i = 1; i < record.size(); i++) {
                    String preference = record.get(i);
                    if (preference.isEmpty() && !allowEmptyPreferences) {
                        throw new FileFormatException("Empty preference in preferences file: " + id);
                    }
                    if (!preferenceSet.add(preference) && !preference.isEmpty()) {
                        throw new FileFormatException(
                                "Duplicate preference in preferences file: " + id + " " + preference);
                    }
                    preferences.add(record.get(i));
                }

                if (students.put(id, preferences) != null) {
                    throw new FileFormatException("Duplicate entry in preference file: " + id);
                }

                preferences.sort(Comparator.comparing(String::isEmpty).reversed());
            }
        }

        return students;
    }
}
