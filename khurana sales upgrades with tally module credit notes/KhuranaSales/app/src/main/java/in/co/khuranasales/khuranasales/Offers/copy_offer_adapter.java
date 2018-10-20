package in.co.khuranasales.khuranasales.Offers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class copy_offer_adapter extends RecyclerView.Adapter<copy_offer_adapter.ViewHolder> {
    public ArrayList<Product> products;
    public recyclerViewItemClickListener item_click_listener;
    public copy_offer_adapter(ArrayList<Product> products, recyclerViewItemClickListener item_click_listener)
    {
        this.products = products;
        this.item_click_listener = item_click_listener;
    }

    @Override
    public copy_offer_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // promoter name list layout has a text view that we need so we dont need to create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.free_item_layout, parent, false);
        copy_offer_adapter.ViewHolder viewHolder = new copy_offer_adapter.ViewHolder(view,item_click_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(copy_offer_adapter.ViewHolder holder, int position) {
        holder.product.setText(products.get(position).get_Name());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView product;
        public ImageView delete;
        public ViewHolder(View itemView, recyclerViewItemClickListener item_click_listener_sent) {
            super(itemView);
            item_click_listener  = item_click_listener_sent;
            product = (TextView)itemView.findViewById(R.id.name);
            delete = (ImageView)itemView.findViewById(R.id.remove_product);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            item_click_listener.onItemClick(v,getAdapterPosition());
        }
    }
}
