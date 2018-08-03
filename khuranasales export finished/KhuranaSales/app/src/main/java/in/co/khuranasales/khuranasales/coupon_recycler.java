package in.co.khuranasales.khuranasales;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ankush Khurana on 28/07/2017.
 */

public class coupon_recycler extends RecyclerView.Adapter<coupon_recycler.ViewHolder> {
    private String[] dataSource;
    public int count_global=0;
    public int amount = 0;
    private Activity activity;
    private List<Coupon> productItems;
    public coupon_recycler(Activity activity, List<Coupon> coupons) {
        this.activity = activity;
        this.productItems = coupons;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coupon_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Coupon c = productItems.get(position);
        holder.card_price.setText("Rs "+c.getCoupon_amount()+" /-");
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Coupon item = productItems.get(position);
                item.setCount(item.getCount()+1);
                holder.count.setText(""+productItems.get(position).getCount());
                amount = amount + item.getCoupon_amount();
                Buy_Now_Activity.total_discount_view.setText(""+amount);

            }
        });
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Coupon item = productItems.get(position);
                item.setCount(item.getCount()-1);
                holder.count.setText(""+productItems.get(position).getCount());
                amount = amount - item.getCoupon_amount();
                Buy_Now_Activity.total_discount_view.setText(""+amount);
            }
        });
        Buy_Now_Activity.total_discount_view.setText(""+amount);
    }
    @Override
    public int getItemCount() {
        return productItems.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public Button add;
        public Button sub;
        public TextView count;
        public TextView card_price;
        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Coupon m=productItems.get(position);
                }
            });
            add = (Button)itemView.findViewById(R.id.add);
            sub = (Button)itemView.findViewById(R.id.sub);
            count = (TextView)itemView.findViewById(R.id.coupon_count);
            card_price = (TextView)itemView.findViewById(R.id.card_price);
        }

    }
}