package bih.in.tarkariapp.activity.thelawala;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.utility.DataBaseHelper;

public class Generate_Order_Thela_Activity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    TextView tv_delvry_date;
    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;
    String deliverydate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate__order__thela_);

        dataBaseHelper = new DataBaseHelper(Generate_Order_Thela_Activity.this);
        tv_delvry_date=findViewById(R.id.tv_delvry_date);

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

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    };
}
