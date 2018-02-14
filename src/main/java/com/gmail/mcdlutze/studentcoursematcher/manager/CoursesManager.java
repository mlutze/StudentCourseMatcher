package com.gmail.mcdlutze.studentcoursematcher.manager;

import com.gmail.mcdlutze.studentcoursematcher.object.Course;
import com.gmail.mcdlutze.studentcoursematcher.object.SeatType;

import java.util.Map;
import java.util.stream.Collectors;

public class CoursesManager {
    private final Map<String, Course> courses;

    public CoursesManager(Map<String, Map<String, Integer>> courses, SeatTypesManager seatTypesManager) {
        this.courses = courses.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, e -> createCourse(e.getKey(), e.getValue(), seatTypesManager)));
    }

    public Course getCourse(String id) {
        if (id.isEmpty()) {
            return Course.EMPTY;
        }
        return courses.get(id);
    }

    private Course createCourse(String id, Map<String, Integer> seatTypeNames, SeatTypesManager seatTypesManager) {
        Map<SeatType, Integer> seatTypes = seatTypeNames.entrySet().stream()
                .collect(Collectors.toMap(e -> seatTypesManager.getSeatType(e.getKey()), Map.Entry::getValue));
        return new Course(id, seatTypes);
    }

}
