package in.co.khuranasales.khuranasales.inventoryViewModule;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.Buy_Now_Activity;
import in.co.khuranasales.khuranasales.CategorizeDataActivity;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.notification.NotificationActivity;
import in.co.khuranasales.khuranasales.notification.NotificationDatabase;

public class inventoryViewMainActivity extends AppCompatActivity {
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private Toolbar toolbar;
    private RelativeLayout select_date_range_layout;
    private RelativeLayout layout_date_presenter;
    private FadeInAnimator in_animator;
    private FadeOutAnimator animator;
    private RelativeLayout layout_back_selector;

    private RecyclerView date_period_recycler;
    private dateSelectorAdapter date_selector_adapter;
    private ArrayList<datePeriodPojo> date_periods= new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_view_main_activity);

        date_period_recycler = (RecyclerView)findViewById(R.id.period_selector_recycler);
        date_period_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        date_period_recycler.setHasFixedSize(true);
        fill_date_period_pojos();
        date_selector_adapter = new dateSelectorAdapter(date_periods,this);
        date_period_recycler.setAdapter(date_selector_adapter);
        date_selector_adapter.notifyDataSetChanged();


        select_date_range_layout = (RelativeLayout)findViewById(R.id.layout_date_range_selector);
        layout_date_presenter = (RelativeLayout)findViewById(R.id.layout_date_presenter);
        layout_date_presenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn(select_date_range_layout);
            }
        });
        layout_back_selector = (RelativeLayout)findViewById(R.id.layout_back_date_selector);
        layout_back_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(select_date_range_layout);
            }
        });
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
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
        FragmentManager manager = getSupportFragmentManager();
        inventoriesViewFragment fragment_inventories_view  = new inventoriesViewFragment();
        while (manager.getBackStackEntryCount() > 0) {
            manager.popBackStackImmediate();
        }
        manager.beginTransaction()
                .replace(R.id.inventory_view_fragment, fragment_inventories_view)
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit();
    }

    public void fill_date_period_pojos()
    {
        datePeriodPojo pojo9 = new datePeriodPojo();
        pojo9.setDate_period_name("Custom Date");
        date_periods.add(pojo9);

        datePeriodPojo pojo = new datePeriodPojo();
        pojo.setDate_period_name("Today");
        date_periods.add(pojo);

        datePeriodPojo pojo1 = new datePeriodPojo();
        pojo1.setDate_period_name("Yesterday");
        date_periods.add(pojo1);

        datePeriodPojo pojo2 = new datePeriodPojo();
        pojo2.setDate_period_name("Last Week");
        date_periods.add(pojo2);

        datePeriodPojo pojo3 = new datePeriodPojo();
        pojo3.setDate_period_name("Last 15 days");
        date_periods.add(pojo3);

        datePeriodPojo pojo5 = new datePeriodPojo();
        pojo5.setDate_period_name("This Month");
        date_periods.add(pojo5);

        datePeriodPojo pojo4 = new datePeriodPojo();
        pojo4.setDate_period_name("Last Month");
        date_periods.add(pojo4);

        datePeriodPojo pojo6 = new datePeriodPojo();
        pojo6.setDate_period_name("Last 2 Months");
        date_periods.add(pojo6);

        datePeriodPojo pojo7 = new datePeriodPojo();
        pojo7.setDate_period_name("Last 6 Months");
        date_periods.add(pojo7);

        datePeriodPojo pojo8 = new datePeriodPojo();
        pojo8.setDate_period_name("This Year");
        date_periods.add(pojo8);

        datePeriodPojo pojo10 = new datePeriodPojo();
        pojo10.setDate_period_name("Last Year");
        date_periods.add(pojo10);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_categorize_toolbar, menu);

        final View notificaitons = menu.findItem(R.id.actionNotifications).getActionView();
        NotificationDatabase database = new NotificationDatabase(getApplicationContext());
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
                Intent intent = new Intent(inventoryViewMainActivity.this,Buy_Now_Activity.class);
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
        }
        return true;
        }

    @Override
    public void onBackPressed() {
        finish();
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
}
