package listeners;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
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
        writeFailureLog(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        logger.info("========== TEST SKIPPED: " + result.getName() + " ==========");
    }

    private static synchronized void writeFailureLog(ITestResult result) {
        StringWriter failureDetails = new StringWriter();
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            throwable.printStackTrace(new PrintWriter(failureDetails));
        }

        String entry = String.format(
                "%s [FAILED] %s.%s%n%s%n",
                Instant.now(),
                result.getTestClass().getName(),
                result.getName(),
                failureDetails);

        try {
            Files.writeString(
                    Path.of("error.log"),
                    entry,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.severe("Unable to write test failure to error.log: " + e.getMessage());
            throw new IllegalStateException("Unable to write test failure to error.log", e);
        }
    }
}