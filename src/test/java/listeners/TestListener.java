package listeners;

import java.util.logging.Logger;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger logger = Logger.getLogger(TestListener.class.getName());

    @Override
    public void onTestSuccess(ITestResult result) {

        logger.info("========== TEST PASSED: " + result.getName() + " ==========");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        logger.warning("========== TEST FAILED: " + result.getName() + " ==========");
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        logger.info("========== TEST SKIPPED: " + result.getName() + " ==========");
    }
}