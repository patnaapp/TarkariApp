package bih.in.tarkariapp.activity.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import bih.in.tarkariapp.R;

public class OrderDetailsActivity extends AppCompatActivity {

    String order_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        order_id=getIntent().getStringExtra("orderid");
    }
}
