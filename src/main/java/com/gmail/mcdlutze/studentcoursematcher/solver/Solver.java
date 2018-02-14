package com.gmail.mcdlutze.studentcoursematcher.solver;

import com.gmail.mcdlutze.studentcoursematcher.object.Course;
import com.gmail.mcdlutze.studentcoursematcher.object.Seat;
import com.gmail.mcdlutze.studentcoursematcher.object.SeatType;
import com.gmail.mcdlutze.studentcoursematcher.object.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Solver {

    private final double alpha;
    private final double nonPreferenceAssignmentCost;
    private final double illegalAssignmentCost;

    public Solver(double alpha, double nonPreferenceAssignmentCost, double illegalAssignmentCost) {
        this.alpha = alpha;
        this.nonPreferenceAssignmentCost = nonPreferenceAssignmentCost;
        this.illegalAssignmentCost = illegalAssignmentCost;
    }

    public Map<Student, Seat> solve(List<Student> students, List<Course> courses) {
        List<Seat> seatList = buildSeatList(courses);
        List<Student> studentList = buildStudentList(students, seatList.size());
        double[][] matrix = buildMatrix(studentList, seatList);

        JonkerVolgenantAlgorithm algorithm = new JonkerVolgenantAlgorithm();
        int[][] assignments = algorithm.computeAssignments(matrix);
        return Arrays.stream(assignments).filter(a -> a[0] < students.size())
                .collect(Collectors.toMap(a -> students.get(a[0]), a -> seatList.get(a[1])));

    }

    private List<Seat> buildSeatList(List<Course> courses) {
        List<Seat> seats = new ArrayList<>();
        for (Course course : courses) {
            for (Map.Entry<SeatType, Integer> entry : course.getSeatTypes().entrySet()) {
                SeatType seatType = entry.getKey();
                int count = entry.getValue();

                for (int i = 0; i < count; i++) {
                    seats.add(new Seat(seatType, course));
                }
            }
        }
        return seats;
    }

    private List<Student> buildStudentList(List<Student> students, int size) {
        List<Student> studentList = new ArrayList<>(students);
        while (studentList.size() < size) {
            studentList.add(Student.DUMMY);
        }
        return studentList;
    }

    private double[][] buildMatrix(List<Student> students, List<Seat> seats) {
        int numPreferences = students.get(0).getPreferences().size();

        double[][] matrix = new double[students.size()][seats.size()];
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            for (int j = 0; j < seats.size(); j++) {
                Seat seat = seats.get(j);
                matrix[i][j] = assignmentCost(student, seat, numPreferences);
            }
        }

        return matrix;
    }

    private double assignmentCost(Student student, Seat seat, int numPreferences) {
        double emptyAssignmentCost = Math.max(illegalAssignmentCost, nonPreferenceAssignmentCost) + 1;
        double maxMatchCost = Math.pow(alpha, numPreferences - 1);
        double emptyMatchCost = maxMatchCost * emptyAssignmentCost;
        double illegalMatchCost = maxMatchCost * illegalAssignmentCost;
        double nonPreferenceMatchCost = maxMatchCost * nonPreferenceAssignmentCost;

        if (student.isDummy()) {
            return emptyMatchCost;
        } else if (!seat.getSeatType().isTypeOfStudent(student)) {
            return illegalMatchCost;
        } else {
            Course course = seat.getCourse();
            int preferenceNumber = student.getPreferences().indexOf(course);
            if (preferenceNumber == -1) {
                return nonPreferenceMatchCost;
            } else {
                return Math.pow(alpha, preferenceNumber);
            }
        }
    }

}
