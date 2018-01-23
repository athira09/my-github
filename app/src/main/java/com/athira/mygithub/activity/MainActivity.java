package com.athira.mygithub.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.athira.mygithub.R;
import com.athira.mygithub.adapter.RepositoryAdapter;
import com.athira.mygithub.helper.Utils;
import com.athira.mygithub.model.Repository;
import com.athira.mygithub.rest.ApiClient;
import com.athira.mygithub.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.repository_title));
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            if (sharedpreferences.contains("isLoggedIn")) {
                if (!sharedpreferences.getBoolean("isLoggedIn", false)) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    MainActivity.this.finish();
                } else
                    showRepositories();
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                MainActivity.this.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showRepositories() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient();
        Call<List<Repository>> call = apiInterface.getRepos(sharedpreferences.getString("token", ""));
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if (response.code() == 200) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        if (response.body().size() == 0)
                            Toast.makeText(MainActivity.this, "No Repositories found!", Toast.LENGTH_LONG).show();
                        else {
                            String parts[] = response.body().get(0).getOwner().split("/");
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("owner", parts[0]);
                            editor.commit();
                            RepositoryAdapter repositoryAdapter = new RepositoryAdapter(MainActivity.this, response.body());
                            recyclerView.setAdapter(repositoryAdapter);
                            repositoryAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Sorry! Please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Sorry! Please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
