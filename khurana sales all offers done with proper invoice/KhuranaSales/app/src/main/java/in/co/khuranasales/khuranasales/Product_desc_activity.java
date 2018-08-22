package in.co.khuranasales.khuranasales;

import android.animation.Animator;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;
import in.co.khuranasales.khuranasales.ComboOfferAdapters.combo_offer_recycler_view_adapter;
import in.co.khuranasales.khuranasales.Offers.Offer;
import in.co.khuranasales.khuranasales.Offers.OffersActivity;
import in.co.khuranasales.khuranasales.Offers.OffersAdapter;
import in.co.khuranasales.khuranasales.product_desc_activity_offers_adapter.offers_adapter_product_desc;


public class Product_desc_activity extends AppCompatActivity {

    ScrollerViewPager viewPager;
    public Button b1;
    public Button b2;
    public NumberPicker numpik1;
    public static AppConfig appConfig;
    public NumberPicker numpik2;
    public TextView dsc1;
    public TextView dsc2;
    public TextView dsc3;
    public TextView dsc4;
    public TextView mobile_name;
    public ImageView ks_price_image;
    public TextView ks_price_title;
    public static String arr_link[];
    public FadeInAnimator in_animator;
    public FadeOutAnimator animator;
    public ArrayList<Offer> offers ;
    public offers_adapter_product_desc adapter_offers ;
    public RecyclerView offers_recycler_view;
    private ArrayList<Offer> combo_offers_list = new ArrayList<>();
    private RelativeLayout view_combo_offers_layout;
    private RelativeLayout close_combo_offers_layout;
    private RecyclerView combo_offers_vew_recycler;
    private LinearLayoutManagerWithSmoothScroller linearLayoutManagerWithSmoothScroller;
    private RecyclerView.Adapter combo_offers_recycler_view_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_desc_activity);

        recyclerViewItemClickListener offer_selection_listener = (view, position) -> {
            Toast.makeText(getApplicationContext(),"Clicked: "+position,Toast.LENGTH_SHORT).show();
            String[] data = new String[1];
            data[0] = ""+position;
            if(combo_offers_list.size() == 0)
            {
                new Product_desc_activity.get_products_info().execute(data);
            }
            fadeIn(view_combo_offers_layout);
        };
        view_combo_offers_layout = (RelativeLayout)findViewById(R.id.combo_offer_view_layout);
        close_combo_offers_layout = (RelativeLayout) findViewById(R.id.close_combo_offer_layout);
        combo_offers_vew_recycler = (RecyclerView)findViewById(R.id.offers_recycler);
        combo_offers_recycler_view_adapter = new combo_offer_recycler_view_adapter(combo_offers_list,offer_selection_listener);
        combo_offers_vew_recycler.setAdapter(combo_offers_recycler_view_adapter);
        linearLayoutManagerWithSmoothScroller = new LinearLayoutManagerWithSmoothScroller(getApplicationContext());
        combo_offers_vew_recycler.setLayoutManager(linearLayoutManagerWithSmoothScroller);
        combo_offers_recycler_view_adapter.notifyDataSetChanged();

        close_combo_offers_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(view_combo_offers_layout);
            }
        });

        mobile_name = (TextView)findViewById(R.id.mobile_name);
       ks_price_image = (ImageView)findViewById(R.id.ksprice);
       ks_price_title = (TextView)findViewById(R.id.ksprice_title);
        appConfig = new AppConfig(getApplicationContext());
        viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
       offers_recycler_view = (RecyclerView)findViewById(R.id.recycler_view_offers);
        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        dsc1=(TextView)findViewById(R.id.desc1);
        dsc2=(TextView)findViewById(R.id.desc2);
        dsc3=(TextView)findViewById(R.id.desc3);
        dsc4=(TextView)findViewById(R.id.desc4);
        if(appConfig.getUserType().equals("Admin") || appConfig.getUserType().equals("Dealer"))
        {
         dsc3.setVisibility(View.VISIBLE);
        ks_price_title.setVisibility(View.VISIBLE);
        ks_price_image.setVisibility(View.VISIBLE);
        }
        else
        {
            dsc3.setVisibility(View.GONE);
            ks_price_title.setVisibility(View.GONE);
            ks_price_image.setVisibility(View.GONE);

        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final String name=getIntent().getExtras().getString("name");
        mobile_name.setText(name);
        final int stock = getIntent().getExtras().getInt("stock");
        final String link=getIntent().getExtras().getString("link");
        final int mrp=getIntent().getExtras().getInt("mrp");
        final int mop=getIntent().getExtras().getInt("mop");
        final int ksprice=getIntent().getExtras().getInt("ksprice");
        final int product_id = getIntent().getExtras().getInt("product_id");
        Log.d("product_id",""+product_id);
        dsc1.setText(" "+mrp+"  /-");
        dsc2.setText(" "+mop+"  /-");

        dsc3.setText(" "+ksprice+"  /-");
        dsc4.setText(" "+stock+"  Units");

        arr_link=link.split(",");
        numpik2=(NumberPicker)findViewById(R.id.numpik2);

        numpik2.setMinValue(1);

        numpik2.setMaxValue(stock);
        numpik2.setValue(1);

        List<String> list1=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        for(int a=0;a<arr_link.length;a++)
        {
            list1.add(""+a);
            list2.add(arr_link[a]);
        }
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        PagerModelManager manager = new PagerModelManager();
        manager.addCommonFragment(GuideFragment.class,list2,list1);
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();
        // just set viewPager
        springIndicator.setViewPager(viewPager);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(appConfig.isLogin())
                {
                    final int count_selected = numpik2.getValue();
                    Product p1=new Product(name,count_selected);
                    p1.setProduct_id(product_id);
                    p1.setPrice_ks(ksprice);
                    p1.setPrice_mrp(mrp);
                    p1.setPrice_mop(mop);
                    add_to_cart(p1);
                }
                else
                {
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    Log.d("Insert: ", "Inserting ..");

                    Product p1=new Product(name,numpik2.getValue());
                    Log.d("Product Id", ""+product_id);
                    p1.setProduct_id(product_id);
                    p1.setPrice_ks(ksprice);
                    p1.setPrice_mrp(mrp);
                    p1.setPrice_mop(mop);
                    boolean found=db.check_avail(p1.getProduct_id());
                    if(found==true)
                    {
                        int count=db.get_bought_count(p1.getProduct_id());
                        Log.d("Final: ","product found in cart already: "+count);
                        Log.d("internal cart count :",""+count);
                        final int count_selected=numpik2.getValue();
                        int count_final=count+count_selected;
                        db.set_bought_count(p1,count_final);
                        Log.d("updated the count:",""+count_final);
                    }
                    else
                    {
                        Log.d("Final: ","product not found in cart ");
                        db.addProduct(p1);
                    }
                    Toast.makeText(getApplicationContext(),"Product Successffully Added to your Bag ", Toast.LENGTH_LONG).show();
                    Log.d("Reading: ", "Reading all products..");
                    List<Product> products = db.getAllProducts();

                    for (Product cn : products) {
                        String log = "Name: " + cn.get_Name()+ " ,stock: " + cn.get_Stock()+",ks: " + cn.getPrice_ks();
                        Log.d("Name: ", log);
                    }

                }

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appConfig.isLogin())
                {
                    final int count_selected=numpik2.getValue();
                    Product p1=new Product(name,count_selected);
                    p1.setProduct_id(product_id);
                    p1.setPrice_ks(ksprice);
                    p1.setPrice_mrp(mrp);
                    p1.setPrice_mop(mop);
                    add_to_cart(p1);
                }
                else
                {
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    Log.d("Insert: ", "Inserting ..");

                    Product p1=new Product(name,numpik2.getValue());
                    p1.setProduct_id(product_id);
                    p1.setPrice_ks(ksprice);
                    p1.setPrice_mrp(mrp);
                    p1.setPrice_mop(mop);
                    boolean found=db.check_avail(p1.getProduct_id());
                    if(found==true)
                    {
                        int count=db.get_bought_count(p1.getProduct_id());
                        Log.d("internal cart count :",""+count);
                        Log.d("Final: ","product found in cart already: "+count);
                        final int count_selected=numpik2.getValue();
                        int count_final=count+count_selected;
                        db.set_bought_count(p1,count_final);
                        Log.d("updated the count:",""+count_final);
                    }
                    else
                    {
                        Log.d("Final: ","product not found in cart ");
                        db.addProduct(p1);
                    }
                    Toast.makeText(getApplicationContext(),"Product Successffully Added to your Bag ", Toast.LENGTH_LONG).show();
                    Log.d("Reading: ", "Reading all products..");
                    List<Product> products = db.getAllProducts();

                    for (Product cn : products) {
                        String log = "Name: " + cn.get_Name()+ " ,stock: " + cn.get_Stock()+",ks: " + cn.getPrice_ks();
                        Log.d("Name: ", log);
                    }

                }
                Intent intent=new Intent(getApplicationContext(),Buy_Now_Activity.class);
                startActivity(intent);
            }
        });


        offers = new ArrayList<>();
        offers_recycler_view = (RecyclerView)findViewById(R.id.recycler_view_offers);
        LinearLayoutManagerWithSmoothScroller manageroffers = new LinearLayoutManagerWithSmoothScroller(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        offers_recycler_view.setLayoutManager(manageroffers);
        adapter_offers = new offers_adapter_product_desc(offers,this,offer_selection_listener);
        offers_recycler_view.setAdapter(adapter_offers);
        offers_recycler_view.setHasFixedSize(true);
        adapter_offers.notifyDataSetChanged();
        new Product_desc_activity.load_offers().execute();

    }
public void add_to_cart(Product p1)
    {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);

        String url=AppConfig.url_set_viewed;
        url=url+"?count="+p1.get_Stock()+"&id="+p1.getProduct_id()+"&customer_email="+appConfig.getUser_email().trim()+"&date="+formattedDate;
        Log.d("url",""+url);
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("True Response", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                String msg=obj.getString("success");
                                if (msg.equals("true"))
                                {
                                    Toast.makeText(getApplicationContext(),"Successfully updated product list", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Could not update product list", Toast.LENGTH_LONG).show();

                                }
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
                    Intent intent = new Intent(Product_desc_activity.this,Final_Cart.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Product_desc_activity.this,"Please login to proceed",Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart= menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Product_desc_activity.this,Buy_Now_Activity.class);
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
                Toast.makeText(Product_desc_activity.this,"You are already logged in",Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent = new Intent(Product_desc_activity.this,Activity_Login.class);
                startActivity(intent);
            }
        }else if(id == R.id.signout)
        {
            if(appConfig.isLogin())
            {
                Intent intent = new Intent(Product_desc_activity.this,Activity_Login.class);
                startActivity(intent);

            }else{
                Toast.makeText(Product_desc_activity.this," Please login to signout",Toast.LENGTH_LONG).show();

            }
        }else if(id == R.id.signup)
        {
            Intent intent = new Intent(Product_desc_activity.this,MainActivity.class);
            startActivity(intent);
        }
        else if(id == android.R.id.home)
        {
            finish();
            overridePendingTransition(0,R.anim.slide_out_left_animation);
        }
        return super.onOptionsItemSelected(item);
    }

    class load_offers extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest request = new JsonArrayRequest(AppConfig.url_load_offers_filtered_by_product_id+getIntent().getExtras().getInt("product_id"), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d("OFFERRESPONSE", response.toString());
                    JSONObject object = null;
                    try {
                        object = response.getJSONObject(0);
                        if(object.getString("status").equals("Success"))
                        {
                            JSONArray response_array  = object.getJSONArray("product_info");
                            offers_recycler_view.setVisibility(View.VISIBLE);
                            for(int i = 0; i < response_array.length();i++)
                            {
                                try {
                                    JSONObject product = response_array.getJSONObject(i);
                                    Offer offer = new Offer();
                                    offer.setOffr_producT_id(product.getString("product_id"));
                                    offer.setOffer_title(product.getString("offer_title"));
                                    offer.setOffer_descripiton(product.getString("offer_description"));
                                    offer.setOffer_product_name(product.getString("Name"));
                                    offer.setOffer_product_price(product.getString("mrp"));
                                    offer.setOffer_product_discounted_price(product.getString("ksprice"));
                                    offer.setOffer_product_mop_price(product.getString("mop"));
                                    offer.setOffer_product_image_links(product.getString("link"));
                                    offer.setOffer_product_instock_quantity(product.getString("stock"));
                                    offer.setOffer_type(product.getString("offer_type"));
                                    if(offer.getOffer_type().equals("Super Value Offer"))
                                    {
                                        offer.setItem_count(product.getString("item_count"));
                                        offer.setTotal_discounted_amount(product.getString("total_item_price"));

                                    }else if(offer.getOffer_type().equals("Combo Offer"))
                                    {
                                        offer.setDiscounted_product_ids(product.getString("discounted_products_ids"));
                                        offer.setFree_product_ids(product.getString("free_products_ids"));
                                        offer.setDiscounted_product_prices(product.getString("discounted_product_prices"));
                                    }else if(offer.getOffer_type().equals("Discount Offer"))
                                    {
                                        offer.setDiscount_amount_offeres_single(product.getString("discount_offered"));
                                    }
                                    else if(offer.getOffer_type().equals("Bank Offer"))
                                    {
                                        offer.setEmi_category(product.getString("emi_category"));
                                        offer.setCard_category(product.getString("card_category"));
                                        offer.setPayment_banks(product.getString("payment_banks"));
                                        offer.setDiscount_amount_offeres_single(product.getString("discount_amount"));
                                    }
                                    else if(offer.getOffer_type().equals("Paytm Offer"))
                                    {
                                        offer.setDiscount_amount_offeres_single(product.getString("paytm_discount_amount"));
                                    }
                                    offers.add(offer);
                                    adapter_offers.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Sorry No Offers!",Toast.LENGTH_LONG).show();
                            offers_recycler_view.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        offers_recycler_view.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Could Not Load Offers Please Retry !",Toast.LENGTH_SHORT).show();
                    offers_recycler_view.setVisibility(View.GONE);
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

    class get_products_info extends AsyncTask<String,Void,Void>
    {

        @Override
        protected Void doInBackground(String... data) {
            int position = Integer.parseInt(data[0]);
            String discounted_product_ids = offers.get(position).discounted_product_ids;
            String free_product_ids = offers.get(position).free_product_ids;
            String ids = "";
            if(discounted_product_ids.equals(""))
            {
                ids  = free_product_ids;
            }
            else if(free_product_ids.equals(""))
            {
                ids = discounted_product_ids;
            }
            else
            {
                ids = free_product_ids + ","+discounted_product_ids;
            }
            String[] prices = offers.get(position).discounted_product_prices.split(",");
            JsonArrayRequest request = new JsonArrayRequest(AppConfig.get_product_by_ids_combo_offer+ids, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d("OFFERSACTIVITY",response.toString());
                    int j =0 ;
                    for(int i =0;i<response.length();i++)
                    {
                        try {
                            JSONObject product = response.getJSONObject(i);
                            Offer offer = new Offer();
                            offer.setOffr_producT_id(product.getString("product_id"));
                            offer.setOffer_product_name(product.getString("Name"));
                            offer.setOffer_product_price(product.getString("mrp"));
                            offer.setOffer_product_discounted_price(product.getString("ksprice"));
                            offer.setOffer_product_mop_price(product.getString("mop"));
                            offer.setOffer_product_image_links(product.getString("link"));
                            offer.setOffer_product_instock_quantity(product.getString("stock"));
                            if(discounted_product_ids.contains(product.getString("product_id")))
                            {
                                offer.setLies_in_discount(true);
                                offer.setDiscount_amount_offeres_single(prices[j]);
                                j = j+1;
                            }
                            else if(free_product_ids.contains(product.getString("product_id")))
                            {
                                offer.setLies_in_offers(true);
                                offer.setDiscount_amount_offeres_single(product.getString("mop"));
                            }

                            combo_offers_list.add(offer);
                            combo_offers_recycler_view_adapter.notifyDataSetChanged();
                            // i have added items directly to discounted offers list because
                            // those items thaat are free will be given discount equal to their amount
                            offers.get(position).discounted_products_offers_list.add(offer);
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
            request.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(request);
            return null;
        }
    }
}
