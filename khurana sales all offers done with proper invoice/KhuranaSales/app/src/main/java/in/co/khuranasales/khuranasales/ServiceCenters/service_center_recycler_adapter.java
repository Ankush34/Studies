package in.co.khuranasales.khuranasales.ServiceCenters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.R;

public class service_center_recycler_adapter extends RecyclerView.Adapter<service_center_recycler_adapter.ViewHolder>{
    public ArrayList<service_center> service_centers = new ArrayList<>();
    public Activity activity;

    public service_center_recycler_adapter(Activity activity, ArrayList<service_center> service_centers)
    {
        this.service_centers = service_centers;
        this.activity = activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_center_recycler_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.shop_name.setText(service_centers.get(position).getName());
        holder.shop_address.setText("Shop Address: "+service_centers.get(position).getAddress());
        holder.shop_phone.setText("Shop Contact: "+service_centers.get(position).getPhone());
        holder.shop_opens_at.setText("Shop Opens At: "+service_centers.get(position).getOpens_at());
        holder.shop_closes_at.setText("Shop Closes At"+service_centers.get(position).getCloses_at());
        holder.shop_brand.setText("Service center brand: "+service_centers.get(position).getBrand());
    }

    @Override
    public int getItemCount() {
        return service_centers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView shop_name;
        public TextView shop_address;
        public TextView shop_phone;
        public TextView shop_brand;
        public TextView shop_opens_at;
        public TextView shop_closes_at;

        public ViewHolder(View itemView) {
            super(itemView);
            shop_name = (TextView)itemView.findViewById(R.id.shop_name);
            shop_address = (TextView)itemView.findViewById(R.id.shop_address);
            shop_phone = (TextView)itemView.findViewById(R.id.shop_phone);
            shop_brand = (TextView)itemView.findViewById(R.id.service_center_brand);
            shop_opens_at = (TextView)itemView.findViewById(R.id.shop_open_at);
            shop_closes_at = (TextView)itemView.findViewById(R.id.shop_closes_at);
        }
    }
}
