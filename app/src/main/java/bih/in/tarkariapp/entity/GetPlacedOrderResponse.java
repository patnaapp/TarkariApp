package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetPlacedOrderResponse {

    @SerializedName("Status")
    private Boolean Status;

    @SerializedName("Message")
    private String msg;

    @SerializedName("lstTelawalaOrderDetails")
    private ArrayList<GetOrderPlacedEntity> data;

    public GetPlacedOrderResponse(Boolean status, String msg, ArrayList<GetOrderPlacedEntity> vegdata)
    {
        this.Status = status;
        this.msg = msg;
        this.data = vegdata;

    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<GetOrderPlacedEntity> getData() {
        return data;
    }

    public void setData(ArrayList<GetOrderPlacedEntity> data) {
        this.data = data;
    }
}
