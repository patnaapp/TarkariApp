package bih.in.tarkariapp.adaptor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.deliveryboy.ScanQrCodeActivity1;
import bih.in.tarkariapp.activity.farmer.ViewUnion_Pvcs_Order_activity;
import bih.in.tarkariapp.activity.listener.GenerateOrderListener;
import bih.in.tarkariapp.entity.DelvryOrder_DetailsEntity;
import bih.in.tarkariapp.utility.DataBaseHelper;

public class DeliverOrder_Detail_Adaptor extends RecyclerView.Adapter<DeliverOrder_Detail_Adaptor.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<DelvryOrder_DetailsEntity> ThrList=new ArrayList<>();

    Boolean isShowDetail = false;
    //WorkReqrmntListener listener;
    String keyid;
    DataBaseHelper dataBaseHelper;
    Integer counter=0;
    GenerateOrderListener listener;

    public DeliverOrder_Detail_Adaptor(Activity listViewshowedit, ArrayList<DelvryOrder_DetailsEntity> rlist)
    {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.adaptor_deliver_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        final DelvryOrder_DetailsEntity info = ThrList.get(position);

        dataBaseHelper = new DataBaseHelper(activity);
        holder.tv_sl_no.setText("("+String.valueOf(position+1)+")");

        if (info.getIsdeliver().equals("P"))
        {
            holder.tv_view_detail.setVisibility(View.VISIBLE);
        }
        else if (info.getIsdeliver().equals("Y"))
        {
            holder.tv_view_detail.setVisibility(View.GONE);

        }

        holder.tv_veg_name.setText(info.getVegname());
        holder.tv_veg_weight.setText(info.getQuantity());

        if (info.getIsdeliver().equals("P"))
        {
            holder.tv_delivery_status.setText("डिलीवरी पेंडिंग है");
            holder.tv_delivery_status.setTextColor(Color.RED);
        }
        else if (info.getIsdeliver().equals("Y"))
        {
            holder.tv_delivery_status.setText("डिलीवरी हो चूका है");
            holder.tv_delivery_status.setTextColor(activity.getResources().getColor(R.color.colorPrimaryLight));
        }
        holder.tv_total_amt_cnf.setText(info.getAmount());


        holder.tv_view_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.getIsdeliver().equals("P")){
                    Intent i=new Intent(activity, ScanQrCodeActivity1.class);
                    i.putExtra("order_code",info.getOrderid());
                    activity.startActivity(i);
                }

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
        TextView tv_sl_no,tv_veg_name,tv_veg_weight,tv_total_amt_cnf,tv_delivery_status,tv_view_detail;
        ImageView iv_call;
        LinearLayout ll_req_quantity;

        ViewHolder(View itemView)
        {
            super(itemView);
            tv_sl_no=itemView.findViewById(R.id.tv_sl_no);
            tv_veg_name=itemView.findViewById(R.id.tv_veg_name);
            tv_veg_weight=itemView.findViewById(R.id.tv_veg_weight);

            tv_delivery_status=itemView.findViewById(R.id.tv_delivery_status);
            tv_total_amt_cnf=itemView.findViewById(R.id.tv_total_amt_cnf);
            iv_call=itemView.findViewById(R.id.iv_call);
            tv_view_detail=itemView.findViewById(R.id.tv_view_detail);

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
