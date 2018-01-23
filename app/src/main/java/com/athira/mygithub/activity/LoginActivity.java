package com.athira.mygithub.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.athira.mygithub.R;
import com.athira.mygithub.helper.Utils;
import com.athira.mygithub.model.AccessToken;
import com.athira.mygithub.rest.ApiClient;
import com.athira.mygithub.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Athira.
 */

public class LoginActivity extends AppCompatActivity {

    private final String clientId = "CLIENT_ID";
    private final String clientSecret = "CLIENT_SECRET";
    private final String redirectUri = "REDIRECT_URI";
    SharedPreferences sharedpreferences;
    Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        TextView login = (TextView) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.haveNetworkConnection(LoginActivity.this)) {
                    Intent intent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://github.com/login/oauth/authorize" + "?client_id=" + clientId + "&scope=user&redirect_uri=" + redirectUri));
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(redirectUri)) {

            String code = null;
            try {
                code = uri.getQueryParameter("code");
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (code != null) {
                // get access token
                ApiInterface apiInterface = ApiClient.getLoginClient();
                Call<AccessToken> accessTokenCall = apiInterface.getAccessToken(clientId, clientSecret, code);
                accessTokenCall.enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                        if (response.code() == 200) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("token", response.body().getAccessToken());
                            editor.commit();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else
                            Toast.makeText(LoginActivity.this, "Sorry! Please try again", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Sorry! Please try again", Toast.LENGTH_LONG).show();
                    }
                });

            } else if (uri.getQueryParameter("error") != null) {
                Toast.makeText(LoginActivity.this, "" + uri.getQueryParameter("error"), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}