package bih.in.tarkariapp.activity.farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.adaptor.order_detailsnotification_adaptor;
import bih.in.tarkariapp.entity.ApprovedOrderResponse;
import bih.in.tarkariapp.entity.Order_DetailsEntity;
import bih.in.tarkariapp.utility.Utiilties;
import bih.in.tarkariapp.webService.Api;
import bih.in.tarkariapp.webService.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAllotedDetails_Activity extends AppCompatActivity {

    String orderdate="",reg_no="";
    ArrayList<Order_DetailsEntity> data;
    ArrayList<Order_DetailsEntity> data1;
    TextView tv_Norecord_details,tv_Norecord1;
    RecyclerView listView,listView1;
    order_detailsnotification_adaptor adapter;
    order_detailsnotification_adaptor adapter1;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_alloted_details);

        img_back=(ImageView) findViewById(R.id.img);

        tv_Norecord_details=findViewById(R.id.tv_Norecord_details);
        listView=findViewById(R.id.listviewshow_details);
        listView1=findViewById(R.id.listviewshow1);
        tv_Norecord1=findViewById(R.id.tv_Norecord1);

        orderdate=getIntent().getStringExtra("orderdate");
        reg_no= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("reg_id", "");

        loadorderDetailsNotificationst();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void loadorderDetailsNotificationst()
    {
        if(Utiilties.isOnline(OrderAllotedDetails_Activity.this))
        {
            JsonObject param = new JsonObject();
            // param.addProperty("Exceptdate", deliverydate);
            param.addProperty("registrationno", reg_no);
            param.addProperty("orderDate", orderdate);

            Log.e("param", param.toString());

            final ProgressDialog dialog = new ProgressDialog(OrderAllotedDetails_Activity.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Loading order list...");
            dialog.show();

            Api request = RetrofitClient.getRetrofitInstance().create(Api.class);

            Call<ApprovedOrderResponse> call = null;

            call = request.GetOrderDetailsNotification(param);

            call.enqueue(new Callback<ApprovedOrderResponse>()
            {
                @Override
                public void onResponse(Call<ApprovedOrderResponse> call, Response<ApprovedOrderResponse> response)
                {
                    if (dialog.isShowing()) dialog.dismiss();

                    ApprovedOrderResponse loadnotification = response.body();

                    if(loadnotification != null)
                    {
                        if (loadnotification.getStatus())
                        {
                            data=loadnotification.getData();
                            data1=loadnotification.getData1();
                            populateData();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                        //Toast.makeText(getContext(), response.body().getRoleName(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        AlertDialog.Builder ab = new AlertDialog.Builder(OrderAllotedDetails_Activity.this);
                        ab.setTitle("सर्वर डाउन");
                        ab.setMessage("सर्वर डाउन है कृपया बाद में प्रयास करे");
                        ab.setPositiveButton("ओके", new DialogInterface.OnClickListener()
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
                public void onFailure(Call<ApprovedOrderResponse> call, Throwable t)
                {
                    if (dialog.isShowing()) dialog.dismiss();
                    Toast.makeText(OrderAllotedDetails_Activity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(OrderAllotedDetails_Activity.this)
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

    public void populateData()
    {
        if(data != null && data.size()> 0)
        {
            Log.e("data", ""+data.size());
            tv_Norecord_details.setVisibility(View.GONE);
            tv_Norecord1.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView1.setVisibility(View.VISIBLE);

            adapter = new order_detailsnotification_adaptor(this, data);
            adapter1 = new order_detailsnotification_adaptor(this, data1);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView1.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adapter);
            listView1.setAdapter(adapter1);

        }
        else
        {
            listView.setVisibility(View.GONE);
            listView1.setVisibility(View.GONE);
            tv_Norecord_details.setVisibility(View.VISIBLE);
            tv_Norecord1.setVisibility(View.VISIBLE);
        }
    }

}
