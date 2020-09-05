package bih.in.tarkariapp.activity.thelawala;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.adaptor.ConfirmOrderAdaptor;
import bih.in.tarkariapp.adaptor.ConfirmStockAdaptor;
import bih.in.tarkariapp.entity.GetVegEntity;
import bih.in.tarkariapp.entity.GetVegStockEntity;
import bih.in.tarkariapp.entity.PlaceOrderResponse;
import bih.in.tarkariapp.utility.Utiilties;
import bih.in.tarkariapp.webService.Api;
import bih.in.tarkariapp.webService.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConfirmStockFarmerActivity extends AppCompatActivity {

    RecyclerView listView;
    TextView tv_Norecord;
    String userid;
    ArrayList<GetVegStockEntity> orderList;
    ConfirmStockAdaptor adapter;
    String stockdate="",thela_id;
    Button buton_placeOrder_confrm;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        listView = findViewById(R.id.listviewshow_cnfrm);
        buton_placeOrder_confrm = findViewById(R.id.buton_placeOrder_confrm);
        tv_Norecord = findViewById(R.id.tv_Norecord_cnfrm);
        img_back=(ImageView) findViewById(R.id.img);
        stockdate=getIntent().getStringExtra("stock");
        userid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("uid", "");
        thela_id= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("thelaid", "");

        orderList = (ArrayList<GetVegStockEntity>) getIntent().getSerializableExtra("orderlist");
        if (orderList.size()>0){
            populateData();
        }


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buton_placeOrder_confrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ConfirmStockFarmerActivity.this);
                alertDialogBuilder.setMessage("Are you sure,You want to place order");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                PlaceOrder();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

    }


    public void populateData(){
        if(orderList != null && orderList.size()> 0){
            Log.e("data", ""+orderList.size());
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adapter = new ConfirmStockAdaptor(this, orderList);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adapter);

        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

//    public JsonArray getorder_json()
//    {
//        JsonArray orderarray= new JsonArray();
//        for (GetVegStockEntity item:orderList)
//        {
//            JsonObject param = new JsonObject();
//            param.addProperty("vegid", item.get());
//            param.addProperty("orderquantity", item.getVegQty());
//            orderarray.add(param);
//        }
//
//        return orderarray;
//    }


    private void PlaceOrder()
    {
        if(Utiilties.isOnline(ConfirmStockFarmerActivity.this))
        {
            JsonObject param = new JsonObject();
            param.addProperty("telaid", thela_id);
            param.addProperty("Orderdate", stockdate);
            // param.addProperty("lstVeg", getorder_json());
            //   param.add("lstVeg", getorder_json());

            Log.e("param", param.toString());

            final ProgressDialog dialog = new ProgressDialog(ConfirmStockFarmerActivity.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Uploading...");
            dialog.show();

            Api request = RetrofitClient.getRetrofitInstance().create(Api.class);

            Call<PlaceOrderResponse> call = null;

            call = request.PlaceOrderApi(param);



            call.enqueue(new Callback<PlaceOrderResponse>()
            {
                @Override
                public void onResponse(Call<PlaceOrderResponse> call, Response<PlaceOrderResponse> response)
                {
                    if (dialog.isShowing()) dialog.dismiss();

                    PlaceOrderResponse userDetail = response.body();

                    if(userDetail != null)
                    {
                        // if(userDetail.getStatus() && (userDetail.getData().getRole().equals("MEMBER")||userDetail.getData().getRole().equals("THELA"))) {
                        if(userDetail.getStatus() ) {

                            AlertDialog.Builder ab = new AlertDialog.Builder(ConfirmStockFarmerActivity.this);
                            ab.setTitle("सफल रहा");
                            ab.setMessage("आर्डर सफलतापूर्वक अपलोड हुआ");
                            ab.setPositiveButton("ओके", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int whichButton)
                                {
                                    finish();
                                }
                            });

                            ab.create().getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                            ab.show();

                        }
                        else
                        {
                            Toast.makeText(ConfirmStockFarmerActivity.this, userDetail.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getContext(), response.body().getRoleName(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        AlertDialog.Builder ab = new AlertDialog.Builder(ConfirmStockFarmerActivity.this);
                        ab.setTitle("Server Down");
                        ab.setMessage("Server Down, Please try again later!");
                        ab.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton)
                            {

                                dialog.dismiss();
                            }
                        });

                        ab.create().getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                        ab.show();
                    }

                }

                @Override
                public void onFailure(Call<PlaceOrderResponse> call, Throwable t)
                {
                    if (dialog.isShowing()) dialog.dismiss();
                    Toast.makeText(ConfirmStockFarmerActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            showAlertDialog();
        }
    }

    public void showAlertDialog()
    {
        new AlertDialog.Builder(ConfirmStockFarmerActivity.this)
                .setIcon(R.drawable.logo)
                .setTitle(R.string.app_name)
                .setMessage("इन्टरनेट कनेक्शन उपलब्ध नहीं है..\nकृपया नेटवर्क कनेक्शन चालू करे")
                .setCancelable(false)
                .setPositiveButton("नेटवर्क कनेक्शन चालू करे", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent I = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(I);
                    }
                })
                .setNegativeButton("कैंसिल", null)
                .show();
    }


}
