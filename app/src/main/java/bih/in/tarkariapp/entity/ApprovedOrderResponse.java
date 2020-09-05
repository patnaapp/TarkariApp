package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApprovedOrderResponse
{

    @SerializedName("Status")
    private Boolean status;

    @SerializedName("Message")
    private String msg;

    @SerializedName("lstFarmerOrderDetails")
    private ArrayList<Order_DetailsEntity> data;

    @SerializedName("lstPvcsFarmerOrderDetail")
    private ArrayList<Order_DetailsEntity> data1;

    public ApprovedOrderResponse(Boolean status, String msg, ArrayList<Order_DetailsEntity> vegdata, ArrayList<Order_DetailsEntity> vegdata1)
    {
        this.status = status;
        this.msg = msg;
        this.data = vegdata;
        this.data1 = vegdata1;

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

    public ArrayList<Order_DetailsEntity> getData1() {
        return data1;
    }

    public void setData1(ArrayList<Order_DetailsEntity> data1) {
        this.data1 = data1;
    }
}
