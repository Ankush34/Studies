package in.co.khuranasales.khuranasales;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;


public class Product_desc_activity extends AppCompatActivity {

    ScrollerViewPager viewPager;
    public Button b1;
    public Button b2;
    public NumberPicker numpik1;
    public static AppConfig appConfig;
    public NumberPicker numpik2;
    public TextView dsc1;
    public TextView dsc2;
    public TextView dsc3;
    public TextView dsc4;
    public ImageView ks_price_image;
    public TextView ks_price_title;
    public static String arr_link[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_desc_activity);
       ks_price_image = (ImageView)findViewById(R.id.ksprice);
       ks_price_title = (TextView)findViewById(R.id.ksprice_title);
        appConfig = new AppConfig(getApplicationContext());
        viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        dsc1=(TextView)findViewById(R.id.desc1);
        dsc2=(TextView)findViewById(R.id.desc2);
        dsc3=(TextView)findViewById(R.id.desc3);
        dsc4=(TextView)findViewById(R.id.desc4);
        if(appConfig.getUserType().equals("Admin") || appConfig.getUserType().equals("Dealer"))
        {
         dsc3.setVisibility(View.VISIBLE);
        ks_price_title.setVisibility(View.VISIBLE);
        ks_price_image.setVisibility(View.VISIBLE);
        }
        else
        {
            dsc3.setVisibility(View.GONE);
            ks_price_title.setVisibility(View.GONE);
            ks_price_image.setVisibility(View.GONE);

        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final String name=getIntent().getExtras().getString("name");
        final int stock=getIntent().getExtras().getInt("stock");
        final String link=getIntent().getExtras().getString("link");
        final int mrp=getIntent().getExtras().getInt("mrp");
        final int mop=getIntent().getExtras().getInt("mop");
        final int ksprice=getIntent().getExtras().getInt("ksprice");
        final int product_id = getIntent().getExtras().getInt("product_id");
        Log.d("product_id",""+product_id);
        dsc1.setText(" "+mrp+"  /-");
        dsc2.setText(" "+mop+"  /-");

        dsc3.setText(" "+ksprice+"  /-");
        dsc4.setText(" "+stock+"  Units");
        arr_link=link.split(",");
        numpik2=(NumberPicker)findViewById(R.id.numpik2);

        numpik2.setMinValue(1);

        numpik2.setMaxValue(stock);
        numpik2.setValue(1);

        List<String> list1=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        for(int a=0;a<arr_link.length;a++)
        {
            list1.add(""+a);
            list2.add(arr_link[a]);
        }
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        PagerModelManager manager = new PagerModelManager();
        manager.addCommonFragment(GuideFragment.class,list2,list1);
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();
        // just set viewPager
        springIndicator.setViewPager(viewPager);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(appConfig.isLogin())
                {
                    final int count_selected = numpik2.getValue();
                    Product p1=new Product(name,count_selected);
                    p1.setProduct_id(product_id);
                    p1.setPrice_ks(ksprice);
                    p1.setPrice_mrp(mrp);
                    p1.setPrice_mop(mop);
                    add_to_cart(p1);
                }
                else
                {
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    Log.d("Insert: ", "Inserting ..");

                    Product p1=new Product(name,numpik2.getValue());
                    Log.d("Product Id", ""+product_id);
                    p1.setProduct_id(product_id);
                    p1.setPrice_ks(ksprice);
                    p1.setPrice_mrp(mrp);
                    p1.setPrice_mop(mop);
                    boolean found=db.check_avail(p1.getProduct_id());
                    if(found==true)
                    {
                        int count=db.get_bought_count(p1.getProduct_id());
                        Log.d("Final: ","product found in cart already: "+count);
                        Log.d("internal cart count :",""+count);
                        final int count_selected=numpik2.getValue();
                        int count_final=count+count_selected;
                        db.set_bought_count(p1,count_final);
                        Log.d("updated the count:",""+count_final);
                    }
                    else
                    {
                        Log.d("Final: ","product not found in cart ");
                        db.addProduct(p1);
                    }
                    Toast.makeText(getApplicationContext(),"Product Successffully Added to your Bag ", Toast.LENGTH_LONG).show();
                    Log.d("Reading: ", "Reading all products..");
                    List<Product> products = db.getAllProducts();

                    for (Product cn : products) {
                        String log = "Name: " + cn.get_Name()+ " ,stock: " + cn.get_Stock()+",ks: " + cn.getPrice_ks();
                        Log.d("Name: ", log);
                    }

                }

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appConfig.isLogin())
                {
                    final int count_selected=numpik2.getValue();
                    Product p1=new Product(name,count_selected);
                    p1.setProduct_id(product_id);
                    p1.setPrice_ks(ksprice);
                    p1.setPrice_mrp(mrp);
                    p1.setPrice_mop(mop);
                    add_to_cart(p1);
                }
                else
                {
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    Log.d("Insert: ", "Inserting ..");

                    Product p1=new Product(name,numpik2.getValue());
                    p1.setProduct_id(product_id);
                    p1.setPrice_ks(ksprice);
                    p1.setPrice_mrp(mrp);
                    p1.setPrice_mop(mop);
                    boolean found=db.check_avail(p1.getProduct_id());
                    if(found==true)
                    {
                        int count=db.get_bought_count(p1.getProduct_id());
                        Log.d("internal cart count :",""+count);
                        Log.d("Final: ","product found in cart already: "+count);
                        final int count_selected=numpik2.getValue();
                        int count_final=count+count_selected;
                        db.set_bought_count(p1,count_final);
                        Log.d("updated the count:",""+count_final);
                    }
                    else
                    {
                        Log.d("Final: ","product not found in cart ");
                        db.addProduct(p1);
                    }
                    Toast.makeText(getApplicationContext(),"Product Successffully Added to your Bag ", Toast.LENGTH_LONG).show();
                    Log.d("Reading: ", "Reading all products..");
                    List<Product> products = db.getAllProducts();

                    for (Product cn : products) {
                        String log = "Name: " + cn.get_Name()+ " ,stock: " + cn.get_Stock()+",ks: " + cn.getPrice_ks();
                        Log.d("Name: ", log);
                    }

                }
                Intent intent=new Intent(getApplicationContext(),Buy_Now_Activity.class);
                startActivity(intent);
            }
        });

    }
public void add_to_cart(Product p1)
    {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);

        String url=AppConfig.url_set_viewed;
        url=url+"?count="+p1.get_Stock()+"&id="+p1.getProduct_id()+"&customer_email="+appConfig.getUser_email()+"&date="+formattedDate;
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_my_cart, menu);
        final View action_profile = menu.findItem(R.id.action_profile).getActionView();
        action_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appConfig.isLogin())
                {
                    Intent intent = new Intent(Product_desc_activity.this,Final_Cart.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Product_desc_activity.this,"Please login to proceed",Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart= menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Product_desc_activity.this,Buy_Now_Activity.class);
                startActivity(intent);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.signin) {
            if(appConfig.isLogin())
            {
                Toast.makeText(Product_desc_activity.this,"You are already logged in",Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent = new Intent(Product_desc_activity.this,Activity_Login.class);
                startActivity(intent);
            }
        }else if(id == R.id.signout)
        {
            if(appConfig.isLogin())
            {
                Intent intent = new Intent(Product_desc_activity.this,Activity_Login.class);
                startActivity(intent);

            }else{
                Toast.makeText(Product_desc_activity.this," Please login to signout",Toast.LENGTH_LONG).show();

            }
        }else if(id == R.id.signup)
        {
            Intent intent = new Intent(Product_desc_activity.this,MainActivity.class);
            startActivity(intent);
        }
        else if(id == android.R.id.home)
        {
            finish();
            overridePendingTransition(0,R.anim.slide_out_left_animation);
        }
        return super.onOptionsItemSelected(item);
    }


}
