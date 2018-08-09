package in.co.khuranasales.khuranasales.NewLaunches;

import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.Product_desc_activity;
import in.co.khuranasales.khuranasales.R;


public class newLaunchAdapter extends RecyclerView.Adapter<newLaunchAdapter.viewHolder> {
    public ArrayList<Product> products;
    public Activity activity;
    public AppConfig appConfig;
    public newLaunchAdapter(ArrayList<Product> products_sent, Activity activity)
    {
        this.products = products_sent;
        this.activity = activity;
        appConfig = new AppConfig(activity.getApplicationContext());
    }

    @Override
    public newLaunchAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_launch_recycler_card, parent, false);
        newLaunchAdapter.viewHolder viewHolder = new newLaunchAdapter.viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(newLaunchAdapter.viewHolder holder, int position) {
        Product product = products.get(position);
        holder.name.setText(product.get_Name());
        holder.stock_count.setText(""+product.get_Stock()+" Units");
        holder.min_price.setText(""+product.getPrice_ks());
        holder.price.setText(""+product.getPrice_mrp());
        holder.sold_count.setText("2 Units");
        holder.launch_date.setText("# Launch Date( "+ product.getLaunch_date()+" )");
        String[] links = product.get_link().split(",");
        Picasso.with(activity.getApplicationContext()).load(links[links.length-1]).into(holder.product_image);
        holder.proceed_to_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(),Product_desc_activity.class);
                intent.putExtra("product_id",product.getProduct_id());
                intent.putExtra("name",product.get_Name());
                intent.putExtra("link",product.get_link());
                intent.putExtra("stock",product.get_Stock());
                intent.putExtra("mrp",product.getPrice_mrp());
                intent.putExtra("mop",product.getPrice_mop());
                intent.putExtra("ksprice",product.getPrice_ks());
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    class viewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView price;
        public TextView min_price;
        public TextView stock_count;
        public TextView sold_count;
        public ImageView product_image;
        public TextView proceed_to_buy;
        public TextView launch_date;

        public viewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.product_name);
            price = (TextView)itemView.findViewById(R.id.product_price);
            min_price = (TextView)itemView.findViewById(R.id.product_discounted_price);
            stock_count = (TextView)itemView.findViewById(R.id.product_stock_count);
            sold_count = (TextView)itemView.findViewById(R.id.products_count_sold);
            product_image = (ImageView)itemView.findViewById(R.id.product_image);
            proceed_to_buy = (TextView)itemView.findViewById(R.id.proceed_to_buy);
            launch_date = (TextView)itemView.findViewById(R.id.launch_date);
        }
    }
}

