package bih.in.tarkariapp.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetVegStockResponse {


    @SerializedName("lstVeg")
    private ArrayList<GetVegStockEntity> data;

    public GetVegStockResponse(ArrayList<GetVegStockEntity> vegdata)
    {

        this.data = vegdata;

    }

    public ArrayList<GetVegStockEntity> getData() {
        return data;
    }

    public void setData(ArrayList<GetVegStockEntity> data) {
        this.data = data;
    }
}
