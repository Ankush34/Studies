package in.co.khuranasales.khuranasales.inventoryViewModule;

import android.animation.Animator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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
import java.util.Map;

import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels.creditNote;
import in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels.credit_note_account_ledger;
import in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels.credit_note_item;
import in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels.credit_note_item_batch;
import in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels.credit_note_ledger;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class inventoriesViewFragment extends Fragment {
    public FadeOutAnimator animator;
    public FadeInAnimator in_animator;

    public View view;
    public RecyclerView recycler_view_inventories;
    public inventoriesViewCreditNoteAdapter inventory_view_adapter;
    public ArrayList<creditNote> credit_notes  = new ArrayList<creditNote>();
    public HashMap<String, creditNote> credit_notes_map = new HashMap<>();
    public HashMap<String, ArrayList<creditNote>> credit_notes_inventory = new HashMap<>();
    public RelativeLayout layout_ledger_credit_notes;
    public RelativeLayout layout_close_ledger_credit_notes_window;
    public TextView ledger_name_text;
    public TextView credit_total_amount;
    public RelativeLayout transparent_background;

    public RecyclerView recycler_view_show_ledger_credit_notes;
    public expandedCreditNotesAdapter credit_note_expanded_adapter;
    public ArrayList<creditNote> ledger_credit_notes = new ArrayList<>();

    public static creditNote selected_credit_note;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.inventories_view_fragment, container, false);

        transparent_background = (RelativeLayout)view.findViewById(R.id.transparent_background);
        recycler_view_show_ledger_credit_notes = (RecyclerView)view.findViewById(R.id.recycler_ledger_credit_notes);
        recycler_view_show_ledger_credit_notes.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recycler_view_show_ledger_credit_notes.setHasFixedSize(true);
        recyclerViewItemClickListener get_credit_note_details_listener = (view1, position)->
        {
            selected_credit_note = ledger_credit_notes.get(position);
            Intent intent = new Intent(this.getActivity(), creditVoucherInfoActivity.class);
            startActivity(intent);
        };
        credit_note_expanded_adapter = new expandedCreditNotesAdapter(ledger_credit_notes, get_credit_note_details_listener);
        recycler_view_show_ledger_credit_notes.setAdapter(credit_note_expanded_adapter);
        credit_note_expanded_adapter.notifyDataSetChanged();
        credit_total_amount = (TextView)view.findViewById(R.id.total_credit_amount);

        layout_ledger_credit_notes = (RelativeLayout)view.findViewById(R.id.layout_ledger_credit_notes);
        layout_close_ledger_credit_notes_window = (RelativeLayout)view.findViewById(R.id.layout_close_ledger_credit_notes_window);
        layout_close_ledger_credit_notes_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut(transparent_background);
                slideToBottom(layout_ledger_credit_notes);
            }
        });
        recycler_view_inventories = (RecyclerView)view.findViewById(R.id.recycler_view_inventory);
        recycler_view_inventories.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recycler_view_inventories.setHasFixedSize(true);
        recyclerViewItemClickListener listener = (view1, position)->{
            ledger_credit_notes.clear();
            credit_note_expanded_adapter.notifyDataSetChanged();

            fadeIn(transparent_background);
            slideToTop(layout_ledger_credit_notes);
            creditNote note_selected = credit_notes.get(position);

            credit_total_amount.setText("Outstanding:   Rs "+note_selected.getCredit_amount()+"  /-");

            ledger_credit_notes.addAll(credit_notes_inventory.get(note_selected.getCredit_ledger_name()));
            credit_note_expanded_adapter.notifyDataSetChanged();

            ledger_name_text = (TextView)view.findViewById(R.id.ledger_name_title);
            ledger_name_text.setText(note_selected.getCredit_ledger_name());
        };
        inventory_view_adapter = new inventoriesViewCreditNoteAdapter(credit_notes, getActivity(),"creditNote", listener);
        recycler_view_inventories.setAdapter(inventory_view_adapter);
        inventory_view_adapter.notifyDataSetChanged();
        new get_credit_notes().execute();
        return  view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class get_credit_notes extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest request_get_Credit_notes = new JsonArrayRequest(AppConfig.get_credit_notes_url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if(response.length() > 0)
                    {
                        try {
                            for(int i = 0; i < response.length(); i++)
                            {
                             JSONObject response_object=  response.getJSONObject(i);
                             creditNote credit_note = new creditNote();
                             credit_note.setCredit_date(response_object.getString("credit_note_date"));
                             credit_note.setCredit_guid(response_object.getString("credit_note_guid"));
                             credit_note.setCompany_state(response_object.getString("credit_note_company_state"));
                             credit_note.setCredit_gstin(response_object.getString("credit_note_gstin"));
                             credit_note.setCredit_voucher_type(response_object.getString("voucher_type"));
                             credit_note.setCredit_ledger_name(response_object.getString("credit_note_ledger_name"));
                             credit_note.setCredit_supply_place_name(response_object.getString("credit_note_place_of_supply"));
                             credit_note.setVoucher_key(response_object.getString("credit_note_voucher_key"));
                             credit_note.setAlter_id(response_object.getString("credit_note_alter_id"));
                             credit_note.setMaster_id(response_object.getString("credit_note_master_id"));
                             credit_note.setNarration(response_object.getString("credit_note_narration"));
                             credit_note.setCredit_amount(response_object.getString("credit_note_amount"));
                             credit_note.setCompany_name(response_object.getString("credit_note_company_name"));
                             credit_note.setCompany_tag(response_object.getString("credit_note_company_tag"));
                             credit_note.setCredit_state(response_object.getString("credit_note_state"));
                             credit_note.setCredit_address(response_object.getString("credit_note_address"));
                             credit_note.setCredit_note_created_by(response_object.getString("credit_note_created_by"));

                             JSONArray credit_note_ledgers = response_object.getJSONArray("credit_note_ledgers");
                             JSONArray account_ledgers = response_object.getJSONArray("credit_note_account_ledgers");

                             for(int h = 0 ; h < credit_note_ledgers.length(); h++)
                             {
                                 JSONObject ledgers = credit_note_ledgers.getJSONObject(h);
                                 credit_note_ledger ledger_instance = new credit_note_ledger();
                                 ledger_instance.setLedger_name(ledgers.getString("ledger_name"));
                                 ledger_instance.setLedger_amount(ledgers.getString("ledger_amount"));
                                 credit_note.credit_ledgers.add(ledger_instance);
                             }

                             for(int g =0 ; g< account_ledgers.length(); g++)
                             {
                                JSONObject ledgers = account_ledgers.getJSONObject(g);
                                credit_note_account_ledger ledger_instance = new credit_note_account_ledger();
                                ledger_instance.setLedger_name(ledgers.getString("ledger_name"));
                                ledger_instance.setLedger_amount(ledgers.getString("ledger_amount"));
                                credit_note.credit_note_accounting_ledgers.add(ledger_instance);
                             }
                             JSONArray credit_note_items_array = response_object.getJSONArray("credit_note_items");
                             for(int j =0 ; j<credit_note_items_array.length(); j++)
                             {
                                 JSONObject item_object = credit_note_items_array.getJSONObject(j);
                                 credit_note_item item = new credit_note_item();
                                 item.setItem_name(item_object.getString("item_name"));
                                 item.setRate(item_object.getString("item_price"));
                                 item.setItem_amount(item_object.getString("item_total_amount"));
                                 item.setActual_quantity(item_object.getString("item_actual_qty"));
                                 item.setBilled_quantity(item_object.getString("item_billed_qty"));
                                 JSONArray batches_array  = item_object.getJSONArray("batches");
                                 for(int m = 0 ; m < batches_array.length(); m++)
                                 {
                                     JSONObject batch_object = batches_array.getJSONObject(m);
                                     credit_note_item_batch batch = new credit_note_item_batch();
                                     batch.setBatch(batch_object.getString("batch_number"));
                                     batch.setGodown_location(batch_object.getString("batch_location"));
                                     item.batches.add(batch);
                                 }
                                 item.ledgers_included.addAll(credit_note.credit_ledgers);
                                 item.account_ledgers_included.addAll(credit_note.credit_note_accounting_ledgers);
                                 credit_note.credit_note_items.add(item);
                             }
                             if(credit_notes_map.get(credit_note.getCredit_ledger_name()) != null)
                             {
                                 double total_amount = Double.parseDouble(credit_note.getCredit_amount()) + Double.parseDouble(credit_notes_map.get(credit_note.getCredit_ledger_name()).getCredit_amount());

                                 credit_notes_map.get(credit_note.getCredit_ledger_name()).setCredit_amount(""+total_amount);

                                 // this is responsible for carrying the credit notes so that we dont need to load them online again
                                 credit_notes_inventory.get(credit_note.getCredit_ledger_name()).add(credit_note);
                             }
                             else
                             {
                                 // this keeps the total credit notes by name so that we not need to load them again online
                                 ArrayList<creditNote> credit_notes_list = new ArrayList<>();
                                 credit_notes_list.add(credit_note);
                                 credit_notes_inventory.put(credit_note.getCredit_ledger_name(), credit_notes_list);

                                 // this is responsible for rendering the current credt notes
                                 creditNote credit_note_new =new creditNote();
                                 credit_note_new.setCredit_ledger_name(credit_note.getCredit_ledger_name());
                                 credit_note_new.setCredit_amount(credit_note.getCredit_amount());
                                 credit_note_new.setNarration(credit_note.getNarration());
                                 credit_note_new.setCredit_date(credit_note.getCredit_date());
                                 credit_notes_map.put(credit_note.getCredit_ledger_name(),credit_note_new);
                             }
                            }

                            for(Map.Entry<String,creditNote> e : credit_notes_map.entrySet())
                            {
                                credit_notes.add(e.getValue());
                                inventory_view_adapter.notifyDataSetChanged();
                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(), "error occured",Toast.LENGTH_SHORT).show();
                }
            });
            request_get_Credit_notes.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            AppController.getInstance().addToRequestQueue(request_get_Credit_notes);
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

    public void fadeOut(View v)
    {
        animator = new FadeOutAnimator();
        animator.setTarget(v);
        animator.prepare(v);
        animator.setDuration(700);
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
        in_animator.setDuration(700);
        in_animator.addAnimatorListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) { v.setVisibility(View.VISIBLE);}
            @Override public void onAnimationEnd(Animator animation) {}
            @Override public void onAnimationCancel(Animator animation) { }
            @Override public void onAnimationRepeat(Animator animation) { }
        });
        in_animator.start();
    }

}
