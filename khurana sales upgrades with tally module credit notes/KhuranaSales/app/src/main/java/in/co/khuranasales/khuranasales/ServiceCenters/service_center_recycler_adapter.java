package in.co.khuranasales.khuranasales.ServiceCenters;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class service_center_recycler_adapter extends RecyclerView.Adapter<service_center_recycler_adapter.ViewHolder>{
    public ArrayList<service_center> service_centers = new ArrayList<>();
    public Activity activity;
    public recyclerViewItemClickListener mlistener;
    public String deletable_id = "-1";

    public service_center_recycler_adapter(Activity activity, ArrayList<service_center> service_centers, recyclerViewItemClickListener listner)
    {
        this.service_centers = service_centers;
        this.mlistener = listner;
        this.activity = activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_center_recycler_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.shop_name.setText(service_centers.get(position).getName());
        holder.shop_address.setText("Shop Address: "+service_centers.get(position).getAddress());
        holder.shop_phone.setText("Shop Contact: "+service_centers.get(position).getPhone());
        holder.shop_opens_at.setText("Shop Opens At: "+service_centers.get(position).getOpens_at());
        holder.shop_closes_at.setText("Shop Closes At: "+service_centers.get(position).getCloses_at());
        holder.shop_brand.setText("Service center brand: "+service_centers.get(position).getBrand());
        holder.delete_this_service_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletable_id = service_centers.get(position).getId();
                new delete_service_center().execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return service_centers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView shop_name;
        public TextView shop_address;
        public TextView shop_phone;
        public TextView shop_brand;
        public TextView shop_opens_at;
        public TextView shop_closes_at;
        public ImageView edit_this_service_center;
        public ImageView delete_this_service_center;

        public ViewHolder(View itemView) {
            super(itemView);
            shop_name = (TextView)itemView.findViewById(R.id.shop_name);
            shop_address = (TextView)itemView.findViewById(R.id.shop_address);
            shop_phone = (TextView)itemView.findViewById(R.id.shop_phone);
            shop_brand = (TextView)itemView.findViewById(R.id.service_center_brand);
            shop_opens_at = (TextView)itemView.findViewById(R.id.shop_open_at);
            shop_closes_at = (TextView)itemView.findViewById(R.id.shop_closes_at);
            delete_this_service_center = (ImageView)itemView.findViewById(R.id.delete_this_service_center);
            edit_this_service_center = (ImageView)itemView.findViewById(R.id.edit_this_service_center);
            edit_this_service_center.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mlistener.onItemClick(v,getAdapterPosition());
        }
    }

    public class delete_service_center extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try {
                params.put("service_center_id",deletable_id);
                Log.d("SENDINGPARAMS"+deletable_id,params.toString());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            JsonObjectRequest delete_request = new JsonObjectRequest(Request.Method.POST, AppConfig.delete_service_center_url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        if(response.getString("success").equals("true"))
                        {
                            Toast.makeText(activity.getApplicationContext(),"Deleted this service center",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(activity.getApplicationContext(),"Sorry Could Not Delete Please Check",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(activity.getApplicationContext(),"Please Retry!",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(activity.getApplicationContext(),"Error Took Place Please Retry!",Toast.LENGTH_SHORT).show();
                }
            });
            AppController.getInstance().addToRequestQueue(delete_request);
            return null;
        }
    }
}
