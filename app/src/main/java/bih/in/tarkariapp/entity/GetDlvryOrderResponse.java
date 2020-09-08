package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetDlvryOrderResponse {

    @SerializedName("Status")
    private Boolean Status;

    @SerializedName("Message")
    private String msg;

    @SerializedName("lstVenderOrder")
    private ArrayList<GetDeliveryOrderEntity> data;

    public GetDlvryOrderResponse(Boolean status, String msg, ArrayList<GetDeliveryOrderEntity> vegdata)
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

    public ArrayList<GetDeliveryOrderEntity> getData() {
        return data;
    }

    public void setData(ArrayList<GetDeliveryOrderEntity> data) {
        this.data = data;
    }
}
