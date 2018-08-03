package in.co.khuranasales.khuranasales.ComboOfferAdapters;

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

public class selected_discount_items_recycler extends RecyclerView.Adapter<selected_discount_items_recycler.ViewHolder> {
    public ArrayList<Product> products;
    public recyclerViewItemClickListener item_click_listener;
    public selected_discount_items_recycler(ArrayList<Product> products, recyclerViewItemClickListener item_click_listener)
    {
        this.products = products;
        this.item_click_listener = item_click_listener;
    }

    @Override
    public selected_discount_items_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // promoter name list layout has a text view that we need so we dont need to create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discount_item_layout, parent, false);
        selected_discount_items_recycler.ViewHolder viewHolder = new selected_discount_items_recycler.ViewHolder(view,item_click_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(selected_discount_items_recycler.ViewHolder holder, int position) {
        holder.product.setText(products.get(position).get_Name());
        holder.price.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                products.get(position).setDicount_amount(Integer.parseInt(holder.price.getText().toString()));
            }
            @Override public void afterTextChanged(Editable s) { }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView product;
        public ImageView delete;
        public EditText price;

        public ViewHolder(View itemView, recyclerViewItemClickListener item_click_listener_sent) {
            super(itemView);
            item_click_listener  = item_click_listener_sent;
            product = (TextView)itemView.findViewById(R.id.name);
            delete = (ImageView)itemView.findViewById(R.id.delete_product);
            price = (EditText)itemView.findViewById(R.id.after_discount_price);
            delete.setOnClickListener(this);
            }

        @Override
        public void onClick(View v) {
            item_click_listener.onItemClick(v,getAdapterPosition());
        }
    }
}
