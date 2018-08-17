package in.co.khuranasales.khuranasales.ServiceCenters;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.Buy_Now_Activity;
import in.co.khuranasales.khuranasales.Buy_final_activity;
import in.co.khuranasales.khuranasales.R;

public class service_center_main_activity extends AppCompatActivity {
    public RecyclerView service_center_recycler;
    public RelativeLayout service_center_search_layout;
    public ImageView search_back;
    public TextView searched_records_found ;
    public horizontal_dotted_progress progress_indicator;
    public RecyclerView.Adapter service_center_recycler_adapter;
    public Toolbar toolbar;
    public EditText search_edit_text;
    String search = "";
    public ArrayList<service_center> service_centers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_center_main_layout);
        search_edit_text = (EditText)findViewById(R.id.search_text_top);
        searched_records_found = (TextView)findViewById(R.id.searched_records_found);
        progress_indicator = (horizontal_dotted_progress)findViewById(R.id.seach_progress_indicator);
        search_back = (ImageView)findViewById(R.id.search_back);
        service_center_search_layout = (RelativeLayout)findViewById(R.id.service_center_search_layout);
        service_center_search_layout.setVisibility(View.INVISIBLE);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideToBottom(service_center_search_layout);
            }
        });
        service_center_recycler = (RecyclerView)findViewById(R.id.recycler_service_center);
        service_center_recycler.setHasFixedSize(true);
        service_center_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        service_center_recycler_adapter = new service_center_recycler_adapter(this,service_centers);
        service_center_recycler.setAdapter(service_center_recycler_adapter);
        service_center_recycler_adapter.notifyDataSetChanged();
        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                search = s.toString();
                new search_service_centers().execute();
            }
            @Override public void afterTextChanged(Editable s) { }
        });
        new load_service_centers().execute();
    }
    class search_service_centers extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            String search_string = "";
            String words[] = search.split(" ");
            search_string = new String("%");
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
            JsonArrayRequest request = new JsonArrayRequest(AppConfig.search_service_center_url + search_string, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    service_centers.clear();
                    service_center_recycler_adapter.notifyDataSetChanged();
                    Log.d("ServiceCenterAvtivity",response.toString());
                    if(response.length() > 0)
                    {
                        for(int i = 0; i <response.length();i++)
                        {
                            service_center sc  = new service_center();
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                sc.setName(obj.getString("name"));
                                sc.setAddress(obj.getString("address"));
                                sc.setOpens_at(obj.getString("opens"));
                                sc.setCloses_at(obj.getString("closes"));
                                sc.setPhone(obj.getString("phone"));
                                sc.setBrand(obj.getString("brand"));
                                service_centers.add(sc);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        searched_records_found.setText(""+service_centers.size());
                        service_center_recycler_adapter.notifyDataSetChanged();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVICECENTERACTIVITY","some error has occured");
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

    class load_service_centers extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest service_centers_request = new JsonArrayRequest(AppConfig.get_service_centers_url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if(response.length() > 0)
                    {
                        for(int i = 0 ; i <response.length(); i++)
                        {
                            service_center sc  = new service_center();
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                sc.setName(obj.getString("name"));
                                sc.setAddress(obj.getString("address"));
                                sc.setOpens_at(obj.getString("opens"));
                                sc.setCloses_at(obj.getString("closes"));
                                sc.setPhone(obj.getString("phone"));
                                sc.setBrand(obj.getString("brand"));
                                service_centers.add(sc);
                                service_center_recycler_adapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("SERVICECENTERACTIVITY","some error has occured");
                }
            });
            service_centers_request.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(service_centers_request);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_service_centers, menu);
        final View action_cart = menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(service_center_main_activity.this, Buy_Now_Activity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search)
        {
            slideToTop(service_center_search_layout);
        }
        else if(id == android.R.id.home)
        {
            finish();
            overridePendingTransition(0,R.anim.slide_out_left_animation);
        }

        return super.onOptionsItemSelected(item);
    }

    public void slideToBottom(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight()+100);
        animate.setDuration(500);;
        animate.setFillAfter(true);
        view.startAnimation(animate);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) { }
            @Override public void onAnimationEnd(Animation animation) {view.setVisibility(View.INVISIBLE);}
            @Override public void onAnimationRepeat(Animation animation) { }
        });
        hide_my_keyboard();
    }

    public void slideToTop(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,view.getHeight(),0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {view.setVisibility(View.VISIBLE); }
            @Override public void onAnimationEnd(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
        });
    }
    public void hide_my_keyboard()
    {
        hideKeyboard(this);
        return;
    }
    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(service_center_search_layout.getWindowToken(), 0);
        return;
    }
}
