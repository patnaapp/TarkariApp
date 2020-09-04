package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApprovedOrderResponse
{

    @SerializedName("Status")
    private Boolean status;

    @SerializedName("Message")
    private String msg;

    @SerializedName("lstFormerOrderDetails")
    private ArrayList<Order_DetailsEntity> data;

    public ApprovedOrderResponse(Boolean status, String msg, ArrayList<Order_DetailsEntity> vegdata)
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

    public ArrayList<Order_DetailsEntity> getData() {
        return data;
    }

    public void setData(ArrayList<Order_DetailsEntity> data) {
        this.data = data;
    }
}
