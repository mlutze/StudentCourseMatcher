package com.gmail.mcdlutze.studentcoursematcher.manager;

import com.gmail.mcdlutze.studentcoursematcher.exception.FileMismatchException;
import com.gmail.mcdlutze.studentcoursematcher.parser.AbstractParserTest;
import com.gmail.mcdlutze.studentcoursematcher.parser.Ternean;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class FilesManagerTest extends AbstractParserTest {

    private FilesManager filesManager;

    @Test
    public void singleMatchTest() throws IOException {
        File coursesFile = getFile("MainTest/singleMatchTest/courses.csv");
        File qualificationsFile = getFile("MainTest/singleMatchTest/qualifications.csv");
        File preferencesFile = getFile("MainTest/singleMatchTest/preferences.csv");
        File seatTypesFile = getFile("MainTest/singleMatchTest/seatTypes.csv");

        filesManager =
                FilesManager.builder().withSeatTypesFile(seatTypesFile).withQualificationsFile(qualificationsFile)
                        .withPreferencesFile(preferencesFile).withCoursesFile(coursesFile).build();


        Map<String, Map<String, Integer>> expectedCourses =
                Collections.singletonMap("course0", Collections.singletonMap("type0", 1));
        Map<String, Map<String, Integer>> actualCourses = filesManager.getCourses();
        assertEquals(expectedCourses, actualCourses);

        Map<String, Set<String>> expectedQualifications =
                Collections.singletonMap("student0", Collections.singleton("required"));
        Map<String, Set<String>> actualQualifications = filesManager.getQualifications();
        assertEquals(expectedQualifications, actualQualifications);


        Map<String, List<String>> expectedPreferences =
                Collections.singletonMap("student0", Collections.singletonList("course0"));
        Map<String, List<String>> actualPreferences = filesManager.getPreferences();
        assertEquals(expectedPreferences, actualPreferences);

        Map<String, Ternean> qualifications = new HashMap<>();
        qualifications.put("", Ternean.FALSE);
        qualifications.put("required", Ternean.TRUE);
        Map<String, Map<String, Ternean>> expectedSeatTypes = Collections.singletonMap("type0", qualifications);
        Map<String, Map<String, Ternean>> actualSeatTypes = filesManager.getSeatTypes();
        assertEquals(expectedSeatTypes, actualSeatTypes);

    }

    @Test(expected = FileMismatchException.class)
    public void unknownCourseForbiddenTest() throws IOException {
        File coursesFile = getFile("manager/FilesManagerTest/unknownCourseTest/courses.csv");
        File qualificationsFile = getFile("manager/FilesManagerTest/unknownCourseTest/qualifications.csv");
        File preferencesFile = getFile("manager/FilesManagerTest/unknownCourseTest/preferences.csv");
        File seatTypesFile = getFile("manager/FilesManagerTest/unknownCourseTest/seatTypes.csv");

        filesManager =
                FilesManager.builder().withSeatTypesFile(seatTypesFile).withQualificationsFile(qualificationsFile)
                        .withPreferencesFile(preferencesFile).withCoursesFile(coursesFile).build();
    }

    @Test
    public void unknownCourseAllowedTest() throws IOException {
        File coursesFile = getFile("manager/FilesManagerTest/unknownCourseTest/courses.csv");
        File qualificationsFile = getFile("manager/FilesManagerTest/unknownCourseTest/qualifications.csv");
        File preferencesFile = getFile("manager/FilesManagerTest/unknownCourseTest/preferences.csv");
        File seatTypesFile = getFile("manager/FilesManagerTest/unknownCourseTest/seatTypes.csv");

        filesManager =
                FilesManager.builder().withSeatTypesFile(seatTypesFile).withQualificationsFile(qualificationsFile)
                        .withPreferencesFile(preferencesFile).withCoursesFile(coursesFile)
                        .withAllowUnknownPreferences(true).build();
        assertEquals(Collections.singletonList(""), filesManager.getPreferences().get("student0"));
    }


    @Test(expected = FileMismatchException.class)
    public void differentStudentsTest() throws IOException {
        File coursesFile = getFile("manager/FilesManagerTest/differentStudentsTest/courses.csv");
        File qualificationsFile = getFile("manager/FilesManagerTest/differentStudentsTest/qualifications.csv");
        File preferencesFile = getFile("manager/FilesManagerTest/differentStudentsTest/preferences.csv");
        File seatTypesFile = getFile("manager/FilesManagerTest/differentStudentsTest/seatTypes.csv");

        filesManager =
                FilesManager.builder().withSeatTypesFile(seatTypesFile).withQualificationsFile(qualificationsFile)
                        .withPreferencesFile(preferencesFile).withCoursesFile(coursesFile).build();
    }

    @Test(expected = FileMismatchException.class)
    public void unknownSeatTypeTest() throws IOException {
        File coursesFile = getFile("manager/FilesManagerTest/unknownSeatTypeTest/courses.csv");
        File qualificationsFile = getFile("manager/FilesManagerTest/unknownSeatTypeTest/qualifications.csv");
        File preferencesFile = getFile("manager/FilesManagerTest/unknownSeatTypeTest/preferences.csv");
        File seatTypesFile = getFile("manager/FilesManagerTest/unknownSeatTypeTest/seatTypes.csv");

        filesManager =
                FilesManager.builder().withSeatTypesFile(seatTypesFile).withQualificationsFile(qualificationsFile)
                        .withPreferencesFile(preferencesFile).withCoursesFile(coursesFile).build();
    }

}