package in.co.khuranasales.khuranasales.add_new_product;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class view_iemi_numbers_adapter extends RecyclerView.Adapter<view_iemi_numbers_adapter.ViewHolder> {
    public ArrayList<String> imei_numbers = new ArrayList<>();
    public recyclerViewItemClickListener item_click_listener;
    public view_iemi_numbers_adapter(ArrayList<String> imei_numbers, recyclerViewItemClickListener item_click_listener)
    {
        this.imei_numbers = imei_numbers ;
        this.item_click_listener = item_click_listener;
    }

    @Override
    public view_iemi_numbers_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // promoter name list layout has a text view that we need so we dont need to create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promoter_name_list_layout, parent, false);
        view_iemi_numbers_adapter.ViewHolder viewHolder = new view_iemi_numbers_adapter.ViewHolder(view,item_click_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(view_iemi_numbers_adapter.ViewHolder holder, int position) {
        holder.imei_number.setText(imei_numbers.get(position));
    }

    @Override
    public int getItemCount() {
        return imei_numbers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView imei_number;
        public ViewHolder(View itemView, recyclerViewItemClickListener item_click_listener_sent) {
            super(itemView);
            item_click_listener  = item_click_listener_sent;
            imei_number = (TextView)itemView.findViewById(R.id.name);
        }

        @Override
        public void onClick(View v) {
            item_click_listener.onItemClick(v,getAdapterPosition());
        }
    }
}

