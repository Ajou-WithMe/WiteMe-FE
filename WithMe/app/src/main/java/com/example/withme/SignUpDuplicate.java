package com.example.withme;

import com.google.gson.annotations.SerializedName;

public class SignUpDuplicate {
    @SerializedName("email")
    private String email;

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private boolean data;

    @SerializedName("status")
    private int status;

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) { this.success = success; }

    public boolean getData() { return data; }

    public void setData(boolean data) { this.data = data;}

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status;}

}

