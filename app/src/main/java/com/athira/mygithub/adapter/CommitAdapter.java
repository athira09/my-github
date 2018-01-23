package com.athira.mygithub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.athira.mygithub.R;
import com.athira.mygithub.model.Commit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Athira.
 */

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.CommitViewHolder> {

    private List<Commit> list;
    private Context context;

    public CommitAdapter(Context context, List<Commit> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CommitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commit, parent, false);
        return new CommitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommitViewHolder holder, final int position) {
        Commit commit = list.get(position);
        holder.mMessage.setText(commit.getCommitInfo().getMessage());
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            sdf1.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date1 = sdf1.parse(commit.getCommitInfo().getCommitter().getDate());
            SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd, yyyy");
            holder.mCommitter.setText(new StringBuilder(commit.getCommitInfo().getCommitter().getName())
                    .append(" committed on ").append(sdf2.format(date1)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CommitViewHolder extends RecyclerView.ViewHolder {
        TextView mMessage, mCommitter;

        public CommitViewHolder(final View v) {
            super(v);
            mMessage = (TextView) v.findViewById(R.id.text_commit_message);
            mCommitter = (TextView) v.findViewById(R.id.text_commit_description);
        }
    }
}

