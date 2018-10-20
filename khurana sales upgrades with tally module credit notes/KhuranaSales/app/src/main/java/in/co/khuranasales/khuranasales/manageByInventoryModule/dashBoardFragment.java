package in.co.khuranasales.khuranasales.manageByInventoryModule;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.co.khuranasales.khuranasales.AppConfig;
import in.co.khuranasales.khuranasales.AppController;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.inventoryViewModule.inventoryViewMainActivity;
import in.co.khuranasales.khuranasales.manageByInventoryModule.billsPayableDashboardDataModels.billPayable;
import in.co.khuranasales.khuranasales.manageByInventoryModule.bills_receivable_dashboard_data_models.billReceivable;
import in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels.creditNote;
import in.co.khuranasales.khuranasales.manageByInventoryModule.debitNodeDashboardDataModels.debitNote;
import in.co.khuranasales.khuranasales.manageByInventoryModule.journalVoucherDashboardDataModels.journalVoucher;
import in.co.khuranasales.khuranasales.manageByInventoryModule.paymentVoucherDashboardDataModels.paymentVoucher;
import in.co.khuranasales.khuranasales.manageByInventoryModule.purchaseVoucherDashboardDataModels.purchaseVoucher;
import in.co.khuranasales.khuranasales.manageByInventoryModule.receiptVoucherDasboardDataModels.receiptVoucher;
import in.co.khuranasales.khuranasales.manageByInventoryModule.retailVoucherDashboardDataModels.retailVoucher;

public class dashBoardFragment extends Fragment {
    private View view;
    private dashboardDetails details = new dashboardDetails();;
    private RelativeLayout credit_note_card_layout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.inventory_dashboard_layout, container, false);
        new get_dashboard_detail().execute();
        credit_note_card_layout = (RelativeLayout)view.findViewById(R.id.layout_credit_note);
        credit_note_card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_inventories_desc = new Intent(getActivity(), inventoryViewMainActivity.class);
                startActivity(intent_inventories_desc);
            }
        });
        return  view;
    }

    public void draw_chart(int id, ArrayList<String> x_values, ArrayList<Float> y_values)
    {
        BarChart chart = (BarChart) view.findViewById(id);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        YAxis yAxis = chart.getAxisLeft();


        XAxis xAxis = chart.getXAxis();
        if(x_values.size() > 4)
        {
            xAxis.setLabelRotationAngle(-45);
        }
        xAxis.setDrawGridLines(false);
        xAxis.isDrawLabelsEnabled();
        xAxis.setLabelCount(x_values.size());
        xAxis.setCenterAxisLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
               if(value >= 0 && value < x_values.size())
               {
                   return x_values.get((int)value);
               }
               return "";
            }
        });

        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < y_values.size(); i++) {
            entries.add(new BarEntry(i, y_values.get(i)));
        }


        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set);

        BarData data = new BarData(dataSets);

        data.setBarWidth(0.9f); // set custom bar width
        chart.setData(data);
        chart.setHighlightFullBarEnabled(true);
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.getLegend().setEnabled(false);
        chart.getLegend().setWordWrapEnabled(true);
        chart.animateY(3000, Easing.EasingOption.EaseInOutBounce);
        chart.invalidate(); // refresh

    }


    public void draw_line_chart(int id, ArrayList<String> x_values, ArrayList<Float> y_values)
    {
        LineChart chart = (LineChart) view.findViewById(id);
        final HashMap<Integer, String>numMap = new HashMap<>();
        for(int i = 0 ; i < x_values.size(); i++)
        {
            numMap.put(i, x_values.get(i));
        }

        List<Entry> entries1 = new ArrayList<Entry>();

        int i  = 1;
        for(float num : y_values){
            entries1.add(new Entry(i, num));
            i++;
        }

        LineDataSet dataSet = new LineDataSet(entries1, "Debit Amount");

        LineData data = new LineData(dataSet);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(x_values.size());
        xAxis.setCenterAxisLabels(true);
        if(x_values.size() > 4)
        {
            xAxis.setLabelRotationAngle(-45);
        }
        xAxis.setValueFormatter(new IAxisValueFormatter(){

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return numMap.get((int)value);
            }
        });

        chart.setData(data);
        chart.getLegend().setWordWrapEnabled(true);
        chart.animateY(3000, Easing.EasingOption.EaseInOutBounce);
        chart.invalidate();
    }

    public void draw_combined_chart(int id, ArrayList<String> x_values, ArrayList<Float> y_values)
    {

        CombinedChart mChart = view.findViewById(id);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setVisibleXRange(4,x_values.size());

        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        if(x_values.size() > 4)
        {
            xAxis.setLabelRotationAngle(-45);
        }
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return x_values.get((int)value);
            }
        });

        CombinedData data = new CombinedData();

        data.setData(generateLineData(y_values));
        data.setData(generateBarData(y_values));

        mChart.setData(data);
        mChart.invalidate();
    }

    private LineData generateLineData(ArrayList<Float> y_values) {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < y_values.size(); index++)
            entries.add(new Entry(index, y_values.get(index)));

        LineDataSet set = new LineDataSet(entries, "Receipt Vouchers Daily Outstanding");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData(ArrayList<Float> y_values) {

        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();

        for (int index = 0; index < y_values.size(); index++) {
            entries1.add(new BarEntry(index, y_values.get(index)));
            entries2.add(new BarEntry(index, y_values.get(index)));
        }

        BarDataSet set1 = new BarDataSet(entries1, "Outstanding Amount");
        set1.setColors(new int[]{Color.rgb(61, 165, 255), Color.rgb(23, 197, 255)});
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1);
        d.setBarWidth(barWidth);

        return d;
    }


    public void update_credit_note_details()
    {
     TextView credit_note_total_outstanding =  (TextView) view.findViewById(R.id.credit_note_outstanding_total);
     credit_note_total_outstanding.setText(details.getCredit_note_total_outstanding());
     TextView credit_note_todays_outstanding =  (TextView) view.findViewById(R.id.credit_note_outstanding_today);
     credit_note_todays_outstanding.setText(details.getCredit_note_todays_outstanding());
     ArrayList<String> x_values = new ArrayList<>();
     ArrayList<Float> y_values = new ArrayList<>();
     for(int i  = 0; i < details.credit_notes_past_15_days.size(); i++)
     {
        x_values.add(details.credit_notes_past_15_days.get(i).getCredit_date());
        y_values.add(Float.parseFloat(details.credit_notes_past_15_days.get(i).getCredit_amount()));
     }
     if(details.credit_notes_past_15_days.size() > 0)
     {
         draw_chart(R.id.credit_note_bar_chart, x_values, y_values);
     }
    }

    public void update_debit_note_details()
    {
        TextView debit_note_total_outstanding =  (TextView) view.findViewById(R.id.debit_note_outstanding_total);
        debit_note_total_outstanding.setText(details.getDebit_note_total_outstanding());
        TextView debit_note_todays_outstanding =  (TextView) view.findViewById(R.id.debit_note_outstanding_today);
        debit_note_todays_outstanding.setText(details.getDebit_note_todays_outstanding());
        ArrayList<String> x_values = new ArrayList<>();
        ArrayList<Float> y_values = new ArrayList<>();
        for(int i  = 0; i < details.debit_notes_past_15_days.size(); i++)
        {
            x_values.add(details.debit_notes_past_15_days.get(i).getDebit_date());
            y_values.add(Float.parseFloat(details.debit_notes_past_15_days.get(i).getDebit_amount()));
        }
        if(details.debit_notes_past_15_days.size() > 0)
        {
            draw_line_chart(R.id.debit_note_line_chart, x_values, y_values);
        }

    }

    public void update_receipt_voucher_details()
    {
        TextView receipt_vouchers_outstanding_total =  (TextView) view.findViewById(R.id.receipt_voucher_outstanding_total);
        receipt_vouchers_outstanding_total.setText(details.getReceipt_vouchers_total_outstanding());

        TextView receipt_vouchers_todays_outstanding =  (TextView) view.findViewById(R.id.receipt_voucher_outstanding_today);
        receipt_vouchers_todays_outstanding.setText(details.getReceipt_vouchers_todays_outstanding());

        TextView receipt_vouchers_total =  (TextView) view.findViewById(R.id.receipt_vouchers_total_count);
        receipt_vouchers_total.setText(details.getTotal_receipt_vouchers_count());

        TextView receipt_voucher_15_days_outstanding_amount = (TextView)view.findViewById(R.id.receipt_voucher_15_days_outstanding_amount);
        receipt_voucher_15_days_outstanding_amount.setText(details.getReceipt_vouchers_15_days_outstanding());

        ArrayList<String> x_values = new ArrayList<>();
        ArrayList<Float> y_values = new ArrayList<>();
        for(int i  = 0; i < details.receipt_vouchers.size(); i++)
        {
            x_values.add(details.receipt_vouchers.get(i).getVoucher_date());
            y_values.add((float) Double.parseDouble(details.receipt_vouchers.get(i).getVoucher_amount_ledgers_account()));
        }

        if(details.receipt_vouchers.size() > 0)
        {
            draw_combined_chart(R.id.receipt_voucher_combined_chart, x_values, y_values);
        }
      }

    public void update_purchase_vouchers_details()
    {
      TextView total_stock_bought = (TextView)view.findViewById(R.id.total_purchase_vouchers_generated);
      TextView total_stock_bought_today = (TextView)view.findViewById(R.id.total_purchase_vouchers_generated_today);
      TextView total_outstanding_today = (TextView)view.findViewById(R.id.purchase_voucher_outstanding_today);
      TextView total_outstanding = (TextView)view.findViewById(R.id.purchase_voucher_outstanding_total);

      total_stock_bought_today.setText(details.getTotal_purchase_voucher_stock_bought_today());
      total_stock_bought.setText(details.getTotal_stock_bought_purchase_voucher());
      total_outstanding.setText(details.getTotal_purchase_voucher_outstanding());
      total_outstanding_today.setText(details.getTotal_purchase_voucher_today_spent_amount());

      ArrayList<String> x_values = new ArrayList<>();
      ArrayList<Float> y_values = new ArrayList<>();
      for(int i  = 0; i < details.purchase_vouchers.size();i++)
      {
          x_values.add(details.purchase_vouchers.get(i).getPurchase_date());
          y_values.add(Float.parseFloat(details.purchase_vouchers.get(i).getPurchase_voucher_spent_amount()));
      }
        if(details.purchase_vouchers.size() > 0) {
            draw_chart(R.id.purchase_voucher_bar_chart, x_values, y_values);
        }
    }

    public void update_bills_receivables()
    {
        TextView bill_receivable_total_outstanding = (TextView)view.findViewById(R.id.bills_receivables_total_outstanding);
        TextView bill_receivable_total_outstanding_today = (TextView)view.findViewById(R.id.total_bills_receivables_outstanding_today);

        bill_receivable_total_outstanding.setText(details.getBills_receivable_total_outstanding());
        bill_receivable_total_outstanding_today.setText(details.getBills_receivable_todays_outstanding());

        ArrayList<String> x_values = new ArrayList<>();
        ArrayList<Float> y_values = new ArrayList<>();

        HashMap<String, Float> mapped_bills= new HashMap<>();
        for(int i  = 0; i < details.bills_receivables.size();i++)
        {
            if(mapped_bills.get(details.bills_receivables.get(i).getBill_date()) != null)
            {
                Log.d("BILLS","in if"+details.bills_receivables.get(i).getBill_date());
                float new_value = mapped_bills.get(details.bills_receivables.get(i).getBill_date()) + Float.parseFloat(details.bills_receivables.get(i).getBill_clearance_amount());
                mapped_bills.put(details.bills_receivables.get(i).getBill_date(),new_value);
            }
            else
            {
                Log.d("BILLS","in else"+details.bills_receivables.get(i).getBill_date());
                mapped_bills.put(details.bills_receivables.get(i).getBill_date(), Float.parseFloat(details.bills_receivables.get(i).getBill_clearance_amount()));
            }
        }

        for(Map.Entry<String, Float> e : mapped_bills.entrySet())
        {
            x_values.add(e.getKey());
            y_values.add(-e.getValue());
        }
        Log.d("BILLS",""+x_values.size());
        Log.d("BILLS",""+y_values.size());
        if(details.bills_receivables.size() > 0)
        {
            draw_line_chart(R.id.bills_receivables_line_chart, x_values, y_values);
        }
    }

    public void update_payment_vouchers()
    {
        TextView payment_voucher_total_outstanding = (TextView)view.findViewById(R.id.payment_vouchers_total_outstanding);
        TextView payment_voucher_todays_outstanding = (TextView)view.findViewById(R.id.total_payment_vouchers_outstanding_today);

        payment_voucher_todays_outstanding.setText(details.getPayment_todays_outstanding());
        payment_voucher_total_outstanding.setText(details.getPayment_outstanding_total());

        HashMap<String,Float> payment_vouchers_map  = new HashMap<>();

        ArrayList<String> x_values  = new ArrayList<>();
        ArrayList<Float> y_values = new ArrayList<>();

        for(int i  = 0 ; i < details.payment_vouchers.size(); i++)
        {
            paymentVoucher voucher = details.payment_vouchers.get(i);
            if(payment_vouchers_map.get(voucher.getPayment_date()) != null)
            {
                Float total = payment_vouchers_map.get(voucher.getPayment_date()) + Float.parseFloat(voucher.getPayment_voucher_amount());
                payment_vouchers_map.put(voucher.getPayment_date(), total);
            }
            else
            {
                payment_vouchers_map.put(voucher.getPayment_date(),Float.parseFloat(voucher.getPayment_voucher_amount()));
            }
        }

        for(Map.Entry<String, Float> entry : payment_vouchers_map.entrySet() )
        {
            x_values.add(entry.getKey());
            y_values.add(entry.getValue());
        }
        draw_chart(R.id.payment_vouchers_bar_chart, x_values, y_values);
    }

    public void update_bills_payable()
    {

        TextView bills_payable_total_outstanding = (TextView)view.findViewById(R.id.bills_payable_total_outstanding);
        TextView bills_payable_todays_outstanding = (TextView)view.findViewById(R.id.total_bills_payable_outstanding_today);

        bills_payable_todays_outstanding.setText(details.getTodays_bills_payabele_outstanding());
        bills_payable_total_outstanding.setText(details.getTotal_bills_payable_outstanding());

        HashMap<String,Float> payment_vouchers_map  = new HashMap<>();

        ArrayList<String> x_values  = new ArrayList<>();
        ArrayList<Float> y_values = new ArrayList<>();

        for(int i  = 0 ; i < details.bills_payable.size(); i++)
        {
            billPayable voucher = details.bills_payable.get(i);
            if(payment_vouchers_map.get(voucher.getBill_date()) != null)
            {
                Float total = payment_vouchers_map.get(voucher.getBill_date()) + Float.parseFloat(voucher.getBill_clearance_amount());
                payment_vouchers_map.put(voucher.getBill_date(), total);
            }
            else
            {
                payment_vouchers_map.put(voucher.getBill_date(),Float.parseFloat(voucher.getBill_clearance_amount()));
            }
        }

        for(Map.Entry<String, Float> entry : payment_vouchers_map.entrySet() )
        {
            x_values.add(entry.getKey());
            y_values.add(entry.getValue());
        }
        draw_combined_chart(R.id.bills_payable_combined_chart, x_values, y_values);
    }

    public void update_journal_vouchers()
    {
        TextView journal_vouchers_total_outstanding = (TextView)view.findViewById(R.id.journal_voucher_total_outstanding);
        TextView journal_vouchers_todays_outstanding = (TextView)view.findViewById(R.id.journal_voucher_payable_outstanding_today);

        journal_vouchers_todays_outstanding.setText(details.getJournal_voucher_total_today());
        journal_vouchers_total_outstanding.setText(details.getJournal_voucher_past_15_days());

        HashMap<String,Float> journal_vouchers_map  = new HashMap<>();

        ArrayList<String> x_values  = new ArrayList<>();
        ArrayList<Float> y_values = new ArrayList<>();

        for(int i  = 0 ; i < details.journal_vouchers.size(); i++)
        {
            journalVoucher voucher = details.journal_vouchers.get(i);
            if(journal_vouchers_map.get(voucher.getJounal_voucher_date()) != null)
            {
                Float total = journal_vouchers_map.get(voucher.getJounal_voucher_date()) + voucher.getItems_ids().split(",").length;
                journal_vouchers_map.put(voucher.getJounal_voucher_date(), total);
            }
            else
            {
                journal_vouchers_map.put(voucher.getJounal_voucher_date(),(float)voucher.getItems_ids().split(",").length);
            }
        }

        for(Map.Entry<String, Float> entry : journal_vouchers_map.entrySet() )
        {
            x_values.add(entry.getKey());
            y_values.add(entry.getValue());
        }
        draw_chart(R.id.journal_voucher_bar_chart, x_values, y_values);
    }

    public void  update_retail_vouchers()
    {
        TextView retail_vouchers_total_outstanding = (TextView)view.findViewById(R.id.retail_vouchers_total_outstanding);
        TextView retail_vouchers_todays_outstanding = (TextView)view.findViewById(R.id.retail_vouchers_payable_outstanding_today);
        TextView retail_voucher_15_days_outstanding = (TextView)view.findViewById(R.id.retail_vouchers_payable_outstanding_15_days);

        retail_vouchers_todays_outstanding.setText(details.getTotal_retail_outstanding_today());
        retail_vouchers_total_outstanding.setText(details.getTotal_retail_outstanding());
        retail_voucher_15_days_outstanding.setText(details.getTotal_retail_outstanding_15_days());

        HashMap<String,Float> retail_vouchers_map  = new HashMap<>();

        ArrayList<String> x_values  = new ArrayList<>();
        ArrayList<Float> y_values = new ArrayList<>();

        for(int i  = 0 ; i < details.retail_vouchers.size(); i++)
        {
            retailVoucher voucher = details.retail_vouchers.get(i);
            if(retail_vouchers_map.get(voucher.getVoucher_date()) != null)
            {
                Float total = retail_vouchers_map.get(voucher.getVoucher_date()) + Float.parseFloat(voucher.get_total_outstanding());
                retail_vouchers_map.put(voucher.getVoucher_date(), total);
            }
            else
            {
                retail_vouchers_map.put(voucher.getVoucher_date(), Float.parseFloat(voucher.get_total_outstanding()));
            }
        }

        for(Map.Entry<String, Float> entry : retail_vouchers_map.entrySet() )
        {
            x_values.add(entry.getKey());
            y_values.add(entry.getValue());
        }
        draw_line_chart(R.id.retail_vouchers_line_chart, x_values, y_values);
    }

    public class get_dashboard_detail extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            details.retail_vouchers.clear();
            details.journal_vouchers.clear();
            details.bills_payable.clear();
            details.payment_vouchers.clear();
            details.credit_notes_past_15_days.clear();
            details.debit_notes_past_15_days.clear();
            details.receipt_vouchers.clear();
            details.purchase_vouchers.clear();
            details.bills_receivables.clear();
            JSONObject params = new JSONObject();
            JsonObjectRequest request_dashboard_details = new JsonObjectRequest(Request.Method.GET, AppConfig.get_dashboard_details_url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        details.setCredit_note_todays_outstanding(response.getString("credit_note_todays_outstanding"));
                        details.setCredit_note_total_outstanding(response.getString("credit_note_total_outstanding"));
                        JSONArray past_credit_notes = response.getJSONArray("credit_notes_past_fifteen_days");
                        for(int i = 0; i < past_credit_notes.length(); i++)
                        {
                            JSONObject credit_note = past_credit_notes.getJSONObject(i);
                            creditNote credit_note_instance = new creditNote();
                            credit_note_instance.setCredit_date(credit_note.getString("date"));
                            credit_note_instance.setCredit_amount(credit_note.getString("credit_note_amount"));
                            details.credit_notes_past_15_days.add(credit_note_instance);
                        }
                        details.setDebit_note_todays_outstanding(response.getString("debit_note_todays_outstanding"));
                        details.setDebit_note_total_outstanding(response.getString("debit_note_total_outstanding"));
                        JSONArray debit_notes_array = response.getJSONArray("debit_notes_past_fifteen_days");
                        for(int l = 0; l <debit_notes_array.length(); l++)
                        {
                            JSONObject debit_note = debit_notes_array.getJSONObject(l);
                            debitNote debit_note_instance = new debitNote();
                            debit_note_instance.setDebit_date(debit_note.getString("date"));
                            debit_note_instance.setDebit_amount(debit_note.getString("debit_note_amount"));
                            details.debit_notes_past_15_days.add(debit_note_instance);
                        }

                        details.setReceipt_vouchers_15_days_outstanding(response.getString("total_15_days_spent_receipt_voucher"));
                        details.setReceipt_vouchers_todays_outstanding(response.getString("total_spent_today_receipt_voucher"));
                        details.setReceipt_vouchers_total_outstanding(response.getString("total_receipt_voucher_outstandings"));
                        details.setTotal_receipt_vouchers_count(response.getString("total_receipt_vouchers"));
                        JSONArray receipt_vouchers = response.getJSONArray("receipts_voucher_receipts");
                        for(int i  = 0;  i < receipt_vouchers.length(); i++)
                        {
                            JSONObject voucher  = receipt_vouchers.getJSONObject(i);
                            receiptVoucher voucher_instance = new receiptVoucher();
                            voucher_instance.setVoucher_date(voucher.getString("voucher_date"));
                            voucher_instance.setVoucher_amount_ledgers_account(voucher.getString("total_received"));
                            details.receipt_vouchers.add(voucher_instance);
                        }
                        details.setTotal_purchase_voucher_count(response.getString("total_purchase_vouchers_count"));
                        details.setTotal_purchase_voucher_outstanding(response.getString("total_purchase_voucher_outstanding"));
                        details.setTotal_purchase_voucher_stock_bought_today(response.getString("total_purchase_voucher_stock_bought_today"));
                        details.setTotal_purchase_voucher_today_spent_amount(response.getString("total_purchase_voucher_todays_spent_amount"));
                        details.setTotal_stock_bought_purchase_voucher(response.getString("total_stock_bought_purchase_voucher"));
                        JSONArray purchase_voucers = response.getJSONArray("vouchers_15_days");
                        for(int l = 0; l < purchase_voucers.length(); l++)
                        {
                            JSONObject purchase_voucher = purchase_voucers.getJSONObject(l);
                            purchaseVoucher purchase_voucher_instance = new purchaseVoucher();
                            purchase_voucher_instance.setPurchase_date(purchase_voucher.getString("voucher_date"));
                            purchase_voucher_instance.setPurchase_voucher_spent_amount(purchase_voucher.getString("purchase_amount"));
                            details.purchase_vouchers.add(purchase_voucher_instance);
                        }

                        details.setBills_receivable_todays_outstanding(response.getString("bills_receivable_todays_outstanding"));
                        details.setBills_receivable_total_outstanding(response.getString("bills_receivable_total_outstanding"));
                        JSONArray bills_receivables_array = response.getJSONArray("bills_receivable_past_fifteen_days");
                        for(int i =0 ; i < bills_receivables_array.length(); i++)
                        {
                            JSONObject object = bills_receivables_array.getJSONObject(i);
                            billReceivable bill = new billReceivable();
                            bill.setBill_date(object.getString("date"));
                            bill.setBill_clearance_amount(object.getString("bill_amount"));
                            details.bills_receivables.add(bill);
                        }

                        details.setPayment_outstanding_total(response.getString("total_payment_outstandings"));
                        details.setPayment_todays_outstanding(response.getString("payment_todays_outstanding"));
                        JSONArray payment_vouchers = response.getJSONArray("payment_fifteen_days");
                        for(int p = 0; p < payment_vouchers.length(); p++)
                        {
                            JSONObject payment_voucher_object = payment_vouchers.getJSONObject(p);
                            paymentVoucher voucher = new paymentVoucher();
                            voucher.setPayment_date(payment_voucher_object.getString("date"));
                            voucher.setPayment_voucher_amount(payment_voucher_object.getString("payment_amount"));
                            details.payment_vouchers.add(voucher);
                        }

                        details.setTotal_bills_payable_outstanding(response.getString("payable_bills_total_outstanding"));
                        details.setTodays_bills_payabele_outstanding(response.getString("payabel_bills_todays_outstanding"));
                        JSONArray bills_payable = response.getJSONArray("payable_bills_past_fifteen_days");
                        for(int i = 0 ; i < bills_payable.length(); i++)
                        {
                            JSONObject object = bills_payable.getJSONObject(i);
                            billPayable  bill = new billPayable();
                            bill.setBill_date(object.getString("date"));
                            bill.setBill_clearance_amount(object.getString("bill_amount"));
                            details.bills_payable.add(bill);
                        }

                        details.setJournal_voucher_total_today(response.getString("journal_voucher_todays_outstanding"));
                        details.setJournal_voucher_past_15_days(response.getString("total_journal_vouchers"));
                        JSONArray array = response.getJSONArray("journal_voucher_past_fifteen_days");
                        for(int i = 0 ; i < array.length(); i++)
                        {
                            JSONObject object = array.getJSONObject(i);
                            journalVoucher voucher = new journalVoucher();
                            voucher.setJounal_voucher_date(object.getString("date"));
                            voucher.setItems_ids(object.getString("items_ids"));
                            details.journal_vouchers.add(voucher);
                        }

                        details.setTotal_retail_outstanding(response.getString("total_retail_outstanding"));
                        details.setTotal_retail_outstanding_today(response.getString("total_retail_outstanding_today"));
                        details.setTotal_retail_outstanding_15_days(response.getString("total_retail_outstanding_15_days"));
                        JSONArray retail_vouchers = response.getJSONArray("retail_vouchers");
                        for(int k = 0 ; k < retail_vouchers.length(); k++)
                        {
                            JSONObject object = retail_vouchers.getJSONObject(k);
                            retailVoucher voucher = new retailVoucher();
                            voucher.setVoucher_date(object.getString("voucher_date"));
                            voucher.set_total_outstanding(object.getString("voucher_outstanding"));
                            details.retail_vouchers.add(voucher);
                        }
                        update_credit_note_details();
                        update_debit_note_details();
                        update_receipt_voucher_details();
                        update_purchase_vouchers_details();
                        update_bills_receivables();
                        update_payment_vouchers();
                        update_bills_payable();
                        update_journal_vouchers();
                        update_retail_vouchers();

                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Sorry data not received please retry!!",Toast.LENGTH_SHORT);
                }
            });
            request_dashboard_details.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            AppController.getInstance().addToRequestQueue(request_dashboard_details);
            return null;
        }
    }
}
