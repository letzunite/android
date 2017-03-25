package com.letzunite.applabs.network;

/**
 * Interface for parsing response received from WebService. We can have for ex:
 * parser for xml, json etc response.
 *
 * @author Akash Patra
 */
public interface IWebServiceResponse {
    void processResponse(Object response, ServiceVO serviceVO);
}
