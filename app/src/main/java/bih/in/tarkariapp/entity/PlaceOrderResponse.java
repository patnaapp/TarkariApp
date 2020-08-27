package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlaceOrderResponse {


    private String Result;

    @SerializedName("Status")
    private Boolean status;

    @SerializedName("Message")
    private String msg;

    @SerializedName("lstTelaMandi")
    private ArrayList<GetVegEntity> data;

    public PlaceOrderResponse(String result, Boolean status, String msg, ArrayList<GetVegEntity> vegdata)
    {
        this.Result = result;
        this.status = status;
        this.msg = msg;
        this.data = vegdata;

    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
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

    public ArrayList<GetVegEntity> getData() {
        return data;
    }

    public void setData(ArrayList<GetVegEntity> data) {
        this.data = data;
    }
}
