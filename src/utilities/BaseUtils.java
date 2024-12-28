package utilities;

/*
 * Abstract base class for utility classes to prevent instantiation.
 * Protected constructor to prevent instantiation.
 * Extend this class to enforce non-instantiable behavior in utility classes.
 */
public abstract class BaseUtils {
    protected static int sleep = 1000; // Interval time in millisecond

    protected BaseUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated!");
    }
}
