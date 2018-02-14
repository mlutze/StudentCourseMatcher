package com.gmail.mcdlutze.studentcoursematcher.manager;

import com.gmail.mcdlutze.studentcoursematcher.object.Qualification;

import java.util.HashMap;
import java.util.Map;

public class QualificationsManager {
    private Map<String, Qualification> qualifications = new HashMap<>();

    {
        qualifications.put("", Qualification.EMPTY);
    }

    public Qualification getQualification(String name) {
        return qualifications.computeIfAbsent(name, n -> new Qualification());
    }
}
