package com.gmail.mcdlutze.studentcoursematcher.printer;

import com.gmail.mcdlutze.studentcoursematcher.object.Course;
import com.gmail.mcdlutze.studentcoursematcher.object.Seat;
import com.gmail.mcdlutze.studentcoursematcher.object.Student;
import com.gmail.mcdlutze.studentcoursematcher.object.SeatType;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AssignmentsPrinterTest {

    private TemporaryFolder temporaryFolder;
    private AssignmentsPrinter assignmentsPrinter;

    @Before
    public void setup() throws IOException {
        temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();
        assignmentsPrinter = new AssignmentsPrinter();
    }

    @Test
    public void singleAssignmentTest() throws IOException {
        Student student = new Student("student", Collections.emptySet(), Collections.emptyList());
        Course course = new Course("course", Collections.emptyMap());
        SeatType seatType = new SeatType("seatType", Collections.emptySet(), Collections.emptySet());
        Seat seat = new Seat(seatType, course);
        Map<Student, Seat> assignments = Collections.singletonMap(student, seat);

        File outputFile = temporaryFolder.newFile();
        assignmentsPrinter.printAssignments(assignments, outputFile);

        List<String> expected = Collections.singletonList("student,course");
        List<String> actual = Files.readAllLines(outputFile.toPath());
        assertEquals(expected, actual);
    }


}