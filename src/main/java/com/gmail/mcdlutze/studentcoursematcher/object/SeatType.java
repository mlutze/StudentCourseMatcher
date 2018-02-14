package com.gmail.mcdlutze.studentcoursematcher.object;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SeatType {
    private final String name;
    private final Set<Qualification> requiredQualifications;
    private final Set<Qualification> forbiddenQualifications;

    public SeatType(String name, Set<Qualification> requiredQualifications,
                    Set<Qualification> forbiddenQualifications) {
        this.name = name;
        this.requiredQualifications = Collections.unmodifiableSet(new HashSet<>(requiredQualifications));
        this.forbiddenQualifications = Collections.unmodifiableSet(new HashSet<>(forbiddenQualifications));
    }

    public String getName() {
        return name;
    }

    public Set<Qualification> getRequiredQualifications() {
        return requiredQualifications;
    }

    public Set<Qualification> getForbiddenQualifications() {
        return forbiddenQualifications;
    }

    public boolean isTypeOfStudent(Student student) {
        for (Qualification qualification : requiredQualifications) {
            if (!student.hasQualification(qualification)) {
                return false;
            }
        }

        for (Qualification qualification : forbiddenQualifications) {
            if (student.hasQualification(qualification)) {
                return false;
            }
        }

        return true;
    }
}
