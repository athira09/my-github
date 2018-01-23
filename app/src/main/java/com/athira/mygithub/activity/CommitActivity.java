package com.athira.mygithub.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.athira.mygithub.R;
import com.athira.mygithub.adapter.CommitAdapter;
import com.athira.mygithub.helper.Utils;
import com.athira.mygithub.model.Commit;
import com.athira.mygithub.rest.ApiClient;
import com.athira.mygithub.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Athira.
 */

public class CommitActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    Utils utils = new Utils();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (utils.haveNetworkConnection(CommitActivity.this)) {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInterface = ApiClient.getClient();
            Call<List<Commit>> call = apiInterface.getCommits(sharedpreferences.getString("owner", ""), getIntent().getExtras().getString("name"));
            call.enqueue(new Callback<List<Commit>>() {
                @Override
                public void onResponse(Call<List<Commit>> call, Response<List<Commit>> response) {
                    if (response.code() == 200) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            if (response.body().size() == 0)
                                Toast.makeText(CommitActivity.this, "No Commits found!", Toast.LENGTH_LONG).show();
                            else {
                                CommitAdapter commitAdapter = new CommitAdapter(CommitActivity.this, response.body());
                                recyclerView.setAdapter(commitAdapter);
                                commitAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(CommitActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(CommitActivity.this, "Sorry! Please try again", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Commit>> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CommitActivity.this, "Sorry! Please try again", Toast.LENGTH_LONG).show();
                }
            });
        } else
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
