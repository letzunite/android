package com.letzunite.applabs.network;

import android.content.Context;

import java.util.HashMap;

/**
 * Interface for all Network Controller Classes.
 *
 * @author Akash Patra
 */
public abstract class BaseNetworkController implements IWebServiceListener {
    private IWebServiceListener listener;
    private RequestConfig requestConfig;

    public abstract HashMap<String, String> getReqHeaders();

    public abstract Context getContext();

    protected void executeGet(RequestConfig requestConfig, IWebServiceListener listener, String url,
                              HashMap<String, String> param, Class responseObj) {
        this.requestConfig = requestConfig;
        this.listener = listener;
        if (requestConfig.isStub()) {
            executeStubRequest(listener, responseObj);
        } else {
            executeRequest(MethodType.GET.getRequestType(), this, url, param, responseObj);
        }
    }

    protected void executePost(RequestConfig requestConfig, IWebServiceListener listener, String url,
                               HashMap<String, String> param, Class responseObj) {
        this.requestConfig = requestConfig;
        this.listener = listener;
        if (requestConfig.isStub()) {
            executeStubRequest(listener, responseObj);
        } else {
            executeRequest(MethodType.POST.getRequestType(), this, url, param, responseObj);
        }
    }

    protected void executePost(RequestConfig requestConfig, IWebServiceListener listener, String url,
                               String param, Class responseObj) {
        this.requestConfig = requestConfig;
        this.listener = listener;
        if (requestConfig.isStub()) {
            executeStubRequest(listener, responseObj);
        } else {
            executeRequest(MethodType.POST.getRequestType(), this, url, param, responseObj);
        }
    }

    private void executeRequest(String methodType, IWebServiceListener listener, String url,
                                Object param, Class responseObj) {
        WebServiceManager webServiceManager = new WebServiceManager(getContext());
        if (MethodType.GET.getRequestType().equalsIgnoreCase(methodType)) {
            webServiceManager.getService(listener, url, getReqHeaders(),
                    (HashMap<String, String>) param, new JsonResponse<>(),
                    responseObj);
        } else {
            if (param instanceof HashMap) {
                webServiceManager.postService(listener, url, getReqHeaders(),
                        (HashMap<String, String>) param, new JsonResponse<>(),
                        responseObj);
            } else {
                webServiceManager.postService(listener, url, getReqHeaders(),
                        (String) param, new JsonResponse<>(),
                        responseObj);
            }
        }
    }

    private void executeStubRequest(IWebServiceListener listener, Class responseObj) {
        StubManager stubManager = new StubManager();
        stubManager.getService(this.requestConfig, listener, responseObj);
    }

    @Override
    public void onWebServiceSuccess(RequestConfig requestConfig, Object responseObj) {
        listener.onWebServiceSuccess(this.requestConfig, responseObj);
    }

    @Override
    public void onWebServiceFailure(RequestConfig requestConfig, String message) {
        listener.onWebServiceFailure(this.requestConfig, message);
    }

    public enum MethodType {
        GET("GET"), POST("POST");

        String requestType;

        MethodType(String requestType) {
            this.requestType = requestType;
        }

        public String getRequestType() {
            return requestType;
        }
    }
}
