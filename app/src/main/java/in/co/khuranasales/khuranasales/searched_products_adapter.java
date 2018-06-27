package in.co.khuranasales.khuranasales;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class searched_products_adapter extends RecyclerView.Adapter<searched_products_adapter.ViewHolder> {
    private String[] dataSource;
    public int count_global=0;
    private Activity activity;
    public User user_used;
    private List<Product> products;
    public searched_products_adapter(Activity activity, List<Product> products) {
        this.activity = activity;
        this.products = products;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card_small, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Product product = products.get(position);
        holder.name.setText(product.get_Name());
        holder.mrp_price.setText(product.getPrice_mrp()+"/-");
        holder.mop_price.setText(product.getPrice_mop()+"/-");
        holder.ks_price.setText(product.getPrice_ks()+"/-");
        holder.stock.setText("QT("+product.get_Stock()+")");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                Product p1= products.get(position);
                intent = new Intent(holder.itemView.getContext(),Product_desc_activity.class);
                intent.putExtra("product_id",p1.getProduct_id());
                intent.putExtra("name",p1.get_Name());
                intent.putExtra("link",p1.get_link());
                intent.putExtra("stock",p1.get_Stock());
                intent.putExtra("mrp",p1.getPrice_mrp());
                intent.putExtra("mop",p1.getPrice_mop());
                intent.putExtra("ksprice",p1.getPrice_ks());
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView mrp_price;
        TextView mop_price;
        TextView ks_price;
        TextView stock;
        public ViewHolder(final View itemView) {
            super(itemView);
        name = (TextView)itemView.findViewById(R.id.mobile_name);
        mrp_price = (TextView)itemView.findViewById(R.id.mrp_price);
        mop_price = (TextView)itemView.findViewById(R.id.mop_price);
        ks_price = (TextView)itemView.findViewById(R.id.ks_price);
        stock = (TextView)itemView.findViewById(R.id.stock);
        }

    }
}
