package com.letzunite.applabs.network.httpURLConnection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.letzunite.applabs.constants.Config;
import com.letzunite.applabs.exceptions.CustomConnectionException;
import com.letzunite.applabs.exceptions.InvalidMethodException;
import com.letzunite.applabs.logger.Logger;
import com.letzunite.applabs.logger.LoggerEnable;
import com.letzunite.applabs.network.IWebServiceListener;
import com.letzunite.applabs.network.ServerConnection;
import com.letzunite.applabs.network.ServiceVO;

/**
 * Connector class for creating connection and getting response from exposed service using
 * {@link java.net.HttpURLConnection}.
 *
 * @author Akash Patra
 */
public class HttpURLConnectionConnector<T> {

    // For Logging
    private final LoggerEnable CLASS_NAME = LoggerEnable.HttpURLConnectionConnector;

    private IWebServiceListener listener;
    private Context mContext;
    private ProgressDialog progressDialog;

    public HttpURLConnectionConnector(Context context, IWebServiceListener listener) {
        this.mContext = context;
        this.listener = listener;
    }

    public void initiateTask(ServiceVO serviceVO) {
        new FetchDataTask().execute(serviceVO);
    }

    private void setFailure(int errorCode, String errorMessage, ServiceVO serviceVO) {
        serviceVO.setErrorCode(errorCode);
        serviceVO.setErrorMessage(errorMessage);
    }

    private class FetchDataTask extends AsyncTask<ServiceVO, Void, ServiceVO> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            try {
//                if (AppUtils.isContextAlive((Activity) mContext)) {
//                    progressDialog = new ProgressDialog(mContext, AlertDialog.THEME_TRADITIONAL);
//                    progressDialog.setCancelable(false);
//                    progressDialog.show();
//                    progressDialog.setContentView(R.layout.dialog_progress_ecaf);
//                    ObjectAnimator anim = ObjectAnimator.ofFloat(progressDialog.findViewById(R.id.loadicon), "rotationY", 360).setDuration(1000);
//                    anim.setStartDelay(500);
//                    anim.setRepeatCount(ValueAnimator.INFINITE);
//                    anim.start();
//                }
//            } catch (Exception e) {
//                LogUtils.logE(Constants.TAG, CLASS_NAME + " >> onPreExecute >> Exception: " + e, showClassLog, e);
//            }
        }

        @Override
        protected ServiceVO doInBackground(ServiceVO... params) {
            ServiceVO serviceVO = params[0];
            ServerConnection serverConnection;
            try {
                serverConnection = HttpURLConnectionFactory.
                        getConnection(serviceVO.getMethodType());
                String response = serverConnection.fetchData(serviceVO);
                Logger.logD(Config.TAG, CLASS_NAME, " >> doInBackground >> Response: ", response);
                serviceVO.getWebServiceResponse().processResponse(response, serviceVO);
//                setSuccess(response, serviceVO);
            } catch (InvalidMethodException e) {
                setFailure(1221, e.getMessage(), serviceVO);
            } catch (CustomConnectionException e) {
                setFailure(e.getStatusCode(), "System not Responding", serviceVO);
            } catch (Exception e) {
                setFailure(1334, "System not Responding", serviceVO);
            }
            return serviceVO;
        }

        @Override
        protected void onPostExecute(ServiceVO serviceVO) {
            try {
//                final ErrorBean errorBean = new ErrorBean(String.valueOf(serviceVO.getErrorCode()),
//                        serviceVO.getErrorMessage());

//                if (AppUtils.isContextAlive((Activity) mContext)) {
//                    if (progressDialog != null && progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
//                    if (errorBean.getErrorCode().equalsIgnoreCase("401")) {
//                        if ((ServerConfig.BASE_URL + ServerConfig.LOGIN_URL).equalsIgnoreCase(serviceVO.getUrl())) {
//                            new AlertDialog.Builder(mContext)
//                                    .setMessage("Please enter correct UserName and Password")
//                                    .setCancelable(false)
//                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    })
//                                    .show();
//                        } else {
//                            new AlertDialog.Builder(mContext)
//                                    .setMessage(mContext.getResources().getString(R.string.session_expired_msg))
//                                    .setCancelable(false)
//                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            MySharedPrefs mySharedPrefs = MySharedPrefs.getInstance(mContext, MySharedPrefs.ECAF_PREFS, Context.MODE_PRIVATE);
//                                            mySharedPrefs.putString(MySharedPrefs.ACCESS_TOKEN_EXTRA, null);
//                                            mySharedPrefs.putLong(MySharedPrefs.VALID_TILL_EXTRA, 0);
//                                            Intent intent = new Intent(mContext, LoginActivity.class);
//                                            ((Activity) mContext).startActivityForResult(intent, BaseAuthCheckActivity.REQUEST_LOGIN);
//                                        }
//                                    })
//                                    .show();
//                        }
//                    } else if (serviceVO.isSuccess()) {
//                        listener.onWebServiceSuccess(serviceVO);
//                    } else {
//                        listener.onWebServiceFailure(serviceVO.getErrorMessage());
//                    }
//                }
            } catch (Exception e) {
                Logger.logE(Config.TAG, e, CLASS_NAME, " >> onPostExecute >> Exception: ", e);
            }
        }
    }
}
