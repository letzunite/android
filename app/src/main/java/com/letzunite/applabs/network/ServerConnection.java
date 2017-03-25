package com.letzunite.applabs.network;

import com.letzunite.applabs.exceptions.CustomConnectionException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Interface for creating http/https connection using different networking libraries.
 *
 * @author Akash Patra
 */
public abstract class ServerConnection {
    protected final static int REQUEST_READ_TIME_OUT = 10000;
    protected final static int REQUEST_CONNECTION_TIME_OUT = 15000;

    protected abstract void addReqHeaders(HashMap<String, String> reqHeaders);

    protected String getQuery(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(String.valueOf(pair.getKey()), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(String.valueOf(pair.getValue()), "UTF-8"));
            it.remove(); // avoids a ConcurrentModificationException
        }

        return result.toString();
    }

    protected abstract String readInputStream() throws IOException;

    public abstract String fetchData(ServiceVO serviceVO)
            throws CustomConnectionException;
}
