package com.athira.mygithub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Athira.
 */

public class AccessToken {
    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
