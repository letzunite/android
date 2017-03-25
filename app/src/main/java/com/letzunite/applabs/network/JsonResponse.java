package com.letzunite.applabs.network;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Implementation for parsing json response received from Web using GSON.
 *
 * @author Akash Patra
 */
public class JsonResponse<T> implements IWebServiceResponse {

    @Override
    public void processResponse(Object response, ServiceVO serviceVO) {
        try {
            Gson gson = new Gson();
            T responseVO = (T) gson.fromJson((String) response, serviceVO.getResponseClass());
            serviceVO.setResponse(responseVO);
            serviceVO.setSuccess(true);
        } catch (JsonSyntaxException e) {
            serviceVO.setSuccess(false);
        } catch (Exception e) {
            serviceVO.setSuccess(false);
        }
    }
}
