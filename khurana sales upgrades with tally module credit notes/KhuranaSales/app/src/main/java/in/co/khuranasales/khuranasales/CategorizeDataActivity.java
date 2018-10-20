package in.co.khuranasales.khuranasales;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.co.khuranasales.khuranasales.ComboOfferAdapters.search_product_discount_free_recycler_adapter;
import in.co.khuranasales.khuranasales.ComboOfferAdapters.selected_discount_items_recycler;
import in.co.khuranasales.khuranasales.ComboOfferAdapters.selected_free_items_adapter;
import in.co.khuranasales.khuranasales.ServiceCenters.service_center_main_activity;
import in.co.khuranasales.khuranasales.aboutPage.aboutActivity;
import in.co.khuranasales.khuranasales.add_new_product.add_new_product_activity;
import in.co.khuranasales.khuranasales.bank_offers_adapter.search_bank_name_recycler_adapter;
import in.co.khuranasales.khuranasales.bank_offers_adapter.selected_bank_recycler_adapter;
import in.co.khuranasales.khuranasales.categorize_data_activity_scanning_adapter.scanned_product_adapter;
import in.co.khuranasales.khuranasales.edit_profile.editProfileActivity;
import in.co.khuranasales.khuranasales.exportWorkers.exportMainActivity;
import in.co.khuranasales.khuranasales.manageByInventoryModule.inventoryMainActivity;
import in.co.khuranasales.khuranasales.notification.NotificationActivity;
import in.co.khuranasales.khuranasales.notification.NotificationDatabase;
import in.co.khuranasales.khuranasales.prebooking_module.prebooking_products_activity;
import io.fabric.sdk.android.services.concurrency.AsyncTask;

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
    private TextView textView15;
    private TextView textView16;
    private TextView textView17;
    private TextView textView18;
    private TextView textView19;

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

    private ImageView image_export;
    private ImageView image_promoters;
    private ImageView notify_users;

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
    public Product selected_discount_product = null;
    public Product selected_free_product;
    public int selected_offer_position;

    public EditText discount_offer_amount;

    public EditText special_value_offer_item_count;
    public EditText special_value_offer_discount_amount;

    private TextView write_offer_product_name;
    private TextView write_offer_product_id;
    private TextView write_offer_product_mrp;
    private TextView write_offer_product_mop;

    private TextView date_of_expiry;
    private ImageView increment_offers_count;
    private ImageView decrement_offers_count;
    private TextView count_of_offers;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String date;

    private Boolean searching = false;
    public RelativeLayout layout_offer_renderable;
    public RecyclerView access_type_list_recycler;
    public access_type_recycler_adapter access_type_recycler_adapter;
    public ArrayList<access_type_pojo> access_list=  new ArrayList<>();

    private TextView product_selection_text;
    private ImageView back_product_name_selection;
    private ArrayList<String> selected_brands;

    private TextView outstanding_amount;
    private ImageView reload_outstanding;
    private ProgressBar outstanding_amount_loader;

    private ImageView provide_access_image;

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

    private EditText paytm_offer_discount_amount;
    private TextView add_new_product_khurana_sales;

    public RelativeLayout product_scanning_layout;
    public scanned_product_adapter adapter_for_scanned_products ;
    public ArrayList<Product> scanned_products = new ArrayList<>();
    public RecyclerView scanned_product_recycler_view;

    public SurfaceView cameraView;
    public CameraSource cameraSource;
    public static final int PERMISSION_CAMERA = 1;
    public ImageView nothing_scanned_image;
    public ImageView scanned_and_move_to_cart;
    public ImageView back_from_scanning;
    public ArrayList<String> barcodes_scanned = new ArrayList<>();
    public ArrayList<String> batches_associated = new ArrayList<>();

    public ImageView confirm_and_move_to_card;
    public TextView clear_my_current_scanned;
    public HashMap<String, ArrayList<String>> batch_by_name = new HashMap<>();
    private RelativeLayout transparent_background ;
    private int switching_activity_case  = -1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        update_firebase_id();
        CategorizeDataActivity.type = "Mobile";
        getBrands();
        setContentView(R.layout.categorize_activity_layout);
        transparent_background = (RelativeLayout)findViewById(R.id.transparent_background);
        clear_my_current_scanned = (TextView) findViewById(R.id.clear_my_current_scanned);
        clear_my_current_scanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanned_products.clear();
                adapter_for_scanned_products.notifyDataSetChanged();
                barcodes_scanned.clear();
                batches_associated.clear();
                batch_by_name.clear();
            }
        });
        confirm_and_move_to_card = (ImageView)findViewById(R.id.confirm_and_move_to_cart);
        confirm_and_move_to_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Product p : scanned_products) {
                    p.set_Stock(batch_by_name.get(p.get_Name()).size());
                    add_to_cart(p);
                    upload_batch_numbers(p);
                }
                Intent intent = new Intent(CategorizeDataActivity.this,Buy_Now_Activity.class);
                startActivity(intent);
            }
        });
        nothing_scanned_image = (ImageView)findViewById(R.id.nothing_scanned_image);
        nothing_scanned_image.setVisibility(View.VISIBLE);
        scanned_and_move_to_cart = (ImageView)findViewById(R.id.confirm_and_move_to_cart);
        back_from_scanning = (ImageView)findViewById(R.id.back_from_scanning);
        back_from_scanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade_out(product_scanning_layout);
                fade_out(transparent_background);
            }
        });
        cameraView = (SurfaceView) findViewById(R.id.suface_view);
        BarcodeDetector barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.ALL_FORMATS)//QR_CODE)
                        .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                if (ContextCompat.checkSelfPermission(CategorizeDataActivity.this,
                        android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(CategorizeDataActivity.this,
                            android.Manifest.permission.CAMERA)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(CategorizeDataActivity.this,
                                new String[]{android.Manifest.permission.CAMERA},
                                PERMISSION_CAMERA);
                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    if(!barcodes_scanned.contains(barcodes.valueAt(0).displayValue))
                    {
                        barcodes_scanned.add(barcodes.valueAt(0).displayValue);
                        String[]data = new String[]{barcodes.valueAt(0).displayValue};
                        Log.d("SCANNEDBARCODE",barcodes.valueAt(0).displayValue);
                        new search_scanned_product().execute(data);
                    }
                  }
            }
        });

        scanned_product_recycler_view = (RecyclerView)findViewById(R.id.scanned_products_recycler_view);
        scanned_product_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        scanned_product_recycler_view.setHasFixedSize(true);
        product_scanning_layout = (RelativeLayout)findViewById(R.id.product_scanning_layout);
        product_scanning_layout.setVisibility(View.INVISIBLE);
        recyclerViewItemClickListener scanned_product_click_listener = (view1, position1) -> {
                Product product  = scanned_products.get(position1);
                Intent intent = new Intent(CategorizeDataActivity.this,Product_desc_activity.class);
                intent.putExtra("product_id",product.getProduct_id());
                intent.putExtra("name",product.get_Name());
                intent.putExtra("link",product.get_link());
                intent.putExtra("stock",product.get_Stock());
                intent.putExtra("mrp",product.getPrice_mrp());
                intent.putExtra("mop",product.getPrice_mop());
                intent.putExtra("ksprice",product.getPrice_ks());
                startActivity(intent);
        };

        adapter_for_scanned_products = new scanned_product_adapter(this, scanned_products,scanned_product_click_listener,batch_by_name);
        scanned_product_recycler_view.setAdapter(adapter_for_scanned_products);
        adapter_for_scanned_products.notifyDataSetChanged();
        scanned_product_recycler_view.setVisibility(View.INVISIBLE);
        provide_access_image = (ImageView)findViewById(R.id.provide_access_image);
        access_type_list_recycler = (RecyclerView)findViewById(R.id.access_type_list_recycler);
        access_type_list_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        access_type_list_recycler.setHasFixedSize(true);
        access_type_recycler_adapter = new access_type_recycler_adapter(this,access_list);
        access_type_list_recycler.setAdapter(access_type_recycler_adapter);
        access_type_recycler_adapter.notifyDataSetChanged();
        new load_access_types().execute();

        date_of_expiry = (TextView)findViewById(R.id.date_of_expiry);
        increment_offers_count = (ImageView)findViewById(R.id.increment_count);
        decrement_offers_count = (ImageView)findViewById(R.id.decrement_count);
        count_of_offers = (TextView)findViewById(R.id.count_of_offer);

        date_of_expiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CategorizeDataActivity.this,R.style.AppTheme_DialogTheme, new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Log.d("Date Selected:",dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        date = formatDate(year,monthOfYear,dayOfMonth);
                        date_of_expiry.setText(date);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });

        increment_offers_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_count = Integer.parseInt(count_of_offers.getText().toString());
                current_count += 1;
                count_of_offers.setText(""+current_count);
            }
        });

        decrement_offers_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_count = Integer.parseInt(count_of_offers.getText().toString());
                if(current_count == 1)
                {
                    Toast.makeText(getApplicationContext(),"Can not go below 1",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    current_count -= 1;
                }
                count_of_offers.setText(""+current_count);
            }
        });

        write_offer_product_name = (TextView)findViewById(R.id.write_offer_product_name);
        write_offer_product_id = (TextView)findViewById(R.id.write_offer_product_id);
        write_offer_product_mrp = (TextView)findViewById(R.id.write_offer_product_price);
        write_offer_product_mop = (TextView)findViewById(R.id.write_offer_product_lowest_price);

        image_export = (ImageView)findViewById(R.id.image_export);
        image_promoters = (ImageView)findViewById(R.id.promoters_image);
        notify_users = (ImageView)findViewById(R.id.notify_image);

        new_offer_layout = (RelativeLayout)findViewById(R.id.new_offer_layout);
        new_offer_layout.setVisibility(View.GONE);
        select_offer_type = (Spinner)findViewById(R.id.spinner_select_offer_type);
        offers.add("Combo Offer");
        offers.add("Discount Offer");
        offers.add("Super Value Offer");
        offers.add("Bank Offer");
        offers.add("Paytm Offer");

        views_offers_renderable.add(R.layout.combo_offer_layout);
        views_offers_renderable.add(R.layout.discount_offer_layout);
        views_offers_renderable.add(R.layout.super_value_offer_layout);
        views_offers_renderable.add(R.layout.bank_offer_layout);
        views_offers_renderable.add(R.layout.paytm_offer_layout);

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
                else if(position == 3)
                {
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
                    selected_banks_recycler_adapter.notifyDataSetChanged();

                    ArrayList<String> emi_category_list = new ArrayList<>();
                    ArrayList<String> card_category_list = new ArrayList<>();
                    emi_category_list.add("Only Non Emi Payments");
                    emi_category_list.add("Only Emi Payments");
                    emi_category_list.add("Both Payments Emi/Non-Emi");

                    card_category_list.add("Only Credit Card Payments");
                    card_category_list.add("Only Debit Card Payents");
                    card_category_list.add("Both Debit Card / Credit Card Paymnets ");

                    CustomSpinnerAdapter emi_spinner_adapter = new CustomSpinnerAdapter(CategorizeDataActivity.this,emi_category_list);
                    CustomSpinnerAdapter card_spinner_adapter = new CustomSpinnerAdapter(CategorizeDataActivity.this,card_category_list);


                    emi_select_spinner = (Spinner)findViewById(R.id.emi_category);
                    emi_select_spinner.setAdapter(emi_spinner_adapter);
                    emi_select_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { selected_emi_category = emi_category_list.get(position);}
                        @Override public void onNothingSelected(AdapterView<?> parent) { }});
                    card_select_spinner = (Spinner)findViewById(R.id.card_category);
                    card_select_spinner.setAdapter(card_spinner_adapter);
                    card_select_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { selected_card_category = card_category_list.get(position);}
                        @Override public void onNothingSelected(AdapterView<?> parent) { }});

                    bank_discount_amount = (EditText)findViewById(R.id.bank_discount_amount);
                }
                else if(position == 4)
                {
                    paytm_offer_discount_amount = (EditText)findViewById(R.id.paytm_offer_discount_amount);
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
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(new_offer_layout.getWindowToken(), 0);
            fade_in(new_offer_layout);
            EditText title = (EditText)findViewById(R.id.write_offer_product_title);
            EditText description = (EditText)findViewById(R.id.write_offer_product_description);
            Button upload_offer = (Button)findViewById(R.id.upload_offer);
            write_offer_product_name.setText(searched_products.get(position).get_Name());
                write_offer_product_id.setText(""+searched_products.get(position).getProduct_id());
                write_offer_product_mrp.setText(""+searched_products.get(position).getPrice_mrp());
                write_offer_product_mop.setText(""+searched_products.get(position).getPrice_mop());

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
                    data[0] = ""+searched_products.get(position).getProduct_id();
                    data[1] = title.getText().toString();
                    data[2] = description.getText().toString();
                    data[3] = offers.get(selected_offer_position);
                    new add_to_offers().execute(data);
                    fade_out(new_offer_layout);
                }
            });


        };

        image_export = (ImageView)findViewById(R.id.image_export);
        image_promoters = (ImageView)findViewById(R.id.promoters_image);
        notify_users = (ImageView)findViewById(R.id.notify_image);

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

        searched_products_adapter = new searched_products_adapter(this, searched_products,product_add_to_offer__click);
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
        mDrawer.addDrawerListener(get_drawer_listener());
        mDrawerToggle.syncState();

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

        textView18 = (TextView)findViewById(R.id.manage_prebooking_label);
        textView18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switching_activity_case = 19;
                mDrawer.closeDrawers();
            }
        });

        textView19 = (TextView)findViewById(R.id.manage_inventory_label);
        textView19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switching_activity_case = 20;
                mDrawer.closeDrawers();
            }
        });

        if(!appConfig.getUserType().equals("Admin"))
        {
            textView17.setVisibility(View.GONE);
            provide_access_image.setVisibility(View.GONE);
        }

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
        Accessories.subCategories.add(create_sub_category("Accessories","Here u will find the list of the accessories_export that are available with us,choose the best accessory among the all brands in the list ",R.drawable.best_selling,"View Accessories","Accessories"));
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

    class load_oustanding_amount extends  AsyncTask<Void, Void, Void>
    {

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
                    try{
                        JSONObject object_outstanding = response.getJSONObject(0);
                        outstanding_amount.setText("Outstanding Balance ( "+object_outstanding.getString("outstanding_amount")+"/- )");
                        appConfig.setUser_outstanding(object_outstanding.getString("outstanding_amount"));
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Please Retry !",Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error took place please retry!",Toast.LENGTH_SHORT).show();
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

        final View action_cart= menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategorizeDataActivity.this,Buy_Now_Activity.class);
                startActivity(intent);
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
        else if(id == R.id.action_scan)
        {
            fade_in(product_scanning_layout);
            fade_in(transparent_background);
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
            JsonArrayRequest search_request = new JsonArrayRequest(AppConfig.search_products_with_offers + search_string, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    searched_products.clear();
                    searched_products_adapter.notifyDataSetChanged();
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
                            if(obj.has("offer_id"))
                            {
                                if(obj.getString("offer_id").equals("-1"))
                                {
                                    pro.set_has_offers(false);
                                }
                                else
                                {
                                    pro.set_has_offers(true);
                                }
                            }
                            pro.set_link(obj.getString("link"));
                            if(appConfig.getUserType().equals("Admin"))
                            {
                                searched_products.add(pro);
                            }
                            else
                            {
                                if(pro.getPrice_ks() != 0 && pro.getPrice_mop() != 0 && pro.getPrice_ks() != 0 && pro.get_Stock() !=0)
                                {
                                    searched_products.add(pro);
                                }
                            }
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

    public class load_access_types extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest request_load_access_types = new JsonArrayRequest(AppConfig.load_access_types, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try{
                        for(int i  = 0; i < response.length();i++)
                        {
                            JSONObject obj = response.getJSONObject(i);
                            access_type_pojo pojo = new access_type_pojo();
                            pojo.setName(obj.getString("access_type"));
                            pojo.setIs_selected(false);
                            access_list.add(pojo);
                        }
                        access_type_recycler_adapter.notifyDataSetChanged();
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

    class add_to_offers extends AsyncTask<String, Void, Void>
    {

        @Override
        protected Void doInBackground(String... data) {
            HashMap<String,String> params = new HashMap<String,String>();
            params.put("product_id",data[0]);
            params.put("title",data[1]);
            params.put("description",data[2]);
            params.put("offer_type",data[3]);
            params.put("date_expiry", date_of_expiry.getText().toString());
            params.put("total_count_applicable", count_of_offers.getText().toString());
            ArrayList<String> access_allowed = new ArrayList<>();
            for(int i = 0;i < access_type_recycler_adapter.access_list.size();i++)
            {
                if(access_type_recycler_adapter.access_list.get(i).is_selected)
                {
                    access_allowed.add(access_type_recycler_adapter.access_list.get(i).getName());
                }
            }
            params.put("access_allowed",TextUtils.join(",",access_allowed));
            if(data[3].equals("Combo Offer"))
            {
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
            if(data[3].equals("Discount Offer"))
            {
                params.put("total_discount_offered",discount_offer_amount.getText().toString());
            }
            if(data[3].equals("Super Value Offer"))
            {
                params.put("minimum_item_count",special_value_offer_item_count.getText().toString());
                params.put("discounted_amount",special_value_offer_discount_amount.getText().toString());
            }
            if(data[3].equals("Bank Offer"))
            {
                params.put("discount_amount",bank_discount_amount.getText().toString());
                params.put("emi_category",selected_emi_category);
                params.put("card_category",selected_card_category);
                params.put("banks_selected",TextUtils.join(",",selected_bank_names));
            }
            if(data[3].equals("Paytm Offer"))
            {
                params.put("paytm_discount_amount",paytm_offer_discount_amount.getText().toString());
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

    public class search_banks extends AsyncTask<Void, Void, Void>
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getApplicationContext(),"Sorry Cant Scan Barcodes Camera Disabled",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            cameraSource.start(cameraView.getHolder());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public class search_scanned_product extends AsyncTask<String, Void, Void >
    {
        @Override
        protected Void doInBackground(String... scanned_imei) {
           JsonArrayRequest request_get_scanned_imie = new JsonArrayRequest(AppConfig.get_scanned_product_details+scanned_imei[0], new Response.Listener<JSONArray>() {
               @Override
               public void onResponse(JSONArray response) {
                   try{
                        if(response.length() > 0)
                        {
                            JSONObject product_object = response.getJSONObject(0);
                            if(product_object.getString("success").equals("true"))
                            {
                                Product p = new Product();
                                p.set_Name(product_object.getString("Name"));
                                p.setProduct_id(Integer.parseInt(product_object.getString("product_id")));
                                p.setPrice_mop(Integer.parseInt(product_object.getString("mop")));
                                p.setPrice_mrp(Integer.parseInt(product_object.getString("mrp")));
                                p.setPrice_ks(Integer.parseInt(product_object.getString("ksprice")));
                                p.set_Stock(Integer.parseInt(product_object.getString("stock")));
                                p.set_link(product_object.getString("link"));
                                p.setTax(Float.parseFloat(product_object.getString("tax")));
                                p.setProduct_HSN(product_object.getString("hsn"));
                                p.setTotal_offers_count(Integer.parseInt(product_object.getString("offers_count")));
                                if(batch_by_name.get(p.get_Name()) == null)
                                {
                                    scanned_products.add(p);
                                }
                                scanned_product_recycler_view.setVisibility(View.VISIBLE);
                                if(batch_by_name.get(p.get_Name()) == null)
                                {
                                    ArrayList<String> batches = new ArrayList<>();
                                    batches.add(scanned_imei[0]);
                                    batch_by_name.put(p.get_Name(),batches);
                                }
                                else
                                {
                                    batch_by_name.get(p.get_Name()).add(scanned_imei[0]);
                                }
                                adapter_for_scanned_products.notifyDataSetChanged();
                                batches_associated.add(scanned_imei[0]);
                                nothing_scanned_image.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Success look in the list for correct product",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Requested Imei was not found in record",Toast.LENGTH_LONG).show();
                            if(scanned_products.size() == 0)
                            {
                                scanned_product_recycler_view.setVisibility(View.INVISIBLE);
                                nothing_scanned_image.setVisibility(View.VISIBLE);

                            }
                            }
                          }

                   }catch (Exception e)
                   {
                       e.printStackTrace();
                       Toast.makeText(getApplicationContext(),"Server Response Error",Toast.LENGTH_SHORT).show();
                       if(scanned_products.size() == 0)
                       {
                           scanned_product_recycler_view.setVisibility(View.INVISIBLE);
                           nothing_scanned_image.setVisibility(View.VISIBLE);

                       }
                   }
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error from server",Toast.LENGTH_SHORT).show();
                   if(scanned_products.size() == 0)
                   {
                       scanned_product_recycler_view.setVisibility(View.INVISIBLE);
                       nothing_scanned_image.setVisibility(View.VISIBLE);

                   }
               }
           });
           AppController.getInstance().addToRequestQueue(request_get_scanned_imie);
            return null;
        }
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

    public void upload_batch_numbers(Product product)
    {
        HashMap<String,String> params = new HashMap<>();
        params.put("product_name",""+product.get_Name());
        params.put("selected_batch",TextUtils.join(",",batch_by_name.get(product.get_Name())));
        params.put("email",appConfig.getUser_email().trim());
        Log.d("BUYNOWACTIVITY", params.toString());
        JSONObject params_json = new JSONObject(params);
        JsonObjectRequest upload_batch_numbers_request = new JsonObjectRequest(Request.Method.POST,AppConfig.upload_batches,params_json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("BuyNow","response: "+response.toString());
                    if(response.getString("success").equals("true"))
                    {
                        Toast.makeText(getApplicationContext(),"Successfully updated batch numbers",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Could not update batch numbers",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Could not update batch numbers",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Could not update batch numbers",Toast.LENGTH_SHORT).show();
            }
        });
        upload_batch_numbers_request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(upload_batch_numbers_request);
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
                    case 19:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(),prebooking_products_activity.class);
                        startActivity(intent);
                        break;
                    case 20:
                        switching_activity_case = -1;
                        intent = new Intent(getApplicationContext(),inventoryMainActivity.class);
                        startActivity(intent);
                }
            }
            @Override
            public void onDrawerStateChanged(int newState) { }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.stop();
        cameraSource.release();
    }

}
