package com.letzunite.applabs.network.httpURLConnection;

import com.letzunite.applabs.exceptions.InvalidMethodException;
import com.letzunite.applabs.network.BaseNetworkController;
import com.letzunite.applabs.network.ServerConnection;

/**
 * The HttpURLConnectionFactory returns an implementation of the {@link ServerConnection} class based
 * on the HttpMethod.
 *
 * @author Akash Patra
 */
public class HttpURLConnectionFactory {

    /**
     * Returns an ServerConnection implementation based on the value of the method
     *
     * @param methodType the type of connection requested
     * @return
     * @throws InvalidMethodException if the value of http method is invalid
     */
    public static ServerConnection getConnection(BaseNetworkController.MethodType methodType)
            throws InvalidMethodException {
        ServerConnection serverConnection = null;
        switch (methodType) {
            case GET:
                serverConnection = new GetHttpURLConnection();
                break;
            case POST:
                serverConnection = new PostHttpURLConnection();
                break;
        }
        if (null == serverConnection) {
            throw new InvalidMethodException();
        }
        return serverConnection;
    }

}
