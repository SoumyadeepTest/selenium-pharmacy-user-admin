# Logging Guide

This project uses `java.util.logging` (JUL). Create a logger once per class and
use it instead of `System.out.println` or `System.err.println`:

```java
import java.util.logging.Logger;

public class LoginController {
    private static final Logger logger =
            Logger.getLogger(LoginController.class.getName());
}
```

## Choosing a log level

Use the lowest level that communicates the event accurately. Levels are listed
from most important to most detailed:

| Level | Use it for | Example |
|---|---|---|
| `SEVERE` | A serious failure that prevents an operation or test from completing. | `logger.severe("Unable to create WebDriver");` |
| `WARNING` | An unexpected problem or recoverable failure. The application can continue, but the issue should be investigated. | `logger.warning("Element was not found: " + locator);` |
| `INFO` | Normal, significant application or test lifecycle events. | `logger.info("Creating WebDriver");` |
| `CONFIG` | Configuration values or setup choices useful for diagnosing an environment. Avoid logging secrets. | `logger.config("Browser: " + browser);` |
| `FINE` | Debugging details about routine operations. | `logger.fine("Waiting for element: " + locator);` |
| `FINER` | More detailed tracing, such as intermediate steps in an operation. | `logger.finer("Visibility check passed; checking clickability");` |
| `FINEST` | Very detailed tracing, usually temporary or only useful for deep debugging. | `logger.finest("Entering visibility polling iteration");` |

For example, use `INFO` when driver creation starts, `WARNING` when navigation
fails but the test can report the failure and continue, and `SEVERE` when a
failure makes the test or operation impossible to proceed.

## Logging exceptions

Pass the exception to the logger so its stack trace is recorded. Prefer this
over logging only `e.getMessage()`:

```java
import java.util.logging.Level;

try {
    driver.get(url);
} catch (Exception e) {
    logger.log(Level.WARNING, "Cannot open page: " + url, e);
}
```

Choose the level based on the impact: use `WARNING` for a recoverable problem
and `SEVERE` when the operation cannot continue.

## Common logging methods

JUL provides convenience methods for each level:

```java
logger.severe("...");
logger.warning("...");
logger.info("...");
logger.config("...");
logger.fine("...");
logger.finer("...");
logger.finest("...");
```

Use `log` when the level is selected dynamically or when you need to attach an
exception:

```java
logger.log(Level.WARNING, "Element lookup failed", exception);
logger.log(Level.INFO, "Operation completed");
```

## Practical guidelines

- Log meaningful events and context, such as a test name, browser, URL, or
  locator, where appropriate.
- Do not log passwords, access tokens, cookies, or other sensitive data.
- Avoid logging the same failure repeatedly at multiple layers unless each log
  adds useful context.
- Use `FINE` and lower for diagnostic detail, not routine success messages.
- JUL's default configuration commonly displays `INFO` and more important
  messages. `CONFIG`, `FINE`, `FINER`, and `FINEST` may require a logging
  configuration that enables those levels.
