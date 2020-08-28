package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChangePassResponse {

    @SerializedName("Result")
    private String result;

    @SerializedName("Status")
    private Boolean status;

    @SerializedName("Message")
    private String msg;


    public ChangePassResponse(String res,Boolean status, String msg)
    {
        this.result = res;
        this.status = status;
        this.msg = msg;


    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
}
