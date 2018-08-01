package in.co.khuranasales.khuranasales;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Product> contents;
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    public recyclerViewItemClickListener mlistener;
    public AppConfig appConfig;
    public Context context;


    public TestRecyclerViewAdapter(List<Product> contents, Context context, recyclerViewItemClickListener listener) {
        this.contents = contents;
        appConfig = new AppConfig(context);
        this.context = context;
        this.mlistener = listener;

    }

    @Override
    public int getItemViewType(int position) {
        switch (position-1) {
            case -1:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return  new ViewHolder3(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                return new ViewHolder1(view,1,mlistener) {
                };
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ViewHolder3 holder2 =(ViewHolder3) holder;
                holder2.image.setBackgroundResource(R.drawable.samsung);
                break;
            case TYPE_CELL:
               final ViewHolder1 holder1 =(ViewHolder1) holder;
                Product p = contents.get(position-1);
                Log.d("UserType",""+( holder1.appConfig.getUserType()));
                if(!holder1.appConfig.getUserType().equals("Admin"))
                {
                    holder1.ks_price_image.setVisibility(View.GONE);
                    holder1.ks_price_title.setVisibility(View.GONE);
                    holder1.view3.setVisibility(View.GONE);
                    holder1.imageView.setVisibility(View.INVISIBLE);
                    holder1.add_to_offer.setVisibility(View.GONE);
                }

                if(p.isFavorite())
                {
                    Log.d("ProductViewActivity","IAMHERE1");
                    holder1.like.setVisibility(View.VISIBLE);
                    holder1.unlike.setVisibility(View.INVISIBLE);
                }
                else
                {

                    Log.d("ProductViewActivity","IAMHERE2");
                    holder1.unlike.setVisibility(View.VISIBLE);
                    holder1.like.setVisibility(View.INVISIBLE);
                }

                holder1.unlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new add_to_favorites().execute(p);
                        contents.get(position-1).setFavorite_status(true);
                        notifyItemChanged(position-1);
                        holder1.like.setVisibility(View.VISIBLE);
                        holder1.unlike.setVisibility(View.INVISIBLE);

                    }
                });
                holder1.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new remove_from_favorites().execute(p);
                        contents.get(position-1).setFavorite_status(false);
                        notifyItemChanged(position-1);
                        holder1.unlike.setVisibility(View.VISIBLE);
                        holder1.like.setVisibility(View.INVISIBLE);
                    }
                });
                holder1.textView.setText(p.get_Name());
                holder1.view1.setText(""+p.getPrice_mrp());
                holder1.view2.setText(""+p.getPrice_mop());
                holder1.view3.setText(""+p.getPrice_ks());
                holder1.textView2.setText("QT("+p.get_Stock()+")");
                holder1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        Product p1=contents.get(position-1);
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
                if(holder1.appConfig.getUserType().equals("Admin"))
                {
                    if(p.get_update_status())
                    {
                        Log.d("Loop: ","In If lloop");
                        holder1.imageView.setVisibility(View.INVISIBLE);
                        holder1.imageView1.setVisibility(View.VISIBLE);
                        holder1.text1.setVisibility(View.VISIBLE);
                        holder1.text2.setVisibility(View.VISIBLE);
                        holder1.text3.setVisibility(View.VISIBLE);
                        holder1.text1.setText(""+p.getPrice_mrp());
                        holder1.text2.setText(""+p.getPrice_mop());
                        holder1.text3.setText(""+p.getPrice_ks());
                    }
                    else
                    {
                        Log.d("Loop","In Else Loop");
                        holder1.imageView.setVisibility(View.VISIBLE);
                        holder1.imageView1.setVisibility(View.INVISIBLE);
                        holder1.text1.setVisibility(View.INVISIBLE);
                        holder1.text2.setVisibility(View.INVISIBLE);
                        holder1.text3.setVisibility(View.INVISIBLE);
                    }

                }
                holder1.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Log.d("clicked: ","I am clicked Pewncil");
                    holder1.imageView.setVisibility(View.INVISIBLE);
                    holder1.imageView1.setVisibility(View.VISIBLE);
                    holder1.text1.setVisibility(View.VISIBLE);
                    holder1.text2.setVisibility(View.VISIBLE);
                    holder1.text3.setVisibility(View.VISIBLE);
                    p.set_update_status(true);
                    }
                });
                holder1.imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        p.set_update_status(false);
                        Log.d("clicked: ","I am clicked Tick");
                        holder1.imageView1.setVisibility(View.INVISIBLE);
                        holder1.imageView.setVisibility(View.VISIBLE);
                        holder1.text1.setVisibility(View.INVISIBLE);
                        holder1.text2.setVisibility(View.INVISIBLE);
                        holder1.text3.setVisibility(View.INVISIBLE);
                        int mrp=p.getPrice_mrp();
                        if(!(holder1.text1.getText().toString().equals(null)||holder1.text1.getText().toString().equals("")))
                        {
                            mrp = Integer.parseInt(holder1.text1.getText().toString());
                        }
                        int mop=p.getPrice_mop();
                        if(!(holder1.text2.getText().toString().equals(null)||holder1.text2.getText().toString().equals("")))
                        {
                            mop= Integer.parseInt(holder1.text2.getText().toString());
                        }
                        int ksprice=p.getPrice_ks();
                        if(!(holder1.text3.getText().toString().equals(null)||holder1.text3.getText().toString().equals("")))
                        {
                            ksprice= Integer.parseInt(holder1.text3.getText().toString());
                        }
                        send_data(mrp,mop,ksprice,p.get_Name(),holder1,position);
                        notifyItemChanged(position-1);
                    }
                });
                break;
        }
    }
    class ViewHolder3 extends RecyclerView.ViewHolder{

        public ImageView image;
        public ViewHolder3(View itemView) {
            super(itemView);
            image= (ImageView)itemView.findViewById(R.id.image_offer);
          }
        public ViewHolder3(final View itemView, int a)
        {
             super(itemView);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener{
        public AppConfig appConfig ;
        public TextView textView;
        public ImageView imageView;
        public TextView view1;
        public TextView view2;
        public TextView textView2;
        public TextView view3;
        public ImageView imageView1;
        public EditText text1;
        public EditText text2;
        public EditText text3;
        public ImageView like;
        public ImageView unlike;
        public TextView add_to_offer;
        public TextView ks_price_title;
        public ImageView ks_price_image;



        public ViewHolder1(final View itemView) {
            super(itemView);
        }
        public ViewHolder1(final View itemView, int a, recyclerViewItemClickListener listener) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.mobile_name);
            imageView=(ImageView)itemView.findViewById(R.id.pencil);
            text1=(EditText)itemView.findViewById(R.id.edit_mrp);
            text2=(EditText)itemView.findViewById(R.id.edit_mop);
            text3=(EditText)itemView.findViewById(R.id.edit_ksprice);
            imageView1=(ImageView)itemView.findViewById(R.id.done);
            view1=(TextView)itemView.findViewById(R.id.mrp_price);
            view2=(TextView)itemView.findViewById(R.id.mop_price);
            view3=(TextView)itemView.findViewById(R.id.ks_price);
            textView2=(TextView)itemView.findViewById(R.id.stock);
            like = (ImageView)itemView.findViewById(R.id.like_star);
            appConfig = new AppConfig(itemView.getContext());
            unlike = (ImageView)itemView.findViewById(R.id.unlike_star);
            add_to_offer = (TextView)itemView.findViewById(R.id.add_to_offers);
            ks_price_image = (ImageView)itemView.findViewById(R.id.ks_price_image);
            ks_price_title = (TextView) itemView.findViewById(R.id.ks_price_title);
            mlistener = listener;
            add_to_offer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mlistener.onItemClick(v,getAdapterPosition());
        }
    }

public void send_data(final int mrp, final int mop, final int ksprice, final String name, final ViewHolder1 holder,int position)
{
    StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.url_price_update,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response:","successfull");
                        holder.view1.setText(""+mrp);
                        holder.view2.setText(""+mop);
                        holder.view3.setText(""+ksprice);
                        contents.get(position-1).setPrice_ks(ksprice);
                        contents.get(position-1).setPrice_mop(mop);
                        contents.get(position-1).setPrice_mrp(mrp);
                        notifyItemChanged(position-1);
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

public class add_to_favorites extends AsyncTask<Product, Void, Void>
{

    @Override
    protected Void doInBackground(Product... products) {
        JsonArrayRequest add_to_favorites_request = new JsonArrayRequest(AppConfig.add_to_favorites+products[0].getProduct_id()+"&email="+appConfig.getUser_email(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                try {
                    JSONObject first_ibject = response.getJSONObject(0);
                    if(first_ibject.getString("status").equals("success"))
                    {
                        Toast.makeText(context,"Successfully added to your favorites",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context,"Could not add to your favorites please retry !!",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Please Retry !!",Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().addToRequestQueue(add_to_favorites_request);
        return null;
    }
}

    public class remove_from_favorites extends AsyncTask<Product, Void, Void>
    {

        @Override
        protected Void doInBackground(Product... products) {
            JsonArrayRequest remove_from_favorites_request = new JsonArrayRequest(AppConfig.remove_from_favorites+products[0].getProduct_id()+"&email="+appConfig.getUser_email(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response)
                {
                    try {
                        JSONObject first_object = response.getJSONObject(0);
                        if(first_object.getString("status").equals("success"))
                        {
                            Toast.makeText(context,"Successfully removed from favorites",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context,"Could not remove from your favorites please retry !!",Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,"Please Retry !!",Toast.LENGTH_SHORT).show();
                }
            });
            AppController.getInstance().addToRequestQueue(remove_from_favorites_request);
            return null;
        }
    }


}
