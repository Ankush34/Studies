package in.co.khuranasales.khuranasales;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {

    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.SmoothScroller smoothScroller;
    public RecyclerView.LayoutManager layoutManager;
    public String brand;
    public String filter = new String("");
    public AppConfig appConfig ;
    public ImageView view1;
    public recyclerViewItemClickListener mlistener ;
    public ArrayList<Product> mContentItems = new ArrayList<>();

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        appConfig = new AppConfig(getContext());
        smoothScroller = new LinearSmoothScroller(view.getContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mContentItems,view.getContext(),mlistener));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
       new LoadOutbox().execute();
    }
    class LoadOutbox extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... params)  {
           String url = "";
            if(brand.equals("Favorites"))
           {
               url = AppConfig.get_favorites+appConfig.getUser_email().trim();
           }else
           {
              url =  AppConfig.load_product_with_offer_details+brand+"&type="+CategorizeDataActivity.type;
           }
            Log.d("url",url);

            JsonArrayRequest movieReq = new JsonArrayRequest(url,new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("True Response", response.toString());
                            JSONArray response_new =new JSONArray();
                            Log.d("ProductViewActivity",response.toString());
                            if(brand.equals("Favorites"))
                            {
                                try {
                                    JSONObject object = response.getJSONObject(0);
                                   response_new  =  object.getJSONArray("product_info");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                response = response_new;
                            }
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Product pro = new Product();
                                    pro.setProduct_id(obj.getInt("product_id"));
                                    pro.set_Name(obj.getString("Name"));
                                    if(!(obj.getString("stock").equals(null)||obj.getString("stock")==null))
                                    {
                                        pro.set_Stock(Integer.parseInt(obj.getString("stock")));
                                    }
                                    if(!(obj.getString("mrp").equals(null)||obj.getString("mrp")==null||obj.getString("mrp")=="null"))
                                    {

                                        pro.setPrice_mrp(Integer.parseInt(obj.getString("mrp")));
                                    }
                                    if(!(obj.getString("mop").equals(null)||obj.getString("mop")==null||obj.getString("mop")=="null"))
                                    {
                                        pro.setPrice_mop(Integer.parseInt(obj.getString("mop")));
                                    }
                                    if(!(obj.getString("ksprice").equals(null)||obj.getString("ksprice")==null||obj.getString("ksprice")=="null"))
                                    {
                                        pro.setPrice_ks(Integer.parseInt(obj.getString("ksprice")));
                                    }
                                    if(obj.has("offer_id"))
                                    {
                                        if(obj.getString("offer_id").equals("-1"))
                                        {
                                            pro.set_has_offers(false);
                                        }
                                        else
                                        {
                                            pro.set_has_offers(true);
                                        }
                                    }
                                    pro.set_link(obj.getString("link"));
                                    if(brand.equals("Favorites"))
                                    {
                                        pro.setFavorite_status(true);
                                    }
                                    else
                                    {
                                        pro.setFavorite_status(false);
                                    }
                                    if(appConfig.getUserType().equals("Admin"))
                                    {
                                        mContentItems.add(pro);
                                    }
                                    else
                                    {
                                        if(!(pro.getPrice_mop() == 0 || pro.getPrice_mrp() == 0 || pro.getPrice_ks() == 0))
                                        {
                                            mContentItems.add(pro);

                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                           mAdapter.notifyDataSetChanged();
                        if(mContentItems.isEmpty())
                        {
                            //view1.setVisibility(View.VISIBLE);
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
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute() {
            // dismiss the dialog after getting all products


        }
    }

}
