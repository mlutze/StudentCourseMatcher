package com.gmail.mcdlutze.studentcoursematcher;

import com.gmail.mcdlutze.studentcoursematcher.analyzer.Analyzer;
import com.gmail.mcdlutze.studentcoursematcher.exception.FileMismatchException;
import com.gmail.mcdlutze.studentcoursematcher.manager.*;
import com.gmail.mcdlutze.studentcoursematcher.object.Course;
import com.gmail.mcdlutze.studentcoursematcher.object.Seat;
import com.gmail.mcdlutze.studentcoursematcher.object.Student;
import com.gmail.mcdlutze.studentcoursematcher.printer.AnalysisPrinter;
import com.gmail.mcdlutze.studentcoursematcher.printer.AssignmentsPrinter;
import com.gmail.mcdlutze.studentcoursematcher.solver.Solver;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    @Option(name = "-c", required = true, usage = "courses file")
    private File coursesFile;

    @Option(name = "-q", required = true, usage = "qualifications file")
    private File qualificationsFile;

    @Option(name = "-p", required = true, usage = "preferences file")
    private File preferencesFile;

    @Option(name = "-s", required = true, usage = "seat types file")
    private File seatTypesFile;

    @Option(name = "-o", required = true, usage = "output file")
    private File outputFile;

    @Option(name = "-h", help = true, usage = "display usage information")
    private boolean help = false;

    @Option(name = "-b", usage = "allow blank preferences")
    private boolean allowBlankPreferences;

    @Option(name = "-u", usage = "allow unknown preferences")
    private boolean allowUnknownPreferences;

    @Option(name = "-a", required = true, usage = "alpha value")
    private double alpha;

    @Option(name = "-n", usage = "non-preference assignment cost")
    private double nonPreferenceAssignmentCost = Double.NEGATIVE_INFINITY;

    @Option(name = "-i", usage = "illegal assignment cost")
    private double illegalAssignmentCost = Double.NEGATIVE_INFINITY;

    private final CmdLineParser parser = new CmdLineParser(this, ParserProperties.defaults().withShowDefaults(false));

    public static void main(String[] args) {
        new Main().doMain(args);
    }

    private void doMain(String[] args) {
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getLocalizedMessage());
            System.err.println("-h for usage information");
            return;
        }

        if (help) {
            displayUsage();
            return;
        }

        if (illegalAssignmentCost == Double.NEGATIVE_INFINITY) {
            illegalAssignmentCost = alpha;
        }
        if (nonPreferenceAssignmentCost == Double.NEGATIVE_INFINITY) {
            nonPreferenceAssignmentCost = alpha;
        }

        try {
            run();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }

    }

    private void displayUsage() {
        parser.printUsage(System.out);
    }

    private void run() throws IOException, FileMismatchException {

        // create managers
        FilesManager filesManager =
                FilesManager.builder().withCoursesFile(coursesFile).withPreferencesFile(preferencesFile)
                        .withQualificationsFile(qualificationsFile).withSeatTypesFile(seatTypesFile)
                        .withAllowBlankPreferences(allowBlankPreferences)
                        .withAllowUnknownPreferences(allowUnknownPreferences).build();
        QualificationsManager qualificationsManager = new QualificationsManager();
        SeatTypesManager seatTypesManager =
                new SeatTypesManager(filesManager.getSeatTypes(), qualificationsManager);
        CoursesManager coursesManager = new CoursesManager(filesManager.getCourses(), seatTypesManager);
        StudentsManager studentsManager =
                new StudentsManager(filesManager.getQualifications(), filesManager.getPreferences(),
                        qualificationsManager,
                        coursesManager);

        // get student and course lists
        List<Student> students = filesManager.getQualifications().keySet().stream().map(studentsManager::getStudent)
                .collect(Collectors.toList());
        List<Course> courses =
                filesManager.getCourses().keySet().stream().map(coursesManager::getCourse).collect(Collectors.toList());

        // run objects through algorithm
        Solver solver = new Solver(alpha, nonPreferenceAssignmentCost, illegalAssignmentCost);
        Map<Student, Seat> assignments = solver.solve(students, courses);

        // print output to file
        AssignmentsPrinter assignmentsPrinter = new AssignmentsPrinter();
        assignmentsPrinter.printAssignments(assignments, outputFile);

        // print analysis to stdout
        Analyzer analyzer = new Analyzer();
        Analyzer.Analysis analysis = analyzer.analyze(assignments);
        AnalysisPrinter analysisPrinter = new AnalysisPrinter();
        analysisPrinter.printAnalysis(analysis);

    }
}
