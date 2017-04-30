package com.letzunite.applabs.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.letzunite.applabs.R;
import com.letzunite.applabs.constants.Config;
import com.letzunite.applabs.constants.Constants;
import com.letzunite.applabs.logger.Logger;
import com.letzunite.applabs.logger.LoggerEnable;
import com.letzunite.applabs.utils.AppUtils;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    // For Logging
    private final LoggerEnable CLASS_NAME = LoggerEnable.LoginFragment;
    // Google
    private final int GOOGLE_SIGN_IN = 1002;
    private View currentView;
    private IActivityFragmentInteraction listener;
    // Facebook
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;

    // Views
    private CoordinatorLayout clLogin;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Logger.logD(Config.TAG, CLASS_NAME, " >> onCreate ");

            // Configure sign-in to request the user's ID, email address, and basic
            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestProfile()
                    .requestIdToken(getString(R.string.google_server_client_id))
                    .requestEmail()
                    .build();

            // Build a GoogleApiClient with access to the Google Sign-In API and the
            // options specified by gso.
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity() /* FragmentActivity */,
                            this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        } catch (Exception e) {
            Logger.logE(Config.TAG, e, CLASS_NAME, " >> onCreate >> Exception: " + e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_login, container, false);
        return currentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            referenceViews();
            setListeners();

            initFacebookViews(view);
            initGoogleViews(view);
        } catch (Exception e) {
            Logger.logE(Config.TAG, e, CLASS_NAME, " >> onViewCreated >> Exception: " + e);
        }
    }

    private void referenceViews() {
        clLogin = (CoordinatorLayout) currentView.findViewById(R.id.cl_login);
        etEmail = (EditText) currentView.findViewById(R.id.et_email);
        etPassword = (EditText) currentView.findViewById(R.id.et_password);
        etConfirmPassword = (EditText) currentView.findViewById(R.id.et_confirm_password);
    }

    private void setListeners() {
        currentView.findViewById(R.id.tv_sign_up).setOnClickListener(this);
        currentView.findViewById(R.id.tv_already_account).setOnClickListener(this);

        currentView.findViewById(R.id.btn_sign_up).setOnClickListener(this);
        currentView.findViewById(R.id.btn_sign_in).setOnClickListener(this);

//        ((EditText) currentView.findViewById(R.id.et_password)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    currentView.findViewById(R.id.btn_sign_in).performClick();
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    private void initFacebookViews(View view) {
        // Facebook
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) view.findViewById(R.id.btn_fb_sign_in);
        loginButton.setFragment(this);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            private ProfileTracker profileTracker;

            @Override
            public void onSuccess(LoginResult loginResult) {
                if (null == Profile.getCurrentProfile()) {
                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Logger.logD(Config.TAG, CLASS_NAME, " >> CurrentProfileChanged");
                            profileTracker.stopTracking();
                            initiateFbGraphRequest(currentProfile);
                        }
                    };
                } else {
                    Profile profile = Profile.getCurrentProfile();
                    initiateFbGraphRequest(profile);
                }
            }

            @Override
            public void onCancel() {
                Logger.logD(Config.TAG, CLASS_NAME, " >> onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Logger.logD(Config.TAG, CLASS_NAME, " >> onError");
            }
        });
    }

    private void initiateFbGraphRequest(final Profile profile) {
        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large),birthday");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            processFbGraphResponse(profile, response);
                        }
                    }
                }).executeAsync();
    }

    private void processFbGraphResponse(Profile profile, GraphResponse response) {
        try {
            StringBuilder tempProfile = new StringBuilder();
            tempProfile.append("Id: ").append(profile.getId())
                    .append("\nFirstName: ").append(profile.getFirstName())
                    .append("\nMiddleName: ").append(profile.getMiddleName())
                    .append("\nLastName: ").append(profile.getLastName());

            JSONObject data = response.getJSONObject();
            if (data.has("gender")) {
                tempProfile.append("\nGender: " + data.getString("gender"));
            }
            if (data.has("birthday")) {
                tempProfile.append("\nBirthday: " + data.getString("birthday"));
            }
            if (data.has("email")) {
                tempProfile.append("\nEmail: " + data.getString("email"));
            }
            if (data.has("picture")) {
                tempProfile.append("\nEmail: " + data.getJSONObject("picture").getJSONObject("data").getString("url"));
            }
            Logger.logD(Config.TAG, CLASS_NAME, " >> processFbGraphResponse >> " + tempProfile);
        } catch (Exception e) {
            Logger.logE(Config.TAG, e, CLASS_NAME, " >> processFbGraphResponse >> Exception: " + e);
        }
    }

    private void initGoogleViews(View view) {
        // Google
        // Set the dimensions of the sign-in button.
        btnSignIn = (SignInButton) view.findViewById(R.id.btn_google_sign_in);
        btnSignIn.setSize(SignInButton.SIZE_ICON_ONLY);
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    private void googleSignOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        try {
            int id = v.getId();
            String message;
            switch (id) {
                case R.id.tv_sign_up:
                    toggleLoginScreen(false);
                    break;
                case R.id.tv_already_account:
                    toggleLoginScreen(true);
                    break;
                case R.id.btn_sign_up:
                    message = validateDetails();
                    if (AppUtils.isStringEmpty(message)) {

                    } else {
                        // SnackBar Message
                        Snackbar snackbar = Snackbar
                                .make(clLogin, message, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    break;
                case R.id.btn_sign_in:
                    message = validateDetails();
                    if (AppUtils.isStringEmpty(message)) {

                    } else {
                        // SnackBar Message
                        Snackbar snackbar = Snackbar
                                .make(clLogin, message, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Logger.logE(Config.TAG, e, CLASS_NAME, " >> onClick >> Exception: " + e);
        }
    }

    private void toggleLoginScreen(boolean isSignInScreen) {
        if (isSignInScreen) {
            currentView.findViewById(R.id.rl_cnf_pwd).setVisibility(View.GONE);
            currentView.findViewById(R.id.btn_sign_up).setVisibility(View.GONE);
            currentView.findViewById(R.id.btn_sign_in).setVisibility(View.VISIBLE);

            currentView.findViewById(R.id.tv_forgot_password).setVisibility(View.VISIBLE);
            currentView.findViewById(R.id.tv_already_account).setVisibility(View.GONE);
            currentView.findViewById(R.id.tv_sign_up).setVisibility(View.VISIBLE);
        } else {
            currentView.findViewById(R.id.rl_cnf_pwd).setVisibility(View.VISIBLE);
            currentView.findViewById(R.id.btn_sign_up).setVisibility(View.VISIBLE);
            currentView.findViewById(R.id.btn_sign_in).setVisibility(View.INVISIBLE);

            currentView.findViewById(R.id.tv_forgot_password).setVisibility(View.INVISIBLE);
            currentView.findViewById(R.id.tv_already_account).setVisibility(View.VISIBLE);
            currentView.findViewById(R.id.tv_sign_up).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IActivityFragmentInteraction) {
            listener = (IActivityFragmentInteraction) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement IActivityFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        Logger.logD(Config.TAG, CLASS_NAME, " >> handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            // Signed in successfully, show authenticated UI.
            processGoogleResponse(acct);
        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    private void processGoogleResponse(GoogleSignInAccount account) {
        StringBuilder tempProfile = new StringBuilder();
        // If Reqd, send token to Server
        tempProfile.append("Id: ").append(account.getId())
                .append("\nToken: ").append(account.getIdToken())
                .append("\nFirstName: ").append(account.getGivenName())
                .append("\nLastName: ").append(account.getFamilyName());

        if (account.getEmail() != null) {
            tempProfile.append("\nEmail: " + account.getEmail());
        }
        Logger.logD(Config.TAG, CLASS_NAME, " >> processGoogleResponse >> " + tempProfile);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private String validateDetails() {
        String emailId = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        // Check Confirm Password Field is Visible or not.
        if (!etConfirmPassword.isShown()) {
            // Check Sign In Information
            if (AppUtils.isStringEmpty(emailId)) {
                return "Please enter email";
            }
            if (!AppUtils.isStringEmpty(emailId)
                    && !emailId.matches(Constants.EMAIL_REG)) {
                return "Please enter valid email";
            }
            if (AppUtils.isStringEmpty(password)) {
                return "Please enter password";
            }
        } else {
            // Check Sign Up Information
            String confirmPassword = etConfirmPassword.getText().toString();
            if (AppUtils.isStringEmpty(emailId)) {
                return "Please enter email";
            }
            if (!AppUtils.isStringEmpty(emailId)
                    && !emailId.matches(Constants.EMAIL_REG)) {
                return "Please enter valid email";
            }
            if (AppUtils.isStringEmpty(password)) {
                return "Please enter password";
            }
            if (AppUtils.isStringEmpty(confirmPassword)) {
                return "Please enter confirm password";
            }
            if (!password.equals(confirmPassword)) {
                return "Password & confirm password mismatched";
            }
        }
        return null;
    }

}
