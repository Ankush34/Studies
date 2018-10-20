package in.co.khuranasales.khuranasales.ServiceCenters;
import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;

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
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class service_center_main_activity extends AppCompatActivity {
    public RecyclerView service_center_recycler;
    public RelativeLayout service_center_search_layout;
    public ImageView search_back;
    public TextView searched_records_found ;
    public horizontal_dotted_progress progress_indicator;
    public RecyclerView.Adapter service_center_recycler_adapter;
    public Toolbar toolbar;
    public EditText search_edit_text;
    public FadeInAnimator in_animator;
    public FadeOutAnimator animator;
    public RelativeLayout add_new_service_center_layout;
    public horizontal_dotted_progress search_progress_indicator;
    public EditText service_center_name_text;
    public EditText service_center_address_text;
    public EditText service_center_contact_text;
    public EditText service_center_opens_at;
    public EditText service_center_closes_at;
    public EditText service_center_brand_text;
    public Button submit_service_center_details;
    public RelativeLayout edit_service_center_layout;
    public EditText edit_service_center_name;
    public EditText edit_service_center_address;
    public EditText edit_service_center_contact;
    public EditText edit_service_center_open_timing;
    public EditText edit_service_center_close_timing;
    public EditText edit_service_center_brand;
    public Button submit_edit_details;
    public String clicked_id = "-1";
    String search = "";
    public ArrayList<service_center> service_centers = new ArrayList<>();
    public ImageView back_from_create_new;
    public ImageView back_edit_service_center;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_center_main_layout);
        edit_service_center_layout = (RelativeLayout)findViewById(R.id.edit_service_center_layout);
        edit_service_center_name = (EditText)findViewById(R.id.edit_service_center_name);
        edit_service_center_address = (EditText)findViewById(R.id.edit_service_center_address);
        edit_service_center_contact = (EditText)findViewById(R.id.edit_service_center_contact);
        edit_service_center_open_timing = (EditText)findViewById(R.id.edit_service_center_open_timing);
        edit_service_center_close_timing = (EditText)findViewById(R.id.edit_service_center_close_timing);
        edit_service_center_brand = (EditText)findViewById(R.id.edit_service_center_brand_text);
        submit_edit_details = (Button)findViewById(R.id.submit_edit_service_center_details);
        submit_edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean need_to_fill_edit = false;
                if(edit_service_center_name.getText().toString().equals(""))
                {
                    edit_service_center_name.setError("Please Fill This Field");
                    need_to_fill_edit = true;
                }
                if(edit_service_center_address.getText().toString().equals(""))
                {
                    edit_service_center_address.setError("Please Fill This Field");
                    need_to_fill_edit = true;
                }
                if(edit_service_center_contact.getText().toString().equals(""))
                {
                    edit_service_center_contact.setError("Please Fill This Field");
                    need_to_fill_edit = true;
                }
                if(edit_service_center_open_timing.getText().toString().equals(""))
                {
                    edit_service_center_open_timing.setError("Please Fill This Field");
                    need_to_fill_edit = true;
                }
                if(edit_service_center_close_timing.getText().toString().equals(""))
                {
                    edit_service_center_close_timing.setError("Please Fill This Field");
                    need_to_fill_edit = true;
                }
                if(edit_service_center_brand.getText().toString().equals(""))
                {
                    edit_service_center_brand.setError("Please Fill This Field");
                    need_to_fill_edit = true;
                }
                if(!need_to_fill_edit)
                {
                    fadeOut(edit_service_center_layout);
                    new edit_service_center_request().execute();
                }
            }
        });

        submit_service_center_details = (Button)findViewById(R.id.submit_service_center_details);
        service_center_name_text = (EditText)findViewById(R.id.service_center_name);
        service_center_address_text = (EditText)findViewById(R.id.service_center_address);
        service_center_contact_text = (EditText)findViewById(R.id.service_center_contact);
        service_center_opens_at = (EditText)findViewById(R.id.service_center_open_timing);
        service_center_closes_at = (EditText)findViewById(R.id.service_center_close_timing);
        service_center_brand_text = (EditText)findViewById(R.id.service_center_brand_text);
        submit_service_center_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean need_to_fill = false;
                if(service_center_name_text.getText().toString().equals(""))
                {
                 service_center_name_text.setError("Please Fill This Field");
                 need_to_fill = true;
                }
                if(service_center_address_text.getText().toString().equals(""))
                {
                    service_center_address_text.setError("Please Fill This Field");
                    need_to_fill = true;
                }
                if(service_center_contact_text.getText().toString().equals(""))
                {
                    service_center_contact_text.setError("Please Fill This Field");
                    need_to_fill = true;
                }
                if(service_center_opens_at.getText().toString().equals(""))
                {
                    service_center_opens_at.setError("Please Fill This Field");
                    need_to_fill = true;
                }
                if(service_center_closes_at.getText().toString().equals(""))
                {
                    service_center_closes_at.setError("Please Fill This Field");
                    need_to_fill = true;
                }
                if(service_center_brand_text.getText().toString().equals(""))
                {
                    service_center_brand_text.setError("Please Fill This Field");
                    need_to_fill = true;
                }
                if(!need_to_fill)
                {
                   new add_service_center_request().execute();
                   fadeOut(add_new_service_center_layout);
                }
            }
        });
        add_new_service_center_layout = (RelativeLayout)findViewById(R.id.add_new_service_center_layout);
        back_from_create_new = (ImageView)findViewById(R.id.back_add_service_center);
        back_from_create_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(add_new_service_center_layout);
            }
        });
        back_edit_service_center = (ImageView)findViewById(R.id.back_edit_service_center);
        back_edit_service_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(edit_service_center_layout);
            }
        });
        search_progress_indicator = (horizontal_dotted_progress)findViewById(R.id.search_progress_indicator);
        search_edit_text = (EditText)findViewById(R.id.search_text_top);
        searched_records_found = (TextView)findViewById(R.id.searched_records_found);
        progress_indicator = (horizontal_dotted_progress)findViewById(R.id.search_progress_indicator);
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
        recyclerViewItemClickListener service_center_selected_listener = (view, position) -> {
            fadeIn(edit_service_center_layout);
            edit_service_center_name.setText(service_centers.get(position).getName());
            edit_service_center_brand.setText("Service center brand: "+service_centers.get(position).getBrand());
            edit_service_center_address.setText("Shop Address: "+service_centers.get(position).getAddress());
            edit_service_center_contact.setText("Shop Contact: "+service_centers.get(position).getPhone());
            edit_service_center_open_timing.setText(service_centers.get(position).getOpens_at());
            edit_service_center_close_timing.setText(service_centers.get(position).getCloses_at());
            clicked_id = service_centers.get(position).getId();
            };
        service_center_recycler_adapter = new service_center_recycler_adapter(this,service_centers,service_center_selected_listener
        );
        service_center_recycler.setAdapter(service_center_recycler_adapter);
        service_center_recycler_adapter.notifyDataSetChanged();
        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                search = s.toString();
                search_progress_indicator.setVisibility(View.VISIBLE);
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
                                sc.setId(obj.getString("id"));
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
                                sc.setId(obj.getString("id"));
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
        else if(id == R.id.add_new_service_center)
        {
            fadeIn(add_new_service_center_layout);
        }

        return super.onOptionsItemSelected(item);
    }

    public void slideToBottom(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight()+200);
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

    public void fadeOut(View v)
    {
        animator = new FadeOutAnimator();
        animator.setTarget(v);
        animator.prepare(v);
        animator.setDuration(500);
        animator.addAnimatorListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) { }
            @Override public void onAnimationEnd(Animator animation) {v.setVisibility(View.INVISIBLE); }
            @Override public void onAnimationCancel(Animator animation) { }
            @Override public void onAnimationRepeat(Animator animation) { }
        });
        animator.start();
    }

    public void fadeIn(View v)
    {

        in_animator = new FadeInAnimator();
        in_animator.setTarget(v);
        in_animator.prepare(v);
        in_animator.setDuration(500);
        in_animator.addAnimatorListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) { v.setVisibility(View.VISIBLE);}
            @Override public void onAnimationEnd(Animator animation) {}
            @Override public void onAnimationCancel(Animator animation) { }
            @Override public void onAnimationRepeat(Animator animation) { }
        });
        in_animator.start();
    }

    public class add_service_center_request extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params_json = new JSONObject();
            try {
                params_json.put("service_center_name",service_center_name_text.getText().toString());
                params_json.put("service_center_address",service_center_address_text.getText().toString());
                params_json.put("service_center_contact",service_center_contact_text.getText().toString());
                params_json.put("service_center_closes_at",service_center_closes_at.getText().toString());
                params_json.put("service_center_opens_at",service_center_opens_at.getText().toString());
                params_json.put("service_center_brand",service_center_brand_text.getText().toString()); }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            JsonObjectRequest request_add_new_service_center = new JsonObjectRequest(Request.Method.POST, AppConfig.add_service_center_url, params_json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("RESPONSe", response.toString());
                    try
                    {
                        if(response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Successfully updated service center",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Could not updatex` service center please retry !!    ",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Error Please Retry!",Toast.LENGTH_SHORT).show();
                    }
                                    }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("VOLLEYERROR",error.getMessage());
                    Toast.makeText(getApplicationContext(),"Error Please Retry",Toast.LENGTH_SHORT).show();
                }
            });
            AppController.getInstance().addToRequestQueue(request_add_new_service_center);
            return null;
        }
    }

    public class edit_service_center_request extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params_json = new JSONObject();
            try {
                params_json.put("service_center_name",edit_service_center_name.getText().toString());
                params_json.put("service_center_address",edit_service_center_address.getText().toString());
                params_json.put("service_center_contact",edit_service_center_contact.getText().toString());
                params_json.put("service_center_closes_at",edit_service_center_close_timing.getText().toString());
                params_json.put("service_center_opens_at",edit_service_center_open_timing.getText());
                params_json.put("service_center_brand",edit_service_center_brand.getText().toString());
                params_json.put("service_center_id",clicked_id);
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
            JsonObjectRequest request_add_new_service_center = new JsonObjectRequest(Request.Method.POST, AppConfig.update_service_center_url, params_json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("RESPONSe", response.toString());
                    try
                    {
                        if(response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Successfully added new service center",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Could not add service center please retry !!    ",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Error Please Retry!",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("VOLLEYERROR",error.getMessage());
                    Toast.makeText(getApplicationContext(),"Error Please Retry",Toast.LENGTH_SHORT).show();
                }
            });
            AppController.getInstance().addToRequestQueue(request_add_new_service_center);
            return null;
        }
    }
}
