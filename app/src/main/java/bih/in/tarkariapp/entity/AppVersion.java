package bih.in.tarkariapp.entity;

import com.google.gson.annotations.SerializedName;

public class AppVersion {

    @SerializedName("AdminMsg")
    private String adminMsg;

    @SerializedName("AdminTitle")
    private String adminTitle;

    @SerializedName("Priority")
    private String priority;

    @SerializedName("Role")
    private String role;

    @SerializedName("UpdateTitle")
    private String updateTitle;

    @SerializedName("UpdateMsg")
    private String updateMsg;

    @SerializedName("Ver")
    private String version;

    private String aapUrl;

    public AppVersion(String adminMsg, String adminTitle, String priority, String role, String updateTitle, String updateMsg, String version, String aapUrl) {
        this.adminMsg = adminMsg;
        this.adminTitle = adminTitle;
        this.priority = priority;
        this.role = role;
        this.updateTitle = updateTitle;
        this.updateMsg = updateMsg;
        this.version = version;
        this.aapUrl = aapUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUpdateMsg() {
        return updateMsg;
    }

    public void setUpdateMsg(String updateMsg) {
        this.updateMsg = updateMsg;
    }

    public String getAdminMsg() {
        return adminMsg;
    }

    public void setAdminMsg(String adminMsg) {
        this.adminMsg = adminMsg;
    }

    public String getAdminTitle() {
        return adminTitle;
    }

    public void setAdminTitle(String adminTitle) {
        this.adminTitle = adminTitle;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getUpdateTitle() {
        return updateTitle;
    }

    public void setUpdateTitle(String updateTitle) {
        this.updateTitle = updateTitle;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAapUrl() {
        return aapUrl;
    }

    public void setAapUrl(String aapUrl) {
        this.aapUrl = aapUrl;
    }
}
