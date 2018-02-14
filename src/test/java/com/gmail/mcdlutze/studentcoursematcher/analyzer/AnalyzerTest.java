package com.gmail.mcdlutze.studentcoursematcher.analyzer;

import com.gmail.mcdlutze.studentcoursematcher.object.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class AnalyzerTest {

    private Analyzer analyzer;

    @Before
    public void setup() {
        analyzer = new Analyzer();
    }

    @Test
    public void singleMatchTest() {
        SeatType seatType = new SeatType("seatType", Collections.emptySet(), Collections.emptySet());
        Course course = new Course("course", Collections.singletonMap(seatType, 1));
        Student student = new Student("student", Collections.emptySet(), Collections.singletonList(course));
        Seat seat = new Seat(seatType, course);


        Analyzer.Analysis analysis = analyzer.analyze(Collections.singletonMap(student, seat));
        assertEquals(0, analysis.getNonPreferredCount());
        assertEquals(Collections.singletonList(1), analysis.getPreferenceCounts());
        assertEquals(0, analysis.getIllegalAssignments());

    }

    @Test
    public void nonPreferredTest() {
        Student student = new Student("student", Collections.emptySet(), Collections.emptyList());
        SeatType seatType = new SeatType("seatType", Collections.emptySet(), Collections.emptySet());
        Course course = new Course("course", Collections.singletonMap(seatType, 1));
        Seat seat = new Seat(seatType, course);

        Analyzer.Analysis analysis = analyzer.analyze(Collections.singletonMap(student, seat));
        assertEquals(1, analysis.getNonPreferredCount());
        assertEquals(Collections.emptyList(), analysis.getPreferenceCounts());
        assertEquals(0, analysis.getIllegalAssignments());
    }

    @Test
    public void illegalAssignmentTest() {
        SeatType seatType = new SeatType("seatType", Collections.singleton(new Qualification()), Collections.emptySet());
        Course course = new Course("course", Collections.singletonMap(seatType, 1));
        Student student = new Student("student", Collections.emptySet(), Collections.singletonList(course));
        Seat seat = new Seat(seatType, course);

        Analyzer.Analysis analysis = analyzer.analyze(Collections.singletonMap(student, seat));
        assertEquals(0, analysis.getNonPreferredCount());
        assertEquals(Collections.singletonList(1), analysis.getPreferenceCounts());
        assertEquals(1, analysis.getIllegalAssignments());
    }



}