package bih.in.tarkariapp.entity;


import java.io.Serializable;

public class GetVegStockEntity implements Serializable {


    private Integer vegid;

    private String vegnamehn;
    private Boolean isChecked = false;
    private String vegQty;
    private String vegstockdate;


//    public GetVegEntity(Integer id, String veg_id, String veg_nm, String ordr_dt, String rate)
//    {
//        this.id = id;
//        this.vegid = veg_id;
//        this.vegname = veg_nm;
//        this.orderdate = ordr_dt;
//        this.actualrate = rate;
//
//    }

    public void setVegid(Integer vegid) {
        this.vegid = vegid;
    }

    public String getVegnamehn() {
        return vegnamehn;
    }

    public void setVegnamehn(String vegnamehn) {
        this.vegnamehn = vegnamehn;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getVegQty() {
        return vegQty;
    }

    public void setVegQty(String vegQty) {
        this.vegQty = vegQty;
    }

    public String getVegstockdate() {
        return vegstockdate;
    }

    public void setVegstockdate(String vegstockdate) {
        this.vegstockdate = vegstockdate;
    }
}
