package bih.in.tarkariapp.activity.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.adaptor.DeliverOrderAdaptor;
import bih.in.tarkariapp.adaptor.DeliverOrder_Detail_Adaptor;
import bih.in.tarkariapp.entity.DelvryOrder_DetailsEntity;
import bih.in.tarkariapp.entity.DlvryOrder_Detail_Response;
import bih.in.tarkariapp.entity.GetDeliveryOrderEntity;
import bih.in.tarkariapp.entity.GetDlvryOrderResponse;
import bih.in.tarkariapp.utility.Utiilties;
import bih.in.tarkariapp.webService.Api;
import bih.in.tarkariapp.webService.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity
{

    String user_id="",date="";
    ArrayList<DelvryOrder_DetailsEntity> data;
    DeliverOrder_Detail_Adaptor adapter;
    TextView tv_Norecord_farmer;
    RecyclerView listView;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        tv_Norecord_farmer=findViewById(R.id.tv_Norecord);
        listView = findViewById(R.id.listviewshow);
        img_back=(ImageView) findViewById(R.id.img);

        user_id=getIntent().getStringExtra("orderid");
        date=getIntent().getStringExtra("order_date");

        loadorderdetailsForDeliveryList();
        img_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }


    private void loadorderdetailsForDeliveryList()
    {
        if(Utiilties.isOnline(OrderDetailsActivity.this))
        {
            JsonObject param = new JsonObject();
             param.addProperty("UserId", user_id);
           param.addProperty("OrderDate", date);

            Log.e("param", param.toString());

            final ProgressDialog dialog = new ProgressDialog(OrderDetailsActivity.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Loading order list...");
            dialog.show();

            Api request = RetrofitClient.getRetrofitInstance().create(Api.class);

            Call<DlvryOrder_Detail_Response> call = null;

            call = request.GetDelvryOrderDetails(param);

            call.enqueue(new Callback<DlvryOrder_Detail_Response>()
            {
                @Override
                public void onResponse(Call<DlvryOrder_Detail_Response> call, Response<DlvryOrder_Detail_Response> response)
                {
                    if (dialog.isShowing()) dialog.dismiss();

                    DlvryOrder_Detail_Response loadnotification = response.body();

                    if(loadnotification != null)
                    {
                        if (loadnotification.getStatus())
                        {
                            data=loadnotification.getData();
                            populateData();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }


                    }
                    else
                    {

                        AlertDialog.Builder ab = new AlertDialog.Builder(OrderDetailsActivity.this);
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
                public void onFailure(Call<DlvryOrder_Detail_Response> call, Throwable t)
                {
                    if (dialog.isShowing()) dialog.dismiss();
                    Toast.makeText(OrderDetailsActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(OrderDetailsActivity.this)
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
            tv_Norecord_farmer.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adapter = new DeliverOrder_Detail_Adaptor(this, data);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adapter);

        }
        else
        {
            listView.setVisibility(View.GONE);
            tv_Norecord_farmer.setVisibility(View.VISIBLE);
        }
    }

}
