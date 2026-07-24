package com.gklx.ai.core.util;

/**
 * Utility methods for exception handling.
 */
public final class ExceptionUtils {

    private ExceptionUtils() {
        // Prevent instantiation
    }

    /**
     * Extracts the most informative error message from an exception.
     *
     *
     * @param throwable The exception to extract the message from (may be null)
     * @return A non-null error message string
     */
    public static String getErrorMessage(Throwable throwable) {
        if (throwable == null) {
            return "Unknown error";
        }

        // Try to get the message from the exception
        String message = throwable.getMessage();
        if (message != null && !message.isEmpty()) {
            return message;
        }

        // If no message, try to get the cause's message
        Throwable cause = throwable.getCause();
        if (cause != null && cause.getMessage() != null && !cause.getMessage().isEmpty()) {
            return cause.getMessage();
        }

        // Fall back to the exception class name
        return throwable.getClass().getSimpleName();
    }
}
