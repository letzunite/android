package com.letzunite.applabs.network;

import java.util.HashMap;

/**
 * Value Object which holds all the values required for successful api call.
 * It includes request and response also.
 *
 * @author Akash Patra
 */
public class ServiceVO {
    private String url;

    // Request MethodType
    private BaseNetworkController.MethodType methodType;

    // Request Headers
    private HashMap<String, String> reqHeaders;

    // Request Params
    private HashMap<String, String> reqParams;
    private String request;

    private IWebServiceResponse webServiceResponse;

    // Response
    private Class responseClass;
    private Object response;

    // ErrorBean
    private int errorCode;
    private String errorMessage;

    // Flag for success/failure response.
    private boolean isSuccess;

    public ServiceVO(String url, BaseNetworkController.MethodType methodType, HashMap<String, String> reqHeaders,
                     HashMap<String, String> reqParams, IWebServiceResponse webServiceResponse,
                     Class responseClass) {
        this.url = url;
        this.methodType = methodType;
        this.reqHeaders = reqHeaders;
        this.reqParams = reqParams;
        this.webServiceResponse = webServiceResponse;
        this.responseClass = responseClass;
    }

    public ServiceVO(String url, BaseNetworkController.MethodType methodType, HashMap<String, String> reqHeaders,
                     String request, IWebServiceResponse webServiceResponse,
                     Class responseClass) {
        this.url = url;
        this.methodType = methodType;
        this.reqHeaders = reqHeaders;
        this.request = request;
        this.webServiceResponse = webServiceResponse;
        this.responseClass = responseClass;
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, String> getReqParams() {
        return reqParams;
    }

    public HashMap<String, String> getReqHeaders() {
        return reqHeaders;
    }

    public Class getResponseClass() {
        return responseClass;
    }

    public String getRequest() {
        return request;
    }

    public BaseNetworkController.MethodType getMethodType() {
        return methodType;
    }

    public Object getResponse() {
        return response;
    }

    public <T> void setResponse(T response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public IWebServiceResponse getWebServiceResponse() {
        return webServiceResponse;
    }

    public void setWebServiceResponse(IWebServiceResponse webServiceResponse) {
        this.webServiceResponse = webServiceResponse;
    }
}
