package com.gmail.mcdlutze.studentcoursematcher.object;

import java.util.*;

public class Student {

    public static final Student DUMMY =
            new Student("<DUMMY>", Collections.singleton(Qualification.EMPTY), Collections.emptyList());

    private final String id;
    private final Set<Qualification> qualifications;
    private final List<Course> preferences;

    public Student(String id, Set<Qualification> qualifications, List<Course> preferences) {
        this.id = id;
        this.qualifications = Collections.unmodifiableSet(new HashSet<>(qualifications));
        this.preferences = Collections.unmodifiableList(new ArrayList<>(preferences));
    }

    public String getId() {
        return id;
    }

    public boolean hasQualification(Qualification qualification) {
        return qualifications.contains(qualification);
    }

    public boolean isDummy() {
        return hasQualification(Qualification.EMPTY);
    }

    public List<Course> getPreferences() {
        return preferences;
    }
}
