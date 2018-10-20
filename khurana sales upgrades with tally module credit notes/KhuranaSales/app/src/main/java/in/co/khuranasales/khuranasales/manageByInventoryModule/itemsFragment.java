package in.co.khuranasales.khuranasales.manageByInventoryModule;

import android.animation.Animator;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
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
import java.util.Iterator;
import java.util.Stack;

import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class itemsFragment extends Fragment {

    private AppConfig appConfig;

    private RecyclerView recycler_view_inventory;
    public Stack<String> categories_stack = new Stack<>();
    private ArrayList<inventoryItem> inventory_items = new ArrayList<>();
    private HashMap<String, ArrayList<inventoryItem>> inventory_maps = new HashMap<>();
    private String category_selected = "Top";
    private inventoryItemAdapter inventoryItemAdapterInstance;


    private ArrayList<batchItem> batches = new ArrayList<>();
    private RelativeLayout layout_batch_details;
    private RecyclerView layout_batch_recycler;
    private batchItemAdapter batch_items_adapter;
    private inventoryItem item_selected_to_view_batch;
    private RelativeLayout transparent_background;
    private FadeOutAnimator animator;
    private FadeInAnimator in_animator;
    private TextView product_name_for_batch;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appConfig = new AppConfig(getContext());
        View view = inflater.inflate(R.layout.item_fragment_layout,container, false);
        transparent_background = (RelativeLayout)view.findViewById(R.id.transparent_background);
        layout_batch_details = (RelativeLayout)view.findViewById(R.id.view_inventory_batch_layout);
        batch_items_adapter = new batchItemAdapter(getActivity(), batches);
        layout_batch_recycler = (RecyclerView)view.findViewById(R.id.batch_recycler);
        layout_batch_recycler.setHasFixedSize(true);
        layout_batch_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        layout_batch_recycler.setAdapter(batch_items_adapter);
        batch_items_adapter.notifyDataSetChanged();
        product_name_for_batch = (TextView)view.findViewById(R.id.product_name_for_batch);

        recycler_view_inventory = (RecyclerView)view.findViewById(R.id.inventory_recycler);
        recycler_view_inventory.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view_inventory.setHasFixedSize(true);
        layout_batch_details.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    slideToBottom(layout_batch_details);
                    fadeOut(transparent_background);
                }
                return false;
            }
        });

        recyclerViewItemClickListener inventory_click_listener = (view1, position)->{

            if(view1.getId() == R.id.view_batch_numbers)
            {
                item_selected_to_view_batch = inventory_items.get(position);
                slideToTop(layout_batch_details);
                product_name_for_batch.setText(item_selected_to_view_batch.getName());
                fadeIn(transparent_background);
                new get_batch_number_by_product_id().execute();
            }
            else
            {
                category_selected = inventory_items.get(position).getName();
                categories_stack.push(category_selected);
                if(inventory_maps.get(category_selected) != null)
                {
                    Toast.makeText(getContext(),"Fetching from cached data",Toast.LENGTH_SHORT).show();
                    inventory_items.clear();
                    inventoryItemAdapterInstance.notifyDataSetChanged();
                    inventory_items.addAll(inventory_maps.get(category_selected));
                    inventoryItemAdapterInstance.notifyDataSetChanged();
                    recycler_view_inventory.scheduleLayoutAnimation();
                }
                else
                {
                    Toast.makeText(getContext(),"Fetching online data",Toast.LENGTH_SHORT).show();
                    new get_content_online().execute();
                }

            }
        };
        inventoryItemAdapterInstance = new inventoryItemAdapter(getActivity(), inventory_items,inventory_click_listener);
        recycler_view_inventory.setAdapter(inventoryItemAdapterInstance);
        final Context context = recycler_view_inventory.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation);

        recycler_view_inventory.setLayoutAnimation(controller);
        inventoryItemAdapterInstance.notifyDataSetChanged();
        recycler_view_inventory.scheduleLayoutAnimation();
        categories_stack.push("Top");
        new get_content_online().execute();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class get_content_online extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            JsonArrayRequest request_get_content = new JsonArrayRequest(appConfig.get_inventory_url+category_selected, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    inventory_items.clear();
                    inventoryItemAdapterInstance.notifyDataSetChanged();
                    if(response.length() > 0)
                    {
                        try {
                            for(int i = 0; i< response.length();i++)
                            {
                                JSONObject response_object = response.getJSONObject(i);
                                inventoryItem item = new inventoryItem();
                                item.setName(response_object.getString("product_name"));
                                item.setMrp(Integer.parseInt(response_object.getString("product_mrp")));
                                item.setMop(Integer.parseInt(response_object.getString("product_mop")));
                                item.setKs_price(Integer.parseInt(response_object.getString("product_ks_price")));
                                item.setHsn(Integer.parseInt(response_object.getString("product_hsn")));
                                item.setBatch_id(Integer.parseInt(response_object.getString("product_batch_id")));
                                item.setBrand(response_object.getString("product_brand"));
                                item.setLinks(response_object.getString("product_links"));
                                item.setParent(response_object.getString("product_parent"));
                                item.setTag_from(response_object.getString("product_tag"));
                                item.setCategory(response_object.getString("product_type"));
                                item.setTax(Double.parseDouble(response_object.getString("product_tax")));
                                item.setProduct_id(Integer.parseInt(response_object.getString("product_id")));
                                item.setStock(Integer.parseInt(response_object.getString("product_in_stock")));
                                item.setType(response_object.getString("product_type"));
                                inventory_items.add(item);
                            }
                            inventoryItemAdapterInstance.notifyDataSetChanged();
                            recycler_view_inventory.scheduleLayoutAnimation();
                            if(inventory_maps.get(category_selected) == null)
                            {
                                ArrayList<inventoryItem> inventoryItems = new ArrayList<>();
                                inventoryItems.addAll(inventory_items);
                                inventory_maps.put(category_selected, inventoryItems);
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

                }
            });
            request_get_content.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(request_get_content);
            return null;
        }
    }

    public class get_batch_number_by_product_id extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("URL",appConfig.get_inventory_batch_numbers_url +item_selected_to_view_batch.getBatch_id());
            JsonArrayRequest request_get_batch_numbers =new JsonArrayRequest(appConfig.get_inventory_batch_numbers_url +item_selected_to_view_batch.getBatch_id(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    batches.clear();
                    batch_items_adapter.notifyDataSetChanged();
                    if(response.length() > 0)
                    {
                        try
                        {
                            JSONObject response_object = response.getJSONObject(0);
                            Iterator<String> keys_set_iterator = response_object.keys();
                            while(keys_set_iterator.hasNext())
                            {
                                String key = keys_set_iterator.next();
                                String batches_inside = response_object.getString(key);
                                String[] batches_array = batches_inside.split(",");

                                batchItem item = new batchItem();
                                item.setType("Section");
                                item.setValue(key);
                                batches.add(item);
                                if(batches_array.length == 1 && (batches_array[0] == "" || batches_array[0] == "null"))
                                {
                                    batches.remove(batches.size()-1);
                                }
                                else
                                {
                                    for(int i = 0;i < batches_array.length;i++)
                                    {
                                        if(batches_array[i].equals(""))
                                        {
                                            continue;
                                        }
                                        batchItem item1 = new batchItem();
                                        item1.setType("Batch");
                                        item1.setValue(batches_array[i]);
                                        batches.add(item1);
                                    }

                                }

                            }
                            if(batches.get(batches.size()-1).getType().equals("Section"))
                            {
                                batches.remove(batches.size()-1);
                            }
                            batch_items_adapter.notifyDataSetChanged();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("VolleyError",error.toString());
                    Toast.makeText(getContext(),"Some error occured",Toast.LENGTH_SHORT);
                }
            });
            request_get_batch_numbers.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(request_get_batch_numbers);
            return null;
        }
    }

    public void onBackPressed() {

        if(this.categories_stack.size() == 1)
        {

            getActivity().finish();
        }
        else
        {
            String category_current_selected = categories_stack.pop();
            category_selected = categories_stack.lastElement();
            Log.d("CATEGORY",category_selected);
            if(inventory_maps.get(category_selected) != null)
            {
                Toast.makeText(getContext(),"Fetching from cached data",Toast.LENGTH_SHORT).show();
                inventory_items.clear();
                inventoryItemAdapterInstance.notifyDataSetChanged();
                inventory_items.addAll(inventory_maps.get(category_selected));
                inventoryItemAdapterInstance.notifyDataSetChanged();
                recycler_view_inventory.scheduleLayoutAnimation();
            }
            else
            {
                Toast.makeText(getContext(),"Fetching online data",Toast.LENGTH_SHORT).show();
                new get_content_online().execute();
            }
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
