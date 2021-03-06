package com.auth0.authorizationdemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.lock.AuthenticationCallback;
import com.auth0.android.lock.Lock;
import com.auth0.android.lock.LockCallback;
import com.auth0.android.lock.utils.LockException;
import com.auth0.android.result.Credentials;
import com.auth0.authorizationdemo.R;
import com.auth0.authorizationdemo.application.App;


public class LockActivity extends Activity {

    private Lock mLock;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Auth0 auth0 = new Auth0(getString(R.string.auth0_client_id), getString(R.string.auth0_domain));
        this.mLock = Lock.newBuilder(auth0, mCallback)
                //Add parameters to the build
                .build();
        mLock.onCreate(this);
        auth0.getAuthorizeUrl();
        startActivity(this.mLock.newIntent(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Your own Activity code
        mLock.onDestroy(this);
        mLock = null;
    }

    private final LockCallback mCallback = new AuthenticationCallback() {
        @Override
        public void onAuthentication(Credentials credentials) {
            Toast.makeText(getApplicationContext(), "Log In - Success", Toast.LENGTH_SHORT).show();
            App.getInstance().setmUserCredentials(credentials);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        @Override
        public void onCanceled() {
            Toast.makeText(getApplicationContext(), "Log In - Cancelled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(LockException error) {
            Toast.makeText(getApplicationContext(), "Log In - Error Occurred", Toast.LENGTH_SHORT).show();
        }
    };

}


