package in.co.khuranasales.khuranasales;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.easing.linear.Linear;
import com.google.common.io.LineReader;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabStop;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import in.co.khuranasales.khuranasales.exportWorkers.exportMainActivity;

import static com.github.florent37.carpaccio.controllers.helper.ViewHelper.getBitmapFromView;

/**
 * Created by amura on 14/4/18.
 */

public class Invoice_Activity extends AppCompatActivity {
    public TextView customer_name_text;
    public TextView customer_phone_text;
    public TextView customer_email_text;
    public TextView customer_address_text;
    public TextView customer_gst_text;

    public TextView promoter_name;
    public TextView promoter_email;

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
    public boolean share_invoice = false;
    public boolean save_invoice = false;
    public boolean print_invoice = false;

    private ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private String invoice_number_from_server = "";
    public AppConfig appConfig ;

    public Integer cash_amount = 0 ;
    public Integer finance_amount = 0 ;
    public Integer paytm_amount = 0 ;
    public Integer card_amount  = 0 ;
    public Integer cheque_amount = 0 ;
    public Integer credit_amount = 0 ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice);
        appConfig = new AppConfig(getApplicationContext());
        promoter_name = (TextView)findViewById(R.id.promoter_name);
        promoter_email = (TextView)findViewById(R.id.promoter_email);
        promoter_name.setText("Sold By Name: "+appConfig.getUser_name());
        promoter_email.setText("Sold By Email: "+appConfig.getUser_email().trim());

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

        invoice_number = (TextView) findViewById(R.id.invoice_number);
        linearLayout_payments = (LinearLayout) findViewById(R.id.payment_info_contents);
        products = getIntent().getParcelableArrayListExtra("products");
        String customer_name = getIntent().getStringExtra("customer_name");
        String customer_phone = getIntent().getStringExtra("customer_phone");

        String customer_email =getIntent().getStringExtra("customer_email");
        String customer_address =getIntent().getStringExtra("customer_address");
        String customer_gst = getIntent().getStringExtra("customer_gst");

        String promoter_email = getIntent().getStringExtra("promoter_email");
        cash_amount = getIntent().getIntExtra("Cash", 0);
        finance_amount = getIntent().getIntExtra("Finance", 0);
        paytm_amount = getIntent().getIntExtra("Paytm", 0);
        card_amount = getIntent().getIntExtra("Card", 0);
        cheque_amount = getIntent().getIntExtra("Cheque", 0);
        credit_amount = getIntent().getIntExtra("credit_amount",0);
        if(getIntent().hasExtra("invoice_number"))
        {
            invoice_number_from_server = getIntent().getExtras().getString("invoice_number");
        }
        Log.d("Cash Amount", "" + getIntent().getIntExtra("Cash", 0));
        Log.d("Finance Amount", "" + getIntent().getIntExtra("Finance", 0));
        Log.d("Paytm Amount", "" + getIntent().getIntExtra("Paytm", 0));
        Log.d("Card Amount", "" + getIntent().getIntExtra("Card", 0));
        Log.d("Cheque Amount", "" + getIntent().getIntExtra("Cheque", 0));


        int total_amount = 0;
        if(getIntent().getExtras().getString("total_payment") != null)
        {
            total_amount = Integer.parseInt(getIntent().getExtras().getString("total_payment"));
        }
        Log.d("TOTALAMOUNT",""+total_amount);
        customer_name_text = (TextView) findViewById(R.id.customer_name);
        customer_phone_text = (TextView) findViewById(R.id.customer_contact);
        customer_email_text = (TextView)findViewById(R.id.customer_email);
        customer_address_text = (TextView)findViewById(R.id.customer_address);
        customer_gst_text = (TextView)findViewById(R.id.customer_gst);

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


        if(getIntent().getExtras().getString("class_name").equals("final_cart_activity"))
        {
            Integer total_cgst_amount_reissue = 0;
            Integer total_sgst_amount_reissue = 0;
            total_cgst_amount_reissue = (int)Math.ceil(Float.parseFloat(getIntent().getExtras().getString("total_cgst_amount")));
            total_sgst_amount_reissue = (int)Math.ceil(Float.parseFloat(getIntent().getExtras().getString("total_sgst_amount")));
            taxable_cgst_percentage.setText("" + Math.floor((100* total_cgst_amount_reissue / (total_amount - total_cgst_amount_reissue)))+ " %");
            taxable_sgst_percentage.setText("" + Math.floor((100* total_sgst_amount_reissue/(total_amount-total_sgst_amount_reissue)) ) + " %");
            tax_cgst.setText("" + (int) total_cgst_amount_reissue + "");
            tax_sgst.setText("" + (int) total_sgst_amount_reissue + "");
            total_taxable_cgst.setText("" + (int) total_amount);
            total_taxable_sgst.setText("" + (int) total_amount);
            sub_total_amount_text.setText("Subtotal Amount: " + (total_amount - (total_cgst_amount_reissue + total_sgst_amount_reissue)) + " /-");
            total_amount_text.setText("Total Amount: " + (total_amount) + "  /-");

        }
        else if(getIntent().getExtras().getString("class_name").equals("buy_final_activity"))
        {
            Integer total_cgst_amount_main = 0;
            Integer total_sgst_amount_main = 0;
            if(appConfig.getUserType().equals("Admin") || appConfig.getUserType().equals("Dealer"))
            {
                for(Product p : products)
                {
                    float total_cgst_percentage = ((100 + (p.getTax()/2))/100);
                    total_cgst_amount_main += (int)Math.ceil((p.getPrice_ks() * p.get_Stock()) - ((p.getPrice_ks() * p.get_Stock()) / total_cgst_percentage)) ;
                    float total_sgst_percentage = ((100 + (p.getTax()/2))/100);
                    total_sgst_amount_main += (int)Math.ceil((p.getPrice_ks() * p.get_Stock()) - ((p.getPrice_ks() * p.get_Stock()) / total_sgst_percentage)) ;
                }
                tax_cgst.setText("" + (int) total_cgst_amount_main + "");
                tax_sgst.setText("" + (int) total_sgst_amount_main + "");
                total_taxable_cgst.setText("" + (int) total_amount);
                total_taxable_sgst.setText("" + (int) total_amount);
                sub_total_amount_text.setText("Subtotal Amount: " + (total_amount - (total_cgst_amount_main + total_sgst_amount_main)) + " /-");
                total_amount_text.setText("Total Amount: " + (total_amount) + "  /-");
                taxable_cgst_percentage.setText("" + Math.floor((100* total_cgst_amount_main / (total_amount - total_cgst_amount_main)))+ "%");
                taxable_sgst_percentage.setText("" + Math.floor((100* total_sgst_amount_main/(total_amount-total_sgst_amount_main)) ) + "%");

            }
            else
            {
                for(Product p : products)
                {
                    float total_cgst_percentage = ((100 + (p.getTax()/2))/100);
                    total_cgst_amount_main += (int)Math.ceil((p.getPrice_mop() * p.get_Stock()) - ((p.getPrice_mop() * p.get_Stock()) / total_cgst_percentage)) ;
                    float total_sgst_percentage = ((100 + (p.getTax()/2))/100);
                    total_sgst_amount_main += (int)Math.ceil((p.getPrice_mop() * p.get_Stock()) - ((p.getPrice_mop() * p.get_Stock()) / total_sgst_percentage)) ;
                }
                tax_cgst.setText("" + (int) total_cgst_amount_main + "");
                tax_sgst.setText("" + (int) total_sgst_amount_main + "");
                total_taxable_cgst.setText("" + (int) total_amount);
                total_taxable_sgst.setText("" + (int) total_amount);
                sub_total_amount_text.setText("Subtotal Amount: " + (total_amount - (total_cgst_amount_main + total_sgst_amount_main)) + " /-");
                total_amount_text.setText("Total Amount: " + (total_amount) + "  /-");
                taxable_cgst_percentage.setText("" + Math.floor((100* total_cgst_amount_main / (total_amount - total_cgst_amount_main)))+ "%");
                taxable_sgst_percentage.setText("" + Math.floor((100* total_sgst_amount_main/(total_amount-total_sgst_amount_main)) ) + "%");

            }

        }

        customer_name_text.setText("Customer Name: " + customer_name);
        customer_phone_text.setText("Customer Contact: " + customer_phone);
        customer_email_text.setText("Customer Email: "+customer_email);
        customer_address_text.setText("Customer Address: "+customer_address);
        customer_gst_text.setText("Customer GST: "+customer_gst);

        bill_by_email.setText(promoter_email);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formatted_date = simpleDateFormat.format(c);
        String return_val_in_english = EnglishNumberToWords.convert(total_amount);
        String upper_case = return_val_in_english.substring(0, 1).toUpperCase();
        String total_amount_in_words = upper_case + return_val_in_english.substring(1, return_val_in_english.length());
        amount_in_words.setText(total_amount_in_words + " only");
        invoice_date.setText("Invoice Date: " + formatted_date);
        invoice_number.setText(formatted_date.replace("-", "") + "-" + "CN" + appConfig.getCustomer_id() + "-INV"+invoice_number_from_server);
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
            StringBuilder builder = new StringBuilder("");
            for (int k = 0; k < product.selected_batch_numbers.size(); k++) {
                builder.append(product.selected_batch_numbers.get(k).getNumber() + "\n");
            }
            if(!(builder.toString().length() > 5))
            {
                txt.setText("N/A");
            }
            else
            {
                txt.setText(builder.toString());
            }
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
        if (credit_amount != 0) {
            add_layout("Credit", credit_amount);
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
                share_invoice = true;
                generate_invoice_printable();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            check_permissions();
            save_invoice = true;
            generate_invoice_printable();

        }
        if (id == R.id.action_print) {
            print_invoice = true;
            check_permissions();
            generate_invoice_printable();
        }
        return true;
    }

    public void generate_invoice_printable()
    {
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
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Khurana sales business solutions");
            document.addCreator("Ankush Amit Khurana");

            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            PdfPTable table = new PdfPTable(1);
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c);
            table.addCell(getCell("Khurana Sales, Sales Order",PdfPCell.ALIGN_CENTER));

            table.setWidthPercentage(100);
            table.setSpacingAfter(10);

            PdfPTable table_invoice_top  = new PdfPTable(1);
            table_invoice_top.setWidthPercentage(100);
            table_invoice_top.setSpacingAfter(10);

            PdfPTable khurana_sales_label = new PdfPTable(1);
            khurana_sales_label.addCell(getCell("Khurana Sales",PdfPCell.ALIGN_CENTER));
            khurana_sales_label.setWidthPercentage(100);
            khurana_sales_label.setSpacingBefore(5);
            khurana_sales_label.setSpacingAfter(10);

            PdfPTable khurana_sales_shop_description = new PdfPTable(2);
            khurana_sales_shop_description.setWidthPercentage(100);
            khurana_sales_shop_description.setSpacingAfter(5);
            khurana_sales_shop_description.setSpacingBefore(5);
            khurana_sales_shop_description.setWidths(new int[]{1, 3});

            Drawable d = getResources().getDrawable(R.drawable.khuranalogo);
            BitmapDrawable bitDw = ((BitmapDrawable) d);
            Bitmap bmp = bitDw.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 20, stream);
            Image image = Image.getInstance(stream.toByteArray());

            PdfPCell cell_image = new PdfPCell(image);
            cell_image.setFixedHeight(30);
            cell_image.setBorder(PdfPCell.NO_BORDER);
            cell_image.setPaddingBottom(10);
            cell_image.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

            khurana_sales_shop_description.setSpacingAfter(10);
            khurana_sales_shop_description.addCell(cell_image);
            khurana_sales_shop_description.addCell(getCell("Shop no 11 kumar sadan building, 444 somwar peth pune\n" +
                    "Phone no: 7304173041, Email: khuranasales2015@gmail.com\n" +
                    "GSTIN: 27AAOHM4180P!ZE, State: 27- Maharashtra",PdfPCell.ALIGN_CENTER));
            PdfPCell cell_khurana_sales_label = new PdfPCell(khurana_sales_label);
            cell_khurana_sales_label.setBorder(PdfPCell.NO_BORDER);

            PdfPCell cell_khurana_sales_shop_description = new PdfPCell(khurana_sales_shop_description);
            cell_khurana_sales_shop_description.setBorder(PdfPCell.NO_BORDER);

            PdfPTable main_content_top_table = new PdfPTable(1);
            main_content_top_table.addCell(cell_khurana_sales_label);
            main_content_top_table.addCell(cell_khurana_sales_shop_description);
            main_content_top_table.setSpacingAfter(10);
            PdfPCell main_cell = new PdfPCell(main_content_top_table);
            table_invoice_top.addCell(main_cell);

            Font tableFont = FontFactory.getFont(FontFactory.HELVETICA,10,Font.NORMAL);
            tableFont.setColor(BaseColor.WHITE);

            PdfPTable invoice_description_table = new PdfPTable(2);
            PdfPCell cell_bill_to = new PdfPCell(new Paragraph("Bill To",tableFont));
            cell_bill_to.setBackgroundColor(new BaseColor(121,134,203));
            cell_bill_to.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell_bill_to.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell_bill_to.setBorder(PdfPCell.NO_BORDER);
            cell_bill_to.setPaddingBottom(5);

            PdfPCell cell_invoice_details = new PdfPCell(new Paragraph("Invoice Details",tableFont));
            cell_invoice_details.setBackgroundColor(new BaseColor(121,134,203));
            cell_invoice_details.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell_invoice_details.setBorder(PdfPCell.NO_BORDER);
            cell_invoice_details.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell_invoice_details.setPaddingBottom(5);
            invoice_description_table.addCell(cell_bill_to);
            invoice_description_table.addCell(cell_invoice_details);
            invoice_description_table.setSpacingAfter(10);
            invoice_description_table.setSpacingBefore(10);
            invoice_description_table.setWidthPercentage(100);

            PdfPTable invoice_main_content = new PdfPTable(2);
            invoice_main_content.addCell(getCell(customer_name_text.getText().toString(),PdfPCell.ALIGN_LEFT));
            if(getIntent().hasExtra("invoice_number"))
            {
                invoice_number_from_server = getIntent().getExtras().getString("invoice_number");
            }
            invoice_main_content.addCell(getCell(formattedDate.replace("-", "") + "-" + "CN" + appConfig.getCustomer_id() + "-INV"+invoice_number_from_server,PdfPCell.ALIGN_RIGHT));
            invoice_main_content.addCell(getCell(customer_phone_text.getText().toString(),PdfPCell.ALIGN_LEFT));
            invoice_main_content.addCell(getCell(invoice_date.getText().toString(),PdfPCell.ALIGN_RIGHT));
            invoice_main_content.addCell(getCell(customer_email_text.getText().toString(),PdfPCell.ALIGN_LEFT));
            invoice_main_content.addCell(getCell(promoter_name.getText().toString(),PdfPCell.ALIGN_RIGHT));
            invoice_main_content.addCell(getCell(customer_address_text.getText().toString(),PdfPCell.ALIGN_LEFT));
            invoice_main_content.addCell(getCell(promoter_email.getText().toString(),PdfPCell.ALIGN_RIGHT));
            invoice_main_content.addCell(getCell(customer_gst_text.getText().toString(),PdfPCell.ALIGN_LEFT));
            invoice_main_content.setWidthPercentage(100);
            invoice_main_content.setSpacingBefore(5);
            invoice_main_content.setSpacingAfter(10);

            PdfPTable product_info_table = new PdfPTable(5);
            product_info_table.setWidths(new int[]{2,3,1,1,1});
            product_info_table.setSpacingBefore(10);
            product_info_table.setWidthPercentage(100);
            product_info_table.setSpacingAfter(10);
            PdfPCell cell_batch_no = get_cell_for_bar("Batch No.");
            PdfPCell cell_product_name = get_cell_for_bar("Product Name");
            PdfPCell cell_quantity = get_cell_for_bar("Quantity");
            PdfPCell cell_rate = get_cell_for_bar("Rate");
            PdfPCell cell_hsn = get_cell_for_bar("HSN No.");
            product_info_table.addCell(cell_batch_no);
            product_info_table.addCell(cell_product_name);
            product_info_table.addCell(cell_quantity);
            product_info_table.addCell(cell_rate);
            product_info_table.addCell(cell_hsn);
            product_info_table.setHeaderRows(1);

            for(Product p : products)
            {
                String batch = new String("");
                StringBuilder builder = new StringBuilder();
                for (int k = 0; k < p.selected_batch_numbers.size(); k++) {
                    builder.append(p.selected_batch_numbers.get(k).getNumber() + "\n");
                }
                if(!(builder.toString().length() > 5))
                {
                    batch = "N/A";
                }
                else
                {
                    batch = builder.toString();
                }
                product_info_table.addCell(getCellPadded(batch,PdfPCell.ALIGN_LEFT));
                product_info_table.addCell(getCellPadded(p.get_Name(),PdfPCell.ALIGN_LEFT));
                product_info_table.addCell(getCellPadded(""+p.get_Stock(),PdfPCell.ALIGN_CENTER));
                product_info_table.addCell(getCellPadded(""+p.getPrice_mop(),PdfPCell.ALIGN_CENTER));
                product_info_table.addCell(getCellPadded(p.getProduct_HSN(),PdfPCell.ALIGN_CENTER));
            }

            PdfPTable tax_info_table = new PdfPTable(5);
            tax_info_table.setWidthPercentage(100);
            tax_info_table.setWidths(new int[]{1,1,1,1,2});
            tax_info_table.setSpacingBefore(10);
            tax_info_table.setSpacingAfter(10);

            tax_info_table.addCell(get_cell_for_bar("Tax"));
            tax_info_table.addCell(get_cell_for_bar("Taxable Amount"));
            tax_info_table.addCell(get_cell_for_bar("Tax % "));
            tax_info_table.addCell(get_cell_for_bar("Total Tax"));
            tax_info_table.addCell(get_cell_for_bar("Total Amount"));

            tax_info_table.addCell(getCellPadded("CGST",PdfPCell.ALIGN_CENTER));
            tax_info_table.addCell(getCellPadded(total_taxable_cgst.getText().toString(),PdfPCell.ALIGN_CENTER));
            tax_info_table.addCell(getCellPadded(taxable_cgst_percentage.getText().toString(),PdfPCell.ALIGN_CENTER));
            tax_info_table.addCell(getCellPadded(tax_cgst.getText().toString(),PdfPCell.ALIGN_CENTER));
            tax_info_table.addCell(getCellPadded(sub_total_amount_text.getText().toString(),PdfPCell.ALIGN_CENTER));

            tax_info_table.addCell(getCellPadded("SGST",PdfPCell.ALIGN_CENTER));
            tax_info_table.addCell(getCellPadded(total_taxable_sgst.getText().toString(),PdfPCell.ALIGN_CENTER));
            tax_info_table.addCell(getCellPadded(taxable_sgst_percentage.getText().toString(),PdfPCell.ALIGN_CENTER));
            tax_info_table.addCell(getCellPadded(tax_sgst.getText().toString(),PdfPCell.ALIGN_CENTER));
            tax_info_table.addCell(getCellPadded(total_amount_text.getText().toString(),PdfPCell.ALIGN_CENTER));
            tax_info_table.setHeaderRows(1);

            PdfPTable table_payment_details = new PdfPTable(2);
            table_payment_details.addCell(get_cell_for_bar("Payment Tye"));
            table_payment_details.addCell(get_cell_for_bar("Payment Amount"));
            table_payment_details.setWidthPercentage(100);
            table_payment_details.setSpacingBefore(10);
            table_payment_details.setSpacingAfter(10);
            table_payment_details.setWidths(new int[]{1,1});
            if (cash_amount != 0) {
                table_payment_details.addCell(getCellPadded("Cash Payment",PdfPCell.ALIGN_CENTER));
                table_payment_details.addCell(getCellPadded(""+cash_amount,PdfPCell.ALIGN_CENTER));
            }
            if (cheque_amount != 0) {
                table_payment_details.addCell(getCellPadded("Cheque Payment",PdfPCell.ALIGN_CENTER));
                table_payment_details.addCell(getCellPadded(""+cheque_amount,PdfPCell.ALIGN_CENTER));
            }
            if (finance_amount != 0) {
                table_payment_details.addCell(getCellPadded(getIntent().getStringExtra("Financer")+" Payment",PdfPCell.ALIGN_CENTER));
                table_payment_details.addCell(getCellPadded(""+finance_amount,PdfPCell.ALIGN_CENTER));
            }
            if (card_amount != 0) {
                table_payment_details.addCell(getCellPadded("Card Payment",PdfPCell.ALIGN_CENTER));
                table_payment_details.addCell(getCellPadded(""+card_amount,PdfPCell.ALIGN_CENTER));
            }
            if (paytm_amount != 0) {
                table_payment_details.addCell(getCellPadded("Paytm Payment",PdfPCell.ALIGN_CENTER));
                table_payment_details.addCell(getCellPadded(""+paytm_amount,PdfPCell.ALIGN_CENTER));
            }
            if (credit_amount != 0) {
                table_payment_details.addCell(getCellPadded("Credit Payment",PdfPCell.ALIGN_CENTER));
                table_payment_details.addCell(getCellPadded(""+credit_amount,PdfPCell.ALIGN_CENTER));
            }
            table_payment_details.setHeaderRows(1);


            PdfPTable invoice_amount_in_words_table = new PdfPTable(2);
            invoice_amount_in_words_table.addCell(get_cell_for_bar("Invoice Amount In Words"));
            invoice_amount_in_words_table.setWidthPercentage(100);
            invoice_amount_in_words_table.setSpacingBefore(10);
            invoice_amount_in_words_table.addCell(getCellPadded("",PdfPCell.ALIGN_CENTER));
            invoice_amount_in_words_table.addCell(getCellPadded(amount_in_words.getText().toString(),PdfPCell.ALIGN_LEFT));
            invoice_amount_in_words_table.addCell(getCellPadded("",PdfPCell.ALIGN_CENTER));
            invoice_amount_in_words_table.setSpacingAfter(10);

            PdfPTable terms_and_conditions = new PdfPTable(2);
            terms_and_conditions.addCell(get_cell_for_bar("Terms And Condition"));
            terms_and_conditions.setWidthPercentage(100);
            terms_and_conditions.setSpacingBefore(10);
            terms_and_conditions.addCell(getCellPadded("",PdfPCell.ALIGN_CENTER));
            terms_and_conditions.addCell(getCellPadded("In case of any issue please visit the nearest authorised service center Tax Invoice will be sent to registered email id",PdfPCell.ALIGN_LEFT));
            terms_and_conditions.addCell(getCellPadded("",PdfPCell.ALIGN_CENTER));
            terms_and_conditions.setSpacingBefore(10);
            terms_and_conditions.setSpacingAfter(10);

            PdfPTable bank_details_khurana_sales_table = new PdfPTable(2);
            bank_details_khurana_sales_table.addCell(get_cell_for_bar("Bank Detail Khurana Sales"));
            bank_details_khurana_sales_table.setWidthPercentage(100);
            bank_details_khurana_sales_table.addCell(getCellPadded("",PdfPCell.ALIGN_CENTER));
            bank_details_khurana_sales_table.addCell(getCellPadded(" IndusInd Bank ",PdfPCell.ALIGN_LEFT));
            bank_details_khurana_sales_table.addCell(getCellPadded("",PdfPCell.ALIGN_CENTER));
            bank_details_khurana_sales_table.addCell(getCellPadded(" Account No: 259913131313 ",PdfPCell.ALIGN_LEFT));
            bank_details_khurana_sales_table.addCell(getCellPadded("",PdfPCell.ALIGN_CENTER));
            bank_details_khurana_sales_table.addCell(getCellPadded(" IFSC Code: INDB0000002 ",PdfPCell.ALIGN_LEFT));
            bank_details_khurana_sales_table.addCell(getCellPadded("",PdfPCell.ALIGN_CENTER));
            bank_details_khurana_sales_table.setSpacingBefore(10);
            bank_details_khurana_sales_table.setSpacingAfter(10);

            PdfPTable table_declaration = new PdfPTable(1);
            table_declaration.setWidthPercentage(100);
            table_declaration.setWidths(new int[]{1});
            table_declaration.setSpacingAfter(10);
            table_declaration.setSpacingBefore(10);
            table_declaration.addCell(getCellPadded("Declaration: ",PdfPCell.ALIGN_LEFT));
            table_declaration.addCell(getCellPadded("             We hereby certify that our registration certificate under the Goods and Service Tax Act 2017 is in force on the date on which the sales of the goods specified in tax invoice is made by us and that it shall beaccounteed for in the turnover of the sales while filing of return and due tax , if any, payable on the sales has been paid or shall be paid.",PdfPCell.ALIGN_LEFT));

            PdfPTable table_signature = new PdfPTable(2);
            table_signature.setWidthPercentage(100);
            table_signature.setSpacingBefore(10);
            table_signature.setSpacingAfter(10);
            table_signature.addCell(getCellPaddedSignature("   Customer's Seal And Signature  ",PdfPCell.ALIGN_LEFT));
            table_signature.addCell(getCellPaddedSignature("   For Khurana Sales   ",PdfPCell.ALIGN_RIGHT));
            table_signature.addCell(getCellPaddedSignature(" ",PdfPCell.ALIGN_LEFT));
            table_signature.addCell(getCellPaddedSignature("   Authorized Signature   ",PdfPCell.ALIGN_RIGHT));

            PdfPTable table_singature_container = new PdfPTable(1);
            table_singature_container.setWidthPercentage(100);
            PdfPCell cell_signature_content = new PdfPCell(table_signature);
            table_singature_container.addCell(cell_signature_content);
            table_singature_container.setSpacingBefore(20);
            table_singature_container.setSpacingAfter(10);

            document.add(table);
            document.add(table_invoice_top);
            document.add(invoice_description_table);
            document.add(invoice_main_content);
            document.add(product_info_table);
            document.add(tax_info_table);
            document.add(table_payment_details);
            document.add(invoice_amount_in_words_table);
            document.add(terms_and_conditions);
            document.add(bank_details_khurana_sales_table);
            document.add(table_declaration);
            document.add(table_singature_container);

            document.close();
            Toast.makeText(getApplicationContext(),"Invoice has been created",Toast.LENGTH_LONG).show();
            if(share_invoice)
            {
                share_invoice = false;
                Uri uri = Uri.fromFile(filePath);
                Intent sharing = new Intent();
                sharing.setAction(Intent.ACTION_SEND);
                sharing.setType("application/pdf");
                sharing.putExtra(Intent.EXTRA_STREAM, uri);
                this.startActivity(sharing);
            }
            else if(save_invoice)
            {
                Toast.makeText(getApplicationContext(),"Saved Your Invoice",Toast.LENGTH_SHORT).show();
                save_invoice = false;
            }
            else if(print_invoice)
            {
                print_invoice = false;
                print_invoice(targetPdf);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Sorry error occured while creating export pleasr retry !! ",Toast.LENGTH_LONG).show();
        }
    }

    public void print_invoice(String file_path)
    {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        String jobName = this.getString(R.string.app_name) + " Document";

        PrintDocumentAdapter pda = new PrintDocumentAdapter(){

            @Override
            public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback){
                InputStream input = null;
                OutputStream output = null;

                try {

                    input = new FileInputStream(file_path);
                    output = new FileOutputStream(destination.getFileDescriptor());

                    byte[] buf = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = input.read(buf)) > 0) {
                        output.write(buf, 0, bytesRead);
                    }

                    callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});

                } catch (FileNotFoundException ee){
                    //Catch exception
                } catch (Exception e) {
                    //Catch exception
                } finally {
                    try {
                        input.close();
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback callback, Bundle extras){

                if (cancellationSignal.isCanceled()) {
                    callback.onLayoutCancelled();
                    return;
                }


                PrintDocumentInfo pdi = new PrintDocumentInfo.Builder("Name of file").setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();

                callback.onLayoutFinished(pdi, true);
            }
        };

        printManager.print(jobName, pda, null);
    }
    public PdfPCell getCell(String text, int alignment) {
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Paragraph(text,paragraphFont));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public PdfPCell getCellPadded(String text, int alignment) {
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Paragraph(text,paragraphFont));
        cell.setPaddingTop(4);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public PdfPCell getCellPaddedSignature(String text, int alignment) {
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Paragraph(text,paragraphFont));
        cell.setPaddingTop(10);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }
    public PdfPCell get_cell_for_bar(String text)
    {
        Font tableFont = FontFactory.getFont(FontFactory.HELVETICA,10,Font.NORMAL);
        tableFont.setColor(BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Paragraph(text,tableFont));
        cell.setBackgroundColor(new BaseColor(121,134,203));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setPaddingBottom(5);
        return  cell;
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
        if(getIntent().getStringExtra("class_name").equals("buy_final_activity"))
        {
            Intent intent = new Intent(this, CategorizeDataActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            finish();
        }

    }
}
