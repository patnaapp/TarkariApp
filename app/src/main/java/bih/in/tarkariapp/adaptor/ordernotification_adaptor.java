package bih.in.tarkariapp.adaptor;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.listener.GenerateOrderListener;
import bih.in.tarkariapp.activity.thelawala.OrderAllotedDetails_Activity;
import bih.in.tarkariapp.activity.thelawala.RecieveOrder_QR_Activity;
import bih.in.tarkariapp.entity.GetVegEntity;
import bih.in.tarkariapp.entity.OrderDateEntity;
import bih.in.tarkariapp.utility.DataBaseHelper;

public class ordernotification_adaptor extends RecyclerView.Adapter<ordernotification_adaptor.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<OrderDateEntity> ThrList=new ArrayList<>();

    Boolean isShowDetail = false;
    //WorkReqrmntListener listener;
    String keyid;
    DataBaseHelper dataBaseHelper;
    Integer counter=0;
    GenerateOrderListener listener;

    public ordernotification_adaptor(Activity listViewshowedit, ArrayList<OrderDateEntity> rlist)
    {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.adaptor_ordernotification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        final OrderDateEntity info = ThrList.get(position);

        dataBaseHelper = new DataBaseHelper(activity);
        holder.tv_sl_no.setText("("+String.valueOf(position+1)+").");
        holder.tv_orderdate.setText(info.getOrderDate());
        holder.sblist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do whatever
                Intent intent = new Intent(activity, OrderAllotedDetails_Activity.class);
                intent.putExtra("orderdate",String.valueOf(info.getOrderDate()));
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return ThrList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tv_sl_no,tv_orderdate;

        LinearLayout sblist;
        LinearLayout ll_req_quantity;

        ViewHolder(View itemView)
        {
            super(itemView);
            tv_sl_no=itemView.findViewById(R.id.tv_sl_no);
            tv_orderdate=itemView.findViewById(R.id.tv_orderdate);
            sblist=itemView.findViewById(R.id.sblist);

        }

        @Override
        public void onClick(View v)
        {

        }

    }

    public String getGenderHindi(String gender)
    {
        return gender;
    }

}
