package com.letzunite.applabs.logger;

/**
 * Enum Class which will enable/disable printing of logs.
 * It implements {@link ILoggerActivator}
 *
 * @author Akash Patra
 */
public enum LoggerEnable implements ILoggerActivator {
    LoginFragment {
        public boolean isEnabled() {
            return true;
        }
    },
    OTPFragment {
        public boolean isEnabled() {
            return true;
        }
    },
    HomeAct {
        @Override
        public boolean isEnabled() {
            return true;
        }
    },
    HttpURLConnectionConnector {
        @Override
        public boolean isEnabled() {
            return true;
        }
    },
    BaseHttpURLConnection {
        @Override
        public boolean isEnabled() {
            return true;
        }
    },
    GetHttpURLConnection {
        @Override
        public boolean isEnabled() {
            return true;
        }
    },
    PostHttpURLConnection {
        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
