package in.co.khuranasales.khuranasales.add_new_product;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
public class selected_batch_numbers_adapter extends RecyclerView.Adapter<selected_batch_numbers_adapter.ViewHolder> {
    public ArrayList<String> batches;
    public recyclerViewItemClickListener item_click_listener;

    public selected_batch_numbers_adapter(ArrayList<String> batches, recyclerViewItemClickListener item_click_listener)
    {
        this.batches = batches;
        this.item_click_listener = item_click_listener;
    }

    @Override
    public selected_batch_numbers_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // promoter name list layout has a text view that we need so we dont need to create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discount_item_layout, parent, false);
        selected_batch_numbers_adapter.ViewHolder viewHolder = new selected_batch_numbers_adapter.ViewHolder(view,item_click_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(selected_batch_numbers_adapter.ViewHolder holder, int position) {
        Log.d("IAMHERE","IN ADAPTER");
        holder.product.setText(batches.get(position));
    }

    @Override
    public int getItemCount() {
        return batches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView product;
        public ImageView delete;
        public EditText discount;

        public ViewHolder(View itemView, recyclerViewItemClickListener item_click_listener_sent) {
            super(itemView);
            item_click_listener  = item_click_listener_sent;
            product = (TextView)itemView.findViewById(R.id.name);
            delete = (ImageView)itemView.findViewById(R.id.delete_product);
            delete.setOnClickListener(this);
            discount = (EditText)itemView.findViewById(R.id.after_discount_price);
            discount.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onClick(View v) {
            item_click_listener.onItemClick(v,getAdapterPosition());
        }
    }
}
