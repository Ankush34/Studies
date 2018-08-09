package in.co.khuranasales.khuranasales.edit_profile;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.Buy_Now_Activity;
import in.co.khuranasales.khuranasales.Final_Cart;
import in.co.khuranasales.khuranasales.R;

public class editProfileActivity extends AppCompatActivity {
    public EditText user_name;
    public AppConfig appConfig;
    public ImageView done_user_name;
    public EditText user_email;
    public ImageView done_user_email;
    public EditText user_password;
    public ImageView done_user_password;
    public EditText user_pan_card;
    public ImageView done_user_pan_card;
    public EditText user_mobile_number;
    public ImageView done_user_mobile_number;
    public EditText user_shop_name;
    public ImageView done_user_shop_name;
    public EditText user_shop_address;
    public ImageView done_user_address;
    public EditText user_shop_gst;
    public ImageView done_user_gst;

    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);
        appConfig = new AppConfig(getApplicationContext());
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user_name = (EditText) findViewById(R.id.user_name);
        done_user_name = (ImageView)findViewById(R.id.done_user_name);
        user_email = (EditText)findViewById(R.id.user_email);
        done_user_email = (ImageView)findViewById(R.id.done_user_email);
        user_password = (EditText)findViewById(R.id.user_password);
        done_user_password = (ImageView)findViewById(R.id.done_user_password);
        user_pan_card = (EditText)findViewById(R.id.user_pan_card);
        done_user_pan_card = (ImageView)findViewById(R.id.done_user_pan);
        user_mobile_number = (EditText)findViewById(R.id.user_phone_number);
        done_user_mobile_number = (ImageView)findViewById(R.id.done_user_phone);
        user_shop_name = (EditText)findViewById(R.id.user_shop_name);
        done_user_shop_name = (ImageView)findViewById(R.id.done_user_shop_name);
        user_shop_address = (EditText)findViewById(R.id.user_shop_address);
        done_user_address = (ImageView)findViewById(R.id.done_user_shop_address);
        user_shop_gst = (EditText)findViewById(R.id.user_shop_gst);
        done_user_gst = (ImageView)findViewById(R.id.done_user_shop_gst);
        new load_profile().execute();

        done_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new update_profile_info().execute();
                hideSoftKeyboard(v);
            }
        });
        done_user_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new update_profile_info().execute();
                hideSoftKeyboard(v);
            }
        });
        done_user_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new update_profile_info().execute();
                hideSoftKeyboard(v);
            }
        });
        done_user_mobile_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new update_profile_info().execute();
                hideSoftKeyboard(v);
            }
        });
        done_user_pan_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new update_profile_info().execute();
                hideSoftKeyboard(v);
            }
        });
        done_user_shop_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new update_profile_info().execute();
                hideSoftKeyboard(v);
            }
        });
        done_user_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new update_profile_info().execute();
                hideSoftKeyboard(v);
            }
        });
        done_user_gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new update_profile_info().execute();
                hideSoftKeyboard(v);
            }
        });



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
                    Intent intent = new Intent(editProfileActivity.this,Final_Cart.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(editProfileActivity.this,"Please login to proceed",Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart= menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editProfileActivity.this,Buy_Now_Activity.class);
                startActivity(intent);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        if(id == android.R.id.home)
        {
            finish();
            overridePendingTransition(0,R.anim.slide_out_left_animation);
        }
       return super.onOptionsItemSelected(item);
    }

    class load_profile extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest request_profile = new JsonArrayRequest(AppConfig.url_load_profile+appConfig.getUser_email(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONObject profile  = response.getJSONObject(0);
                        user_name.setText(profile.getString("name"));
                        user_email.setText(profile.getString("email"));
                        user_password.setText(profile.getString("password"));
                        user_pan_card.setText(profile.getString("pan"));
                        user_mobile_number.setText(profile.getString("phone"));
                        user_shop_name.setText(profile.getString("shop_name"));
                        user_shop_address.setText(profile.getString("shop_address"));
                        user_shop_gst.setText(profile.getString("shop_gst"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Please Retry !!",Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Sorry Could Not Load Your Info",Toast.LENGTH_LONG).show();
                }
            });
            AppController.getInstance().addToRequestQueue(request_profile);
            return null;
        }
    }

    class update_profile_info extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try {
                params.put("name", user_name.getText().toString());
                params.put("email", user_email.getText().toString());
                params.put("password", user_password.getText().toString());
                params.put("phone", user_mobile_number.getText().toString());
                params.put("pan", user_pan_card.getText().toString());
                params.put("shop_name", user_shop_name.getText().toString());
                params.put("shop_address", user_shop_address.getText().toString());
                params.put("shop_gst", user_shop_gst.getText().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("URL: ",AppConfig.url_update_profile +"params: "+params.toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AppConfig.url_update_profile, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("RESPONSE", response.toString());
                    try {
                        if(response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Updation was successfull",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("RESPONSE",response.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error",error.getMessage());
                    Log.d("EDITPROFILEACTIVITY","an error has taken place");
                }
            });
            AppController.getInstance().addToRequestQueue(request);
        return null;
        }
    }

    private void hideSoftKeyboard(View v){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
