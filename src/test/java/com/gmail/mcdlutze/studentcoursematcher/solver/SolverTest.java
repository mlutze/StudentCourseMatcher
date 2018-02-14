package com.gmail.mcdlutze.studentcoursematcher.solver;

import com.gmail.mcdlutze.studentcoursematcher.object.Course;
import com.gmail.mcdlutze.studentcoursematcher.object.Qualification;
import com.gmail.mcdlutze.studentcoursematcher.object.SeatType;
import com.gmail.mcdlutze.studentcoursematcher.object.Student;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public class SolverTest {

    private Solver solver;

    @Before
    public void setup() {
        solver = new Solver(3.5, 3.5, 3.5, 3.5);
    }

    @Test
    public void singleMatchTest() {
        Student student = new Student("student", Collections.emptySet(), Collections.emptyList());
        SeatType seatType = new SeatType("seatType", Collections.emptySet(), Collections.emptySet());
        Course course = new Course("course", Collections.singletonMap(seatType, 1));

        Course expected = course;
        Course actual =
                solver.solve(Collections.singletonList(student), Collections.singletonList(course)).get(student)
                        .getCourse();

        assertEquals(expected, actual);
    }

    @Test
    public void dummyTest() {
        Student student = new Student("student", Collections.emptySet(), Collections.emptyList());
        SeatType seatType = new SeatType("seatType", Collections.emptySet(), Collections.emptySet());
        Course course = new Course("course", Collections.singletonMap(seatType, 2));

        Course expected = course;
        Course actual =
                solver.solve(Collections.singletonList(student), Collections.singletonList(course)).get(student)
                        .getCourse();

        assertEquals(expected, actual);
    }

    @Test
    public void qualificationTest() {
        int numCourses = 1000;
        int goodCourseNum = ThreadLocalRandom.current().nextInt(numCourses);

        Qualification forbiddenQualification = new Qualification();

        SeatType restrictiveSeatType =
                new SeatType("restrictive", Collections.emptySet(), Collections.singleton(forbiddenQualification));
        SeatType nonrestrictiveSeatType =
                new SeatType("nonrestrictive", Collections.emptySet(), Collections.emptySet());

        List<Course> courses = new ArrayList<>();
        Course goodCourse = null;
        for (int i = 0; i < numCourses; i++) {
            String courseId = "course" + i;
            SeatType seatType;
            if (i == goodCourseNum) {
                seatType = nonrestrictiveSeatType;
                Course course = new Course(courseId, Collections.singletonMap(seatType, 1));
                courses.add(course);
                goodCourse = course;
            } else {
                seatType = restrictiveSeatType;
                courses.add(new Course(courseId, Collections.singletonMap(seatType, 1)));
            }
        }

        Student student =
                new Student("student", Collections.singleton(forbiddenQualification),
                        Arrays.asList(courses.get((goodCourseNum + 1) % numCourses), goodCourse));


        Course expected = goodCourse;
        Course actual = solver.solve(Collections.singletonList(student), courses).get(student).getCourse();

        assertEquals(expected, actual);
    }

    @Test
    public void preferencesTest() {
        int numCourses = 1000;
        int goodCourseNum = ThreadLocalRandom.current().nextInt(numCourses);

        SeatType seatType = new SeatType("seatType", Collections.emptySet(), Collections.emptySet());

        List<Course> courses = new ArrayList<>();
        Course goodCourse = null;
        for (int i = 0; i < numCourses; i++) {
            String courseId = "course" + i;
            if (i == goodCourseNum) {
                Course course = new Course(courseId, Collections.singletonMap(seatType, 1));
                courses.add(course);
                goodCourse = course;
            } else {
                courses.add(new Course(courseId, Collections.singletonMap(seatType, 1)));
            }
        }
        Student student = new Student("student", Collections.emptySet(), Collections.singletonList(goodCourse));

        Course expected = goodCourse;
        Course actual = solver.solve(Collections.singletonList(student), courses).get(student).getCourse();

        assertEquals(expected, actual);

    }

}