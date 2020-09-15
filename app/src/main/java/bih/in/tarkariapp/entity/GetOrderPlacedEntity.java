package bih.in.tarkariapp.entity;


public class GetOrderPlacedEntity {


    private Integer id;

    private String registrationno;

    private String vegid;

    private String vegname;


    private String telaid;
    private String orderdate;

    private String orderquantity;
    private String entrydate;
    private String entryby;
    private String lstVeg;
    private String isdelivery;


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

    public String getRegistrationno() {
        return registrationno;
    }

    public void setRegistrationno(String registrationno) {
        this.registrationno = registrationno;
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

    public String getTelaid() {
        return telaid;
    }

    public void setTelaid(String telaid) {
        this.telaid = telaid;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrderquantity() {
        return orderquantity;
    }

    public void setOrderquantity(String orderquantity) {
        this.orderquantity = orderquantity;
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String getEntryby() {
        return entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    public String getLstVeg() {
        return lstVeg;
    }

    public void setLstVeg(String lstVeg) {
        this.lstVeg = lstVeg;
    }

    public String getIsdelivery() {
        return isdelivery;
    }

    public void setIsdelivery(String isdelivery) {
        this.isdelivery = isdelivery;
    }
}
