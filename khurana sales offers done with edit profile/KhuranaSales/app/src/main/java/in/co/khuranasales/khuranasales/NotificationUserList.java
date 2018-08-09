package in.co.khuranasales.khuranasales;

import android.animation.Animator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationUserList extends AppCompatActivity{
    public Toolbar toolbar;
    public RecyclerView notification_users;
    public ImageView notification_back;
    public static RelativeLayout notification_write_layout;
    public RecyclerView.Adapter notification_adapter;
    public ArrayList<User> users;
    public AppConfig appConfig;
    public EditText title;
    public EditText message;
    public Button send_notification;
    public static ArrayList<String> reg_ids = new ArrayList<String>();
    public RecyclerView search_name_recycler;
    public Spinner type_selection_spinner;
    public FadeOutAnimator outAnimator;
    public ArrayList<String> type_of_notification = new ArrayList<>();
    public write_notification_product_name_selection_adapter product_name_adapter;
    public ArrayList<String> product_names = new ArrayList<>();
    public EditText product_name;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_main);
        product_name = (EditText)findViewById(R.id.product_name);
        product_name.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                search_products(s.toString());
            }
            @Override public void afterTextChanged(Editable s) { }});
        type_selection_spinner = (Spinner)findViewById(R.id.type_selection_spinner);
        type_of_notification.add("Price Change");
        type_of_notification.add("New Launched Product");
        CustomSpinnerAdapter customAdapter=new CustomSpinnerAdapter(this,type_of_notification);
        type_selection_spinner.setAdapter(customAdapter);
        type_selection_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_selection_spinner.setSelected(true);
                Toast.makeText(getApplicationContext(),"Spinner: "+type_of_notification.get(position),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type_selection_spinner.setSelection(0);
            }
        });

        recyclerViewItemClickListener product_names_click_listener = (view, position) -> {
            // fadeIn(batch_selection_layout);
            Toast.makeText(getApplicationContext(),"position clicked"+position,Toast.LENGTH_LONG).show();
            product_name.setText(product_names.get(position));
        };
        search_name_recycler = (RecyclerView)findViewById(R.id.search_name_recycler);
        search_name_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        product_name_adapter = new write_notification_product_name_selection_adapter(this,product_names, product_names_click_listener);
        search_name_recycler.setAdapter(product_name_adapter);
        product_name_adapter.notifyDataSetChanged();


        appConfig = new AppConfig(getApplicationContext());
        send_notification= (Button)findViewById(R.id.send_notificaton);
        notification_back = (ImageView)findViewById(R.id.back_message);
        notification_write_layout = (RelativeLayout)findViewById(R.id.write_notification_layout);
        title = (EditText)findViewById(R.id.title);
        message = (EditText)findViewById(R.id.message);
        users = new ArrayList<>();
        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Khurana Sales");
        notification_users = (RecyclerView)findViewById(R.id.notificaiton_users);
        notification_users.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        notification_users.setLayoutManager(manager);
        notification_users.setLayoutManager(manager);
        notification_adapter = new NotificationAdapter(this,users);
        notification_users.setAdapter(notification_adapter);
        new LoadOutbox().execute();
        notification_adapter.notifyDataSetChanged();
        notification_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outAnimator = new FadeOutAnimator();
                outAnimator.setTarget(notification_write_layout);
                outAnimator.prepare(notification_write_layout);
                outAnimator.setDuration(400);
                outAnimator.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override public void onAnimationStart(Animator animator) {}
                    @Override public void onAnimationEnd(Animator animator) {notification_write_layout.setVisibility(View.INVISIBLE); }
                    @Override public void onAnimationCancel(Animator animator) {}
                    @Override public void onAnimationRepeat(Animator animator) { }});
                outAnimator.start();
                reg_ids.clear();
            }
        });
        send_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_notification(reg_ids);
            }
        });
    }
    public void send_notification(ArrayList<String> ids)
    {
        boolean compulsary = false;
        if(message.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"i am here 1",Toast.LENGTH_SHORT).show();
            message.setError("message is needed");
            compulsary = true;
        }

        if(title.getText().toString().equals(""))
        {

            Toast.makeText(getApplicationContext(),"i am here 2",Toast.LENGTH_SHORT).show();
            title.setError("title is needed");
            compulsary = true;
        }

        if(product_name.getText().toString().equals(""))
        {

            Toast.makeText(getApplicationContext(),"i am here 3",Toast.LENGTH_SHORT).show();
            product_name.setError("message is needed");
            compulsary = true;
        }

        if(!(type_selection_spinner.getSelectedItemPosition()>= 0))
        {

            Toast.makeText(getApplicationContext(),"i am here 4",Toast.LENGTH_SHORT).show();
            compulsary = true;
        }
        if(compulsary == true)
        {
            Toast.makeText(getApplicationContext(),"Data is pending..",Toast.LENGTH_SHORT).show();
        }
        else
        {
            compulsary = false;
            Toast.makeText(getApplicationContext(),"Sending Notifications Total: "+ids.size(),Toast.LENGTH_LONG).show();
            Log.e("MyNotification","id: "+ids.get(0));
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("regId", ids.get(0));
            params.put("message",message.getText().toString());
            params.put("title",title.getText().toString());
            params.put("push_type","individual");
            params.put("include_image","FALSE");
            params.put("product_name",product_name.getText().toString());
            params.put("notification_type",type_of_notification.get(type_selection_spinner.getSelectedItemPosition()));
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,AppConfig.url_send_notifications, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("NotificationActivity","Response: "+response.toString());
                            VolleyLog.v("Response:%n %s", response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                    Log.d("NotificationActivity","Error: "+error.getMessage());
                }
            });
            req.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(req);
            reg_ids.clear();
            outAnimator = new FadeOutAnimator();
            outAnimator.setTarget(notification_write_layout);
            outAnimator.prepare(notification_write_layout);
            outAnimator.setDuration(400);
            outAnimator.addAnimatorListener(new Animator.AnimatorListener() {
                @Override public void onAnimationStart(Animator animator) {}
                @Override public void onAnimationEnd(Animator animator) {notification_write_layout.setVisibility(View.INVISIBLE); }
                @Override public void onAnimationCancel(Animator animator) {}
                @Override public void onAnimationRepeat(Animator animator) { }});
            outAnimator.start();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.signin) {
            if(appConfig.isLogin())
            {
                Toast.makeText(NotificationUserList.this,"You are already logged in",Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent = new Intent(NotificationUserList.this,Activity_Login.class);
                startActivity(intent);
            }
        }else if(id == R.id.signout)
        {
            if(appConfig.isLogin())
            {
                Intent intent = new Intent(NotificationUserList.this,Activity_Login.class);
                startActivity(intent);

            }else{
                Toast.makeText(NotificationUserList.this," Please login to signout",Toast.LENGTH_LONG).show();

            }
        }else if(id == R.id.signup)
        {
            Intent intent = new Intent(NotificationUserList.this,MainActivity.class);
            startActivity(intent);
        }
        else if(id == android.R.id.home)
        {
            finish();
            overridePendingTransition(0,R.anim.slide_out_left_animation);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main_my_cart,menu);
        final View action_profile = menu.findItem(R.id.action_profile).getActionView();
        action_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appConfig.isLogin())
                {
                    Intent intent = new Intent(NotificationUserList.this,Final_Cart.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(NotificationUserList.this,"Please login to proceed",Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart= menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationUserList.this,Buy_Now_Activity.class);
                startActivity(intent);
            }
        });
        return true;
    }
    class LoadOutbox extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... params)  {
            Log.d("url",AppConfig.url_all_users_access);
            JsonArrayRequest usersReq = new JsonArrayRequest(AppConfig.url_all_users_access,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("True Response", response.toString());
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    User user = new User(obj.getString("name"),obj.getString("contact"),obj.getString("user_type"));
                                    user.setCustomer_id(Integer.parseInt(obj.getString("user_id")));
                                    user.setShop_name(obj.getString("shop_name"));
                                    user.setFirebaseld(obj.getString("firebaseId"));
                                    users.add(user);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            notification_adapter.notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("VOLLEY ERROR:-", "Error: " + error.getMessage());


                        }

                    });
            usersReq.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(usersReq);
            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute() {
            // dismiss the dialog after getting all products


        }
    }
    public void search_products(String search) {

        if (!search.equals("")) {
            product_names.clear();
            product_name_adapter.notifyDataSetChanged();
            String words[] = search.split(" ");
            String search_string = new String("%");
            for (String word : words) {
                if (word.equals(" ")) {
                    continue;
                } else {
                    search_string = search_string + word + "%";
                }
            }
            try {
                search_string = java.net.URLEncoder.encode(search_string, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d("Searched: ", "Reference " + AppConfig.url_search + search_string);
            JsonArrayRequest search_request = new JsonArrayRequest(AppConfig.load_product_names_search + search_string, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d("True Response", response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                           product_names.add(obj.getString("Name"));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    product_name_adapter.notifyDataSetChanged();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error Volley : ", error.toString());
                }
            });
            search_request.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(search_request);
        }
    }
}
