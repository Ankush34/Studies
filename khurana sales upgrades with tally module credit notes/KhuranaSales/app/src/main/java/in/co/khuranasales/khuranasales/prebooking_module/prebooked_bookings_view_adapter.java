package in.co.khuranasales.khuranasales.prebooking_module;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class prebooked_bookings_view_adapter extends  RecyclerView.Adapter<prebooked_bookings_view_adapter.ViewHolder>{

    public ArrayList<PrebookingLedger> prebooking_ledgers ;
    public Activity mActivity;
    public recyclerViewItemClickListener listner_cancel_booking;
    public recyclerViewItemClickListener send_arrival_sms_listener;

    public prebooked_bookings_view_adapter(Activity activity, ArrayList<PrebookingLedger> prebooking_ledgers, recyclerViewItemClickListener cancel_booking_listener, recyclerViewItemClickListener arrival_sms_listener) {
        this.mActivity  = activity;
        this.listner_cancel_booking = cancel_booking_listener;
        this.prebooking_ledgers = prebooking_ledgers;
        this.send_arrival_sms_listener = arrival_sms_listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prebooked_customers_recycler_card, parent, false);
        prebooked_bookings_view_adapter.ViewHolder viewHolder = new prebooked_bookings_view_adapter.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PrebookingLedger ledger = prebooking_ledgers.get(position);
        holder.customer_name.setText(ledger.getName());
        holder.customer_phone.setText(ledger.getPhone());
        holder.customer_address.setText(ledger.getAddress());
        holder.customer_prebooking_id.setText(ledger.getPrebooking_id());
        holder.customer_prebooking_amount.setText(ledger.getPrebooking_amount() + " /-");
        holder.customer_prebooking_status.setText(ledger.getBooking_status().toLowerCase());
        if(ledger.getBooking_status().equals("CANCELLED"))
        {
            holder.cancel_booking.setText("Re-Confirm Booking");
        }
        else if(ledger.getBooking_status().equals("BOOKED"))
        {
            holder.cancel_booking.setText("Cancel Confirmed Booking");
        }
    }

    @Override
    public int getItemCount() {
        return prebooking_ledgers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
      public TextView customer_name;
      public TextView customer_phone;
      public TextView customer_address;
      public TextView customer_prebooking_id;
      public TextView customer_prebooking_amount;
      public TextView cancel_booking;
      public TextView customer_prebooking_status;
      public ImageView send_arrival_sms;

        public ViewHolder(View itemView) {
            super(itemView);
            customer_name = (TextView)itemView.findViewById(R.id.customer_name);
            customer_phone = (TextView)itemView.findViewById(R.id.customer_phone);
            customer_address = (TextView)itemView.findViewById(R.id.customer_address);
            customer_prebooking_amount = (TextView)itemView.findViewById(R.id.customer_prebooking_amount);
            customer_prebooking_id = (TextView)itemView.findViewById(R.id.customer_prebooking_id);
            cancel_booking = (TextView)itemView.findViewById(R.id.cancel_confirm_booking);
            cancel_booking.setOnClickListener(this);
            customer_prebooking_status = (TextView)itemView.findViewById(R.id.customer_prebooking_status);
            send_arrival_sms = (ImageView)itemView.findViewById(R.id.send_arrival_sms);
            send_arrival_sms.setOnClickListener(this);

        }

        @Override
        public void onClick(View v)
        {
            if(v.getId() == R.id.send_arrival_sms)
            {
                send_arrival_sms_listener.onItemClick(v, getAdapterPosition());
            }
            else
            {
                listner_cancel_booking.onItemClick(v, getAdapterPosition());
            }
         }
    }
}
