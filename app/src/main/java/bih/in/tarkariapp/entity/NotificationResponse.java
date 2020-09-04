package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationResponse {

    @SerializedName("Status")
    private Boolean status;

    @SerializedName("Message")
    private String msg;

    @SerializedName("lstTelaMandi")
    private ArrayList<OrderDateEntity> data;

    public NotificationResponse(Boolean status, String msg, ArrayList<OrderDateEntity> vegdata)
    {
        this.status = status;
        this.msg = msg;
        this.data = vegdata;

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

    public ArrayList<OrderDateEntity> getData() {
        return data;
    }

    public void setData(ArrayList<OrderDateEntity> data) {
        this.data = data;
    }
}
