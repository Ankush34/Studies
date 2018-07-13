package in.co.khuranasales.khuranasales;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.co.khuranasales.khuranasales.aboutPage.aboutActivity;
import in.co.khuranasales.khuranasales.notification.NotificationActivity;
import in.co.khuranasales.khuranasales.notification.NotificationDatabase;

public class CategorizeDataActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    public int count = 0;
    public FloatingActionButton fab_watsapp;
    private DrawerLayout mDrawer;
    private ArrayList<String> brands = new ArrayList<>();
    private ActionBarDrawerToggle mDrawerToggle;
    public  ViewPager viewPager;
    public static String type = new String();
    private FadeOutAnimator fadeOutAnimator;
    public static RelativeLayout brand_selection;
    public ImageView brand_selection_next;
    public TextView txtViewCount;
    public RecyclerView brand_selection_recycler;
    public LinearLayoutManager linearLayoutManager;
    public ImageView back_brand_selection;
    public BroadcastReceiver mRegistrationBroadcastReceiver;
    public BrandSelectionCategorizationAdapter adapter;
    public TextView drawer_name;
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
    public ImageView back_search_top;
    public ImageView clear_search_top;
    public String search_string;
    public EditText search_text_top;
    public RecyclerView searched_products_recycler;
    public LinearLayout search_bar;
    public RelativeLayout search_page;
    public ArrayList<Product> searched_products = new ArrayList<>();
    public RecyclerView.Adapter searched_products_adapter;
    public CategoryFragment Mobiles = new CategoryFragment();
    public  CategoryFragment Accessories = new CategoryFragment();
    public ViewPagerAdapter view_pager_adapter ;
    public AppConfig appConfig;
    public ArrayList<String> brands_mobiles = new ArrayList<>();
    public ArrayList<String> brands_accessories = new ArrayList<>();
    public int adapter_position = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        update_firebase_id();
        CategorizeDataActivity.type = "Mobile";
        getBrands();
        setContentView(R.layout.categorize_activity_layout);
        appConfig = new AppConfig(getApplicationContext());
        view_pager_adapter = new ViewPagerAdapter(getSupportFragmentManager());
        drawer_name = (TextView)findViewById(R.id.drawer_name);
        drawer_name.setText(appConfig.getUser_name());
        fab_watsapp = (FloatingActionButton) findViewById(R.id.fab);
        fab_watsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "91+7304123456"; //without '+'
                try {
                    number = number.replace(" ", "").replace("+", "");
                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net");
                    startActivity(sendIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        searched_products_recycler = (RecyclerView) findViewById(R.id.searched_products_recycler);
        searched_products_recycler.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        searched_products_recycler.setLayoutManager(manager);
        searched_products_adapter = new searched_products_adapter(this, searched_products);
        searched_products_recycler.setAdapter(searched_products_adapter);
        searched_products_adapter.notifyDataSetChanged();
        search_text_top = (EditText) findViewById(R.id.search_text_top);
        search_text_top.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { search_products(charSequence.toString()); }
            @Override public void afterTextChanged(Editable editable) { }
        });
        search_bar = (LinearLayout) findViewById(R.id.search_bar_layout_whole);
        search_page = (RelativeLayout) findViewById(R.id.search_recycler_view);
        back_search_top = (ImageView) findViewById(R.id.search_back_top);
        clear_search_top = (ImageView) findViewById(R.id.clear_search_top);
        clear_search_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text_top.setText("");
            }
        });
        back_search_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searched_products.clear();
                searched_products_adapter.notifyDataSetChanged();
                Mobiles.recycler_subcategory.setVisibility(View.VISIBLE); Accessories.recycler_subcategory.setVisibility(View.VISIBLE);
                ScaleAnimation anim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f);
                anim.setDuration(500);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) { }
                    @Override public void onAnimationEnd(Animation animation) {     search_bar.setVisibility(View.INVISIBLE); }
                    @Override public void onAnimationRepeat(Animation animation) { }
                });
                search_bar.startAnimation(anim);
                Animation animation = AnimationUtils.loadAnimation(CategorizeDataActivity.this, R.anim.top_to_bottom);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {search_page.setVisibility(View.INVISIBLE);
                        search_text_top.setText("");
                    }
                    @Override public void onAnimationRepeat(Animation animation) { }
                });
                search_page.startAnimation(animation);

            }
        });

       toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
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
                    Toast.makeText(getApplicationContext(), "Please Login Before Proceedig ...", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), "Please Login Before Proceedig ...", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(getApplicationContext(), aboutActivity.class);
                startActivity(intent);

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
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
                mDrawer.closeDrawers();
            }
        });
        /*  */
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(PushNotificationConfig.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(PushNotificationConfig.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(PushNotificationConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };
        displayFirebaseRegId();
       /* */
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                if(position == 0)
                {
                    adapter_position = 0;
                    CategorizeDataActivity.type  = "Mobiles";
                    getBrands();
                }
                else {
                    adapter_position = 1;
                    CategorizeDataActivity.type = "Accessories";
                    getBrands();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        brand_selection = (RelativeLayout)findViewById(R.id.brand_selection_layout);
        brand_selection.setVisibility(View.INVISIBLE);

        brand_selection_next = (ImageView)findViewById(R.id.brand_selection_next);

        fadeOutAnimator = new FadeOutAnimator();
        fadeOutAnimator.prepare(brand_selection);
        fadeOutAnimator.setDuration(400);
        fadeOutAnimator.setTarget(brand_selection);
        fadeOutAnimator.addAnimatorListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animator) {}
            @Override public void onAnimationEnd(Animator animator) {brand_selection.setVisibility(View.INVISIBLE);}
            @Override public void onAnimationCancel(Animator animator) {}
            @Override public void onAnimationRepeat(Animator animator) {}

        });
        back_brand_selection = (ImageView)findViewById(R.id.back_brand_selection);
        back_brand_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeOutAnimator.start();
            }
        });
        brand_selection_recycler = (RecyclerView)findViewById(R.id.brands_selection_recycler_view);
        brand_selection_recycler.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        brand_selection_recycler.setLayoutManager(linearLayoutManager);
        adapter  = new BrandSelectionCategorizationAdapter(this, brands);
        brand_selection_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        brand_selection_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Product_view_activity.class);
                fadeOutAnimator.start();
                intent.putStringArrayListExtra("selected_brands",adapter.selected_names);
                startActivity(intent);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        Mobiles.subCategories.add(create_sub_category("Buy More Save More","Buy more of these products to add more discount to your cart",R.drawable.buy_more_save_more,"View ->","Mobile"));
        Mobiles.subCategories.add(create_sub_category("Favorites","Add products to favorites to buy them later anyday anytime , Maintain your list helps you for easy buy",R.drawable.favorites,"View My Favorites ->","Mobile"));
        Mobiles.subCategories.add(create_sub_category("Brands","Get a list of all the brands that are avalilable with us to choose between the brands for the product of your choice",R.drawable.brands,"View By Brands ->","Mobile"));
        Mobiles.subCategories.add(create_sub_category("Offers","Get the best offers that are available with us , to get the most out of our selling house",R.drawable.offers, "View My Offers ->","Mobile"));
        Mobiles.subCategories.add(create_sub_category("Best Selling","Fetch the best selling phones in India satisfying the needs of current power, storage ,etc",R.drawable.best_selling, "View BestSelling Products ->","Mobile"));
        Mobiles.subCategories.add(create_sub_category("New Launches","Here is the list of all the new launches in India , grab them as soon as u can !! ",R.drawable.new_launches,"View New Launches ->","Mobile"));
        Mobiles.subCategories.add(create_sub_category("Browse By Features","Here is the list of all the features that you can select for a device, customize your features and search only those phones satisfying your choice",R.drawable.brands,"View By Features ->","Mobile"));
        Accessories.subCategories.add(create_sub_category("Accessories","Here u will find the list of the accessories that are available with us,choose the best accessory among the all brands in the list ",R.drawable.best_selling,"View Accessories","Accessories"));
        Accessories.subCategories.add(create_sub_category("Mobile Chargers","Here u will find the list of the mobile chargers that are available with us,choose the best accessory among the all brands in the list ",R.drawable.charger,"View Chargers","Accessories"));
        Accessories.subCategories.add(create_sub_category("Mobile Earphones","A sorted list of all the  earphones that we have with us , choose your best earphone",R.drawable.earphones,"View Earphones","Accessories"));
        Accessories.subCategories.add(create_sub_category("Phone Covers","Here u will find the list of the phone covers that are available with us,choose the best accessory among the all brands in the list ",R.drawable.best_selling,"View Accessories","Accessories"));
        Accessories.subCategories.add(create_sub_category("DataCable","Here u will find the list of the DataCables that are available with us,choose the best accessory among the all brands in the list ",R.drawable.datacable,"View Accessories","Accessories"));
        Accessories.subCategories.add(create_sub_category("Mobile Bluetooth","A sorted list of all the bluetooth devices that we have with us , choose your accessory",R.drawable.bluetooth,"View Bluetooths","Accessories"));

        Accessories.subCategories.add(create_sub_category("PowerBank","A sorted list of all the PowerBanks that we have with us , choose your best powerbank",R.drawable.powerbank,"View By Brands","Accessories"));
        Accessories.subCategories.add(create_sub_category("ScreenGuard","A sorted list of all the ScreenGuards that we have with us , choose your acessory wise",R.drawable.screenguard,"View By Brands","Accessories"));
        Accessories.subCategories.add(create_sub_category("Speakers","A sorted list of all the speakers that we have with us , choose your speaker with the best configurations at minimum price",R.drawable.speakers,"View By Brands","Accessories"));

        view_pager_adapter.addFragment(Mobiles, "  Mobiles ");
        view_pager_adapter.addFragment(Accessories, "  Accessories ");
        viewPager.setAdapter(view_pager_adapter);

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_categorize_toolbar, menu);

        final View notificaitons = menu.findItem(R.id.actionNotifications).getActionView();
        txtViewCount = (TextView) notificaitons.findViewById(R.id.txtCount);
        NotificationDatabase database = new NotificationDatabase(getApplicationContext());
        txtViewCount.setText(""+database.get_notification_recent_count());
        notificaitons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NotificationActivity.class);
                startActivity(intent);
                //    TODO
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search)
        {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(500);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation animation) {search_bar.setVisibility(View.VISIBLE); }
                @Override public void onAnimationEnd(Animation animation) { }
                @Override public void onAnimationRepeat(Animation animation) { }});
            search_bar.startAnimation(anim);
            Animation animation = AnimationUtils.loadAnimation(CategorizeDataActivity.this, R.anim.item_animation_from_bottom);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation animation) {search_page.setVisibility(View.VISIBLE); }
                @Override public void onAnimationEnd(Animation animation) { Mobiles.recycler_subcategory.setVisibility(View.GONE); Accessories.recycler_subcategory.setVisibility(View.GONE);}
                @Override public void onAnimationRepeat(Animation animation) { } });
            search_page.startAnimation(animation);
        }
        return super.onOptionsItemSelected(item);
    }

    public SubCategory create_sub_category(String name, String description, int url, String categorizing_text,String fragment_name)
    {
        SubCategory subCategory = new SubCategory(name,description,url,categorizing_text,fragment_name);
        return subCategory;

    }
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(PushNotificationConfig.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e("Firebase Registration", "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            Log.e("Firebase Registration", "Firebase reg id: " + regId);

        else
            Log.e("Firebase Registration", "Firebase reg id not recieved: " + regId);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(PushNotificationConfig.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(PushNotificationConfig.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());

        NotificationDatabase database = new NotificationDatabase(getApplicationContext());
        if(txtViewCount != null)
        {
            txtViewCount.setText(""+database.get_notification_recent_count());
        }

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    public void search_products(String search) {
        if (!search.equals("")) {
            searched_products.clear();
            searched_products_adapter.notifyDataSetChanged();
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
            Log.d("Searched: ", "Reference " + AppConfig.url_search + search_string);
            JsonArrayRequest search_request = new JsonArrayRequest(AppConfig.url_search + search_string, new Response.Listener<JSONArray>() {
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
                            searched_products.add(pro);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    searched_products_adapter.notifyDataSetChanged();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error Volley : ", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("search", search_string);
                    return params;
                }
            };
            search_request.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            AppController.getInstance().addToRequestQueue(search_request);
        }
    }

    public void update_firebase_id()
    {
        AppConfig appConfig = new AppConfig(getApplicationContext());

        String url = AppConfig.url_firebase_id+"?token="+appConfig.getFirebaseId()+"&&customer_id="+appConfig.getCustomer_id();
        Log.d("MFInstanceID",url);
        JsonArrayRequest firebase_id_req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject response_object = response.getJSONObject(0);
                    if(response_object.getBoolean("success"))
                    {
                        Toast.makeText(getApplicationContext(),"Successfully Updated For Notifications",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Could Not Update For Notifications",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MyfirebInstanceService",""+error);
            }
        });
        firebase_id_req.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(firebase_id_req); }

    public void getBrands()
    {
        if(adapter_position  == 0)
        {
            if(brands_mobiles.size() <= 0)
            {
                JsonArrayRequest brands_request = new JsonArrayRequest(AppConfig.url_categories+"?category="+CategorizeDataActivity.type, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        AppConfig.Brands.clear();
                        Log.d("True Response", response.toString());
                        AppConfig.Brands.clear();
                        for(int i = 0; i<response.length();i++)
                        {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                AppConfig.Brands.add(object.getString("brand"));
                                brands_mobiles.add(object.getString("brand"));
                                brands.add(object.getString("brand"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error: ",error.toString());

                    }
                });
                brands_request.setRetryPolicy(new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                AppController.getInstance().addToRequestQueue(brands_request);
            }
            else
            {
                brands.clear();
                brands.addAll(brands_mobiles);
                adapter.notifyDataSetChanged();
            }
        }
        if(adapter_position == 1)
        {
            if(brands_accessories.size() <= 0)
            {

                JsonArrayRequest brands_request = new JsonArrayRequest(AppConfig.url_categories+"?category="+CategorizeDataActivity.type, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        AppConfig.Brands.clear();
                        Log.d("True Response", response.toString());
                        AppConfig.Brands.clear();
                        for(int i = 0; i<response.length();i++)
                        {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                AppConfig.Brands.add(object.getString("brand"));
                                brands_accessories.add(object.getString("brand"));
                                brands.add(object.getString("brand"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley Error: ",error.toString());

                    }
                });
                brands_request.setRetryPolicy(new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                AppController.getInstance().addToRequestQueue(brands_request);
            }
            else
            {
             brands.clear();
             brands.addAll(brands_accessories);
             adapter.notifyDataSetChanged();
            }
        }

    }

}
