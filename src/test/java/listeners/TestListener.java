package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {

        System.out.println(
                "========== TEST PASSED: "
                        + result.getName()
                        + " ==========");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        System.out.println(
                "========== TEST FAILED: "
                        + result.getName()
                        + " ==========");
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        System.out.println(
                "========== TEST SKIPPED: "
                        + result.getName()
                        + " ==========");
    }
}