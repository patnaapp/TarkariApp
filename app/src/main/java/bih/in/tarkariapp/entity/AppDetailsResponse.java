package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

public class AppDetailsResponse {

    @SerializedName("Status")
    private Boolean status;

    @SerializedName("Message")
    private String msg;

    @SerializedName("AppDetails")
    private AppVersion data;


    public AppDetailsResponse(Boolean status, String msg,AppVersion appdata)
    {
        this.status = status;
        this.msg = msg;
        this.data = appdata;

    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public AppVersion getData() {
        return data;
    }

    public void setData(AppVersion data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
