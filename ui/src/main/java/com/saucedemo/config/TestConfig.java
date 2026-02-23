package com.saucedemo.config;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestConfig {

    private TestConfig() {}
        public static final String BASE_URL = prop("base.url", "BASE_URL", "https://www.saucedemo.com");
        public static final String STANDARD_USER = prop("standard.user", "STANDARD_USER", "standard_user");
        public static final String LOCKED_USER = prop("locked.user", "LOCKED_USER", "locked_out_user");
        public static final String PASSWORD = prop("password", "PASSWORD", "secret_sauce");

        public static final boolean HEADLESS = Boolean.parseBoolean(prop("headless", "HEADLESS", "true"));
        public static final double  SLOW_MO      = Double.parseDouble( prop("slowmo",   "SLOW_MO",   "0"));
        public static final int     TIMEOUT      = Integer.parseInt(   prop("timeout",  "TIMEOUT",   "30000"));

        public static final String  SCREENSHOT_DIR = prop("screenshot.dir", "SCREENSHOT_DIR", "screenshots");
        public static final String  TRACE_DIR      = prop("trace.dir",      "TRACE_DIR",      "traces");

        public static final Path STORAGE_STATE_PATH = Paths.get("build", "auth.json");

        private static String prop(String sysProp, String envVar, String fallback) {
            String v = System.getProperty(sysProp);
            if (v != null && !v.isBlank()) return v;

            v = System.getenv(envVar);
            if (v != null && !v.isBlank()) return v;
            return fallback;
        }

}
