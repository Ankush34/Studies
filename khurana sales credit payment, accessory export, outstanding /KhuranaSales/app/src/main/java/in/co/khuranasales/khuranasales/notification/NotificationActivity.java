package in.co.khuranasales.khuranasales.notification;

import android.animation.Animator;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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
import in.co.khuranasales.khuranasales.CategorizeDataActivity;
import in.co.khuranasales.khuranasales.Final_Cart;
import in.co.khuranasales.khuranasales.MainActivity;
import in.co.khuranasales.khuranasales.NotificationUserList;
import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.Product_desc_activity;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.ServiceCenters.service_center_main_activity;
import in.co.khuranasales.khuranasales.SoldItemPromotersActivity;
import in.co.khuranasales.khuranasales.edit_profile.editProfileActivity;
import in.co.khuranasales.khuranasales.exportWorkers.exportMainActivity;
import in.co.khuranasales.khuranasales.provideAccessActivity;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;
import io.fabric.sdk.android.services.concurrency.AsyncTask;

public class NotificationActivity extends AppCompatActivity {

    public DrawerLayout mDrawer;
    public ActionBarDrawerToggle mDrawerToggle;
    private  TextView drawer_name;
    private AppConfig appConfig;
    public Toolbar toolbar;
    public GridLayoutManager layoutManager;
    public RecyclerView recyclerView;
    public NotificationAdapter adapter;
    public ArrayList<Notification> notifications;
    public RelativeLayout notification_view_main;
    public RelativeLayout close_notification_window;
    public View view;
    public RelativeLayout background_layout;
    public int xDelta_new;
    public int yDelta_new;
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
    private TextView textView14;
    private TextView textView15;
    private TextView textView16;
    private TextView textView17;
    private ImageView image_export;
    private ImageView image_promoters;
    private ImageView notify_users;
    private ImageView provide_access_image;

    private TextView recent_count;
    private TextView pending_count;
    private TextView viewed_count;
    public NotificationDatabase notificationDatabase;
    public int recent = 0;
    public int old = 0;
    public int viewed;

    private TextView outstanding_amount;
    private ImageView reload_outstanding;
    private ProgressBar outstanding_amount_loader;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity_layout);

        provide_access_image = (ImageView)findViewById(R.id.provide_access_image);
        image_export = (ImageView)findViewById(R.id.image_export);
        image_promoters = (ImageView)findViewById(R.id.promoters_image);
        notify_users = (ImageView)findViewById(R.id.notify_image);

        recent_count = (TextView)findViewById(R.id.recent_count);
        pending_count = (TextView)findViewById(R.id.pending_count);
        viewed_count = (TextView)findViewById(R.id.viewed_count);
        background_layout = (RelativeLayout)findViewById(R.id.background_layout);
        FadeInAnimator inAnimator = new FadeInAnimator();
        inAnimator.setDuration(400);
        inAnimator.setTarget(background_layout);
        inAnimator.prepare(background_layout);
        inAnimator.addAnimatorListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {  background_layout.setVisibility(View.VISIBLE);}
            @Override public void onAnimationEnd(Animator animation) {}
            @Override public void onAnimationCancel(Animator animation) { }
            @Override public void onAnimationRepeat(Animator animation) { }
        });
         close_notification_window = (RelativeLayout) findViewById(R.id.close_notification_window);
        close_notification_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FadeOutAnimator outAnimator = new FadeOutAnimator();
                outAnimator.setDuration(400);
                outAnimator.setTarget(background_layout);
                outAnimator.prepare(background_layout);
                outAnimator.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override public void onAnimationStart(Animator animation) { }
                    @Override public void onAnimationEnd(Animator animation) { background_layout.setVisibility(View.GONE);               notification_view_main.removeAllViews(); }
                    @Override public void onAnimationCancel(Animator animation) { }
                    @Override public void onAnimationRepeat(Animator animation) { }
                });
                outAnimator.start();
            }
        });
        notification_view_main = (RelativeLayout)findViewById(R.id.notification_view_main);
       notificationDatabase = new NotificationDatabase(getApplicationContext());
        notifications = new ArrayList<>();
        notifications = notificationDatabase.getAllNotifications();
        update_count();
        recent_count.setText(""+notifications.size());
        recyclerViewItemClickListener notification_cards_click_listener = (view, position) -> {
            // fadeIn(batch_selection_layout);
            inAnimator.start();
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.notification_card,null);
            if(notifications.get(position).getStatus().equals("Pending"))
            {
                notificationDatabase.update_status(notifications.get(position),"Viewed");
                notifications.get(position).setStatus("Viewed");
            }
            TextView user_name = v.findViewById(R.id.notification_date);
            user_name.setText("Notification User:   "+appConfig.getUser_name());
            TextView user_email = v.findViewById(R.id.notification_type);
            user_email.setText("Notification email:   "+appConfig.getUser_email().trim());
            user_email.setTextSize(6);
            user_name.setTextSize(6);

            View notification_details = LayoutInflater.from(getApplicationContext()).inflate(R.layout.notification_details,null);
            RelativeLayout.LayoutParams notification_details_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            notification_view_main.addView(v);
            notification_view_main.addView(notification_details);
            notification_view_main.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(NotificationAdapter.width,NotificationAdapter.height);
            v.setLayoutParams(params);
            v.findViewById(R.id.card_notification).setBackgroundColor(getResources().getColor(R.color.color_white));
            v.setX(NotificationAdapter.positions[0]);
            v.setY(NotificationAdapter.positions[1]);
            ((CardView) v.findViewById(R.id.card_notification)).setRadius(20);
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics( dm );

            notification_details_params.setMargins((int)((dm.widthPixels - NotificationAdapter.width*1.6)),(int)(245.0*dm.heightPixels/1920.0)+(int)(NotificationAdapter.height*1.4),(int)((dm.widthPixels - NotificationAdapter.width*1.6)),20);
            notification_details.setLayoutParams(notification_details_params);
            TextView notification_title = notification_details.findViewById(R.id.title);
            notification_title.setText("Notice:   "+notifications.get(position).getTitle());
            TextView notification_message = notification_details.findViewById(R.id.message);
            notification_message.setText(notifications.get(position).getMessage().replace("\n","\n\n"));
            TextView notification_type = notification_details.findViewById(R.id.notificatino_type);
            notification_type.setText(notifications.get(position).getType());
            TextView notification_date = notification_details.findViewById(R.id.notification_date);
            notification_date.setText("Notification Date:   "+notifications.get(position).getDate());

            Button view_details = (Button)findViewById(R.id.submit_details);
            view_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    get_product_and_launch_activity(notifications.get(position).getProduct_id());
                }
            });
            final int xDelta;
            xDelta = (dm.widthPixels - NotificationAdapter.width)/2 - NotificationAdapter.positions[0];
            final double yDelta_double = (dm.heightPixels - (1400.0/1920.0*dm.heightPixels) )/2  + 125.0*dm.heightPixels/1920.0 - NotificationAdapter.positions[1];
            xDelta_new = xDelta;
            yDelta_new = (int)yDelta_double;
            AnimationSet animSet = new AnimationSet(true);
            animSet.setFillAfter(true);
            animSet.setDuration(200);
            animSet.setInterpolator(new LinearInterpolator());
            TranslateAnimation translate = new TranslateAnimation( 0, xDelta , 0, (int)yDelta_double
            );
            animSet.addAnimation(translate);
            ScaleAnimation scale = new ScaleAnimation(1f, 1.4f, 1f, 1.4f, ScaleAnimation.RELATIVE_TO_PARENT, .5f, ScaleAnimation.RELATIVE_TO_PARENT, .5f);
            animSet.addAnimation(scale);
            v.startAnimation(animSet);
            view = v;
            update_count();
          };
        layoutManager = new GridLayoutManager(this,2);
        adapter = new NotificationAdapter(this,notifications,notification_cards_click_listener);
        recyclerView = (RecyclerView)findViewById(R.id.notification_recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        appConfig  = new AppConfig(getApplicationContext());
        drawer_name = (TextView)findViewById(R.id.drawer_name);
        drawer_name.setText(appConfig.getUser_name());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("");
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

        if(!appConfig.getUserType().equals("Admin"))
        {
            textView17.setVisibility(View.GONE);
            provide_access_image.setVisibility(View.GONE);
        }
        /*  */

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
        /*  */
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
                    Intent intent = new Intent(getApplicationContext(),Final_Cart.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please login to proceed",Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart= menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Buy_Now_Activity.class);
                startActivity(intent);
            }
        });
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            finish();
            overridePendingTransition(0,R.anim.slide_out_left_animation);
        }
        return super.onOptionsItemSelected(item);
         }

         public void get_product_and_launch_activity(String product_id)
         {
             Log.d("NotificationActivity","URL: "+AppConfig.load_single_product_by_id+product_id);
             JsonArrayRequest request_product = new JsonArrayRequest(AppConfig.load_single_product_by_id+product_id, new Response.Listener<JSONArray>() {
                 @Override
                 public void onResponse(JSONArray response) {
                     Log.d("True Response", response.toString());
                     for (int i = 0; i < response.length(); i++) {
                         try {
                             JSONObject obj = response.getJSONObject(i);
                             Product pro = new Product();
                             pro.setProduct_id(obj.getInt("product_id"));
                             pro.set_Name(obj.getString("Name"));
                             if (!(obj.getString("stock").equals(null) || obj.getString("stock") == null)) {
                                 pro.set_Stock(Integer.parseInt(obj.getString("stock")));
                             }
                             if (!(obj.getString("mrp").equals(null) || obj.getString("mrp") == null)) {
                                 pro.setPrice_mrp(Integer.parseInt(obj.getString("mrp")));
                             }
                             if (!(obj.getString("mop").equals(null) || obj.getString("mop") == null)) {
                                 pro.setPrice_mop(Integer.parseInt(obj.getString("mop")));
                             }
                             if (!(obj.getString("ksprice").equals(null) || obj.getString("ksprice") == null)) {
                                 pro.setPrice_ks(Integer.parseInt(obj.getString("ksprice")));
                             }
                             pro.set_link(obj.getString("link"));
                             Intent intent = new Intent(getApplicationContext(),Product_desc_activity.class);
                             intent.putExtra("product_id",pro.getProduct_id());
                             intent.putExtra("name",pro.get_Name());
                             intent.putExtra("link",pro.get_link());
                             intent.putExtra("stock",pro.get_Stock());
                             intent.putExtra("mrp",pro.getPrice_mrp());
                             intent.putExtra("mop",pro.getPrice_mop());
                             intent.putExtra("ksprice",pro.getPrice_ks());
                             startActivity(intent);
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                 }
             }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Sorry Please Retry !! ",Toast.LENGTH_SHORT).show();
                 }
             });
             request_product.setRetryPolicy(new DefaultRetryPolicy(
                 DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                 DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                 DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

             AppController.getInstance().addToRequestQueue(request_product);
         }

       public void update_count()
            {
                old = 0;
                viewed = 0;
                for(int i = 0 ; i <notifications.size();i++)
                {

                        if(notifications.get(i).getStatus().equals("Pending"))
                        {
                            old = old+1;
                        }
                        else if(notifications.get(i).getStatus().equals("Viewed"))
                        {
                            viewed  = viewed +1;
                        }
                        viewed_count.setText(""+viewed);
                    pending_count.setText(""+old);
                }
         }

    class load_oustanding_amount extends AsyncTask<Void, Void, Void> {

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
