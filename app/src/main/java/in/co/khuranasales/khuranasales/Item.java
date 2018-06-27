package in.co.khuranasales.khuranasales;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Simple POJO model for example
 */
public class Item {

    private String price;
    private String pledgePrice;
    private String fromAddress;
    private String toAddress;
    private int requestsCount;
    private String date;
    private String time;
    private static AppConfig appConfig;

    public  Item()
    {

    }
    public Item(Context context) {
            appConfig = new AppConfig(context);
    }

    public Item(String price, String pledgePrice, String fromAddress, String toAddress, int requestsCount, String date, String time) {
        this.price = price;
        this.pledgePrice = pledgePrice;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.requestsCount = requestsCount;
        this.date = date;
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPledgePrice() {
        return pledgePrice;
    }

    public void setPledgePrice(String pledgePrice) {
        this.pledgePrice = pledgePrice;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public int getRequestsCount() {
        return requestsCount;
    }

    public void setRequestsCount(int requestsCount) {
        this.requestsCount = requestsCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (requestsCount != item.requestsCount) return false;
        if (price != null ? !price.equals(item.price) : item.price != null) return false;
        if (pledgePrice != null ? !pledgePrice.equals(item.pledgePrice) : item.pledgePrice != null)
            return false;
        if (fromAddress != null ? !fromAddress.equals(item.fromAddress) : item.fromAddress != null)
            return false;
        if (toAddress != null ? !toAddress.equals(item.toAddress) : item.toAddress != null)
            return false;
        if (date != null ? !date.equals(item.date) : item.date != null) return false;
        return !(time != null ? !time.equals(item.time) : item.time != null);

    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (pledgePrice != null ? pledgePrice.hashCode() : 0);
        result = 31 * result + (fromAddress != null ? fromAddress.hashCode() : 0);
        result = 31 * result + (toAddress != null ? toAddress.hashCode() : 0);
        result = 31 * result + requestsCount;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    /**
     * @return List of elements prepared for tests
     */
  public static class LoadOutbox extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Final_Cart.items.clear();
        }
        @Override
        protected Void doInBackground(String... params)  {
            Log.d("URL:",AppConfig.url_get_bought+params[0]+"&&order_date="+Final_Cart.date);
            JsonArrayRequest movieReq = new JsonArrayRequest((AppConfig.url_get_bought+params[0]+"&&order_date="+Final_Cart.date),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("True Response", response.toString());
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Product pro = new Product();
                                    pro.set_Name(obj.getString("name"));
                                        pro.set_Stock(obj.getInt("stock"));
                                        pro.setPrice_mop(obj.getInt("MOP"));
                                        pro.setPrice_mrp(obj.getInt("MRP"));
                                        pro.setPrice_ks(obj.getInt("ksprice"));
                                        pro.setProduct_id(obj.getInt("product_id"));
                                        pro.set_link(obj.getString("links"));
                                        pro.set_to_address(obj.getString("address"));
                                        pro.set_sales_order_date(obj.getString("sales_order_date"));
                                        pro.setOrder_status(obj.getString("sales_order_status"));
                                        if (!(obj.getString("ksprice").equals(null) || obj.getString("ksprice") == null)) {
                                        pro.setPrice_mrp(Integer.parseInt(obj.getString("ksprice")));
                                        pro.set_sales_order_number(obj.getInt("sales_order_number"));
                                        pro.setSales_order_invoice_number(Integer.parseInt(obj.getString("sales_order_invoice_number")));
                                        }
                                    Final_Cart.items.add(pro);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        Final_Cart.adapter.notifyDataSetChanged();
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

 public static void  getProducts()
 {
     JsonArrayRequest movieReq = new JsonArrayRequest((AppConfig.url_get_bought+"ankushkhurana34@gmail.com"),
             new Response.Listener<JSONArray>() {
                 @Override
                 public void onResponse(JSONArray response) {
                     Log.d("True Response", response.toString());
                     for (int i = 0; i < response.length(); i++) {
                         try {
                             JSONObject obj = response.getJSONObject(i);
                             Product pro = new Product();
                             pro.set_Name(obj.getString("name"));
                             pro.set_Stock(10);
                             if (!(obj.getString("ksprice").equals(null) || obj.getString("ksprice") == null)) {
                                 pro.setPrice_mrp(Integer.parseInt(obj.getString("ksprice")));
                             }
                             Final_Cart.items.add(pro);
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                Final_Cart.adapter.notifyDataSetChanged();
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
}
