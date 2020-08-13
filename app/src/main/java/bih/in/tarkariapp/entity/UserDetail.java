package bih.in.tarkariapp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;

public class UserDetail implements Serializable {

    private boolean isAuthenticated = true;

    private String Password = "";
    private String UserID = "";

    private String LastVisitedOn = "";
    private String MobileNo = "";
    private String Address = "";
    private String Email = "";
    private String DistrictCode = "";
    private String DistName = "";
    private String BlockCode = "";
    private String BlockName = "";
    private String PanchayatName = "";
    private String PanchayatCode = "";
    private String Degignation = "";
    private String Userrole = "";
    private String UserroleId = "";
    private String Name = "";


    public UserDetail() {
    }

    public String getUserroleId() {
        return UserroleId;
    }

    public void setUserroleId(String userroleId) {
        UserroleId = userroleId;
    }

    public String getPanchayatName() {
        return PanchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        PanchayatName = panchayatName;
    }

    public String getPanchayatCode() {
        return PanchayatCode;
    }

    public void setPanchayatCode(String panchayatCode) {
        PanchayatCode = panchayatCode;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public Boolean getIsAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(Boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public String getLastVisitedOn() {
        return LastVisitedOn;
    }

    public void setLastVisitedOn(String lastVisitedOn) {
        LastVisitedOn = lastVisitedOn;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDistrictCode() {
        return DistrictCode;
    }

    public void setDistrictCode(String districtCode) {
        DistrictCode = districtCode;
    }

    public String getDistName() {
        return DistName;
    }

    public void setDistName(String distName) {
        DistName = distName;
    }

    public String getBlockCode() {
        return BlockCode;
    }

    public void setBlockCode(String blockCode) {
        BlockCode = blockCode;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setBlockName(String blockName) {
        BlockName = blockName;
    }

    public String getDegignation() {
        return Degignation;
    }

    public void setDegignation(String degignation) {
        Degignation = degignation;
    }

    public String getUserrole() {
        return Userrole;
    }

    public void setUserrole(String userrole) {
        Userrole = userrole;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}

