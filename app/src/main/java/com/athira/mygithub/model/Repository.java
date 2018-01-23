package com.athira.mygithub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Athira.
 */

public class Repository {

    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("stargazers_count")
    private Integer stars;
    @SerializedName("watchers")
    private Integer watchers;
    @SerializedName("forks")
    private Integer forks;
    @SerializedName("full_name")
    private String owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStars() {
        return stars;
    }

    public Integer getWatchers() {
        return watchers;
    }

    public Integer getForks() {
        return forks;
    }

    public String getOwner() {
        return owner;
    }
}