package bih.in.tarkariapp.entity;

import java.io.Serializable;

public class DeliveryVendorUserDetail implements Serializable
{

    private boolean isAuthenticated = true;

    private Integer id;
    private String name;
    private String registrationno = "";

    private String mobilenumber = "";
    private String DOB = "";
    private String entereddate = "";


    public DeliveryVendorUserDetail()
    {

    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationno() {
        return registrationno;
    }

    public void setRegistrationno(String registrationno) {
        this.registrationno = registrationno;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEntereddate() {
        return entereddate;
    }

    public void setEntereddate(String entereddate) {
        this.entereddate = entereddate;
    }
}

