package org.example.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentReportManager {
    private static final Path REPORT_PATH = Path.of("target", "extent-reports", "extent-report.html");
    private static final ThreadLocal<ExtentTest> CURRENT_TEST = new ThreadLocal<>();
    private static ExtentReports reports;

    private ExtentReportManager() {
    }

    public static synchronized void initialize() {
        if (reports != null) {
            return;
        }
        try {
            Files.createDirectories(REPORT_PATH.getParent());
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to create report directory: " + REPORT_PATH.getParent(),
                    exception);
        }
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_PATH.toString());
        sparkReporter.config().setDocumentTitle("REST Assured API Test Results");
        sparkReporter.config().setReportName("API Automation Results");
        reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
    }

    public static void startTest(String name) {
        CURRENT_TEST.set(reports.createTest(name));
    }

    public static void log(Status status, String message) {
        ExtentTest currentTest = CURRENT_TEST.get();
        if (currentTest != null) {
            currentTest.log(status, message);
        }
    }

    public static void finishTest(Status status, String message) {
        ExtentTest currentTest = CURRENT_TEST.get();
        if (currentTest != null) {
            currentTest.log(status, message);
            CURRENT_TEST.remove();
        }
    }

    public static synchronized void flush() {
        if (reports != null) {
            reports.flush();
        }
    }
}
