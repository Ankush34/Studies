package in.co.khuranasales.khuranasales;

/**
 * Created by Ankush khurana on 7/30/2017.
 */

import android.app.Activity;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by Ankush Khurana on 28/07/2017.
 */

public class access_grant_adapter extends RecyclerView.Adapter<access_grant_adapter.ViewHolder> {
    private String[] dataSource;
    public int count_global=0;
    private Activity activity;
    public User user_used;
    private List<User> users;
    public access_grant_adapter(Activity activity, List<User> users) {
        this.activity = activity;
        this.users = users;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.access_granting_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final User user = users.get(position);
        holder.name.setText(user.getName());
        holder.contact.setText(user.getContact_number());
        holder.access_type.setText(user.getUser_type());
        holder.select_access_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("access granting : ","in click listener");
                PopupMenu popup = new PopupMenu(view.getContext(), holder.select_access_type);
                popup.inflate(R.menu.menu_select_access);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        holder.access_type.setText(item.getTitle());
                        return true;
                    }
                });
                popup.show();
            }
        });
        holder.grant_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grant_permission(holder.access_type.getText().toString(),holder.contact.getText().toString(),user,position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return users.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView name ;
        TextView contact;
        TextView access_type;
        ImageView select_access_type;
        ImageView grant_permission;
        public ViewHolder(final View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name_to_write);
            contact =(TextView)itemView.findViewById(R.id.contact_to_write);
            select_access_type = (ImageView)itemView.findViewById(R.id.select_access_type);
            access_type = (TextView)itemView.findViewById(R.id.access_type_to_write);
            grant_permission = (ImageView)itemView.findViewById(R.id.grant_permission);
        }

    }
    public void grant_permission(final String permission, String contact, User user, final int position)
    {
        user_used = user;
        String url = AppConfig.url_update_access+"permission_type="+permission+"&&contact_number="+contact;
        JsonArrayRequest update_access_request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if(response.getJSONObject(0).getString("success").equals("true"))
                    {
                        user_used.setUser_type(permission);
                        notifyItemChanged(position);
                    Toast.makeText(activity.getApplicationContext(),"Successfully Updated Permissions",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Access Grant Adapter ",""+error);
            }
        });
        update_access_request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(update_access_request);
    }
}