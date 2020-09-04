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
import bih.in.tarkariapp.activity.thelawala.RecieveOrder_QR_Activity;
import bih.in.tarkariapp.entity.OrderDateEntity;
import bih.in.tarkariapp.entity.Order_DetailsEntity;
import bih.in.tarkariapp.utility.DataBaseHelper;

public class order_detailsnotification_adaptor extends RecyclerView.Adapter<order_detailsnotification_adaptor.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<Order_DetailsEntity> ThrList=new ArrayList<>();

    Boolean isShowDetail = false;
    //WorkReqrmntListener listener;
    String keyid;
    DataBaseHelper dataBaseHelper;
    Integer counter=0;
    GenerateOrderListener listener;

    public order_detailsnotification_adaptor(Activity listViewshowedit, ArrayList<Order_DetailsEntity> rlist)
    {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.adaptor_order_details_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        final Order_DetailsEntity info = ThrList.get(position);

        dataBaseHelper = new DataBaseHelper(activity);
        holder.tv_sl_no.setText("("+String.valueOf(position+1)+")");

        holder.tv_vegname.setText(info.getVegname());
        holder.tv_vegqty.setText(info.getQuantity());


    }

    @Override
    public int getItemCount()
    {
        return ThrList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tv_sl_no,tv_vegname,tv_vegqty;


        LinearLayout ll_req_quantity;

        ViewHolder(View itemView)
        {
            super(itemView);
            tv_sl_no=itemView.findViewById(R.id.tv_sl_no);
            tv_vegname=itemView.findViewById(R.id.tv_vegname);
            tv_vegqty=itemView.findViewById(R.id.tv_vegqty);

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
