package in.co.khuranasales.khuranasales.Offers;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.Product_desc_activity;
import in.co.khuranasales.khuranasales.R;


public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.viewHolder> {
        public ArrayList<Offer> offers;
        public Activity activity;
        public AppConfig appConfig;
        public OffersAdapter(ArrayList<Offer> offers_sent, Activity activity)
        {
            this.offers = offers_sent;
            this.activity = activity;
            appConfig = new AppConfig(activity.getApplicationContext());
        }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offers_recycler_card, parent, false);
        OffersAdapter.viewHolder viewHolder = new OffersAdapter.viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
            Offer offer = offers.get(position);
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
                    Intent intent = new Intent(activity.getApplicationContext(),Product_desc_activity.class);
                    intent.putExtra("product_id",Integer.parseInt(offer.getOffr_producT_id()));
                    intent.putExtra("name",offer.getOffer_product_name());
                    intent.putExtra("link",offer.getOffer_product_image_links());
                    intent.putExtra("stock",Integer.parseInt(offer.getOffer_product_instock_quantity()));
                    intent.putExtra("mrp",Integer.parseInt(offer.getOffer_product_price()));
                    intent.putExtra("mop",Integer.parseInt(offer.getOffer_product_mop_price()));
                    intent.putExtra("ksprice",Integer.parseInt(offer.getOffer_product_discounted_price()));
                    activity.startActivity(intent);

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

    class viewHolder extends RecyclerView.ViewHolder
{
    public TextView name;
    public TextView price;
    public TextView ksprice;
    public TextView offer_description;
    public TextView offer_title;
    public TextView item_in_stock;
    public Button avail_offer;
    public ImageView delete_offer;
    public viewHolder(View itemView) {
        super(itemView);
        price = (TextView)itemView.findViewById(R.id.offer_product_price);
        ksprice = (TextView)itemView.findViewById(R.id.offer_product_discounted_price);
        offer_description = (TextView)itemView.findViewById(R.id.offer_description);
        offer_title = (TextView)itemView.findViewById(R.id.offer_title);
        item_in_stock = (TextView)itemView.findViewById(R.id.offer_product_instock_quantity);
        name = (TextView)itemView.findViewById(R.id.offer_product_name);
        avail_offer = (Button)itemView.findViewById(R.id.avail_offer);
        delete_offer = (ImageView)itemView.findViewById(R.id.delete_offer);
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
         AppController.getInstance().addToRequestQueue(delete_request);
         return null;
     }
 }
}

