package in.co.khuranasales.khuranasales.categorize_data_activity_scanning_adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;
public class scanned_product_adapter extends RecyclerView.Adapter<scanned_product_adapter.ViewHolder> {
    public ArrayList<Product> products;
    public recyclerViewItemClickListener item_click_listener;
    public Activity mActivity;
    public HashMap<String,ArrayList<String>> batch_by_name = new HashMap<>();
    public scanned_product_adapter(Activity activity, ArrayList<Product> products, recyclerViewItemClickListener item_click_listener, HashMap<String,ArrayList<String>> batches_associated)
    {
        this.products = products;
        this.item_click_listener = item_click_listener;
        this.mActivity = activity;
        this.batch_by_name = batches_associated;
    }

    @Override
    public scanned_product_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // promoter name list layout has a text view that we need so we dont need to create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scanned_product_recycler_card, parent, false);
        scanned_product_adapter.ViewHolder viewHolder = new scanned_product_adapter.ViewHolder(view,item_click_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(scanned_product_adapter.ViewHolder holder, int position) {
        Product p = products.get(position);
        holder.product_name.setText(p.get_Name());
        holder.product_mop.setText(""+p.getPrice_mop()+ "/-         ( MOP Price )");
        holder.product_mrp.setText(""+p.getPrice_mrp()+ "/-         ( MRP Price )");
        holder.imei_number.setText(TextUtils.join("\n",batch_by_name.get(p.get_Name())));
        holder.offer_count.setText(""+p.getTotal_offers_count()+" special offers available");
        Picasso.with(mActivity.getApplicationContext())
                .load(p.get_link().split(",")[0])
                .placeholder(mActivity.getResources().getDrawable( R.drawable.weel )) //this is optional the image to display while the url image is downloading
                .error(mActivity.getResources().getDrawable( R.drawable.nothing_found_images_product_desc))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(holder.product_image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView imei_number;
        public TextView product_name;
        public TextView product_mop;
        public TextView product_mrp;
        public ImageView product_image;
        public TextView offer_count;

        public ViewHolder(View itemView, recyclerViewItemClickListener item_click_listener_sent) {
            super(itemView);
            item_click_listener  = item_click_listener_sent;
            imei_number = (TextView)itemView.findViewById(R.id.scanned_imei_number);
            product_name = (TextView)itemView.findViewById(R.id.product_name);
            product_mop = (TextView)itemView.findViewById(R.id.product_mop);
            product_mrp = (TextView)itemView.findViewById(R.id.product_mrp);
            product_image = (ImageView)itemView.findViewById(R.id.product_image);
            product_image.setOnClickListener(this);
            offer_count = (TextView)itemView.findViewById(R.id.offer_count_text);

        }

        @Override
        public void onClick(View v) {
            item_click_listener.onItemClick(v,getAdapterPosition());
        }
    }
}
