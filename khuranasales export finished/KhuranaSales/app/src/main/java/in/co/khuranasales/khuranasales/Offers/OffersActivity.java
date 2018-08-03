package in.co.khuranasales.khuranasales.Offers;

import android.animation.Animator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.Activity_Login;
import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.Buy_Now_Activity;
import in.co.khuranasales.khuranasales.ComboOfferAdapters.combo_offer_recycler_view_adapter;
import in.co.khuranasales.khuranasales.Final_Cart;
import in.co.khuranasales.khuranasales.LinearLayoutManagerWithSmoothScroller;
import in.co.khuranasales.khuranasales.MainActivity;
import in.co.khuranasales.khuranasales.NotificationUserList;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.SoldItemPromotersActivity;
import in.co.khuranasales.khuranasales.notification.NotificationActivity;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class OffersActivity extends AppCompatActivity {
    public DrawerLayout mDrawer;
    public ActionBarDrawerToggle mDrawerToggle;
    public Toolbar toolbar;
    public ArrayList<Offer> offers ;
    public RecyclerView offers_recycler ;
    public OffersAdapter adapter_offers ;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;
    private TextView textView9;
    private TextView textView10;
    private TextView textView11;
    private TextView textView12;
    private TextView textView13;
    private  AppConfig appConfig;
    private TextView textView14;
    private FadeOutAnimator animator;
    private FadeInAnimator in_animator;

    private RelativeLayout view_combo_offers_layout;
    private RelativeLayout close_combo_offers_layout;
    private RecyclerView combo_offers_vew_recycler;
    private RecyclerView.Adapter combo_offers_recycler_view_adapter;
    private LinearLayoutManagerWithSmoothScroller linearLayoutManagerWithSmoothScroller;
    private ArrayList<Offer> combo_offers_list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_activity);
        view_combo_offers_layout = (RelativeLayout)findViewById(R.id.combo_offer_view_layout);
        close_combo_offers_layout = (RelativeLayout) findViewById(R.id.close_combo_offer_layout);
        combo_offers_vew_recycler = (RecyclerView)findViewById(R.id.offers_recycler);
        recyclerViewItemClickListener offer_selection_listener = (view, position) -> {
            Toast.makeText(getApplicationContext(),"Clicked: "+position,Toast.LENGTH_SHORT).show();
            String[] data = new String[1];
            data[0] = ""+position;
            if(combo_offers_list.size() == 0)
            {
                new get_products_info().execute(data);
            }
            fadeIn(view_combo_offers_layout);
        };
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
        appConfig = new AppConfig(getApplicationContext());
        offers = new ArrayList<>();
        offers_recycler = (RecyclerView)findViewById(R.id.recycler_offers);
        LinearLayoutManagerWithSmoothScroller manager = new LinearLayoutManagerWithSmoothScroller(getApplicationContext());
        offers_recycler.setLayoutManager(manager);
        adapter_offers = new OffersAdapter(offers,this,offer_selection_listener);
        offers_recycler.setAdapter(adapter_offers);
        offers_recycler.setHasFixedSize(true);
        adapter_offers.notifyDataSetChanged();
        new load_offers().execute();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("Khurana Sales");
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        set_drawer_listeners();
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
                    Intent intent = new Intent(OffersActivity.this,Final_Cart.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(OffersActivity.this,"Please login to proceed",Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart= menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OffersActivity.this,Buy_Now_Activity.class);
                startActivity(intent);
            }
        });

        return true;

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    class load_offers extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest request = new JsonArrayRequest(AppConfig.get_offers, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject object = null;
                    try {
                        object = response.getJSONObject(0);
                        if(object.getString("status").equals("Success"))
                        {
                            JSONArray response_array  = object.getJSONArray("product_info");
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
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Could Not Load Offers Please Retry !",Toast.LENGTH_SHORT).show();
                }
            });
            AppController.getInstance().addToRequestQueue(request);
            return null;
        }
    }

    public void set_drawer_listeners()
    {
        textView1 = (TextView) findViewById(R.id.login);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appConfig.isLogin()) {
                    mDrawer.closeDrawers();
                    Toast.makeText(getApplicationContext(), "Already Logged In As " + appConfig.getUser_name(), Toast.LENGTH_LONG).show();
                } else {
                    mDrawer.closeDrawers();
                    Intent intent = new Intent(getApplicationContext(), Activity_Login.class);
                    startActivity(intent);

                }
            }
        });
        textView2 = (TextView) findViewById(R.id.signup);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        textView3 = (TextView) findViewById(R.id.cart);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appConfig.isLogin()) {
                    mDrawer.closeDrawers();
                    Intent intent = new Intent(getApplicationContext(), Buy_Now_Activity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Login Before Proceeding ...", Toast.LENGTH_LONG).show();
                }
            }
        });
        textView4 = (TextView) findViewById(R.id.acc);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appConfig.isLogin()) {
                    mDrawer.closeDrawers();
                    Intent intent = new Intent(getApplicationContext(), Final_Cart.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Login Before Proceeding ...", Toast.LENGTH_LONG).show();
                }
            }
        });

        textView5 = (TextView) findViewById(R.id.setPasscode);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                Toast.makeText(getApplicationContext(), "Soon.....", Toast.LENGTH_LONG).show();
            }
        });


        textView6 = (TextView) findViewById(R.id.forgotKey);
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                Toast.makeText(getApplicationContext(), "Soon.....", Toast.LENGTH_LONG).show();
            }
        });


        textView7 = (TextView) findViewById(R.id.help);
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                Toast.makeText(getApplicationContext(), "Soon.....", Toast.LENGTH_LONG).show();
            }
        });


        textView8 = (TextView) findViewById(R.id.share);
        textView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                Toast.makeText(getApplicationContext(), "Soon.....", Toast.LENGTH_LONG).show();
            }
        });

        textView9 = (TextView) findViewById(R.id.contact);
        textView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                Toast.makeText(getApplicationContext(), "Soon.....", Toast.LENGTH_LONG).show();
            }
        });

        textView10 = (TextView) findViewById(R.id.about);
        textView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                Toast.makeText(getApplicationContext(), "Soon.....", Toast.LENGTH_LONG).show();
            }
        });

        textView11 = (TextView) findViewById(R.id.logout);
        textView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                appConfig.setStatus_login(false);
                Toast.makeText(getApplicationContext(), "Logged Out " + appConfig.getUser_name(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Activity_Login.class);
                startActivity(intent);
            }
        });
        textView12 = (TextView) findViewById(R.id.notify);
        textView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                Intent intent = new Intent(getApplicationContext(), NotificationUserList.class);
                startActivity(intent);
            }
        });
        textView13 = (TextView) findViewById(R.id.promoters);
        textView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                Intent intent = new Intent(getApplicationContext(), SoldItemPromotersActivity.class);
                startActivity(intent);
            }
        });
        textView14 = (TextView) findViewById(R.id.notification);
        textView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });
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
            String ids  = discounted_product_ids+","+free_product_ids;
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
                                offer.setDiscount_amount_offeres_single(product.getString("mrp"));
                            }

                            combo_offers_list.add(offer);
                            combo_offers_recycler_view_adapter.notifyDataSetChanged();
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
            AppController.getInstance().addToRequestQueue(request);
            return null;
        }
    }
}
