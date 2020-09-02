package bih.in.tarkariapp.adaptor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.in.tarkariapp.R;
import bih.in.tarkariapp.activity.listener.GenerateOrderListener;
import bih.in.tarkariapp.entity.GetVegEntity;
import bih.in.tarkariapp.entity.GetVegStockEntity;
import bih.in.tarkariapp.utility.DataBaseHelper;

public class VegListAdapter extends RecyclerView.Adapter<VegListAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<GetVegStockEntity> ThrList=new ArrayList<>();
    ViewHolder viewHolder1;
    Boolean isShowDetail = false;
    //WorkReqrmntListener listener;
    String keyid;
    DataBaseHelper dataBaseHelper;
    Integer counter=0;
    GenerateOrderListener listener;

    public VegListAdapter(Activity listViewshowedit, ArrayList<GetVegStockEntity> rlist, GenerateOrderListener listner) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        this.listener = listner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // View view = mInflater.inflate(R.layout.adaptor_veg_list, parent, false);
        View view = LayoutInflater.from(activity).inflate(R.layout.adaptor_veg_list,parent,false);

        viewHolder1 = new ViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        final GetVegStockEntity info = ThrList.get(position);

        dataBaseHelper = new DataBaseHelper(activity);
        holder.tv_sl_no.setText("("+String.valueOf(position+1)+")");

        holder.tv_veg_name.setText(info.getVegnamehn());
        holder.iv_chk_veg.setOnCheckedChangeListener(null);


        holder.iv_chk_veg.setChecked(info.getChecked());
        // holder.tv_veg_price.setText(info.getActualrate());
        holder.iv_chk_veg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                         @Override
                                                         public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                             //LandOwnersModel detail = new LandOwnersModel();

                                                             info.setChecked(isChecked);

                                                             listener.onPlaceOrder(position, isChecked);

                                                             if (holder.iv_chk_veg.isChecked()==true){
                                                                 holder.ll_req_quantity.setVisibility(View.VISIBLE);
                                                             }
                                                             else if (holder.iv_chk_veg.isChecked()==false){
                                                                 holder.ll_req_quantity.setVisibility(View.GONE);
                                                             }


                                                         }

                                                     }
        );

        holder.iv_remove_veg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(counter>0) {
                    counter--;
                    holder.tv_veg_qty.setText(String.valueOf(counter));
                    info.setVegQty(holder.tv_veg_qty.getText().toString());
                }
            }
        });

        holder.iv_add_veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                holder.tv_veg_qty.setText(String.valueOf(counter));
                info.setVegQty(holder.tv_veg_qty.getText().toString());
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
        TextView tv_sl_no,tv_veg_name,tv_veg_price,tv_veg_qty;
        ImageView iv_delete,iv_edit,iv_remove_veg,iv_add_veg;
        final LinearLayout ll_req_quantity;
        final  CheckBox iv_chk_veg;

        ViewHolder(View itemView)
        {
            super(itemView);
            tv_sl_no=itemView.findViewById(R.id.tv_sl_no);
            tv_veg_name=itemView.findViewById(R.id.tv_veg_name);
            tv_veg_price=itemView.findViewById(R.id.tv_veg_price);
            ll_req_quantity=itemView.findViewById(R.id.ll_req_quantity);
            iv_chk_veg=itemView.findViewById(R.id.iv_chk_veg);
            tv_veg_qty=itemView.findViewById(R.id.tv_veg_qty);
            iv_remove_veg=itemView.findViewById(R.id.iv_remove_veg);
            iv_add_veg=itemView.findViewById(R.id.iv_add_veg);

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
