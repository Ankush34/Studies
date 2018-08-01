package in.co.khuranasales.khuranasales.exportWorkers;

import android.animation.Animator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class exportMainActivity extends AppCompatActivity {
    public Toolbar toolbar;
    public RelativeLayout background_transparent;

    public FadeOutAnimator animator;
    public FadeInAnimator in_animator;

    public RelativeLayout select_promoter_export_orders;
    public RelativeLayout select_brand_export_stock;
    public RelativeLayout select_brand_export_batch;
    public RelativeLayout select_type_export_payment;

    public CardView card_export_orders;
    public CardView card_export_stock;
    public CardView card_export_payments;
    public CardView card_export_batch;

    public ImageView down_arrow_export_order;
    public ImageView down_arrow_select_brand_export_stock;
    public ImageView down_arrow_select_brand_export_batch;
    public ImageView down_arrow_select_type_payment_export;

    public RecyclerView brands_select_export_stock_recycler;
    public select_options_export_recycler_adapter brands_select_export_stock_recycler_adapter;
    public RecyclerView brands_select_export_batch_recycler;
    public select_options_export_recycler_adapter brands_select_export_batch_recycler_adapter;
    public RecyclerView promoters_select_export_orders_recycler;
    public select_options_export_recycler_adapter promoters_select_export_orders_recycler_adapter;
    public RecyclerView payment_type_select_export_batch_recycler;
    public select_options_export_recycler_adapter payment_type_select_export_batch_recycler_adapter;

    public ArrayList<selectionPojo> selection_list_promoters = new ArrayList<>();
    public ArrayList<selectionPojo> selection_list_brands = new ArrayList<>();
    public ArrayList<selectionPojo> selection_list_payment_types = new ArrayList<>();

    public Button promoter_selected_button;
    public ArrayList<OrderDetails> order_details = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_main_activity);
        background_transparent = (RelativeLayout)findViewById(R.id.background_transparent);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        promoter_selected_button = (Button)findViewById(R.id.promoters_selected_button);
        promoter_selected_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new get_promoters_orders().execute();
            }
        });

        down_arrow_export_order = (ImageView)findViewById(R.id.down_arrow_promoter_list);
        down_arrow_select_brand_export_stock = (ImageView)findViewById(R.id.down_arrow_brand_select_export_stock);
        down_arrow_select_type_payment_export = (ImageView)findViewById(R.id.down_arrow_type_select_payment_export);
        down_arrow_select_brand_export_batch = (ImageView)findViewById(R.id.down_arrow_brand_select_export_batch);

        card_export_orders = (CardView)findViewById(R.id.layout_export_orders);
        card_export_stock = (CardView)findViewById(R.id.layout_export_stock);
        card_export_payments = (CardView)findViewById(R.id.layout_export_payment);
        card_export_batch = (CardView)findViewById(R.id.layout_export_batch);

        select_promoter_export_orders = (RelativeLayout)findViewById(R.id.select_promoter_list_export_layout);
        select_brand_export_stock = (RelativeLayout)findViewById(R.id.select_brand_list_export_stock_layout);
        select_brand_export_batch = (RelativeLayout)findViewById(R.id.select_brand_list_export_batch_layout);
        select_type_export_payment = (RelativeLayout)findViewById(R.id.select_payment_type_export_layout);

        promoters_select_export_orders_recycler = (RecyclerView)findViewById(R.id.recycler_promoter_selection);
        promoters_select_export_orders_recycler_adapter = new select_options_export_recycler_adapter(this,selection_list_promoters);
        promoters_select_export_orders_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        promoters_select_export_orders_recycler.setHasFixedSize(true);
        promoters_select_export_orders_recycler.setAdapter(promoters_select_export_orders_recycler_adapter);
        promoters_select_export_orders_recycler_adapter.notifyDataSetChanged();

            brands_select_export_stock_recycler = (RecyclerView)findViewById(R.id.recycler_brand_selection_export_stock);
        brands_select_export_stock_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        brands_select_export_stock_recycler_adapter = new select_options_export_recycler_adapter(this, selection_list_brands);
        brands_select_export_stock_recycler.setHasFixedSize(true);
        brands_select_export_stock_recycler.setAdapter(brands_select_export_stock_recycler_adapter);
        brands_select_export_stock_recycler_adapter.notifyDataSetChanged();

        brands_select_export_batch_recycler = (RecyclerView)findViewById(R.id.recycler_brand_selection_export_batch);
        brands_select_export_batch_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        brands_select_export_batch_recycler_adapter = new select_options_export_recycler_adapter(this, selection_list_brands);
        brands_select_export_batch_recycler.setHasFixedSize(true);
        brands_select_export_batch_recycler.setAdapter(brands_select_export_batch_recycler_adapter);
        brands_select_export_batch_recycler_adapter.notifyDataSetChanged();

        payment_type_select_export_batch_recycler = (RecyclerView)findViewById(R.id.recycler_payment_type);
        payment_type_select_export_batch_recycler.setHasFixedSize(true);
        payment_type_select_export_batch_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        payment_type_select_export_batch_recycler_adapter = new select_options_export_recycler_adapter(this, selection_list_payment_types);
        payment_type_select_export_batch_recycler.setAdapter(payment_type_select_export_batch_recycler_adapter);
        payment_type_select_export_batch_recycler_adapter.notifyDataSetChanged();

        new load_promoters().execute();
        new load_brands().execute();
        selectionPojo p = new selectionPojo("Cash",false);
        selection_list_payment_types.add(p);
        selectionPojo p1 = new selectionPojo("Card",false);
        selection_list_payment_types.add(p1);
        selectionPojo p2 = new selectionPojo("Finance",false);
        selection_list_payment_types.add(p2);
        selectionPojo p3 = new selectionPojo("Paytm",false);
        selection_list_payment_types.add(p3);
        selectionPojo p4 = new selectionPojo("Cheque",false);
        selection_list_payment_types.add(p4);


        card_export_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn(background_transparent);
                slideToTop(select_promoter_export_orders);
            }
        });
        card_export_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn(background_transparent);
                slideToTop(select_brand_export_stock);
            }
        });
        card_export_payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn(background_transparent);
                slideToTop(select_type_export_payment);
            }
        });
        card_export_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn(background_transparent);
                slideToTop(select_brand_export_batch);
            }
        });

        down_arrow_export_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(background_transparent);
                slideToBottom(select_promoter_export_orders);
            }
        });

        down_arrow_select_brand_export_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(background_transparent);
                slideToBottom(select_brand_export_stock);
            }
        });

        down_arrow_select_type_payment_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(background_transparent);
                slideToBottom(select_type_export_payment);
            }
        });

        down_arrow_select_brand_export_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(background_transparent);
                slideToBottom(select_brand_export_batch);
            }
        });
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

    public class load_promoters extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest request_load_promoters = new JsonArrayRequest(AppConfig.load_promoters_url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i =0 ; i < response.length();i++)
                    {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            selectionPojo p = new selectionPojo(obj.getString("promoter_name"),false);
                            p.setPromoter_email(obj.getString("promoter_email"));
                            selection_list_promoters.add(p);
                            promoters_select_export_orders_recycler_adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.getMessage() != null)
                    {
                        Toast.makeText(getApplicationContext(),"Volley Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Some Volley Error Has Occured",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            AppController.getInstance().addToRequestQueue(request_load_promoters);
            return null;
        }
    }

    public class load_brands extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest request_brands = new JsonArrayRequest(AppConfig.url_categories+"?category=mobile", new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i =0 ; i < response.length();i++)
                    {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            selectionPojo p = new selectionPojo(obj.getString("brand"),false);
                            selection_list_brands.add(p);
                            brands_select_export_batch_recycler_adapter.notifyDataSetChanged();
                            brands_select_export_stock_recycler_adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.getMessage() != null)
                    {
                        Toast.makeText(getApplicationContext(),"Volley Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Some Volley Error Has Occured",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            AppController.getInstance().addToRequestQueue(request_brands);
            return null;
        }
    }

    public class load_payment_types extends  AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
           JsonArrayRequest request_payment_types = new JsonArrayRequest("", new Response.Listener<JSONArray>() {
               @Override
               public void onResponse(JSONArray response) {
                   for(int i =0 ; i < response.length();i++)
                   {
                       try {
                           JSONObject obj = response.getJSONObject(i);
                           selectionPojo p = new selectionPojo(obj.getString("name"),false);
                           selection_list_payment_types.add(p);
                       } catch (Exception e) {
                           e.printStackTrace();
                       }

                   }
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   if(error.getMessage() != null)
                   {
                       Toast.makeText(getApplicationContext(),"Volley Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
                   }else
                   {
                       Toast.makeText(getApplicationContext(),"Some Volley Error Has Occured",Toast.LENGTH_SHORT).show();
                   }
               }
           });
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            overridePendingTransition(0,R.anim.slide_out_left_animation);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_service_centers,menu);
        return true;
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

    public class get_promoters_orders extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            order_details.clear();
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c);
            JSONObject params_json = new JSONObject();
            JSONArray array = new JSONArray();
            ArrayList<selectionPojo> selection_list = promoters_select_export_orders_recycler_adapter.selection_list;
            for(int i = 0; i <selection_list.size();i++)
            {
                if(selection_list.get(i).is_selected()) {
                    try {
                        JSONObject object = new JSONObject();
                        Log.d("EXPORT",selection_list.get(i).getPromoter_email());
                        object.put("email", selection_list.get(i).getPromoter_email());
                        object.put("from", "26-06-2018");
                        object.put("to", formattedDate);
                        array.put(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                params_json.put("promoters", array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest get_promoters_data = new JsonObjectRequest(Request.Method.POST,AppConfig.load_promoters_orders,params_json,
                    new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.d("exportMainActivity","response: "+response.toString());
                        if(response.getString("success").equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),"Successfully received order",Toast.LENGTH_LONG).show();
                            JSONArray array = response.getJSONArray("response");
                            for(int i = 0; i < array.length();i++)
                            {
                                JSONObject obj  = array.getJSONObject(i);
                                OrderDetails details = new OrderDetails();
                                details.setPromoter_name(obj.getString("promoter_name"));
                                details.setProduct_name(obj.getString("name"));
                                details.setTotal_price(obj.getString("price"));
                                details.setTotal_count(obj.getString("count"));
                                details.setDiscount_amount(obj.getString("discount_offered"));
                                details.setCustomer_name(obj.getString("customer_name"));
                                order_details.add(details);
                            }
                            generatePromoterExport(order_details);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Please Select other promoters retry !!",Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("BuyNow Volley Error: ","Error in upload promoters request"+error.getMessage());
                    Toast.makeText(getApplicationContext(),"Please Select other promoters retry !!",Toast.LENGTH_LONG).show();
                }
            });
            AppController.getInstance().addToRequestQueue(get_promoters_data);
        return null;
        }
    }

    public void generatePromoterExport(ArrayList<OrderDetails> order_details) {
        String state = Environment.getExternalStorageState();
        String root = "";
        String fileName = "/invoice" + System.currentTimeMillis() + ".pdf";
        String parent = "KhuranaSales";
        File mFile;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            root = Environment.getExternalStorageDirectory().toString();
            mFile = new File(root, parent);
            if (!mFile.isDirectory())
                mFile.mkdirs();
        } else {
            root = exportMainActivity.this.getFilesDir().toString();
            mFile = new File(root, parent);
            if (!mFile.isDirectory())
                mFile.mkdirs();
        }
        String strCaptured_FileName = root + "/KhuranaSales" + fileName;
        String targetPdf = strCaptured_FileName;
        File filePath = new File(targetPdf);
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Khurana sales business solutions");
            document.addCreator("Ankush Amit Khurana");

            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            PdfPTable table = new PdfPTable(3);
            table.addCell(getCell("Export Date: "+(new Date().getTime()), PdfPCell.ALIGN_LEFT));
            table.addCell(getCell("Khurana Sales Promoters Orders Export",PdfPCell.ALIGN_CENTER));
            table.addCell(getCell("Email: khuranasales2015@gmail.com",PdfPCell.ALIGN_RIGHT));
            table.setWidthPercentage(100);
            table.setSpacingAfter(20);
            document.add(table);

            Font tableFont = FontFactory.getFont(FontFactory.HELVETICA,10,Font.NORMAL);
            PdfPTable table_promoter = new PdfPTable(6);
            table_promoter.addCell(new Paragraph("Promoter Name",tableFont));
            table_promoter.addCell(new Paragraph("Product Name",tableFont));
            table_promoter.addCell(new Paragraph("Sold Count",tableFont));
            table_promoter.addCell(new Paragraph("Total",tableFont));
            table_promoter.addCell(new Paragraph("Applied Discount",tableFont));
            table_promoter.addCell(new Paragraph("Customer Name",tableFont));
            table_promoter.setHeaderRows(1);
            for(int i = 0 ;i < order_details.size();i++)
            {
                table_promoter.addCell(new Paragraph(order_details.get(i).getPromoter_name(),tableFont));
                table_promoter.addCell(new Paragraph(order_details.get(i).getProduct_name(),tableFont));
                table_promoter.addCell(new Paragraph(order_details.get(i).getTotal_count(),tableFont));
                table_promoter.addCell(new Paragraph(order_details.get(i).getTotal_price(),tableFont));
                table_promoter.addCell(new Paragraph(order_details.get(i).getDiscount_amount(),tableFont));
                table_promoter.addCell(new Paragraph(order_details.get(i).getCustomer_name(),tableFont));
            }
            table_promoter.setWidthPercentage(100);
            document.add(table_promoter);
            document.close();
            Toast.makeText(getApplicationContext(),"Export has been created",Toast.LENGTH_LONG).show();
        }
         catch (Exception e) {
            e.printStackTrace();
             Toast.makeText(getApplicationContext(),"Sorry error occured while creating export pleasr retry !! ",Toast.LENGTH_LONG).show();
         }
    }
    public PdfPCell getCell(String text, int alignment) {
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Paragraph(text,paragraphFont));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

}
