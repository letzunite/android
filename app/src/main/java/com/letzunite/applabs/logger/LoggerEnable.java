package com.letzunite.applabs.logger;

/**
 * Enum Class which will enable/disable printing of logs.
 * It implements {@link ILoggerActivator}
 *
 * @author Akash Patra
 */
public enum LoggerEnable implements ILoggerActivator {
    LoginAct {
        public boolean isEnabled() {
            return true;
        }
    },
    LoginFragment {
        public boolean isEnabled() {
            return true;
        }
    },
    MainAct {
        public boolean isEnabled() {
            return true;
        }
    },
    ParentRegisterFragment {
        public boolean isEnabled() {
            return true;
        }
    },
    Register1Fragment {
        public boolean isEnabled() {
            return true;
        }
    },
    Register2Fragment {
        public boolean isEnabled() {
            return true;
        }
    },
    Register3Fragment {
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
    ServerConnection {
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
    },
    AppUtils {
        @Override
        public boolean isEnabled() {
            return true;
        }
    },
    BitmapLruCache {
        @Override
        public boolean isEnabled() {
            return true;
        }
    },
    FileCache {
        @Override
        public boolean isEnabled() {
            return true;
        }
    },
    ImageManager {
        @Override
        public boolean isEnabled() {
            return true;
        }
    },
    ImageLoader {
        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
