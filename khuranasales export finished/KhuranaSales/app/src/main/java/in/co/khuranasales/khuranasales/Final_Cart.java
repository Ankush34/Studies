package in.co.khuranasales.khuranasales;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Example of using Folding Cell with ListView and ListAdapter
 */
public class Final_Cart extends AppCompatActivity {
    public  static FoldingCellListAdapter adapter;
    public int mYear = 0;
    public int mDay = 0;
    public static AppConfig appConfig;
    public int mMonth = 0;
    public static String date = "";
    public static ArrayList<Product> items = new ArrayList<Product>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_main1);
        appConfig = new AppConfig(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView theListView = (ListView) findViewById(R.id.mainListView);
        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        // prepare elements to display
        new Item.LoadOutbox().execute(appConfig.getUser_email());
        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
        // set elements to adapter
        adapter = new FoldingCellListAdapter(this,items);

        theListView.setAdapter(adapter);
        // set on click event listener to list view
        adapter.notifyDataSetChanged();
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_final_cart, menu);
        final View action_profile = menu.findItem(R.id.action_profile).getActionView();
        action_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(appConfig.isLogin())
                {
                    Intent intent = new Intent(Final_Cart.this,Final_Cart.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Final_Cart.this,"Please login to proceed",Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart= menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Final_Cart.this,Buy_Now_Activity.class);
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
                Toast.makeText(Final_Cart.this,"You are already logged in",Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent = new Intent(Final_Cart.this,Activity_Login.class);
                startActivity(intent);
            }
        }else if(id == R.id.signout)
        {
            if(appConfig.isLogin())
            {
                Intent intent = new Intent(Final_Cart.this,Activity_Login.class);
                startActivity(intent);
            }else{
                Toast.makeText(Final_Cart.this," Please login to signout",Toast.LENGTH_LONG).show();
            }
        }else if(id == R.id.signup)
        {
            Intent intent = new Intent(Final_Cart.this,MainActivity.class);
            startActivity(intent);
        }
        else if(id  == android.R.id.home)
        {
            finish();
            overridePendingTransition(0,R.anim.slide_out_left_animation);

        }else if(id == R.id.action_calender)
        {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.AppTheme_DialogTheme, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {

                    Log.d("Date Selected:",dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    String date_selected = formatDate(year,monthOfYear,dayOfMonth);
                     date = date_selected;
                            Toast.makeText(getApplicationContext(),"Refreshing For Date: "+date_selected,Toast.LENGTH_LONG).show();
                    new Item.LoadOutbox().execute(appConfig.getUser_email());
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    private static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(date);
    }
}
