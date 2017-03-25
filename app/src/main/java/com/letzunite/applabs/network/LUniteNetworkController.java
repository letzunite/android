package com.letzunite.applabs.network;

import android.content.Context;
import android.content.pm.PackageManager;

import java.util.HashMap;

/**
 * Implementation of {@link BaseNetworkController} handles all requests related to CAF.
 *
 * @author Akash Patra
 */
public class LUniteNetworkController extends BaseNetworkController {

    private Context mContext;
    private HashMap<String, String> mReqHeaders;
    private boolean isLoginCall;

    public LUniteNetworkController(Context context) {
        this.mContext = context;
        mReqHeaders = new HashMap<>();
    }

    public void executeLogin(RequestConfig requestConfig, IWebServiceListener listener, String olmId, String password) {
        HashMap<String, String> param = new HashMap<>();
        if (mContext != null) {
            try {
                int versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
                param.put("grant_type", "client_credentials");
                param.put("client_id", olmId);
                param.put("client_secret", password);
                param.put("appVersion", String.valueOf(versionCode));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            throw new NullPointerException("Context is empty");
        }
        isLoginCall = true;
//        executePost(listener, ServerConfig.BASE_URL + ServerConfig.LOGIN_URL, param, ResponseGlobalBean.class);
    }

    @Override
    public HashMap<String, String> getReqHeaders() {
        mReqHeaders.put("User-Agent", "android");
        if (!isLoginCall) {
//            MySharedPrefs pref = MySharedPrefs.getInstance(mContext, MySharedPrefs.ECAF_PREFS, Context.MODE_PRIVATE);
//            mReqHeaders.put("Authorization", "bearer " + pref.getString(MySharedPrefs.ACCESS_TOKEN_EXTRA));
        }
        return mReqHeaders;
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
