package com.gmail.mcdlutze.studentcoursematcher.manager;

import com.gmail.mcdlutze.studentcoursematcher.object.Qualification;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QualificationsManagerTest {

    private QualificationsManager qualificationsManager;

    @Before
    public void setup() {
        qualificationsManager = new QualificationsManager();
    }

    @Test
    public void emptyTest() {
        assertEquals(Qualification.EMPTY, qualificationsManager.getQualification(""));
    }

    @Test
    public void getQualificationTest() {
        String name = "qualification";
        assertEquals(qualificationsManager.getQualification(name), qualificationsManager.getQualification(name));
    }
}
