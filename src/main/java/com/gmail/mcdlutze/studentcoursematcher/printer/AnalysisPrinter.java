package com.gmail.mcdlutze.studentcoursematcher.printer;

import com.gmail.mcdlutze.studentcoursematcher.analyzer.Analyzer;

public class AnalysisPrinter {
    public void printAnalysis(Analyzer.Analysis analysis) {
        for (int i = 0; i < analysis.getPreferenceCounts().size(); i++) {
            int count = analysis.getPreferenceCounts().get(i);
            System.out.printf("#%d choice: %d%n", i + 1, count);
        }
        System.out.printf("Non-preferences: %d%n", analysis.getNonPreferredCount());
        System.out.printf("Illegal assignments: %d%n", analysis.getIllegalAssignments());
    }
}
