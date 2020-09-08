package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DlvryOrder_Detail_Response {

    @SerializedName("Status")
    private Boolean Status;

        @SerializedName("Message")
    private String msg;

    @SerializedName("lstVendorVeg")
    private ArrayList<DelvryOrder_DetailsEntity> data;

    public DlvryOrder_Detail_Response(Boolean status, String msg, ArrayList<DelvryOrder_DetailsEntity> vegdata)
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

    public ArrayList<DelvryOrder_DetailsEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DelvryOrder_DetailsEntity> data) {
        this.data = data;
    }
}
