package in.co.khuranasales.khuranasales.inventoryViewModule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collections;

import in.co.khuranasales.khuranasales.Buy_Now_Activity;
import in.co.khuranasales.khuranasales.MyPromoterProductListAdapter;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels.creditNote;
import in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels.credit_note_ledger;
import in.co.khuranasales.khuranasales.notification.NotificationActivity;
import in.co.khuranasales.khuranasales.notification.NotificationDatabase;

public class creditVoucherInfoActivity extends AppCompatActivity {
    public Toolbar toolbar;
    public TextView ledger_name;
    public TextView credit_note_type;
    public TextView credit_note_date;
    public ListView item_list_view;
    public RelativeLayout item_list_view_container;
    public RelativeLayout total_ledgers_container;
    public ListView total_ledgers_list;
    public RelativeLayout ledgers_item_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_voucher_info_activity_layout);
        creditNote creditNote = inventoriesViewFragment.selected_credit_note;

        ledgers_item_layout = (RelativeLayout)findViewById(R.id.ledger_items_layout);
        if(creditNote.credit_note_items.size() == 0)
        {
            ledgers_item_layout.setVisibility(View.GONE);
        }

        total_ledgers_container = (RelativeLayout)findViewById(R.id.total_ledgers_container);
        total_ledgers_list = (ListView)findViewById(R.id.total_ledgers_list);

        item_list_view_container = (RelativeLayout)findViewById(R.id.list_view_container);
        item_list_view = (ListView)findViewById(R.id.item_list_view);
        credit_note_date = (TextView)findViewById(R.id.credit_voucher_date);
        credit_note_type = (TextView)findViewById(R.id.credit_voucher_type);
        ledger_name = (TextView)findViewById(R.id.ledger_name);

        credit_note_type.setText(creditNote.getCredit_voucher_type()+ "  | " +creditNote.getCompany_name()+" | "+creditNote.getCredit_note_created_by());
        credit_note_date.setText(creditNote.getCredit_date());
        ledger_name.setText(creditNote.getCredit_ledger_name());

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

        Log.d("CREDITNOTELEDGER",""+creditNote.credit_ledgers.size());
        Collections.reverse(creditNote.credit_ledgers);
        creditNote.credit_ledgers.add(new credit_note_ledger("Gross Total","-"+creditNote.getCredit_amount()));
        creditVoucherLedgerAdapter adapter_ledger = new creditVoucherLedgerAdapter(getApplicationContext(),creditNote.credit_ledgers);
        total_ledgers_list.setAdapter(adapter_ledger);
        adapter_ledger.notifyDataSetChanged();
        int totalHeight1 = 0 ;
        int desiredWidth1 = View.MeasureSpec.makeMeasureSpec(total_ledgers_list.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < adapter_ledger.getCount(); i++) {
            View mView = adapter_ledger.getView(i, null, total_ledgers_list);

            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),

                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight1 += mView.getMeasuredHeight();
            Log.w("HEIGHT" + i, String.valueOf(totalHeight1));
        }
        totalHeight1 = totalHeight1
                + (total_ledgers_list.getDividerHeight() * (adapter_ledger.getCount() - 1));
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,totalHeight1);
        params1.setMargins(10,10,10,10);
        total_ledgers_container.setLayoutParams(params1);


        creditVoucherItemAdapter adapter1 = new creditVoucherItemAdapter(getApplicationContext(),creditNote.credit_note_items);
        item_list_view.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
        int totalHeight = 0 ;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(item_list_view.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < adapter1.getCount(); i++) {
            View mView = adapter1.getView(i, null, item_list_view);

            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),

                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
            Log.w("HEIGHT" + i, String.valueOf(totalHeight));
        }
        totalHeight = totalHeight
                + (item_list_view.getDividerHeight() * (adapter1.getCount() - 1));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,totalHeight);
        params.setMargins(10,10,10,10);
        item_list_view_container.setLayoutParams(params);
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
                Intent intent = new Intent(creditVoucherInfoActivity.this,Buy_Now_Activity.class);
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
        return  true;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

