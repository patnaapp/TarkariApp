package bih.in.tarkariapp.activity.thelawala;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.LoginActivity;
import bih.in.tarkariapp.activity.PreLoginActivity;
import bih.in.tarkariapp.adaptor.WorkReqrmntEntryAdapter;
import bih.in.tarkariapp.adaptor.ordernotification_adaptor;
import bih.in.tarkariapp.entity.GetVegEntity;
import bih.in.tarkariapp.entity.GetVegResponse;
import bih.in.tarkariapp.entity.NotificationResponse;
import bih.in.tarkariapp.entity.OrderDateEntity;
import bih.in.tarkariapp.utility.AppConstant;
import bih.in.tarkariapp.utility.DataBaseHelper;
import bih.in.tarkariapp.utility.Utiilties;
import bih.in.tarkariapp.webService.Api;
import bih.in.tarkariapp.webService.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends Activity
{

    TextView tv_username,tv_phone,tv_email,tv_district,tv_thelaid,tv_notifcaton,tv_regno;
    DataBaseHelper localDBHelper;
    String username,phone,district,role,dist_name,thelaid;
    String logintype="",userid="",reg_no="";
    LinearLayout ll_thela_datail,ll_zone,ll_username,ll_listarrow;
    RecyclerView listView;
    ArrayList<OrderDateEntity> data;
    ordernotification_adaptor adapter;
    TextView tv_Norecord_order,tv_generate_lbl,tv_view_lbl;
    ImageView img1,img2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        localDBHelper = new DataBaseHelper(HomeActivity.this);
        initialisation();


        logintype= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("logintype", "");
        userid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("uid", "");
        reg_no= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("reg_id", "");


        if(logintype.equals("thela"))
        {
            tv_generate_lbl.setText("तरकारी के लिए ऑर्डर दें");
            tv_view_lbl.setText("आर्डर देखें");
            ll_thela_datail.setVisibility(View.VISIBLE);
            tv_notifcaton.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            ll_username.setVisibility(View.GONE);
            ll_listarrow.setVisibility(View.GONE);

        }
        else if(logintype.equals("farmer"))
        {
            tv_generate_lbl.setText("तरकारी का स्टॉक दें");
            tv_view_lbl.setText("आर्डर देखें");
            ll_thela_datail.setVisibility(View.GONE);
            tv_notifcaton.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            ll_username.setVisibility(View.VISIBLE);
            ll_listarrow.setVisibility(View.VISIBLE);

        }
        TextView tv_version = findViewById(R.id.tv_version);
        tv_version.setText(AppConstant.APP_VERSION+ Utiilties.getAppVersion(this));
        BlinkImageView(img1);
        BlinkImageView(img2);
        if(logintype.equals("farmer"))
        {
            loadorderNotificationst();
        }
    }

    public void onGenerateOrder(View view)
    {
        if (logintype.equals("thela"))
        {
            Intent i=new Intent(HomeActivity.this,Generate_Order_Thela_Activity.class);
            startActivity(i);
        }
        else if (logintype.equals("farmer"))
        {
            Intent i=new Intent(HomeActivity.this,GenerateStockForDate_Activity.class);
            startActivity(i);
        }

    }

    public void onViewOrder(View view)
    {

        if(logintype.equals("thela")) {
            Intent i = new Intent(HomeActivity.this, ViewPlacedOrder_Activity.class);
            startActivity(i);
        }
        else if (logintype.equals("farmer"))
        {
            Intent i = new Intent(HomeActivity.this, ViewUnion_Pvcs_Order_activity.class);
            startActivity(i);
        }
    }

    public void OnClickLogout(View view)
    {
        new AlertDialog.Builder(HomeActivity.this)
                .setTitle("लॉग आउट ??")
                .setIcon(R.drawable.logo)
                .setMessage("क्या आप लॉग आउट करना चाहते हैं  \n ")
                .setCancelable(false)
                .setPositiveButton("हाँ", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        confirmLogout();
                    }
                })
                .setNegativeButton("नहीं", null)
                .show();
    }

    private void confirmLogout()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("username", false);
        editor.putBoolean("password", false);
        editor.putString("logintype", "");
        editor.commit();

        Intent intent = new Intent(this, PreLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    public void initialisation()
    {
        tv_username=findViewById(R.id.tv_username);
        tv_phone=findViewById(R.id.tv_phone);
        tv_email=findViewById(R.id.tv_email);
        tv_district=findViewById(R.id.tv_district);
        tv_thelaid=findViewById(R.id.tv_thelaid);
        ll_thela_datail=findViewById(R.id.ll_thela_datail);
        tv_notifcaton=findViewById(R.id.tv_notifcaton);
        listView=findViewById(R.id.listviewshow_ordernotification);
        tv_Norecord_order=findViewById(R.id.tv_Norecord_order);
        tv_generate_lbl=findViewById(R.id.tv_generate_lbl);
        tv_view_lbl=findViewById(R.id.tv_view_lbl);
        tv_regno=findViewById(R.id.tv_regno);
        ll_zone=findViewById(R.id.ll_zone);
        ll_username=findViewById(R.id.ll_username);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        ll_listarrow=findViewById(R.id.ll_listarrow);

        username= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("uname", "");
        reg_no= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("reg_id", "");
        phone= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("mob", "");
        role= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("userRole", "");
        thelaid= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("thelaid", "");

        district= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("distcode", "");
        tv_username.setText(username);
        tv_phone.setText(phone);
        tv_email.setText(role);
        dist_name = localDBHelper.getNameFor("Districts", "DistCode", "DistName", district);

        tv_regno.setText(reg_no);
        tv_district.setText(dist_name);
        tv_thelaid.setText(String.valueOf(thelaid));
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        if(logintype.equals("thela"))
        {
            ll_thela_datail.setVisibility(View.VISIBLE);
            ll_zone.setVisibility(View.GONE);
            ll_username.setVisibility(View.GONE);
        }
        else {
            ll_thela_datail.setVisibility(View.GONE);
            ll_zone.setVisibility(View.VISIBLE);
            ll_username.setVisibility(View.VISIBLE);
        }
    }

    private void loadorderNotificationst()
    {
        if(Utiilties.isOnline(HomeActivity.this))
        {
            JsonObject param = new JsonObject();
            // param.addProperty("Exceptdate", deliverydate);
            param.addProperty("registrationno", reg_no);

            Log.e("param", param.toString());

            final ProgressDialog dialog = new ProgressDialog(HomeActivity.this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Loading order list...");
            dialog.show();

            Api request = RetrofitClient.getRetrofitInstance().create(Api.class);

            Call<NotificationResponse> call = null;

            call = request.GetNotification(param);

            call.enqueue(new Callback<NotificationResponse>()
            {
                @Override
                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response)
                {
                    if (dialog.isShowing()) dialog.dismiss();

                    NotificationResponse loadnotification = response.body();

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

                        AlertDialog.Builder ab = new AlertDialog.Builder(HomeActivity.this);
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
                public void onFailure(Call<NotificationResponse> call, Throwable t)
                {
                    if (dialog.isShowing()) dialog.dismiss();
                    Toast.makeText(HomeActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(HomeActivity.this)
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
            tv_Norecord_order.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            tv_notifcaton.setVisibility(View.VISIBLE);
            adapter = new ordernotification_adaptor(this, data);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(adapter);

        }
        else
        {
            listView.setVisibility(View.GONE);
            tv_notifcaton.setVisibility(View.GONE);
            ll_listarrow.setVisibility(View.GONE);
            // tv_Norecord_order.setVisibility(View.VISIBLE);
        }
    }


    public  void BlinkImageView(ImageView img)
    {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(600); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        img.startAnimation(anim);
    }
}
