package in.co.khuranasales.khuranasales;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.print.PrintManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.easing.linear.Linear;
import com.google.common.io.LineReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import in.co.khuranasales.khuranasales.CircularPagerIndicartor.CirclePageIndicator;
import in.co.khuranasales.khuranasales.CircularPagerIndicartor.FragmentPermission1;
import in.co.khuranasales.khuranasales.CircularPagerIndicartor.FragmentPermission2;
import in.co.khuranasales.khuranasales.CircularPagerIndicartor.FragmentPermission3;

import static com.github.florent37.carpaccio.controllers.helper.ViewHelper.getBitmapFromView;

/**
 * Created by amura on 14/4/18.
 */

public class Invoice_Activity extends AppCompatActivity {
    public TextView customer_name_text;
    public TextView customer_phone_text;
    public TextView invoice_date;
    public TextView sub_total_amount_text;
    public TextView total_amount_text;
    public TextView total_taxable_cgst;
    public TextView total_taxable_sgst;
    public TextView bill_by_email;
    public TextView taxable_cgst_percentage;
    public LinearLayout linearLayout_payments;
    public TextView taxable_sgst_percentage;
    public TextView amount_in_words;
    public TextView tax_cgst;
    public TextView invoice_number;
    public TextView tax_sgst;
    public LinearLayout linear_main;
    public ArrayList<Product> products;

    private ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setVisibility(View.INVISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mPager.setAdapter(new MyAdapter(fragmentManager));

        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        indicator.setVisibility(View.INVISIBLE);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        NUM_PAGES = 3;

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {
            }
        });


        StrictMode.VmPolicy.Builder builder_new = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder_new.build());
        AppConfig appConfig = new AppConfig(getApplicationContext());
        invoice_number = (TextView) findViewById(R.id.invoice_number);
        linearLayout_payments = (LinearLayout) findViewById(R.id.payment_info_contents);
        products = getIntent().getParcelableArrayListExtra("products");
        String customer_name = getIntent().getStringExtra("customer_name");
        String customer_phone = getIntent().getStringExtra("customer_phone");
        String promoter_email = getIntent().getStringExtra("promoter_email");
        Integer cash_amount = getIntent().getIntExtra("Cash", 0);
        Integer finance_amount = getIntent().getIntExtra("Finance", 0);
        Integer paytm_amount = getIntent().getIntExtra("Paytm", 0);
        Integer card_amount = getIntent().getIntExtra("Card", 0);
        Integer cheque_amount = getIntent().getIntExtra("Cheque", 0);

        Log.d("Cash Amount", "" + getIntent().getIntExtra("Cash", 0));
        Log.d("Finance Amount", "" + getIntent().getIntExtra("Finance", 0));
        Log.d("Paytm Amount", "" + getIntent().getIntExtra("Paytm", 0));
        Log.d("Card Amount", "" + getIntent().getIntExtra("Card", 0));
        Log.d("Cheque Amount", "" + getIntent().getIntExtra("Cheque", 0));

        int total_amount = getIntent().getExtras().getInt("total_payment", 0);

        customer_name_text = (TextView) findViewById(R.id.customer_name);
        customer_phone_text = (TextView) findViewById(R.id.customer_contact);
        invoice_date = (TextView) findViewById(R.id.invoice_date);
        sub_total_amount_text = (TextView) findViewById(R.id.sub_total_tax);
        total_amount_text = (TextView) findViewById(R.id.total_amount_after_tax);
        total_taxable_cgst = (TextView) findViewById(R.id.taxable_amount_cgst);
        total_taxable_sgst = (TextView) findViewById(R.id.taxable_amount_sgst);
        taxable_cgst_percentage = (TextView) findViewById(R.id.taxable_cgst_percentage);
        taxable_sgst_percentage = (TextView) findViewById(R.id.taxable_sgst_percentage);
        tax_cgst = (TextView) findViewById(R.id.tax_cgst);
        tax_sgst = (TextView) findViewById(R.id.tax_sgst);
        bill_by_email = (TextView) findViewById(R.id.bill_by_email);
        amount_in_words = (TextView) findViewById(R.id.amount_in_words);

        taxable_cgst_percentage.setText("" + AppConfig.CGST + "%");
        taxable_sgst_percentage.setText("" + AppConfig.SGST + "%");
        total_taxable_cgst.setText("" + (int) total_amount);
        total_taxable_sgst.setText("" + (int) total_amount);
        float tax_cgst_total = (total_amount - ((100 * total_amount) / (100 + AppConfig.CGST)));
        float tax_sgst_total = (total_amount - ((100 * total_amount) / (100 + AppConfig.SGST)));
        tax_cgst.setText("" + (int) tax_cgst_total + "");
        tax_sgst.setText("" + (int) tax_sgst_total + "");
        sub_total_amount_text.setText("Subtotal Amount: " + (total_amount - (tax_cgst_total + tax_sgst_total)) + " /-");
        total_amount_text.setText("Total Amount: " + (total_amount) + "  /-");
        customer_name_text.setText("" + customer_name);
        customer_phone_text.setText("" + customer_phone);
        bill_by_email.setText(promoter_email);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formatted_date = simpleDateFormat.format(c);
        String return_val_in_english = EnglishNumberToWords.convert(total_amount);
        String upper_case = return_val_in_english.substring(0, 1).toUpperCase();
        String total_amount_in_words = upper_case + return_val_in_english.substring(1, return_val_in_english.length());
        amount_in_words.setText(total_amount_in_words + " only");
        invoice_date.setText("Invoice Date: " + formatted_date);
        invoice_number.setText(formatted_date.replace("-", "") + "-" + "CN" + appConfig.getCustomer_id() + "-INV");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        TableLayout layout = (TableLayout) findViewById(R.id.order_items);
        TableRow row = new TableRow(getApplicationContext());
        linear_main = new LinearLayout(getApplicationContext());
        linear_main.setOrientation(LinearLayout.VERTICAL);
        linear_main.setMinimumWidth(width);
        for (Product product : products) {

            View view = getLayoutInflater().inflate(R.layout.bill_info_row, null);
            LinearLayout.LayoutParams params6 = new LinearLayout.LayoutParams(displayMetrics.widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params6);
            TextView txt = view.findViewById(R.id.date);
            StringBuilder builder = new StringBuilder();
            for (int k = 0; k < product.selected_batch_numbers.size(); k++) {
                builder.append(product.selected_batch_numbers.get(k).getNumber() + "\n");
            }
            txt.setText(builder.toString());
            TextView txt1 = view.findViewById(R.id.name);
            txt1.setText("" + product.get_Name());
            TextView txt2 = view.findViewById(R.id.quantity);
            txt2.setText(" " + product.get_Stock());
            TextView txt3 = view.findViewById(R.id.mop);
            txt3.setText("" + product.getPrice_mop());
            TextView txt4 = view.findViewById(R.id.ksprice);
            txt4.setText("" + product.getProduct_HSN());
            linear_main.addView(view);
        }
        row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        row.addView(linear_main);
        layout.addView(row);
        if (cash_amount != 0) {
            add_layout("Cash", cash_amount);
        }
        if (cheque_amount != 0) {
            add_layout("Cheque", cheque_amount);
        }
        if (finance_amount != 0) {
            add_layout("Finance", finance_amount);
        }
        if (card_amount != 0) {
            add_layout("Card", card_amount);
        }
        if (paytm_amount != 0) {
            add_layout("Paytm", paytm_amount);
        }
    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            Fragment fragment = null;
            if (arg0 == 0) {
                fragment = new FragmentPermission1();
            }
            if (arg0 == 1) {
                fragment = new FragmentPermission2();
            }
            if (arg0 == 2) {
                fragment = new FragmentPermission3();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
        /************************************Finishes Up View Pager Adapter******************************************************/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_invoice, menu);
        final View share = menu.findItem(R.id.action_share).getActionView();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_permissions();
                createPdf("Share");
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            check_permissions();
            createPdf("Save");
        }
        if (id == R.id.action_print) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            Log.d("children count: ", "" + linear_main.getChildCount());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(displayMetrics.widthPixels + (int) (displayMetrics.widthPixels * 550 / 293), ViewGroup.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < linear_main.getChildCount(); i++) {
                linear_main.getChildAt(i).setLayoutParams(params);
            }
            ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                    .findViewById(R.id.invoice_layout)).getChildAt(0);
            printDocument(viewGroup);
        }
        return true;
    }

    public void printDocument(View view) {
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        String jobName = "Invoice Printing Document";

        printManager.print(jobName, new MyPrintDocumentAdapter(view, this, products),
                null);
    }

    @Override
    protected void onResume() {
        View v = this.findViewById(R.id.invoice_layout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.findViewById(R.id.invoice_layout).setLayoutParams(params);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        LinearLayout.LayoutParams params_new = new LinearLayout.LayoutParams(displayMetrics.widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < linear_main.getChildCount(); i++) {
            linear_main.getChildAt(i).setLayoutParams(params_new);
        }

        super.onResume();
    }

    public void add_layout(String payment_type_input, Integer payment_amount_input) {
        if (payment_type_input.equals("Finance")) {
            payment_type_input = getIntent().getStringExtra("Financer");
        }
        View payment_info_view = getLayoutInflater().inflate(R.layout.payment_row_invoice, null);
        TextView payment_type = payment_info_view.findViewById(R.id.payment_type);
        TextView payment_amount = payment_info_view.findViewById(R.id.payment_amount);
        payment_type.setText(payment_type_input + " Payment");
        payment_amount.setText("Rs " + payment_amount_input + ".00 /-");
        linearLayout_payments.addView(payment_info_view);

    }

    private void createPdf(String option) {
        // create a new document
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Log.d("children count: ", "" + linear_main.getChildCount());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(displayMetrics.widthPixels + (int) (displayMetrics.widthPixels * 550 / 293), ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < linear_main.getChildCount(); i++) {
            linear_main.getChildAt(i).setLayoutParams(params);
        }
        PdfDocument document = new PdfDocument();

        // crate a page description


        // draw something on the page
        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(R.id.invoice_layout)).getChildAt(0);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(displayMetrics.widthPixels + (int) (displayMetrics.widthPixels * 550 / 293), viewGroup.getMeasuredHeight(), 1).create();

        // start a page

        PdfDocument.Page page = document.startPage(pageInfo);


        int measureWidth = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getWidth(), View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getHeight(), View.MeasureSpec.EXACTLY);

//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View content = inflater.inflate(view, null);
        viewGroup.measure(measureWidth, measuredHeight);
        viewGroup.layout(0, 0, page.getCanvas().getWidth(), page.getCanvas().getHeight());
        viewGroup.draw(page.getCanvas());


        viewGroup.draw(page.getCanvas());

        // finish the page
        document.finishPage(page);

        // write the document content

        String state = Environment.getExternalStorageState();
        String root = "";
        String fileName = "/invoice" + System.currentTimeMillis() + ".pdf";
        String parent = "KhuranaSales";
        File mFile;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            root = Environment.getExternalStorageDirectory().toString();
            mFile = new File(root, parent);
            if (!mFile.isDirectory())
                mFile.mkdirs();
        } else {
            root = Invoice_Activity.this.getFilesDir().toString();
            mFile = new File(root, parent);
            if (!mFile.isDirectory())
                mFile.mkdirs();
        }
        String strCaptured_FileName = root + "/KhuranaSales" + fileName;
        String targetPdf = strCaptured_FileName;
        File filePath = new File(targetPdf);

        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
            onResume();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
            onResume();
        }

        // close the document
        document.close();
        if (option.equals("Share")) {
            Uri uri = Uri.fromFile(filePath);

            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("application/pdf");
            share.putExtra(Intent.EXTRA_STREAM, uri);
            this.startActivity(share);
        }

    }

    public void check_permissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);


            }
        } else {
            // Permission has already been granted
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),"Thank U For Permission",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Some Features might not work Sorry !!",Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, CategorizeDataActivity.class);
        startActivity(intent);
        finish();

    }
}
