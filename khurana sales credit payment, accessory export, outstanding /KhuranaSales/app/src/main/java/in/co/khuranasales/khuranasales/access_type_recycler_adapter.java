package in.co.khuranasales.khuranasales;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class access_type_recycler_adapter extends RecyclerView.Adapter<access_type_recycler_adapter.ViewHolder> {

    public ArrayList<access_type_pojo> access_list ;
    public Activity activity;
    public access_type_recycler_adapter(Activity activity, ArrayList<access_type_pojo> access_list)
    {
        this.activity = activity;
        this.access_list = access_list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.access_type_offer_card, parent, false);
        access_type_recycler_adapter.ViewHolder viewHolder = new access_type_recycler_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        access_type_pojo access_type = access_list.get(position);
        holder.access_name.setText(access_type.getName());
        if(access_type.get_is_selected())
        {
            holder.tick.setVisibility(View.VISIBLE);
            holder.untick.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.untick.setVisibility(View.VISIBLE);
            holder.tick.setVisibility(View.INVISIBLE);
        }
        holder.tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             access_type.setIs_selected(false);
             notifyItemChanged(position);
            }
        });

        holder.untick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                access_type.setIs_selected(true);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return access_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView access_name;
        ImageView untick;
        ImageView tick;
        public ViewHolder(View itemView) {
            super(itemView);
            access_name = (TextView)itemView.findViewById(R.id.access_name);
            untick = (ImageView)itemView.findViewById(R.id.access_rejected);
            tick = (ImageView)itemView.findViewById(R.id.access_selected);
        }
    }
}
