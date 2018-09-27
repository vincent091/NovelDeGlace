package com.lnproduction.noveldeglace.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Log {

    public interface LogImplementation {
        void log(int level, String tag, String msg);
        void log(int level, String tag, String msg, Throwable tr);
    }

    private static class DefaultImplementation implements LogImplementation {

        private static Level level2Level(int level) {
            switch (level) {
                case VERBOSE : return Level.FINER;
                case DEBUG : return Level.FINE;
                case INFO : return Level.INFO;
                case WARN : return Level.WARNING;
                case ERROR : return Level.SEVERE;
            }
            return Level.FINEST;
        }

        @Override
        public void log(int level, String tag, String msg) {
            Logger.getLogger(tag).log(level2Level(level), msg);
        }

        @Override
        public void log(int level, String tag, String msg, Throwable tr) {
            Logger.getLogger(tag).log(level2Level(level), msg, tr);
        }
    }


    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;

    private Log() {
    }

    /**
     * The log implementation can be changed
     */
    public static LogImplementation implementation = new DefaultImplementation();


    public static void v(String tag, String msg) {
        implementation.log(VERBOSE, tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        implementation.log(VERBOSE, tag, msg, tr);
    }

    public static void d(String tag, String msg) {
        implementation.log(DEBUG, tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        implementation.log(DEBUG, tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        implementation.log(INFO, tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        implementation.log(INFO, tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        implementation.log(WARN, tag, msg);
    }
    public static void w(String tag, String msg, Throwable tr) {
        implementation.log(WARN, tag, msg, tr);
    }
    public static void e(String tag, String msg) {
        implementation.log(ERROR, tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        implementation.log(ERROR, tag, msg, tr);
    }

    public static void log(int level, String tag, String msg) {
        implementation.log(level, tag, msg);
    }

    public static void log(int level, String tag, String msg, Throwable t) {
        implementation.log(level, tag, msg, t);
    }

}
