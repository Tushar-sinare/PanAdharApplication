package com.netwin.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLogger implements MyLogger {

    private final Logger logger;

    public Slf4jLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }
   
}
