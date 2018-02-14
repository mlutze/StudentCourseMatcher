package com.gmail.mcdlutze.studentcoursematcher.object;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SeatTypeTest {

    @Test
    public void noQualificationsTest() {
        Student student = new Student("student", Collections.emptySet(), Collections.emptyList());
        SeatType seatType = new SeatType("seatType", Collections.emptySet(), Collections.emptySet());
        assertTrue(seatType.isTypeOfStudent(student));
    }

    @Test
    public void hasForbiddenTest() {
        Qualification qualification = new Qualification();
        Student student = new Student("student", Collections.singleton(qualification), Collections.emptyList());
        SeatType seatType =
                new SeatType("seatType", Collections.emptySet(), Collections.singleton(qualification));
        assertFalse(seatType.isTypeOfStudent(student));
    }

    @Test
    public void hasNotRequiredTest() {
        Qualification qualification = new Qualification();
        Student student = new Student("student", Collections.emptySet(), Collections.emptyList());
        SeatType seatType =
                new SeatType("seatType", Collections.singleton(qualification), Collections.emptySet());
        assertFalse(seatType.isTypeOfStudent(student));
    }

    @Test
    public void hasRequiredTest() {
        Qualification required = new Qualification();
        Qualification forbidden = new Qualification();
        Student student = new Student("student", Collections.singleton(required), Collections.emptyList());
        SeatType seatType =
                new SeatType("seatType", Collections.singleton(required), Collections.singleton(forbidden));
        assertTrue(seatType.isTypeOfStudent(student));
    }
}
