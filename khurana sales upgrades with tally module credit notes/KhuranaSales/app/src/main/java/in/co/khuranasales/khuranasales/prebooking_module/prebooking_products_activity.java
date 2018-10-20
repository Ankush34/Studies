package in.co.khuranasales.khuranasales.prebooking_module;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.Buy_Now_Activity;
import in.co.khuranasales.khuranasales.Final_Cart;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.add_new_product.search_ledger_adapter;
import in.co.khuranasales.khuranasales.ledger.Ledger;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;


public class prebooking_products_activity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView nothing_found_image;
    private RecyclerView prebooking_products_Recycler;
    private prebooking_products_adapter products_prebooked_adapter;
    private AppConfig appConfig;
    private ArrayList<PrebookingProduct> products_of_prebooking = new ArrayList<>();
    private RelativeLayout add_prebooking_product_layout;
    private ImageView back_add_new_product;
    private Button create_button;
    private TextView expected_date_text;
    private EditText expected_price_text;
    private EditText product_name_text;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String date;

    private RelativeLayout edit_prebooking_product_layout;
    private ImageView back_edit_product;
    private Button edit_button;
    private TextView edit_expected_date_text;
    private EditText edit_expected_price_text;
    private EditText edit_product_name_text;
    private int selected_index_for_udpate = -1;
    private int selected_index_for_delete = -1;

    private RelativeLayout add_new_prebooking_customer_layout;
    private EditText customer_name_search_Text;
    private EditText customer_mobile_number_text;
    private EditText customer_address_text;
    private EditText customer_otp_text;
    private TextView send_otp;
    private TextView verify_otp;
    private Button confirm_booking;
    private EditText customer_booking_amount_text;
    private ImageView back_add_prebooking_customer;
    private CardView ledger_search_recycler_layout;
    private RecyclerView search_ledger_recycler;
    private search_ledger_adapter search_ledger_recycler_adapter;
    private String search_string;
    private ArrayList<Ledger> ledgers = new ArrayList<>();
    private boolean selected_ledger = false;

    private RelativeLayout customer_remainig_time_layout;
    private TextView otp_remaining_time;
    private int otp_number_sent = 0;
    public boolean otp_verified = false;
    public boolean otp_expired = false;

    public RelativeLayout view_pre_bookings_layout;
    public RecyclerView view_pre_bookings_recycler;
    public prebooked_bookings_view_adapter prebookings_view_adapter;
    public ArrayList<PrebookingLedger> prebooked_ledgers = new ArrayList<>();
    public RelativeLayout back_view_prebooked_customers;
    public int selected_prebooking_product_id_for_view = -1;
    public int position_selected_for_cancelling = -1;
    public int position_selected_for_sending_sms = -1;
    // this index is for maintaining the details if the user clicks on the same index and set details clear if the index has been changed
    public int  previously_opened_index = -1;
    public int previously_selected_id_for_viewing_ledgers = -1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prebooking_products_activity_layout);
        appConfig = new AppConfig(getApplicationContext());

        back_view_prebooked_customers = (RelativeLayout) findViewById(R.id.back_view_prebooked_customers);
        back_view_prebooked_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade_out(view_pre_bookings_layout);
            }
        });
        view_pre_bookings_layout = (RelativeLayout)findViewById(R.id.view_pre_bookings_layout);
        view_pre_bookings_recycler = (RecyclerView)findViewById(R.id.my_prebooked_customers_recycler);
        view_pre_bookings_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        view_pre_bookings_recycler.setHasFixedSize(true);
        recyclerViewItemClickListener cancel_booking_listener = (view, position)-> {
            position_selected_for_cancelling = position;
            if(prebooked_ledgers.get(position_selected_for_cancelling).getBooking_status().equals("BOOKED"))
            {
                new cancel_booking().execute();
            }
            else
            {
              new re_confirm_booking().execute();
            }
        };
        recyclerViewItemClickListener send_arrival_sms_listener = (view, position)-> {
            position_selected_for_sending_sms = position;
            new send_arrival_sms().execute();
        };
        prebookings_view_adapter = new prebooked_bookings_view_adapter(this,prebooked_ledgers,cancel_booking_listener, send_arrival_sms_listener);
        view_pre_bookings_recycler.setAdapter(prebookings_view_adapter);
        prebookings_view_adapter.notifyDataSetChanged();

        customer_remainig_time_layout = (RelativeLayout)findViewById(R.id.customer_otp_remaining_time_layout);
        customer_remainig_time_layout.setVisibility(View.INVISIBLE);
        otp_remaining_time = (TextView)findViewById(R.id.otp_remaining_time);

        add_new_prebooking_customer_layout = (RelativeLayout)findViewById(R.id.add_new_prebooking_customer_layout);
        back_add_prebooking_customer = (ImageView)findViewById(R.id.back_add_new_prebooking_customer);
        back_add_prebooking_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade_out(add_new_prebooking_customer_layout);
            }
        });
        ledger_search_recycler_layout = (CardView)findViewById(R.id.ledger_search_recycler_layout);
        search_ledger_recycler = (RecyclerView)findViewById(R.id.ledger_search_recycler);
        recyclerViewItemClickListener ledger_selected_listener = (view, position)->{
           Toast.makeText(getApplicationContext(),"Ledger selected",Toast.LENGTH_SHORT).show();
           selected_ledger = true;
           customer_name_search_Text.setText(ledgers.get(position).getName());
           customer_address_text.setText(ledgers.get(position).getAddress());
           if(!ledgers.get(position).getMobile().equals(""))
           {
               customer_mobile_number_text.setText(ledgers.get(position).getMobile());
           }
           else if(!ledgers.get(position).getPhone().equals(""))
           {
               customer_mobile_number_text.setText(ledgers.get(position).getPhone());
           }
           fade_out(ledger_search_recycler_layout);
        };
        search_ledger_recycler_adapter = new search_ledger_adapter(ledgers,ledger_selected_listener);
        search_ledger_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        search_ledger_recycler.setHasFixedSize(true);
        search_ledger_recycler.setAdapter(search_ledger_recycler_adapter);
        search_ledger_recycler_adapter.notifyDataSetChanged();

        customer_name_search_Text = (EditText)findViewById(R.id.customer_name_search_text);
        customer_mobile_number_text = (EditText)findViewById(R.id.customer_mobile_text);
        customer_mobile_number_text.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(otp_verified)
               {
                   otp_verified = false;
                   otp_number_sent = -1;
                   fade_in(send_otp);
                   fade_in(verify_otp);
               }
            }
            @Override public void afterTextChanged(Editable s) { }
        });
        customer_address_text = (EditText)findViewById(R.id.customer_address_text);
        customer_otp_text = (EditText)findViewById(R.id.customer_otp_text);
        customer_booking_amount_text  =(EditText)findViewById(R.id.booking_amount_text);
        send_otp = (TextView)findViewById(R.id.send_otp);
        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new send_otp().execute();
            }
        });
        verify_otp = (TextView)findViewById(R.id.verify_otp);
        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!otp_expired && customer_otp_text.getText().toString().equals(""+otp_number_sent))
                {
                    Toast.makeText(getApplicationContext(),"OTP Verified",Toast.LENGTH_SHORT).show();
                    if(send_otp.getVisibility() == View.VISIBLE)
                    {
                        fade_out(send_otp);
                    }
                    fade_out(verify_otp);
                    fade_out(customer_remainig_time_layout);
                    otp_verified = true;
                    otp_number_sent = -1;
                }
                else if(otp_expired)
                {
                    otp_number_sent = -1;
                    Toast.makeText(getApplicationContext(),"Sorry OTP has expired",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Sorry OTP Doesn't Match",Toast.LENGTH_SHORT).show();
                }
            }
        });
        confirm_booking = (Button)findViewById(R.id.confirm_booking);
        confirm_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otp_verified)
                {
                    Toast.makeText(getApplicationContext(),"Please Wait Confirming Booking",Toast.LENGTH_SHORT).show();
                    new send_confirm_bookings_sms().execute();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Sorry OTP not verified yet",Toast.LENGTH_SHORT).show();
                }
            }
        });
        customer_name_search_Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(""))
                {
                    search_ledgers(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        edit_prebooking_product_layout = (RelativeLayout)findViewById(R.id.edit_pre_booking_product_layout);
        back_edit_product = (ImageView)findViewById(R.id.back_edit_product);
        edit_button = (Button)findViewById(R.id.edit_button);
        edit_expected_date_text = (TextView)findViewById(R.id.edit_expected_date_text);
        edit_expected_date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(prebooking_products_activity.this,R.style.AppTheme_DialogTheme, new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Log.d("Date Selected:",dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        date = formatDate(year,monthOfYear,dayOfMonth);
                        edit_expected_date_text.setText(date);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });
        edit_expected_price_text = (EditText)findViewById(R.id.edit_expected_price_text);
        edit_product_name_text = (EditText)findViewById(R.id.edit_product_name_text);
        back_edit_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade_out(edit_prebooking_product_layout);
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new update_product_online().execute();
            }
        });

        product_name_text = (EditText)findViewById(R.id.product_name_text);
        expected_date_text = (TextView)findViewById(R.id.expected_date_text);
        expected_price_text = (EditText)findViewById(R.id.expected_price_text);
        expected_date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(prebooking_products_activity.this,R.style.AppTheme_DialogTheme, new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Log.d("Date Selected:",dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        date = formatDate(year,monthOfYear,dayOfMonth);
                        expected_date_text.setText(date);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });
        create_button = (Button)findViewById(R.id.create_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new add_product_online().execute();
            }
        });

        add_prebooking_product_layout = (RelativeLayout)findViewById(R.id.add_new_pre_booking_product_layout);
        add_prebooking_product_layout.setVisibility(View.INVISIBLE);
        back_add_new_product = (ImageView)findViewById(R.id.back_add_new_product);
        back_add_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade_out(add_prebooking_product_layout);
            }
        });
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appConfig = new AppConfig(getApplicationContext());
        prebooking_products_Recycler = (RecyclerView)findViewById(R.id.prebooking_products_recycler);
        prebooking_products_Recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        prebooking_products_Recycler.setHasFixedSize(true);

        recyclerViewItemClickListener listener_edit = (view,position)->{
           PrebookingProduct pro  = products_of_prebooking.get(position);
           edit_product_name_text.setText(pro.getName());
           edit_expected_price_text.setText(pro.getExpected_price());
           edit_expected_date_text.setText(pro.getExpected_date());
           selected_index_for_udpate = position;
           fade_in(edit_prebooking_product_layout);
        };

        recyclerViewItemClickListener listener_delete = (view,position)->{
            if(appConfig.getUserType().equals("Admin"))
            {
                selected_index_for_delete = position;
                new delete_product_online().execute();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Sorry Admin has permission to delete",Toast.LENGTH_SHORT).show();
            }
        };

        recyclerViewItemClickListener listener_add_booking = (view,position)->{
            send_otp.setText("Send OTP");
            if(position != previously_opened_index)
            {
                customer_mobile_number_text.setText("");
                customer_name_search_Text.setText("");
                customer_address_text.setText("");
                customer_booking_amount_text.setText("0");
                customer_otp_text.setText("");
                verify_otp.setVisibility(View.VISIBLE);
                verify_otp.setAlpha(1);
                send_otp.setVisibility(View.VISIBLE);
                otp_verified = false;
                otp_expired = false;
                send_otp.setAlpha(1);

            }
            previously_opened_index = position;
            fade_in(add_new_prebooking_customer_layout);
        };

        recyclerViewItemClickListener listener_view_bookings = (view,position)->{
            selected_prebooking_product_id_for_view = Integer.parseInt(products_of_prebooking.get(position).getPrebooking_product_id());
            new get_my_prebookings().execute();
            fade_in(view_pre_bookings_layout);
        };

        products_prebooked_adapter = new prebooking_products_adapter(this, products_of_prebooking, listener_edit, listener_add_booking, listener_delete, listener_view_bookings);
        prebooking_products_Recycler.setAdapter(products_prebooked_adapter);
        products_prebooked_adapter.notifyDataSetChanged();
        new get_prebooking_products().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_new_product, menu);
        final View action_profile = menu.findItem(R.id.action_profile).getActionView();
        action_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appConfig.isLogin()) {
                    Intent intent = new Intent(prebooking_products_activity.this, Final_Cart.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(prebooking_products_activity.this, "Please login to proceed", Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart = menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(prebooking_products_activity.this, Buy_Now_Activity.class);
                startActivity(intent);
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add_new)
        {
            Toast.makeText(getApplicationContext(),"starting process to add new product",Toast.LENGTH_SHORT).show();
            fade_in(add_prebooking_product_layout);
            return  true;
        }
        else if(item.getItemId() == android.R.id.home)
        {
            finish();
            overridePendingTransition(0,R.anim.slide_out_left_animation);
        }
        return true;
    }


    public class get_prebooking_products extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest request = new JsonArrayRequest(appConfig.get_prebooking_products, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try
                    {
                        if(response.length() > 0)
                        {
                            if(response.getJSONObject(0).has("success"))
                            {
                                Toast.makeText(getApplicationContext(),"Nothing Found",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                for(int i =0 ; i < response.length();i++)
                                {
                                    JSONObject object = response.getJSONObject(i);
                                    PrebookingProduct pro = new PrebookingProduct();
                                    pro.setName(object.getString("Name"));
                                    pro.setExpected_date(object.getString("expected_date"));
                                    pro.setExpected_price(object.getString("expected_price"));
                                    pro.setPrebooking_product_id(object.getString("product_id"));
                                    pro.setLink(object.getString("link"));
                                    products_of_prebooking.add(pro);
                                }
                                products_prebooked_adapter.notifyDataSetChanged();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Got Nothing From Server",Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Error On Server !",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Please Retry !! ",Toast.LENGTH_SHORT).show();
                    Log.d("PREBOOKING",error.toString());
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

    private static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");

        return sdf.format(date);
    }

    public class add_product_online extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try
            {
                params.put("product_name", product_name_text.getText().toString());
                params.put("product_expected_price",expected_price_text.getText().toString());
                params.put("product_expected_date",expected_date_text.getText().toString());

            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Error in details",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            JsonObjectRequest request_add_product = new JsonObjectRequest(Request.Method.POST, AppConfig.add_prebooking_products, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Product was successfully uploaded",Toast.LENGTH_SHORT).show();
                            fade_out(add_prebooking_product_layout);
                            PrebookingProduct pro = new PrebookingProduct();
                            pro.setPrebooking_product_id(products_of_prebooking.get(products_of_prebooking.size()-1).getPrebooking_product_id() + 1);
                            pro.setName(product_name_text.getText().toString());
                            pro.setExpected_price(expected_price_text.getText().toString());
                            pro.setExpected_date(expected_date_text.getText().toString());
                            products_of_prebooking.add(pro);
                            products_prebooked_adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Sorry Error In Upload",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Unwanted Response from server",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("PREBOOKINGERROR",error.toString());
                    Toast.makeText(getApplicationContext(),"Server Error in upload !",Toast.LENGTH_SHORT).show();
                }
            });
            request_add_product.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(request_add_product);
            return null;
        }
    }

    public class update_product_online extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try
            {
                params.put("product_name", edit_product_name_text.getText().toString());
                params.put("product_expected_price",edit_expected_price_text.getText().toString());
                params.put("product_expected_date",edit_expected_date_text.getText().toString());
                params.put("product_id", products_of_prebooking.get(selected_index_for_udpate).getPrebooking_product_id());
            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Error in details",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            JsonObjectRequest request_add_product = new JsonObjectRequest(Request.Method.POST, AppConfig.update_prebooking_products, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Product was successfully updated",Toast.LENGTH_SHORT).show();
                            products_of_prebooking.get(selected_index_for_udpate).setName(edit_product_name_text.getText().toString());
                            products_of_prebooking.get(selected_index_for_udpate).setExpected_date(edit_expected_date_text.getText().toString());
                            products_of_prebooking.get(selected_index_for_udpate).setExpected_price(edit_expected_price_text.getText().toString());
                            products_prebooked_adapter.notifyItemChanged(selected_index_for_udpate);
                            fade_out(edit_prebooking_product_layout);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Sorry Error In update",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Unwanted Response from server",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("PREBOOKINGERROR",error.toString());
                    Toast.makeText(getApplicationContext(),"Server Error in update !",Toast.LENGTH_SHORT).show();
                }
            });
            request_add_product.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(request_add_product);
            return null;
        }
    }

    public class delete_product_online extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try
            {
                params.put("product_id", products_of_prebooking.get(selected_index_for_delete).getPrebooking_product_id());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            JsonObjectRequest request_delete_product = new JsonObjectRequest(Request.Method.POST, AppConfig.delete_prebooking_product, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Product was successfully deleted",Toast.LENGTH_SHORT).show();
                            products_of_prebooking.remove(selected_index_for_delete);
                            products_prebooked_adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Sorry Error In update",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Unwanted Response from server",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("PREBOOKINGERROR",error.toString());
                    Toast.makeText(getApplicationContext(),"Server Error in delete !",Toast.LENGTH_SHORT).show();
                }
            });
            request_delete_product.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(request_delete_product);
            return null;
        }
    }


    public void search_ledgers(String search) {
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
        Log.d("Searched: ", "Reference " + AppConfig.load_ledgers + search_string);
        JsonArrayRequest search_request = new JsonArrayRequest(AppConfig.load_ledgers + search_string, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ledgers.clear();
                search_ledger_recycler_adapter.notifyDataSetChanged();
                Log.d("True Response", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Ledger ledger = new Ledger();
                        ledger.setName(obj.getString("Name"));
                        ledger.setAddress(obj.getString("Address"));
                        ledger.setGSTIN(obj.getString("GSTIN"));
                        ledger.setMobile(obj.getString("Mobile"));
                        ledger.setParent(obj.getString("Parent"));
                        ledger.setPhone(obj.getString("Phone"));
                        ledger.setState(obj.getString("State"));
                        ledger.setEmail(obj.getString("Email"));
                        ledgers.add(ledger);
                        search_ledger_recycler_adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ledger_search_recycler_layout.setVisibility(View.INVISIBLE);
                    }
                }
                if(ledgers.size() > 0 )
                {
                    if(ledger_search_recycler_layout.getVisibility() != View.VISIBLE)
                    {
                        fade_in(ledger_search_recycler_layout);
                    }
                }
                else{
                    fade_out(ledger_search_recycler_layout);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Volley : ", error.toString());
                fade_out(ledger_search_recycler_layout);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("search", search_string);
                return params;
            }
        };
        if(!selected_ledger)
        {
            search_request.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(search_request);
        }
        selected_ledger = false;

    }

    public void fade_out(View v)
    {
        FadeOutAnimator fadeout = new FadeOutAnimator();
        fadeout.prepare(v);
        fadeout.setTarget(v);
        fadeout.setDuration(400);
        fadeout.addAnimatorListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) { }
            @Override public void onAnimationEnd(Animator animation) { v.setVisibility(View.GONE);}
            @Override public void onAnimationCancel(Animator animation) { }
            @Override public void onAnimationRepeat(Animator animation) { }
        });
        fadeout.start();
    }
    public void fade_in(View v)
    {
        FadeInAnimator fadein = new FadeInAnimator();
        fadein.prepare(v);
        fadein.setTarget(v);
        fadein.setDuration(400);
        fadein.addAnimatorListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {v.setVisibility(View.VISIBLE);}
            @Override public void onAnimationEnd(Animator animation) {}
            @Override public void onAnimationCancel(Animator animation) { }
            @Override public void onAnimationRepeat(Animator animation) { }
        });
        fadein.start();
    }

    public class send_otp extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try
            {
                Random rnd = new Random();
                int n = 100000 + rnd.nextInt(900000);
                otp_number_sent = n;
                params.put("otp",""+n);
                params.put("mobile_number",customer_mobile_number_text.getText().toString());

            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Error In Submitted Details",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AppConfig.send_otp_message,params,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try
                    {
                        if(response.getString("status").equals("success"))
                        {
                            fade_out(send_otp);
                            Toast.makeText(getApplicationContext(),"Otp has been send successfully",Toast.LENGTH_LONG).show();
                            fade_in(customer_remainig_time_layout);
                            otp_expired = false;
                            otp_verified = false;
                            new CountDownTimer(90000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    otp_remaining_time.setText(" " + millisUntilFinished / 1000);
                                }

                                public void onFinish() {
                                    fade_out(customer_remainig_time_layout);
                                   if(!otp_verified)
                                   {
                                       otp_expired = true;
                                       Toast.makeText(getApplicationContext(),"OTP has expired",Toast.LENGTH_SHORT).show();
                                       send_otp.setText("Resend OTP");
                                       otp_remaining_time.setText("done!");
                                       otp_number_sent = -1;
                                        fade_in(send_otp);
                                   }
                                }
                            }.start();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Otp Not Sent",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Sorry Error In Sending OTP",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("PREBOOKINGPRODUCT",error.toString());
                    Toast.makeText(getApplicationContext(),"OPT Not Sent",Toast.LENGTH_SHORT).show();
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

    public class send_confirm_bookings_sms extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try
            {
                params.put("message","Thank You Sir, For booking with khurana sales , Product Name : "+products_of_prebooking.get(previously_opened_index).getName()+" With booking amount of Rs "+customer_booking_amount_text.getText().toString());
                params.put("mobile_number",customer_mobile_number_text.getText().toString());
                params.put("customer_name",customer_name_search_Text.getText().toString());
                params.put("customer_phone",customer_mobile_number_text.getText().toString());
                params.put("customer_address",customer_address_text.getText().toString());
                params.put("prebooking_product_id",products_of_prebooking.get(previously_opened_index).getPrebooking_product_id());
                params.put("prebooking_amount",customer_booking_amount_text.getText().toString());
            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Error In Submitted Details",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AppConfig.send_confirm_booking_sms,params,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try
                    {
                        if(response.getString("status").equals("success"))
                        {
                            Toast.makeText(getApplicationContext(),"Booking has been confirmed successfully",Toast.LENGTH_LONG).show();
                            fade_out(add_new_prebooking_customer_layout);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error In Confirming Booking",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Sorry Error In Confirming Booking",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("PREBOOKINGPRODUCT",error.toString());
                    Toast.makeText(getApplicationContext(),"Booking Not Confirmed",Toast.LENGTH_SHORT).show();
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

    public class cancel_booking extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try
            {
                params.put("customer_phone",prebooked_ledgers.get(position_selected_for_cancelling).getPhone());
                params.put("prebooking_id",prebooked_ledgers.get(position_selected_for_cancelling).getPrebooking_id());
                params.put("message", "Hello Sir , Your Pre-Booking of product "+prebooked_ledgers.get(position_selected_for_cancelling).getPrebooked_product_name()+" has been successfully cancelled, "+"looking forward for your new booking with khurana sales "+"Thank You");

            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Error In Submitted Details",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Log.d("PARAMS",params.toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AppConfig.cancel_booking,params,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("RESPONSE",response.toString());
                    try
                    {
                        if(response.getString("status").equals("success"))
                        {
                            Toast.makeText(getApplicationContext(),"Booking has been cancelled successfully",Toast.LENGTH_LONG).show();
                            fade_out(add_new_prebooking_customer_layout);
                            prebooked_ledgers.get(position_selected_for_cancelling).setBooking_status("CANCELLED");
                            prebookings_view_adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error In Cancelling Booking",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Sorry Error In Cancelling Booking",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("PREBOOKINGPRODUCT",error.toString());
                    Toast.makeText(getApplicationContext(),"Booking Not Cancelled",Toast.LENGTH_SHORT).show();
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

    public class re_confirm_booking extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try
            {
                params.put("customer_phone",prebooked_ledgers.get(position_selected_for_cancelling).getPhone());
                params.put("prebooking_id",prebooked_ledgers.get(position_selected_for_cancelling).getPrebooking_id());
                params.put("message", "Hello Sir , Your Pre-Booking of product "+prebooked_ledgers.get(position_selected_for_cancelling).getPrebooked_product_name()+" has been successfully re-confirmed , "+"Thank you for booking with Khurana Sales ");

            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Error In Submitted Details",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Log.d("PARAMS",params.toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AppConfig.reconfirm_booking,params,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("RESPONSE",response.toString());
                    try
                    {
                        if(response.getString("status").equals("success"))
                        {
                            Toast.makeText(getApplicationContext(),"Booking has been re-confirmed successfully",Toast.LENGTH_LONG).show();
                            fade_out(add_new_prebooking_customer_layout);
                            prebooked_ledgers.get(position_selected_for_cancelling).setBooking_status("BOOKED");
                            prebookings_view_adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error In Re Confirming Booking",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Sorry Error In Re Confirming Booking",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("PREBOOKINGPRODUCT",error.toString());
                    Toast.makeText(getApplicationContext(),"Booking Not Re Confirmed",Toast.LENGTH_SHORT).show();
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

    public class get_my_prebookings extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            if(previously_selected_id_for_viewing_ledgers != selected_prebooking_product_id_for_view)
            {
                prebooked_ledgers.clear();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        prebookings_view_adapter.notifyDataSetChanged();
                    }
                });
            }
            previously_selected_id_for_viewing_ledgers = selected_prebooking_product_id_for_view;
         JsonArrayRequest request_prebookings = new JsonArrayRequest(AppConfig.get_prebookings_url+"?prebooking_product_id="+selected_prebooking_product_id_for_view, new Response.Listener<JSONArray>() {
             @Override
             public void onResponse(JSONArray response) {
                 if(response.length() > 0)
                {
                    try
                    {
                        if(response.getJSONObject(0).has("success"))
                        {
                            Toast.makeText(getApplicationContext(),"Sorry No Bookings Found",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(response.length() == prebooked_ledgers.size())
                            {
                                return;
                            }
                            for(int i  =0 ; i<response.length();i++)
                            {
                                JSONObject ledger_object = response.getJSONObject(i);
                                PrebookingLedger ledger = new PrebookingLedger();
                                ledger.setName(ledger_object.getString("name"));
                                ledger.setPhone(ledger_object.getString("phone"));
                                ledger.setPrebooked_product_id(ledger_object.getString("prebooking_product_id"));
                                ledger.setAddress(ledger_object.getString("address"));
                                ledger.setPrebooking_amount(ledger_object.getString("customer_prebooking_amount"));
                                ledger.setBooking_status(ledger_object.getString("status"));
                                ledger.setPrebooked_product_name(ledger_object.getString("product_name"));
                                ledger.setPrebooking_id(ledger_object.getString("customer_prebooking_id"));
                                prebooked_ledgers.add(ledger);
                                prebookings_view_adapter.notifyDataSetChanged();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Invalid Response form server", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Log.d("PREBOOKINGACTIVITY",error.toString());
                 Toast.makeText(getApplicationContext(),"Could Not Get Please Retry !!",Toast.LENGTH_SHORT).show();
             }
         });
            request_prebookings.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(request_prebookings);
            return null;
        }
    }

    public class send_arrival_sms extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try
            {
                params.put("customer_phone",prebooked_ledgers.get(position_selected_for_sending_sms).getPhone());
                params.put("prebooking_id",prebooked_ledgers.get(position_selected_for_cancelling).getPrebooking_id());
                params.put("product_name", prebooked_ledgers.get(position_selected_for_sending_sms).getPrebooked_product_name());
            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Error In Submitted Details",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Log.d("PARAMS",params.toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AppConfig.send_arrival_sms,params,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("RESPONSE",response.toString());
                    try
                    {
                        if(response.getString("status").equals("success"))
                        {
                            Toast.makeText(getApplicationContext(),"SMS has been sent successfully",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error In Sending SMS",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Sorry Error In Sending SMS",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("PREBOOKINGPRODUCT",error.toString());
                    Toast.makeText(getApplicationContext(),"SMS not sent ",Toast.LENGTH_SHORT).show();
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


}
