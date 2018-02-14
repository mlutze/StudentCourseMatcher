package com.gmail.mcdlutze.studentcoursematcher.manager;

import com.gmail.mcdlutze.studentcoursematcher.object.Student;
import com.gmail.mcdlutze.studentcoursematcher.parser.Ternean;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StudentsManagerTest {

    private StudentsManager studentsManager;
    private SeatTypesManager seatTypesManager;
    private CoursesManager coursesManager;
    private QualificationsManager qualificationsManager;

    @Before
    public void setup() {
        qualificationsManager = new QualificationsManager();
    }

    @Test
    public void singleStudentTest() {
        String courseName = "course";
        String studentName = "student";
        String qualificationName = "qual";
        String seatTypeName = "type";

        Map<String, List<String>> preferences =
                Collections.singletonMap(studentName, Collections.singletonList(courseName));
        Map<String, Set<String>> qualifications =
                Collections.singletonMap(studentName, Collections.singleton(qualificationName));
        Map<String, Map<String, Integer>> courses =
                Collections.singletonMap(courseName, Collections.singletonMap(seatTypeName, 1));
        Map<String, Map<String, Ternean>> seatTypes =
                Collections.singletonMap(seatTypeName, Collections.singletonMap(qualificationName, Ternean.TRUE));

        seatTypesManager = new SeatTypesManager(seatTypes, qualificationsManager);
        coursesManager = new CoursesManager(courses, seatTypesManager);
        studentsManager = new StudentsManager(qualifications, preferences, qualificationsManager, coursesManager);

        Student actual = studentsManager.getStudent(studentName);

        assertEquals(coursesManager.getCourse(courseName), actual.getPreferences().get(0));
        assertTrue(actual.hasQualification(qualificationsManager.getQualification(qualificationName)));
        assertEquals(studentName, actual.getId());
    }

}