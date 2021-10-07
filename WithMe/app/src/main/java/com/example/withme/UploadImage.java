package com.example.withme;

import com.google.gson.annotations.SerializedName;

public class UploadImage {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private String data;

    @SerializedName("status")
    private int status;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) { this.success = success; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data;}

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status;}
}
