package com.letzunite.applabs.network.httpURLConnection;

import com.letzunite.applabs.constants.Config;
import com.letzunite.applabs.exceptions.CustomConnectionException;
import com.letzunite.applabs.logger.Logger;
import com.letzunite.applabs.logger.LoggerEnable;
import com.letzunite.applabs.network.ServiceVO;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * GET method Implementation of {@link BaseHttpURLConnection} to send and receive data over the
 * web.
 *
 * @author Akash Patra
 */
public class GetHttpURLConnection extends BaseHttpURLConnection {

    // For Logging
    private final LoggerEnable CLASS_NAME = LoggerEnable.GetHttpURLConnection;

    @Override
    public String fetchData(ServiceVO serviceVO) throws CustomConnectionException {
        return dataFromServer(serviceVO);
    }

    private String dataFromServer(ServiceVO serviceVO) throws CustomConnectionException {
        URL url;

        try {
            url = new URL(serviceVO.getUrl() + "?" + getQuery(serviceVO.getReqParams()));
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(REQUEST_READ_TIME_OUT);
            connection.setConnectTimeout(REQUEST_CONNECTION_TIME_OUT);

            if (serviceVO.getReqHeaders() != null) {
                addReqHeaders(serviceVO.getReqHeaders());
            }

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setRequestMethod(serviceVO.getMethodType().getRequestType());
            connection.connect();

            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                throw new CustomConnectionException(statusCode);
            }

            // Get Response
            return readInputStream();
        } catch (Exception ex) {
            Logger.logE(Config.TAG, ex, CLASS_NAME, " >> doInBack >> Exception: ", ex);
            if (ex instanceof CustomConnectionException) {
                throw new CustomConnectionException(((CustomConnectionException) ex).getStatusCode());
            }
            throw new CustomConnectionException(5001, ex.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
