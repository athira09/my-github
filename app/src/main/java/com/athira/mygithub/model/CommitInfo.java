package com.athira.mygithub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Athira.
 */

public class CommitInfo {
    @SerializedName("message")
    private String message;
    @SerializedName("committer")
    private Committer committer;

    public String getMessage() {
        return message;
    }

    public Committer getCommitter() {
        return committer;
    }
}
