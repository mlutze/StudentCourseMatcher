package com.gmail.mcdlutze.studentcoursematcher.object;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Course {
    public static final Course EMPTY = new Course("<EMPTY>", Collections.emptyMap());
    private final String id;
    private final Map<SeatType, Integer> seatTypes;

    public Course(String id, Map<SeatType, Integer> seatTypes) {
        this.id = id;
        this.seatTypes = Collections.unmodifiableMap(new HashMap<>(seatTypes));
    }

    public String getId() {
        return id;
    }

    public Map<SeatType, Integer> getSeatTypes() {
        return seatTypes;
    }

}
