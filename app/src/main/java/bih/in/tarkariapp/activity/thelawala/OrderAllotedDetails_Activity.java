package bih.in.tarkariapp.activity.thelawala;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;

import bih.in.tarkariapp.R;

public class OrderAllotedDetails_Activity extends AppCompatActivity {

    String orderdate="",reg_no="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_alloted_details);

        orderdate=getIntent().getStringExtra("orderdate");
        reg_no= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("reg_id", "");

    }
}
