package com.example.withme;

import com.google.gson.annotations.SerializedName;

public class GetProfile {
    @SerializedName("success")
    private boolean success;

    @SerializedName("status")
    private int status;

//    @SerializedName("data")
//    private Data data;
//
//    public class Data {
//        @SerializedName("id")
//        private int id;
//
//        @SerializedName("name")
//        private String name;
//
//        @SerializedName("email")
//        private String email;
//
//        @SerializedName("pwd")
//        private String pwd;
//
//        @SerializedName("address")
//        private String address;
//
//        @SerializedName("phone")
//        private String phone;
//
//        @SerializedName("type")
//        private int type;
//
//        @SerializedName("profileImg")
//        private String profileImg;
//
//        @SerializedName("uid")
//        private String uid;
//
//        public int getId() {
//            return id;
//        }
//
//        public String getUid() {
//            return uid;
//        }
//
//        public int getType() {
//            return type;
//        }
//
//        public String getPwd() {
//            return pwd;
//        }
//
//        public String getProfileImg() {
//            return profileImg;
//        }
//
//        public String getPhone() {
//            return phone;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public void setPhone(String phone) {
//            this.phone = phone;
//        }
//
//        public void setProfileImg(String profileImg) {
//            this.profileImg = profileImg;
//        }
//
//        public void setPwd(String pwd) {
//            this.pwd = pwd;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//
//        public void setUid(String uid) {
//            this.uid = uid;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//    }

//    public Data getData() {
//        return data;
//    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

//    public void setData(Data data) {
//        this.data = data;
//    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
