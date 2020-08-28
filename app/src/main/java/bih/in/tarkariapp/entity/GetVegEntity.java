package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetVegEntity implements Serializable {


    private Integer id;

    private String vegid;

    private String vegname;

    private String orderdate;


    private String actualrate;
    private String vegQty;

    private String expecteddel_date;
    private Boolean isChecked = false;

//    public GetVegEntity(Integer id, String veg_id, String veg_nm, String ordr_dt, String rate)
//    {
//        this.id = id;
//        this.vegid = veg_id;
//        this.vegname = veg_nm;
//        this.orderdate = ordr_dt;
//        this.actualrate = rate;
//
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVegid() {
        return vegid;
    }

    public void setVegid(String vegid) {
        this.vegid = vegid;
    }

    public String getVegname() {
        return vegname;
    }

    public void setVegname(String vegname) {
        this.vegname = vegname;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getActualrate() {
        return actualrate;
    }

    public void setActualrate(String actualrate) {
        this.actualrate = actualrate;
    }

    public String getVegQty() {
        return vegQty;
    }

    public void setVegQty(String vegQty) {
        this.vegQty = vegQty;
    }

    public String getExpecteddel_date() {
        return expecteddel_date;
    }

    public void setExpecteddel_date(String expecteddel_date) {
        this.expecteddel_date = expecteddel_date;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }


}
