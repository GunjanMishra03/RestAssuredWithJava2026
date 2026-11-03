package org.example.listeners;

import com.aventstack.extentreports.Status;
import org.example.reporting.ExtentReportManager;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements IExecutionListener, ITestListener {
    @Override
    public void onExecutionStart() {
        ExtentReportManager.initialize();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getMethod().getMethodName();
        if (result.getParameters().length > 0) {
            name += " " + java.util.Arrays.toString(result.getParameters());
        }
        ExtentReportManager.startTest(name);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.finishTest(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable failure = result.getThrowable();
        ExtentReportManager.finishTest(Status.FAIL, failure == null ? "Test failed" : failure.toString());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.finishTest(Status.SKIP, "Test skipped");
    }

    @Override
    public void onExecutionFinish() {
        ExtentReportManager.flush();
    }
}
