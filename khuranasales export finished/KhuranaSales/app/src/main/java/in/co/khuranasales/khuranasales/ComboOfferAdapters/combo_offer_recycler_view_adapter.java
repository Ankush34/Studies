package in.co.khuranasales.khuranasales.ComboOfferAdapters;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.Offers.Offer;
import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class combo_offer_recycler_view_adapter extends RecyclerView.Adapter<combo_offer_recycler_view_adapter.ViewHolder> {
    public ArrayList<Offer> offers;
    public recyclerViewItemClickListener item_click_listener;
    public combo_offer_recycler_view_adapter(ArrayList<Offer> products, recyclerViewItemClickListener item_click_listener)
    {
        this.offers = products;
        this.item_click_listener = item_click_listener;
    }

    @Override
    public combo_offer_recycler_view_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // promoter name list layout has a text view that we need so we dont need to create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.combo_offer_recycler_view_card, parent, false);
        combo_offer_recycler_view_adapter.ViewHolder viewHolder = new combo_offer_recycler_view_adapter.ViewHolder(view,item_click_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(combo_offer_recycler_view_adapter.ViewHolder holder, int position) {
        holder.product.setText(""+offers.get(position).getOffer_product_name());
        if(offers.get(position).getLies_in_discount())
        {
            holder.discounted.setVisibility(View.VISIBLE);
            holder.product_price.setText(""+offers.get(position).getOffer_product_price());
            holder.product_discounted_price.setText(""+(Integer.parseInt(offers.get(position).getOffer_product_price())-Integer.parseInt(offers.get(position).getDiscount_amount_offeres_single())));
            holder.product_stock_count.setText(""+offers.get(position).getOffer_product_instock_quantity());
        }
        else {
            holder.free.setVisibility(View.VISIBLE);
            holder.product_price.setText(""+offers.get(position).getOffer_product_price());
            holder.product_discounted_price.setText("0 (Absolutely Free)");
            holder.product_stock_count.setText(""+offers.get(position).getOffer_product_instock_quantity());

        }

        Picasso.with(holder.itemView.getContext()).load(
                offers.get(position).getOffer_product_image_links()
                        .split(",")[0]).into(holder.product_image);
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView product;
        public ImageView free;
        public ImageView discounted;
        public ImageView product_image;
        public TextView product_price;
        public TextView product_discounted_price;
        public TextView product_stock_count;


        public ViewHolder(View itemView, recyclerViewItemClickListener item_click_listener_sent) {
            super(itemView);
            item_click_listener  = item_click_listener_sent;
            product = (TextView)itemView.findViewById(R.id.product_name);
            free = (ImageView)itemView.findViewById(R.id.free);
            discounted = (ImageView)itemView.findViewById(R.id.discounted);
            product_image = (ImageView)itemView.findViewById(R.id.product_image);
            product_price = (TextView)itemView.findViewById(R.id.product_price);
            product_discounted_price = (TextView)itemView.findViewById(R.id.product_discounted_price);
            product_stock_count = (TextView)itemView.findViewById(R.id.product_stock_count);
        }

        @Override
        public void onClick(View v) {
            item_click_listener.onItemClick(v,getAdapterPosition());
        }
    }
}
