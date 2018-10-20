package in.co.khuranasales.khuranasales.add_new_product;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class product_uploaded_recycler_adapter extends RecyclerView.Adapter<product_uploaded_recycler_adapter.ViewHolder> {
    public ArrayList<DealerProduct> dealer_products = new ArrayList<>();
    public Activity mActivity;
    public recyclerViewItemClickListener mlistener;
    public recyclerViewItemClickListener cancel_order_listener;
    public product_uploaded_recycler_adapter(Activity activity, ArrayList<DealerProduct> dealer_products, recyclerViewItemClickListener listener, recyclerViewItemClickListener cancel_order_listener) {
        this.dealer_products = dealer_products;
        this.mActivity = activity;
        this.mlistener = listener;
        this.cancel_order_listener = cancel_order_listener;
    }

    @Override
    public product_uploaded_recycler_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // promoter name list layout has a text view that we need so we dont need to create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.uploaded_product_recycler_card, parent, false);
        product_uploaded_recycler_adapter.ViewHolder viewHolder = new product_uploaded_recycler_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(product_uploaded_recycler_adapter.ViewHolder holder, int position) {
        DealerProduct product= dealer_products.get(position);
        holder.dealer_name.setText(product.getDealer_name());
        holder.delaer_phone.setText(product.getDealer_phone());
        holder.product_tax.setText(product.getTax()+" %");
        holder.product_hsn.setText(product.getHsn());
        holder.product_dealer_total_amount_cleared.setText("Rs  "+product.getDealer_total_amount_cleared()+" /-");
        holder.product_dealer_outstanding.setText("Rs  "+product.getDealer_total_outstanding()+" /-");
        holder.product_mop.setText("Rs  "+product.getProduct_mop()+" /-");
        holder.product_mrp.setText("Rs  "+product.getProduct_mrp()+" /-");
        holder.product_batches_available.setText(product.getBatch_bought_available().replaceAll(",","\n"));
        holder.product_batches_sold.setText(product.getBatch_sold().replaceAll(",","\n"));
        holder.product_count.setText(product.getStock_bought()+" Units");
        holder.product_name.setText(product.getProduct_name());
        Picasso.with(mActivity.getApplicationContext())
                .load(product.getLink().split(",")[0])
                .placeholder(mActivity.getResources().getDrawable( R.drawable.weel )) //this is optional the image to display while the url image is downloading
                .error( mActivity.getResources().getDrawable( R.drawable.nothing_found_images_product_desc))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(holder.uploaded_product_image);
    }

    @Override
    public int getItemCount() {
        return dealer_products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView dealer_name;
        public TextView delaer_phone;
        public TextView product_name;
        public TextView product_mop;
        public TextView product_mrp;
        public TextView product_dealer_outstanding;
        public TextView product_dealer_total_amount_cleared;
        public TextView product_batches_available;
        public TextView product_batches_sold;
        public TextView product_tax;
        public TextView product_hsn;
        public TextView product_count;
        public ImageView uploaded_product_image;
        public ImageView edit_product;
        public ImageView cancel_purchase_order;

        public ViewHolder(View itemView){
            super(itemView);
            product_count = (TextView)itemView.findViewById(R.id.product_count);
            delaer_phone = (TextView)itemView.findViewById(R.id.dealer_phone);
            dealer_name = (TextView)itemView.findViewById(R.id.dealer_name);
            product_name = (TextView)itemView.findViewById(R.id.product_name);
            product_mrp = (TextView)itemView.findViewById(R.id.product_mrp);
            product_mop = (TextView)itemView.findViewById(R.id.product_mop);
            product_batches_available = (TextView)itemView.findViewById(R.id.batches_available);
            product_hsn = (TextView)itemView.findViewById(R.id.product_hsn);
            product_tax = (TextView)itemView.findViewById(R.id.product_tax);
            product_batches_sold = (TextView)itemView.findViewById(R.id.batches_sold);
            product_dealer_outstanding = (TextView)itemView.findViewById(R.id.total_outstanding);
            product_dealer_total_amount_cleared = (TextView)itemView.findViewById(R.id.total_amount_cleared);
            uploaded_product_image = (ImageView)itemView.findViewById(R.id.product_image);
            edit_product = (ImageView)itemView.findViewById(R.id.edit_product);
            edit_product.setOnClickListener(this);
            cancel_purchase_order = (ImageView)itemView.findViewById(R.id.cancel_purchase_order);
            cancel_purchase_order.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.cancel_purchase_order)
            {
                cancel_order_listener.onItemClick(v, getAdapterPosition());
            }
            else
            {
                mlistener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}


