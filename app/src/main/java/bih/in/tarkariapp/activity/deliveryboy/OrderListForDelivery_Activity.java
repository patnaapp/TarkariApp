package bih.in.tarkariapp.activity.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.adaptor.DeliverOrderAdaptor;
import bih.in.tarkariapp.entity.GetDeliveryOrderEntity;
import bih.in.tarkariapp.entity.GetDlvryOrderResponse;
import bih.in.tarkariapp.utility.Utiilties;
import bih.in.tarkariapp.webService.Api;
import bih.in.tarkariapp.webService.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListForDelivery_Activity extends AppCompatActivity
{

    ImageView img_back;
    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;
    TextView tv_delvry_date_vendor;
    String date;
    ArrayList<GetDeliveryOrderEntity> data;
    DeliverOrderAdaptor adapter;
    TextView tv_Norecord_farmer;
    RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list_for_delivery_);

        img_back=(ImageView) findViewById(R.id.img);
        tv_Norecord_farmer=findViewById(R.id.tv_Norecord);
        listView = findViewById(R.id.listviewshow);
        tv_delvry_date_vendor=(TextView) findViewById(R.id.tv_delvry_date_vendor);

        img_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    public void onShowCalendar(View view)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 2);
        Date min = new Date(2018, 4, 25);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(this,mDateSetListener, mYear, mMonth, mDay);
        long now = c.getTimeInMillis() - 1000;
//        datedialog.getDatePicker().setMinDate(c.getTimeInMillis());
//        datedialog.getDatePicker().setMaxDate(now+(1000*60*60*24*6));
        //datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datedialog.show();
    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay)
        {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;
            String ds = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            ds = ds.replace("/", "-");
            String[] separated = ds.split(" ");
            Date min = new Date(2018, 4, 25);
            try {
                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTimeString = sdf.getTimeInstance().format(new Date());
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String newString = currentTimeString.replace("A.M.", "");

                String smDay = "" + mDay, smMonth = "" + (mMonth + 1);
                if (mDay < 10)
                {
                    smDay = "0" + mDay;//Integer.parseInt("0" + mDay);
                }
                if ((mMonth + 1) < 10)
                {
                    smMonth = "0" + (mMonth + 1);
                }

                tv_delvry_date_vendor.setText(smDay + "-" + smMonth + "-" + mYear);

                tv_delvry_date_vendor.setError(null);
                //_DOB = mYear + "-" + smMonth + "-" + smDay + " " + newString;
                date = mYear + "-" + smMonth + "-" + smDay;
                loadorderForDeliveryList();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    };

    private void loadorderForDeliveryList()
    {
        if(Utiilties.isOnline(OrderListForDelivery_Activity.this))
        {
            JsonObject param = new JsonObject();
            // param.addProperty("Exceptdate", deliverydate);
            param.addProperty("OrderDate", date);

            Log.e("param", param.toString());

            final ProgressDialog dialog = new ProgressDialog(OrderListForDelivery_Activity.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Loading order list...");
            dialog.show();

            Api request = RetrofitClient.getRetrofitInstance().create(Api.class);

            Call<GetDlvryOrderResponse> call = null;

            call = request.GetDelvryOrderList(param);

            call.enqueue(new Callback<GetDlvryOrderResponse>()
            {
                @Override
                public void onResponse(Call<GetDlvryOrderResponse> call, Response<GetDlvryOrderResponse> response)
                {
                    if (dialog.isShowing()) dialog.dismiss();

                    GetDlvryOrderResponse loadnotification = response.body();

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

                        //Toast.makeText(getContext(), response.body().getRoleName(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        AlertDialog.Builder ab = new AlertDialog.Builder(OrderListForDelivery_Activity.this);
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
                public void onFailure(Call<GetDlvryOrderResponse> call, Throwable t)
                {
                    if (dialog.isShowing()) dialog.dismiss();
                    Toast.makeText(OrderListForDelivery_Activity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(OrderListForDelivery_Activity.this)
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

            adapter = new DeliverOrderAdaptor(this, data,date);
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
