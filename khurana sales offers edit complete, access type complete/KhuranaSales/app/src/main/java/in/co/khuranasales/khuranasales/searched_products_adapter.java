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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class searched_products_adapter extends RecyclerView.Adapter<searched_products_adapter.ViewHolder> {
    private String[] dataSource;
    public int count_global=0;
    private Activity activity;
    public User user_used;
    private List<Product> products;
    private AppConfig appConfig;
    public recyclerViewItemClickListener mlistener;
    public searched_products_adapter(Activity activity, List<Product> products, recyclerViewItemClickListener listener) {
        this.activity = activity;
        this.products = products;
        this.mlistener = listener;
        appConfig = new AppConfig(activity.getApplicationContext());
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card_small, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mlistener);
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

        if(!appConfig.getUserType().equals("Admin"))
        {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        if(!appConfig.getUserType().equals("Admin") && !appConfig.getUserType().equals("Shop Admin"))
        {
            holder.ks_price_image.setVisibility(View.GONE);
            holder.ks_price_title.setVisibility(View.GONE);
            holder.ks_price.setVisibility(View.GONE);
        }
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
        if(appConfig.getUserType().equals("Admin"))
        {
            if(product.get_update_status())
            {
                Log.d("Loop: ","In If lloop");
                holder.imageView.setVisibility(View.INVISIBLE);
                holder.imageView1.setVisibility(View.VISIBLE);
                holder.text1.setVisibility(View.VISIBLE);
                holder.text2.setVisibility(View.VISIBLE);
                holder.text3.setVisibility(View.VISIBLE);
                holder.text1.setText(""+product.getPrice_mrp());
                holder.text2.setText(""+product.getPrice_mop());
                holder.text3.setText(""+product.getPrice_ks());
            }
            else
            {
                Log.d("Loop","In Else Loop");
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView1.setVisibility(View.INVISIBLE);
                holder.text1.setVisibility(View.INVISIBLE);
                holder.text2.setVisibility(View.INVISIBLE);
                holder.text3.setVisibility(View.INVISIBLE);
            }

        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked: ","I am clicked Pewncil");
                holder.imageView.setVisibility(View.INVISIBLE);
                holder.imageView1.setVisibility(View.VISIBLE);
                holder.text1.setVisibility(View.VISIBLE);
                holder.text2.setVisibility(View.VISIBLE);
                holder.text3.setVisibility(View.VISIBLE);
                product.set_update_status(true);
            }
        });
        holder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.set_update_status(false);
                Log.d("clicked: ","I am clicked Tick");
                holder.imageView1.setVisibility(View.INVISIBLE);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.text1.setVisibility(View.INVISIBLE);
                holder.text2.setVisibility(View.INVISIBLE);
                holder.text3.setVisibility(View.INVISIBLE);
                int mrp=product.getPrice_mrp();
                if(!(holder.text1.getText().toString().equals(null)||holder.text1.getText().toString().equals("")))
                {
                    mrp = Integer.parseInt(holder.text1.getText().toString());
                }
                int mop=product.getPrice_mop();
                if(!(holder.text2.getText().toString().equals(null)||holder.text2.getText().toString().equals("")))
                {
                    mop= Integer.parseInt(holder.text2.getText().toString());
                }
                int ksprice=product.getPrice_ks();
                if(!(holder.text3.getText().toString().equals(null)||holder.text3.getText().toString().equals("")))
                {
                    ksprice= Integer.parseInt(holder.text3.getText().toString());
                }
                send_data(mrp,mop,ksprice,product.get_Name(),holder,position);
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView mrp_price;
        TextView mop_price;
        TextView ks_price;
        TextView stock;
        ImageView imageView;
        ImageView imageView1;
        EditText text1;
        EditText text2;
        EditText text3;
        TextView ks_price_title;
        ImageView ks_price_image;
        TextView add_to_offer;

        public ViewHolder(final View itemView, recyclerViewItemClickListener listener){
            super(itemView);
        name = (TextView)itemView.findViewById(R.id.mobile_name);
        mrp_price = (TextView)itemView.findViewById(R.id.mrp_price);
        mop_price = (TextView)itemView.findViewById(R.id.mop_price);
        ks_price = (TextView)itemView.findViewById(R.id.ks_price);
        stock = (TextView)itemView.findViewById(R.id.stock);
            imageView=(ImageView)itemView.findViewById(R.id.pencil);
            text1=(EditText)itemView.findViewById(R.id.edit_mrp);
            text2=(EditText)itemView.findViewById(R.id.edit_mop);
            text3=(EditText)itemView.findViewById(R.id.edit_ksprice);
            imageView1=(ImageView)itemView.findViewById(R.id.done);
            ks_price_title = (TextView)itemView.findViewById(R.id.ks_price_title);
            ks_price_image = (ImageView)itemView.findViewById(R.id.ks_price_image);
            add_to_offer = (TextView)itemView.findViewById(R.id.add_to_offers);
            mlistener = listener;
            if(activity.getLocalClassName().equals("CategorizeDataActivity") || appConfig.getUserType().equals("Promoter"))
            {
                add_to_offer.setVisibility(View.INVISIBLE);
            }
            add_to_offer.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            mlistener.onItemClick(v,getAdapterPosition());
        }
    }

    public void send_data(final int mrp, final int mop, final int ksprice, final String name, final searched_products_adapter.ViewHolder holder, int position)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.url_price_update,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response:","successfull");
                        holder.text1.setText(""+mrp);
                        holder.text2.setText(""+mop);
                        holder.text3.setText(""+ksprice);
                        products.get(position).setPrice_ks(ksprice);
                        products.get(position).setPrice_mop(mop);
                        products.get(position).setPrice_mrp(mrp);
                        notifyItemChanged(position);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name",""+name);
                params.put("mrp",""+mrp);
                params.put("mop",""+mop);
                params.put("ksprice",""+ksprice);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
