package com.gmail.mcdlutze.studentcoursematcher.manager;

import com.gmail.mcdlutze.studentcoursematcher.exception.FileMismatchException;
import com.gmail.mcdlutze.studentcoursematcher.parser.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FilesManager {

    private final Map<String, Set<String>> qualifications;
    private final Map<String, Map<String, Integer>> courses;
    private final Map<String, List<String>> preferences;
    private final Map<String, Map<String, Ternean>> seatTypes;

    public static Builder builder() {
        return new Builder();
    }

    private FilesManager(File coursesFile, File qualificationsFile, File preferencesFile, File seatTypesFile,
                         boolean allowEmptyPreferences, boolean allowUnknownPreferences)
            throws IOException {

        // parse files
        qualifications = new QualificationsFileParser().parseQualificationsFile(qualificationsFile);
        courses = new CoursesFileParser().parseCoursesFile(coursesFile);
        preferences = new PreferencesFileParser(allowEmptyPreferences).parsePreferencesFile(preferencesFile);
        seatTypes = new SeatTypesFileParser().parseSeatTypesFile(seatTypesFile);

        // ensure qualifications and preferences files have same qualifications
        if (!qualifications.keySet().equals(preferences.keySet())) {
            throw new FileMismatchException("Qualifications file and preferences file have different students");
        }

        // ensure each preference is a course, convert unknown courses to EMPTY
        for (List<String> preferenceList : preferences.values()) {
            for (int i = 0; i < preferenceList.size(); i++) {
                String preference = preferenceList.get(i);
                if (!preference.isEmpty() && !courses.keySet().contains(preference)) {
                    if (allowUnknownPreferences) {
                        preferenceList.set(i, "");
                    } else {
                        throw new FileMismatchException("Preferences file contains unknown course: " + preference);
                    }
                }
            }
        }

        // ensure each course's seat type is a seat type
        for (Map<String, Integer> seatTypeCount : courses.values()) {
            for (String seatType : seatTypeCount.keySet()) {
                if (!seatTypes.keySet().contains(seatType)) {
                    throw new FileMismatchException("Courses file contains unknown seat type: " + seatType);
                }
            }
        }
    }

    public Map<String, Set<String>> getQualifications() {
        return qualifications;
    }

    public Map<String, Map<String, Integer>> getCourses() {
        return courses;
    }

    public Map<String, List<String>> getPreferences() {
        return preferences;
    }

    public Map<String, Map<String, Ternean>> getSeatTypes() {
        return seatTypes;
    }

    public static class Builder {
        private File coursesFile;
        private File qualificationsFile;
        private File preferencesFile;
        private File seatTypesFile;
        private boolean allowEmptyPreferences;
        private boolean allowUnknownPreferences;

        public Builder withCoursesFile(File coursesFile) {
            this.coursesFile = coursesFile;
            return this;
        }

        public Builder withQualificationsFile(File qualificationsFile) {
            this.qualificationsFile = qualificationsFile;
            return this;
        }

        public Builder withPreferencesFile(File preferencesFile) {
            this.preferencesFile = preferencesFile;
            return this;
        }

        public Builder withSeatTypesFile(File seatTypesFile) {
            this.seatTypesFile = seatTypesFile;
            return this;
        }

        public Builder withAllowEmptyPreferences(boolean allowEmptyPreferences) {
            this.allowEmptyPreferences = allowEmptyPreferences;
            return this;
        }

        public Builder withAllowUnknownPreferences(boolean allowUnknownPreferences) {
            this.allowUnknownPreferences = allowUnknownPreferences;
            return this;
        }

        public FilesManager build() throws IOException {
            return new FilesManager(coursesFile, qualificationsFile, preferencesFile, seatTypesFile,
                    allowEmptyPreferences, allowUnknownPreferences);
        }
    }
}
