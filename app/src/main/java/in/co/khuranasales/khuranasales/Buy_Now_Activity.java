package in.co.khuranasales.khuranasales;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Buy_Now_Activity extends AppCompatActivity {
    public static int applicable_discount = 0;
    public static RecyclerView.Adapter adapter1;
    public static RecyclerView.Adapter adapter2;
    public RecyclerView.LayoutManager layoutManager;
    public RecyclerView recycler_product;
    public static Context mcontext;
    public HashMap<Integer , Boolean> hash_status_confirm_batch = new HashMap<>();
    public  static AppConfig appConfig;
    public RelativeLayout discount_layout;
    public int product_clicked_position = 0;
    public ImageView back_discount_selection;
    public Button apply_discount_succesfully;
    public RecyclerView coupons_recycler_view ;
    public Button submit_details;
    public ScrollView main_scroll_view;
    public RelativeLayout no_cart ;
    public ImageView back_customer_entry;
    public RelativeLayout customer_info_entry;
    public static Button apply_discount;
    public static TextView text1;
    public static TextView text2;
    public static TextView text3;
    public  FadeOutAnimator animator = new FadeOutAnimator();
    public  FadeInAnimator in_animator = new FadeInAnimator();
    public static DatabaseHandler db;
    public static TextView text4;
    public int total_count_to_update;
    public int count_updated ;
    public static TextView total_discount_view;
    public List<Product> products= new ArrayList<>();
    public List<Coupon> coupons = new ArrayList<>();
    public static Button order;
    public Button continue_shopping;

    public Button batch_selected_button;
    public RelativeLayout batch_selection_layout;
    public RecyclerView batch_selection_recycler;
    public ImageView back_batch_selection;
    public Batch_selection_adapter batch_selection_adapter;
    public TextView batch_selection_error_text;
    public ArrayList<Batch> batch_numbers = new ArrayList<>();

    public   TextView customer_address;

    public RelativeLayout new_address_layout;
    public TextView open_new_address_form;
    public ImageView back_address_entry;
    public TextView address_form_title_label;
    public Button address_entry_done ;
    AutoCompleteTextView address_name ;
    AutoCompleteTextView address_area ;
    AutoCompleteTextView address_city ;
    AutoCompleteTextView address_state ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_now_activity);
        address_city = (AutoCompleteTextView) findViewById(R.id.city_address);
        address_city.setText("Pune");
        address_state = (AutoCompleteTextView) findViewById(R.id.state_address);
        address_state.setText("Maharashtra");
        address_area = (AutoCompleteTextView) findViewById(R.id.pincode_address);
        address_name = (AutoCompleteTextView) findViewById(R.id.name_address);
        open_new_address_form = (TextView)findViewById(R.id.open_new_address_form);
        new_address_layout = (RelativeLayout)findViewById(R.id.new_address_layout);
        back_address_entry = (ImageView)findViewById(R.id.back_address_entry);
        address_form_title_label = (TextView)findViewById(R.id.title_lable);
        address_entry_done = (Button)findViewById(R.id.address_entry_done);
        customer_address = (TextView)findViewById(R.id.customer_address);

        address_entry_done.setMaxWidth(55);
        address_entry_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean compulsary  = false;
                if(address_city.getText().toString().equals(""))
                {
                    compulsary = true;
                    address_city.setError("city is required");
                }
                if(address_state.getText().toString().equals(""))
                {
                    compulsary = true;
                    address_state.setError("state is required");
                }
                if(address_area.getText().toString().equals(""))
                {
                    compulsary = true;
                    address_area.setError("area |  pincode required");
                }
                if(address_name.getText().toString().equals(""))
                {
                    compulsary = true;
                    address_name.setError("Build|Office name requried");
                }
                if(compulsary == false)
                {
                    StringBuilder builder =new StringBuilder("");
                    builder.append(address_name.getText().toString()+"-");
                    builder.append(address_area.getText().toString()+"-");
                    builder.append(address_city.getText().toString()+"-");
                    builder.append(address_state.getText().toString());
                    customer_address.setText(""+builder.toString());
                    open_new_address_form.setText("Edit new address");
                    fadeOut(new_address_layout);
                }
            }
        });
        back_address_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(new_address_layout);
            }
        });
        open_new_address_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn(new_address_layout);
            }
        });

        batch_selected_button = (Button)findViewById(R.id.batch_selected);
        batch_selection_layout = (RelativeLayout)findViewById(R.id.batch_recycler_layout);
        batch_selection_recycler = (RecyclerView)findViewById(R.id.batch_recycler);
        back_batch_selection = (ImageView)findViewById(R.id.back_batch_selection);
        batch_selection_error_text = (TextView)findViewById(R.id.batch_selection_error);

        batch_selected_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(products.get(product_clicked_position).selected_batch_numbers.size() > products.get(product_clicked_position).get_Stock())
                {
                    batch_selection_error_text.setText("Cannot select more than product count in cart");
                }
                else if(products.get(product_clicked_position).selected_batch_numbers.size() < products.get(product_clicked_position).get_Stock())
                {
                    batch_selection_error_text.setText("Please select "+products.get(product_clicked_position).get_Stock()+" batch numbers");
                }
                else
                {
                    batch_selection_error_text.setText("");
                    Toast.makeText(getApplicationContext(),"Selected Batch success",Toast.LENGTH_LONG).show();
                    fadeOut(batch_selection_layout);
                    upload_batch_numbers();
                    hash_status_confirm_batch.put(product_clicked_position,true);
                }
            }
        });
        recyclerViewItemClickListener batch_selection_listener = (view, position) -> {
            hash_status_confirm_batch.remove(product_clicked_position);
            boolean present = false;
            for(int i =0 ; i < products.get(product_clicked_position).selected_batch_numbers.size() ; i ++)
            {
                if(products.get(product_clicked_position).selected_batch_numbers.get(i).getNumber().equals(batch_selection_adapter.getBatches().get(position).getNumber()))
                {
                    products.get(product_clicked_position).remove_batch_number(batch_selection_adapter.getBatches().get(position));
                    present = true;
                }
            }
            if(present == false)
            {
                products.get(product_clicked_position).addSelected_batch_numbers(batch_selection_adapter.getBatches().get(position));
            }
            Log.d("BuyNowActivity",products.get(product_clicked_position).get_Name());
            for(int i = 0 ; i <products.get(product_clicked_position).getSelected_batch_numbers().size();i++)
            {
                Log.d("BuyNowActivity",products.get(product_clicked_position).getSelected_batch_numbers().get(i).getNumber());
            }
            batch_selection_adapter.notifyItemChanged(position);
        };

        recyclerViewItemClickListener batch_selection_open_listener = (view, position) -> {
            // fadeIn(batch_selection_layout);
            fadeIn(batch_selection_layout);
            product_clicked_position = position;
            batch_numbers = products.get(position).getBatchNumbers();
            batch_selection_adapter.setBatches(batch_numbers);
            batch_selection_adapter.selected_batches.clear();
            batch_selection_adapter.selected_batches.addAll(products.get(position).selected_batch_numbers);
            for(int i  = 0 ; i < batch_selection_adapter.selected_batches.size(); i++)
            {
                Log.d("BuyNow printing selec: ",batch_selection_adapter.selected_batches.get(i).getNumber());
                Log.d("BuyNow printing in: ",batch_selection_adapter.getBatches().get(i).getNumber());
            }
            batch_selection_adapter.notifyDataSetChanged();
        };

        batch_selection_recycler.setHasFixedSize(true);
        batch_selection_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        batch_selection_adapter = new Batch_selection_adapter(this,batch_numbers,batch_selection_listener);
        batch_selection_recycler.setAdapter(batch_selection_adapter);
        batch_selection_adapter.notifyDataSetChanged();
        back_batch_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(batch_selection_layout);
            }
        });


        appConfig = new AppConfig(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mcontext = this;
        total_discount_view = (TextView)findViewById(R.id.total_discount_view);
        back_customer_entry = (ImageView)findViewById(R.id.back_customer_entry);
        discount_layout = (RelativeLayout)findViewById(R.id.discount_layout);
        back_discount_selection = (ImageView)findViewById(R.id.back_dicount_selection);
        customer_info_entry = (RelativeLayout)findViewById(R.id.customer_information_form);
        submit_details = (Button)findViewById(R.id.submit_details);
        apply_discount_succesfully = (Button)findViewById(R.id.apply_discount_successfully);
        continue_shopping = (Button)findViewById(R.id.continue_shopping);
        continue_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,R.anim.slide_out_left_animation);
            }
        });
        back_customer_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeOut(customer_info_entry);
                           }
        });
        apply_discount_succesfully.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeOut(discount_layout);
                int discount_amount = 0;
                Coupon[] coupon_array = new Coupon[coupons.size()];
                int k = 0;
                for(Coupon c : coupons) {
                    discount_amount += c.coupon_amount * c.count;
                    coupon_array[k] = c;
                    k++;
                }
                text2.setText("Discount Amount:  "+discount_amount+" /-");
                applicable_discount = discount_amount;
                update_price_card(products,coupon_array);
            }
        });
        no_cart = (RelativeLayout)findViewById(R.id.no_cart);
        main_scroll_view =(ScrollView)findViewById(R.id.scrollView);
        check_products_availability();
        back_discount_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeOut(discount_layout);
            }
        });
        apply_discount = (Button)findViewById(R.id.apply_discount);
        if(appConfig.getUserType().equals("Admin"))
        {
            apply_discount.setVisibility(View.VISIBLE);
        }
        else
        {
            apply_discount.setVisibility(View.INVISIBLE);
        }
        apply_discount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               fadeIn(discount_layout);
            }
        });

        if(appConfig.getUserType().equals("Dealer"))
        {
           apply_discount.setVisibility(View.GONE);
        }
        submit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int total_batch_numbers = 0;
                int total_products = 0 ;
                for(int i = 0 ; i < products.size();i++)
                {
                    total_batch_numbers = total_batch_numbers + products.get(i).getSelected_batch_numbers().size();
                    total_products = total_products + products.get(i).get_Stock();
                }
                if(hash_status_confirm_batch.size() != products.size())
                {
                    Toast.makeText(getApplicationContext(),"Sorry please upload all batch details",Toast.LENGTH_LONG).show();
                    return;
                }
                if(total_batch_numbers == total_products)
                {
                    Boolean customer_entry_vacant = false;
                    TextView customer_name = (TextView)findViewById(R.id.customer_name);
                    TextView customer_phone = (TextView)findViewById(R.id.customer_phone);
                    TextView customer_email = (TextView)findViewById(R.id.customer_email);
                    TextView customer_gst = (TextView)findViewById(R.id.customer_gst);
                    if(customer_name.getText().toString().equals(""))
                    {
                        customer_name.setError("Please enter name ");
                        customer_entry_vacant = true;
                    }
                    if(customer_phone.getText().toString().equals(""))
                    {
                        customer_phone.setError("Please enter phone number");
                        customer_entry_vacant = true;
                    }
                    if(customer_email.getText().toString().equals(""))
                    {
                        customer_email.setError("Please enter email id");
                        customer_entry_vacant = true;
                    }
                    if(customer_address.getText().toString().equals(""))
                    {
                        customer_address.setError("Please enter delivery address");
                        customer_entry_vacant = true;
                    }
                    if(customer_gst.getText().toString().equals(""))
                    {
                        customer_gst.setText(" xxxxxx");
                    }
                    if(customer_entry_vacant){
                        return;
                    }
                    String data[] = new String[6];
                    data[0] = customer_email.getText().toString();
                    data[1] = appConfig.getUser_email();
                    data[2] = customer_phone.getText().toString();
                    data[3] = customer_address.getText().toString();
                    data[4] = customer_gst.getText().toString();
                    data[5] = customer_name.getText().toString();

                    new UpdateCustomer().execute(data);

                    animator.setTarget(customer_info_entry);
                    animator.prepare(customer_info_entry);
                    animator.addAnimatorListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {}
                        @Override
                        public void onAnimationEnd(Animator animator) {
                            customer_info_entry.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(Buy_Now_Activity.this,Buy_final_activity.class);
                            Log.i("Buy_Now", "onResponse: "+data[5]);
                            intent.putExtra("customer_email", data[0]);
                            intent.putExtra("promoter_email", data[1]);
                            intent.putExtra("customer_phone",data[2]);
                            intent.putExtra("customer_address",data[3]);
                            intent.putExtra("customer_gst", data[4]);
                            intent.putExtra("customer_name",data[5]);
                            intent.putExtra("discount_applicable",applicable_discount);
                            startActivity(intent);
                        }
                        @Override
                        public void onAnimationCancel(Animator animator) {}
                        @Override
                        public void onAnimationRepeat(Animator animator) {}
                    });
                    animator.start();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please Select appropriate batch numbers",Toast.LENGTH_LONG).show();
                }
            }
        });
        coupons_recycler_view = (RecyclerView)findViewById(R.id.recycler_view_coupons);
        coupons_recycler_view.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        coupons_recycler_view.setLayoutManager(layoutManager);
        adapter2 = new coupon_recycler(this,coupons);
        coupons_recycler_view.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
        new CouponsLoad().execute();
        recycler_product = (RecyclerView)findViewById(R.id.recyclerView);
        recycler_product.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_product.setLayoutManager(layoutManager);

        db = new DatabaseHandler(getApplicationContext());
        Toast.makeText(getApplicationContext(),"Loading list From Database", Toast.LENGTH_LONG).show();
        if(appConfig.isLogin())
        {
         getAllProducts();
        }else {
         products = db.getAllProducts();
        }
        adapter1 = new RecyclerAdapter(this,products,batch_selection_open_listener);
        recycler_product.setAdapter(adapter1);
        order=(Button)findViewById(R.id.order);
        if(appConfig.getUserType().equals("Admin") || appConfig.getUserType().equals("Promoter"))
        {
            order.setText("Sell Product");
        }
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);
        text4=(TextView)findViewById(R.id.text4);
        order.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    if(db.getAllProducts().size() > 0) {
                        if(appConfig.isLogin() == false)
                        {

                            Intent intent = new Intent(Buy_Now_Activity.this,Activity_Login.class);
                            intent.putExtra("Activity","BuyNow");
                            startActivityForResult(intent,250,null);
                        }
                         else
                         {
                            Toast.makeText(getApplicationContext(),"i am here",Toast.LENGTH_LONG).show();
                            send_data(db.getAllProducts());
                            fadeIn(customer_info_entry);
                         }
                }
                else{
                    if(appConfig.isLogin() == false)
                    {
                        Toast.makeText(getApplicationContext(),"No Products In Cart", Toast.LENGTH_LONG);
                    }
                    else
                    {
                     if(products.size() > 0)
                     {
                         YoYo.with(Techniques.FadeIn).onStart(new YoYo.AnimatorCallback() {
                             @Override
                             public void call(Animator animator) {
                                 customer_info_entry.setVisibility(View.VISIBLE);
                             }
                         }).duration(400).playOn(customer_info_entry);
                     }
                     else
                     {
                         Toast.makeText(getApplicationContext(),"No Products In Cart", Toast.LENGTH_LONG);
                     }
                    }
                }

            }
        });
    }

    class UpdateCustomer extends AsyncTask<String,Void,Void>
    {

        @Override
        protected Void doInBackground(final String... data) {
            StringRequest update_customer_request = new StringRequest(Request.Method.POST,AppConfig.url_customer_info_update, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response--------------:",""+response);
                    try{
                        if(response.equals("successfull"))
                        {
                            Toast.makeText(getApplicationContext(),"Successfully updated customer",Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Could not update customer please retry",Toast.LENGTH_LONG).show();
                        }
                    }catch(Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection Error...",Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                Log.d("Volley Error",""+error.getMessage());
                Toast.makeText(getApplicationContext(),"Error Found In Request",Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("customer_email", data[0]);
                    params.put("promoter_email", data[1]);
                    params.put("customer_phone",data[2]);
                    params.put("customer_address",data[3]);
                    params.put("customer_gst", data[4]);
                    params.put("customer_name",data[5]);
                    return params;
                }
            };
            update_customer_request.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(update_customer_request);
            return null;
        }
    }
    class LoadOutbox extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)  {
              JsonArrayRequest movieReq = new JsonArrayRequest(AppConfig.url_get_viewed,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("True Response", response.toString());
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Product pro = new Product();
                                    pro.set_Name(obj.getString("Name"));
                                    if(!(obj.getString("stock").equals(null)||obj.getString("stock")==null))
                                    {
                                        pro.set_Stock(Integer.parseInt(obj.getString("stock")));
                                    }
                                    if(!(obj.getString("mrp").equals(null)||obj.getString("mrp")==null))
                                    {
                                        pro.setPrice_mrp(Integer.parseInt(obj.getString("mrp")));
                                    }
                                    if(!(obj.getString("mop").equals(null)||obj.getString("mop")==null))
                                    {
                                        pro.setPrice_mop(Integer.parseInt(obj.getString("mop")));
                                    }
                                    if(!(obj.getString("ksprice").equals(null)||obj.getString("ksprice")==null))
                                    {
                                        pro.setPrice_ks(Integer.parseInt(obj.getString("ksprice")));
                                    }
                                    pro.set_link(obj.getString("links"));
                                    products.add(pro);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("VOLLEY ERROR:-", "Error: " + error.getMessage());
                        }

                    });
            movieReq.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(movieReq);
            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute() {
            // dismiss the dialog after getting all products


        }
    }

    class CouponsLoad extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)  {
            JsonArrayRequest movieReq = new JsonArrayRequest(AppConfig.url_load_coupons,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("True Response", response.toString());
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Coupon c1 = new Coupon();
                                    c1.setCoupon_id(obj.getInt("coupon_id"));
                                    c1.setCoupon_amount(obj.getInt("coupon_amount"));
                                    c1.setCoupon_type(obj.getString("coupon_type"));
                                    c1.setCoupon_url(obj.getString("coupon_url"));
                                    coupons.add(c1);
                                    adapter2.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("VOLLEY ERROR:-", "Error: " + error.getMessage());
                        }

                    });
            movieReq.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(movieReq);
            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute() {
            // dismiss the dialog after getting all products


        }
    }
    public void check_products_availability()
    {
        if(appConfig.isLogin())
        {
            JsonArrayRequest product_count_request = new JsonArrayRequest((AppConfig.url_load_count + appConfig.getUser_email()), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try{
                        JSONObject obj = response.getJSONObject(0);
                    if(obj.getInt("total") > 0)
                    {
                    main_scroll_view.setVisibility(View.VISIBLE);
                    no_cart.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        main_scroll_view.setVisibility(View.INVISIBLE);
                        no_cart.setVisibility(View.VISIBLE);
                    }
                    }catch(Exception e)
                    {
                        main_scroll_view.setVisibility(View.INVISIBLE);
                        no_cart.setVisibility(View.VISIBLE);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Volley Error: ",""+error.getMessage());
                }
            });
            product_count_request.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(product_count_request);
        }
        else
        {
            db = new DatabaseHandler(getApplicationContext());
            if(db.getAllProducts().size() == 0)
            {
                main_scroll_view.setVisibility(View.INVISIBLE);
                no_cart.setVisibility(View.VISIBLE);
            }
        }
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
                    Intent intent = new Intent(Buy_Now_Activity.this,Final_Cart.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Buy_Now_Activity.this,"Please login to proceed",Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart= menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Buy_Now_Activity.this,Buy_Now_Activity.class);
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
                Toast.makeText(Buy_Now_Activity.this,"You are already logged in",Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent = new Intent(Buy_Now_Activity.this,Activity_Login.class);
                startActivity(intent);
            }
        }else if(id == R.id.signout)
        {
            if(appConfig.isLogin())
            {
                Intent intent = new Intent(Buy_Now_Activity.this,Activity_Login.class);
                startActivity(intent);

            }else{
                Toast.makeText(Buy_Now_Activity.this," Please login to signout",Toast.LENGTH_LONG).show();

            }
        }else if(id == R.id.signup)
        {
            Intent intent = new Intent(Buy_Now_Activity.this,MainActivity.class);
            startActivity(intent);
        }
        else if(id == android.R.id.home)
        {
            finish();
            overridePendingTransition(0,R.anim.slide_out_left_animation);
        }
        return super.onOptionsItemSelected(item);
    }
    public void getAllProducts()
    {
        Toast.makeText(getApplicationContext(),"Loading list Online", Toast.LENGTH_LONG).show();
        Log.d("url",""+AppConfig.url_get_viewed+appConfig.getUser_email());
        JsonArrayRequest movieReq = new JsonArrayRequest(AppConfig.url_get_viewed+appConfig.getUser_email(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Log.d("True Response", response.toString());
                                JSONObject obj = response.getJSONObject(i);
                                Product pro = new Product();
                                pro.setProduct_id(obj.getInt("product_id"));
                                pro.set_Name(obj.getString("name"));
                                pro.setTax(obj.getInt("tax"));
                                if(!(obj.getString("stock").equals(null)||obj.getString("stock")==null))
                                {
                                    pro.set_Stock(Integer.parseInt(obj.getString("stock")));
                                }
                                if(!(obj.getString("MRP").equals(null)||obj.getString("MRP")==null))
                                {
                                    pro.setPrice_mrp(Integer.parseInt(obj.getString("MRP")));
                                }
                                if(!(obj.getString("MOP").equals(null)||obj.getString("MOP")==null))
                                {
                                    pro.setPrice_mop(Integer.parseInt(obj.getString("MOP")));
                                }
                                if(!(obj.getString("ksprice").equals(null)||obj.getString("ksprice")==null))
                                {
                                    pro.setPrice_ks(Integer.parseInt(obj.getString("ksprice")));
                                }
                                pro.set_link(obj.getString("links"));
                                String [] selected_batch_numbers = new String[response.length()];
                                if(!(obj.getString("batch_selected").equals("") || obj.getString("batch_selected").equals(null)))
                                {
                                    selected_batch_numbers = obj.getString("batch_selected").split(",");

                                }
                                else
                                {
                                   selected_batch_numbers = new String[0];
                                }
                                JSONArray array = obj.getJSONArray("batch_details");
                                if(array.length() != 0) {
                                    JSONObject batch_details = array.getJSONObject(0);
                                    for(int j = 0 ; j < batch_details.names().length(); j++)
                                    {
                                    if(!(batch_details.names().getString(j).equals("batch_id") || batch_details.names().getString(j).equals("product_id")))
                                    {
                                        if(!batch_details.getString(batch_details.names().getString(j)).equals("null"))
                                        {
                                        String batches[] = batch_details.getString(batch_details.names().getString(j)).split(",");
                                        for(int k = 0; k <batches.length;k ++)
                                        {
                                            Batch b1 = new Batch(batch_details.names().getString(j),batches[k]);
                                            boolean selected = false;
                                            for(int l = 0 ; l < selected_batch_numbers.length;l++)
                                            {
                                                if(b1.getNumber().equals(selected_batch_numbers[l]))
                                                {
                                                    selected = true;
                                                    pro.selected_batch_numbers.add(b1);
                                                    pro.batch_numbers.add(b1);
                                                }
                                            }
                                            if(selected == false)
                                            {
                                                pro.addBatchNumbers(b1);
                                            }
                                        }
                                        }

                                    }
                                    }
                                }
                                if(pro.getSelected_batch_numbers().size() == pro.get_Stock())
                                {
                                    hash_status_confirm_batch.put(i,true);
                                }
                                products.add(pro);
                           } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                       adapter1.notifyDataSetChanged();
                      }
                 },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("VOLLEY ERROR:-", "Error: " + error.getMessage());
                             }

                });
        movieReq.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(movieReq);
    }
    public void send_offline_products_stored_online(Product product)
    {
        String url = AppConfig.url_set_viewed+"?customer_email="+appConfig.getUser_email()+"&id="+product.getProduct_id()+"&count="+product.get_Stock();
        Log.d("url",""+url);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Error: ", "Register Response: " + response.toString());
                try {
                    JSONObject obj = response.getJSONObject(0);
                    String msg=obj.getString("success");
                    if (msg.equals("true"))
                    {
                        Log.d("Product","Successfully sent to online database");
                        call_to_update_show_list();
                    }
                    else {
                        Log.d("Product","unable to send to online database");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Product Send Error: " + error.getMessage());
            }
        });
        arrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(arrayRequest);
    };
    public static void update_price_card(List<Product> products,Coupon... coupons)
    {
        Log.d("Reading: ", "Reading all products..");
        double ks_price_sum = 0;
        double mop_price_sum = 0;
        double amount=0.0;
        double tax = 0.0;
        double discount_amount = 0.0;
        for(Coupon c : coupons)
        {
            discount_amount = discount_amount + c.getCount()*c.getCoupon_amount();
        }
        text2.setText("Discount Amount:  "+discount_amount+" /-");
        for (int x=0;x<products.size();x++) {
            Product product = products.get(x);
            ks_price_sum = product.getPrice_ks() * product.get_Stock();
            mop_price_sum = product.getPrice_mop() * product.get_Stock();
            double init_tax = (product.getPrice_ks()*product.get_Stock()) - (product.getPrice_ks()*product.get_Stock())/(1.12);
            if(appConfig.getUserType().equals("Admin") || appConfig.getUserType().equals("Dealer"))
            {
                amount = amount + (product.getPrice_ks() * product.get_Stock()) - init_tax;
            }
            else
            {
                amount = amount + (product.getPrice_mop() * product.get_Stock()) - init_tax;

            }
            tax = tax + init_tax;
        }

        text1.setText("Payment:  " + Math.round(amount) + " /-");
        text3.setText("Payable Tax:  "+Math.round(tax));
        text4.setText("Total Pay Amount  "+(Math.round(amount+tax)-applicable_discount)+" /-");
        if((mop_price_sum - ks_price_sum) < discount_amount)
        {
            discount_amount = 0;
            applicable_discount = 0;
            text2.setTextColor(mcontext.getResources().getColor(R.color.red));
            text2.setText("Discount amount can not be less than "+(mop_price_sum - ks_price_sum));
        }
        else
        {
            text2.setTextColor(mcontext.getResources().getColor(R.color.black));
        }
    }

    public void send_data(List<Product> products)
    {
        total_count_to_update = products.size();
        List<Product> products_new = db.getAllProducts();
        for(Product product : products)
        {
            send_offline_products_stored_online(product);
            db.delete_entry(product.get_Name());
        }
    }

    public void call_to_update_show_list()
    {
        ++count_updated;
        if(count_updated == total_count_to_update)
        {
            getAllProducts();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == 250 )
      {
        if(resultCode == 200)
          {
            Toast.makeText(getApplicationContext(),"Switching to Final Shopping Pack",Toast.LENGTH_LONG);
            DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
            send_data(handler.getAllProducts());
            products.clear();
          }
      }
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

    public void upload_batch_numbers()
    {
        HashMap<String,String> params = new HashMap<>();
            Log.d("BuyNow",products.get(product_clicked_position).get_batch_numbers_filtered("selected"));
            Log.d("BuyNow",""+products.get(product_clicked_position).get_batch_numbers_filtered("filtered"));
            params.put("product_name",""+products.get(product_clicked_position).get_Name());
            params.put("selected_batch",products.get(product_clicked_position).get_batch_numbers_filtered("selected"));
            params.put("email",appConfig.getUser_email());
        JSONObject params_json = new JSONObject(params);
        JsonObjectRequest upload_batch_numbers_request = new JsonObjectRequest(Request.Method.POST,AppConfig.upload_batches,params_json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("BuyNow","response: "+response.toString());
                    if(response.getString("success").equals("true"))
                    {
                        Toast.makeText(getApplicationContext(),"Successfully updated batch numbers",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Could not update batch numbers",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BuyNow Volley Error: ","Error in upload batch request"+error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(upload_batch_numbers_request);
    }
}
