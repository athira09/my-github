package com.athira.mygithub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Athira.
 */

public class Commit {
    @SerializedName("commit")
    private CommitInfo commitInfo;

    public CommitInfo getCommitInfo() {
        return commitInfo;
    }
}
