package in.co.khuranasales.khuranasales;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;
import com.google.common.io.LineReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class SoldItemPromotersActivity extends AppCompatActivity{
    public static RecyclerView promoters_recycler;
    public RecyclerView promoter_recycler_list;
    public RecyclerView.Adapter promoter_recycler_adapter;
    public ArrayList<Promoter> promoters = new ArrayList<>();
    public Toolbar toolbar;
    private AppConfig appConfig;
    public ImageView back_promoter_selection;
    public RelativeLayout layout_promoter_selection;
    public RecyclerView.Adapter promoter_list_adapter;
    public String date = "";
    public ArrayList<String> promoters_names = new ArrayList<>();
    public HashMap<String,ArrayList<Product>> promoters_hash = new HashMap<String,ArrayList<Product>>();
    public FloatingActionButton fab_promoter_list;
    public RelativeLayout promoter_selection_layout_main;
    public RecyclerView promoter_selection_recycler_main;
    public ImageView back_promoter_selection_main;
    public ImageView reload_button_promoter_main;
    public Button selection_button_promoter_main;
    public RecyclerView.Adapter promoter_selection_main_adapter;
    public  ArrayList<Promoter> promoter_selection_main_names =  new ArrayList<>();
    private int mYear, mMonth, mDay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promoters_sold_recycler);
        appConfig = new AppConfig(getApplicationContext());
        promoter_selection_layout_main  =  (RelativeLayout)findViewById(R.id.layout_promoter_selection_single);
        promoter_selection_recycler_main = (RecyclerView)findViewById(R.id.recycler_promoter_selection_single);
        back_promoter_selection_main = (ImageView)findViewById(R.id.back_promoter_selection_main);
        reload_button_promoter_main = (ImageView)findViewById(R.id.reload_button_promoter_main);
        selection_button_promoter_main = (Button)findViewById(R.id.selection_button_promoter_main);
        promoter_selection_recycler_main.setHasFixedSize(true);
        promoter_selection_recycler_main.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getApplicationContext()));
        promoter_selection_main_adapter = new PromoterSelectionMainAdapter(this,promoter_selection_main_names);
        promoter_selection_recycler_main.setAdapter(promoter_selection_main_adapter);
        promoter_selection_main_adapter.notifyDataSetChanged();
        back_promoter_selection_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FadeOutAnimator fadeout = new FadeOutAnimator();
                fadeout.setDuration(400);
                fadeout.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override public void onAnimationStart(Animator animation) {}
                    @Override public void onAnimationEnd(Animator animation) {promoter_selection_layout_main.setVisibility(View.GONE);}
                    @Override public void onAnimationCancel(Animator animation) {}
                    @Override public void onAnimationRepeat(Animator animation) {} });
                fadeout.setTarget(promoter_selection_layout_main);
                fadeout.prepare(promoter_selection_layout_main);
                fadeout.start();
            }
        });
        selection_button_promoter_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FadeOutAnimator fadeout = new FadeOutAnimator();
                fadeout.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override public void onAnimationStart(Animator animation) {}
                    @Override public void onAnimationEnd(Animator animation) {promoter_selection_layout_main.setVisibility(View.GONE);}
                    @Override public void onAnimationCancel(Animator animation) {}
                    @Override public void onAnimationRepeat(Animator animation) { } });
                fadeout.setDuration(400);
                fadeout.setTarget(promoter_selection_layout_main);
                fadeout.prepare(promoter_selection_layout_main);
                fadeout.start();
                get_promoter_filtered(PromoterSelectionMainAdapter.selected_promoters);
            }
        });

        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        back_promoter_selection = (ImageView)findViewById(R.id.back_promoter_selection);
        layout_promoter_selection = (RelativeLayout)findViewById(R.id.layout_promoter_selection);
        fab_promoter_list = (FloatingActionButton)findViewById(R.id.promoter_list_fab);
        back_promoter_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScaleAnimation scaleAnim = new ScaleAnimation(
                        1f, 0f,
                        1f, 0f,
                        Animation.RELATIVE_TO_SELF,1.0f,
                        Animation.RELATIVE_TO_SELF , 1.0f);
                scaleAnim.setDuration(400);
                scaleAnim.setRepeatCount(0);
                scaleAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {layout_promoter_selection.setVisibility(View.GONE); }
                    @Override public void onAnimationRepeat(Animation animation) { } });
                layout_promoter_selection.startAnimation(scaleAnim);
            }
        });
        fab_promoter_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScaleAnimation scaleAnim = new ScaleAnimation(
                        0f, 1f,
                        0f, 1f,
                        Animation.RELATIVE_TO_SELF,1.0f,
                        Animation.RELATIVE_TO_SELF , 1.0f);
                scaleAnim.setDuration(400);
                scaleAnim.setRepeatCount(0);
            scaleAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation animation) { layout_promoter_selection.setVisibility(View.VISIBLE); }
                @Override public void onAnimationEnd(Animation animation) { }
                @Override public void onAnimationRepeat(Animation animation) { } });
                layout_promoter_selection.startAnimation(scaleAnim);
            }
        });



        promoter_recycler_list = (RecyclerView)findViewById(R.id.promoters_recycler_list);
        promoter_recycler_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        promoter_recycler_list.setHasFixedSize(true);
        promoter_list_adapter = new PromoterListAdapter(this,promoters_names);
        promoter_recycler_list.setAdapter(promoter_list_adapter);
        promoter_list_adapter.notifyDataSetChanged();

        promoters_recycler = (RecyclerView)findViewById(R.id.promoters_recycler);
        promoters_recycler.setHasFixedSize(true);
        LinearLayoutManagerWithSmoothScroller layoutManager = new LinearLayoutManagerWithSmoothScroller(getApplicationContext());
        promoters_recycler.setLayoutManager(layoutManager);
        promoter_recycler_adapter = new PromotersRecyclerAdapter(this,promoters);
        promoters_recycler.setAdapter(promoter_recycler_adapter);
        promoter_recycler_adapter.notifyDataSetChanged();
        get_promoters();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_promoter_sold_activity,menu);
       final View action_promoters = menu.findItem(R.id.action_promoter).getActionView();
       action_promoters.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FadeInAnimator fadein = new FadeInAnimator();
               fadein.addAnimatorListener(new Animator.AnimatorListener() {
                   @Override public void onAnimationStart(Animator animation) { promoter_selection_layout_main.setVisibility(View.VISIBLE);}
                   @Override public void onAnimationEnd(Animator animation) { }
                   @Override public void onAnimationCancel(Animator animation) {}
                   @Override public void onAnimationRepeat(Animator animation) { } });
               fadein.prepare(promoter_selection_layout_main);
               fadein.setDuration(400);
               fadein.setTarget(promoter_selection_layout_main);
               fadein.start();
               initialize_promoter_selection();
           }
       });
       final View action_profile = menu.findItem(R.id.action_profile).getActionView();
        action_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appConfig.isLogin())
                {
                    Intent intent = new Intent(SoldItemPromotersActivity.this,Final_Cart.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(SoldItemPromotersActivity.this,"Please login to proceed",Toast.LENGTH_LONG).show();
                }
            }
        });
        return true; }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch(item.getItemId())
       {
           case R.id.action_calendar:
               final Calendar c = Calendar.getInstance();
               mYear = c.get(Calendar.YEAR);
               mMonth = c.get(Calendar.MONTH);
               mDay = c.get(Calendar.DAY_OF_MONTH);

               DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.AppTheme_DialogTheme, new DatePickerDialog.OnDateSetListener() {

                           @Override
                           public void onDateSet(DatePicker view, int year,
                                                 int monthOfYear, int dayOfMonth) {

                               Log.d("Date Selected:",dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                               date = formatDate(year,monthOfYear,dayOfMonth);
                               get_promoters();
                           }
                       }, mYear, mMonth, mDay);

               datePickerDialog.show();
           break;
           case android.R.id.home:
                finish();
                overridePendingTransition(0,R.anim.slide_out_left_animation);
            break;

       }
        return true;
    }

    public void initialize_promoter_selection()
    {
       if(promoter_selection_main_names.size() == 0 )
       {
        get_promoters_from_web();
       }
    }
    public void get_promoters_from_web()
    {

      JsonArrayRequest promoters_name_request = new JsonArrayRequest(AppConfig.get_promoters, new Response.Listener<JSONArray>() {
          @Override
          public void onResponse(JSONArray response) {
            for(int i =0; i<response.length(); i++)
            {
                try {
                    JSONObject json = response.getJSONObject(i);
                    Promoter promoter = new Promoter();
                    promoter.setName(json.getString("promoter_name"));
                    promoter.setId(json.getString("promoter_id"));
                    promoter.setEmail(json.getString("promoter_email"));
                    promoter_selection_main_names.add(promoter);
                    promoter_selection_main_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

          }
      });
        promoters_name_request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(promoters_name_request);
    }
    public void get_promoters()
       {
           promoters_hash.clear();
           promoters_names.clear();
           promoters.clear();

           Log.d("Promoters request: ",AppConfig.url_get_promoters_sold_products+"sales_date="+date);
           JsonArrayRequest request = new JsonArrayRequest(AppConfig.url_get_promoters_sold_products+"sales_date="+date, new Response.Listener<JSONArray>() {
               @Override
               public void onResponse(JSONArray response) {
                    for(int i = 0 ; i<response.length();i++)
                    {
                        try {
                            JSONObject my_json_object= response.getJSONObject(i);
                            String key = my_json_object.keys().next();
                            JSONObject mobile_object = my_json_object.getJSONObject(key);
                            Product p = new Product();
                            p.set_Name(mobile_object.getString("name"));
                            p.setCustomer_name(mobile_object.getString("customer_name"));
                            p.setCustomer_contact(mobile_object.getString("customer_contact"));
                            p.setPrice_ks(mobile_object.getInt("price"));
                            p.setInvoice_status(mobile_object.getString("invoice_status"));
                            p.setOrder_status(mobile_object.getString("order_status"));
                            p.set_Stock(mobile_object.getInt("quantity"));
                            p.setOrder_type(mobile_object.getString("order_type"));
                            p.setPromoter_name(mobile_object.getString("promoter_name"));
                            p.setPromoter_id(mobile_object.getString("promoter_id"));
                            p.setPromoter_contact(mobile_object.getString("promoter_contact"));
                            if(promoters_hash.containsKey(key))
                            {
                                promoters_hash.get(key).add(p);
                            }
                            else
                            {
                                ArrayList<Product> product_list = new ArrayList<>();
                                product_list.add(p);
                                promoters_hash.put(key,product_list);
                                promoters_names.add(p.getPromoter_name());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    update_promoters(promoters_hash);
                   promoter_list_adapter.notifyDataSetChanged();
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
               Log.d("Volley Error: ","Volley error ");
               }
           });
           request.setRetryPolicy(new DefaultRetryPolicy(
                   DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                   DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                   DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

           AppController.getInstance().addToRequestQueue(request);
       }

    public void update_promoters(HashMap<String, ArrayList<Product>> promoters_hash){


        for(HashMap.Entry<String,ArrayList<Product>> entry: promoters_hash.entrySet())
        {
            Promoter promoter = new Promoter();
            promoter.name = entry.getValue().get(0).getPromoter_name();
            promoter.products = entry.getValue();
            promoters.add(promoter);
            promoter_recycler_adapter.notifyDataSetChanged();
            promoters_recycler.scrollToPosition(0);
            promoter.id = entry.getValue().get(0).getPromoter_id();
            promoter.contact = entry.getValue().get(0).getPromoter_contact();
        }
    }

    public void get_promoter_filtered(ArrayList<String> promoters_emails){
        if(promoters_emails.size()==0)
        {
            get_promoters();

        }
        else {
            Toast.makeText(getApplicationContext(), "fetching promomters with filters: " + promoters.size(), Toast.LENGTH_LONG).show();
            ArrayList<String> date_list = new ArrayList<>();
            HashMap<String, ArrayList<String>> params = new HashMap<String, ArrayList<String>>();
            params.put("promoters", promoters_emails);
            date_list.add(date);
            params.put("date", date_list);

            promoters_hash.clear();
            promoters_names.clear();
            promoters.clear();

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, AppConfig.filtered_promoters, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("PromoterActivity", "Response: " + response.toString());
                            for (int i = 0; i < PromoterSelectionMainAdapter.selected_promoters.size(); i++) {
                                try {
                                    String promoter = PromoterSelectionMainAdapter.selected_promoters.get(i);
                                    JSONArray array = response.getJSONArray(promoter);
                                    for (int j = 0; j < array.length(); j++) {
                                        JSONObject new_obj = array.getJSONObject(j);
                                        Log.d("PromoterActivity", new_obj.getString("promoter_name"));
                                        Product p = new Product();
                                        p.set_Name(new_obj.getString("name"));
                                        p.setCustomer_name(new_obj.getString("customer_name"));
                                        p.setCustomer_contact(new_obj.getString("customer_contact"));
                                        p.setPrice_ks(new_obj.getInt("price"));
                                        p.setInvoice_status(new_obj.getString("invoice_status"));
                                        p.setOrder_status(new_obj.getString("order_status"));
                                        p.set_Stock(new_obj.getInt("quantity"));
                                        p.setOrder_type(new_obj.getString("order_type"));
                                        p.setPromoter_name(new_obj.getString("promoter_name"));
                                        p.setPromoter_id(new_obj.getString("promoter_id"));
                                        p.setPromoter_contact(new_obj.getString("promoter_contact"));
                                        if (promoters_hash.containsKey(promoter)) {
                                            promoters_hash.get(promoter).add(p);
                                        } else {
                                            ArrayList<Product> product_list = new ArrayList<>();
                                            product_list.add(p);
                                            promoters_hash.put(promoter, product_list);
                                            promoters_names.add(p.getPromoter_name());

                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            update_promoters(promoters_hash);
                            promoter_list_adapter.notifyDataSetChanged();
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                    Log.d("PromoterActivity", "Error: " + error.getMessage());
                }
            });
            req.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(req);
        }
    }
    private static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(date);
    }
}
