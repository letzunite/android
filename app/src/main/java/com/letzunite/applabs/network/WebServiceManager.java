package com.letzunite.applabs.network;

import android.content.Context;

import com.letzunite.applabs.network.httpURLConnection.HttpURLConnectionConnector;

import java.util.HashMap;

/**
 * Manager class for managing all services of different method types.
 *
 * @author Akash Patra
 */
public class WebServiceManager {

    private Context mContext;

    public WebServiceManager(Context context) {
        this.mContext = context;
    }

    // GET
    public void getService(IWebServiceListener listener, String url, HashMap<String, String> reqParams,
                           Class responseClass) {
        this.getService(listener, url, null, reqParams, null, responseClass);
    }

    public void getService(IWebServiceListener listener, String url, HashMap<String, String> reqHeaders,
                           HashMap<String, String> reqParams, IWebServiceResponse webServiceResponse,
                           Class responseClass) {
        ServiceVO serviceVO = new ServiceVO(url, BaseNetworkController.MethodType.GET, reqHeaders,
                reqParams, webServiceResponse, responseClass);
        initiateWebConnection(listener, serviceVO);
    }

    // POST
    public void postService(IWebServiceListener listener, String url, HashMap<String, String> reqParams,
                            Class responseClass) {
        this.postService(listener, url, null, reqParams, null, responseClass);
    }

    public void postService(IWebServiceListener listener, String url, HashMap<String, String> reqHeaders,
                            HashMap<String, String> reqParams, IWebServiceResponse webServiceResponse,
                            Class responseClass) {
        ServiceVO serviceVO = new ServiceVO(url, BaseNetworkController.MethodType.POST, reqHeaders,
                reqParams, webServiceResponse, responseClass);
        initiateWebConnection(listener, serviceVO);
    }

    public void postService(IWebServiceListener listener, String url, HashMap<String, String> reqHeaders,
                            String reqParams, IWebServiceResponse webServiceResponse,
                            Class responseClass) {
        ServiceVO serviceVO = new ServiceVO(url, BaseNetworkController.MethodType.POST, reqHeaders,
                reqParams, webServiceResponse, responseClass);
        initiateWebConnection(listener, serviceVO);
    }

    private void initiateWebConnection(IWebServiceListener listener, ServiceVO serviceVO) {
        HttpURLConnectionConnector connector = new HttpURLConnectionConnector(mContext, listener);
        connector.initiateTask(serviceVO);
    }
}
