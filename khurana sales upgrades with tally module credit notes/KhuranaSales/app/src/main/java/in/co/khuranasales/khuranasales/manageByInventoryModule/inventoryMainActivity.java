package in.co.khuranasales.khuranasales.manageByInventoryModule;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.Buy_Now_Activity;
import in.co.khuranasales.khuranasales.Final_Cart;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.prebooking_module.prebooking_products_activity;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class inventoryMainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private AppConfig appConfig;
    ImageView dashboard ;
    ImageView items_view;
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private itemsFragment itemFragment = new itemsFragment();
    private dashBoardFragment dashBoardFragment = new dashBoardFragment();
    private int selected_fragment_index = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appConfig = new AppConfig(getApplicationContext());
        setContentView(R.layout.inventory_main_activity_layout);
        dashboard = (ImageView)findViewById(R.id.dashboard);
        items_view =(ImageView)findViewById(R.id.item);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       fragmentManager = getSupportFragmentManager();

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStackImmediate();
                }
                selected_fragment_index = 1;
                fragmentManager.beginTransaction()
                        .replace(R.id.dynamically_load_fragment, dashBoardFragment)
                        .addToBackStack(BACK_STACK_ROOT_TAG)
                        .commit();
            }
        });

        items_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStackImmediate();
                }
                selected_fragment_index =2;
                itemFragment = new itemsFragment();
                itemFragment.categories_stack.removeAllElements();
                fragmentManager.beginTransaction()
                        .replace(R.id.dynamically_load_fragment, itemFragment)
                        .addToBackStack(BACK_STACK_ROOT_TAG)
                        .commit();

            }
        });
       }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_new_product, menu);
        final View action_profile = menu.findItem(R.id.action_profile).getActionView();
        action_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appConfig.isLogin()) {
                    Intent intent = new Intent(inventoryMainActivity.this, Final_Cart.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(inventoryMainActivity.this, "Please login to proceed", Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart = menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inventoryMainActivity.this, Buy_Now_Activity.class);
                startActivity(intent);
            }
        });
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        selected_fragment_index = 1;
        fragmentManager.beginTransaction()
                .replace(R.id.dynamically_load_fragment, new dashBoardFragment())
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit();
    }

    @Override
    public void onBackPressed() {
    if(selected_fragment_index == 1)
    {
        finish();
    }
    else if(selected_fragment_index == 2)
    {
        itemFragment.onBackPressed();
    }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if(selected_fragment_index == 1)
            {
                finish();
            }
            else if(selected_fragment_index == 2)
            {
                itemFragment.onBackPressed();
            }
        }
        return true;
    }
}
