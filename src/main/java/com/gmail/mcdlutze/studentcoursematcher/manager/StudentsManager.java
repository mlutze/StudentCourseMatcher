package com.gmail.mcdlutze.studentcoursematcher.manager;

import com.gmail.mcdlutze.studentcoursematcher.object.Course;
import com.gmail.mcdlutze.studentcoursematcher.object.Qualification;
import com.gmail.mcdlutze.studentcoursematcher.object.Student;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentsManager {
    private final Map<String, Student> students;

    public StudentsManager(Map<String, Set<String>> qualifications, Map<String, List<String>> preferences,
                           QualificationsManager qualificationsManager, CoursesManager coursesManager) {
        this.students = qualifications.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                e -> createStudent(e.getKey(), e.getValue(), preferences.get(e.getKey()), qualificationsManager,
                        coursesManager)));
    }

    public Student getStudent(String id) {
        return students.get(id);
    }

    private Student createStudent(String name, Set<String> qualificationNames, List<String> preferenceNames,
                                  QualificationsManager qualificationsManager, CoursesManager coursesManager) {
        Set<Qualification> qualifications =
                qualificationNames.stream().map(qualificationsManager::getQualification).collect(Collectors.toSet());
        List<Course> preferences = preferenceNames.stream().map(coursesManager::getCourse).collect(Collectors.toList());

        return new Student(name, qualifications, preferences);
    }
}
