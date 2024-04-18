package com.netwin.logger;


public class LoggerProvider {

    private static volatile MyLogger loggerInstance;

    private LoggerProvider() {
        // Private constructor to prevent instantiation
    }

    public static MyLogger getLogger(Class<?> clazz) {
        if (loggerInstance == null) {
            synchronized (LoggerProvider.class) {
                if (loggerInstance == null) {
                    // You can modify this line to change the logger implementation
                    // For example, return new Log4jLogger(clazz) for Log4j
                    loggerInstance = new Log4jLogger(clazz);
                }
            }
        }
        return loggerInstance;
    }
}
