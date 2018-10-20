package in.co.khuranasales.khuranasales.add_new_product;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;
public class location_wise_add_batch_recycler_adapter extends RecyclerView.Adapter<location_wise_add_batch_recycler_adapter.ViewHolder> {
    public ArrayList<String> locations;
    public recyclerViewItemClickListener item_click_listener;
    public recyclerViewItemClickListener listener2;
    public location_wise_add_batch_recycler_adapter(ArrayList<String> locations, recyclerViewItemClickListener item_click_listener, recyclerViewItemClickListener view_batch_by_location_listener)
    {
        this.locations = locations;
        this.item_click_listener = item_click_listener;
        this.listener2 = view_batch_by_location_listener;
    }

    @Override
    public location_wise_add_batch_recycler_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // promoter name list layout has a text view that we need so we dont need to create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_wise_batch_row, parent, false);
        location_wise_add_batch_recycler_adapter.ViewHolder viewHolder = new location_wise_add_batch_recycler_adapter.ViewHolder(view,item_click_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(location_wise_add_batch_recycler_adapter.ViewHolder holder, int position) {
        holder.location_name.setText(locations.get(position));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView location_name;
        public ImageView add_batches;
        public ImageView view_batches;
        public ViewHolder(View itemView, recyclerViewItemClickListener item_click_listener_sent) {
            super(itemView);
            item_click_listener  = item_click_listener_sent;
            location_name = (TextView)itemView.findViewById(R.id.batch_location_name);
            add_batches = (ImageView)itemView.findViewById(R.id.batch_add);
            add_batches.setOnClickListener(this);
            view_batches = (ImageView)itemView.findViewById(R.id.view_batch);
            view_batches.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.view_batch)
            {
                listener2.onItemClick(v,getAdapterPosition());
            }
            else
            {
                item_click_listener.onItemClick(v,getAdapterPosition());
            }
        }
    }
}
