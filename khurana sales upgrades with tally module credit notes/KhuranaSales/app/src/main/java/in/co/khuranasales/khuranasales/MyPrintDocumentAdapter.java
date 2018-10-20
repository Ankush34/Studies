package in.co.khuranasales.khuranasales;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itextpdf.text.pdf.parser.Line;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by amura on 18/4/18.
 */

public class MyPrintDocumentAdapter extends PrintDocumentAdapter
{
    Context context;
    private int pageHeight;
    private int pageWidth;
    private View view;
    private ArrayList<Product> products;
    public PdfDocument myPdfDocument;
    public int totalpages = 1;

    public MyPrintDocumentAdapter(View view, Context context, ArrayList<Product> products)
    {
        this.view = view;
        this.context = context;
        this.products = products;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes,
                         PrintAttributes newAttributes,
                         CancellationSignal cancellationSignal,
                         LayoutResultCallback callback,
                         Bundle metadata) {
        myPdfDocument = new PrintedPdfDocument(context, newAttributes);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = Resources.getSystem().getDisplayMetrics();
        pageWidth = displayMetrics.widthPixels+(int)((displayMetrics.widthPixels * 650) / 293);
        pageHeight = view.getMeasuredHeight() + 500;

//            pageHeight = newAttributes.getMediaSize().getHeightMils()/1000 * 72;
//            pageWidth = newAttributes.getMediaSize().getWidthMils()/1000 * 76;
        if (cancellationSignal.isCanceled() ) {
            callback.onLayoutCancelled();
            return;
        }

        if (totalpages > 0) {
            PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                    .Builder("print_output.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(totalpages);

            PrintDocumentInfo info = builder.build();
            callback.onLayoutFinished(info, true);
        } else {
            callback.onLayoutFailed("Page count is zero.");
        }
    }


    @Override
    public void onWrite(final PageRange[] pageRanges,
                        final ParcelFileDescriptor destination,
                        final CancellationSignal cancellationSignal,
                        final PrintDocumentAdapter.WriteResultCallback callback) {
        for (int i = 0; i < totalpages; i++) {
            if (pageInRange(pageRanges, i))
            {

                PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i).create();
                PdfDocument.Page page = myPdfDocument.startPage(newPage);
                int measureWidth = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getWidth(), View.MeasureSpec.EXACTLY);
                int measuredHeight = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getHeight(), View.MeasureSpec.EXACTLY);

                ImageView logo = (ImageView)view.findViewById(R.id.khurana_sales_logo);
                logo.getLayoutParams().width = (int)Utils.dpToPx(55,context);
                logo.getLayoutParams().height = (int)Utils.dpToPx(105,context);

                TextView address = view.findViewById(R.id.khurana_sales_address);
                address.setTextSize(25);

                TextView email_label = view.findViewById(R.id.bill_by_label);
                email_label.setTextSize(25);
                email_label.setPadding(0,20,0,0);

                TextView email = view.findViewById(R.id.bill_by_email);
                email.setTextSize(25);
                email.setPadding(0,20,0,0);

                TextView invoice_label = view.findViewById(R.id.invoice_label);
                invoice_label.setTextSize(25);

                TextView logo_label = view.findViewById(R.id.logo_label);
                logo_label.setTextSize(30);
                logo_label.setPadding(0,0,0,20);

                TextView customer_name = view.findViewById(R.id.customer_name);
                customer_name.setTextSize(25);

                TextView customer_contact = view.findViewById(R.id.customer_contact);
                customer_contact.setTextSize(25);

                TextView customer_email = view.findViewById(R.id.customer_email);
                customer_email.setTextSize(25);

                TextView customer_address = view.findViewById(R.id.customer_address);
                customer_address.setTextSize(25);

                TextView customer_gst = view.findViewById(R.id.customer_gst);
                customer_gst.setTextSize(25);

                TextView invoice_number = view.findViewById(R.id.invoice_number);
                invoice_number.setTextSize(25);

                TextView invoice_date = view.findViewById(R.id.invoice_date);
                invoice_date.setTextSize(25);

                TextView promoter_name = view.findViewById(R.id.promoter_name);
                promoter_name.setTextSize(25);

                TextView promoter_email = view.findViewById(R.id.promoter_email);
                promoter_email.setTextSize(25);


                TextView tax_cgst_label = view.findViewById(R.id.cgst_label);
                tax_cgst_label.setTextSize(25);

                TextView taxable_amount_cgst = view.findViewById(R.id.taxable_amount_cgst);
                taxable_amount_cgst.setTextSize(25);

                TextView taxable_percentage_cgst = view.findViewById(R.id.taxable_cgst_percentage);
                taxable_percentage_cgst.setTextSize(25);

                TextView tax_cgst = view.findViewById(R.id.tax_cgst);
                tax_cgst.setTextSize(25);

                TextView tax_sgst_label = view.findViewById(R.id.sgst_label);
                tax_sgst_label.setTextSize(25);

                TextView taxable_amount_sgst = view.findViewById(R.id.taxable_amount_sgst);
                taxable_amount_sgst.setTextSize(25);

                TextView taxable_percentage_sgst = view.findViewById(R.id.taxable_sgst_percentage);
                taxable_percentage_sgst.setTextSize(25);

                TextView tax_sgst = view.findViewById(R.id.tax_sgst);
                tax_sgst.setTextSize(25);

                TextView sub_total = view.findViewById(R.id.sub_total_tax);
                sub_total.setTextSize(25);

                TextView total = view.findViewById(R.id.total_amount_after_tax);
                total.setTextSize(25);

                LinearLayout layout_payment_view = (LinearLayout) view.findViewById(R.id.payment_info_contents);
                for(int j  = 0 ; j < layout_payment_view.getChildCount();j++)
                {
                    TextView payment_type = layout_payment_view.getChildAt(j).findViewById(R.id.payment_type);
                    payment_type.setTextSize(25);

                    TextView payment_amount = layout_payment_view.getChildAt(j).findViewById(R.id.payment_amount);
                    payment_amount.setTextSize(25);

                }

                TextView amount_in_words = view.findViewById(R.id.amount_in_words);
                amount_in_words.setTextSize(25);

                TextView terms = view.findViewById(R.id.terms);
                terms.setTextSize(25);

                TextView bank_name = view.findViewById(R.id.bank_name);
                bank_name.setTextSize(25);

                TextView account_no = view.findViewById(R.id.account_no);
                account_no.setTextSize(25);

                TextView ifsc = view.findViewById(R.id.ifsc);
                ifsc.setTextSize(25);

                TextView declaration = view.findViewById(R.id.declaration);
                declaration.setTextSize(20);

                view.measure(measureWidth, measuredHeight);
                view.layout(0, 0, pageWidth, pageHeight);
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View content = inflater.inflate(view, null);
                view.draw(page.getCanvas());
                if (cancellationSignal.isCanceled()) {
                    callback.onWriteCancelled();
                    myPdfDocument.close();
                    myPdfDocument = null;
                    return;
                }
                myPdfDocument.finishPage(page);
            }
        }

        try {
            myPdfDocument.writeTo(new FileOutputStream(
                    destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            myPdfDocument.close();
            myPdfDocument = null;
        }

        callback.onWriteFinished(pageRanges);
    }

    private boolean pageInRange(PageRange[] pageRanges, int page)
    {
        for (int i = 0; i<pageRanges.length; i++)
        {
            if ((page >= pageRanges[i].getStart()) &&
                    (page <= pageRanges[i].getEnd()))
                return true;
        }
        return false;
    }
    private void drawPage(PdfDocument.Page page,
                          int pagenumber) {
        Canvas canvas = page.getCanvas();

        pagenumber++; // Make sure page numbers start at 1

        int titleBaseLine = 72;
        int leftMargin = 54;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        canvas.drawText(
                "Test Print Document Page " + pagenumber,
                leftMargin,
                titleBaseLine,
                paint);

        paint.setTextSize(14);
        canvas.drawText("This is some test content to verify that custom document printing works", leftMargin, titleBaseLine + 35, paint);

        if (pagenumber % 2 == 0)
            paint.setColor(Color.RED);
        else
            paint.setColor(Color.GREEN);

        PdfDocument.PageInfo pageInfo = page.getInfo();


        canvas.drawCircle(pageInfo.getPageWidth()/2,
                pageInfo.getPageHeight()/2,
                150,
                paint);
    }
}
