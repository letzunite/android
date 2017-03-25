package com.letzunite.applabs.network.httpURLConnection;

import com.letzunite.applabs.constants.Config;
import com.letzunite.applabs.logger.Logger;
import com.letzunite.applabs.logger.LoggerEnable;
import com.letzunite.applabs.network.ServerConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Interface of {@link ServerConnection} uses {@link HttpURLConnection} to send and receive data over the
 * web.
 *
 * @author Akash Patra
 */
public abstract class BaseHttpURLConnection extends ServerConnection {

    // For Logging
    private final LoggerEnable CLASS_NAME = LoggerEnable.BaseHttpURLConnection;

    HttpURLConnection connection;

    @Override
    protected void addReqHeaders(HashMap<String, String> reqHeaders) {
        Iterator it = reqHeaders.entrySet().iterator();
        Logger.logD(Config.TAG, CLASS_NAME, " >> addReqHeaders >>\n");
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Logger.logD(Config.TAG, CLASS_NAME, " >> ", pair.getKey(),
                    " = ", pair.getValue(), "\n");
            connection.setRequestProperty(pair.getKey() + "", pair.getValue() + "");
            it.remove();
        }
    }

    @Override
    protected String readInputStream() throws IOException {
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();
    }
}
