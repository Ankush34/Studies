package in.co.khuranasales.khuranasales.product_desc_activity_offers_adapter;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.Offers.Offer;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;


public class offers_adapter_product_desc extends RecyclerView.Adapter<offers_adapter_product_desc.viewHolder> {
    public ArrayList<Offer> offers;
    public Activity activity;
    public AppConfig appConfig;
    public recyclerViewItemClickListener m_listener;

    public offers_adapter_product_desc(ArrayList<Offer> offers_sent, Activity activity,recyclerViewItemClickListener listener)
    {
        this.offers = offers_sent;
        this.activity = activity;
        appConfig = new AppConfig(activity.getApplicationContext());
        this.m_listener = listener;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_desc_offers_combo, parent, false);
        offers_adapter_product_desc.viewHolder viewHolder = new offers_adapter_product_desc.viewHolder(view,m_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Offer offer = offers.get(position);
        if(offer.getOffer_type().equals("Super Value Offer"))
        {
            holder.item_count.setText(offer.getItem_count()+" Units");
            holder.mega_offer_image.setVisibility(View.VISIBLE);
            holder.discounted_price.setText(offer.getTotal_discounted_amount()+" /-");
            holder.super_value_offer_layout.setVisibility(View.VISIBLE);
        }
        else if(offer.getOffer_type().equals("Discount Offer"))
        {
            holder.discount_offered.setText(offer.getDiscount_amount_offeres_single()+" /-");
            holder.discount_offer_layout.setVisibility(View.VISIBLE);
            holder.discount_offer_image.setVisibility(View.VISIBLE);
        }
        else if(offer.getOffer_type().equals("Combo Offer"))
        {
            holder.eye.setVisibility(View.VISIBLE);
            holder.combo_offer_image.setVisibility(View.VISIBLE);
        }
        if(!appConfig.getUserType().equals("Admin"))
        {
            holder.delete_offer.setVisibility(View.GONE);
        }
        else
        {
            holder.delete_offer.setVisibility(View.VISIBLE);
        }
        holder.name.setText(offer.getOffer_product_name());
        holder.offer_description.setText(offer.getOffer_descripiton());
        holder.avail_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Intent intent = new Intent(activity.getApplicationContext(),Product_desc_activity.class);
//                    intent.putExtra("product_id",Integer.parseInt(offer.getOffr_producT_id()));
//                    intent.putExtra("name",offer.getOffer_product_name());
//                    intent.putExtra("link",offer.getOffer_product_image_links());
//                    intent.putExtra("stock",Integer.parseInt(offer.getOffer_product_instock_quantity()));
//                    intent.putExtra("mrp",Integer.parseInt(offer.getOffer_product_price()));
//                    intent.putExtra("mop",Integer.parseInt(offer.getOffer_product_mop_price()));
//                    intent.putExtra("ksprice",Integer.parseInt(offer.getOffer_product_discounted_price()));
//                    activity.startActivity(intent);
                Log.d("OFFERACTIVITY","offer type: "+offer.getOffer_type()+"list : "+offer.discounted_products_offers_list.size());
                if(offer.getOffer_type().equals("Combo Offer"))
                {
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = df.format(c);
                    for(int i =0 ; i <offer.discounted_products_offers_list.size();i++)
                    {
                        String array[] = new String[7];
                        array[0] = offer.discounted_products_offers_list.get(i).getOffr_producT_id();
                        array[1] = appConfig.getUser_email().trim();
                        array[2] = "Combo";
                        array[3] = "1";
                        array[4] = formattedDate;
                        array[5] = offer.discounted_products_offers_list.get(i).getDiscount_amount_offeres_single();
                        array[6] = offer.getOffr_producT_id();
                        new UploadProducts().execute(array);
                    }
                }

                if(offer.getOffer_type().equals("Discount Offer"))
                {
                    String array[] = new String[6];
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = df.format(c);
                    array[0] = offer.getOffr_producT_id();
                    array[1] = appConfig.getUser_email().trim();
                    array[2] = "Discount";
                    array[3] = "1";
                    array[4] = formattedDate;
                    array[5] = offer.getDiscount_amount_offeres_single();
                    new UploadProducts().execute(array);
                }

                if(offer.getOffer_type().equals("Super Value Offer"))
                {
                    String array[] = new String[7];
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = df.format(c);
                    array[0] = offer.getOffr_producT_id();
                    array[1] = appConfig.getUser_email().trim();
                    array[2] = "Super";
                    array[3] = "1";
                    array[4] = formattedDate;
                    array[5] = offer.getTotal_discounted_amount();
                    array[6] = offer.getItem_count();

                    new UploadProducts().execute(array);
                }


            }
        });
        holder.delete_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_id[] = new String[1];
                product_id[0] = offer.getOffr_producT_id();
                new delete_offers().execute(product_id);
                offers.remove(offer);
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return offers.size();
    }

    class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView name;
        public TextView offer_description;
        public Button avail_offer;
        public ImageView delete_offer;
        public LinearLayout discount_offer_layout;
        public LinearLayout super_value_offer_layout;
        public TextView item_count;
        public TextView discount_offered;
        public ImageView discount_offer_image;
        public ImageView combo_offer_image;
        public ImageView mega_offer_image;
        public TextView discounted_price;
        public ImageView eye;


        public viewHolder(View itemView,recyclerViewItemClickListener listener) {
            super(itemView);
            m_listener = listener;
            offer_description = (TextView)itemView.findViewById(R.id.offer_description);
            name = (TextView)itemView.findViewById(R.id.offer_product_name);
            delete_offer = (ImageView) itemView.findViewById(R.id.delete_offer);
            avail_offer = (Button)itemView.findViewById(R.id.avail_offer);
            discount_offer_layout = (LinearLayout)itemView.findViewById(R.id.discount_offer_layout);
            super_value_offer_layout = (LinearLayout)itemView.findViewById(R.id.super_value_offer_layout);
            item_count = (TextView)itemView.findViewById(R.id.item_counts);
            discounted_price = (TextView)itemView.findViewById(R.id.discounted_price);
            discount_offered = (TextView)itemView.findViewById(R.id.discount_amount);
            eye = (ImageView)itemView.findViewById(R.id.eye);
            discount_offer_image = (ImageView)itemView.findViewById(R.id.discount_offer_image);
            combo_offer_image = (ImageView)itemView.findViewById(R.id.combo_offer_image);
            mega_offer_image = (ImageView)itemView.findViewById(R.id.mega_offer_image);
            eye.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            m_listener.onItemClick(v,getAdapterPosition());
        }
    }

    class delete_offers extends AsyncTask<String, Void, Void>
    {

        @Override
        protected Void doInBackground(String... product_id) {
            JsonArrayRequest delete_request = new JsonArrayRequest(AppConfig.delete_offer+product_id[0], new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        if(response.getJSONObject(0).getString("status").equals("success"))
                        {
                            Toast.makeText(activity.getApplicationContext(),"Offer removed successfully",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(activity.getApplicationContext(),"Please Retry !!",Toast.LENGTH_SHORT).show();

                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(activity.getApplicationContext(),"Please Retry !!",Toast.LENGTH_SHORT).show();
                }
            });
            delete_request.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(delete_request);
            return null;
        }
    }
    class UploadProducts extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... data) {
            String url = "";
            if(data[2].equals("Super"))
            {
                url = AppConfig.upload_offers_product+"product_id="+data[0]+"&customer_email="+data[1]+"&offer_type="+data[2]+"&count="+data[3]+"&date="+data[4]+"&discount_offered="+data[5]+"&item_count="+data[6];
            }
            else if(data[2].equals("Combo"))
            {
                url = AppConfig.upload_offers_product+"product_id="+data[0]+"&customer_email="+data[1]+"&offer_type="+data[2]+"&count="+data[3]+"&date="+data[4]+"&discount_offered="+data[5]+"&main_product_id="+data[6];
            }
            else
            {
                url = AppConfig.upload_offers_product+"product_id="+data[0]+"&customer_email="+data[1]+"&offer_type="+data[2]+"&count="+data[3]+"&date="+data[4]+"&discount_offered="+data[5];
            }
            Log.d("OFFERADAPTER:",""+url);
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONObject object = response.getJSONObject(0);
                        if(object.getString("success").equals("true"))
                        {
                            Toast.makeText(activity.getApplicationContext(),"SuccessFully Uploaded Ur Offer",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(activity.getApplicationContext(),"Please Retry!",Toast.LENGTH_SHORT).show();
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(request);
            return null;
        }

    }
}

