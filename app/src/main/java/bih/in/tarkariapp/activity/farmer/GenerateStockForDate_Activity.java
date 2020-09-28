package bih.in.tarkariapp.activity.farmer;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.listener.GenerateOrderListener;
import bih.in.tarkariapp.adaptor.VegListAdapter;
import bih.in.tarkariapp.entity.GetVegEntity;
import bih.in.tarkariapp.entity.GetVegStockEntity;
import bih.in.tarkariapp.entity.GetVegStockResponse;
import bih.in.tarkariapp.utility.Utiilties;
import bih.in.tarkariapp.webService.Api;
import bih.in.tarkariapp.webService.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateStockForDate_Activity extends AppCompatActivity implements GenerateOrderListener
{

    TextView tv_stock_date,tv_Norecord_farmer;
    RecyclerView listView;
    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;
    String stockdate="";
    ArrayList<GetVegStockEntity> data;
    VegListAdapter adapter;
    Button buton_placeStock;
    ArrayList<GetVegStockEntity> newArrayList;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_stock_for_date_);

        tv_stock_date=findViewById(R.id.tv_stock_date);
        tv_Norecord_farmer=findViewById(R.id.tv_Norecord_farmer);
        listView = findViewById(R.id.listviewshow_farmer);
        buton_placeStock = findViewById(R.id.buton_placeStock);


        buton_placeStock.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                newArrayList=new ArrayList<>();

                for(GetVegStockEntity land : data)
                {
                    if(land.getChecked()&& land.getVegQty()!=null)
                    {
                        newArrayList.add(land);
                        Log.d("vegid_stock" ,""+land.getVegid());
                        Log.d("qty" ,""+land.getVegQty());
                    }

                }
                //  Log.d("fhbdhb" ,""+newArrayList.size());
                //   new UploadTeacherDetails(newArrayList).execute();
                if (newArrayList.size()>0 && newArrayList.get(0).getVegQty()!=null)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GenerateStockForDate_Activity.this);
                    alertDialogBuilder.setMessage("क्या आप आर्डर देना चाहते हैं |");
                    alertDialogBuilder.setPositiveButton("हाँ",
                            new DialogInterface.OnClickListener()
                            {
                                @Override

                                public void onClick(DialogInterface arg0, int arg1)
                                {
                                    Intent i=new Intent(GenerateStockForDate_Activity.this, ConfirmStockFarmerActivity.class);
                                    i.putExtra("orderlist", newArrayList);
                                    i.putExtra("stock",stockdate);
                                    startActivity(i);
                                    //finish();
                                }
                            });

                    alertDialogBuilder.setNegativeButton("नहीं",new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }

        });

        img_back=(ImageView) findViewById(R.id.img);

        img_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

    }

    public void  onShowCalendar_farmer(View view)
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
            try
            {
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

                tv_stock_date.setText(smDay + "-" + smMonth + "-" + mYear);

                tv_stock_date.setError(null);
                //_DOB = mYear + "-" + smMonth + "-" + smDay + " " + newString;
                stockdate = mYear + "-" + smMonth + "-" + smDay;
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
        if(Utiilties.isOnline(GenerateStockForDate_Activity.this))
        {
//            JsonObject param = new JsonObject();
//            // param.addProperty("Exceptdate", deliverydate);
//          //  param.addProperty("Exceptdate", "2020-08-25");
//
//
//            Log.e("param", param.toString());
//
            final ProgressDialog dialog = new ProgressDialog(GenerateStockForDate_Activity.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("तरकारी की सूचि लोड हो रही है...");
            dialog.show();

            Api request = RetrofitClient.getRetrofitInstance().create(Api.class);

            Call<GetVegStockResponse> call = null;

            call = request.GetVegListByDateFarmer();

            call.enqueue(new Callback<GetVegStockResponse>()
            {
                @Override
                public void onResponse(Call<GetVegStockResponse> call, Response<GetVegStockResponse> response)
                {
                    if (dialog.isShowing()) dialog.dismiss();
                    GetVegStockResponse loadveglist = response.body();

                    if(loadveglist != null)
                    {
                        data=loadveglist.getData();
                        populateData();
                    }
                    else
                    {
                        AlertDialog.Builder ab = new AlertDialog.Builder(GenerateStockForDate_Activity.this);
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
                public void onFailure(Call<GetVegStockResponse> call, Throwable t)
                {
                    if (dialog.isShowing()) dialog.dismiss();
                    Toast.makeText(GenerateStockForDate_Activity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            showAlertDialog();
        }
    }


    public void populateData()
    {
        if(data != null && data.size()> 0)
        {
            Log.e("data", ""+data.size());
            tv_Norecord_farmer.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adapter = new VegListAdapter(this, data,GenerateStockForDate_Activity.this);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adapter);

        }
        else
        {
            listView.setVisibility(View.GONE);
            tv_Norecord_farmer.setVisibility(View.VISIBLE);
        }
    }

    public void showAlertDialog()
    {
        new AlertDialog.Builder(GenerateStockForDate_Activity.this)
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

    @Override
    public void onPlaceOrder(int position, boolean isChecked)
    {
        final GetVegStockEntity detail= data.get(position) ;

        detail.setChecked(isChecked);
        detail.setVegstockdate(stockdate);
        data.set(position, detail);
        Log.d("marklistvalue",""+position+data.get(position).getChecked());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChangeQty(int position, boolean isIncrease)
    {
        GetVegStockEntity detail =data.get(position);
        if (isIncrease)
        {
            detail.setVegcount(detail.getVegcount()+1);
        }
        else
        {
            detail.setVegcount(detail.getVegcount()-1);
        }
        detail.setVegQty(detail.getVegcount().toString());
        data.set(position, detail);
        adapter.notifyDataSetChanged();
    }
}
