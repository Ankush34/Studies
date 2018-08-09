package in.co.khuranasales.khuranasales;

import android.animation.Animator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Buy_final_activity extends AppCompatActivity {
    public Intent intent ;
    public RecyclerView productsList;
    public RecyclerView addressList;
    public Button buy_final;
    public int cash_payment;
    public static AppConfig appConfig;
    public boolean partial_cash_payment = false;
    public int total_price_of_products = 0;
    public RelativeLayout payment_modes_selection_layout;
    public RecyclerView payment_modes_selection_recycler;
    public ImageView payment_mode;
    public ImageView back_address_selection;
    public ImageView close_finance;
    public ImageView close_cheque;
    public ImageView close_card;
    public ImageView close_paytm;
    public ImageView back_address_entry;
    public List<View> views_visible = Collections.synchronizedList(new ArrayList<View>());
    public ImageView back_payment_selection;
    public Button address_entry_done;
    public Button address_selection_done;
    public RecyclerView.LayoutManager layoutManager;
    public RelativeLayout address_selection;
    public RelativeLayout address_entry;
    public Button select_address;
    public static RecyclerView.Adapter adapter1;
    public static RecyclerView.Adapter adapter2;
    public Button payment_modes_selected;
    public ImageView add_address_button;
    public Boolean multiple_selected = false;
    public static ArrayList<Product> list = new ArrayList<Product>();
    public static List<Address> address = new ArrayList<>();
    public ArrayList<String> payment_modes = new ArrayList<>();
    public RelativeLayout payment_mode_cheque;
    public RelativeLayout payment_mode_card;
    public RelativeLayout payment_mode_paytm;
    public RelativeLayout payment_mode_finance;
    public RecyclerView.Adapter adapter_payment_modes;

    private TextView financer_name;
    private EditText finance_amount;
    private EditText finance_processing_payment;
    private EditText finance_file_number;

    private EditText paytm_amount;

    private EditText card_payment_amount;
    private TextView card_bank_name;

    private EditText cheque_number;
    private TextView cheque_bank_name;
    private EditText cheque_amount;

    private RelativeLayout bank_selection_layout;
    private ImageView back_bank_selection;
    private RecyclerView bank_name_recycler;
    private FinanceBankNameAdapter bank_name_recycler_adapter;
    private  ArrayList<String> banks = new ArrayList<>();

    private RelativeLayout finance_selection_layout;
    private ImageView back_finance_selection;
    private RecyclerView finance_name_recycler;
    private FinanceBankNameAdapter finance_name_recycler_adapter;
    private  ArrayList<String> financers = new ArrayList<>();
    private RelativeLayout order_confirmation_layout ;
    private ImageView back_order_confirmation;
    private Button confirm_order;
    private TextView paytm_amount_confirm;
    private TextView cheque_amount_confirm;
    private TextView finance_amount_confirm;
    private TextView cash_amount_confirm;
    private TextView card_amount_confirm;
    private TextView total_amount_confirm;

    private RelativeLayout view_batch_layout;
    private RecyclerView view_batch_recycler;
    private ImageView back_view_batch;
    private BatchViewAdapter batch_view_adapter;
    private TextView product_name_batch_description;
    private ArrayList<Batch> batch_numbers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.clear();
        address.clear();
        setContentView(R.layout.buy_final_activity);

        view_batch_layout = (RelativeLayout)findViewById(R.id.view_batch_numbers_layout);
        view_batch_recycler = (RecyclerView)findViewById(R.id.recycler_view_batch);
        back_view_batch = (ImageView)findViewById(R.id.back_batch_view);
        batch_view_adapter = new BatchViewAdapter(this,batch_numbers);
        view_batch_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        view_batch_recycler.setHasFixedSize(true);
        view_batch_recycler.setAdapter(batch_view_adapter);
        product_name_batch_description = (TextView)findViewById(R.id.product_name_batch_description);
        batch_view_adapter.notifyDataSetChanged();

        back_view_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade_out_bank_selection(view_batch_layout);
            }
        });

        recyclerViewItemClickListener view_batch_numbers_listener = (view, position) -> {
            // fadeIn(batch_selection_layout);
            fade_in_bank_selection(view_batch_layout);
            product_name_batch_description.setText(list.get(position).get_Name());
            batch_view_adapter.batches.clear();
            batch_view_adapter.batches.addAll(list.get(position).getSelected_batch_numbers());
            batch_view_adapter.notifyDataSetChanged();
        };


        appConfig = new AppConfig(getApplicationContext());
        intent = new Intent(getApplicationContext(), Invoice_Activity.class);
        intent.putExtra("class_name","buy_final_activity");

        order_confirmation_layout = (RelativeLayout)findViewById(R.id.order_confirmation_layout);
        back_order_confirmation = (ImageView)findViewById(R.id.back_order_confirmation);
        confirm_order = (Button)findViewById(R.id.confirm_order);
        paytm_amount_confirm = (TextView)findViewById(R.id.paytm_amount_confirm);
        cheque_amount_confirm = (TextView)findViewById(R.id.cheque_amount_confirm);
        card_amount_confirm = (TextView)findViewById(R.id.card_amount_confirm);
        cash_amount_confirm = (TextView)findViewById(R.id.cash_amount_confirm);
        finance_amount_confirm = (TextView)findViewById(R.id.finance_amount_confirm);
        total_amount_confirm = (TextView)findViewById(R.id.total_amount_confirm);
        confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade_out_bank_selection(order_confirmation_layout);
                set_baught();
               // new check_availablity().execute();
            }
        });
        back_order_confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade_out_bank_selection(order_confirmation_layout);
            }
        });
        bank_name_recycler = (RecyclerView)findViewById(R.id.bank_recycler);
        recyclerViewItemClickListener cheque_bank_selection_listener = (view, position) -> {
            cheque_bank_name.setText(banks.get(position));
            fade_out_bank_selection(bank_selection_layout);
        };
        recyclerViewItemClickListener card_bank_selection_listener = (view, position) -> {
            card_bank_name.setText(banks.get(position));
           fade_out_bank_selection(bank_selection_layout);
        };
        bank_name_recycler_adapter = new FinanceBankNameAdapter(this,banks,cheque_bank_selection_listener);
        bank_name_recycler.setHasFixedSize(true);
        bank_name_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        bank_name_recycler.setAdapter(bank_name_recycler_adapter);
        bank_name_recycler_adapter.notifyDataSetChanged();
        new LoadBanks().execute("Banks");
        bank_selection_layout = (RelativeLayout)findViewById(R.id.bank_selection_layout);
        back_bank_selection = (ImageView)findViewById(R.id.back_bank_selection);
        back_bank_selection.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
           fade_out_bank_selection(bank_selection_layout);
         }
         });

        cheque_number = (EditText) findViewById(R.id.cheque_number);
        cheque_bank_name = (TextView) findViewById(R.id.cheque_bank_name);
        cheque_bank_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fade_in_bank_selection(bank_selection_layout);
               bank_name_recycler_adapter.setListener(cheque_bank_selection_listener);
            }
            });

        cheque_amount = (EditText) findViewById(R.id.cheque_amount);

        card_bank_name = (TextView) findViewById(R.id.card_bank);
        card_bank_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade_in_bank_selection(bank_selection_layout);
                bank_name_recycler_adapter.setListener(card_bank_selection_listener);
            }
        });
        card_payment_amount = (EditText) findViewById(R.id.card_payment_amount);

        paytm_amount = (EditText) findViewById(R.id.paytm_amount);

        finance_name_recycler = (RecyclerView)findViewById(R.id.financer_recycler);
        recyclerViewItemClickListener finance_selection_listener = (view, position) -> {
            financer_name.setText(financers.get(position));
            fade_out_bank_selection(finance_selection_layout);
        };
        finance_name_recycler_adapter = new FinanceBankNameAdapter(this,financers,finance_selection_listener);
        finance_name_recycler.setHasFixedSize(true);
        finance_name_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        finance_name_recycler.setAdapter(finance_name_recycler_adapter);
        finance_name_recycler_adapter.notifyDataSetChanged();
        new LoadBanks().execute("Financers");
        finance_selection_layout = (RelativeLayout)findViewById(R.id.financer_selection_layout);
        back_finance_selection = (ImageView)findViewById(R.id.back_financer_selection);
        back_finance_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade_out_bank_selection(finance_selection_layout);
            }
        });

        finance_amount = (EditText) findViewById(R.id.finance_amount);
        financer_name = (TextView) findViewById(R.id.financer_name);
        financer_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fade_in_bank_selection(finance_selection_layout);
            }
        });
        finance_processing_payment = (EditText) findViewById(R.id.finance_processing_payment);
        finance_file_number = (EditText)findViewById(R.id.finance_file_number);

        payment_mode_card = (RelativeLayout) findViewById(R.id.payment_mode_card);
        payment_mode_cheque = (RelativeLayout) findViewById(R.id.payment_mode_cheque);
        payment_mode_paytm = (RelativeLayout) findViewById(R.id.payment_mode_paytm);
        payment_mode_finance = (RelativeLayout) findViewById(R.id.payment_mode_finance);
        payment_modes_selection_layout = (RelativeLayout) findViewById(R.id.payment_modes_selection_layout);
        payment_modes_selection_recycler = (RecyclerView) findViewById(R.id.payment_modes_selection_recycler);
        payment_modes_selection_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        payment_modes_selection_recycler.setHasFixedSize(true);
        close_finance = (ImageView) findViewById(R.id.close_finance);
        close_finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_mode_finance.setVisibility(View.GONE);
                views_visible.remove(payment_mode_finance);
                check_views_on_delete("Finance");
                finance_amount_confirm.setText("0 /-");
            }
        });
        close_card = (ImageView) findViewById(R.id.close_card);
        close_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_mode_card.setVisibility(View.GONE);
                views_visible.remove(payment_mode_card);
                check_views_on_delete("Cards credit/debit");
                card_amount_confirm.setText("0 /-");
            }
        });
        close_paytm = (ImageView) findViewById(R.id.close_paytm);
        close_paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_mode_paytm.setVisibility(View.GONE);
                views_visible.remove(payment_mode_paytm);
                check_views_on_delete("Paytm");
                paytm_amount_confirm.setText("0 /-");
            }
        });
        close_cheque = (ImageView) findViewById(R.id.close_cheque);
        close_cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_mode_cheque.setVisibility(View.GONE);
                views_visible.remove(payment_mode_cheque);
                check_views_on_delete("Cheque");
                cheque_amount_confirm.setText("0 /-");
            }
        });

        // here i am using brand selection categorization adapter bcz it has the same selection layout as i need so no need to
        // have a new implementation of the adapter can use the already present adapter
        payment_modes.add("Cash");
        payment_modes.add("Cheque");
        payment_modes.add("Paytm");
        payment_modes.add("Finance");
        payment_modes.add("Cards credit/debit");

        adapter_payment_modes = new BrandSelectionCategorizationAdapter(this, payment_modes);
        payment_modes_selection_recycler.setAdapter(adapter_payment_modes);
        adapter_payment_modes.notifyDataSetChanged();
        back_payment_selection = (ImageView) findViewById(R.id.back_payment_selection);
        payment_modes_selected = (Button) findViewById(R.id.payment_modes_selected);
        payment_modes_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (BrandSelectionCategorizationAdapter.selected_payment_modes.size() == 1) {
                    switch (BrandSelectionCategorizationAdapter.selected_payment_modes.get(0)) {
                        case "Cash":
                            payment_mode.setImageResource(R.drawable.cash);
                            break;
                        case "Cheque":
                            payment_mode.setImageResource(R.drawable.cheque);
                            views_visible.add(payment_mode_cheque);
                            break;
                        case "Paytm":
                            payment_mode.setImageResource(R.drawable.paytm);
                            views_visible.add(payment_mode_paytm);
                            break;
                        case "Finance":
                            payment_mode.setImageResource(R.drawable.bank);
                            views_visible.add(payment_mode_finance);
                            break;
                        case "Cards credit/debit":
                            payment_mode.setImageResource(R.drawable.credit_card);
                            views_visible.add(payment_mode_card);
                            break;
                    }
                    multiple_selected = false;
                }
                if (BrandSelectionCategorizationAdapter.selected_payment_modes.size() == 0) {
                    payment_mode.setImageResource(R.drawable.payment);
                    multiple_selected = false;
                } else if (BrandSelectionCategorizationAdapter.selected_payment_modes.size() > 1) {
                    multiple_selected = true;
                }
                for (View view : views_visible) {
                    view.setVisibility(View.GONE);
                    views_visible.remove(view);
                }
                fade_out_bank_selection(payment_modes_selection_layout);
                for (int k = 0; k < BrandSelectionCategorizationAdapter.selected_payment_modes.size(); k++) {
                    String mode = BrandSelectionCategorizationAdapter.selected_payment_modes.get(k);
                    Log.d("PaymentMode", mode);
                    switch (mode) {
                        case "Cheque":
                            payment_mode_cheque.setVisibility(View.VISIBLE);
                            views_visible.add(payment_mode_cheque);
                            break;
                        case "Paytm":
                            payment_mode_paytm.setVisibility(View.VISIBLE);
                            views_visible.add(payment_mode_paytm);
                            break;
                        case "Finance":
                            payment_mode_finance.setVisibility(View.VISIBLE);
                            views_visible.add(payment_mode_finance);
                            break;
                        case "Cards credit/debit":
                            payment_mode_card.setVisibility(View.VISIBLE);
                            views_visible.add(payment_mode_card);
                        case "Cash":
                            break;
                    }
                }
            }
        });
        back_payment_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fade_out_bank_selection(payment_modes_selection_layout);
                if (BrandSelectionCategorizationAdapter.selected_payment_modes.size() == 0) {
                    payment_mode.setImageResource(R.drawable.payment);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        payment_mode = (ImageView) findViewById(R.id.payment_mode);
        payment_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("access granting : ", "in click listener");
                PopupMenu popup = new PopupMenu(view.getContext(), payment_mode);
                popup.inflate(R.menu.menu_select_payment_mode);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.cash) {
                            payment_mode.setImageResource(R.drawable.cash);
                            for (View v : views_visible) {
                                v.setVisibility(View.GONE);
                            }
                        } else if (item.getItemId() == R.id.card) {
                            check_visibility(payment_mode_card);
                            payment_mode.setImageResource(R.drawable.credit_card);
                            payment_mode_card.setVisibility(View.VISIBLE);
                            views_visible.add(payment_mode_card);
                        } else if (item.getItemId() == R.id.cheque) {

                            check_visibility(payment_mode_cheque);
                            payment_mode.setImageResource(R.drawable.cheque);
                            payment_mode_cheque.setVisibility(View.VISIBLE);
                            views_visible.add(payment_mode_cheque);
                        } else if (item.getItemId() == R.id.paytm) {
                            check_visibility(payment_mode_paytm);
                            payment_mode.setImageResource(R.drawable.paytm);
                            payment_mode_paytm.setVisibility(View.VISIBLE);
                            views_visible.add(payment_mode_paytm);
                        } else if (item.getItemId() == R.id.finance) {
                            check_visibility(payment_mode_finance);
                            payment_mode.setImageResource(R.drawable.bank);
                            payment_mode_finance.setVisibility(View.VISIBLE);
                            views_visible.add(payment_mode_finance);
                        } else if (item.getItemId() == R.id.multiple) {
                            payment_mode.setImageResource(R.drawable.multiple_payments);
                            fade_in_bank_selection(payment_modes_selection_layout);
                        }

                        return true;
                    }
                });
                popup.show();
            }
        });
        select_address = (Button) findViewById(R.id.select_address);
        address_selection = (RelativeLayout) findViewById(R.id.address_selection_layout);
        address_entry = (RelativeLayout) findViewById(R.id.new_address_layout);
        back_address_entry = (ImageView) findViewById(R.id.back_address_entry);
        back_address_selection = (ImageView) findViewById(R.id.back_address_selection);
        address_entry_done = (Button) findViewById(R.id.address_entry_done);
        address_selection_done = (Button) findViewById(R.id.address_selection_done);
        add_address_button = (ImageView) findViewById(R.id.add_address_button);
        select_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fade_in_bank_selection(address_selection);
            }
        });
        address_selection_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fade_out_bank_selection(address_selection);
            }
        });
        address_entry_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fade_out_bank_selection(address_entry);
            }
        });
        back_address_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fade_out_bank_selection(address_entry);
            }
        });
        back_address_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fade_out_bank_selection(address_selection);
            }
        });
        add_address_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            fade_in_bank_selection(address_entry);
            }
        });
        productsList = (RecyclerView) findViewById(R.id.productsList);
        buy_final = (Button) findViewById(R.id.Confirm_order);
        productsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        new LoadOutbox().execute();
        productsList.setLayoutManager(layoutManager);
        adapter1 = new RecyclerAdapter1(this, list, view_batch_numbers_listener);
        productsList.setAdapter(adapter1);
        addressList = (RecyclerView) findViewById(R.id.address);
        addressList.setHasFixedSize(true);
        address.clear();
        update();
        layoutManager = new LinearLayoutManager(this);
        addressList.setLayoutManager(layoutManager);
        adapter2 = new Adapter2(this, address);
        addressList.setAdapter(adapter2);
        String string_address = getIntent().getExtras().getString("customer_address");
        if (string_address != null) {
            sendAddress(string_address);
            address.add(new Address(string_address));
            adapter2.notifyDataSetChanged();
        }
        buy_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int total_amount = 0;
                Boolean entries_required = false;
                for (int i = 0; i < views_visible.size(); i++) {
                    View v = views_visible.get(i);
                    if (v.getId() == R.id.payment_mode_finance) {

                        if(finance_amount.getText().toString().equals(""))
                        {
                            finance_amount.setError("Enter amount");
                            entries_required = true;
                        }
                        if(finance_processing_payment.getText().toString().equals(""))
                        {
                            finance_processing_payment.setError("Enter processing fee amount / 0 if no payment");
                            entries_required = true;
                        }
                        if(finance_file_number.getText().toString().equals(""))
                        {
                            finance_file_number.setError("Enter file number ");
                            entries_required = true;
                        }
                        if(financer_name.getText().toString().equals(""))
                        {
                            financer_name.setError("Enter financer_name");
                            entries_required = true;
                        }
                        if (!finance_amount.getText().toString().equals("")) {
                            total_amount = total_amount + Integer.parseInt(finance_amount.getText().toString());
                            Log.d("ViewVisible", "Finance ID: " + v.getId());
                            upload_payment_details("Finance");
                            int payment_by_finance = Integer.parseInt(finance_amount.getText().toString());
                            finance_amount_confirm.setText(""+payment_by_finance+" /-");
                        }

                    }
                    if (v.getId() == R.id.payment_mode_card) {
                        if(card_payment_amount.getText().toString().equals(""))
                        {
                            card_payment_amount.setError("Enter payment amount");
                            entries_required = true;
                        }
                        if (!card_payment_amount.getText().toString().equals("")) {
                            total_amount = total_amount + Integer.parseInt(card_payment_amount.getText().toString());
                            Log.d("ViewVisible", "Card ID: " + v.getId());
                            upload_payment_details("Card");
                            card_amount_confirm.setText(card_payment_amount.getText()+" /-");
                        }

                    }
                    if (v.getId() == R.id.payment_mode_paytm) {
                        if(paytm_amount.getText().toString().equals(""))
                        {
                            paytm_amount.setError("Enter payment amount");
                            entries_required = true;
                        }
                        if (!paytm_amount.getText().toString().equals("")) {
                            total_amount = total_amount + Integer.parseInt(paytm_amount.getText().toString());
                            Log.d("ViewVisible", "Paytm ID: " + v.getId());
                            upload_payment_details("Paytm");
                            paytm_amount_confirm.setText(paytm_amount.getText()+" /-");
                        }
                    }
                    if (v.getId() == R.id.payment_mode_cheque) {
                        if(cheque_amount.getText().toString().equals(""))
                        {
                            cheque_amount.setError("Enter cheque amount");
                            entries_required = true;
                        }
                        if(cheque_number.getText().toString().equals(""))
                        {
                            cheque_number.setError("Enter cheque number");
                            entries_required = true;
                        }
                        if(cheque_bank_name.getText().toString().equals(""))
                        {
                            cheque_bank_name.setError("Enter cheque bank name");
                            entries_required = true;
                        }
                        if (!cheque_amount.getText().toString().equals("")) {
                            total_amount = total_amount + Integer.parseInt(cheque_amount.getText().toString());
                            Log.d("ViewVisible", " Cheque ID: " + v.getId());
                            upload_payment_details("Cheque");
                            cheque_amount_confirm.setText(cheque_amount.getText()+" /-");
                        }
                    }
                }
                if(entries_required)
                {
                    return;
                }
                if (total_amount == total_price_of_products) {
                    Toast.makeText(getApplicationContext(), "Can place order succesfully With No Cash Payment", Toast.LENGTH_LONG).show();
                    partial_cash_payment = false;
                    total_amount_confirm.setText(total_price_of_products - getIntent().getExtras().getInt("discount_applicable")+" /-");
                } else {
                    Toast.makeText(getApplicationContext(), "Can place order succesfully With " + (total_price_of_products - total_amount - getIntent().getExtras().getInt("discount_applicable")) + " Cash Payment", Toast.LENGTH_LONG).show();
                    partial_cash_payment = true;
                    cash_payment = total_price_of_products - total_amount - getIntent().getExtras().getInt("discount_applicable");
                    cash_amount_confirm.setText(""+cash_payment+" /-");
                    total_amount_confirm.setText(total_price_of_products - getIntent().getExtras().getInt("discount_applicable")+" /-");
                    upload_payment_details("Cash");
                }
               fade_in_bank_selection(order_confirmation_layout);
            }
        });
    }

    public void update_payment_card(int payment_amount, float tax) {
        TextView text1 = (TextView) findViewById(R.id.text1);
        text1.setText("Payment Amount  " + (Math.round(payment_amount - tax)) + " /-");

        TextView text2 = (TextView) findViewById(R.id.text2);
        text2.setText("Discount  " + getIntent().getExtras().getInt("discount_applicable") + " /-");

        TextView text3 = (TextView) findViewById(R.id.text3);
        text3.setText("Taxable Amount   " + Math.round(tax) + " /-");

        TextView text4 = (TextView) findViewById(R.id.text4);
        text4.setText("Total Payment " + (payment_amount - getIntent().getExtras().getInt("discount_applicable")) + " /-");

    }

    public void set_baught() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);
        Log.d("URL: ", "" + AppConfig.url_set_bought + appConfig.getUser_email());
        JsonArrayRequest bought_req = new JsonArrayRequest((AppConfig.url_set_bought + appConfig.getUser_email()+"&discount="+getIntent().getExtras().getInt("discount_applicable") +"&date="+formattedDate),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("True Response", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                String msg = obj.getString("status");
                                if (msg.equals("success")) {
                                    Toast.makeText(getApplicationContext(), "Successfully placed your order", Toast.LENGTH_LONG).show();
                                    create_invoice();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Sorry Could not place your order ", Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter2.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("VOLLEY ERROR:-", "Error: " + error.getMessage());
                    }

                });
        bought_req.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(bought_req);
    }

    public static void update() {
        JsonArrayRequest movieReq = new JsonArrayRequest(AppConfig.url_address_list + appConfig.getCustomer_id(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("True Response", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                address.add(new Address(obj.getString("address")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter2.notifyDataSetChanged();

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

    public void create_invoice() {
        StringRequest invoice_request = new StringRequest(Request.Method.POST, AppConfig.url_generate_invoice, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.d("URL:", AppConfig.url_generate_invoice);
                    Toast.makeText(getApplicationContext(), "invoice generated successfully", Toast.LENGTH_LONG).show();
                    intent.putExtra("customer_email", "" + getIntent().getExtras().getString("customer_email"));
                    intent.putExtra("customer_name", getIntent().getExtras().getString("customer_name"));
                    intent.putExtra("customer_phone", getIntent().getExtras().getString("customer_phone"));
                    intent.putExtra("customer_email", getIntent().getExtras().getString("customer_email"));
                    intent.putExtra("customer_address", getIntent().getExtras().getString("customer_address"));
                    intent.putExtra("promoter_email", getIntent().getExtras().getString("promoter_email"));
                    intent.putExtra("customer_gst", getIntent().getExtras().getString("customer_gst"));
                    intent.putExtra("class_name","buy_final_activity");
                    intent.putExtra("total_payment",""+total_price_of_products);
                    Log.d("Views Visible: ",""+views_visible.size());
                    for(int i = 0;i < views_visible.size();i++)
                    {
                        if(views_visible.get(i).getId() == R.id.payment_mode_card)
                        {
                            intent.putExtra("Card",Integer.parseInt(card_payment_amount.getText().toString()));
                        }
                        else if(views_visible.get(i).getId() == R.id.payment_mode_cheque)
                        {
                            intent.putExtra("Cheque",Integer.parseInt(cheque_amount.getText().toString()));
                        }
                        else if(views_visible.get(i).getId() == R.id.payment_mode_paytm)
                        {
                            intent.putExtra("Paytm",Integer.parseInt(paytm_amount.getText().toString()));
                        }
                        else if(views_visible.get(i).getId() == R.id.payment_mode_finance)
                        {
                            intent.putExtra("Finance",Integer.parseInt(finance_amount.getText().toString()));
                            intent.putExtra("Financer",financer_name.getText().toString());
                        }
                        else
                        {
                            if(cash_payment != 0)
                            {
                                intent.putExtra("Cash",cash_payment);
                            }
                        }
                    }
                    intent.putExtra("Cash",cash_payment);
                    intent.putParcelableArrayListExtra("products", list);
                    startActivity(intent);
                                  } else if (response.equals("failure")) {
                    Toast.makeText(getApplicationContext(), "invoice could not be generated successfully", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "invoice could not be generated", Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("customer_email", "" + getIntent().getExtras().getString("customer_email"));
                params.put("customer_name", getIntent().getExtras().getString("customer_name"));
                params.put("customer_phone", getIntent().getExtras().getString("customer_phone"));
                params.put("customer_address", getIntent().getExtras().getString("customer_address"));
                params.put("promoter_email", getIntent().getExtras().getString("promoter_email"));
                params.put("customer_gst", getIntent().getExtras().getString("customer_gst"));
                return params;
            }
        };
        invoice_request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(invoice_request);
    }

    public void sendAddress(String address) {
        String url = AppConfig.url_add_address + appConfig.getCustomer_id() + "&address=";
        String arr[] = address.split(" ");
        StringBuilder builder = new StringBuilder(url);
        for (String s : arr) {
            builder.append(s);
            builder.append("+");
        }
        int index = builder.lastIndexOf("+");
        builder.deleteCharAt(index);
        String url_new = builder.toString();
        Log.d("address url: ", url_new);
        JsonArrayRequest movieReq = new JsonArrayRequest(url_new,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("True Response", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                String msg = obj.getString("success");
                                if (msg.equals("true")) {
                                    Toast.makeText(getApplicationContext(), "Successfully updated address list", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Could not update address list", Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter2.notifyDataSetChanged();

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

    class LoadOutbox extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("URL:", "" + AppConfig.url_get_viewed + appConfig.getUser_email());
        }

        @Override
        protected Void doInBackground(Void... params) {
            JsonArrayRequest movieReq = new JsonArrayRequest(AppConfig.url_get_viewed + appConfig.getUser_email(),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("True Response", response.toString());
                            int amount = 0;
                            float tax = 0 ;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Product pro = new Product();
                                    pro.setProduct_id(obj.getInt("product_id"));
                                    pro.set_Name(obj.getString("name"));
                                    pro.setTax(obj.getInt("tax"));
                                    if (!(obj.getString("stock").equals(null) || obj.getString("stock") == null)) {
                                        pro.set_Stock(Integer.parseInt(obj.getString("stock")));
                                    }
                                    if (!(obj.getString("MRP").equals(null) || obj.getString("MRP") == null)) {
                                        pro.setPrice_mrp(Integer.parseInt(obj.getString("MRP")));
                                    }
                                    if (!(obj.getString("MOP").equals(null) || obj.getString("MOP") == null)) {
                                        pro.setPrice_mop(Integer.parseInt(obj.getString("MOP")));
                                    }
                                    if (!(obj.getString("ksprice").equals(null) || obj.getString("ksprice") == null)) {
                                        pro.setPrice_ks(Integer.parseInt(obj.getString("ksprice")));
                                    }
                                    pro.setProduct_HSN(obj.getString("product_hsn"));
                                    pro.setOffer_type(obj.getString("offer_type"));
                                    if(!pro.getOffer_type().equals("NONE"))
                                    {
                                        pro.setDiscount_offered_online_fetch(Integer.parseInt(obj.getString("discount_amount")));
                                        pro.setMin_item_count(Integer.parseInt(obj.getString("item_count")));
                                        if(pro.getOffer_type().equals("Super Value Offer"))
                                        {
                                            pro.set_Stock(Integer.parseInt(obj.getString("item_count")));
                                        }
                                    }

                                    if(appConfig.getUserType().equals("Admin") || appConfig.getUserType().equals("Dealer"))
                                    {
                                        amount = amount + pro.get_Stock() * pro.getPrice_ks();
                                        tax = tax + (pro.getPrice_ks()*pro.get_Stock()) - (pro.getPrice_ks()*pro.get_Stock())/((100 + pro.getTax())/100);
                                    }
                                    else
                                    {
                                        amount = amount + pro.get_Stock() * pro.getPrice_mop();
                                        tax = tax + (pro.getPrice_mop()*pro.get_Stock()) - (pro.getPrice_mop()*pro.get_Stock())/((100 + pro.getTax())/100);
                                    }
                                    String [] selected_batch_numbers = new String[response.length()];
                                    if(!(obj.getString("batch_selected").equals("") || obj.getString("batch_selected").equals(null)))
                                    {
                                        selected_batch_numbers = obj.getString("batch_selected").split(",");

                                    }
                                    else
                                    {
                                        selected_batch_numbers = new String[0];
                                    }
                                    JSONArray array = obj.getJSONArray("batch_details");
                                    if(array.length() != 0) {
                                        JSONObject batch_details = array.getJSONObject(0);
                                        for(int j = 0 ; j < batch_details.names().length(); j++)
                                        {
                                            if(!(batch_details.names().getString(j).equals("batch_id") || batch_details.names().getString(j).equals("product_id")))
                                            {
                                                if(!batch_details.getString(batch_details.names().getString(j)).equals("null"))
                                                {
                                                    String batches[] = batch_details.getString(batch_details.names().getString(j)).split(",");
                                                    for(int k = 0; k <batches.length;k ++)
                                                    {
                                                        Batch b1 = new Batch(batch_details.names().getString(j),batches[k]);
                                                        boolean selected = false;
                                                        for(int l = 0 ; l < selected_batch_numbers.length;l++)
                                                        {
                                                            Log.d("BuyNow comparing:",""+selected_batch_numbers[l]+ ""+b1.getNumber());
                                                            if(b1.getNumber().equals(selected_batch_numbers[l]))
                                                            {
                                                                selected = true;
                                                                pro.selected_batch_numbers.add(b1);
                                                                pro.batch_numbers.add(b1);
                                                            }
                                                        }
                                                        if(selected == false)
                                                        {
                                                            pro.addBatchNumbers(b1);
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                    list.add(pro);
                                    adapter1.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            total_price_of_products = amount;
                            update_payment_card(amount,tax);
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
            return null;
        }

        protected void onPostExecute() {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_my_cart, menu);
        final View action_profile = menu.findItem(R.id.action_profile).getActionView();
        action_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appConfig.isLogin()) {
                    Intent intent = new Intent(Buy_final_activity.this, Final_Cart.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Buy_final_activity.this, "Please login to proceed", Toast.LENGTH_LONG).show();
                }
            }
        });
        final View action_cart = menu.findItem(R.id.action_cart).getActionView();
        action_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Buy_final_activity.this, Buy_Now_Activity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.signin) {
            if (appConfig.isLogin()) {
                Toast.makeText(Buy_final_activity.this, "You are already logged in", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(Buy_final_activity.this, Activity_Login.class);
                startActivity(intent);
            }
        } else if (id == R.id.signout) {
            if (appConfig.isLogin()) {
                Intent intent = new Intent(Buy_final_activity.this, Activity_Login.class);
                startActivity(intent);

            } else {
                Toast.makeText(Buy_final_activity.this, " Please login to signout", Toast.LENGTH_LONG).show();

            }
        } else if (id == R.id.signup) {
            Intent intent = new Intent(Buy_final_activity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == android.R.id.home) {
            finish();
            overridePendingTransition(0, R.anim.slide_out_left_animation);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void check_visibility(View v_to_be_checked) {
        for (View v : views_visible) {
            if (v.equals(v_to_be_checked)) {
                continue;
            } else {
                v.setVisibility(View.GONE);
            }
        }
    }

    public void check_views_on_delete(String view_string) {
        if (multiple_selected) {
            if (views_visible.size() == 0) {
                payment_mode.setImageResource(R.drawable.payment);
                multiple_selected = false;
                BrandSelectionCategorizationAdapter.selected_payment_modes.clear();
                adapter_payment_modes.notifyDataSetChanged();
            } else {
                BrandSelectionCategorizationAdapter.selected_payment_modes.remove(view_string);
                adapter_payment_modes.notifyDataSetChanged();
            }
        } else {
            if (BrandSelectionCategorizationAdapter.selected_payment_modes.size() > 0) {
                BrandSelectionCategorizationAdapter.selected_payment_modes.clear();
                adapter_payment_modes.notifyDataSetChanged();
            }
            payment_mode.setImageResource(R.drawable.payment);
        }

    }

    public void upload_payment_details(final String payment_mode) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("user_email",appConfig.getUser_email());
        if (payment_mode.equals("Cash")) {
            params.put("payment_type","Cash");
            params.put("cash_payment_amount", "" + cash_payment);
        }
        if (payment_mode.equals("Finance")) {
            params.put("payment_type","Finance");
            params.put("finance_beneficiary", "");
            params.put("financer_name", financer_name.getText().toString());
            params.put("finance_amount", finance_amount.getText().toString());
            params.put("processing_payment_amount", finance_processing_payment.getText().toString());
            params.put("finance_file_number", finance_file_number.getText().toString());
        }
        if (payment_mode.equals("Card")) {
            params.put("payment_type","Card");
            params.put("card_no", "");
            params.put("name_on_card","");
            params.put("card_payment_amount", card_payment_amount.getText().toString());
            params.put("card_bank_name", card_bank_name.getText().toString());
        }
        if (payment_mode.equals("Cheque")) {
            params.put("payment_type","Cheque");
            params.put("cheque_number", cheque_number.getText().toString());
            params.put("cheque_bank_name", cheque_bank_name.getText().toString());
            params.put("cheque_amount", cheque_amount.getText().toString());
            params.put("cheque_beneficiary","Khurana Sales");
        }

        if (payment_mode.equals("Paytm")) {
            params.put("payment_type","Paytm");
            params.put("paytm_number","");
            params.put("paytm_account_name","");
            params.put("paytm_amount", paytm_amount.getText().toString());
        }
        JsonObjectRequest payment_upload_request = new JsonObjectRequest(Request.Method.POST, AppConfig.upload_payment, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("BuyFinalActivity",response.toString());
                switch(payment_mode)
                {
                    case "Cash":
                        Toast.makeText(getApplicationContext(),"Cash Payment Uploaded",Toast.LENGTH_LONG).show();
                        break;
                    case "Card":
                        Toast.makeText(getApplicationContext(),"Card Payment Uploaded",Toast.LENGTH_LONG).show();
                        break;
                    case "Cheque":
                        Toast.makeText(getApplicationContext(),"Cheque Payment Uploaded",Toast.LENGTH_LONG).show();
                        break;
                    case "Paytm":
                        Toast.makeText(getApplicationContext(),"Paytm Payment Uploaded",Toast.LENGTH_LONG).show();
                        break;
                    case "Finance":
                        Toast.makeText(getApplicationContext(),"Finance Payment Uploaded",Toast.LENGTH_LONG).show();
                         break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BuyFinal","Some error has occured");
            }
        });
        payment_upload_request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(payment_upload_request);
    }

    class LoadBanks extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("URL:", "" + AppConfig.url_get_viewed + appConfig.getUser_email());
        }

        @Override
        protected Void doInBackground(String... strings)
        {
            String type = strings[0];
            Log.d("BuyFinal",AppConfig.get_banks_or_financers+type);
            JsonArrayRequest movieReq = new JsonArrayRequest(AppConfig.get_banks_or_financers + type,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("True Response", response.toString());
                            int amount = 0;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                   if(type.equals("Banks"))
                                   {
                                       banks.add(obj.getString("bank_name"));
                                       bank_name_recycler_adapter.notifyDataSetChanged();
                                   }
                                   else
                                   {
                                       financers.add(obj.getString("financer_name"));
                                       finance_name_recycler_adapter.notifyDataSetChanged();
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
            return null;
        }

        protected void onPostExecute() {
        }
    }
public void fade_out_bank_selection(View v)
{
    FadeOutAnimator outAnimator = new FadeOutAnimator();
    outAnimator.prepare(v);
    outAnimator.setTarget(v);
    outAnimator.setDuration(400);
    outAnimator.addAnimatorListener(new Animator.AnimatorListener() {
        @Override public void onAnimationStart(Animator animation) { }
        @Override public void onAnimationEnd(Animator animation) {v.setVisibility(View.INVISIBLE); }
        @Override public void onAnimationCancel(Animator animation) { }
        @Override public void onAnimationRepeat(Animator animation) { }});
    outAnimator.start();
}
public void fade_in_bank_selection(View v)
{
    FadeInAnimator inAnimator = new FadeInAnimator();
    inAnimator.prepare(v);
    inAnimator.setTarget(v);
    inAnimator.setDuration(400);
    inAnimator.addAnimatorListener(new Animator.AnimatorListener() {
        @Override public void onAnimationStart(Animator animation) { v.setVisibility(View.VISIBLE);}
        @Override public void onAnimationEnd(Animator animation) { }
        @Override public void onAnimationCancel(Animator animation) { }
        @Override public void onAnimationRepeat(Animator animation) { }});
    inAnimator.start();
}

class check_availablity extends AsyncTask<Void, Void , Void >
{

    @Override
    protected Void doInBackground(Void... voids) {

        JsonArrayRequest check_final_availability = new JsonArrayRequest(AppConfig.check_availability_order + appConfig.getUser_email(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject object = response.getJSONObject(0);
                    if (object.getString("availability").equals("true")) {
                        set_baught();
                    } else if (object.getString("availability").equals("false")) {
                        Toast.makeText(getApplicationContext(), "Some products are out of stock please check before order", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please Check network connection", Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(check_final_availability);
        return null;
    }
}
}
