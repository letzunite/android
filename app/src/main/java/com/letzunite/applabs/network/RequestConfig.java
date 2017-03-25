package com.letzunite.applabs.network;

/**
 * @author Akash Patra
 */
public enum RequestConfig implements IRequestConfig {
    LOGIN {
        @Override
        public boolean isStub() {
            return true;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }
    }
}
