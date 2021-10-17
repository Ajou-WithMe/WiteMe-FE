package com.example.withme.retorfit;

import com.google.gson.annotations.SerializedName;

public class EmailSignUp {
    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("pwd")
    private String pwd;

    @SerializedName("address")
    private String address;

    @SerializedName("phone")
    private String phone;

    @SerializedName("success")
    private boolean success;

    @SerializedName("status")
    private int status;

    public String getName() { return name;}

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPwd() { return pwd; }

    public void setPwd(String pwd) { this.pwd = pwd; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone;}

    public boolean getSuccess() { return success; }

    public void setSuccess(boolean success) { this.success = success; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status;}

}
