package com.gmail.mcdlutze.studentcoursematcher.analyzer;

import com.gmail.mcdlutze.studentcoursematcher.object.Seat;
import com.gmail.mcdlutze.studentcoursematcher.object.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Analyzer {

    public Analysis analyze(Map<Student, Seat> assignments) {
        List<Integer> preferenceFrequencies = new ArrayList<>();
        int nonPreferredCount = 0;
        int illegalAssignments = 0;

        for (Map.Entry<Student, Seat> entry : assignments.entrySet()) {
            Student student = entry.getKey();
            Seat seat = entry.getValue();

            int preferenceNumber = student.getPreferences().indexOf(seat.getCourse());
            if (preferenceNumber == -1) {
                nonPreferredCount++;
            } else {
                while (preferenceFrequencies.size() <= preferenceNumber) {
                    preferenceFrequencies.add(0);
                }
                preferenceFrequencies.set(preferenceNumber, preferenceFrequencies.get(preferenceNumber) + 1);
            }

            if (!seat.getSeatType().isTypeOfStudent(student)) {
                illegalAssignments++;
            }
        }
        return new Analysis(preferenceFrequencies, nonPreferredCount, illegalAssignments);

    }

    public static class Analysis {
        private final List<Integer> preferenceCounts;
        private final int nonPreferredCount;
        private final int illegalAssignments;

        public Analysis(List<Integer> preferenceCounts, int nonPreferredCount, int illegalAssignments) {
            this.preferenceCounts = Collections.unmodifiableList(new ArrayList<>(preferenceCounts));
            this.nonPreferredCount = nonPreferredCount;
            this.illegalAssignments = illegalAssignments;
        }

        public List<Integer> getPreferenceCounts() {
            return preferenceCounts;
        }

        public int getNonPreferredCount() {
            return nonPreferredCount;
        }

        public int getIllegalAssignments() {
            return illegalAssignments;
        }
    }
}
