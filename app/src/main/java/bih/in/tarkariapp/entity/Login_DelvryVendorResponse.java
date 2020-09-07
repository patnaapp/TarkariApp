package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

public class Login_DelvryVendorResponse {

    @SerializedName("Status")
    private Boolean status;

    @SerializedName("Message")
    private String msg;

    @SerializedName("VendorBO")
    private DeliveryVendorUserDetail data;


    public Login_DelvryVendorResponse(Boolean status, String msg, DeliveryVendorUserDetail appdata)
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DeliveryVendorUserDetail getData() {
        return data;
    }

    public void setData(DeliveryVendorUserDetail data) {
        this.data = data;
    }
}
