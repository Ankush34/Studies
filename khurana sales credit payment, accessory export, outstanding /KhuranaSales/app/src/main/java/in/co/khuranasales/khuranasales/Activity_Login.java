package in.co.khuranasales.khuranasales;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static in.co.khuranasales.khuranasales.AppConfig.url_login;

/**
 * Created by Ankush khurana on 6/16/2017.
 */

public class Activity_Login extends AppCompatActivity {
    public AutoCompleteTextView text1;
    public TextView text4;
    public ProgressBar progress;
    public AutoCompleteTextView text2;
    public ProgressDialog dialog;
    public ImageView login;
    public String email="";
    public String password="";
    public AppConfig appConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        appConfig =new AppConfig(this);
        text1=(AutoCompleteTextView)findViewById(R.id.email);
        text2=(AutoCompleteTextView)findViewById(R.id.password);
        text4=(TextView)findViewById(R.id.signup);
        progress=(ProgressBar)findViewById(R.id.progress);
        login=(ImageView)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean texts_necessary = false;
        email=text1.getText().toString();
        if(email.equals(""))
        {
            text1.setError("Please enter email");
            texts_necessary = true;
        }
        password=text2.getText().toString();
        if(password.equals(""))
        {
            text2.setError("Please enter password");
            texts_necessary = true;
        }
        if(texts_necessary)
        {
            return;
        }
        try_login();
            }
        });
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // Back?
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Back
            moveTaskToBack(true);
            return true;
        }
        else {
            // Return
            return super.onKeyDown(keyCode, event);
        }
    }

    public void try_login()
    {
        email = text1.getText().toString();
        Log.d("email for login:",""+email);
        Log.d("email for login:",""+email.trim().equals("khuranasales2015@gmail.com"));
        String tag_string_req = "sending";
        String send_url_new = url_login;
        Log.d("send url:- ", "" + url_login+"?email="+email.trim()+"&&password="+password);
        progress.setVisibility(View.VISIBLE);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(url_login+"?email="+email.trim()+"&&password="+password, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Error: ", "Register Response: " + response.toString());
                try {
                    JSONObject obj = response.getJSONObject(0);
                    String msg=obj.getString("success");
                    if (msg.equals("true")) {
                        progress.setVisibility(View.INVISIBLE);
                        JSONObject obj1 = response.getJSONObject(1);
                        appConfig.setUser_email(email);
                        appConfig.setUserType(obj1.getString("customer_type"));
                        appConfig.setCustomer_id(Integer.parseInt(obj1.getString("customer_id")));
                        appConfig.setUser_shop_name(obj1.getString("customer_shop_name"));
                        appConfig.setUser_name(obj1.getString("user_name"));
                        if(obj1.has("outstanding_amount"))
                        {
                            appConfig.setUser_outstanding(obj1.getString("outstanding_amount"));
                        }
                        appConfig.setStatus_login(true);
                        Toast.makeText(getApplicationContext(), "Welcome "+appConfig.getUser_name(),Toast.LENGTH_LONG).show();
                        if(getIntent().getExtras()!= null)
                        {
                            Intent output = new Intent(getApplicationContext(),Buy_Now_Activity.class);
                            setResult(200, output);
                            finish();
                        }
                        else
                        {
                            Intent intent = new Intent(getApplicationContext(),CategorizeDataActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Not Permitted Login OR Please Retry Again!!", Toast.LENGTH_LONG).show();
                        progress.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progress.setVisibility(View.INVISIBLE);
                Log.e("Error", "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Please Retry !! ", Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", ""+"khuranasales2015@gmail.com");
                params.put("password", ""+"mukeshskhurana");


                return params;
            }

        };

        // Adding request to request queue
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(arrayRequest,tag_string_req);
    }
}
