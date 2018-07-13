package in.co.khuranasales.khuranasales;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private String[] dataSource;
    public int count_global=0;
    private Activity activity;
    private AppConfig appConfig;
    private List<Product> productItems;
    private recyclerViewItemClickListener mListener;
    public RecyclerAdapter(String[] dataArgs){
           dataSource = dataArgs;
    }
    public RecyclerAdapter(Activity activity, List<Product> movieItems,recyclerViewItemClickListener item_listener) {
        this.activity = activity;
        this.productItems = movieItems;
        appConfig = new AppConfig(activity);
        this.mListener = item_listener;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buy_now_recycler_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view,mListener);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Product m=productItems.get(position);
        holder.title.setText(m.get_Name());
        holder.mrp.setText(""+m.getPrice_mrp()+" /-");
        holder.mop.setText(""+m.getPrice_mop()+" /-");
        if(appConfig.getUserType().equals("Admin") || appConfig.getUserType().equals("Dealer"))
        {
        holder.ksprice.setVisibility(View.VISIBLE);
        holder.ksprice_image.setVisibility(View.VISIBLE);
            holder.ksprice.setText(""+m.getPrice_ks()+" /-");
        }
        else {
            holder.ksprice.setVisibility(View.GONE);
            holder.ksprice_image.setVisibility(View.GONE);
        }

        holder.stock.setText(""+m.get_Stock());
        Buy_Now_Activity.update_price_card(productItems);
        check_avail(m,holder);
        count_global=0;
        holder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             int pos=holder.getAdapterPosition();
                DatabaseHandler databaseHandler=new DatabaseHandler(holder.itemView.getContext());
                databaseHandler.delete_entry(productItems.get(holder.getAdapterPosition()).get_Name());
                Toast.makeText(holder.itemView.getContext(),"product at position: "+pos+" removed from cart", Toast.LENGTH_LONG).show();
               if(appConfig.isLogin() == true)
               {
                   delete_viewed(productItems.get(pos));
               }
               productItems.remove(pos);
               Buy_Now_Activity.adapter1.notifyDataSetChanged();
               Buy_Now_Activity.update_price_card(productItems);

            }
        });
   ;     holder.Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                Toast.makeText(holder.itemView.getContext(), "Position Clicked:" + pos, Toast.LENGTH_LONG).show();
                if (appConfig.isLogin() == true)
                {
                    Log.d("Status: ","Decrementing it online");
                    change_count_product_online(productItems.get(pos),"Decrement");
                    int update_count = productItems.get(pos).get_Stock() - 1;
                    if(m.getOffer_type().equals("Super Value Offer"))
                    {
                        update_count = productItems.get(pos).get_Stock() - productItems.get(pos).getMin_item_count();
                    }
                    productItems.get(pos).set_Stock(update_count);
                }
                else {
                    DatabaseHandler databaseHandler=new DatabaseHandler(holder.itemView.getContext());
                    if (databaseHandler.check_avail(productItems.get(pos).getProduct_id()))
                    {
                        Log.d("Status: ","Decrementing it offline");
                        int count = databaseHandler.get_bought_count(productItems.get(pos).getProduct_id());
                        int update_count = count-1;
                        if(m.getOffer_type().equals("Super Value Offer"))
                        {
                            update_count = productItems.get(pos).get_Stock() - productItems.get(pos).getMin_item_count();
                        }
                        databaseHandler.set_bought_count(productItems.get(pos),update_count);
                        productItems.get(pos).set_Stock(update_count);
                    }
                }
                Buy_Now_Activity.update_price_card(productItems);
                check_avail(productItems.get(pos),holder);
                int count1=count_global;
                DatabaseHandler db=new DatabaseHandler(holder.itemView.getContext());
                int con=db.see(productItems.get(pos).get_Name());
                if (con>count1)
                {
                    productItems.get(pos).setStatus(true);
                }
                else if(con<count1)
                {
                    productItems.get(pos).setStatus(false);
                }
                count_global=0;
                Buy_Now_Activity.adapter1.notifyDataSetChanged();

            }
        });
        holder.Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=holder.getAdapterPosition();
                if(appConfig.isLogin() == true )
                {
                    Log.d("Status: ","Incermenting it online");
                    change_count_product_online(productItems.get(pos),"Increment");
                    int update_count = productItems.get(pos).get_Stock() + 1;
                    if(m.getOffer_type().equals("Super Value Offer"))
                    {
                        update_count = productItems.get(pos).get_Stock() + productItems.get(pos).getMin_item_count();
                    }
                    productItems.get(pos).set_Stock(update_count);
                }
                else{
                    DatabaseHandler databaseHandler=new DatabaseHandler(holder.itemView.getContext());
                    if(databaseHandler.check_avail(productItems.get(pos).getProduct_id()))
                    {
                        Log.d("Status: ","Incermenting it offline");
                        int count=databaseHandler.get_bought_count(productItems.get(pos).getProduct_id());
                        int update_count=count+1;
                        if(m.getOffer_type().equals("Super Value Offer"))
                        {
                            update_count = productItems.get(pos).get_Stock() + productItems.get(pos).getMin_item_count();
                        }
                        databaseHandler.set_bought_count(productItems.get(pos),update_count);
                        productItems.get(pos).set_Stock(update_count);
                    }

                }
                Toast.makeText(holder.itemView.getContext(),"Position Clicked:"+pos, Toast.LENGTH_LONG).show();
                Buy_Now_Activity.update_price_card(productItems);
                check_avail(productItems.get(pos),holder);
                int count1=count_global;
                DatabaseHandler db=new DatabaseHandler(holder.itemView.getContext());
                int con=db.see(productItems.get(pos).get_Name());
                if (con>count1)
                {
                    productItems.get(pos).setStatus(true);
                }
                else if(con<count1)
                {
                    productItems.get(pos).setStatus(false);
                }
                count_global=0;
                Buy_Now_Activity.adapter1.notifyDataSetChanged();

            }
        });
        if(m.getOffer_type().equals("Combo Offer"))
        {
            holder.Add.setOnClickListener(null);
            holder.Sub.setOnClickListener(null);
        }
    }
    @Override
    public int getItemCount() {
        return productItems.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView title;
        protected TextView mrp;
        protected TextView mop;
        protected TextView ksprice;
        private ImageView ksprice_image;
        protected TextView stock;
        public ImageView imageView1;
        public ImageView imageView;
        public ImageButton Add;
        public ImageButton Sub;
        public  ImageView batch_numbers;
        public ViewHolder(final View itemView, recyclerViewItemClickListener itemClickListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      int position=getAdapterPosition();
                      Product p1=productItems.get(position);
                     mListener = itemClickListener;
                    Intent intent;
                    intent = new Intent(itemView.getContext(),Product_desc_activity.class);
                    intent.putExtra("product_id",p1.getProduct_id());
                    intent.putExtra("name",p1.get_Name());
                    intent.putExtra("link",p1.get_link());
                    intent.putExtra("stock",p1.get_Stock());
                    intent.putExtra("mrp",p1.getPrice_mrp());
                    intent.putExtra("mop",p1.getPrice_mop());
                    intent.putExtra("ksprice",p1.getPrice_ks());
                    itemView.getContext().startActivity(intent);
           }
                 });
        title = (TextView) itemView.findViewById(R.id.desc1);
        mrp = (TextView) itemView.findViewById(R.id.mrp);
        mop = (TextView) itemView.findViewById(R.id.mop);
        ksprice = (TextView) itemView.findViewById(R.id.ks_price);
        stock = (TextView) itemView.findViewById(R.id.stock);
        imageView=(ImageView)itemView.findViewById(R.id.out_of_stock);
        imageView.setVisibility(View.INVISIBLE);
        imageView1=(ImageView)itemView.findViewById(R.id.close);
        Add=(ImageButton)itemView.findViewById(R.id.add);
        Sub=(ImageButton)itemView.findViewById(R.id.sub);
        batch_numbers = (ImageView)itemView.findViewById(R.id.batch_numbers);
        ksprice_image = (ImageView)itemView.findViewById(R.id.ks_price_image);
            batch_numbers.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v,getAdapterPosition());
        }
    }
   public void delete_viewed(final Product m)
   {
       String url = AppConfig.url_delete_viewed+"customer_email="+appConfig.getUser_email()+"&product_id="+m.getProduct_id();
       Log.d("URL:",url);
       JsonArrayRequest deleteRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {
               Log.d("True Response", response.toString());
               for (int i = 0; i < response.length(); i++) {
                   try {
                       JSONObject obj = response.getJSONObject(i);
                       {
                           String recv = obj.getString("response");
                        if(recv.equals("success"))
                         {
                             Log.d("Product Deletion","success");
                         }
                         else{
                            Log.d("Product Deletion","failure");
                        }
                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               VolleyLog.d("VOLLEY ERROR:-", "Error: " + error.getMessage());
           }
       });
       deleteRequest.setRetryPolicy(new DefaultRetryPolicy(
               DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
               DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
               DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       AppController.getInstance().addToRequestQueue(deleteRequest);
   }
   public void change_count_product_online(Product product,String operation){
        String url = operation.equals("Increment") ? AppConfig.url_increment_viewed : AppConfig.url_decrement_viewed ;
        Log.d("url",""+url);
        JsonArrayRequest decrementRequest = new JsonArrayRequest(url+"product_id="+product.getProduct_id()+"&customer_email="+appConfig.getUser_email(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
               try {
                   JSONObject obj = response.getJSONObject(0);
                String response1 = obj.getString("response");
                if(response1.equals("success"))
                {
                    Log.d("response","successfully changed the count");
                }
                else
                {
                    Log.d("response","could not change the count error at server");
                }
               }catch (Exception e)
               {
                   Log.d("Error:","Some error has taken place in request of changing count");
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VOLLEY ERROR:-", "Error: " + error.getMessage());

            }
        });
       decrementRequest.setRetryPolicy(new DefaultRetryPolicy(
               DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
               DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
               DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

       AppController.getInstance().addToRequestQueue(decrementRequest);
    }
   public  void check_avail(final Product m, final ViewHolder holder)
   {
       final int[] count = new int[1];
       Log.d("URL:",AppConfig.url_product_stock_check+m.getProduct_id());
       JsonArrayRequest movieReq = new JsonArrayRequest(AppConfig.url_product_stock_check+m.getProduct_id(),
               new Response.Listener<JSONArray>() {
                   @Override
                   public void onResponse(JSONArray response) {
                       Log.d("True Response", response.toString());
                       for (int i = 0; i < response.length(); i++) {
                           try {
                               JSONObject obj = response.getJSONObject(i);
                              {
                                count[0] = obj.getInt("count");
                                  Log.d("Value : ",""+count[0]);
                                  count_global=count[0];
                                  if (m.get_Stock() > count_global)
                                  {
                                      holder.imageView.setVisibility(View.VISIBLE);
                                  }
                                  else
                                  {
                                      holder.imageView.setVisibility(View.INVISIBLE);
                                  }

                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }

                                 }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       VolleyLog.d("VOLLEY ERROR:-", "Error: " + error.getMessage());


                   }

               });
       movieReq.setRetryPolicy(new DefaultRetryPolicy(
               DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
               DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
               DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

       AppController.getInstance().addToRequestQueue(movieReq);

   }
}