package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

public class GetOTPEntity {


    private String GenerateOPTNO;

    @SerializedName("Status")
    private Boolean status;

    @SerializedName("Message")
    private String msg;


    public GetOTPEntity(String otp,Boolean status, String msg)
    {
        this.GenerateOPTNO = otp;
        this.status = status;
        this.msg = msg;

    }

    public String getGenerateOPTNO()
    {
        return GenerateOPTNO;
    }

    public void setGenerateOPTNO(String generateOPTNO)
    {
        GenerateOPTNO = generateOPTNO;
    }

    public Boolean getStatus()
    {
        return status;
    }

    public void setStatus(Boolean status)
    {
        this.status = status;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}
