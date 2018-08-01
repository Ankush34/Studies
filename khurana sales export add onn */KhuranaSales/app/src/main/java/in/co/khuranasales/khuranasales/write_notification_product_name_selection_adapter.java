package in.co.khuranasales.khuranasales;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class write_notification_product_name_selection_adapter extends RecyclerView.Adapter<write_notification_product_name_selection_adapter.ViewHolder> {
    public Activity mActivity;
    public ArrayList<String> product_names;
    public recyclerViewItemClickListener item_click_listener;
    public write_notification_product_name_selection_adapter(Activity activity, ArrayList<String> product_names, recyclerViewItemClickListener item_click_listener)
    {
        this.mActivity = activity;
        this.product_names = product_names;
        this.item_click_listener = item_click_listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // promoter name list layout has a text view that we need so we dont need to create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promoter_name_list_layout, parent, false);
        write_notification_product_name_selection_adapter.ViewHolder viewHolder = new write_notification_product_name_selection_adapter.ViewHolder(view,item_click_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.product.setText(product_names.get(position));
    }

    @Override
    public int getItemCount() {
        return product_names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView product;
    public ViewHolder(View itemView, recyclerViewItemClickListener item_click_listener_sent) {
        super(itemView);
        item_click_listener  = item_click_listener_sent;
        product = (TextView)itemView.findViewById(R.id.name);
        product.setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {
            item_click_listener.onItemClick(v,getAdapterPosition());
        }
    }
}
