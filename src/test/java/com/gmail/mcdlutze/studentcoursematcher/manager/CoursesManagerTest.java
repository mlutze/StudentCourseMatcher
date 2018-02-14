package com.gmail.mcdlutze.studentcoursematcher.manager;

import com.gmail.mcdlutze.studentcoursematcher.object.Course;
import com.gmail.mcdlutze.studentcoursematcher.parser.Ternean;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class CoursesManagerTest {

    private CoursesManager coursesManager;
    private SeatTypesManager seatTypesManager;
    private QualificationsManager qualificationsManager;

    @Before
    public void setup() {
        qualificationsManager = new QualificationsManager();
    }

    @Test
    public void singleCourseTest() {
        String qualificationName = "qual";
        String seatTypeName = "type";
        String courseName = "course";
        seatTypesManager = new SeatTypesManager(
                Collections.singletonMap(seatTypeName, Collections.singletonMap(qualificationName, Ternean.TRUE)),
                qualificationsManager);
        coursesManager =
                new CoursesManager(Collections.singletonMap(courseName, Collections.singletonMap(seatTypeName, 1)),
                        seatTypesManager);

        Course actual = coursesManager.getCourse(courseName);
        assertEquals(1, actual.getSeatTypes().size());
        assertEquals(1, (int) actual.getSeatTypes().get(seatTypesManager.getSeatType(seatTypeName)));

    }

    @Test
    public void emptyCourseTest() {
        seatTypesManager = new SeatTypesManager(Collections.emptyMap(), qualificationsManager);
        coursesManager = new CoursesManager(Collections.emptyMap(), seatTypesManager);
        assertEquals(Course.EMPTY, coursesManager.getCourse(""));
    }
}
