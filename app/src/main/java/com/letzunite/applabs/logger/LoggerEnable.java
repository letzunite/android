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
            return false;
        }
    },
    LoginFragment {
        public boolean isEnabled() {
            return false;
        }
    },
    OTPFragment {
        public boolean isEnabled() {
            return false;
        }
    },
    HomeAct {
        @Override
        public boolean isEnabled() {
            return false;
        }
    },
    NavDrawAdapter {
        @Override
        public boolean isEnabled() {
            return false;
        }
    },
    StoreListFragment {
        @Override
        public boolean isEnabled() {
            return false;
        }
    },
    StoreListAdapter {
        @Override
        public boolean isEnabled() {
            return false;
        }
    },
    StoreFragment {
        @Override
        public boolean isEnabled() {
            return false;
        }
    },
    DayStoreListAdapter {
        @Override
        public boolean isEnabled() {
            return false;
        }
    },
    ElementListFragment {
        @Override
        public boolean isEnabled() {
            return true;
        }
    },
    ElementListAdapter {
        @Override
        public boolean isEnabled() {
            return true;
        }
    },
    SearchListAda {
        @Override
        public boolean isEnabled() {
            return true;
        }
    },
    SearchAct {
        @Override
        public boolean isEnabled() {
            return true;
        }
    },
    SMSReceiver {
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
