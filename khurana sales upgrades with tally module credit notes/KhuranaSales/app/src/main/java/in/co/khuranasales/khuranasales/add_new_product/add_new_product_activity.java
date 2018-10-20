package in.co.khuranasales.khuranasales.add_new_product;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
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
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.Buy_Now_Activity;
import in.co.khuranasales.khuranasales.Final_Cart;
import in.co.khuranasales.khuranasales.Product;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.exportWorkers.selectionPojo;
import in.co.khuranasales.khuranasales.ledger.Ledger;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class add_new_product_activity extends AppCompatActivity {
    public AppConfig appConfig;
    public android.support.v7.widget.Toolbar toolbar;
    public EditText product_name;
    public EditText product_stock_count;
    public EditText product_mop_price;
    public EditText product_mrp_price;
    public EditText product_ks_price;
    public ImageView proceed_to_product_upload;
    public SurfaceView surface_view;
    public ArrayList<String> barcodes_scanned = new ArrayList<>();
    public SurfaceView cameraView;
    public CameraSource cameraSource;
    public RecyclerView scanned_batches_recycler ;
    public selected_batch_numbers_adapter scanned_batch_recycler_adapter;
    public static final int PERMISSION_CAMERA = 1;
    public ImageView nothing_selected_imei;
    public RelativeLayout product_entry_layout;
    public RelativeLayout batch_entry_layout;
    public FadeInAnimator in_animator;
    public FadeOutAnimator animator;
    public ImageView back_product_entry;
    public ImageView back_batch_entry;
    public RecyclerView location_wise_add_batch_recycler;
    public location_wise_add_batch_recycler_adapter location_wise_add_batch_recycler_adapter_1;
    public ArrayList<String> locations = new ArrayList<>();
    public String selected_location = new String();
    public RelativeLayout transparent_background ;
    public RecyclerView product_search_recycler;
    public String search_string;
    public ArrayList<Product> searched_products = new ArrayList<>();
    public search_product_adapter adapter_search_products;
    public RelativeLayout search_products_layout ;
    public Boolean selected = false;
    public ImageView confirm_batch_selection;
    public HashMap<String, ArrayList<String>> batch_with_location = new HashMap<>();
    public RelativeLayout view_batch_by_location_layout;
    public RecyclerView view_location_wise_imei_recycler;
    public view_iemi_numbers_adapter view_imei_adapter;
    public String selected_view_location_for_batch = "";
    public ImageView drop_batch_view;
    public TextView location_name;

    private EditText search_dealer_text;
    private RelativeLayout search_ledger_layout;
    private RecyclerView ledger_search_recyler;
    private search_ledger_adapter ledger_search_adapter;
    private ArrayList<Ledger> ledgers = new ArrayList<Ledger>();
    private String search_string_ledger;
    private Ledger selected_ledger;
    private Product selected_product;
    private EditText total_payment_text;
    private EditText total_paymnet_cleared_text;
    private EditText product_hsn;
    private EditText product_tax;
    private EditText product_brand;

    private ArrayList<DealerProduct> products_uploaded = new ArrayList<>();
    private RecyclerView products_uploaded_recycler;
    private product_uploaded_recycler_adapter uploaded_products_recycler_adapter;

    private ImageView no_products_uploaded_image ;
    private ImageView edit_product;
    private TextView window_label;
    private TextView product_name_to_edit;
    private boolean editing_mode = false;
    private boolean editing_mode_for_search = false;

    private String selected_product_id_for_update = "-1";
    private DealerProduct selected_product_for_editing;
    private int selected_position_cancel =-1;
    private int selected_order_for_delete = -1;

    private EditText imei_text_written;
    private ImageView add_imei_text_written;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appConfig = new AppConfig(getApplicationContext());
        setContentView(R.layout.add_new_product_layout);

        add_imei_text_written = (ImageView)findViewById(R.id.add_imei_text_written);
        imei_text_written = (EditText)findViewById(R.id.imei_text_written);
        add_imei_text_written.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(batch_with_location.get(selected_location) == null)
                {
                    if(!barcodes_scanned.contains(imei_text_written.getText().toString().trim()))
                    {
                        barcodes_scanned.add(imei_text_written.getText().toString().trim());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                scanned_batch_recycler_adapter.notifyDataSetChanged();
                                nothing_selected_imei.setVisibility(View.GONE);
                                scanned_batches_recycler.setVisibility(View.VISIBLE);
                            }
                        });
                        Uri notification = Uri.parse("android.resource://com.ak.ego/raw/scanned.mp3");
                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                        r.play();
                    }
                }
                else if(batch_with_location.get(selected_location) != null && !batch_with_location.get(selected_location).contains(imei_text_written.getText().toString().trim()) && !barcodes_scanned.contains(imei_text_written.getText().toString().trim()))
                {
                    barcodes_scanned.add(imei_text_written.getText().toString().trim());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            scanned_batch_recycler_adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });


        window_label = (TextView)findViewById(R.id.window_label);
        product_name_to_edit = (TextView)findViewById(R.id.product_name_to_edit);
        cameraView = (SurfaceView) findViewById(R.id.suface_view);
        no_products_uploaded_image = (ImageView)findViewById(R.id.not_products_uploaded_image);
        no_products_uploaded_image.setVisibility(View.VISIBLE);
        products_uploaded_recycler = (RecyclerView)findViewById(R.id.products_uploaded_recycler);
        products_uploaded_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        products_uploaded_recycler.setHasFixedSize(true);
        recyclerViewItemClickListener product_edit_click_listener = (view, position) -> {
            Toast.makeText(getApplicationContext(),"selected product at position"+position,Toast.LENGTH_SHORT).show();
            selected_product_for_editing = products_uploaded.get(position);
            selected_product_id_for_update = products_uploaded.get(position).getProduct_id();
            editing_mode = true;
            editing_mode_for_search = true;
            search_dealer_text.clearFocus();
            product_name.clearFocus();
            window_label.setText("Edit Product");
            product_name_to_edit.setText(products_uploaded.get(position).getProduct_name());
            product_name.setText(products_uploaded.get(position).getProduct_name());
            product_hsn.setText(""+products_uploaded.get(position).getHsn());
            product_tax.setText(""+products_uploaded.get(position).getTax());
            product_brand.setText("Samsung");
            product_ks_price.setText(""+products_uploaded.get(position).getProduct_ks());
            product_mop_price.setText(""+products_uploaded.get(position).getProduct_mop());
            product_mrp_price.setText(""+products_uploaded.get(position).getProduct_mrp());
            total_payment_text.setText(""+products_uploaded.get(position).getDealer_total_outstanding());
            total_paymnet_cleared_text.setText(""+products_uploaded.get(position).getDealer_total_amount_cleared());
            product_stock_count.setText(""+products_uploaded.get(position).getBatch_bought_available().split(",").length);
            editing_mode_for_search = true;
            search_dealer_text.setText(products_uploaded.get(position).getDealer_name());
            String data[] = new String[2];
            Log.d("BATCHAVAILABLE",products_uploaded.get(position).getBatch_bought_available());
            data[0] = products_uploaded.get(position).getBatch_bought_available();
            data[1]= products_uploaded.get(position).getProduct_id();
            new get_batches_locations().execute(data);
            fadeIn(product_entry_layout);
        };

        recyclerViewItemClickListener cancel_purchase_order_listner = (view, position) -> {
            PopupMenu popupMenu = new PopupMenu(add_new_product_activity.this, view);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                   if(item.getItemId() == R.id.cancel)
                   {
                       Toast.makeText(getApplicationContext(),"Pending Cancel",Toast.LENGTH_SHORT).show();
                       selected_position_cancel = position;
                       new cancel_order_selected().execute();
                   }
                   else
                   {
                       selected_order_for_delete = position;
                       Toast.makeText(getApplicationContext(),"Pending Delete",Toast.LENGTH_SHORT).show();
                       new delete_order_selected().execute();
                   }
                    return true;
                };
            });
            popupMenu.getMenu().add(1,R.id.delete, 1, "Delete");
            popupMenu.getMenu().add(1,R.id.cancel,2,"Cancel");
            popupMenu.show();
        };
        uploaded_products_recycler_adapter = new product_uploaded_recycler_adapter(this, products_uploaded, product_edit_click_listener, cancel_purchase_order_listner);
        products_uploaded_recycler.setAdapter(uploaded_products_recycler_adapter);
        uploaded_products_recycler_adapter.notifyDataSetChanged();
        products_uploaded_recycler.setVisibility(View.INVISIBLE);
        total_payment_text = (EditText)findViewById(R.id.total_payment_text);
        total_paymnet_cleared_text = (EditText)findViewById(R.id.total_payment_cleared_text);

        search_ledger_layout = (RelativeLayout)findViewById(R.id.search_ledger_layout);
        ledger_search_recyler = (RecyclerView)findViewById(R.id.ledger_search_recycler);
        ledger_search_recyler.setHasFixedSize(true);
        ledger_search_recyler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewItemClickListener ledger_selection_listener = (view, position) -> {
            Toast.makeText(getApplicationContext(),"selected product at position"+position,Toast.LENGTH_SHORT).show();
            selected_ledger = ledgers.get(position);
            search_dealer_text.clearFocus();
            search_dealer_text.setText(selected_ledger.getName());
            fadeOut(search_ledger_layout);
        };
        ledger_search_adapter = new search_ledger_adapter(ledgers,ledger_selection_listener);
        ledger_search_recyler.setAdapter(ledger_search_adapter);
        search_dealer_text = (EditText)findViewById(R.id.search_dealer_text);
        search_dealer_text.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!search_dealer_text.getText().toString().equals(""))
                {
                    search_ledgers(search_dealer_text.getText().toString());
                }
                else
                {
                    if(search_ledger_layout.getVisibility() == View.VISIBLE)
                    {
                        fadeOut(search_ledger_layout);
                    }
                }

            }
            @Override public void afterTextChanged(Editable s) { }
        });
        location_name = (TextView)findViewById(R.id.location_of_batch);
        view_batch_by_location_layout = (RelativeLayout)findViewById(R.id.view_batch_by_location_layout);
        view_location_wise_imei_recycler = (RecyclerView)findViewById(R.id.imei_numbers_view_by_location_recycler);
        view_location_wise_imei_recycler.setHasFixedSize(true);
        view_location_wise_imei_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        view_imei_adapter = new view_iemi_numbers_adapter(new ArrayList<>(),null);
        view_location_wise_imei_recycler.setAdapter(view_imei_adapter);
        view_imei_adapter.notifyDataSetChanged();
        drop_batch_view = (ImageView)findViewById(R.id.drop_view_imei_numbers);
        drop_batch_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideToBottom(view_batch_by_location_layout);
                view_imei_adapter.imei_numbers.clear();
                view_imei_adapter.notifyDataSetChanged();
            }
        });
        search_products_layout = (RelativeLayout)findViewById(R.id.search_products_layout);
        confirm_batch_selection = (ImageView)findViewById(R.id.confirm_batch_selection);
        confirm_batch_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(batch_with_location.get(selected_location) == null)
                {
                    Log.d("SELECTEDLOCATION",selected_location);
                    ArrayList<String> batches_selected = new ArrayList<>();
                    batches_selected.addAll(barcodes_scanned);
                    batch_with_location.put(selected_location,batches_selected);
                }
                else
                {
                    Log.d("SELECTEDLOCATION",selected_location);
                    ArrayList<String> batches_selected = new ArrayList<>();
                    batches_selected.addAll(barcodes_scanned);
                    if(!batch_with_location.get(selected_location).containsAll(barcodes_scanned))
                    {
                        batch_with_location.put(selected_location,batches_selected);
                    }
                }

                barcodes_scanned.clear();
                scanned_batch_recycler_adapter.notifyDataSetChanged();
                slideToBottom(batch_entry_layout);
                fadeIn(product_entry_layout);
            }
        });
        product_search_recycler = (RecyclerView)findViewById(R.id.product_search_recycler);
        product_search_recycler.setHasFixedSize(true);
        product_search_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewItemClickListener product_selection_listener = (view, position) -> {
            Toast.makeText(getApplicationContext(),"selected product at position"+position,Toast.LENGTH_SHORT).show();
            selected_product = searched_products.get(position);
            product_name.clearFocus();
            product_name.setText(selected_product.get_Name());
            product_mrp_price.setText(""+selected_product.getPrice_mrp());
            product_mop_price.setText(""+selected_product.getPrice_mop());
            product_ks_price.setText(""+selected_product.getPrice_ks());
            product_hsn.setText(selected_product.getProduct_HSN());
            product_tax.setText(""+selected_product.getTax());
            product_brand.setText(selected_product.getBrand());
            fadeOut(search_products_layout);
            selected = true;
        };
        adapter_search_products = new search_product_adapter(searched_products,product_selection_listener);
        product_search_recycler.setAdapter(adapter_search_products);
        adapter_search_products.notifyDataSetChanged();

        transparent_background = (RelativeLayout)findViewById(R.id.transparent_background);
        location_wise_add_batch_recycler = (RecyclerView)findViewById(R.id.location_wise_add_batch_recycler);
        location_wise_add_batch_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        location_wise_add_batch_recycler.setHasFixedSize(true);

        recyclerViewItemClickListener view_batch_of_location_listener = (view, position) -> {
            Toast.makeText(getApplicationContext(),"Clicked: "+position,Toast.LENGTH_SHORT).show();
            selected_view_location_for_batch = locations.get(position);
            Log.d("SELECTEDBATCHVIEW",selected_view_location_for_batch);
            location_name.setText(selected_view_location_for_batch);
            slideToTop(view_batch_by_location_layout);
            if(batch_with_location.get(selected_view_location_for_batch) != null)
            {
                view_imei_adapter.imei_numbers.addAll(batch_with_location.get(selected_view_location_for_batch));
            }
            for(int i  = 0 ;i < view_imei_adapter.imei_numbers.size();i++)
            {
               Log.d("IMEINUMBER",view_imei_adapter.imei_numbers.get(i));
            }
            view_imei_adapter.notifyDataSetChanged();
        };

        recyclerViewItemClickListener location_selection_listener = (view, position) -> {
            Toast.makeText(getApplicationContext(),"Adding For Location"+locations.get(position),Toast.LENGTH_SHORT).show();
            selected_location = locations.get(position);
            if(batch_with_location.get(selected_location) != null)
            {
                barcodes_scanned.clear();
                scanned_batch_recycler_adapter.batches.clear();
                nothing_selected_imei.setVisibility(View.GONE);
                scanned_batches_recycler.setVisibility(View.VISIBLE);
                scanned_batch_recycler_adapter.batches.addAll(batch_with_location.get(selected_location));
                scanned_batch_recycler_adapter.notifyDataSetChanged();
            }
            else
            {
                barcodes_scanned.clear();
                nothing_selected_imei.setVisibility(View.VISIBLE);
                scanned_batches_recycler.setVisibility(View.INVISIBLE);
            }
            slideToTop(batch_entry_layout);
        };

        location_wise_add_batch_recycler_adapter_1 = new location_wise_add_batch_recycler_adapter(locations,location_selection_listener,view_batch_of_location_listener);
        location_wise_add_batch_recycler.setAdapter(location_wise_add_batch_recycler_adapter_1);
        location_wise_add_batch_recycler_adapter_1.notifyDataSetChanged();
        if(locations.size() == 0)
        {
            locations.clear();
            new fetch_locations().execute();
        }
        product_entry_layout = (RelativeLayout)findViewById(R.id.product_entry_layout);
        batch_entry_layout = (RelativeLayout)findViewById(R.id.batch_entry_layout);
        back_product_entry = (ImageView)findViewById(R.id.back_product_entry);
        back_batch_entry = (ImageView)findViewById(R.id.back_batch_entry);
        back_product_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(product_entry_layout);
                fadeOut(transparent_background);
            }
        });
        back_batch_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideToBottom(batch_entry_layout);
            }
        });
        nothing_selected_imei = (ImageView)findViewById(R.id.nothing_batch_selected_imei_image);
        scanned_batches_recycler = (RecyclerView)findViewById(R.id.scanned_batch_recycler_view);
        scanned_batches_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewItemClickListener batches_remove_listener = (view, position) -> {
            Toast.makeText(getApplicationContext(),"Clicked: "+position,Toast.LENGTH_SHORT).show();
            String barcode_to_remove = barcodes_scanned.get(position);
            if(batch_with_location.get(selected_location) != null)
            {
                if(batch_with_location.get(selected_location).contains(barcode_to_remove))
                {
                    batch_with_location.get(selected_location).remove(barcode_to_remove);
                }
            }
            barcodes_scanned.remove(position);
            scanned_batch_recycler_adapter.notifyDataSetChanged();
            if(barcodes_scanned.size() == 0)
            {
                nothing_selected_imei.setVisibility(View.VISIBLE);
                scanned_batches_recycler.setVisibility(View.GONE);
            }
        };
        scanned_batch_recycler_adapter = new selected_batch_numbers_adapter(barcodes_scanned,batches_remove_listener);
        scanned_batches_recycler.setAdapter(scanned_batch_recycler_adapter);
        scanned_batch_recycler_adapter.notifyDataSetChanged();
        if(barcodes_scanned.size() == 0)
        {
            nothing_selected_imei.setVisibility(View.VISIBLE);
            scanned_batches_recycler.setVisibility(View.GONE);
        }

        product_name = (EditText) findViewById(R.id.product_name);
        product_name.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(""))
                {
                    search_offer_products(s.toString());
                    selected = false;
                }
            }
            @Override public void afterTextChanged(Editable s) { }
        });
        product_brand = (EditText)findViewById(R.id.product_brand);
        product_hsn = (EditText)findViewById(R.id.product_hsn);
        product_tax = (EditText)findViewById(R.id.product_tax);
        product_mop_price = (EditText) findViewById(R.id.product_mop_price);
        product_mrp_price = (EditText) findViewById(R.id.product_mrp_price);
        product_ks_price = (EditText) findViewById(R.id.product_ks_price);
        product_stock_count = (EditText) findViewById(R.id.product_stock_count);
        proceed_to_product_upload = (ImageView) findViewById(R.id.proceed_to_product_upload);
        proceed_to_product_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editing_mode == true)
                {

                    new update_product_selected().execute();
                }
                else
                {
                    new add_new_product().execute();
                }
            }
        });
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        locations.clear();
        new fetch_locations().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_new_product, menu);
        final View action_profile = menu.findItem(R.id.action_profile).getActionView();
        action_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appConfig.isLogin()) {
                    Intent intent = new Intent(add_new_product_activity.this, Final_Cart.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(add_new_product_activity.this, "Please login to proceed", Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart = menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_new_product_activity.this, Buy_Now_Activity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add_new)
        {
            editing_mode = false;
            editing_mode_for_search = false;
            Toast.makeText(getApplicationContext(),"starting process to add new product",Toast.LENGTH_SHORT).show();
            window_label.setText("New Product");
            product_name_to_edit.setText("");
            clear_all();
            fadeIn(product_entry_layout);
            fadeIn(transparent_background);
            return  true;
        }
        else if(item.getItemId() == android.R.id.home)
        {
            finish();
            overridePendingTransition(0,R.anim.slide_out_left_animation);
        }
        return true;
    }

    public class initialize_camera_view extends AsyncTask<Void, Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            BarcodeDetector barcodeDetector =
                    new BarcodeDetector.Builder(add_new_product_activity.this)
                            .setBarcodeFormats(Barcode.ALL_FORMATS)//QR_CODE)
                            .build();
            cameraSource = new CameraSource
                    .Builder(add_new_product_activity.this, barcodeDetector)
                    .setRequestedPreviewSize(120, 640)
                    .setAutoFocusEnabled(true)
                    .build();

            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {

                    if (ContextCompat.checkSelfPermission(add_new_product_activity.this,
                            android.Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(add_new_product_activity.this,
                                android.Manifest.permission.CAMERA)) {
                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(add_new_product_activity.this,
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
                        if(batch_with_location.get(selected_location) == null)
                        {
                            if(!barcodes_scanned.contains(barcodes.valueAt(0).displayValue))
                            {
                                barcodes_scanned.add(barcodes.valueAt(0).displayValue);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        scanned_batch_recycler_adapter.notifyDataSetChanged();
                                        nothing_selected_imei.setVisibility(View.GONE);
                                        scanned_batches_recycler.setVisibility(View.VISIBLE);
                                    }
                                });
                                Uri notification = Uri.parse("android.resource://com.ak.ego/raw/scanned.mp3");
                                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                                r.play();
                            }
                        }
                        else if(batch_with_location.get(selected_location) != null && !batch_with_location.get(selected_location).contains(barcodes.valueAt(0).displayValue) && !barcodes_scanned.contains(barcodes.valueAt(0).displayValue))
                        {
                            barcodes_scanned.add(barcodes.valueAt(0).displayValue);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    scanned_batch_recycler_adapter.notifyDataSetChanged();
                                }
                            });
                            Uri notification = Uri.parse("android.resource://com.ak.ego/raw/scanned.mp3");
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                        }
                    }
                }
            });
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

    public class fetch_locations extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest request_locations_of_stock = new JsonArrayRequest(AppConfig.load_batch_locations, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    locations.clear();
                    Log.d("LOCATIONSREPONSE",response.toString());
                    for(int i = 0; i < response.length();i++)
                    {
                        try{
                            JSONObject location = response.getJSONObject(i);
                            locations.add(location.getString("Column"));
                            location_wise_add_batch_recycler_adapter_1.notifyDataSetChanged();
                        }catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"Issue with our server",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            request_locations_of_stock.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(request_locations_of_stock);
            return null;
        }
    }

    public void search_offer_products(String search)
    {
        searched_products.clear();
        adapter_search_products.notifyDataSetChanged();

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
                if(response.length() == 0)
                {
                    if(search_products_layout.getVisibility() == View.VISIBLE)
                    {
                        fadeOut(search_products_layout);
                    }
                }
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
                        pro.setBrand(obj.getString("brand"));
                        pro.setProduct_HSN(obj.getString("hsn"));
                        pro.setTax(Integer.parseInt(obj.getString("tax")));
                        pro.set_link(obj.getString("link"));
                        searched_products.add(pro);
                        adapter_search_products.notifyDataSetChanged();
                        if(!selected)
                        {
                            fadeIn(search_products_layout);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if(search_products_layout.getVisibility() == View.VISIBLE)
                        {
                            fadeOut(search_products_layout);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Volley : ", error.toString());
                if(search_products_layout.getVisibility() == View.VISIBLE)
                {
                    fadeOut(search_products_layout);
                }
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

        if(!editing_mode_for_search)
        {
            AppController.getInstance().addToRequestQueue(search_request);
        }
        editing_mode_for_search = false;
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


    public void search_ledgers(String search) {
        String words[] = search.split(" ");
        search_string_ledger = new String("%");
        for (String word : words) {
            if (word.equals(" ")) {
                continue;
            } else {
                search_string_ledger = search_string_ledger + word + "%";
            }
        }
        try {
            search_string = java.net.URLEncoder.encode(search_string_ledger, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("Searched: ", "Reference " + AppConfig.load_ledgers + search_string_ledger);
        JsonArrayRequest search_request = new JsonArrayRequest(AppConfig.load_ledgers + search_string_ledger, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ledgers.clear();
                ledger_search_adapter.notifyDataSetChanged();
                Log.d("True Response", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Ledger ledger = new Ledger();
                        ledger.setName(obj.getString("Name"));
                        ledger.setAddress(obj.getString("Address"));
                        ledger.setGSTIN(obj.getString("GSTIN"));
                        ledger.setMobile(obj.getString("Mobile"));
                        ledger.setParent(obj.getString("Parent"));
                        ledger.setPhone(obj.getString("Phone"));
                        ledger.setState(obj.getString("State"));
                        ledger.setEmail(obj.getString("Email"));
                        ledgers.add(ledger);
                        ledger_search_adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        search_ledger_layout.setVisibility(View.INVISIBLE);
                    }
                }
                if(ledgers.size() > 0 )
                {
                    if(search_ledger_layout.getVisibility() != View.VISIBLE)
                    {
                        fadeIn(search_ledger_layout);
                    }
                }
                else{
                    fadeOut(search_ledger_layout);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Volley : ", error.toString());
                fadeOut(search_ledger_layout);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("search", search_string_ledger);
                return params;
            }
        };
        search_request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        if(!editing_mode_for_search)
        {
            AppController.getInstance().addToRequestQueue(search_request);
        }
        editing_mode_for_search = false;

    }

    public class add_new_product extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try {
                params.put("dealer_name",search_dealer_text.getText().toString());
                if(selected_ledger != null)
                {
                    params.put("dealer_phone",selected_ledger.getPhone());
                }
                else
                {
                    params.put("dealer_phone","");
                }
                params.put("product_name",product_name.getText().toString());
                params.put("product_mop",product_mop_price.getText().toString());
                params.put("product_mrp",product_mrp_price.getText().toString());
                params.put("brand",product_brand.getText().toString());
                params.put("product_ks",product_ks_price.getText().toString());
                params.put("stock_count",product_stock_count.getText().toString());
                params.put("total_outstanding",total_payment_text.getText().toString());
                params.put("total_cleared_outstanding",total_paymnet_cleared_text.getText().toString());
                if(selected_product == null)
                {
                    params.put("product_id", "-1");
                }
                else
                {
                    params.put("product_id",selected_product.getProduct_id());
                }
                params.put("product_tax_percent",product_tax.getText().toString());
                params.put("product_hsn",product_hsn.getText().toString());
                JSONObject batches = new JSONObject();
                for(int i = 0;i < locations.size();i++)
                {
                    if(batch_with_location.get(locations.get(i)) != null)
                    {
                      String batches_at_this_position = TextUtils.join(",",batch_with_location.get(locations.get(i)));
                      batches.put(locations.get(i),batches_at_this_position);
                    }
                    else
                    {
                        batches.put(locations.get(i),"");
                    }
                }
                params.put("batches_with_location",batches);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            Log.d("PARAMSFORUPLOAD",params.toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, appConfig.add_purchase_product_url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                   try{
                        if(response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Product has been successfully uploaded",Toast.LENGTH_SHORT).show();
                            fadeOut(product_entry_layout);
                            fadeOut(transparent_background);
                        }
                   }catch (Exception e)
                   {
                       Toast.makeText(getApplicationContext(),"Problem in server",Toast.LENGTH_SHORT).show();
                   }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Problem in server sorry !!",Toast.LENGTH_SHORT).show();
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

    public class get_uploaded_products extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest request = new JsonArrayRequest(appConfig.get_uploaded_purchased_products, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for(int i = 0 ; i <response.length();i++)
                        {
                            JSONObject product_object = response.getJSONObject(i);
                            DealerProduct pro = new DealerProduct();
                            pro.setProduct_id(product_object.getString("product_id"));
                            pro.setStock_bought(product_object.getString("stock"));
                            pro.setProduct_name(product_object.getString("Name"));
                            pro.setProduct_mrp(product_object.getString("mrp"));
                            pro.setProduct_mop(product_object.getString("mop"));
                            pro.setProduct_ks(product_object.getString("ksprice"));
                            pro.setTax(product_object.getString("tax"));
                            pro.setHsn(product_object.getString("hsn"));
                            pro.setLink(product_object.getString("link"));
                            pro.setDealer_name(product_object.getString("dealer_name"));
                            pro.setDealer_phone(product_object.getString("dealer_phone"));
                            pro.setBatch_bought_available(product_object.getString("batches_available"));
                            pro.setBatch_sold(product_object.getString("batches_sold"));
                            pro.setDealer_total_amount_cleared(product_object.getString("amount_cleared"));
                            pro.setDealer_total_outstanding(product_object.getString("amount_total"));
                        products_uploaded.add(pro);
                        uploaded_products_recycler_adapter.notifyDataSetChanged();
                        products_uploaded_recycler.setVisibility(View.VISIBLE);
                        no_products_uploaded_image.setVisibility(View.GONE);
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Some Error On Server!!",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Could not get products please retry!!",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();
        products_uploaded.clear();
        uploaded_products_recycler_adapter.notifyDataSetChanged();
        new get_uploaded_products().execute();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new initialize_camera_view().execute();
            }
        }).start();

       }

    public class get_batches_locations extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... data) {
            JSONObject params = new JSONObject();
            try {
                params.put("batches", data[0]);
                params.put("product_id",data[1]);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            JsonObjectRequest request_get_batch_locations = new JsonObjectRequest(Request.Method.POST, AppConfig.get_batches_locations_by_batch_numbers, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    batch_with_location.clear();
                    try {
                        String locations = response.getString("locations");
                        if(!locations.equals(""))
                        {
                            String[] locations_array = locations.split(",");
                            String[]batches_array = data[0].split(",");
                            for(int i = 0; i < locations_array.length;i++)
                            {
                                if(batch_with_location.get(locations_array[i]) != null)
                                {
                                    batch_with_location.get(locations_array[i]).add(batches_array[i]);
                                }
                                else
                                {
                                    ArrayList<String> batches_list = new ArrayList<>();
                                    batches_list.add(batches_array[i]);
                                    batch_with_location.put(locations_array[i],batches_list);
                                }
                            }
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Batches Not Found ",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            request_get_batch_locations.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(request_get_batch_locations);
            return null;
        }
    }

    public void clear_all()
    {
        product_name.setText("");
        product_hsn.setText("8517");
        product_tax.setText("12");
        product_brand.setText("");
        product_mrp_price.setText("0");
        product_ks_price.setText("0");
        product_mop_price.setText("0");
        total_payment_text.setText("0");
        total_paymnet_cleared_text.setText("0");
        product_stock_count.setText("1");
        search_dealer_text.setText("");
        batch_with_location.clear();

    }

    public class update_product_selected extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try {
                params.put("dealer_name",search_dealer_text.getText().toString());
                if(selected_ledger == null)
                {
                    params.put("dealer_phone",selected_product_for_editing.getDealer_phone());
                }
                else
                {
                    params.put("dealer_phone",selected_ledger.getPhone());
                }
                params.put("product_name",product_name.getText().toString());
                params.put("product_mop",product_mop_price.getText().toString());
                params.put("product_mrp",product_mrp_price.getText().toString());
                params.put("brand",product_brand.getText().toString());
                params.put("product_ks",product_ks_price.getText().toString());
                params.put("stock_count",product_stock_count.getText().toString());
                params.put("total_outstanding",total_payment_text.getText().toString());
                params.put("total_cleared_outstanding",total_paymnet_cleared_text.getText().toString());
                params.put("product_id",selected_product_id_for_update);
                params.put("product_tax_percent",product_tax.getText().toString());
                params.put("product_hsn",product_hsn.getText().toString());
                JSONObject batches = new JSONObject();
                Log.d("PARAMS",params.toString());
                for(int i = 0;i < locations.size();i++)
                {
                    if(batch_with_location.get(locations.get(i)) != null)
                    {
                        String batches_at_this_position = TextUtils.join(",",batch_with_location.get(locations.get(i)));
                        batches.put(locations.get(i),batches_at_this_position);
                    }
                    else
                    {
                        batches.put(locations.get(i),"");
                    }
                }
                params.put("batches_with_location",batches);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            Log.d("PARAMSFORUPLOAD",params.toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, appConfig.update_purchase_product_url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        if(response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Product has been successfully updated",Toast.LENGTH_SHORT).show();
                            fadeOut(product_entry_layout);
                            fadeOut(transparent_background);
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Problem in server",Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Problem in server sorry !!",Toast.LENGTH_SHORT).show();
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

    public class cancel_order_selected extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try {
                params.put("dealer_name",products_uploaded.get(selected_position_cancel).getDealer_name());
                params.put("dealer_phone",products_uploaded.get(selected_position_cancel).getDealer_phone());
                params.put("product_id",products_uploaded.get(selected_position_cancel).getProduct_id());
                JSONObject batches = new JSONObject();
                for(int i = 0;i < locations.size();i++)
                {
                        batches.put(locations.get(i),"");
                }
                params.put("batches_with_location",batches);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            Log.d("PARAMSFORUPLOAD",params.toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, appConfig.cancel_purchase_product, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        if(response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Product has been successfully cancelled",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Problem in server",Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Problem in server sorry !!",Toast.LENGTH_SHORT).show();
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

    public class delete_order_selected extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject params = new JSONObject();
            try {
                params.put("dealer_name",products_uploaded.get(selected_order_for_delete).getDealer_name());
                params.put("dealer_phone",products_uploaded.get(selected_order_for_delete).getDealer_phone());
                params.put("product_id",products_uploaded.get(selected_order_for_delete).getProduct_id());
                JSONObject batches = new JSONObject();
                for(int i = 0;i < locations.size();i++)
                {
                    batches.put(locations.get(i),"");
                }
                params.put("batches_with_location",batches);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            Log.d("PARAMSFORUPLOAD",params.toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, appConfig.delete_purchase_product, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        if(response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Product has been successfully deleted please refresh",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Problem in server",Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Problem in server sorry !!",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.stop();
        cameraSource.release();
    }
}
