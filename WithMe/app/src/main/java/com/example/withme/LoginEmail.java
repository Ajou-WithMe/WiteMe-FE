package com.example.withme;

import com.google.gson.annotations.SerializedName;

public class LoginEmail {

    @SerializedName("email")
    private String email;

    @SerializedName("pwd")
    private String pwd;

    @SerializedName("success")
    private boolean success;

    @SerializedName("status")
    private int status;

    @SerializedName("data")
    private String data;

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPwd() { return pwd; }

    public void setPwd(String pwd) { this.pwd = pwd; }

    public boolean getSuccess() { return success; }

    public void setSuccess(boolean success) { this.success = success; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status;}

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }
}
