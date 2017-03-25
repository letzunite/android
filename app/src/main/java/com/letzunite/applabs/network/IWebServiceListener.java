package com.letzunite.applabs.network;

/**
 * Interface for passing success/failure response to the implementation class.
 *
 * @author Akash Patra
 */
public interface IWebServiceListener {
    void onWebServiceSuccess(RequestConfig requestConfig, Object responseObj);

    void onWebServiceFailure(RequestConfig requestConfig, String message);
}
