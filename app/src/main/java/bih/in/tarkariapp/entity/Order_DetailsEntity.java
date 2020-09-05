package bih.in.tarkariapp.entity;


import java.io.Serializable;

public class Order_DetailsEntity implements Serializable {



    private String vegname;
    private String quantity;
    private String amount;
    private String deliverydate;




//    public GetVegEntity(Integer id, String veg_id, String veg_nm, String ordr_dt, String rate)
//    {
//        this.id = id;
//        this.vegid = veg_id;
//        this.vegname = veg_nm;
//        this.orderdate = ordr_dt;
//        this.actualrate = rate;
//
//    }


    public String getVegname() {
        return vegname;
    }

    public void setVegname(String vegname) {
        this.vegname = vegname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }
}
