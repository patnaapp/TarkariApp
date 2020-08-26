package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetVegResponse {

    @SerializedName("lstTelaMandi")
    private ArrayList<GetVegEntity> data;

    public ArrayList<GetVegEntity> getData() {
        return data;
    }



}
