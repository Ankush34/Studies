package in.co.khuranasales.khuranasales;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static in.co.khuranasales.khuranasales.AppConfig.url_register;

public class MainActivity extends AppCompatActivity {

    public static String Name;
    public static TextView view1;
    public static String Shop;
    public static ProgressBar bar;
    public static String Address;
    public static String EmailId;
    public static String Password;
    public static String Pan;
    public static String Phone;
    public static String GST;
    public static EditText text1;
    public static EditText text2;
    public static EditText text3;
    public static EditText text4;
    public static EditText text5;
    public static EditText text6;
    public static EditText text7;
    public static EditText text8;
    public static ImageView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        bar=(ProgressBar)findViewById(R.id.progress);
        text1=(EditText)findViewById(R.id.name);
        view1=(TextView)findViewById(R.id.registered);
        text2=(EditText)findViewById(R.id.shop_new);
        text3=(EditText)findViewById(R.id.address);
        text4=(EditText)findViewById(R.id.email);
        text5=(EditText)findViewById(R.id.password);
        text6=(EditText)findViewById(R.id.Pan);
        text7=(EditText)findViewById(R.id.Phone);
        text8=(EditText)findViewById(R.id.GST);
        register=(ImageView) findViewById(R.id.signup);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Activity_Login.class);
                startActivity(intent);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean texts_necessary = false;
                Name=text1.getText().toString();
                Shop=text2.getText().toString();
                Address=text3.getText().toString();
                EmailId=text4.getText().toString();
                Password=text5.getText().toString();
                Pan=text6.getText().toString();
                Phone=text7.getText().toString();
                GST=text8.getText().toString();
                if(Name.equals(""))
                {
                    text1.setError("Please enter name");
                    texts_necessary = true;
                }
                if(Shop.equals(""))
                {
                    text2.setError("Please enter name");
                    texts_necessary = true;
                }
                if(Address.equals(""))
                {
                    text3.setError("Please enter name");
                    texts_necessary = true;
                }
                if(EmailId.equals(""))
                {
                    text4.setError("Please enter name");
                    texts_necessary = true;
                }
                if(Password.equals(""))
                {
                    text5.setError("Please enter name");
                    texts_necessary = true;
                }
                if(Pan.equals(""))
                {
                    text6.setError("Please enter name");
                    texts_necessary = true;
                }
                if(Phone.equals(""))
                {
                    text7.setError("Please enter name");
                    texts_necessary = true;
                }
                if(GST.equals(""))
                {
                    text8.setError("Please enter name");
                    texts_necessary = true;
                }
                if(texts_necessary)
                {
                    return;
                }
                else
                {
                    registerUser();
                }



            }
        });
       }

    /* Prevent app from being killed on back */
     private void registerUser() {
        // Tag used to cancel the request
        String tag_string_req = "sending";
        String send_url_new = url_register;
        Log.d("send url:- ", "" + url_register);
        bar.setVisibility(View.VISIBLE);
        StringRequest strReq = new StringRequest(Method.POST,
                send_url_new, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Error: ", "Register Response: " + response.toString());
                Intent intent=new Intent(getApplicationContext(),Activity_Login.class);
                startActivity(intent);

                try {
                    if (!response.equals(null)) {
                        bar.setVisibility(View.INVISIBLE);
                        Log.d("logs:", "location successfully entered!");
                        Toast.makeText(getApplicationContext(),"Successfull Registered", Toast.LENGTH_LONG).show();
                    } else {
                        bar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Sorry Could Not Register ", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                bar.setVisibility(View.INVISIBLE);
                Log.e("Error", "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("Name", ""+Name);
                params.put("Shop", ""+Shop);
                params.put("Address",""+Address);
                params.put("EmailId", ""+EmailId.trim());
                params.put("Password", ""+Password);
                params.put("Pan", ""+Pan.trim());
                params.put("Phone", ""+Phone.trim());
                params.put("GST", ""+GST.trim());

                return params;
            }

        };

        // Adding request to request queue
         strReq.setRetryPolicy(new DefaultRetryPolicy(
                 DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                 DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                 DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

         AppController.getInstance().addToRequestQueue(strReq,tag_string_req);

     }

}
