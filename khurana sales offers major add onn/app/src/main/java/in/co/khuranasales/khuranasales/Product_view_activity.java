package in.co.khuranasales.khuranasales;

import android.animation.Animator;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.crashlytics.android.Crashlytics;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.co.khuranasales.khuranasales.ComboOfferAdapters.search_product_discount_free_recycler_adapter;
import in.co.khuranasales.khuranasales.ComboOfferAdapters.selected_discount_items_recycler;
import in.co.khuranasales.khuranasales.ComboOfferAdapters.selected_free_items_adapter;
import in.co.khuranasales.khuranasales.notification.NotificationActivity;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.concurrency.AsyncTask;

public class Product_view_activity extends AppCompatActivity implements recyclerViewItemClickListener {

    private MaterialViewPager mViewPager;
    public EditText search_text;
    public int previous_recycler_position = 0;
    public RecyclerView brand_products_list_names;
    public static Boolean scrolled = false;
    public ImageView back_search_top;
    public ImageView clear_search_top;
    public String search_string;
    public EditText search_text_top;
    public AppConfig appConfig;
    public RecyclerView searched_products_recycler;
    public LinearLayout search_bar;
    public Boolean performed_search = false;
    public RelativeLayout search_page;
    public ArrayList<Product> searched_products = new ArrayList<>();
    public ArrayList<RecyclerViewFragment> fragments = new ArrayList<>();
    public int[] colors = {R.color.blue, R.color.red, R.color.cyan};
    public ImageView access_grating_lock;
    public ArrayList<ProductListElement> currentArrayList = new ArrayList<ProductListElement>();
    public boolean list_copied = false;
    public ArrayList<ProductListElement> product_names = new ArrayList<>();
    public RelativeLayout search_bar_layout;
    public RecyclerView.Adapter searched_products_adapter;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    public TextView textView1;
    public FloatingActionButton fab_watsapp;
    public FloatingActionButton fab_search;
    public TextView textView2;
    public boolean button_hidden;
    public TextView textView3;
    public int page_selected;
    public TextView textView4;
    public TextView product_count;
    public ImageView image1;
    public ImageView image2, image3;
    private Toolbar toolbar;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    public  Boolean search_layout_visible = false;
    private TextView textView8;
    private TextView textView9;
    private TextView textView10;
    private TextView textView11;
    private TextView textView12;
    private TextView textView13;
    private TextView textView14;
    private TextView product_selection_text;
    private ImageView back_product_name_selection;
    private ArrayList<String> selected_brands;
    public TextView drawer_name;
    public PromoterListAdapter adapter;
    public RelativeLayout layout_offer_renderable;

    public static RelativeLayout new_offer_layout;
    public Spinner select_offer_type;
    public CustomSpinnerAdapter custom_spinner_adapter;
    public ArrayList<String> offers = new ArrayList<>();
    public ArrayList<Integer> views_offers_renderable= new ArrayList<>();
    public RecyclerView search_products_discount;
    public RecyclerView search_products_free;
    public EditText search_products_discount_text;
    public EditText search_products_free_text;
    public RecyclerView discounted_products_recycler;
    public RecyclerView free_products_recycler;
    public RecyclerView.Adapter search_discount_products_adapter;
    public RecyclerView.Adapter search_free_products_adapter;
    public RecyclerView.Adapter selected_discount_items_adapter;
    public RecyclerView.Adapter selected_free_items_adapter;
    public TextView nothing_found_text_discount;
    public TextView nothing_foung_text_free;
    public ArrayList<Product> searched_products_discount_list = new ArrayList<>();
    public ArrayList<Product> searched_products_free_list = new ArrayList<>();
    public ArrayList<Product> discounted_products_list = new ArrayList<>();
    public ArrayList<Product> free_products_list = new ArrayList<>();
    public CardView search_free_items_card_view;
    public CardView search_discount_items_card_view;
    public ImageView discount_items_add;
    public ImageView free_items_add;
    public Product selected_discount_product;
    public Product selected_free_product;
    public int selected_offer_position;

    public EditText discount_offer_amount;

    public EditText special_value_offer_item_count;
    public EditText special_value_offer_discount_amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        new_offer_layout = (RelativeLayout)findViewById(R.id.new_offer_layout);
        new_offer_layout.setVisibility(View.GONE);
        select_offer_type = (Spinner)findViewById(R.id.spinner_select_offer_type);
        offers.add("Combo Offer");
        offers.add("Discount Offer");
        offers.add("Super Value Offer");
        views_offers_renderable.add(R.layout.combo_offer_layout);
        views_offers_renderable.add(R.layout.discount_offer_layout);
        views_offers_renderable.add(R.layout.super_value_offer_layout);

        custom_spinner_adapter = new CustomSpinnerAdapter(this,offers);
        select_offer_type.setAdapter(custom_spinner_adapter);

        select_offer_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                layout_offer_renderable.removeAllViews();
                getLayoutInflater().inflate(views_offers_renderable.get(position),layout_offer_renderable);
                selected_offer_position = position;
             if(position == 0)
             {
                 recyclerViewItemClickListener product_selection_listener = (view1, position1) -> {
                     Toast.makeText(getApplicationContext(),"Clicked: "+position1,Toast.LENGTH_SHORT).show();
                    selected_discount_product = searched_products_discount_list.get(position1);
                    search_products_discount_text.setText(selected_discount_product.get_Name());
                    search_discount_items_card_view.setVisibility(View.GONE);

                 };

                 recyclerViewItemClickListener product_selection_listener_free = (view1, position1) -> {
                     Toast.makeText(getApplicationContext(),"Clicked: "+position1,Toast.LENGTH_SHORT).show();
                     selected_free_product = searched_products_free_list.get(position1);
                     search_products_free_text.setText(selected_free_product.get_Name());
                     search_free_items_card_view.setVisibility(View.GONE);
                 };

                 recyclerViewItemClickListener product_click_listener = (view1, position1) -> {
                     discounted_products_list.remove(position1);
                     selected_discount_items_adapter.notifyDataSetChanged();
                     if(discounted_products_list.size()==0)
                     {
                         discounted_products_recycler.setVisibility(View.GONE);
                         nothing_found_text_discount.setVisibility(View.VISIBLE);
                     }
                 };

                 recyclerViewItemClickListener product_click_listener_free = (view1, position1) -> {
                    free_products_list.remove(position1);
                    selected_free_items_adapter.notifyDataSetChanged();
                    if(free_products_list.size()==0)
                    {
                        free_products_recycler.setVisibility(View.GONE);
                        nothing_foung_text_free.setVisibility(View.VISIBLE);

                    }
                 };
                 discount_items_add = (ImageView)findViewById(R.id.add_items_discount);
                 free_items_add = (ImageView)findViewById(R.id.add_items_free);
                 discount_items_add.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         discounted_products_recycler.setVisibility(View.VISIBLE);
                         discounted_products_list.add(selected_discount_product);
                         selected_discount_items_adapter.notifyDataSetChanged();
                     }
                 });
                 free_items_add.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         free_products_recycler.setVisibility(View.VISIBLE);
                         free_products_list.add(selected_free_product);
                         selected_free_items_adapter.notifyDataSetChanged();
                     }
                 });
                 search_free_items_card_view = (CardView)findViewById(R.id.search_free_items_cardview);
                 search_discount_items_card_view = (CardView)findViewById(R.id.search_discount_items_cardview);

                 nothing_found_text_discount = (TextView)findViewById(R.id.nothing_found_text);
                 nothing_foung_text_free = (TextView)findViewById(R.id.nothing_free_found_text);

                 search_products_discount = (RecyclerView)findViewById(R.id.search_products_discount);
                 search_products_free = (RecyclerView)findViewById(R.id.search_products_free);

                 search_products_free_text = (EditText)findViewById(R.id.free_products_select_text);
                 search_products_discount_text = (EditText)findViewById(R.id.discount_products_select_text);

                 discounted_products_recycler = (RecyclerView)findViewById(R.id.selected_items_discount_recycler);
                 selected_discount_items_adapter  = new selected_discount_items_recycler(discounted_products_list, product_click_listener);
                 discounted_products_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                 discounted_products_recycler.setAdapter(selected_discount_items_adapter);
                 selected_discount_items_adapter.notifyDataSetChanged();

                 free_products_recycler = (RecyclerView)findViewById(R.id.selected_items_free_recycler);
                 selected_free_items_adapter = new selected_free_items_adapter(free_products_list,product_click_listener_free);
                 free_products_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                 free_products_recycler.setAdapter(selected_free_items_adapter);
                 selected_free_items_adapter.notifyDataSetChanged();

                 search_discount_products_adapter = new search_product_discount_free_recycler_adapter(searched_products_discount_list,product_selection_listener);
                 search_products_discount.setAdapter(search_discount_products_adapter);
                 search_products_discount.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                 search_discount_products_adapter.notifyDataSetChanged();

                 search_free_products_adapter = new search_product_discount_free_recycler_adapter(searched_products_free_list,product_selection_listener_free);
                 search_products_free.setAdapter(search_free_products_adapter);
                 search_products_free.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                 search_free_products_adapter.notifyDataSetChanged();

                 search_products_discount_text.addTextChangedListener(new TextWatcher() {
                     @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                     @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                         if (s.toString().equals("")) {
                            search_discount_items_card_view.setVisibility(View.GONE);
                             nothing_found_text_discount.setVisibility(View.VISIBLE);
                         }else
                        {
                            Log.d("PRODUCTVIEWACTIVITY",s.toString());
                            search_discount_items_card_view.setVisibility(View.VISIBLE);
                            nothing_found_text_discount.setVisibility(View.GONE);
                            search_offer_products(s.toString(),"discount");
                        }
                     }
                     @Override public void afterTextChanged(Editable s) { }
                 });
                 search_products_free_text.addTextChangedListener(new TextWatcher() {
                     @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                     @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                         if (s.toString().equals("")) {
                             search_free_items_card_view.setVisibility(View.GONE);
                             nothing_foung_text_free.setVisibility(View.VISIBLE);
                         }else
                         {
                             Log.d("PRODUCTVIEWACTIVITY",s.toString());
                             search_free_items_card_view.setVisibility(View.VISIBLE);
                             nothing_foung_text_free.setVisibility(View.GONE);
                             search_offer_products(s.toString(),"free");
                         }
                     }
                     @Override public void afterTextChanged(Editable s) { }
                 });

             }
             else if(position == 1)
             {
                discount_offer_amount  = (EditText)findViewById(R.id.discount_offer_amount);
             }
             else if(position == 2)
             {
                special_value_offer_discount_amount = (EditText)findViewById(R.id.special_value_offer_price);
                special_value_offer_item_count = (EditText)findViewById(R.id.special_value_offer_item_count);
             }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
        layout_offer_renderable = (RelativeLayout)findViewById(R.id.layout_offer_renderable);

        appConfig = new AppConfig(getApplicationContext());
        product_selection_text = (TextView) findViewById(R.id.brand_selection_text);
        back_product_name_selection = (ImageView)findViewById(R.id.back_product_name_selection);

        if (this.getIntent().hasExtra("selected_brands")) {
            selected_brands = this.getIntent().getStringArrayListExtra("selected_brands");
            Log.d("Recieved Brands List: ", "Size: " + selected_brands.size());
        }

        recyclerViewItemClickListener product_add_to_offer__click = (view, position) -> {
            Toast.makeText(getApplicationContext(),"Clicked: "+position,Toast.LENGTH_SHORT).show();
            fade_in(Product_view_activity.new_offer_layout);
            EditText title = (EditText)findViewById(R.id.write_offer_product_title);
            EditText description = (EditText)findViewById(R.id.write_offer_product_description);
            Button upload_offer = (Button)findViewById(R.id.upload_offer);
            ImageView back_write_offer = (ImageView) findViewById(R.id.back_write_offer);
            back_write_offer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fade_out(new_offer_layout);
                }
            });
            upload_offer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data[]  =new String[4];
                    data[0] = ""+fragments.get(page_selected).mContentItems.get(position-2).getProduct_id();
                    data[1] = title.getText().toString();
                    data[2] = description.getText().toString();
                    data[3] = offers.get(selected_offer_position);
                    new add_to_offers().execute(data);
                    fade_out(Product_view_activity.new_offer_layout);
                }
            });


        };

        if(!getIntent().hasExtra("Filter"))
        {
            if (selected_brands.size() != 0) {
                for (int i = 0; i < selected_brands.size(); i++) {
                    RecyclerViewFragment rvf = new RecyclerViewFragment();
                    rvf.brand = selected_brands.get(i);
                    rvf.mlistener = product_add_to_offer__click;
                    fragments.add(rvf);
                }
            } else{
                for (int i = 0; i < AppConfig.Brands.size(); i++) {
                    RecyclerViewFragment rvf = new RecyclerViewFragment();
                    rvf.brand = AppConfig.Brands.get(i);
                    rvf.mlistener = product_add_to_offer__click;
                    fragments.add(rvf);
                }
            }
        }
        else
        {
            RecyclerViewFragment rvf = new RecyclerViewFragment();
            rvf.filter = "Favorites";
            rvf.mlistener = product_add_to_offer__click;
            rvf.brand = "Favorites";
            fragments.add(rvf);
        }

        brand_products_list_names = (RecyclerView)findViewById(R.id.brand_products_list);
        brand_products_list_names.setHasFixedSize(true);
        brand_products_list_names.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getApplicationContext()));
        //using promoter list adapter bcz it has the same layout
        // that is just a textview , so using this adapter no need to create a new adapter
        adapter = new PromoterListAdapter(this,product_names,0);
        adapter.setClickListener(this);
        brand_products_list_names.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        drawer_name = (TextView) findViewById(R.id.drawer_name);
        drawer_name.setText(appConfig.getUser_name());
        searched_products_recycler = (RecyclerView) findViewById(R.id.searched_products_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        searched_products_recycler.hasFixedSize();
        searched_products_recycler.setLayoutManager(layoutManager);
        searched_products_adapter = new searched_products_adapter(this, searched_products);
        searched_products_recycler.setAdapter(searched_products_adapter);
        searched_products_adapter.notifyDataSetChanged();

        search_text_top = (EditText) findViewById(R.id.search_text_top);
        search_text_top.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search_products(charSequence.toString()); }
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
        access_grating_lock = (ImageView) findViewById(R.id.access_grant_icon);
        if (appConfig.getUserType().equals("Admin")) {
            access_grating_lock.setVisibility(View.VISIBLE);
        }
        access_grating_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Product_view_activity.this, provideAccessActivity.class);
                startActivity(intent);
            }
        });
        search_text = (EditText) findViewById(R.id.search_text);
        search_text.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("Edit Text Changed: ", "" + charSequence);
                performed_search = true;
                filter_data(charSequence.toString()); }
            @Override  public void afterTextChanged(Editable editable) { }
        });
        fab_watsapp = (FloatingActionButton) findViewById(R.id.fab);
        fab_search = (FloatingActionButton) findViewById(R.id.fab_search);
        product_count = (TextView) findViewById(R.id.item_count);
        product_count.setText(""+AppConfig.product_count);
        check_products_availability();
        image2 = (ImageView) findViewById(R.id.shopping_bag);
        image1 = (ImageView) findViewById(R.id.search_icon);
        image3 = (ImageView) findViewById(R.id.user);
        search_bar_layout = (RelativeLayout) findViewById(R.id.search_bar_layout_new);
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
        final Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setDuration(700);
        fab_search.setOnClickListener(new View.OnClickListener() {
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
                    @Override public void onAnimationStart(Animation animation) { search_bar_layout.setVisibility(View.VISIBLE); }
                    @Override public void onAnimationEnd(Animation animation) { }
                    @Override public void onAnimationRepeat(Animation animation) { } });
                search_bar_layout.startAnimation(scaleAnim);
                adapter.productListElements = Product.load_names(fragments.get(page_selected).mContentItems);
                        adapter.notifyDataSetChanged();
                search_layout_visible = true;
            }
        });
        back_product_name_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleAnimation scaleAnim = new ScaleAnimation(
                        1f, 0f,
                        1f, 0f,
                        Animation.RELATIVE_TO_SELF,1.0f,
                        Animation.RELATIVE_TO_SELF , 1.0f);
                scaleAnim.setDuration(400);
                scaleAnim.setRepeatCount(0);
                scaleAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {search_bar_layout.setVisibility(View.GONE); }
                    @Override public void onAnimationRepeat(Animation animation) { } });
                search_bar_layout.startAnimation(scaleAnim);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Final_Cart.class);
                startActivity(intent);
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .repeat(5)
                        .playOn(findViewById(R.id.shopping_bag));
                Intent intent = new Intent(getApplicationContext(), Buy_Now_Activity.class);
                startActivity(intent);
            }
        });
        back_search_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScaleAnimation anim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f);
                anim.setDuration(500);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) { search_bar.setVisibility(View.INVISIBLE); }
                    @Override public void onAnimationEnd(Animation animation) { }
                    @Override public void onAnimationRepeat(Animation animation) { }
                });
                search_bar.startAnimation(anim);
                Animation animation = AnimationUtils.loadAnimation(Product_view_activity.this, R.anim.top_to_bottom);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override   public void onAnimationStart(Animation animation) { search_page.setVisibility(View.INVISIBLE); }
                    @Override   public void onAnimationEnd(Animation animation) { }
                    @Override   public void onAnimationRepeat(Animation animation) { }
                });
                search_page.startAnimation(animation);

            }
        });
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f);
                anim.setDuration(500);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        search_bar.setVisibility(View.VISIBLE); }
                    @Override  public void onAnimationEnd(Animation animation) { }
                    @Override  public void onAnimationRepeat(Animation animation) { }
                });
                search_bar.startAnimation(anim);
                Animation animation = AnimationUtils.loadAnimation(Product_view_activity.this, R.anim.item_animation_from_bottom);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) { search_page.setVisibility(View.VISIBLE); }
                    @Override public void onAnimationEnd(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) { }
                });
                search_page.startAnimation(animation);
            }
        });
        if (!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());

        setTitle("");

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        toolbar = mViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                if(getIntent().hasExtra("Filter"))
                {
                    return 1;
                }
                else
                {
                    if (selected_brands.size() != 0) {
                        return selected_brands.size();
                    }
                    return AppConfig.Brands.size();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if(getIntent().hasExtra("Filter"))
                {
                    return  getIntent().getStringExtra("Filter");
                }
                if (selected_brands.size() != 0) {
                    return selected_brands.get(position);
                }
                return AppConfig.Brands.get(position);
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                return HeaderDesign.fromColorResAndUrl(
                        colors[(page % 3)],
                        "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg"); }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
        mViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(getIntent().hasExtra("Filter"))
                {

                    search_text.setHint("Search Your Favorites");
                    product_selection_text.setText(getIntent().getStringExtra("Filter"));
                }
                else
                {
                    if(selected_brands.size() == 0) {
                        search_text.setHint("Search " + AppConfig.Brands.get(position) + " Phones ...");
                        product_selection_text.setText(AppConfig.Brands.get(position));
                    }else{search_text.setHint("Search "+selected_brands.get(position)+" Phones ...");
                        product_selection_text.setText(selected_brands.get(position));
                    }
                }
                if(search_layout_visible == true)
                {
                    product_names = Product.load_names(fragments.get(position).mContentItems);
                    adapter.productListElements =  product_names;
                    adapter.notifyDataSetChanged();
                }
                    page_selected = position;
                        performed_search = false;
                list_copied = false;
            }
            @Override public void onPageSelected(int position) {}
            @Override public void onPageScrollStateChanged(int state) { }
        });
        TextView logo = (TextView) findViewById(R.id.logo_white);
        logo.setText(appConfig.getUser_shop_name());
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();

                }
            });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    public void search_offer_products(String search, String option)
    {
        if(option.equals("discount"))
        {
            searched_products_discount_list.clear();
            search_discount_products_adapter.notifyDataSetChanged();
        }
        else
        {
            searched_products_free_list.clear();
            search_free_products_adapter.notifyDataSetChanged();
        }
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
                        if(option.equals("discount"))
                        {
                            searched_products_discount_list.add(pro);
                            search_discount_products_adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            searched_products_free_list.add(pro);
                            search_free_products_adapter.notifyDataSetChanged();
                        }
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
    public void search_products(String search) {
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
                        searched_products_adapter.notifyDataSetChanged();
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

    public void filter_data(String sequence) {
        ArrayList<ProductListElement> filtered;
        if (list_copied == false) {
            currentArrayList.clear();
            currentArrayList.addAll(adapter.productListElements);
            list_copied = true; }
          filtered = Product.filtered_products(currentArrayList, sequence);
        adapter.productListElements = filtered;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        ProductListElement productListElement = adapter.productListElements.get(position);
        if(position  > previous_recycler_position)
        {
            fragments.get(page_selected).mRecyclerView.smoothScrollToPosition(productListElement.getProduct_index()+2);

        }else if(position < previous_recycler_position)
        {
            fragments.get(page_selected).mRecyclerView.smoothScrollToPosition(productListElement.getProduct_index());

        }
        ScaleAnimation scaleAnim = new ScaleAnimation(
                1f, 0f,
                1f, 0f,
                Animation.RELATIVE_TO_SELF,1.0f,
                Animation.RELATIVE_TO_SELF , 1.0f);
        scaleAnim.setDuration(400);
        scaleAnim.setRepeatCount(0);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation) {search_bar_layout.setVisibility(View.GONE); }
            @Override public void onAnimationRepeat(Animation animation) { } });
        search_bar_layout.startAnimation(scaleAnim);
        previous_recycler_position = position;

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
                            product_count.setText(""+obj.getInt("total"));
                        }

                    }catch(Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Could not get products in cart ",Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Could not get products in cart ",Toast.LENGTH_SHORT).show();
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
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            if(db.getAllProducts().size() != 0)
            {
                product_count.setText(db.getAllProducts().size());
            }
        }
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
    @Override
    protected void onResume() {
        super.onResume();
        if(product_count != null)
        {
            check_products_availability();
        }
    }

    class add_to_offers extends AsyncTask<String, Void, Void>
    {

        @Override
        protected Void doInBackground(String... data) {
            HashMap<String,String> params = new HashMap<String,String>();
            params.put("product_id",data[0]);
            params.put("title",data[1]);
            params.put("description",data[2]);
            params.put("offer_type",data[3]);
            if(data[3].equals("Combo Offer"))
            {
                StringBuilder product_ids_discount = new StringBuilder();
                StringBuilder discounted_prices= new StringBuilder();
                StringBuilder products_ids_free = new StringBuilder();
                int i = 0 ;
                for(i = 0  ; i < discounted_products_list.size()-1;i++)
                {
                    product_ids_discount.append(discounted_products_list.get(i).getProduct_id()+"-");
                    discounted_prices.append(discounted_products_list.get(i).getDicount_amount()+"-");
                }
                 product_ids_discount.append(discounted_products_list.get(i).getProduct_id());
                discounted_prices.append(discounted_products_list.get(i).getDicount_amount());
                int j = 0 ;
                for(j = 0  ; j < free_products_list.size()-1;j++)
                {
                    products_ids_free.append(free_products_list.get(j).getProduct_id()+"-");
                }
                products_ids_free.append(free_products_list.get(j).getProduct_id());
            params.put("discount_product_ids",product_ids_discount.toString());
            params.put("free_product_ids",products_ids_free.toString());
            params.put("discounted_product_discounts", discounted_prices.toString());
            }
            if(data[3].equals("Discount Offer"))
            {
                params.put("total_discount_offered",discount_offer_amount.getText().toString());
            }
            if(data[3].equals("Super Value Offer"))
            {
                params.put("minimum_item_count",special_value_offer_item_count.getText().toString());
                params.put("discounted_amount",special_value_offer_discount_amount.getText().toString());
            }
            Log.d("uploading params",""+params);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,AppConfig.add_offer,new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("ProductViewActivity: ",""+response.toString());
                    try {
                        if(response.getString("status").equals("success"))
                        {
                            Toast.makeText(getApplicationContext(),"Successfully uploaded offer",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Please Retry !! ",Toast.LENGTH_LONG).show();

                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("VolleyError",""+error.getMessage());
                    Toast.makeText(getApplicationContext(),"Error Took Place , Please Check Your Network Connection",Toast.LENGTH_SHORT).show();
                }
            });
            AppController.getInstance().addToRequestQueue(request);
            return null;
        }
    }
}
