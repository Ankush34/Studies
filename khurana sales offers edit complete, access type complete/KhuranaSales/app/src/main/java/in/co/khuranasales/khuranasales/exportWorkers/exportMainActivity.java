package in.co.khuranasales.khuranasales.exportWorkers;

import android.animation.Animator;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import in.co.khuranasales.khuranasales.Activity_Login;
import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.Batch;
import in.co.khuranasales.khuranasales.Buy_Now_Activity;
import in.co.khuranasales.khuranasales.Final_Cart;
import in.co.khuranasales.khuranasales.MainActivity;
import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class exportMainActivity extends AppCompatActivity {
    public Toolbar toolbar;
    public RelativeLayout background_transparent;

    public FadeOutAnimator animator;
    public FadeInAnimator in_animator;

    public RelativeLayout select_promoter_export_orders;
    public RelativeLayout select_brand_export_stock;
    public RelativeLayout select_brand_export_batch;
    public RelativeLayout select_type_export_payment;

    public CardView card_export_orders;
    public CardView card_export_stock;
    public CardView card_export_payments;
    public CardView card_export_batch;

    public ImageView down_arrow_export_order;
    public ImageView down_arrow_select_brand_export_stock;
    public ImageView down_arrow_select_brand_export_batch;
    public ImageView down_arrow_select_type_payment_export;

    public RecyclerView brands_select_export_stock_recycler;
    public select_options_export_recycler_adapter brands_select_export_stock_recycler_adapter;
    public RecyclerView brands_select_export_batch_recycler;
    public select_options_export_recycler_adapter brands_select_export_batch_recycler_adapter;
    public RecyclerView promoters_select_export_orders_recycler;
    public select_options_export_recycler_adapter promoters_select_export_orders_recycler_adapter;
    public RecyclerView payment_type_select_export_batch_recycler;
    public select_options_export_recycler_adapter payment_type_select_export_batch_recycler_adapter;

    public ArrayList<selectionPojo> selection_list_promoters = new ArrayList<>();
    public ArrayList<selectionPojo> selection_list_brands = new ArrayList<>();
    public ArrayList<selectionPojo> selection_list_payment_types = new ArrayList<>();

    public Button promoter_selected_button;
    public ArrayList<OrderDetails> order_details = new ArrayList<>();

    public Button brand_selected_export_stock_button;
    public ArrayList<Product> product_details = new ArrayList<>();

    public ArrayList<String> batch_locations = new ArrayList<>();
    public Button brand_selected_export_batch_button;

    public Button payment_export_button;

    Button promoter_selection_button_share;
    Button brand_selected_button_export_stock_share;
    Button brand_selected_button_export_batch_share;
    Button payment_type_selected_button_export_share;

    public Boolean share =  false;
    public ArrayList<cash_export> cash_export_data = new ArrayList<>();
    public ArrayList<card_export> card_export_data = new ArrayList<>();
    public ArrayList<finance_export> finance_export_data = new ArrayList<>();
    public ArrayList<cheque_export> cheque_export_data = new ArrayList<>();
    public ArrayList<paytm_export> paytm_export_data = new ArrayList<>();

    public AppConfig appConfig;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_main_activity);
        StrictMode.VmPolicy.Builder builder_new = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder_new.build());

        appConfig = new AppConfig(getApplicationContext());
        background_transparent = (RelativeLayout)findViewById(R.id.background_transparent);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        promoter_selected_button = (Button)findViewById(R.id.promoters_selected_button);
        promoter_selected_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new get_promoters_orders().execute();
                slideToBottom(select_promoter_export_orders);
                fadeOut(background_transparent);
            }
        });

        brand_selected_export_stock_button = (Button)findViewById(R.id.export_stock_button);
        brand_selected_export_stock_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new get_product_details().execute();
                slideToBottom(select_brand_export_stock);
                fadeOut(background_transparent);
            }
        });

        brand_selected_export_batch_button = (Button)findViewById(R.id.brand_selection_export_batch_button);
        brand_selected_export_batch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new get_product_details_with_batch().execute();
                slideToBottom(select_brand_export_batch);
                fadeOut(background_transparent);
            }
        });

        payment_export_button = (Button)findViewById(R.id.payment_selected_export_button);
        payment_export_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new get_payment_details().execute();
                slideToBottom(select_type_export_payment);
                fadeOut(background_transparent);
            }
        });

        promoter_selection_button_share = (Button)findViewById(R.id.promoters_selected_button_share);
        promoter_selection_button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share = true;
                new get_promoters_orders().execute();
                slideToBottom(select_promoter_export_orders);
                fadeOut(background_transparent);
            }
        });

        brand_selected_button_export_stock_share = (Button)findViewById(R.id.export_stock_button_share);
        brand_selected_button_export_stock_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share = true;
                new get_product_details().execute();
                slideToBottom(select_brand_export_stock);
                fadeOut(background_transparent);
            }
        });

        brand_selected_button_export_batch_share = (Button)findViewById(R.id.brand_selection_export_batch_button_share);
        brand_selected_button_export_batch_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share = true;
                new get_product_details_with_batch().execute();
                slideToBottom(select_brand_export_batch);
                fadeOut(background_transparent);
            }
        });

        payment_type_selected_button_export_share = (Button)findViewById(R.id.payment_selected_export_share_button);
        payment_type_selected_button_export_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share = true;
                new get_payment_details().execute();
                slideToBottom(select_type_export_payment);
                fadeOut(background_transparent);
            }
        });

        down_arrow_export_order = (ImageView)findViewById(R.id.down_arrow_promoter_list);
        down_arrow_select_brand_export_stock = (ImageView)findViewById(R.id.down_arrow_brand_select_export_stock);
        down_arrow_select_type_payment_export = (ImageView)findViewById(R.id.down_arrow_type_select_payment_export);
        down_arrow_select_brand_export_batch = (ImageView)findViewById(R.id.down_arrow_brand_select_export_batch);

        card_export_orders = (CardView)findViewById(R.id.layout_export_orders);
        card_export_stock = (CardView)findViewById(R.id.layout_export_stock);
        card_export_payments = (CardView)findViewById(R.id.layout_export_payment);
        card_export_batch = (CardView)findViewById(R.id.layout_export_batch);

        select_promoter_export_orders = (RelativeLayout)findViewById(R.id.select_promoter_list_export_layout);
        select_brand_export_stock = (RelativeLayout)findViewById(R.id.select_brand_list_export_stock_layout);
        select_brand_export_batch = (RelativeLayout)findViewById(R.id.select_brand_list_export_batch_layout);
        select_type_export_payment = (RelativeLayout)findViewById(R.id.select_payment_type_export_layout);

        promoters_select_export_orders_recycler = (RecyclerView)findViewById(R.id.recycler_promoter_selection);
        promoters_select_export_orders_recycler_adapter = new select_options_export_recycler_adapter(this,selection_list_promoters);
        promoters_select_export_orders_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        promoters_select_export_orders_recycler.setHasFixedSize(true);
        promoters_select_export_orders_recycler.setAdapter(promoters_select_export_orders_recycler_adapter);
        promoters_select_export_orders_recycler_adapter.notifyDataSetChanged();

            brands_select_export_stock_recycler = (RecyclerView)findViewById(R.id.recycler_brand_selection_export_stock);
        brands_select_export_stock_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        brands_select_export_stock_recycler_adapter = new select_options_export_recycler_adapter(this, selection_list_brands);
        brands_select_export_stock_recycler.setHasFixedSize(true);
        brands_select_export_stock_recycler.setAdapter(brands_select_export_stock_recycler_adapter);
        brands_select_export_stock_recycler_adapter.notifyDataSetChanged();

        brands_select_export_batch_recycler = (RecyclerView)findViewById(R.id.recycler_brand_selection_export_batch);
        brands_select_export_batch_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        brands_select_export_batch_recycler_adapter = new select_options_export_recycler_adapter(this, selection_list_brands);
        brands_select_export_batch_recycler.setHasFixedSize(true);
        brands_select_export_batch_recycler.setAdapter(brands_select_export_batch_recycler_adapter);
        brands_select_export_batch_recycler_adapter.notifyDataSetChanged();

        payment_type_select_export_batch_recycler = (RecyclerView)findViewById(R.id.recycler_payment_type);
        payment_type_select_export_batch_recycler.setHasFixedSize(true);
        payment_type_select_export_batch_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        payment_type_select_export_batch_recycler_adapter = new select_options_export_recycler_adapter(this, selection_list_payment_types);
        payment_type_select_export_batch_recycler.setAdapter(payment_type_select_export_batch_recycler_adapter);
        payment_type_select_export_batch_recycler_adapter.notifyDataSetChanged();

        new load_promoters().execute();
        new load_brands().execute();
        selectionPojo p = new selectionPojo("Cash",false);
        selection_list_payment_types.add(p);
        selectionPojo p1 = new selectionPojo("Card",false);
        selection_list_payment_types.add(p1);
        selectionPojo p2 = new selectionPojo("Finance",false);
        selection_list_payment_types.add(p2);
        selectionPojo p3 = new selectionPojo("Paytm",false);
        selection_list_payment_types.add(p3);
        selectionPojo p4 = new selectionPojo("Cheque",false);
        selection_list_payment_types.add(p4);


        card_export_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn(background_transparent);
                slideToTop(select_promoter_export_orders);
            }
        });
        card_export_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn(background_transparent);
                slideToTop(select_brand_export_stock);
            }
        });
        card_export_payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn(background_transparent);
                slideToTop(select_type_export_payment);
            }
        });
        card_export_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn(background_transparent);
                slideToTop(select_brand_export_batch);
            }
        });

        down_arrow_export_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(background_transparent);
                slideToBottom(select_promoter_export_orders);
            }
        });

        down_arrow_select_brand_export_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(background_transparent);
                slideToBottom(select_brand_export_stock);
            }
        });

        down_arrow_select_type_payment_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(background_transparent);
                slideToBottom(select_type_export_payment);
            }
        });

        down_arrow_select_brand_export_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(background_transparent);
                slideToBottom(select_brand_export_batch);
            }
        });
    }

    public void slideToBottom(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight()-10);
        animate.setDuration(500);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) { }
            @Override public void onAnimationEnd(Animation animation) {view.setVisibility(View.INVISIBLE);}
            @Override public void onAnimationRepeat(Animation animation) { }
        });
        view.startAnimation(animate);
    }

    public void slideToTop(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,view.getHeight(),0);
        animate.setDuration(500);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {view.setVisibility(View.VISIBLE); }
            @Override public void onAnimationEnd(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
        });
        view.startAnimation(animate);
    }

    public class load_promoters extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest request_load_promoters = new JsonArrayRequest(AppConfig.load_promoters_url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i =0 ; i < response.length();i++)
                    {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            selectionPojo p = new selectionPojo(obj.getString("promoter_name"),false);
                            p.setPromoter_email(obj.getString("promoter_email"));
                            selection_list_promoters.add(p);
                            promoters_select_export_orders_recycler_adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.getMessage() != null)
                    {
                        Toast.makeText(getApplicationContext(),"Volley Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Some Volley Error Has Occured",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            request_load_promoters.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(request_load_promoters);
            return null;
        }
    }

    public class load_brands extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest request_brands = new JsonArrayRequest(AppConfig.url_categories+"?category=Mobile", new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i =0 ; i < response.length();i++)
                    {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            selectionPojo p = new selectionPojo(obj.getString("brand"),false);
                            selection_list_brands.add(p);
                            brands_select_export_batch_recycler_adapter.notifyDataSetChanged();
                            brands_select_export_stock_recycler_adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.getMessage() != null)
                    {
                        Toast.makeText(getApplicationContext(),"Volley Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Some Volley Error Has Occured",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            request_brands.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(request_brands);
            return null;
        }
    }

    public class load_payment_types extends  AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
           JsonArrayRequest request_payment_types = new JsonArrayRequest("", new Response.Listener<JSONArray>() {
               @Override
               public void onResponse(JSONArray response) {
                   for(int i =0 ; i < response.length();i++)
                   {
                       try {
                           JSONObject obj = response.getJSONObject(i);
                           selectionPojo p = new selectionPojo(obj.getString("name"),false);
                           selection_list_payment_types.add(p);
                       } catch (Exception e) {
                           e.printStackTrace();
                       }

                   }
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   if(error.getMessage() != null)
                   {
                       Toast.makeText(getApplicationContext(),"Volley Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
                   }else
                   {
                       Toast.makeText(getApplicationContext(),"Some Volley Error Has Occured",Toast.LENGTH_SHORT).show();
                   }
               }
           });
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            overridePendingTransition(0,R.anim.slide_out_left_animation);
        }
        int id = item.getItemId();
        if (id == R.id.signin) {
            if(appConfig.isLogin())
            {
                Toast.makeText(exportMainActivity.this,"You are already logged in",Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent = new Intent(exportMainActivity.this,Activity_Login.class);
                startActivity(intent);
            }
        }else if(id == R.id.signout)
        {
            if(appConfig.isLogin())
            {
                Intent intent = new Intent(exportMainActivity.this,Activity_Login.class);
                startActivity(intent);

            }else{
                Toast.makeText(exportMainActivity.this," Please login to signout",Toast.LENGTH_LONG).show();

            }
        }else if(id == R.id.signup)
        {
            Intent intent = new Intent(exportMainActivity.this,MainActivity.class);
            startActivity(intent);
        }
        else if(id == android.R.id.home)
        {
            finish();
            overridePendingTransition(0,R.anim.slide_out_left_animation);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_my_cart,menu);
        final View action_profile = menu.findItem(R.id.action_profile).getActionView();
        action_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appConfig.isLogin())
                {
                    Intent intent = new Intent(exportMainActivity.this,Final_Cart.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(exportMainActivity.this,"Please login to proceed",Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart= menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(exportMainActivity.this,Buy_Now_Activity.class);
                startActivity(intent);
            }
        });
        return true;
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

    public class get_promoters_orders extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            order_details.clear();
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c);
            JSONObject params_json = new JSONObject();
            JSONArray array = new JSONArray();
            ArrayList<selectionPojo> selection_list = promoters_select_export_orders_recycler_adapter.selection_list;
            for(int i = 0; i <selection_list.size();i++)
            {
                if(selection_list.get(i).is_selected()) {
                    try {
                        JSONObject object = new JSONObject();
                        Log.d("EXPORT",selection_list.get(i).getPromoter_email());
                        object.put("email", selection_list.get(i).getPromoter_email());
                        object.put("from", "26-06-2018");
                        object.put("to", formattedDate);
                        array.put(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                params_json.put("promoters", array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest get_promoters_data = new JsonObjectRequest(Request.Method.POST,AppConfig.load_promoters_orders,params_json,
                    new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.d("exportMainActivity","response: "+response.toString());
                        if(response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Successfully received order",Toast.LENGTH_LONG).show();
                            JSONArray array = response.getJSONArray("response");
                            for(int i = 0; i < array.length();i++)
                            {
                                JSONObject obj  = array.getJSONObject(i);
                                OrderDetails details = new OrderDetails();
                                details.setPromoter_name(obj.getString("promoter_name"));
                                details.setProduct_name(obj.getString("name"));
                                details.setTotal_price(obj.getString("price"));
                                details.setTotal_count(obj.getString("count"));
                                details.setDiscount_amount(obj.getString("discount_offered"));
                                details.setCustomer_name(obj.getString("customer_name"));
                                order_details.add(details);
                            }
                            generatePromoterExport(order_details);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Please Select other promoters retry !!",Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("BuyNow Volley Error: ","Error in upload promoters request"+error.getMessage());
                    Toast.makeText(getApplicationContext(),"Please Select other promoters retry !!",Toast.LENGTH_LONG).show();
                }
            });
            get_promoters_data.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(get_promoters_data);
        return null;
        }
    }

    public void generatePromoterExport(ArrayList<OrderDetails> order_details) {
        String state = Environment.getExternalStorageState();
        String root = "";
        String fileName = "/promoters_sold_export" + System.currentTimeMillis() + ".pdf";
        String parent = "KhuranaSales";
        File mFile;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            root = Environment.getExternalStorageDirectory().toString();
            mFile = new File(root, parent);
            if (!mFile.isDirectory())
                mFile.mkdirs();
        } else {
            root = exportMainActivity.this.getFilesDir().toString();
            mFile = new File(root, parent);
            if (!mFile.isDirectory())
                mFile.mkdirs();
        }
        String strCaptured_FileName = root + "/KhuranaSales" + fileName;
        String targetPdf = strCaptured_FileName;
        File filePath = new File(targetPdf);
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Khurana sales business solutions");
            document.addCreator("Ankush Amit Khurana");

            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            PdfPTable table = new PdfPTable(3);
            table.addCell(getCell("Export Date: "+(new Date().getTime()), PdfPCell.ALIGN_LEFT));
            table.addCell(getCell("Khurana Sales Promoters Orders Export",PdfPCell.ALIGN_CENTER));
            table.addCell(getCell("Email: khuranasales2015@gmail.com",PdfPCell.ALIGN_RIGHT));
            table.setWidthPercentage(100);
            table.setSpacingAfter(20);
            document.add(table);

            Font tableFont = FontFactory.getFont(FontFactory.HELVETICA,10,Font.NORMAL);
            PdfPTable table_promoter = new PdfPTable(6);
            table_promoter.addCell(new Paragraph("Promoter Name",tableFont));
            table_promoter.addCell(new Paragraph("Product Name",tableFont));
            table_promoter.addCell(new Paragraph("Sold Count",tableFont));
            table_promoter.addCell(new Paragraph("Total",tableFont));
            table_promoter.addCell(new Paragraph("Applied Discount",tableFont));
            table_promoter.addCell(new Paragraph("Customer Name",tableFont));
            table_promoter.setHeaderRows(1);
            for(int i = 0 ;i < order_details.size();i++)
            {
                table_promoter.addCell(new Paragraph(order_details.get(i).getPromoter_name(),tableFont));
                table_promoter.addCell(new Paragraph(order_details.get(i).getProduct_name(),tableFont));
                table_promoter.addCell(new Paragraph(order_details.get(i).getTotal_count(),tableFont));
                table_promoter.addCell(new Paragraph(order_details.get(i).getTotal_price(),tableFont));
                table_promoter.addCell(new Paragraph(order_details.get(i).getDiscount_amount(),tableFont));
                table_promoter.addCell(new Paragraph(order_details.get(i).getCustomer_name(),tableFont));
            }
            table_promoter.setWidthPercentage(100);
            document.add(table_promoter);
            document.close();
            Toast.makeText(getApplicationContext(),"Export has been created",Toast.LENGTH_LONG).show();
            if(share)
            {
                Uri uri = Uri.fromFile(filePath);
                Intent sharing = new Intent();
                sharing.setAction(Intent.ACTION_SEND);
                sharing.setType("application/pdf");
                sharing.putExtra(Intent.EXTRA_STREAM, uri);
                this.startActivity(sharing);
                share = false;
            }
        }
         catch (Exception e) {
            e.printStackTrace();
             Toast.makeText(getApplicationContext(),"Sorry error occured while creating export pleasr retry !! ",Toast.LENGTH_LONG).show();
         }
    }
    public PdfPCell getCell(String text, int alignment) {
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Paragraph(text,paragraphFont));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }


    public class get_product_details extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            product_details.clear();
            JSONObject params_json = new JSONObject();
            JSONArray array = new JSONArray();
            ArrayList<selectionPojo> selection_list = brands_select_export_stock_recycler_adapter.selection_list;
            for(int i = 0; i <selection_list.size();i++)
            {
                if(selection_list.get(i).is_selected()) {
                    try {
                        JSONObject object = new JSONObject();
                        Log.d("EXPORT",selection_list.get(i).getSelection_text());
                        object.put("brand",selection_list.get(i).getSelection_text());
                        object.put("type","Mobile");
                        array.put(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                params_json.put("brands", array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest get_promoters_data = new JsonObjectRequest(Request.Method.POST,AppConfig.load_products_by_multiple_brands,params_json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("exportMainActivity","response: "+response.toString());
                                if(response.getString("success").equals("true"))
                                {
                                    Toast.makeText(getApplicationContext(),"Successfully received products in stock",Toast.LENGTH_LONG).show();
                                    JSONArray array = response.getJSONArray("response");
                                    for(int i = 0; i < array.length();i++)
                                    {
                                        JSONObject obj  = array.getJSONObject(i);
                                        Product product = new Product();
                                        product.set_Name(obj.getString("Name"));
                                        product.setProduct_id(obj.getInt("product_id"));
                                        product.set_Stock(obj.getInt("stock"));
                                        product.setPrice_mrp(obj.getInt("mrp"));
                                        product.setPrice_mop(obj.getInt("mop"));
                                        product.setPrice_ks(obj.getInt("ksprice"));
                                        product.setProduct_HSN(obj.getString("hsn"));
                                        product.setTax(obj.getInt("tax"));

                                        product_details.add(product);
                                    }
                                    generateStockExport(product_details);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Please retry !!",Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("BuyNow Volley Error: ","Error in upload brands request"+error.getMessage());
                    Toast.makeText(getApplicationContext(),"Please retry !!",Toast.LENGTH_LONG).show();
                }
            });
            get_promoters_data.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(get_promoters_data);
            return null;
        }
    }
   public void generateStockExport(ArrayList<Product> product_details)
   {
       String state = Environment.getExternalStorageState();
       String root = "";
       String fileName = "/stock_export" + System.currentTimeMillis() + ".pdf";
       String parent = "KhuranaSales";
       File mFile;
       if (Environment.MEDIA_MOUNTED.equals(state)) {
           root = Environment.getExternalStorageDirectory().toString();
           mFile = new File(root, parent);
           if (!mFile.isDirectory())
               mFile.mkdirs();
       } else {
           root = exportMainActivity.this.getFilesDir().toString();
           mFile = new File(root, parent);
           if (!mFile.isDirectory())
               mFile.mkdirs();
       }
       String strCaptured_FileName = root + "/KhuranaSales" + fileName;
       String targetPdf = strCaptured_FileName;
       File filePath = new File(targetPdf);
       try {
           Document document = new Document();
           PdfWriter.getInstance(document, new FileOutputStream(filePath));
           document.open();
           document.setPageSize(PageSize.A4);
           document.addCreationDate();
           document.addAuthor("Khurana sales business solutions");
           document.addCreator("Ankush Amit Khurana");

           Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
           PdfPTable table = new PdfPTable(3);
           table.addCell(getCell("Export Date: "+(new Date().getTime()), PdfPCell.ALIGN_LEFT));
           table.addCell(getCell("Khurana Sales Stock Export",PdfPCell.ALIGN_CENTER));
           table.addCell(getCell("Email: khuranasales2015@gmail.com",PdfPCell.ALIGN_RIGHT));
           table.setWidthPercentage(100);
           table.setSpacingAfter(20);
           document.add(table);

           Font tableFont = FontFactory.getFont(FontFactory.HELVETICA,10,Font.NORMAL);
           PdfPTable table_promoter = new PdfPTable(7);
           table_promoter.addCell(new Paragraph("Product Name",tableFont));
           table_promoter.addCell(new Paragraph("Stock",tableFont));
           table_promoter.addCell(new Paragraph("Product MRP",tableFont));
           table_promoter.addCell(new Paragraph("Product MOP",tableFont));
           table_promoter.addCell(new Paragraph("Product KS-Price",tableFont));
           table_promoter.addCell(new Paragraph("Product Tax",tableFont));
           table_promoter.addCell(new Paragraph("Product Hsn",tableFont));
           table_promoter.setHeaderRows(1);
           for(int i = 0 ;i < product_details.size();i++)
           {
               table_promoter.addCell(new Paragraph(product_details.get(i).get_Name(),tableFont));
               table_promoter.addCell(new Paragraph(""+product_details.get(i).get_Stock(),tableFont));
               table_promoter.addCell(new Paragraph(""+product_details.get(i).getPrice_mrp(),tableFont));
               table_promoter.addCell(new Paragraph(""+product_details.get(i).getPrice_mop(),tableFont));
               table_promoter.addCell(new Paragraph(""+product_details.get(i).getPrice_ks(),tableFont));
               table_promoter.addCell(new Paragraph(""+product_details.get(i).getTax(),tableFont));
               table_promoter.addCell(new Paragraph(product_details.get(i).getProduct_HSN(),tableFont));
           }
           table_promoter.setWidthPercentage(100);
           document.add(table_promoter);
           document.close();
           Toast.makeText(getApplicationContext(),"Export has been created",Toast.LENGTH_LONG).show();
           if(share)
           {
               Uri uri = Uri.fromFile(filePath);
               Intent sharing = new Intent();
               sharing.setAction(Intent.ACTION_SEND);
               sharing.setType("application/pdf");
               sharing.putExtra(Intent.EXTRA_STREAM, uri);
               this.startActivity(sharing);
               share = false;
           }
       }
       catch (Exception e) {
           e.printStackTrace();
           Toast.makeText(getApplicationContext(),"Sorry error occured while creating export pleasr retry !! ",Toast.LENGTH_LONG).show();
       }
   }

   public class get_product_details_with_batch extends AsyncTask<Void, Void, Void>
   {
       @Override
       protected Void doInBackground(Void... voids) {

           JSONObject params_json = new JSONObject();
           JSONArray array = new JSONArray();
           ArrayList<selectionPojo> selection_list = brands_select_export_batch_recycler_adapter.selection_list;
           for(int i = 0; i <selection_list.size();i++)
           {
               if(selection_list.get(i).is_selected()) {
                   try {
                       JSONObject object = new JSONObject();
                       Log.d("EXPORT",selection_list.get(i).getSelection_text());
                       object.put("brand",selection_list.get(i).getSelection_text());
                       object.put("type","Mobile");
                       array.put(object);
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           }
           try {
               params_json.put("brands", array);
           } catch (JSONException e) {
               e.printStackTrace();
           }
           JsonObjectRequest request_products_with_batch = new JsonObjectRequest(Request.Method.POST, AppConfig.load_products_with_batch, params_json, new Response.Listener<JSONObject>() {
               @Override
               public void onResponse(JSONObject response) {
                   Log.d("EXPORTMAINSTOCK",response.toString());
                    product_details.clear();
                    try{
                        if(response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Successfully received products in stock",Toast.LENGTH_LONG).show();
                            JSONArray array = response.getJSONArray("response");
                            for(int i = 0; i < array.length();i++)
                            {
                                JSONObject obj  = array.getJSONObject(i);
                                Product product = new Product();
                                product.set_Name(obj.getString("Name"));
                                product.setProduct_id(obj.getInt("product_id"));
                                product.set_Stock(obj.getInt("stock"));
                                product.setPrice_mrp(obj.getInt("mrp"));
                                product.setPrice_mop(obj.getInt("mop"));
                                product.setPrice_ks(obj.getInt("ksprice"));
                                product.setProduct_HSN(obj.getString("hsn"));
                                product.setTax(obj.getInt("tax"));
                                JSONObject batches = obj.getJSONObject("batch");
                                Iterator<String> iter = batches.keys();
                                while (iter.hasNext()) {
                                    String key = iter.next();
                                    if(!batch_locations.contains(key))
                                    {
                                        batch_locations.add(key);
                                    }
                                    try {
                                        String value = (String) batches.get(key);
                                        String[] batch_numbers =value.split(",");
                                        for(int j = 0; j < batch_numbers.length;j++)
                                        {
                                            product.batch_numbers.add(new Batch(key,batch_numbers[j]));
                                        }

                                    } catch (JSONException e) {
                                        // Something went wrong!
                                    }
                                }
                                product_details.add(product);
                            }
                            generateExportStockBatch(product_details);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Please Retry !!",Toast.LENGTH_LONG).show();

                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Please Retry !! ",Toast.LENGTH_LONG).show();
                    }
                                  }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                Log.d("ExportMainActivity","Error Occured In Volley");

               }
           });
           request_products_with_batch.setRetryPolicy(new DefaultRetryPolicy(
                   DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                   DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                   DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

           AppController.getInstance().addToRequestQueue(request_products_with_batch);
           return null;
       }
   }

   public void generateExportStockBatch(ArrayList<Product> product_details)
   {
       String state = Environment.getExternalStorageState();
       String root = "";
       String fileName = "/batch_export" + System.currentTimeMillis() + ".pdf";
       String parent = "KhuranaSales";
       File mFile;
       if (Environment.MEDIA_MOUNTED.equals(state)) {
           root = Environment.getExternalStorageDirectory().toString();
           mFile = new File(root, parent);
           if (!mFile.isDirectory())
               mFile.mkdirs();
       } else {
           root = exportMainActivity.this.getFilesDir().toString();
           mFile = new File(root, parent);
           if (!mFile.isDirectory())
               mFile.mkdirs();
       }
       String strCaptured_FileName = root + "/KhuranaSales" + fileName;
       String targetPdf = strCaptured_FileName;
       File filePath = new File(targetPdf);
       try {
           Document document = new Document();
           PdfWriter.getInstance(document, new FileOutputStream(filePath));
           document.open();
           document.setPageSize(PageSize.A4);
           document.addCreationDate();
           document.addAuthor("Khurana sales business solutions");
           document.addCreator("Ankush Amit Khurana");

           Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
           PdfPTable table = new PdfPTable(3);
           table.addCell(getCell("Export Date: "+(new Date().getTime()), PdfPCell.ALIGN_LEFT));
           table.addCell(getCell("Khurana Sales Batch Export",PdfPCell.ALIGN_CENTER));
           table.addCell(getCell("Email: khuranasales2015@gmail.com",PdfPCell.ALIGN_RIGHT));
           table.setWidthPercentage(100);
           table.setSpacingAfter(20);
           document.add(table);

           Font tableFont = FontFactory.getFont(FontFactory.HELVETICA,10,Font.NORMAL);
           PdfPTable table_promoter = new PdfPTable(7 + batch_locations.size());
           table_promoter.addCell(new Paragraph("Product Name",tableFont));
           table_promoter.addCell(new Paragraph("Stock",tableFont));
           table_promoter.addCell(new Paragraph("Product MRP",tableFont));
           table_promoter.addCell(new Paragraph("Product MOP",tableFont));
           table_promoter.addCell(new Paragraph("Product KS-Price",tableFont));
           table_promoter.addCell(new Paragraph("Product Tax",tableFont));
           table_promoter.addCell(new Paragraph("Product Hsn",tableFont));
           for(int i = 0 ; i<batch_locations.size();i++)
           {
               table_promoter.addCell(new Paragraph(batch_locations.get(i),tableFont));
           }
           table_promoter.setHeaderRows(1);
           for(int i = 0; i < this.product_details.size(); i++)
           {
               table_promoter.addCell(new Paragraph(this.product_details.get(i).get_Name(),tableFont));
               table_promoter.addCell(new Paragraph(""+ this.product_details.get(i).get_Stock(),tableFont));
               table_promoter.addCell(new Paragraph(""+ this.product_details.get(i).getPrice_mrp(),tableFont));
               table_promoter.addCell(new Paragraph(""+ this.product_details.get(i).getPrice_mop(),tableFont));
               table_promoter.addCell(new Paragraph(""+ this.product_details.get(i).getPrice_ks(),tableFont));
               table_promoter.addCell(new Paragraph(""+ this.product_details.get(i).getTax(),tableFont));
               table_promoter.addCell(new Paragraph(this.product_details.get(i).getProduct_HSN(), tableFont));
                for(int j = 0;j<batch_locations.size();j++)
                {
                    StringBuilder batch_numbers_writing = new StringBuilder("");
                    ArrayList<Batch> batch_numbers = product_details.get(i).batch_numbers;
                    for(int k = 0; k < batch_numbers.size();k++)
                    {
                        if(batch_numbers.get(k).getLocation().equals(batch_locations.get(j)))
                        {
                            batch_numbers_writing.append(batch_numbers.get(k).getNumber()+", \n");
                        }
                    }
                    int lastCommaIndex = batch_numbers_writing.lastIndexOf(",");
                    table_promoter.addCell(new Paragraph(batch_numbers_writing.toString().substring(0,lastCommaIndex),tableFont));
                }
           }
           table_promoter.setWidthPercentage(100);
           document.add(table_promoter);
           document.close();
           Toast.makeText(getApplicationContext(),"Export has been created",Toast.LENGTH_LONG).show();
           if(share)
           {
               Uri uri = Uri.fromFile(filePath);
               Intent sharing = new Intent();
               sharing.setAction(Intent.ACTION_SEND);
               sharing.setType("application/pdf");
               sharing.putExtra(Intent.EXTRA_STREAM, uri);
               this.startActivity(sharing);
               share = false;
           }
       }
       catch (Exception e) {
           e.printStackTrace();
           Toast.makeText(getApplicationContext(),"Sorry error occured while creating export pleasr retry !! ",Toast.LENGTH_LONG).show();
       }
   }
    public class get_payment_details extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {

            JSONObject params_json = new JSONObject();
            JSONArray array = new JSONArray();
            ArrayList<selectionPojo> selection_list = payment_type_select_export_batch_recycler_adapter.selection_list;
            for(int i = 0; i <selection_list.size();i++)
            {
                if(selection_list.get(i).is_selected()) {
                    try {
                        JSONObject object = new JSONObject();
                        Log.d("EXPORT",selection_list.get(i).getSelection_text());
                        object.put("payment_type",selection_list.get(i).getSelection_text());
                        array.put(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                params_json.put("payment_types", array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest request_payment_details = new JsonObjectRequest(Request.Method.POST, AppConfig.url_payment_export, params_json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("EXPORTMAINSTOCK",response.toString());
                    product_details.clear();
                    try{
                        if(response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Successfully received payments data",Toast.LENGTH_LONG).show();
                            generate_payment_export(response);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Please Retry !!",Toast.LENGTH_LONG).show();

                        }

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Please Retry !! ",Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ExportMainActivity","Error Occured In Volley");

                }
            });
            request_payment_details.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(request_payment_details);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public void generate_payment_export(JSONObject response)
    {
        paytm_export_data.clear();
        cash_export_data.clear();
        finance_export_data.clear();
        card_export_data.clear();
        cheque_export_data.clear();

        try {
            ArrayList<selectionPojo> selectionlistpayment = payment_type_select_export_batch_recycler_adapter.selection_list;
            for (int i = 0; i < selectionlistpayment.size(); i++) {
                if (selectionlistpayment.get(i).is_selected()) {
                    if (selectionlistpayment.get(i).getSelection_text().equals("Cash")) {
                        JSONArray array = response.getJSONArray("cash_payments");
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject obj_cash = array.getJSONObject(j);
                            cash_export cash_export_obj = new cash_export();
                            cash_export_obj.setAmount(obj_cash.getString("cash_payment_amount"));
                            cash_export_obj.setPromoter_name(obj_cash.getString("promoter_name"));
                            cash_export_obj.setCustomer_name(obj_cash.getString("customer_name"));
                            cash_export_obj.setSales_order_number(obj_cash.getString("sales_order_number"));
                            cash_export_data.add(cash_export_obj);
                        }
                    }
                    if (selectionlistpayment.get(i).getSelection_text().equals("Card")) {
                        JSONArray array = response.getJSONArray("card_payments");
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject obj_card = array.getJSONObject(j);
                            card_export card_export_obj = new card_export();
                            card_export_obj.setSales_order_number(obj_card.getString("sales_order_number"));
                            card_export_obj.setCard_bank_name(obj_card.getString("card_bank_name"));
                            card_export_obj.setCard_payment_amount(obj_card.getString("card_payment_amount"));
                            card_export_obj.setCustomer_name(obj_card.getString("customer_name"));
                            card_export_obj.setPromoter_name(obj_card.getString("promoter_name"));
                            card_export_data.add(card_export_obj);
                        }
                    }
                    if (selectionlistpayment.get(i).getSelection_text().equals("Cheque")) {
                        JSONArray array = response.getJSONArray("cheque_payments");
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject obj_cheque = array.getJSONObject(j);
                            cheque_export cheque_export_obj = new cheque_export();
                            cheque_export_obj.setCustomer_name(obj_cheque.getString("customer_name"));
                            cheque_export_obj.setPromoter_name(obj_cheque.getString("promoter_name"));
                            cheque_export_obj.setCheque_bank_name(obj_cheque.getString("cheque_bank_name"));
                            cheque_export_obj.setCheque_payment_amount(obj_cheque.getString("cheque_payment_amount"));
                            cheque_export_obj.setSales_order_number(obj_cheque.getString("sales_order_number"));
                            cheque_export_data.add(cheque_export_obj);
                        }
                    }
                    if (selectionlistpayment.get(i).getSelection_text().equals("Finance")) {
                        JSONArray array = response.getJSONArray("finance_payments");
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject obj_finance = array.getJSONObject(j);
                            finance_export finance_export_obj = new finance_export();
                            finance_export_obj.setCustomer_name(obj_finance.getString("customer_name"));
                            finance_export_obj.setPromoter_name(obj_finance.getString("promoter_name"));
                            finance_export_obj.setSales_order_number(obj_finance.getString("sales_order_number"));
                            finance_export_obj.setFinance_file_number(obj_finance.getString("finance_file_number"));
                            finance_export_obj.setFinance_payment_amount(obj_finance.getString("finance_payment_amount"));
                            finance_export_obj.setFinancer(obj_finance.getString("financer"));
                            finance_export_obj.setProcessing_fees(obj_finance.getString("processing_fees"));
                            finance_export_data.add(finance_export_obj);
                        }
                    }
                    if (selectionlistpayment.get(i).getSelection_text().equals("Paytm")) {
                        JSONArray array = response.getJSONArray("paytm_payments");
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject obj_paytm = array.getJSONObject(j);
                            paytm_export paytm_export_obj = new paytm_export();
                            paytm_export_obj.setCustomer_name(obj_paytm.getString("customer_name"));
                            paytm_export_obj.setPromoter_name(obj_paytm.getString("promoter_name"));
                            paytm_export_obj.setSales_order_number(obj_paytm.getString("sales_order_number"));
                            paytm_export_obj.setPaytm_payment_amount(obj_paytm.getString("paytm_payment_amount"));
                            paytm_export_data.add(paytm_export_obj);
                        }
                    }
                } else {
                    continue;
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        String state = Environment.getExternalStorageState();
        String root = "";
        String fileName = "/payment_export" + System.currentTimeMillis() + ".pdf";
        String parent = "KhuranaSales";
        File mFile;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            root = Environment.getExternalStorageDirectory().toString();
            mFile = new File(root, parent);
            if (!mFile.isDirectory())
                mFile.mkdirs();
        } else {
            root = exportMainActivity.this.getFilesDir().toString();
            mFile = new File(root, parent);
            if (!mFile.isDirectory())
                mFile.mkdirs();
        }
        String strCaptured_FileName = root + "/KhuranaSales" + fileName;
        String targetPdf = strCaptured_FileName;
        File filePath = new File(targetPdf);
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Khurana sales business solutions");
            document.addCreator("Ankush Amit Khurana");

            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            PdfPTable table = new PdfPTable(3);
            table.addCell(getCell("Export Date: " + (new Date().getTime()), PdfPCell.ALIGN_LEFT));
            table.addCell(getCell("Khurana Sales Payments Export", PdfPCell.ALIGN_CENTER));
            table.addCell(getCell("Email: khuranasales2015@gmail.com", PdfPCell.ALIGN_RIGHT));
            table.setWidthPercentage(100);
            table.setSpacingAfter(20);
            document.add(table);
            Font tableFont = FontFactory.getFont(FontFactory.HELVETICA,10,Font.NORMAL);
            ArrayList<selectionPojo> selection_list_payments = payment_type_select_export_batch_recycler_adapter.selection_list;
            for(int i = 0; i < selection_list_payments.size() ; i++)
            {
                if(selection_list_payments.get(i).is_selected())
                {
                    if(selection_list_payments.get(i).getSelection_text().equals("Cash"))
                    {
                        PdfPTable table_cash = new PdfPTable(4);
                        table_cash.addCell(new Paragraph("Cash Payment Amount",tableFont));
                        table_cash.addCell(new Paragraph("Promoter Name",tableFont));
                        table_cash.addCell(new Paragraph("Customer Name",tableFont));
                        table_cash.addCell(new Paragraph("Sales Order Number",tableFont));
                        table_cash.setWidthPercentage(100);
                        table_cash.setSpacingAfter(20);
                        for (cash_export cash_detail : cash_export_data) {
                            table_cash.addCell(new Paragraph(cash_detail.getAmount(),tableFont));
                            table_cash.addCell(new Paragraph(cash_detail.getPromoter_name(),tableFont));
                            table_cash.addCell(new Paragraph(cash_detail.getCustomer_name(),tableFont));
                            table_cash.addCell(new Paragraph(cash_detail.getSales_order_number(),tableFont));
                        }
                        document.add(table_cash);
                    }
                    if(selection_list_payments.get(i).getSelection_text().equals("Card"))
                    {
                        PdfPTable table_card = new PdfPTable(5);
                        table_card.addCell(new Paragraph("Card Payment Amount",tableFont));
                        table_card.addCell(new Paragraph("Card Bank Name",tableFont));
                        table_card.addCell(new Paragraph("Promoter Name",tableFont));
                        table_card.addCell(new Paragraph("Customer Name",tableFont));
                        table_card.addCell(new Paragraph("Sales Order Number",tableFont));
                        table_card.setWidthPercentage(100);
                        table_card.setSpacingAfter(20);
                        for (card_export card_detail : card_export_data) {
                            table_card.addCell(new Paragraph(card_detail.getCard_payment_amount(),tableFont));
                            table_card.addCell(new Paragraph(card_detail.getCard_bank_name(),tableFont));
                            table_card.addCell(new Paragraph(card_detail.getPromoter_name(),tableFont));
                            table_card.addCell(new Paragraph(card_detail.getCustomer_name(),tableFont));
                            table_card.addCell(new Paragraph(card_detail.getSales_order_number(),tableFont));
                        }
                        document.add(table_card);
                    }
                    if(selection_list_payments.get(i).getSelection_text().equals("Cheque"))
                    {
                        PdfPTable table_cheque = new PdfPTable(5);
                        table_cheque.addCell(new Paragraph("Cheque Payment Amount",tableFont));
                        table_cheque.addCell(new Paragraph("Cheque Bank Name",tableFont));
                        table_cheque.addCell(new Paragraph("Promoter Name",tableFont));
                        table_cheque.addCell(new Paragraph("Customer Name",tableFont));
                        table_cheque.addCell(new Paragraph("Sales Order Number",tableFont));
                        table_cheque.setWidthPercentage(100);
                        table_cheque.setSpacingAfter(20);
                        for (cheque_export cheque_detail : cheque_export_data) {
                            table_cheque.addCell(new Paragraph(cheque_detail.getCheque_payment_amount(), tableFont));
                            table_cheque.addCell(new Paragraph(cheque_detail.getCheque_bank_name(),tableFont));
                            table_cheque.addCell(new Paragraph(cheque_detail.getPromoter_name(),tableFont));
                            table_cheque.addCell(new Paragraph(cheque_detail.getCustomer_name(),tableFont));
                            table_cheque.addCell(new Paragraph(cheque_detail.getSales_order_number(),tableFont));
                        }
                        document.add(table_cheque);
                    }
                    if(selection_list_payments.get(i).getSelection_text().equals("Finance"))
                    {
                        PdfPTable table_finance = new PdfPTable(7);
                        table_finance.addCell(new Paragraph("Finance Payment Amount",tableFont));
                        table_finance.addCell(new Paragraph("Financer ",tableFont));
                        table_finance.addCell(new Paragraph("Finance File Number ",tableFont));
                        table_finance.addCell(new Paragraph("Finance ProcessingFees ",tableFont));
                        table_finance.addCell(new Paragraph("Promoter Name",tableFont));
                        table_finance.addCell(new Paragraph("Customer Name",tableFont));
                        table_finance.addCell(new Paragraph("Sales Order Number",tableFont));
                        table_finance.setWidthPercentage(100);
                        table_finance.setSpacingAfter(20);
                        for (finance_export finance_detail : finance_export_data) {
                            table_finance.addCell(new Paragraph(finance_detail.getFinance_payment_amount(), tableFont));
                            table_finance.addCell(new Paragraph(finance_detail.getFinancer(),tableFont));
                            table_finance.addCell(new Paragraph(finance_detail.getFinance_file_number(),tableFont));
                            table_finance.addCell(new Paragraph(finance_detail.getProcessing_fees(),tableFont));
                            table_finance.addCell(new Paragraph(finance_detail.getPromoter_name(),tableFont));
                            table_finance.addCell(new Paragraph(finance_detail.getCustomer_name(),tableFont));
                            table_finance.addCell(new Paragraph(finance_detail.getSales_order_number(),tableFont));

                        }
                        document.add(table_finance);
                    }
                    if(selection_list_payments.get(i).getSelection_text().equals("Paytm"))
                    {
                        PdfPTable table_paytm = new PdfPTable(4);
                        table_paytm.addCell(new Paragraph("Paytm Payment Amount",tableFont));
                        table_paytm.addCell(new Paragraph("Promoter Name",tableFont));
                        table_paytm.addCell(new Paragraph("Customer Name",tableFont));
                        table_paytm.addCell(new Paragraph("Sales Order Number",tableFont));
                        table_paytm.setWidthPercentage(100);
                        table_paytm.setSpacingAfter(20);
                        for (paytm_export paytm_detail : paytm_export_data) {
                            table_paytm.addCell(new Paragraph(paytm_detail.getPaytm_payment_amount(), tableFont));
                            table_paytm.addCell(new Paragraph(paytm_detail.getPromoter_name(),tableFont));
                            table_paytm.addCell(new Paragraph(paytm_detail.getCustomer_name(),tableFont));
                            table_paytm.addCell(new Paragraph(paytm_detail.getSales_order_number(),tableFont));

                        }
                        document.add(table_paytm);
                    }

                }
            }
            document.close();
            Toast.makeText(getApplicationContext(),"Export has been created",Toast.LENGTH_LONG).show();
            if(share)
            {
                Uri uri = Uri.fromFile(filePath);
                Intent sharing = new Intent();
                sharing.setAction(Intent.ACTION_SEND);
                sharing.setType("application/pdf");
                sharing.putExtra(Intent.EXTRA_STREAM, uri);
                this.startActivity(sharing);
                share = false;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        }

}

