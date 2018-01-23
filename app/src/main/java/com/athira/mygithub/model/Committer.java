package com.athira.mygithub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Athira.
 */

public class Committer {
    @SerializedName("name")
    private String name;
    @SerializedName("date")
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

}
