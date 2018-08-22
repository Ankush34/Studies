package in.co.khuranasales.khuranasales.BestSelling;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.Activity_Login;
import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.Buy_Now_Activity;
import in.co.khuranasales.khuranasales.CategorizeDataActivity;
import in.co.khuranasales.khuranasales.Final_Cart;
import in.co.khuranasales.khuranasales.LinearLayoutManagerWithSmoothScroller;
import in.co.khuranasales.khuranasales.MainActivity;
import in.co.khuranasales.khuranasales.NotificationUserList;
import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.ServiceCenters.service_center_main_activity;
import in.co.khuranasales.khuranasales.SoldItemPromotersActivity;
import in.co.khuranasales.khuranasales.edit_profile.editProfileActivity;
import in.co.khuranasales.khuranasales.exportWorkers.exportMainActivity;
import in.co.khuranasales.khuranasales.notification.NotificationActivity;
import in.co.khuranasales.khuranasales.provideAccessActivity;

public class bestSellingActivity extends AppCompatActivity {
    public DrawerLayout mDrawer;
    public ActionBarDrawerToggle mDrawerToggle;
    public Toolbar toolbar;
    public RecyclerView recycler_best_selling;
    public bestSellingAdapter bestSellingAdapter;
    public LinearLayoutManagerWithSmoothScroller layoutManagerWithSmoothScroller;
    public ArrayList<Product> products = new ArrayList<Product>();
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
    private TextView textView15;
    private TextView textView16;
    private TextView textView17;

    private TextView outstanding_amount;
    private ImageView reload_outstanding;
    private ProgressBar outstanding_amount_loader;
    private ImageView image_export;
    private ImageView image_promoters;
    private ImageView notify_users;

    private ImageView provide_access_image;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.best_selling_activity_layout);

        image_export = (ImageView)findViewById(R.id.image_export);
        image_promoters = (ImageView)findViewById(R.id.promoters_image);
        notify_users = (ImageView)findViewById(R.id.notify_image);
        provide_access_image = (ImageView)findViewById(R.id.provide_access_image);

        appConfig = new AppConfig(getApplicationContext());
        recycler_best_selling = (RecyclerView)findViewById(R.id.best_selling_recycler);
        bestSellingAdapter = new bestSellingAdapter(products,this);
        recycler_best_selling.setHasFixedSize(true);
        layoutManagerWithSmoothScroller = new LinearLayoutManagerWithSmoothScroller(getApplicationContext());
        recycler_best_selling.setLayoutManager(layoutManagerWithSmoothScroller);
        recycler_best_selling.setAdapter(bestSellingAdapter);
        bestSellingAdapter.notifyDataSetChanged();
        new load_best_sellings().execute();

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
    protected void onResume() {
        super.onResume();
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
                    Intent intent = new Intent(bestSellingActivity.this,Final_Cart.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(bestSellingActivity.this,"Please login to proceed",Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart= menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bestSellingActivity.this,Buy_Now_Activity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    class load_best_sellings extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest load_best_sellings_request = new JsonArrayRequest(AppConfig.get_best_sellings, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i =0;i<response.length();i++)
                    {
                        try {
                            JSONObject product_object = response.getJSONObject(i);
                            Product pro = new Product();
                            pro.set_Name(product_object.getString("Name"));
                            pro.setProduct_id(product_object.getInt("product_id"));
                            pro.set_Stock(product_object.getInt("stock"));
                            pro.setPrice_mrp(product_object.getInt("mrp"));
                            pro.setPrice_mop(product_object.getInt("mop"));
                            pro.setPrice_ks(product_object.getInt("ksprice"));
                            pro.set_link(product_object.getString("link"));
                            pro.setSold_count(product_object.getInt("sold_count"));
                            products.add(pro);
                            bestSellingAdapter.notifyDataSetChanged();
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
            load_best_sellings_request.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(load_best_sellings_request);
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
                Intent intent = new Intent(getApplicationContext(), service_center_main_activity.class);
                startActivity(intent);
                mDrawer.closeDrawers();

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
        if(appConfig.getUserType().equals("Admin"))
        {
            textView12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawer.closeDrawers();
                    Intent intent = new Intent(getApplicationContext(), NotificationUserList.class);
                    startActivity(intent);
                }
            });
        }
        else {
            textView12.setVisibility(View.GONE);
            notify_users.setVisibility(View.GONE);
        }

        textView13 = (TextView) findViewById(R.id.promoters);
        if(appConfig.getUserType().equals("Admin"))
        {
            textView13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawer.closeDrawers();
                    Intent intent = new Intent(getApplicationContext(), SoldItemPromotersActivity.class);
                    startActivity(intent);
                }
            });
        }
        else {
            textView13.setVisibility(View.GONE);
            image_promoters.setVisibility(View.GONE);
        }

        textView15 = (TextView) findViewById(R.id.export);
        if(appConfig.getUserType().equals("Admin"))
        {
            textView15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), exportMainActivity.class);
                    startActivity(intent);
                    mDrawer.closeDrawers();
                }
            });
        }
        else
        {
            image_export.setVisibility(View.GONE);
            textView15.setVisibility(View.GONE);
        }

        textView14 = (TextView) findViewById(R.id.notification);
        textView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        textView16 = (TextView) findViewById(R.id.edit_profile);
        textView16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), editProfileActivity.class);
                startActivity(intent);
                mDrawer.closeDrawers();
            }
        });

        textView17 = (TextView)findViewById(R.id.provide_access);
        textView17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), provideAccessActivity.class);
                startActivity(intent);
                mDrawer.closeDrawers();

            }
        });

        outstanding_amount = (TextView)findViewById(R.id.outstanding_amount);
        outstanding_amount.setText("Outstanding Balance( "+appConfig.getUser_outstanding()+" /- )");
        outstanding_amount_loader = (ProgressBar)findViewById(R.id.loading_outstanding_amount);
        reload_outstanding = (ImageView)findViewById(R.id.reload_outstanding_amount);

        reload_outstanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new load_oustanding_amount().execute();
            }
        });

        if(!appConfig.getUserType().equals("Admin"))
        {
            textView17.setVisibility(View.GONE);
            provide_access_image.setVisibility(View.GONE);
        }
        /*  */

    }
    class load_oustanding_amount extends io.fabric.sdk.android.services.concurrency.AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    outstanding_amount_loader.setVisibility(View.VISIBLE);
                    reload_outstanding.setVisibility(View.INVISIBLE);
                }
            });
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest load_outstanding = new JsonArrayRequest(AppConfig.load_outstanding_amount + appConfig.getUser_email(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d("Response", response.toString());
                    try {
                        JSONObject object_outstanding = response.getJSONObject(0);
                        outstanding_amount.setText("Outstanding Balance ( " + object_outstanding.getString("outstanding_amount") + "/- )");
                        appConfig.setUser_outstanding(object_outstanding.getString("outstanding_amount"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Please Retry !", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error took place please retry!", Toast.LENGTH_SHORT).show();
                }
            });
            AppController.getInstance().addToRequestQueue(load_outstanding);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    outstanding_amount_loader.setVisibility(View.INVISIBLE);
                    reload_outstanding.setVisibility(View.VISIBLE);
                }
            });
        }
    }


}
