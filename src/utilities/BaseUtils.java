package utilities;

/*
 * Abstract base class for utility classes to prevent instantiation.
 * Protected constructor to prevent instantiation.
 * Extend this class to enforce non-instantiable behavior in utility classes.
 */
public abstract class BaseUtils {
    // Interval time in millisecond
    protected static int interval;
    protected static int errorInterval;

    static {
        // Todo
        // interval = 1000;
        interval = 300;
        errorInterval = interval * 2;
    }

    protected BaseUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated!");
    }
}
