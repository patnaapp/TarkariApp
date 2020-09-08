package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

public class GetDeliveryOrderEntity {


    private String OrderId;
    private String amount;

    private String mobile;
    private String Address;
    private String Status;




    public GetDeliveryOrderEntity(String orderid, String amt, String mob,String address,String status)
    {
        this.OrderId = orderid;

        this.amount = amt;
        this.mobile = mob;
        this.Address = address;
        this.Status = status;


    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
