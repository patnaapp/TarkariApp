package bih.in.tarkariapp.entity;

public class PlaceVegOrder {

    private String thela_ID;
    private String orderDate;
    private String vegId;
    private String vegName;
    private String veg_Qty;


    public String getThela_ID() {
        return thela_ID;
    }

    public void setThela_ID(String thela_ID) {
        this.thela_ID = thela_ID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getVegId() {
        return vegId;
    }

    public void setVegId(String vegId) {
        this.vegId = vegId;
    }

    public String getVegName() {
        return vegName;
    }

    public void setVegName(String vegName) {
        this.vegName = vegName;
    }

    public String getVeg_Qty() {
        return veg_Qty;
    }

    public void setVeg_Qty(String veg_Qty) {
        this.veg_Qty = veg_Qty;
    }
}
