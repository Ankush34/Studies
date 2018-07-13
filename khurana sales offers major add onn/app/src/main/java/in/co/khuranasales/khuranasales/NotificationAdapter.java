package in.co.khuranasales.khuranasales;
import android.animation.Animator;
import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import java.util.ArrayList;
import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    public FadeInAnimator inAnimator;
    private Activity activity;
    private List<User> notificationUsers;
    public ArrayList<Integer> colors = new ArrayList<>();
    public NotificationAdapter(Activity activity, List<User> notificationUsers) {
        this.activity = activity;
        this.notificationUsers = notificationUsers;
        colors.add(Color.parseColor("#1976D2"));
        colors.add(Color.parseColor("#303F9F"));
        colors.add(Color.parseColor("#00B8D4"));
        colors.add(Color.parseColor("#F4511E"));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_layout_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        User m = notificationUsers.get(position);
        holder.text1.setText(m.getName());
        holder.text2.setText(m.getShop_name());
        holder.text3.setText(m.getContact_number());
        holder.text4.setText("#"+m.getCustomer_id());
        holder.side_color_bar.setBackgroundColor(colors.get(position%colors.size()));
        inAnimator = new FadeInAnimator();
        inAnimator.setTarget(NotificationUserList.notification_write_layout);
        inAnimator.prepare(NotificationUserList.notification_write_layout);
        inAnimator.setDuration(400);
        inAnimator.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {NotificationUserList.notification_write_layout.setVisibility(View.VISIBLE);}
            @Override public void onAnimationEnd(Animator animator) {}
            @Override public void onAnimationCancel(Animator animator) {}
            @Override public void onAnimationRepeat(Animator animator) { }});
        holder.notify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!NotificationUserList.reg_ids.contains(notificationUsers.get(position).getFirebaseld()))
                {
                    NotificationUserList.reg_ids.add(notificationUsers.get(position).getFirebaseld());
                }
                inAnimator.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationUsers.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView text1;
        public TextView text2;
        public TextView text3;
        public TextView text4;
        public Button notify_button;
        public RelativeLayout side_color_bar;
        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();

                }
            });
            text1=(TextView)itemView.findViewById(R.id.name);
            text2=(TextView)itemView.findViewById(R.id.shop_name);
            text3=(TextView)itemView.findViewById(R.id.contact_number);
            text4=(TextView)itemView.findViewById(R.id.customer_id);
            side_color_bar = (RelativeLayout)itemView.findViewById(R.id.side_color_bar);
            notify_button = (Button)itemView.findViewById(R.id.notify_button);
        }

    }
}
