package in.co.khuranasales.khuranasales.prebooking_module;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class prebooking_products_adapter extends  RecyclerView.Adapter<prebooking_products_adapter.ViewHolder>{

    public ArrayList<PrebookingProduct> prebooking_products ;
    public Activity mActivity;
    public recyclerViewItemClickListener listner_edit;
    public recyclerViewItemClickListener listner_add_bookings;
    public recyclerViewItemClickListener listner_delete;
    public recyclerViewItemClickListener listner_view_bookings;

    public prebooking_products_adapter(Activity activity, ArrayList<PrebookingProduct> products, recyclerViewItemClickListener listner_edit, recyclerViewItemClickListener listner_add_bookings, recyclerViewItemClickListener listner_delete, recyclerViewItemClickListener listner_view_bookings) {
        this.mActivity  = activity;
        this.listner_delete = listner_delete;
        this.listner_edit = listner_edit;
        this.listner_add_bookings = listner_add_bookings;
        this.listner_view_bookings = listner_view_bookings;
        this.prebooking_products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prebooking_product_recycler_card, parent, false);
        prebooking_products_adapter.ViewHolder viewHolder = new prebooking_products_adapter.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PrebookingProduct pro_current = prebooking_products.get(position);
        if(pro_current.getLink() != null)
        {
            Picasso.with(mActivity.getApplicationContext())
                    .load(pro_current.getLink().split(",")[0])
                    .placeholder( mActivity.getResources().getDrawable( R.drawable.weel )) //this is optional the image to display while the url image is downloading
                    .error( mActivity.getResources().getDrawable( R.drawable.nothing_found_images_product_desc))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(holder.product_image);
        }
        else
        {
            holder.product_image.setImageResource(R.drawable.nothing_found_images_product_desc);
        }

        holder.product_name.setText(""+pro_current.getName());
        holder.expected_date.setText("Expected Date Of Launch:   "+pro_current.getExpected_date());
        holder.expected_price.setText("Expected Price Of Launch:   "+pro_current.getExpected_price());
    }

    @Override
    public int getItemCount() {
        return prebooking_products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView product_image;
        public TextView expected_date;
        public TextView expected_price;
        public TextView product_name;
        public ImageView edit_product;
        public ImageView delete_product;
        public ImageView view_bookings_product;
        public ImageView add_bookings_product;
        public ViewHolder(View itemView) {
            super(itemView);
            product_image = (ImageView)itemView.findViewById(R.id.product_image);
            expected_date = (TextView)itemView.findViewById(R.id.expected_date);
            expected_price = (TextView)itemView.findViewById(R.id.expected_price);
            product_name = (TextView)itemView.findViewById(R.id.product_name);
            edit_product = (ImageView)itemView.findViewById(R.id.edit_prebooking_product);
            edit_product.setOnClickListener(this);
            delete_product = (ImageView)itemView.findViewById(R.id.delete_prebooking_product);
            delete_product.setOnClickListener(this);
            view_bookings_product = (ImageView)itemView.findViewById(R.id.view_bookings);
            view_bookings_product.setOnClickListener(this);
            add_bookings_product = (ImageView)itemView.findViewById(R.id.add_booking);
            add_bookings_product.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.add_booking)
            {
                listner_add_bookings.onItemClick(v, getAdapterPosition());
            }
            else if(v.getId() == R.id.view_bookings)
            {
                listner_view_bookings.onItemClick(v, getAdapterPosition());

            }
            else if(v.getId() == R.id.edit_prebooking_product)
            {
                listner_edit.onItemClick(v, getAdapterPosition());

            }
            else if(v.getId() == R.id.delete_prebooking_product)
            {
                listner_delete.onItemClick(v, getAdapterPosition());

            }
        }
    }
}
