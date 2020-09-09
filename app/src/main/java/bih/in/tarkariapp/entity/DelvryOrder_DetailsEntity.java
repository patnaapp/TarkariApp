package bih.in.tarkariapp.entity;


import java.io.Serializable;

public class DelvryOrder_DetailsEntity implements Serializable {



    private String orderid;
    private String vegname;
    private String quantity;
    private String amount;
    private String isdeliver;




//    public GetVegEntity(Integer id, String veg_id, String veg_nm, String ordr_dt, String rate)
//    {
//        this.id = id;
//        this.vegid = veg_id;
//        this.vegname = veg_nm;
//        this.orderdate = ordr_dt;
//        this.actualrate = rate;
//
//    }


    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

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

    public String getIsdeliver() {
        return isdeliver;
    }

    public void setIsdeliver(String isdeliver) {
        this.isdeliver = isdeliver;
    }
}
