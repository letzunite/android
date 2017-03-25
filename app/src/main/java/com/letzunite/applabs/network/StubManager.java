package com.letzunite.applabs.network;

import com.google.gson.Gson;

/**
 * @author Akash Patra
 */

public class StubManager<T> {

    public void getService(RequestConfig requestConfig, IWebServiceListener listener, Class responseObj) {
        switch (requestConfig) {
            case LOGIN:
                getToken(requestConfig, listener, responseObj);
                break;
        }
    }

    private void getToken(RequestConfig requestConfig, IWebServiceListener listener, Class responseObj) {
        String stub;
        if (requestConfig.isSuccess()) {
            stub = "";
        } else {
            stub = "{\"status\":{\"code\":500,\"message\":\"Something went wrong\"},\"result\":null}";
        }

        T responseVO = processResponse(stub, responseObj);
        listener.onWebServiceSuccess(requestConfig, responseVO);
    }

    private T processResponse(Object response, Class responseObj) {
        try {
            Gson gson = new Gson();
            T responseVO = (T) gson.fromJson((String) response, responseObj);
            return responseVO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
