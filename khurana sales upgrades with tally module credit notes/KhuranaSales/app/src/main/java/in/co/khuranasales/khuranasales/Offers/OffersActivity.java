package in.co.khuranasales.khuranasales.Offers;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.co.khuranasales.khuranasales.Activity_Login;
import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.Buy_Now_Activity;
import in.co.khuranasales.khuranasales.CategorizeDataActivity;
import in.co.khuranasales.khuranasales.ComboOfferAdapters.combo_offer_recycler_view_adapter;
import in.co.khuranasales.khuranasales.ComboOfferAdapters.search_product_discount_free_recycler_adapter;
import in.co.khuranasales.khuranasales.ComboOfferAdapters.selected_discount_items_recycler;
import in.co.khuranasales.khuranasales.ComboOfferAdapters.selected_free_items_adapter;
import in.co.khuranasales.khuranasales.CustomSpinnerAdapter;
import in.co.khuranasales.khuranasales.Final_Cart;
import in.co.khuranasales.khuranasales.LinearLayoutManagerWithSmoothScroller;
import in.co.khuranasales.khuranasales.MainActivity;
import in.co.khuranasales.khuranasales.NotificationUserList;
import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.Product_view_activity;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.ServiceCenters.service_center_main_activity;
import in.co.khuranasales.khuranasales.SoldItemPromotersActivity;
import in.co.khuranasales.khuranasales.aboutPage.aboutActivity;
import in.co.khuranasales.khuranasales.access_type_pojo;
import in.co.khuranasales.khuranasales.access_type_recycler_adapter;
import in.co.khuranasales.khuranasales.add_new_product.add_new_product_activity;
import in.co.khuranasales.khuranasales.bank_offers_adapter.search_bank_name_recycler_adapter;
import in.co.khuranasales.khuranasales.bank_offers_adapter.selected_bank_recycler_adapter;
import in.co.khuranasales.khuranasales.edit_profile.editProfileActivity;
import in.co.khuranasales.khuranasales.exportWorkers.exportMainActivity;
import in.co.khuranasales.khuranasales.notification.NotificationActivity;
import in.co.khuranasales.khuranasales.provideAccessActivity;
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
    private TextView textView15;
    private TextView textView16;
    private TextView textView17;
    private TextView add_new_product_khurana_sales;
    private FadeOutAnimator animator;
    private FadeInAnimator in_animator;

    private RelativeLayout view_combo_offers_layout;
    private RelativeLayout close_combo_offers_layout;
    private RecyclerView combo_offers_vew_recycler;
    private RecyclerView.Adapter combo_offers_recycler_view_adapter;
    private LinearLayoutManagerWithSmoothScroller linearLayoutManagerWithSmoothScroller;
    private ArrayList<Offer> combo_offers_list = new ArrayList<>();


    public boolean load_filtered = false;
    private ImageView image_export;
    private ImageView image_promoters;
    private ImageView notify_users;

    private RelativeLayout edit_discount_offer_layout;
    private RelativeLayout edit_combo_offer_layout;
    private RelativeLayout edit_super_value_offer_layout;
    private RelativeLayout edit_bank_offer_layout;
    private RelativeLayout edit_paytm_offer_layout;

    private ImageView back_update_discount_offer;
    private ImageView back_update_super_value_offer;
    private ImageView back_update_combo_offer;
    private ImageView back_update_bank_offer;
    private ImageView back_update_paytm_offer;

    private TextView update_discount_offer_product_name;
    private TextView update_discount_offer_product_id;
    private TextView update_discount_offer_product_price;
    private TextView update_discount_offer_product_lowest_price;
    private TextView update_discount_offer_product_date_of_expiry;
    private TextView update_discount_offer_product_count_of_offer;
    private TextView update_discount_offer_title;
    private TextView update_discount_offer_description;
    private TextView update_discount_offer_discount_amount;
    private ImageView discount_offer_increment_count;
    private ImageView discount_offer_decrement_count;
    private Button update_discount_offer;

    private TextView update_paytm_offer_product_name;
    private TextView update_paytm_offer_product_id;
    private TextView update_paytm_offer_product_price;
    private TextView update_paytm_offer_product_lowest_price;
    private TextView update_paytm_offer_product_date_of_expiry;
    private TextView update_paytm_offer_product_count_of_offer;
    private TextView update_paytm_offer_title;
    private TextView update_paytm_offer_description;
    private TextView update_paytm_offer_discount_amount;
    private ImageView paytm_offer_increment_count;
    private ImageView paytm_offer_decrement_count;
    private Button update_paytm_offer;

    private TextView update_bank_product_name;
    private TextView update_bank_product_id;
    private TextView update_bank_product_price;
    private TextView update_bank_product_lowest_price;
    private TextView update_bank_product_date_of_expiry;
    private TextView update_bank_product_count_of_offer;
    private TextView update_bank_title;
    private TextView update_bank_description;
    private TextView update_bank_discount_amount;
    private ImageView bank_offer_increment_count;
    private ImageView bank_offer_decrement_count;
    private Button update_bank_offer;

    private TextView update_super_value_offer_product_name;
    private TextView update_super_value_offer_product_id;
    private TextView update_super_value_offer_product_price;
    private TextView update_super_value_offer_product_lowest_price;
    private TextView update_super_value_offer__product_date_of_expiry;
    private TextView update_super_value_offer_product_count_of_offer;
    private TextView update_super_value_offer_title;
    private TextView update_super_value_offer_description;
    private TextView super_value_offer_item_count;
    private TextView super_value_offer_item_discount_amount;
    private ImageView super_value_offer_increment_count;
    private ImageView super_value_offer_decrement_count;
    private Button update_super_value_offer;

    private TextView update_combo_offer_product_name;
    private TextView update_combo_offer_product_id;
    private TextView update_combo_offer_product_price;
    private TextView update_combo_offer_product_lowest_price;
    private TextView update_combo_offer__product_date_of_expiry;
    private TextView update_combo_offer_product_count_of_offer;
    private TextView update_combo_offer_title;
    private TextView update_combo_offer_description;
    private ImageView combo_offer_increment_count;
    private ImageView combo_offer_decrement_count;
    private Button update_combo_offer;

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
    public Product selected_discount_product = null;
    public Product selected_free_product;
    public RecyclerView searched_products_recycler;
    public String search_string = "";
    private int mYear;
    private int mMonth;
    private int mDay;
    private String date;

    public RecyclerView discount_offer_access_type_list_recycler;
    public in.co.khuranasales.khuranasales.access_type_recycler_adapter discount_offer_access_type_recycler_adapter;
    public ArrayList<access_type_pojo> discount_offer_access_list=  new ArrayList<>();

    public RecyclerView super_value_offer_access_type_list_recycler;
    public in.co.khuranasales.khuranasales.access_type_recycler_adapter super_value_offer_access_type_recycler_adapter;
    public ArrayList<access_type_pojo> super_value_offer_access_list=  new ArrayList<>();

    public RecyclerView combo_offer_access_type_list_recycler;
    public in.co.khuranasales.khuranasales.access_type_recycler_adapter combo_offer_access_type_recycler_adapter;
    public ArrayList<access_type_pojo> combo_offer_access_list=  new ArrayList<>();

    public RecyclerView bank_offer_access_type_list_recycler;
    public in.co.khuranasales.khuranasales.access_type_recycler_adapter bank_offer_access_type_recycler_adapter;
    public ArrayList<access_type_pojo> bank_offer_access_list=  new ArrayList<>();

    public RecyclerView paytm_offer_access_type_list_recycler;
    public in.co.khuranasales.khuranasales.access_type_recycler_adapter paytm_offer_access_type_recycler_adapter;
    public ArrayList<access_type_pojo> paytm_offer_access_list=  new ArrayList<>();

    private ImageView provide_access_image;

    private TextView outstanding_amount;
    private ImageView reload_outstanding;
    private ProgressBar outstanding_amount_loader;

    private ArrayList<String> bank_names = new ArrayList<>();
    private Spinner emi_select_spinner;
    private ArrayList<String> selected_bank_names = new ArrayList<>();
    private search_bank_name_recycler_adapter search_bank_names_recycler_adapter;
    private selected_bank_recycler_adapter selected_banks_recycler_adapter;
    private Spinner card_select_spinner;
    private RecyclerView search_bank_name_recycler;
    private CardView search_bank_name_layout;
    private RecyclerView selected_bank_recycler;
    private EditText bank_discount_amount;
    private EditText search_bank_name;
    private String search_bank_name_string ="";
    private ImageView add_banks;
    private String selected_emi_category ="";
    private String selected_card_category = "";

    private RelativeLayout copy_offer_layout;
    private RelativeLayout search_products_copy_offer_layout;
    private RecyclerView copy_offer_recycler;
    private EditText copy_offer_product_name_text;
    private ImageView copy_offer_back;
    private Button copy_offer_proceed;
    private RecyclerView searched_copy_offer_product_name_recycler;
    private search_product_copy_offer_adapter search_copy_offer_products_adapter;
    private copy_offer_adapter products_for_copy_adapter;
    private ArrayList<Product> searched_products_for_copy = new ArrayList<>();
    private ArrayList<Product> added_products_for_copy = new ArrayList<>();
    private ImageView selected_product_done;
    private int selected_index_for_copy_offer_product;
    private ImageView nothing_found_to_copy_image;
    private TextView clear_search;
    private int selected_offer_id_for_copying;

    // all below variable relate to the view in which we have shown all those products that had copied this offer

    private RelativeLayout copied_offer_products_list_layout;
    private ImageView look_for_copied_products;
    private EditText search_copied_products_text;
    private TextView clear_search_products_copied_offer;
    private ImageView back_copied_products_offer_list_view_layout;
    private RecyclerView copied_offer_products_list_recycler;
    private copy_offer_adapter copied_offer_product_list_adapter;
    private ArrayList<Product> products_of_copied_offer  = new ArrayList<>();
    private int viewing_copy_products_offer_id = -1;
    public boolean selected_product_for_copy =false;
    public int switching_activity_case = -1;
    private FloatingActionButton turn_on_button_copy_mode;
    private FloatingActionButton turn_off_button_copy_mode;
    private boolean copy_mode_on = false;
    private ArrayList<Offer> copy_offers_array_list = new ArrayList<>();

    private boolean can_add;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_activity);
        turn_on_button_copy_mode = (FloatingActionButton)findViewById(R.id.turn_onncopy_mode);
        turn_on_button_copy_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy_mode_on = true;
                fadeOut(turn_on_button_copy_mode);
                fadeIn(turn_off_button_copy_mode);
                filter_offers_by_copy_mode();
            }
        });

        turn_off_button_copy_mode = (FloatingActionButton)findViewById(R.id.turn_off_copy_mode);
        turn_off_button_copy_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy_mode_on = false;
                fadeIn(turn_on_button_copy_mode);
                fadeOut(turn_off_button_copy_mode);
                filter_offers_by_copy_mode();
            }
        });

        recyclerViewItemClickListener remove_offer_copied_product_listner = (view, position) -> {
            Toast.makeText(getApplicationContext(),"Pending Removal",Toast.LENGTH_SHORT).show();
            Product[] product_to_delete = new Product[1];
            product_to_delete[0] = copied_offer_product_list_adapter.products.get(position);
            new remove_copied_offer().execute(product_to_delete);
        };
        copied_offer_products_list_layout = (RelativeLayout)findViewById(R.id.copied_offer_products_list_layout);
        search_copied_products_text = (EditText)findViewById(R.id.search_product_copied_in_offer);
        clear_search_products_copied_offer = (TextView)findViewById(R.id.clear_searched_text);
        back_copied_products_offer_list_view_layout = (ImageView)findViewById(R.id.back_copied_products_view);
        copied_offer_products_list_recycler = (RecyclerView)findViewById(R.id.copied_products_view_recycler);
        copied_offer_products_list_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        copied_offer_products_list_recycler.setHasFixedSize(true);

        copied_offer_product_list_adapter = new copy_offer_adapter(products_of_copied_offer,remove_offer_copied_product_listner);
        copied_offer_products_list_recycler.setAdapter(copied_offer_product_list_adapter);

        back_copied_products_offer_list_view_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(copied_offer_products_list_layout.getVisibility() == View.VISIBLE)
                {
                    slideToBottom(copied_offer_products_list_layout);
                }
            }
        });
        look_for_copied_products = (ImageView)findViewById(R.id.look_for_copied_products);
        look_for_copied_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideToTop(copied_offer_products_list_layout);
                if(viewing_copy_products_offer_id != selected_offer_id_for_copying)
                {
                    products_of_copied_offer.clear();
                    copied_offer_product_list_adapter.notifyDataSetChanged();
                }
                new get_copied_offer_products().execute();
                viewing_copy_products_offer_id = selected_offer_id_for_copying;
            }
        });

        clear_search = (TextView)findViewById(R.id.clear_search);
        clear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(search_products_copy_offer_layout);
            }
        });
        nothing_found_to_copy_image = (ImageView)findViewById(R.id.nothing_found_to_copy_image);
        copy_offer_proceed = (Button)findViewById(R.id.copy_offer_proceed);
        copy_offer_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Pending upload",Toast.LENGTH_SHORT).show();
                new copy_offer().execute();
            }
        });
        copy_offer_back = (ImageView)findViewById(R.id.back_copy_offer);
        copy_offer_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(copy_offer_layout);
            }
        });
        selected_product_done = (ImageView)findViewById(R.id.selected_product_done);
        selected_product_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"adding",Toast.LENGTH_SHORT).show();
                added_products_for_copy.add(searched_products_for_copy.get(selected_index_for_copy_offer_product));
                products_for_copy_adapter.notifyDataSetChanged();
                nothing_found_to_copy_image.setVisibility(View.INVISIBLE);
                copy_offer_recycler.setVisibility(View.VISIBLE);
                fadeOut(search_products_copy_offer_layout);
            }
        });
        recyclerViewItemClickListener searched_product_for_copy_click_listener = (view, position) -> {
            copy_offer_product_name_text.clearFocus();
            selected_product_for_copy = true;
            copy_offer_product_name_text.setText(searched_products_for_copy.get(position).get_Name());
            selected_index_for_copy_offer_product = position;
            };
        recyclerViewItemClickListener remove_added_product_to_copy_listener = (view, position) -> {
            added_products_for_copy.remove(position);
            products_for_copy_adapter.notifyDataSetChanged();
            if(added_products_for_copy.size() == 0)
            {
                nothing_found_to_copy_image.setVisibility(View.VISIBLE);
            }
        };
        search_copy_offer_products_adapter = new search_product_copy_offer_adapter(searched_products_for_copy,searched_product_for_copy_click_listener);
        products_for_copy_adapter = new copy_offer_adapter(added_products_for_copy,remove_added_product_to_copy_listener);
        copy_offer_layout = (RelativeLayout)findViewById(R.id.copy_offer_layout);
        search_products_copy_offer_layout = (RelativeLayout)findViewById(R.id.search_products_copy_offer_layout);
        copy_offer_recycler = (RecyclerView)findViewById(R.id.copy_products_recycler);
        copy_offer_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        copy_offer_recycler.setHasFixedSize(true);
        copy_offer_recycler.setAdapter(products_for_copy_adapter);
        products_for_copy_adapter.notifyDataSetChanged();

        copy_offer_product_name_text = (EditText)findViewById(R.id.product_name_to_search);
        searched_copy_offer_product_name_recycler =(RecyclerView)findViewById(R.id.searched_copy_offer_product_name_recycler);
        searched_copy_offer_product_name_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        searched_copy_offer_product_name_recycler.setHasFixedSize(true);
        searched_copy_offer_product_name_recycler.setAdapter(search_copy_offer_products_adapter);
        search_copy_offer_products_adapter.notifyDataSetChanged();

        copy_offer_product_name_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.equals(""))
                {
                    fadeOut(search_products_copy_offer_layout);
                }
                else
                {
                    search_products_for_copy(s.toString());
                }
            }
            @Override public void afterTextChanged(Editable s) { }});

        provide_access_image = (ImageView)findViewById(R.id.provide_access_image);
        discount_offer_increment_count = (ImageView)findViewById(R.id.discount_offer_increment_count);
        discount_offer_decrement_count = (ImageView)findViewById(R.id.discount_offer_decrement_count);

        paytm_offer_increment_count = (ImageView)findViewById(R.id.paytm_offer_increment_count);
        paytm_offer_decrement_count = (ImageView)findViewById(R.id.paytm_offer_decrement_count);

        super_value_offer_increment_count = (ImageView)findViewById(R.id.super_value_offer_increment_count);
        super_value_offer_decrement_count = (ImageView)findViewById(R.id.super_value_offer_decrement_count);

        combo_offer_increment_count = (ImageView)findViewById(R.id.combo_offer_increment_count);
        combo_offer_decrement_count = (ImageView)findViewById(R.id.combo_offer_decrement_count);

        bank_offer_decrement_count = (ImageView)findViewById(R.id.bank_offer_decrement_count);
        bank_offer_increment_count = (ImageView)findViewById(R.id.bank_offer_increment_count);

        update_bank_product_name = (TextView)findViewById(R.id.update_bank_offer_product_name);
        update_bank_product_id =(TextView)findViewById(R.id.update_bank_offer_product_id);
        update_bank_product_price = (TextView)findViewById(R.id.update_bank_offer_product_price);
        update_bank_product_lowest_price = (TextView)findViewById(R.id.update_bank_offer_product_lowest_price);
        update_bank_product_date_of_expiry  = (TextView)findViewById(R.id.update_bank_offer_date_of_expiry);
        update_bank_product_count_of_offer = (TextView)findViewById(R.id.update_bank_offer_count_of_offer);
        update_bank_title = (TextView)findViewById(R.id.update_bank_offer_product_title);
        update_bank_description = (TextView)findViewById(R.id.update_bank_offer_product_description);
        update_bank_discount_amount = (TextView)findViewById(R.id.bank_discount_amount);
        update_bank_offer = (Button)findViewById(R.id.update_bank_offer);
        update_bank_product_date_of_expiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(OffersActivity.this,R.style.AppTheme_DialogTheme, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Log.d("Date Selected:",dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        date = formatDate(year,monthOfYear,dayOfMonth);
                        update_bank_product_date_of_expiry.setText(date);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });
        bank_offer_increment_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_count = Integer.parseInt(update_bank_product_count_of_offer.getText().toString());
                current_count += 1;
                update_bank_product_count_of_offer.setText(""+current_count);
            }
        });

        bank_offer_decrement_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_count = Integer.parseInt(update_bank_product_count_of_offer.getText().toString());
                if(current_count == 1)
                {
                    Toast.makeText(getApplicationContext(),"Can not go below 1",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    current_count -= 1;
                }
                update_bank_product_count_of_offer.setText(""+current_count);
            }
        });


        update_discount_offer_product_name = (TextView)findViewById(R.id.update_discount_offer_product_name);
        update_discount_offer_product_id =(TextView)findViewById(R.id.update_discount_offer_product_id);
        update_discount_offer_product_price = (TextView)findViewById(R.id.update_discount_offer_product_price);
        update_discount_offer_product_lowest_price = (TextView)findViewById(R.id.update_discount_product_lowest_price);
        update_discount_offer_product_date_of_expiry  = (TextView)findViewById(R.id.update_discount_offer_date_of_expiry);
        update_discount_offer_product_count_of_offer = (TextView)findViewById(R.id.update_discount_offer_count_of_offer);
        update_discount_offer_title = (TextView)findViewById(R.id.update_discount_offer_title);
        update_discount_offer_description = (TextView)findViewById(R.id.update_discount_offer_description);
        update_discount_offer_discount_amount = (TextView)findViewById(R.id.discount_offer_amount);
        update_discount_offer = (Button)findViewById(R.id.update_discount_offer);
        update_discount_offer_product_date_of_expiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(OffersActivity.this,R.style.AppTheme_DialogTheme, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Log.d("Date Selected:",dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        date = formatDate(year,monthOfYear,dayOfMonth);
                        update_discount_offer_product_date_of_expiry.setText(date);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });
        discount_offer_increment_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_count = Integer.parseInt(update_discount_offer_product_count_of_offer.getText().toString());
                current_count += 1;
                update_discount_offer_product_count_of_offer.setText(""+current_count);
            }
        });

        discount_offer_decrement_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_count = Integer.parseInt(update_discount_offer_product_count_of_offer.getText().toString());
                if(current_count == 1)
                {
                    Toast.makeText(getApplicationContext(),"Can not go below 1",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    current_count -= 1;
                }
                update_discount_offer_product_count_of_offer.setText(""+current_count);
            }
        });



        update_paytm_offer_product_name = (TextView)findViewById(R.id.update_paytm_offer_product_name);
        update_paytm_offer_product_id =(TextView)findViewById(R.id.update_paytm_offer_product_id);
        update_paytm_offer_product_price = (TextView)findViewById(R.id.update_paytm_offer_product_price);
        update_paytm_offer_product_lowest_price = (TextView)findViewById(R.id.update_paytm_product_lowest_price);
        update_paytm_offer_product_date_of_expiry  = (TextView)findViewById(R.id.update_paytm_offer_date_of_expiry);
        update_paytm_offer_product_count_of_offer = (TextView)findViewById(R.id.update_paytm_offer_count_of_offer);
        update_paytm_offer_title = (TextView)findViewById(R.id.update_paytm_offer_title);
        update_paytm_offer_description = (TextView)findViewById(R.id.update_paytm_offer_description);
        update_paytm_offer_discount_amount = (TextView)findViewById(R.id.paytm_offer_discount_amount);
        update_paytm_offer = (Button)findViewById(R.id.update_paytm_offer);
        update_paytm_offer_product_date_of_expiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(OffersActivity.this,R.style.AppTheme_DialogTheme, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Log.d("Date Selected:",dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        date = formatDate(year,monthOfYear,dayOfMonth);
                        update_paytm_offer_product_date_of_expiry.setText(date);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });
        paytm_offer_increment_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_count = Integer.parseInt(update_paytm_offer_product_count_of_offer.getText().toString());
                current_count += 1;
                update_paytm_offer_product_count_of_offer.setText(""+current_count);
            }
        });

        paytm_offer_decrement_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_count = Integer.parseInt(update_paytm_offer_product_count_of_offer.getText().toString());
                if(current_count == 1)
                {
                    Toast.makeText(getApplicationContext(),"Can not go below 1",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    current_count -= 1;
                }
                update_paytm_offer_product_count_of_offer.setText(""+current_count);
            }
        });

        update_super_value_offer_product_name = (TextView)findViewById(R.id.update_super_value_offer_product_name);
        update_super_value_offer_product_id = (TextView)findViewById(R.id.update_super_value_offer_product_id);
        update_super_value_offer_product_price =(TextView)findViewById(R.id.update_super_value_offer_product_price);
        update_super_value_offer_product_lowest_price = (TextView)findViewById(R.id.update_super_value_offer_product_lowest_price);
        update_super_value_offer__product_date_of_expiry = (TextView)findViewById(R.id.update_super_value_offer_date_of_expiry);
        update_super_value_offer_product_count_of_offer = (TextView)findViewById(R.id.update_super_value_offer_count_of_offer);
        update_super_value_offer_title = (TextView)findViewById(R.id.update_super_value_offer_product_title);
        update_super_value_offer_description = (TextView)findViewById(R.id.update_super_value_offer_product_description);
        super_value_offer_item_count = (TextView)findViewById(R.id.special_value_offer_item_count);
        super_value_offer_item_discount_amount = (TextView)findViewById(R.id.special_value_offer_price);
        super_value_offer_increment_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_count = Integer.parseInt(update_super_value_offer_product_count_of_offer.getText().toString());
                current_count += 1;
                update_super_value_offer_product_count_of_offer.setText(""+current_count);
            }
        });

        super_value_offer_decrement_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_count = Integer.parseInt(update_super_value_offer_product_count_of_offer.getText().toString());
                if(current_count == 1)
                {
                    Toast.makeText(getApplicationContext(),"Can not go below 1",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    current_count -= 1;
                }
                update_super_value_offer_product_count_of_offer.setText(""+current_count);
            }
        });
        update_super_value_offer__product_date_of_expiry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(OffersActivity.this,R.style.AppTheme_DialogTheme, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Log.d("Date Selected:",dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        date = formatDate(year,monthOfYear,dayOfMonth);
                        update_super_value_offer__product_date_of_expiry.setText(date);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });

        update_combo_offer_product_name = (TextView)findViewById(R.id.update_combo_offer_product_name);
        update_combo_offer_product_id = (TextView)findViewById(R.id.update_combo_offer_product_id);
        update_combo_offer_title = (TextView)findViewById(R.id.update_combo_offer_product_title);
        update_combo_offer_description = (TextView)findViewById(R.id.update_combo_offer_product_description);
        update_combo_offer_product_price = (TextView)findViewById(R.id.update_combo_offer_product_price);
        update_combo_offer_product_lowest_price = (TextView)findViewById(R.id.update_combo_offer_product_lowest_price);
        update_combo_offer__product_date_of_expiry = (TextView)findViewById(R.id.update_combo_offer_date_of_expiry);
        update_combo_offer_product_count_of_offer = (TextView)findViewById(R.id.update_combo_offer_count_of_offer);
        combo_offer_increment_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_count = Integer.parseInt(update_combo_offer_product_count_of_offer.getText().toString());
                current_count += 1;
                update_combo_offer_product_count_of_offer.setText(""+current_count);
            }
        });

        combo_offer_decrement_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_count = Integer.parseInt(update_combo_offer_product_count_of_offer.getText().toString());
                if(current_count == 1)
                {
                    Toast.makeText(getApplicationContext(),"Can not go below 1",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    current_count -= 1;
                }
                update_combo_offer_product_count_of_offer.setText(""+current_count);
            }
        });
        update_combo_offer__product_date_of_expiry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(OffersActivity.this,R.style.AppTheme_DialogTheme, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Log.d("Date Selected:",dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        date = formatDate(year,monthOfYear,dayOfMonth);
                        update_combo_offer__product_date_of_expiry.setText(date);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });

        back_update_combo_offer = (ImageView)findViewById(R.id.back_update_combo_offer);
        back_update_combo_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(edit_combo_offer_layout);
            }
        });

        back_update_super_value_offer = (ImageView)findViewById(R.id.back_update_super_value_offer);
        back_update_super_value_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(edit_super_value_offer_layout);
            }
        });

        back_update_discount_offer = (ImageView)findViewById(R.id.back_update_discount_offer);
        back_update_discount_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(edit_discount_offer_layout);
            }
        });

        back_update_paytm_offer = (ImageView)findViewById(R.id.back_update_paytm_offer);
        back_update_paytm_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(edit_paytm_offer_layout);
            }
        });

        back_update_bank_offer = (ImageView)findViewById(R.id.back_update_bank_offer);
        back_update_bank_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(edit_bank_offer_layout);
            }
        });


        edit_discount_offer_layout = (RelativeLayout)findViewById(R.id.edit_discount_offer_layout);
        edit_super_value_offer_layout = (RelativeLayout)findViewById(R.id.edit_super_value_offer_layout);
        edit_combo_offer_layout = (RelativeLayout)findViewById(R.id.edit_combo_offer_layout);
        edit_bank_offer_layout = (RelativeLayout)findViewById(R.id.edit_bank_offer_layout);
        edit_paytm_offer_layout = (RelativeLayout)findViewById(R.id.edit_paytm_offer_layout);


        if(getIntent().hasExtra("product_id"))
        {
            load_filtered = true;
        }
        else
        {
            load_filtered = false;
        }
        image_export = (ImageView)findViewById(R.id.image_export);
        image_promoters = (ImageView)findViewById(R.id.promoters_image);
        notify_users = (ImageView)findViewById(R.id.notify_image);

        view_combo_offers_layout = (RelativeLayout)findViewById(R.id.combo_offer_view_layout);
        close_combo_offers_layout = (RelativeLayout) findViewById(R.id.close_combo_offer_layout);
        combo_offers_vew_recycler = (RecyclerView)findViewById(R.id.offers_recycler);
                recyclerViewItemClickListener offer_selection_listener = (view, position) -> {
                    Toast.makeText(getApplicationContext(),"Clicked: "+position,Toast.LENGTH_SHORT).show();
                    String[] data = new String[1];
                    data[0] = ""+position;
                    if(combo_offers_list.size() == 0 )
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

        recyclerViewItemClickListener edit_offer_listener = (view, position) -> {
            Offer offer = offers.get(position);
            if(offer.getOffer_type().equals("Discount Offer"))
            {
                fadeIn(edit_discount_offer_layout);
                update_discount_offer_product_name.setText(offer.getOffer_product_name());
                update_discount_offer_product_id.setText(offer.getOffr_producT_id());
                update_discount_offer_product_price.setText(offer.getOffer_product_price());
                update_discount_offer_product_lowest_price.setText(offer.getOffer_product_discounted_price());
                update_discount_offer_title.setText(offer.getOffer_title());
                update_discount_offer_description.setText(offer.getOffer_descripiton());
                update_discount_offer_discount_amount.setText(offer.getDiscount_amount_offeres_single());
                update_discount_offer_product_date_of_expiry.setText(offer.getExpiry_date());
                update_discount_offer_product_count_of_offer.setText(offer.getCount_of_times());
                discount_offer_access_type_list_recycler = (RecyclerView)findViewById(R.id.edit_discount_offer_access_type_list_recycler);
                discount_offer_access_type_list_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                discount_offer_access_type_list_recycler.setHasFixedSize(true);
                discount_offer_access_type_recycler_adapter = new access_type_recycler_adapter(this,discount_offer_access_list);
                discount_offer_access_type_list_recycler.setAdapter(discount_offer_access_type_recycler_adapter);
                discount_offer_access_type_recycler_adapter.notifyDataSetChanged();
                String offer_position[] = {""+position};
                new load_access_types().execute(offer_position);
                update_discount_offer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] data = new String[5];
                        data[0] = offer.getOffr_producT_id();
                        data[1] = update_discount_offer_title.getText().toString();
                        data[2] = update_discount_offer_description.getText().toString();
                        data[3] = "Discount Offer";
                        data[4] = offer.getOffer_id();
                        new add_to_offers().execute(data);
                        fadeOut(edit_discount_offer_layout);
                    }
                });
            }
            else if(offer.getOffer_type().equals("Paytm Offer"))
            {
                fadeIn(edit_paytm_offer_layout);
                update_paytm_offer_product_name.setText(offer.getOffer_product_name());
                update_paytm_offer_product_id.setText(offer.getOffr_producT_id());
                update_paytm_offer_product_price.setText(offer.getOffer_product_price());
                update_paytm_offer_product_lowest_price.setText(offer.getOffer_product_discounted_price());
                update_paytm_offer_title.setText(offer.getOffer_title());
                update_paytm_offer_description.setText(offer.getOffer_descripiton());
                update_paytm_offer_discount_amount.setText(offer.getDiscount_amount_offeres_single());
                update_paytm_offer_product_date_of_expiry.setText(offer.getExpiry_date());
                update_paytm_offer_product_count_of_offer.setText(offer.getCount_of_times());
                paytm_offer_access_type_list_recycler = (RecyclerView)findViewById(R.id.edit_paytm_offer_access_type_list_recycler);
                paytm_offer_access_type_list_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                paytm_offer_access_type_list_recycler.setHasFixedSize(true);
                paytm_offer_access_type_recycler_adapter = new access_type_recycler_adapter(this,paytm_offer_access_list);
                paytm_offer_access_type_list_recycler.setAdapter(paytm_offer_access_type_recycler_adapter);
                paytm_offer_access_type_recycler_adapter.notifyDataSetChanged();
                String offer_position[] = {""+position};
                new load_access_types().execute(offer_position);
                update_paytm_offer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] data = new String[5];
                        data[0] = offer.getOffr_producT_id();
                        data[1] = update_paytm_offer_title.getText().toString();
                        data[2] = update_paytm_offer_description.getText().toString();
                        data[3] = "Paytm Offer";
                        data[4] = offer.getOffer_id();
                        new add_to_offers().execute(data);
                        fadeOut(edit_paytm_offer_layout);
                    }
                });
            }
            else if(offer.getOffer_type().equals("Bank Offer"))
            {
                selected_bank_names.clear();
                add_banks = (ImageView)findViewById(R.id.add_banks);
                search_bank_name = (EditText)findViewById(R.id.search_bank_name);
                fadeIn(edit_bank_offer_layout);
                update_bank_product_name.setText(offer.getOffer_product_name());
                update_bank_product_id.setText(offer.getOffr_producT_id());
                update_bank_product_price.setText(offer.getOffer_product_price());
                update_bank_product_lowest_price.setText(offer.getOffer_product_discounted_price());
                update_bank_title.setText(offer.getOffer_title());
                update_bank_description.setText(offer.getOffer_descripiton());
                update_bank_discount_amount.setText(offer.getDiscount_amount_offeres_single());
                update_bank_product_date_of_expiry.setText(offer.getExpiry_date());
                update_bank_product_count_of_offer.setText(offer.getCount_of_times());
                bank_offer_access_type_list_recycler = (RecyclerView)findViewById(R.id.edit_bank_offer_access_type_list_recycler);
                bank_offer_access_type_list_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                bank_offer_access_type_list_recycler.setHasFixedSize(true);
                bank_offer_access_type_recycler_adapter = new access_type_recycler_adapter(this,bank_offer_access_list);
                bank_offer_access_type_list_recycler.setAdapter(bank_offer_access_type_recycler_adapter);
                bank_offer_access_type_recycler_adapter.notifyDataSetChanged();
                String offer_position[] = {""+position};
                new load_access_types().execute(offer_position);
                update_bank_offer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] data = new String[5];
                        data[0] = offer.getOffr_producT_id();
                        data[1] = update_bank_title.getText().toString();
                        data[2] = update_bank_description.getText().toString();
                        data[3] = "Bank Offer";
                        data[4] = offer.getOffer_id();
                        new add_to_offers().execute(data);
                        fadeOut(edit_bank_offer_layout);
                    }
                });

                add_banks = (ImageView)findViewById(R.id.add_banks);
                add_banks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(search_bank_name.getText().toString().trim() != "")
                        {
                            selected_bank_recycler.setVisibility(View.VISIBLE);
                            selected_bank_names.add(search_bank_name.getText().toString());
                            selected_banks_recycler_adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Nothing Selected",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                search_bank_name = (EditText)findViewById(R.id.search_bank_name);
                search_bank_name.addTextChangedListener(new TextWatcher() {
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().equals("")) {
                            search_bank_name_layout.setVisibility(View.GONE);
                        }else
                        {
                            Log.d("PRODUCTVIEWACTIVITY",s.toString());
                            search_bank_name_layout.setVisibility(View.VISIBLE);
                            search_bank_name_string = s.toString();
                            new search_banks().execute();
                        }
                    }
                    @Override public void afterTextChanged(Editable s) { }
                });
                recyclerViewItemClickListener bank_selection_listener = (view1, position1) -> {
                    Toast.makeText(getApplicationContext(),"Clicked: "+position1,Toast.LENGTH_SHORT).show();
                    search_bank_name.setText(bank_names.get(position1));
                    search_bank_name_layout.setVisibility(View.GONE);
                };
                search_bank_name_layout = (CardView)findViewById(R.id.search_bank_recycler_layout);
                search_bank_name_recycler = (RecyclerView)findViewById(R.id.search_bank_recycler);
                search_bank_name_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                search_bank_name_recycler.setHasFixedSize(true);
                search_bank_names_recycler_adapter = new search_bank_name_recycler_adapter(bank_names,bank_selection_listener);
                search_bank_name_recycler.setAdapter(search_bank_names_recycler_adapter);


                recyclerViewItemClickListener remove_banks_listener = (view1, position1) -> {
                    selected_bank_names.remove(position1);
                    selected_banks_recycler_adapter.notifyDataSetChanged();
                    if(selected_bank_names.size()==0)
                    {
                        selected_bank_recycler.setVisibility(View.GONE);
                    }
                };
                selected_banks_recycler_adapter = new selected_bank_recycler_adapter(selected_bank_names,remove_banks_listener);
                selected_bank_recycler = (RecyclerView)findViewById(R.id.bank_selected_recycler);
                selected_bank_recycler.setHasFixedSize(true);
                selected_bank_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                selected_bank_recycler.setAdapter(selected_banks_recycler_adapter);
                selected_bank_names.addAll(Arrays.asList(offer.getPayment_banks().split(",")));
                selected_banks_recycler_adapter.notifyDataSetChanged();
                selected_bank_recycler.setVisibility(View.VISIBLE);

                ArrayList<String> emi_category_list = new ArrayList<>();
                ArrayList<String> card_category_list = new ArrayList<>();
                emi_category_list.add("Only Non Emi Payments");
                emi_category_list.add("Only Emi Payments");
                emi_category_list.add("Both Payments Emi/Non-Emi");

                card_category_list.add("Only Credit Card Payments");
                card_category_list.add("Only Debit Card Payments");
                card_category_list.add("Both Debit Card / Credit Card Payments ");

                CustomSpinnerAdapter emi_spinner_adapter = new CustomSpinnerAdapter(OffersActivity.this,emi_category_list);
                CustomSpinnerAdapter card_spinner_adapter = new CustomSpinnerAdapter(OffersActivity.this,card_category_list);

                emi_select_spinner = (Spinner)findViewById(R.id.emi_category);
                emi_select_spinner.setAdapter(emi_spinner_adapter);
                emi_select_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { selected_emi_category = emi_category_list.get(position);}
                    @Override public void onNothingSelected(AdapterView<?> parent) { }});
                if(offer.getEmi_category().equals("Only Non Emi Payments"))
                {
                    emi_select_spinner.setSelection(0);
                }
                else if(offer.getEmi_category().equals("Only Emi Payments"))
                {
                    emi_select_spinner.setSelection(1);
                }
                else
                {
                    emi_select_spinner.setSelection(2);
                }

                card_select_spinner = (Spinner)findViewById(R.id.card_category);
                card_select_spinner.setAdapter(card_spinner_adapter);
                card_select_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { selected_card_category = card_category_list.get(position);}
                    @Override public void onNothingSelected(AdapterView<?> parent) { }});
                if(offer.getCard_category().equals("Only Credit Card Payments"))
                {
                    card_select_spinner.setSelection(0);
                }
                else if(offer.getCard_category().equals("Only Debit Card Payments"))
                {
                    card_select_spinner.setSelection(1);
                }
                else
                {
                    card_select_spinner.setSelection(2);
                }
            }
            else if(offer.getOffer_type().equals("Combo Offer"))
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
                        if(selected_discount_product != null)
                        {
                            discounted_products_recycler.setVisibility(View.VISIBLE);
                            discounted_products_list.add(selected_discount_product);
                            selected_discount_items_adapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Nothing Selected",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                free_items_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selected_free_product != null)
                        {
                            free_products_recycler.setVisibility(View.VISIBLE);
                            free_products_list.add(selected_free_product);
                            selected_free_items_adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Nothing Selected",Toast.LENGTH_LONG).show();
                        }
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
                update_combo_offer_product_name.setText(offer.getOffer_product_name());
                update_combo_offer_product_id.setText(offer.getOffr_producT_id());
                update_combo_offer_product_price.setText(offer.getOffer_product_price());
                update_combo_offer_product_lowest_price.setText(offer.getOffer_product_discounted_price());
                update_combo_offer_title.setText(offer.getOffer_title());
                update_combo_offer_description.setText(offer.getOffer_descripiton());
                update_combo_offer_product_count_of_offer.setText(offer.getCount_of_times());
                update_combo_offer__product_date_of_expiry.setText(offer.getExpiry_date());
                update_combo_offer = (Button)findViewById(R.id.update_combo_offer);
                update_combo_offer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] data = new String[5];
                        data[0] = offer.getOffr_producT_id();
                        data[1] = update_combo_offer_title.getText().toString();
                        data[2] = update_combo_offer_description.getText().toString();
                        data[3] = "Combo Offer";
                        data[4] = offer.getOffer_id();
                        new add_to_offers().execute(data);
                        fadeOut(edit_combo_offer_layout);
                    }
                });
                String[] data = {""+position};
                new pre_load_products().execute(data);
                combo_offer_access_type_list_recycler = (RecyclerView)findViewById(R.id.edit_combo_offer_access_type_list_recycler);
                combo_offer_access_type_list_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                combo_offer_access_type_list_recycler.setHasFixedSize(true);
                combo_offer_access_type_recycler_adapter = new access_type_recycler_adapter(this,combo_offer_access_list);
                combo_offer_access_type_list_recycler.setAdapter(combo_offer_access_type_recycler_adapter);
                combo_offer_access_type_recycler_adapter.notifyDataSetChanged();
                String offer_position[] = {""+position};
                new load_access_types().execute(offer_position);
                fadeIn(edit_combo_offer_layout);

            }
            else if(offer.getOffer_type().equals("Super Value Offer"))
            {
                fadeIn(edit_super_value_offer_layout);
                update_super_value_offer_product_name.setText(offer.getOffer_product_name());
                update_super_value_offer_product_id.setText(offer.getOffr_producT_id());
                update_super_value_offer_product_price.setText(offer.getOffer_product_price());
                update_super_value_offer_product_lowest_price.setText(offer.getOffer_product_discounted_price());
                update_super_value_offer__product_date_of_expiry.setText(offer.getExpiry_date());
                update_super_value_offer_product_count_of_offer.setText(offer.getCount_of_times());
                update_super_value_offer_title.setText(offer.getOffer_title());
                update_super_value_offer_description.setText(offer.getOffer_descripiton());
                super_value_offer_item_count.setText(offer.getItem_count());
                super_value_offer_item_discount_amount.setText(offer.getTotal_discounted_amount());
                update_super_value_offer = (Button)findViewById(R.id.update_super_value_offer);
                super_value_offer_access_type_list_recycler = (RecyclerView)findViewById(R.id.edit_super_value_offer_access_type_list_recycler);
                super_value_offer_access_type_list_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                super_value_offer_access_type_list_recycler.setHasFixedSize(true);
                super_value_offer_access_type_recycler_adapter = new access_type_recycler_adapter(this,super_value_offer_access_list);
                super_value_offer_access_type_list_recycler.setAdapter(super_value_offer_access_type_recycler_adapter);
                super_value_offer_access_type_recycler_adapter.notifyDataSetChanged();
                String offer_position[] = {""+position};
                new load_access_types().execute(offer_position);

                update_super_value_offer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] data = new String[5];
                        data[0] = offer.getOffr_producT_id();
                        data[1] = update_super_value_offer_title.getText().toString();
                        data[2] = update_super_value_offer_description.getText().toString();
                        data[3] = "Super Value Offer";
                        data[4] = offer.getOffer_id();
                        new add_to_offers().execute(data);
                        fadeOut(edit_super_value_offer_layout);
                    }
                });
            }
        };

        recyclerViewItemClickListener copy_offer_listener = (view, position) -> {
            Toast.makeText(getApplicationContext(),"Clicked: "+position,Toast.LENGTH_SHORT).show();
            if(selected_offer_id_for_copying != Integer.parseInt(offers.get(position).getOffer_id()))
            if(selected_offer_id_for_copying != Integer.parseInt(offers.get(position).getOffer_id()))
            {
                added_products_for_copy.clear();
                products_for_copy_adapter.notifyDataSetChanged();
                nothing_found_to_copy_image.setVisibility(View.VISIBLE);
                copy_offer_recycler.setVisibility(View.GONE);
            }
            selected_offer_id_for_copying = Integer.parseInt(offers.get(position).getOffer_id());
            fadeIn(copy_offer_layout);
        };

        adapter_offers = new OffersAdapter(offers,this,offer_selection_listener, edit_offer_listener, copy_offer_listener);
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
        mDrawer.addDrawerListener(get_drawer_listener());
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
            String url = "";
            if(load_filtered)
            {
                url = AppConfig.load_offers_filtered+getIntent().getStringExtra("product_id");
            }
            else
            {
                url = AppConfig.get_offers;
            }
            Log.d("URLOFFERSACTIVITY",url);
            JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
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
                                    offer.setOffer_id(product.getString("offer_id"));
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
                                    offer.setExpired(product.getBoolean("expired"));
                                    offer.setCount_applicable(Integer.parseInt(product.getString("count_applicable")));
                                    offer.setExpiry_date(product.getString("expiry_date"));
                                    offer.setCount_of_times(product.getString("count_of_times"));
                                    offer.setAccess_allowed(product.getString("access_allowed"));
                                    offer.setCopy_id(product.getString("copy_id"));
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
                                    if(offer.getAccess_allowed().contains(appConfig.getUserType()))
                                    {
                                        offers.add(offer);
                                        if(offer.getOffer_type().equals("Combo Offer"))
                                        {
                                            String data[] = new String[1];
                                            data[0] = ""+(offers.size() -1);
                                            new get_products_info_combo_offer().execute(data);
                                        }
                                    }
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
            request.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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

        outstanding_amount = (TextView)findViewById(R.id.outstanding_amount);
        outstanding_amount.setText("Outstanding Balance ( "+appConfig.getUser_outstanding()+" /- )");
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
    public void fadeOut(View v)
    {
        animator = new FadeOutAnimator();
        animator.setTarget(v);
        animator.prepare(v);
        animator.setDuration(400);
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
        in_animator.setDuration(400);
        in_animator.addAnimatorListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) { v.setVisibility(View.VISIBLE);}
            @Override public void onAnimationEnd(Animator animation) {}
            @Override public void onAnimationCancel(Animator animation) { }
            @Override public void onAnimationRepeat(Animator animation) { }
        });
        in_animator.start();
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

    public void search_products_for_copy(String search)
    {

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
                searched_products_for_copy.clear();
                search_copy_offer_products_adapter.notifyDataSetChanged();
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
                        searched_products_for_copy.add(pro);
                        search_copy_offer_products_adapter.notifyDataSetChanged();
                        if(searched_products_for_copy.size() > 0)
                        {
                            fadeIn(search_products_copy_offer_layout);
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

        if(! selected_product_for_copy)
        {
            AppController.getInstance().addToRequestQueue(search_request);
        }
        selected_product_for_copy = false;
    }

        class pre_load_products extends AsyncTask<String,Void,Void>
        {
            @Override
            protected Void doInBackground(String... data) {
                free_products_list.clear();
                discounted_products_list.clear();

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
                                Product p = new Product();
                                p.setProduct_id(Integer.parseInt(product.getString("product_id")));
                                p.set_Name(product.getString("Name"));
                                p.setPrice_mrp(Integer.parseInt(product.getString("mrp")));
                                p.setPrice_ks(Integer.parseInt(product.getString("ksprice")));
                                p.set_link(product.getString("link"));
                                p.set_Stock(Integer.parseInt(product.getString("stock")));
                                p.setPrice_mop(Integer.parseInt(product.getString("mop")));

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
                                    discounted_products_list.add(p);
                                    selected_discount_items_adapter.notifyDataSetChanged();
                                    if(discounted_products_list.size() > 0)
                                    {
                                        discounted_products_recycler.setVisibility(View.VISIBLE);
                                        nothing_found_text_discount.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        discounted_products_recycler.setVisibility(View.GONE);
                                        nothing_found_text_discount.setVisibility(View.VISIBLE);

                                    }
                                }
                                else if(free_product_ids.contains(product.getString("product_id")))
                                {
                                    offer.setLies_in_offers(true);
                                    offer.setDiscount_amount_offeres_single(product.getString("mop"));
                                    free_products_list.add(p);
                                    selected_free_items_adapter.notifyDataSetChanged();
                                    if(free_products_list.size() > 0)
                                    {
                                        free_products_recycler.setVisibility(View.VISIBLE);
                                        nothing_foung_text_free.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        free_products_recycler.setVisibility(View.GONE);
                                        nothing_foung_text_free.setVisibility(View.VISIBLE);

                                    }
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
        private static String formatDate(int year, int month, int day) {

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day);
            Date date = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");

            return sdf.format(date);
        }

        class add_to_offers extends io.fabric.sdk.android.services.concurrency.AsyncTask<String, Void, Void>
        {

            @Override
            protected Void doInBackground(String... data) {
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("product_id",data[0]);
                params.put("title",data[1]);
                params.put("description",data[2]);
                params.put("offer_type",data[3]);
                if(data[3].equals("Discount Offer"))
                {
                    params.put("offer_id",data[4]);
                    params.put("date_expiry", update_discount_offer_product_date_of_expiry.getText().toString());
                    params.put("total_count_applicable", update_discount_offer_product_count_of_offer.getText().toString());
                    params.put("total_discount_offered",update_discount_offer_discount_amount.getText().toString());
                    ArrayList<String> access_granted_list = new ArrayList<>();
                    for(int i = 0; i < discount_offer_access_type_recycler_adapter.access_list.size();i++)
                    {
                        if(discount_offer_access_type_recycler_adapter.access_list.get(i).is_selected)
                        {
                            access_granted_list.add(discount_offer_access_type_recycler_adapter.access_list.get(i).getName());
                        }

                    }
                    params.put("access_types",TextUtils.join(",",access_granted_list));
                }
                if(data[3].equals("Paytm Offer"))
                {
                    params.put("offer_id",data[4]);
                    params.put("date_expiry", update_paytm_offer_product_date_of_expiry.getText().toString());
                    params.put("total_count_applicable", update_paytm_offer_product_count_of_offer.getText().toString());
                    params.put("paytm_discount_amount",update_paytm_offer_discount_amount.getText().toString());
                    ArrayList<String> access_granted_list = new ArrayList<>();
                    for(int i = 0; i < paytm_offer_access_type_recycler_adapter.access_list.size();i++)
                    {
                        if(paytm_offer_access_type_recycler_adapter.access_list.get(i).is_selected)
                        {
                            access_granted_list.add(paytm_offer_access_type_recycler_adapter.access_list.get(i).getName());
                        }

                    }
                    params.put("access_types",TextUtils.join(",",access_granted_list));
                }
                if(data[3].equals("Bank Offer"))
                {
                    params.put("offer_id",data[4]);
                    params.put("date_expiry", update_bank_product_date_of_expiry.getText().toString());
                    params.put("total_count_applicable", update_bank_product_count_of_offer.getText().toString());
                    params.put("discount_amount",update_bank_discount_amount.getText().toString());
                    ArrayList<String> access_granted_list = new ArrayList<>();
                    for(int i = 0; i < bank_offer_access_type_recycler_adapter.access_list.size();i++)
                    {
                        if(bank_offer_access_type_recycler_adapter.access_list.get(i).is_selected)
                        {
                            access_granted_list.add(bank_offer_access_type_recycler_adapter.access_list.get(i).getName());
                        }

                    }
                    params.put("access_types",TextUtils.join(",",access_granted_list));
                    params.put("emi_category",selected_emi_category);
                    params.put("card_category",selected_card_category);
                    params.put("banks_selected",TextUtils.join(",",selected_bank_names));
                }
                if(data[3].equals("Combo Offer"))
                {
                    params.put("offer_id",data[4]);
                    params.put("date_expiry", update_combo_offer__product_date_of_expiry.getText().toString());
                    params.put("total_count_applicable", update_combo_offer_product_count_of_offer.getText().toString());

                    ArrayList<String> access_granted_list = new ArrayList<>();
                    for(int i = 0; i < combo_offer_access_type_recycler_adapter.access_list.size();i++)
                    {
                        if(combo_offer_access_type_recycler_adapter.access_list.get(i).is_selected)
                        {
                            access_granted_list.add(combo_offer_access_type_recycler_adapter.access_list.get(i).getName());
                        }

                    }
                    params.put("access_types",TextUtils.join(",",access_granted_list));

                    StringBuilder product_ids_discount = new StringBuilder("");
                    StringBuilder discounted_prices= new StringBuilder("");
                    StringBuilder products_ids_free = new StringBuilder("");
                    if(discounted_products_list.size() > 0)
                    {
                        int i = 0 ;
                        for(i = 0  ; i < discounted_products_list.size()-1;i++)
                        {
                            product_ids_discount.append(discounted_products_list.get(i).getProduct_id()+",");
                            discounted_prices.append(discounted_products_list.get(i).getDicount_amount()+",");
                        }
                        product_ids_discount.append(discounted_products_list.get(i).getProduct_id());
                        discounted_prices.append(discounted_products_list.get(i).getDicount_amount());

                    }
                    if(free_products_list.size() > 0)
                    {
                        int j = 0 ;
                        for(j = 0  ; j < free_products_list.size()-1;j++)
                        {
                            products_ids_free.append(free_products_list.get(j).getProduct_id()+",");
                        }
                        products_ids_free.append(free_products_list.get(j).getProduct_id());

                    }
                    params.put("discount_product_ids",product_ids_discount.toString());
                    params.put("free_product_ids",products_ids_free.toString());
                    params.put("discounted_product_discounts", discounted_prices.toString());
                }
                if(data[3].equals("Super Value Offer"))
                {
                    params.put("offer_id",data[4]);

                    params.put("date_expiry", update_super_value_offer__product_date_of_expiry.getText().toString());
                    params.put("total_count_applicable", update_super_value_offer_product_count_of_offer.getText().toString());

                    params.put("minimum_item_count",super_value_offer_item_count.getText().toString());
                    params.put("discounted_amount",super_value_offer_item_discount_amount.getText().toString());

                    ArrayList<String> access_granted_list = new ArrayList<>();
                    for(int i = 0; i < super_value_offer_access_type_recycler_adapter.access_list.size();i++)
                    {
                        if(super_value_offer_access_type_recycler_adapter.access_list.get(i).is_selected)
                        {
                            access_granted_list.add(super_value_offer_access_type_recycler_adapter.access_list.get(i).getName());
                        }

                    }
                    params.put("access_types",TextUtils.join(",",access_granted_list));

                }
                Log.d("uploading params",""+params);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,AppConfig.update_offer,new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("PRODUCTVIEWACTIVITY: ",""+response.toString());
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
                request.setRetryPolicy(new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                AppController.getInstance().addToRequestQueue(request);
                return null;
            }
        }

        public class load_access_types extends io.fabric.sdk.android.services.concurrency.AsyncTask<String, Void, Void>
        {
            @Override
            protected Void doInBackground(String... offer_position) {

                discount_offer_access_list.clear();
                super_value_offer_access_list.clear();
                combo_offer_access_list.clear();
                bank_offer_access_list.clear();
                paytm_offer_access_list.clear();

                Offer offer = offers.get(Integer.parseInt(offer_position[0]));
                JsonArrayRequest request_load_access_types = new JsonArrayRequest(AppConfig.load_access_types, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i  = 0; i < response.length();i++)
                            {
                                JSONObject obj = response.getJSONObject(i);
                                access_type_pojo pojo = new access_type_pojo();
                                pojo.setName(obj.getString("access_type"));
                                if(offer.getAccess_allowed().contains(pojo.getName()))
                                {
                                    pojo.setIs_selected(true);
                                }
                                else
                                {
                                    pojo.setIs_selected(false);
                                }
                                if(offer.getOffer_type().equals("Discount Offer"))
                                {
                                    discount_offer_access_list.add(pojo);
                                    discount_offer_access_type_recycler_adapter.notifyDataSetChanged();
                                }
                                else if(offer.getOffer_type().equals("Super Value Offer"))
                                {
                                    super_value_offer_access_list.add(pojo);
                                    super_value_offer_access_type_recycler_adapter.notifyDataSetChanged();
                                }
                                else if(offer.getOffer_type().equals("Combo Offer"))
                                {
                                    combo_offer_access_list.add(pojo);
                                    combo_offer_access_type_recycler_adapter.notifyDataSetChanged();
                                }
                                else if(offer.getOffer_type().equals("Bank Offer"))
                                {
                                    bank_offer_access_list.add(pojo);
                                    bank_offer_access_type_recycler_adapter.notifyDataSetChanged();
                                }
                                else if(offer.getOffer_type().equals("Paytm Offer"))
                                {
                                    paytm_offer_access_list.add(pojo);
                                    paytm_offer_access_type_recycler_adapter.notifyDataSetChanged();
                                }

                            }

                        }catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"Could not load access types ",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Could not load access types",Toast.LENGTH_SHORT).show();
                    }
                });
                AppController.getInstance().addToRequestQueue(request_load_access_types);
                return null;
            }
        }
    class get_products_info extends AsyncTask<String,Void,Void>
    {

        @Override
        protected Void doInBackground(String... data) {
            int position = Integer.parseInt(data[0]);
            can_add = false;
            String discounted_product_ids = offers.get(position).discounted_product_ids;
            String free_product_ids = offers.get(position).free_product_ids;
            if(offers.get(position).discounted_products_offers_list.size() == 0)
            {
                can_add = true;
            }
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
                            if(can_add)
                            {
                                offers.get(position).discounted_products_offers_list.add(offer);
                            }
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

    class get_products_info_combo_offer extends AsyncTask<String,Void,Void>
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

    public class search_banks extends io.fabric.sdk.android.services.concurrency.AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            bank_names.clear();
            String words[] = search_bank_name_string.split(" ");
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
            JsonArrayRequest request_search_bank = new JsonArrayRequest(AppConfig.search_bank + search_string, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try{
                        Log.d("BANKRESPONSE",response.toString());
                        JSONObject response_first = response.getJSONObject(0);
                        if(response_first.getString("found").equals("true"))
                        {
                            JSONObject response_main = response.getJSONObject(1);
                            JSONArray banks = response_main.getJSONArray("banks");
                            for(int h = 0; h < banks.length();h++)
                            {
                                bank_names.add(banks.getJSONObject(h).getString("bank_name"));
                                search_bank_names_recycler_adapter.notifyDataSetChanged();
                            }
                        }
                        else
                        {
                            search_bank_name_layout.setVisibility(View.GONE);
                        }
                    }catch(Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Please Retry !!",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        search_bank_name_layout.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Could Not Load Please Retry !",Toast.LENGTH_SHORT).show();
                    search_bank_name_layout.setVisibility(View.GONE);
                }
            });
            AppController.getInstance().addToRequestQueue(request_search_bank);
            return null;
        }
    }

    public class copy_offer extends AsyncTask<Void, Void, Void >
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            ArrayList<String> product_ids = new ArrayList<>();
            for(int i  = 0 ; i < added_products_for_copy.size();i++)
            {
                product_ids.add(""+added_products_for_copy.get(i).getProduct_id());
            }
            try
            {
                params.put("offer_id",selected_offer_id_for_copying);
                params.put("product_ids", TextUtils.join(",",product_ids));
            }catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error In Application",Toast.LENGTH_SHORT).show();
            }
            JsonObjectRequest request_copy_offer = new JsonObjectRequest(Request.Method.POST, AppConfig.copy_offer_url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("RESPONSESERVER",response.toString());
                    try
                    {
                        if(response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Successfully copied your offer",Toast.LENGTH_SHORT).show();
                            fadeOut(copy_offer_layout);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Sorry Could Not copy your offer",Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Some error in server",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error Volley : ", error.toString());
                    Toast.makeText(getApplicationContext(),"Request Error",Toast.LENGTH_SHORT).show();
                }
            });
            request_copy_offer.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(request_copy_offer);
            return null;
        }
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

    public class get_copied_offer_products extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
           JsonArrayRequest request_get_copied_offers = new JsonArrayRequest(AppConfig.get_copied_offer_products_url+selected_offer_id_for_copying, new Response.Listener<JSONArray>() {
               @Override
               public void onResponse(JSONArray response) {
                   products_of_copied_offer.clear();
                   copied_offer_product_list_adapter.notifyDataSetChanged();
                   try {
                    for(int i  = 0 ;i <response.length();i++)
                    {
                        JSONObject product = response.getJSONObject(i);
                        if(product.has("success"))
                        {
                            Toast.makeText(getApplicationContext(),"Offer Not Copied To Any Other Product",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else
                        {
                            Product p = new Product();
                            p.setProduct_id(Integer.parseInt(product.getString("product_id")));
                            p.set_Name(product.getString("Name"));
                            p.setPrice_mrp(Integer.parseInt(product.getString("mrp")));
                            p.setPrice_ks(Integer.parseInt(product.getString("ksprice")));
                            p.set_link(product.getString("link"));
                            p.set_Stock(Integer.parseInt(product.getString("stock")));
                            p.setPrice_mop(Integer.parseInt(product.getString("mop")));
                            p.setOffer_id_for_deletion_copied_offer_product(Integer.parseInt(product.getString("offer_id")));
                            products_of_copied_offer.add(p);
                            copied_offer_product_list_adapter.notifyDataSetChanged();

                        }
                       }
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error in server",Toast.LENGTH_SHORT).show();
                }
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   Log.d("OFFERSACTIVITY",error.toString());
                    Toast.makeText(getApplicationContext(),"Request Not successfull",Toast.LENGTH_LONG).show();
               }
           });
            request_get_copied_offers.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
           AppController.getInstance().addToRequestQueue(request_get_copied_offers);
            return null;
        }
    }

    public class remove_copied_offer extends AsyncTask<Product, Void, Void>
    {
        @Override
        protected Void doInBackground(Product... products) {
            JsonArrayRequest request_delete_offer_copied = new JsonArrayRequest(AppConfig.delete_copied_offer +products[0].getOffer_id_for_deletion_copied_offer_product(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try
                    {
                        JSONObject response_object = response.getJSONObject(0);
                        if(response_object.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Successfully Deleted This Product From Offer",Toast.LENGTH_SHORT).show();
                            products_of_copied_offer.remove(products[0]);
                            copied_offer_product_list_adapter.products.remove(products[0]);
                            copied_offer_product_list_adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Could Not Remove This Product From Offer",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("OFFERSACTIVITY",error.toString());
                    Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
                }
            });
            request_delete_offer_copied.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(request_delete_offer_copied);
            return null;
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

    public void filter_offers_by_copy_mode()
    {
        if(copy_mode_on)
        {
            copy_offers_array_list.clear();
            copy_offers_array_list.addAll(offers);

            ArrayList<Offer> removable_list = new ArrayList<>();
            for(int i  =0 ;i <offers.size();i++) {
                Log.d("COPYID", offers.get(i).getOffer_product_name()+" "+offers.get(i).getCopy_id());
                if(!offers.get(i).getCopy_id().equals("-1"))
                {
                    Log.d("REMOVING",offers.get(i).getOffer_product_name());
                    removable_list.add(offers.get(i));
                }
            }
            offers.removeAll(removable_list);
            adapter_offers.notifyDataSetChanged();
        }
        else
        {
            offers.clear();
            offers.addAll(copy_offers_array_list);
            adapter_offers.notifyDataSetChanged();
        }

    }

    }
