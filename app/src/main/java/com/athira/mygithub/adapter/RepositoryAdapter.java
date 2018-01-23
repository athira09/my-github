package com.athira.mygithub.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.athira.mygithub.R;
import com.athira.mygithub.activity.CommitActivity;
import com.athira.mygithub.model.Repository;

import java.util.List;

/**
 * Created by Athira.
 */

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private List<Repository> list;
    private Context context;

    public RepositoryAdapter(Context context, List<Repository> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
        return new RepositoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, final int position) {
        Repository repository = list.get(position);
        holder.mName.setText(repository.getName());
        holder.mDescription.setText(repository.getDescription());
        holder.mWatchers.setText(new StringBuilder().append(repository.getWatchers())
                .append((repository.getWatchers()==1?"\n Watcher":"\n Watchers")));
        holder.mForks.setText(new StringBuilder().append(repository.getForks())
                .append((repository.getForks()==1?"\n Fork":"\n Forks")));
        holder.mStars.setText(new StringBuilder().append(repository.getStars())
                .append((repository.getStars()==1?"\n Star":"\n Stars")));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RepositoryViewHolder extends RecyclerView.ViewHolder {
        TextView mName, mDescription, mWatchers, mStars, mForks;

        public RepositoryViewHolder(final View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.text_repo_name);
            mDescription = (TextView) v.findViewById(R.id.text_repo_description);
            mWatchers = (TextView) v.findViewById(R.id.text_watchers);
            mStars = (TextView) v.findViewById(R.id.text_stars);
            mForks = (TextView) v.findViewById(R.id.text_forks);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CommitActivity.class);
                    intent.putExtra("name", mName.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}

