package in.co.khuranasales.khuranasales.NewLaunches;

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
import com.android.volley.VolleyLog;
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
import in.co.khuranasales.khuranasales.aboutPage.aboutActivity;
import in.co.khuranasales.khuranasales.add_new_product.add_new_product_activity;
import in.co.khuranasales.khuranasales.edit_profile.editProfileActivity;
import in.co.khuranasales.khuranasales.exportWorkers.exportMainActivity;
import in.co.khuranasales.khuranasales.notification.NotificationActivity;
import in.co.khuranasales.khuranasales.provideAccessActivity;

public class newLaunchesActivity extends AppCompatActivity {
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
    private AppConfig appConfig;
    private TextView textView14;
    private TextView textView15;
    private TextView textView16;
    private TextView textView17;
    private TextView add_new_product_khurana_sales;
    private TextView outstanding_amount;
    private ImageView reload_outstanding;
    private ProgressBar outstanding_amount_loader;
    private ImageView provide_access_image;

    public DrawerLayout mDrawer;
    public ActionBarDrawerToggle mDrawerToggle;
    public Toolbar toolbar;

    public RecyclerView new_launch_recycler;
    public newLaunchAdapter adapter_new_launch;
    public LinearLayoutManagerWithSmoothScroller layoutManagerWithSmoothScroller;
    public ArrayList<Product> products;

    private ImageView image_export;
    private ImageView image_promoters;
    private ImageView notify_users;
    private int switching_activity_case = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appConfig = new AppConfig(getApplicationContext());
        products = new ArrayList<>();
        setContentView(R.layout.new_launch_activity_layout);

        provide_access_image = (ImageView) findViewById(R.id.provide_access_image);
        image_export = (ImageView) findViewById(R.id.image_export);
        image_promoters = (ImageView) findViewById(R.id.promoters_image);
        notify_users = (ImageView) findViewById(R.id.notify_image);

        new_launch_recycler = (RecyclerView) findViewById(R.id.new_launch_recycler);
        new_launch_recycler.setHasFixedSize(true);
        layoutManagerWithSmoothScroller = new LinearLayoutManagerWithSmoothScroller(getApplicationContext());
        new_launch_recycler.setLayoutManager(layoutManagerWithSmoothScroller);
        adapter_new_launch = new newLaunchAdapter(products, this);
        new_launch_recycler.setAdapter(adapter_new_launch);
        adapter_new_launch.notifyDataSetChanged();
        new LoadOutbox().execute();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        mDrawer.addDrawerListener(get_drawer_listener());
        mDrawerToggle.syncState();
        set_drawer_listeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_my_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void set_drawer_listeners() {
        textView1 = (TextView) findViewById(R.id.login);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appConfig.isLogin()) {
                    switching_activity_case = -1;
                    mDrawer.closeDrawers();
                    Toast.makeText(getApplicationContext(), "Already Logged In As " + appConfig.getUser_name(), Toast.LENGTH_LONG).show();
                } else {
                    switching_activity_case = 1;
                    mDrawer.closeDrawers();
                }
            }
        });
        textView2 = (TextView) findViewById(R.id.signup);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switching_activity_case = 2;
                mDrawer.closeDrawers();
            }
        });
        textView3 = (TextView) findViewById(R.id.cart);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appConfig.isLogin()) {
                    switching_activity_case = 3;
                    mDrawer.closeDrawers();
                } else {
                    switching_activity_case = -1;
                    Toast.makeText(getApplicationContext(), "Please Login Before Proceeding ...", Toast.LENGTH_LONG).show();
                }
            }
        });
        textView4 = (TextView) findViewById(R.id.acc);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appConfig.isLogin()) {
                    switching_activity_case = 4;
                    mDrawer.closeDrawers();
                } else {
                    switching_activity_case = -1;
                    Toast.makeText(getApplicationContext(), "Please Login Before Proceeding ...", Toast.LENGTH_LONG).show();
                }
            }
        });

        textView5 = (TextView) findViewById(R.id.setPasscode);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switching_activity_case = 5;
                mDrawer.closeDrawers();
                Toast.makeText(getApplicationContext(), "Soon.....", Toast.LENGTH_LONG).show();
            }
        });


        textView6 = (TextView) findViewById(R.id.forgotKey);
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switching_activity_case = 6;
                mDrawer.closeDrawers();
                Toast.makeText(getApplicationContext(), "Soon.....", Toast.LENGTH_LONG).show();
            }
        });


        textView7 = (TextView) findViewById(R.id.help);
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switching_activity_case = 7;
                mDrawer.closeDrawers();
            }
        });


        textView8 = (TextView) findViewById(R.id.share);
        textView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switching_activity_case = 8;
                mDrawer.closeDrawers();
                Toast.makeText(getApplicationContext(), "Soon.....", Toast.LENGTH_LONG).show();
            }
        });

        textView9 = (TextView) findViewById(R.id.contact);
        textView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switching_activity_case = 9;
                mDrawer.closeDrawers();
                Toast.makeText(getApplicationContext(), "Soon.....", Toast.LENGTH_LONG).show();
            }
        });

        textView10 = (TextView) findViewById(R.id.about);
        textView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switching_activity_case = 10;
                mDrawer.closeDrawers();
            }
        });

        textView11 = (TextView) findViewById(R.id.logout);
        textView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switching_activity_case = 11;
                mDrawer.closeDrawers();
                appConfig.setStatus_login(false);
                Toast.makeText(getApplicationContext(), "Logged Out " + appConfig.getUser_name(), Toast.LENGTH_LONG).show();
            }
        });

        textView12 = (TextView) findViewById(R.id.notify);
        if(appConfig.getUserType().equals("Admin"))
        {
            textView12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switching_activity_case = 12;
                    mDrawer.closeDrawers();
                }
            });
        }
        else {
            switching_activity_case = -1;
            textView12.setVisibility(View.GONE);
            notify_users.setVisibility(View.GONE);
        }

        textView13 = (TextView) findViewById(R.id.promoters);
        if(appConfig.getUserType().equals("Admin"))
        {
            textView13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switching_activity_case = 13;
                    mDrawer.closeDrawers();
                }
            });
        }
        else {
            switching_activity_case = -1;
            textView13.setVisibility(View.GONE);
            image_promoters.setVisibility(View.GONE);
        }

        textView15 = (TextView) findViewById(R.id.export);
        textView15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switching_activity_case = 14;
                mDrawer.closeDrawers();
            }
        });


        textView14 = (TextView) findViewById(R.id.notification);
        textView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switching_activity_case = 15;
                mDrawer.closeDrawers();
            }
        });

        textView16 = (TextView) findViewById(R.id.edit_profile);
        textView16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switching_activity_case = 16;
                mDrawer.closeDrawers();
            }
        });

        textView17 = (TextView)findViewById(R.id.provide_access);
        textView17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switching_activity_case = 17;
                mDrawer.closeDrawers();

            }
        });
        add_new_product_khurana_sales = (TextView)findViewById(R.id.add_new_product_khurana_sales_label);
        add_new_product_khurana_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switching_activity_case = 18;
                mDrawer.closeDrawers();
            }
        });
        if(!appConfig.getUserType().equals("Admin"))
        {
            textView17.setVisibility(View.GONE);
            provide_access_image.setVisibility(View.GONE);
        }

        outstanding_amount = (TextView) findViewById(R.id.outstanding_amount);
        outstanding_amount.setText("Outstanding Balance ( "+appConfig.getUser_outstanding()+" /- )");
        outstanding_amount_loader = (ProgressBar) findViewById(R.id.loading_outstanding_amount);
        reload_outstanding = (ImageView) findViewById(R.id.reload_outstanding_amount);

        reload_outstanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new load_oustanding_amount().execute();
            }
        });

        if (!appConfig.getUserType().equals("Admin")) {
            textView17.setVisibility(View.GONE);
            provide_access_image.setVisibility(View.GONE);
        }
        /*  */
    }


    class LoadOutbox extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Final_Cart.items.clear();
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.d("URL:", AppConfig.get_new_launches);
            JsonArrayRequest new_launch_request = new JsonArrayRequest(AppConfig.get_new_launches,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("True Response", response.toString());
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Product pro = new Product();
                                    pro.set_Name(obj.getString("Name"));
                                    pro.set_Stock(obj.getInt("stock"));
                                    pro.setPrice_mop(obj.getInt("mop"));
                                    pro.setPrice_mrp(obj.getInt("mrp"));
                                    pro.setPrice_ks(obj.getInt("ksprice"));
                                    pro.setProduct_id(obj.getInt("product_id"));
                                    pro.set_link(obj.getString("link"));
                                    pro.setLaunch_date(obj.getString("date_of_insert"));
                                    products.add(pro);
                                    adapter_new_launch.notifyDataSetChanged();
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
            new_launch_request.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(new_launch_request);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute() {
            // dismiss the dialog after getting all products


        }
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

    public DrawerLayout.DrawerListener get_drawer_listener()
    {
        return new DrawerLayout.DrawerListener() {
            @Override public void onDrawerSlide(View drawerView, float slideOffset) { }
            @Override public void onDrawerOpened(View drawerView) { }
            @Override
            public void onDrawerClosed(View drawerView) {
                Intent intent;
                switch(switching_activity_case)
                {
                    case 1:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(), Activity_Login.class);
                        startActivity(intent);
                        break;
                    case 2:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(), Buy_Now_Activity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(), Final_Cart.class);
                        startActivity(intent);
                        break;
                    case 5:
                        switching_activity_case = -1;
                        break;
                    case 6:
                        switching_activity_case = -1;
                        break;
                    case 7:
                        intent = new Intent(getApplicationContext(), service_center_main_activity.class);
                        startActivity(intent);
                        switching_activity_case = -1;
                        break;
                    case 8:
                        switching_activity_case = -1;
                        break;
                    case 9:
                        switching_activity_case = -1;
                        break;
                    case 10:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(), aboutActivity.class);
                        startActivity(intent);
                        break;
                    case 11:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(), Activity_Login.class);
                        startActivity(intent);
                        break;
                    case 12:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(), NotificationUserList.class);
                        startActivity(intent);
                        break;
                    case 13:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(), SoldItemPromotersActivity.class);
                        startActivity(intent);
                        break;
                    case 14:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(), exportMainActivity.class);
                        startActivity(intent);
                        break;
                    case 15:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(), NotificationActivity.class);
                        startActivity(intent);
                        break;
                    case 16:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(), editProfileActivity.class);
                        startActivity(intent);
                        break;
                    case 17:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(), provideAccessActivity.class);
                        startActivity(intent);
                        break;
                    case 18:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(),add_new_product_activity.class);
                        startActivity(intent);
                        break;
                }
            }
            @Override
            public void onDrawerStateChanged(int newState) { }
        };
    }

    }
