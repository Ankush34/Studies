package in.co.khuranasales.khuranasales.Offers;

import android.app.Activity;
import android.content.Intent;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.Product_desc_activity;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;


public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.viewHolder> {
        public ArrayList<Offer> offers;
        public Activity activity;
        public AppConfig appConfig;
        public recyclerViewItemClickListener m_listener;
        public recyclerViewItemClickListener edit_listener;

        public OffersAdapter(ArrayList<Offer> offers_sent, Activity activity,recyclerViewItemClickListener listener, recyclerViewItemClickListener edit_listener)
        {
            this.offers = offers_sent;
            this.activity = activity;
            appConfig = new AppConfig(activity.getApplicationContext());
            this.m_listener = listener;
            this.edit_listener = edit_listener;
        }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offers_recycler_card, parent, false);
        OffersAdapter.viewHolder viewHolder = new OffersAdapter.viewHolder(view,m_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
            Offer offer = offers.get(position);


            if(offer.isExpired())
            {
                holder.offer_expired_image.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.offer_expired_image.setVisibility(View.INVISIBLE);
            }

            if(offer.getOffer_product_image_links().split(",").length != 0)
            {
                Picasso.with(activity.getApplicationContext())
                        .load(offer.getOffer_product_image_links().split(",")[0])
                        .placeholder(activity.getResources().getDrawable( R.drawable.weel )) //this is optional the image to display while the url image is downloading
                        .error( activity.getResources().getDrawable( R.drawable.nothing_found_images_product_desc))         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(holder.image_offered_product);

            }
            else
            {
                holder.image_offered_product.setImageDrawable(activity.getResources().getDrawable(R.drawable.nothing_found_images_product_desc));
            }

            if(offer.getOffer_type().equals("Super Value Offer"))
            {
                holder.item_count.setText(offer.getItem_count()+" Units");
                holder.discounted_price.setText(offer.getTotal_discounted_amount()+" /-");
                holder.super_value_offer_layout.setVisibility(View.VISIBLE);
            }
            else if(offer.getOffer_type().equals("Discount Offer"))
            {
                holder.discount_offered.setText(offer.getDiscount_amount_offeres_single()+" /-");
                holder.discount_offer_layout.setVisibility(View.VISIBLE);
            }
            else if(offer.getOffer_type().equals("Combo Offer"))
            {
                holder.eye.setVisibility(View.VISIBLE);
            }
            if(!appConfig.getUserType().equals("Admin"))
            {
                holder.delete_offer.setVisibility(View.GONE);
            }
            else
            {
                holder.delete_offer.setVisibility(View.VISIBLE);
            }
            holder.offer_title.setText(offer.getOffer_title());
            holder.name.setText(offer.getOffer_product_name());
            holder.item_in_stock.setText(offer.getOffer_product_instock_quantity());
            holder.offer_description.setText(offer.getOffer_descripiton());
            holder.ksprice.setText(offer.getOffer_product_discounted_price());
            holder.price.setText(offer.getOffer_product_price());
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
    public TextView price;
    public TextView ksprice;
    public TextView offer_description;
    public TextView offer_title;
    public TextView item_in_stock;
    public Button avail_offer;
    public ImageView delete_offer;
    public LinearLayout discount_offer_layout;
    public LinearLayout super_value_offer_layout;
    public TextView item_count;
    public TextView discount_offered;
    public TextView discounted_price;
    public ImageView image_offered_product;
    public ImageView offer_expired_image;
    public ImageView eye;
    public ImageView update_offer;


    public viewHolder(View itemView,recyclerViewItemClickListener listener) {
        super(itemView);
        m_listener = listener;
        price = (TextView)itemView.findViewById(R.id.offer_product_price);
        ksprice = (TextView)itemView.findViewById(R.id.offer_product_discounted_price);
        offer_description = (TextView)itemView.findViewById(R.id.offer_description);
        offer_title = (TextView)itemView.findViewById(R.id.offer_title);
        item_in_stock = (TextView)itemView.findViewById(R.id.offer_product_instock_quantity);
        name = (TextView)itemView.findViewById(R.id.offer_product_name);
        avail_offer = (Button)itemView.findViewById(R.id.avail_offer);
        delete_offer = (ImageView)itemView.findViewById(R.id.delete_offer);
        discount_offer_layout = (LinearLayout)itemView.findViewById(R.id.discount_offer_layout);
        super_value_offer_layout = (LinearLayout)itemView.findViewById(R.id.super_value_offer_layout);
        item_count = (TextView)itemView.findViewById(R.id.item_counts);
        discounted_price = (TextView)itemView.findViewById(R.id.discounted_price);
        discount_offered = (TextView)itemView.findViewById(R.id.discount_amount);
        eye = (ImageView)itemView.findViewById(R.id.eye);
        eye.setOnClickListener(this);
        image_offered_product = (ImageView)itemView.findViewById(R.id.image_offered_product);
        offer_expired_image = (ImageView)itemView.findViewById(R.id.image_offer_expired);
        update_offer = (ImageView)itemView.findViewById(R.id.update_offer);
        update_offer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.eye)
        {
            m_listener.onItemClick(v,getAdapterPosition());
        }
        else
        {
            edit_listener.onItemClick(v, getAdapterPosition());
        }
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
                 Toast.makeText(activity.getApplicationContext(),"Please Retruy !!",Toast.LENGTH_SHORT).show();
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

