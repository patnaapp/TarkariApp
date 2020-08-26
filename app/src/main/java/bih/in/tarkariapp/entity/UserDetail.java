package bih.in.tarkariapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;

public class UserDetail implements Serializable
{

    private boolean isAuthenticated = true;

    private String RegistrationNO = "";
    private String UserID = "";

    private String UserName = "";
    private String Role = "";
    private String DistCode = "";
    private String BlockCode = "";

    private String ApplicantMob = "";




    public UserDetail()
    {

    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getRegistrationNO() {
        return RegistrationNO;
    }

    public void setRegistrationNO(String registrationNO) {
        RegistrationNO = registrationNO;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getDistCode() {
        return DistCode;
    }

    public void setDistCode(String distCode) {
        DistCode = distCode;
    }

    public String getBlockCode() {
        return BlockCode;
    }

    public void setBlockCode(String blockCode) {
        BlockCode = blockCode;
    }

    public String getApplicantMob() {
        return ApplicantMob;
    }

    public void setApplicantMob(String applicantMob) {
        ApplicantMob = applicantMob;
    }
}

