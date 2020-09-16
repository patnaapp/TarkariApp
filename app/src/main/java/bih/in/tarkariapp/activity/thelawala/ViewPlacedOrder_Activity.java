package bih.in.tarkariapp.activity.thelawala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.adaptor.OrderPlacedByThelaAdaptor;
import bih.in.tarkariapp.adaptor.WorkReqrmntEntryAdapter;
import bih.in.tarkariapp.entity.GetOrderPlacedEntity;
import bih.in.tarkariapp.entity.GetPlacedOrderResponse;
import bih.in.tarkariapp.entity.GetVegEntity;
import bih.in.tarkariapp.entity.GetVegResponse;
import bih.in.tarkariapp.utility.Utiilties;
import bih.in.tarkariapp.webService.Api;
import bih.in.tarkariapp.webService.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPlacedOrder_Activity extends AppCompatActivity
{

    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;
    String deliverydate="";
    TextView tv_from_date,tv_to_date;
    String fromdate="",todate="";
    String userid="",thela_id="";
    ArrayList<GetOrderPlacedEntity> data;
    OrderPlacedByThelaAdaptor orderadaptor;
    RecyclerView listView;
    TextView tv_Norecord;
    RelativeLayout rl_toDate;
    ImageView img_back;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_placed_order_);

        listView = findViewById(R.id.listviewshow);
        tv_Norecord = findViewById(R.id.tv_Norecord);

        tv_from_date=findViewById(R.id.tv_from_date);
        tv_to_date=findViewById(R.id.tv_to_date);
        rl_toDate=findViewById(R.id.rl_toDate);
        userid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("uid", "");
        thela_id= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("thelaid", "");

        img_back=(ImageView) findViewById(R.id.img);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void onFromDate(View view)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 2);
        Date min = new Date(2018, 4, 25);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(this,mDateSetListener, mYear, mMonth, mDay);
        long now = c.getTimeInMillis() - 1000;
        //datedialog.getDatePicker().setMinDate(c.getTimeInMillis());
      //  datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        //datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datedialog.show();
    }

    public void onToDate(View view)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 2);
        Date min = new Date(2018, 4, 25);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(this,mDateSetListener1, mYear, mMonth, mDay);
        long now = c.getTimeInMillis() - 1000;
        // datedialog.getDatePicker().setMinDate(c.getTimeInMillis());
        //datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        //datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datedialog.show();
    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
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
                if (mDay < 10) {
                    smDay = "0" + mDay;//Integer.parseInt("0" + mDay);
                }
                if ((mMonth + 1) < 10) {
                    smMonth = "0" + (mMonth + 1);
                }


                tv_from_date.setText(smDay + "-" + smMonth + "-" + mYear);

                tv_from_date.setError(null);
                //_DOB = mYear + "-" + smMonth + "-" + smDay + " " + newString;
                fromdate = mYear + "-" + smMonth + "-" + smDay;


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    };


    DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
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
                if (mDay < 10) {
                    smDay = "0" + mDay;//Integer.parseInt("0" + mDay);
                }
                if ((mMonth + 1) < 10) {
                    smMonth = "0" + (mMonth + 1);
                }


                tv_to_date.setText(smDay + "-" + smMonth + "-" + mYear);

                tv_to_date.setError(null);
                //_DOB = mYear + "-" + smMonth + "-" + smDay + " " + newString;
                todate = mYear + "-" + smMonth + "-" + smDay;

                ViewOrderList();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



        }
    };

    private void ViewOrderList()
    {
        if(Utiilties.isOnline(ViewPlacedOrder_Activity.this))
        {
            JsonObject param = new JsonObject();
            // param.addProperty("Exceptdate", deliverydate);
            param.addProperty("Telaid", thela_id);
            param.addProperty("StartDate", fromdate);
            param.addProperty("EndDate", todate);


            Log.e("param", param.toString());

            final ProgressDialog dialog = new ProgressDialog(ViewPlacedOrder_Activity.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Loading Order list...");
            dialog.show();

            Api request = RetrofitClient.getRetrofitInstance().create(Api.class);

            Call<GetPlacedOrderResponse> call = null;

            call = request.GetOrderPlaced(param);

            call.enqueue(new Callback<GetPlacedOrderResponse>()
            {
                @Override
                public void onResponse(Call<GetPlacedOrderResponse> call, Response<GetPlacedOrderResponse> response)
                {
                    if (dialog.isShowing()) dialog.dismiss();

                    GetPlacedOrderResponse loadveglist = response.body();

                    if(loadveglist != null)
                    {
                        if (loadveglist.getStatus()){
                            data=loadveglist.getData();
                            populateData();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                        //Toast.makeText(getContext(), response.body().getRoleName(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        AlertDialog.Builder ab = new AlertDialog.Builder(ViewPlacedOrder_Activity.this);
                        ab.setTitle("सर्वर डाउन है");
                        ab.setMessage("सर्वर डाउन है कृपया बाद में प्रयास करें |");
                        ab.setPositiveButton("ठीक है", new DialogInterface.OnClickListener()
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
                public void onFailure(Call<GetPlacedOrderResponse> call, Throwable t)
                {
                    if (dialog.isShowing()) dialog.dismiss();
                    Toast.makeText(ViewPlacedOrder_Activity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(ViewPlacedOrder_Activity.this)
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

    public void populateData(){
        if(data != null && data.size()> 0)
        {

            Log.e("data", ""+data.size());
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            orderadaptor = new OrderPlacedByThelaAdaptor(this, data);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(orderadaptor);

        }
        else
        {
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }
}
