package com.letzunite.applabs.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private final int FB_SIGN_IN = 1001;
    private final int GOOGLE_SIGN_IN = 1002;
    // Facebook
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView tvFbProfile;
    private ImageView ivFbProfilePic;
    // Google
    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;
    private Button btnSignOut;
    private TextView tvGoogleProfile;
    private ImageView ivGoogleProfilePic;

    public SocialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_social, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFacebookViews(view);

        initGoogleViews(view);
    }

    private void initFacebookViews(View view) {
        // Facebook
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) view.findViewById(R.id.btn_fb_sign_in);
        tvFbProfile = (TextView) view.findViewById(R.id.tv_fb_profile_info);
        ivFbProfilePic = (ImageView) view.findViewById(R.id.iv_fb_profile_pic);
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
                            Log.d("LetzUnite", "CurrentProfileChanged");
                            profileTracker.stopTracking();
                            displayProfile(currentProfile);
                        }
                    };
                } else {
                    Profile profile = Profile.getCurrentProfile();
                    displayProfile(profile);
                }
            }

            @Override
            public void onCancel() {
                Log.d("LetzUnite", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("LetzUnite", "onError >> " + error);
            }
        });
    }

    private void initGoogleViews(View view) {
        // Google
        // Set the dimensions of the sign-in button.
        btnSignIn = (SignInButton) view.findViewById(R.id.btn_google_sign_in);
        btnSignIn.setSize(SignInButton.SIZE_ICON_ONLY);
        btnSignOut = (Button) view.findViewById(R.id.sign_out_button);
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                googleSignOut();
            }
        });

        tvGoogleProfile = (TextView) view.findViewById(R.id.tv_google_profile_info);
        ivGoogleProfilePic = (ImageView) view.findViewById(R.id.iv_google_profile_pic);
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    private void googleSignOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
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
        Log.d(Config.TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            // Signed in successfully, show authenticated UI.
            processGoogleResponse(acct);
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void processGoogleResponse(GoogleSignInAccount account) {
        StringBuilder tempProfile = new StringBuilder();
        // If Reqd, send token to Server
        Log.d("LetzUnite", "ID Token: " + account.getIdToken());
        tempProfile.append("Id: ").append(account.getId())
                .append("Token: ").append(account.getIdToken())
                .append("\nFirstName: ").append(account.getGivenName())
                .append("\nLastName: ").append(account.getFamilyName());

//        if (account.get) {
//            tempProfile.append("\nGender: " + data.getString("gender"));
//        }
//        if (data.has("birthday")) {
//            tempProfile.append("\nBirthday: " + data.getString("birthday"));
//        }
        if (account.getEmail() != null) {
            tempProfile.append("\nEmail: " + account.getEmail());
        }

        tvGoogleProfile.setText(tempProfile);

        if (account.getPhotoUrl() != null) {
            Glide.with(getApplicationContext())
                    .load(account.getPhotoUrl())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivGoogleProfilePic);
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            tvGoogleProfile.setVisibility(View.VISIBLE);
            ivGoogleProfilePic.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            tvGoogleProfile.setText("");
            tvGoogleProfile.setVisibility(View.GONE);
            ivGoogleProfilePic.setImageBitmap(null);
            ivGoogleProfilePic.setVisibility(View.GONE);
        }
    }

    private void displayProfile(final Profile profile) {
        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large),birthday");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            processGraphResponse(profile, response);
                        }
                    }
                }).executeAsync();
    }

    private void processGraphResponse(Profile profile, GraphResponse response) {
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
            tvFbProfile.setText(tempProfile);

            if (data.has("picture")) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                URL url = new URL(profilePicUrl);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//                                    connection.setInstanceFollowRedirects(false);
                Bitmap profileBitmap = BitmapFactory.decodeStream(connection.getInputStream());
                ivFbProfilePic.setImageBitmap(profileBitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap decodeSampleBitmapFromResource(Uri uri, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri.getPath(), options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(uri.getPath(), options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keep both
            // height and width larger than the required height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
