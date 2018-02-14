package com.gmail.mcdlutze.studentcoursematcher.printer;

import com.gmail.mcdlutze.studentcoursematcher.object.Seat;
import com.gmail.mcdlutze.studentcoursematcher.object.Student;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class AssignmentsPrinter {

    public void printAssignments(Map<Student, Seat> assignments, File outputFile) throws IOException {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(outputFile), CSVFormat.DEFAULT)) {
            for (Map.Entry<Student, Seat> entry : assignments.entrySet()) {
                printer.printRecord(entry.getKey().getId(), entry.getValue().getCourse().getId());
            }
        }
    }

}
