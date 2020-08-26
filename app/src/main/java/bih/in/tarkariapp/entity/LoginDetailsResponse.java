package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

public class LoginDetailsResponse {

    @SerializedName("Status")
    private Boolean status;

    @SerializedName("Message")
    private String msg;

    @SerializedName("MemberData")
    private UserDetail data;

    @SerializedName("TelaWalaDetails")
    private UserDetail data1;

    public LoginDetailsResponse(Boolean status, String msg, UserDetail appdata)
    {
        this.status = status;
        this.msg = msg;
        this.data = appdata;
        this.data1 = appdata;

    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserDetail getData() {
        return data;
    }

    public void setData(UserDetail data) {
        this.data = data;
    }

    public UserDetail getData1() {
        return data1;
    }

    public void setData1(UserDetail data1) {
        this.data1 = data1;
    }
}
