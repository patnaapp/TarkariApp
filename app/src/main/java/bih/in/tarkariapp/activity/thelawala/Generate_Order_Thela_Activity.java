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
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.LoginActivity;
import bih.in.tarkariapp.adaptor.WorkReqrmntEntryAdapter;
import bih.in.tarkariapp.entity.GetVegEntity;
import bih.in.tarkariapp.entity.GetVegResponse;
import bih.in.tarkariapp.entity.LoginDetailsResponse;
import bih.in.tarkariapp.utility.DataBaseHelper;
import bih.in.tarkariapp.utility.Utiilties;
import bih.in.tarkariapp.webService.Api;
import bih.in.tarkariapp.webService.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Generate_Order_Thela_Activity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    TextView tv_delvry_date;
    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;
    String deliverydate="";
    ArrayList<GetVegEntity> data;
    RecyclerView listView;
    TextView tv_Norecord;
    WorkReqrmntEntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate__order__thela_);

        dataBaseHelper = new DataBaseHelper(Generate_Order_Thela_Activity.this);
        tv_delvry_date=findViewById(R.id.tv_delvry_date);
        listView = findViewById(R.id.listviewshow);
        tv_Norecord = findViewById(R.id.tv_Norecord);

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
        datedialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datedialog.getDatePicker().setMaxDate(now+(1000*60*60*24*6));
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


                tv_delvry_date.setText(smDay + "-" + smMonth + "-" + mYear);

                tv_delvry_date.setError(null);
                //_DOB = mYear + "-" + smMonth + "-" + smDay + " " + newString;
                deliverydate = mYear + "-" + smMonth + "-" + smDay;
                loadveglist();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    };


    private void loadveglist()
    {
        if(Utiilties.isOnline(Generate_Order_Thela_Activity.this))
        {
            JsonObject param = new JsonObject();
           // param.addProperty("Exceptdate", deliverydate);
            param.addProperty("Exceptdate", "2020-08-25");


            Log.e("param", param.toString());

            final ProgressDialog dialog = new ProgressDialog(Generate_Order_Thela_Activity.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Loading veg list...");
            dialog.show();

            Api request = RetrofitClient.getRetrofitInstance().create(Api.class);

            Call<GetVegResponse> call = null;

            call = request.GetVegListByDate(param);

            call.enqueue(new Callback<GetVegResponse>()
            {
                @Override
                public void onResponse(Call<GetVegResponse> call, Response<GetVegResponse> response)
                {
                    if (dialog.isShowing()) dialog.dismiss();

                    GetVegResponse loadveglist = response.body();

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

                        AlertDialog.Builder ab = new AlertDialog.Builder(Generate_Order_Thela_Activity.this);
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
                public void onFailure(Call<GetVegResponse> call, Throwable t)
                {
                    if (dialog.isShowing()) dialog.dismiss();
                    Toast.makeText(Generate_Order_Thela_Activity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(Generate_Order_Thela_Activity.this)
                .setIcon(R.drawable.logo)
                .setTitle(R.string.app_name)
                .setMessage("Internet Connection is not avaliable..\nPlease Turn ON Network Connection")
                .setCancelable(false)
                .setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent I = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(I);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void populateData(){
        if(data != null && data.size()> 0){
            Log.e("data", ""+data.size());
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adapter = new WorkReqrmntEntryAdapter(this, data);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adapter);

        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

}
