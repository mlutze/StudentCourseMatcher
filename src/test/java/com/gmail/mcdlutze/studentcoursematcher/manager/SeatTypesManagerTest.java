package com.gmail.mcdlutze.studentcoursematcher.manager;

import com.gmail.mcdlutze.studentcoursematcher.object.SeatType;
import com.gmail.mcdlutze.studentcoursematcher.parser.Ternean;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SeatTypesManagerTest {

    private SeatTypesManager seatTypesManager;
    private QualificationsManager qualificationsManager;

    @Before
    public void setup() {
        qualificationsManager = new QualificationsManager();
    }

    @Test
    public void singleSeatTypeTest() {
        String name = "seatType";
        Map<String, Ternean> qualifications = new HashMap<>();
        qualifications.put("required", Ternean.TRUE);
        qualifications.put("forbidden", Ternean.FALSE);
        qualifications.put("ignored", Ternean.BLANK);
        seatTypesManager =
                new SeatTypesManager(Collections.singletonMap(name, qualifications), qualificationsManager);

        SeatType actual = seatTypesManager.getSeatType(name);
        assertEquals(name, actual.getName());
        assertEquals(Collections.singleton(qualificationsManager.getQualification("required")),
                actual.getRequiredQualifications());
        assertEquals(Collections.singleton(qualificationsManager.getQualification("forbidden")),
                actual.getForbiddenQualifications());
    }

}