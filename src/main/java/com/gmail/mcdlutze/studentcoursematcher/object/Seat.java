package com.gmail.mcdlutze.studentcoursematcher.object;

public class Seat {
    private final SeatType seatType;
    private final Course course;

    public Seat(SeatType seatType, Course course) {
        this.seatType = seatType;
        this.course = course;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public Course getCourse() {
        return course;
    }
}
