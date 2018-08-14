package in.co.khuranasales.khuranasales;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PromotersRecyclerAdapter extends RecyclerView.Adapter<PromotersRecyclerAdapter.ViewHolder> {

    private Context context;
    private static ArrayList<Promoter> promoters;
    private ArrayList<Integer> colors = new ArrayList<>();
    public int size = 0;
    public int extra_size = 0;
   public PromotersRecyclerAdapter(Context context, ArrayList<Promoter> promoters)
   {
       this.context = context;
       this.promoters = promoters;
       colors.add(Color.parseColor("#1976D2"));
       colors.add(Color.parseColor("#303F9F"));
       colors.add(Color.parseColor("#00B8D4"));
       colors.add(Color.parseColor("#F4511E"));
       DisplayMetrics metrics = context.getResources().getDisplayMetrics();
       Toast.makeText(this.context,"density pixels"+metrics.heightPixels,Toast.LENGTH_LONG).show();
       size = (int)(300 * metrics.heightPixels)/1280;
       extra_size = (int)(300* metrics.heightPixels)/1280;

   }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promoter_sold_products_card, parent, false);
        PromotersRecyclerAdapter.ViewHolder viewHolder = new PromotersRecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Promoter promoter = promoters.get(position);
        holder.promoter_id.setText("Promoter Id:       #"+promoter.getId());
        holder.promoter_contact.setText("Promoter Contact:          "+promoter.getContact());
        holder.promoter_name.setText("Promoter Email:       "+promoter.getName());
        Log.d("Promoter Products: ",""+promoter.getProducts().size());
        MyPromoterProductListAdapter adapter1 =new MyPromoterProductListAdapter(holder.itemView.getContext(),promoter.getProducts());
        holder.promoter_products_list.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
        int totalHeight = 0 ;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(holder.promoter_products_list.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < adapter1.getCount(); i++) {
            View mView = adapter1.getView(i, null, holder.promoter_products_list);

            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),

                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
            Log.w("HEIGHT" + i, String.valueOf(totalHeight));
        }
        totalHeight = totalHeight
                + (holder.promoter_products_list.getDividerHeight() * (adapter1.getCount() - 1));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,totalHeight + 300);
        params.setMargins(10,10,10,10);
        holder.cardView.setLayoutParams(params);

        holder.side_bar.setBackgroundColor(colors.get(position%colors.size()));
   }

    @Override
    public int getItemCount() {
        return promoters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView promoter_name;
        public CardView cardView;
        public TextView promoter_id;
        public TextView promoter_contact;
        public ListView promoter_products_list;
        public RelativeLayout side_bar;
        public ViewHolder(View itemView) {
            super(itemView);
            promoter_name = (TextView)itemView.findViewById(R.id.promoter_name);
           promoter_products_list = (ListView)itemView.findViewById(R.id.promoter_products);
            cardView = (CardView)itemView.findViewById(R.id.card_view_sold_items);
            side_bar = (RelativeLayout)itemView.findViewById(R.id.side_bar);
            promoter_id = (TextView)itemView.findViewById(R.id.promoter_id);
            promoter_contact = (TextView)itemView.findViewById(R.id.promoter_contact);

        }
    }

    public static int find_promoter(String name)
    {
        for(int i = 0 ; i < promoters.size();i++)
        {
            if(promoters.get(i).getName().equals(name))
            {
                return i;
            }
            else
            {
                continue;
            }
        }
        return  0;
    }
}
