package com.yksc.lambda.log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerFactory {
    public static Logger getLogger() {
        String callerClassName = Thread.currentThread().getStackTrace()[2].getClassName();
        return LogManager.getLogger(callerClassName);
    }
}
