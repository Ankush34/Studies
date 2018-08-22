package in.co.khuranasales.khuranasales.notification;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

/**
 * Created by Ankush Khurana on 28/07/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Activity activity;
    public List<Notification> notifications;
    public static  int width = 0 ;
    public static int height = 0;
    public static ArrayList<String> selected_payment_modes = new ArrayList<String>();
    public static ArrayList<String> selected_names = new ArrayList<String>();
    public recyclerViewItemClickListener mListener;
    public static int[] positions = new int[2];
    public NotificationAdapter(Activity activity, List<Notification> notifications, recyclerViewItemClickListener item_click_listener) {
        this.activity = activity;
        this.notifications = notifications;
        mListener = item_click_listener;
    }
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_card, parent, false);
        NotificationAdapter.ViewHolder viewHolder = new NotificationAdapter.ViewHolder(view, mListener);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final NotificationAdapter.ViewHolder holder, final int position) {
         Notification  notification = notifications.get(position);
         holder.notification_date.setText("Notification Date:  "+notification.getDate());
         holder.notification_type.setText("Notification Type:  "+notification.getType());
    }
    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView notification_date;
        public TextView notification_type;

        public ViewHolder(final View itemView, recyclerViewItemClickListener item_click_listener) {
            super(itemView);
            CardView notification_card;
            mListener = item_click_listener;
            notification_card = (CardView)itemView.findViewById(R.id.card_notification);
            notification_card.setOnClickListener(this);
            notification_date = (TextView)itemView.findViewById(R.id.notification_date);
            notification_type = (TextView)itemView.findViewById(R.id.notification_type);
        }

        @Override
        public void onClick(View v) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics( dm );
            v.getLocationOnScreen( positions );
            Log.d("Notification","Clicked Width:"+v.getMeasuredWidth());
            Log.d("Notification","Clicked Height:"+v.getMeasuredHeight());
            width = v.getMeasuredWidth();
            height = v.getMeasuredHeight();
            mListener.onItemClick(v, getAdapterPosition());
        }
    }
}
