package in.co.khuranasales.khuranasales;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class provideAccessActivity extends AppCompatActivity{
    public RecyclerView user_cards_recycler;
    public RecyclerView.Adapter user_card_adapter;
    private AppConfig appConfig;
    public ArrayList<User> users =  new ArrayList<User>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access_grant_recycler_view);
        appConfig = new AppConfig(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user_cards_recycler = (RecyclerView)findViewById(R.id.user_card_recycler);
        user_cards_recycler.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        user_cards_recycler.setLayoutManager(layoutManager);
        user_card_adapter = new access_grant_adapter(this,users);
        user_cards_recycler.setAdapter(user_card_adapter);
        new LoadOutbox().execute();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_my_cart, menu);
        final View action_profile = menu.findItem(R.id.action_profile).getActionView();
        action_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appConfig.isLogin())
                {
                    Intent intent = new Intent(provideAccessActivity.this,Final_Cart.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(provideAccessActivity.this,"Please login to proceed",Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart= menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(provideAccessActivity.this,Buy_Now_Activity.class);
                startActivity(intent);
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.signin) {
            if(appConfig.isLogin())
            {
                Toast.makeText(provideAccessActivity.this,"You are already logged in",Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent = new Intent(provideAccessActivity.this,Activity_Login.class);
                startActivity(intent);
            }
        }else if(id == R.id.signout)
        {
            if(appConfig.isLogin())
            {
                Intent intent = new Intent(provideAccessActivity.this,Activity_Login.class);
                startActivity(intent);

            }else{
                Toast.makeText(provideAccessActivity.this," Please login to signout",Toast.LENGTH_LONG).show();

            }
        }else if(id == R.id.signup)
        {
            Intent intent = new Intent(provideAccessActivity.this,MainActivity.class);
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
                                    user.setEmail(obj.getString("email"));
                                    users.add(user);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            user_card_adapter.notifyDataSetChanged();
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
}
