package in.co.khuranasales.khuranasales;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankush Khurana on 28/07/2017.
 */

public class CategorizeAdapter extends RecyclerView.Adapter<CategorizeAdapter.ViewHolder> {
    private String[] dataSource;
    public int count_global=0;
    private Activity activity;
    private List<SubCategory> sub_categories;
    private ArrayList<int[]> colors = new ArrayList<>();
    public CategorizeAdapter(String[] dataArgs){
        dataSource = dataArgs;
    }
    public CategorizeAdapter(Activity activity, List<SubCategory> sub_categories) {
        this.activity = activity;
        this.sub_categories = sub_categories;
        colors.add(new int[]{Color.parseColor("#1976D2"),Color.parseColor("#2196F3")});
        colors.add(new int[]{Color.parseColor("#303F9F"),Color.parseColor("#7B1FA2")});
        colors.add(new int[]{Color.parseColor("#00B8D4"),Color.parseColor("#00E5FF")});
        colors.add(new int[]{Color.parseColor("#F4511E"),Color.parseColor("#EF6C00")});

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categorize_layout_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SubCategory  category = sub_categories.get(position);
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, colors.get((position%4)));
        gd.setCornerRadius(0f);
        //apply the button background to newly created drawable gradient
        holder.relativeLayout.setBackground(gd);
        holder.title.setText(category.getName());
        holder.description.setText(category.getDescription());
        holder.view1.setBackgroundResource(category.getResource());
        holder.categorization_text.setText(category.getCategorizing_text());
    }
    @Override
    public int getItemCount() {
        return sub_categories.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView view1;
        public RelativeLayout relativeLayout;
        public TextView title;
        public TextView description;
        public TextView categorization_text;
        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(sub_categories.get(position).fragment_name.equals("Mobile"))
                    {
                        if(sub_categories.get(position).name.equals("Brands"))
                        {   CategorizeDataActivity.type = "Mobile";
                            Log.d("Sub Category",""+sub_categories.get(position).name);
                            FadeInAnimator fadeInAnimator = new FadeInAnimator();
                            fadeInAnimator.prepare(CategorizeDataActivity.brand_selection);
                            fadeInAnimator.setDuration(400);
                            fadeInAnimator.setTarget(CategorizeDataActivity.brand_selection);
                            fadeInAnimator.addAnimatorListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {CategorizeDataActivity.brand_selection.setVisibility(View.VISIBLE);}
                                @Override
                                public void onAnimationEnd(Animator animator) {}
                                @Override
                                public void onAnimationCancel(Animator animator) {}
                                @Override
                                public void onAnimationRepeat(Animator animator) {}});
                            fadeInAnimator.start();
                        }
                        else
                        {
                            Toast.makeText(itemView.getContext(),"Coming Soon....",Toast.LENGTH_LONG).show();
                        }
                    }
                    if(sub_categories.get(position).fragment_name.equals("Accessories"))
                    {
                        if(sub_categories.get(position).name.equals("Accessories"))
                        {

                            CategorizeDataActivity.type = "Accessories";
                            FadeInAnimator fadeInAnimator = new FadeInAnimator();
                            fadeInAnimator.prepare(CategorizeDataActivity.brand_selection);
                            fadeInAnimator.setDuration(400);
                            fadeInAnimator.setTarget(CategorizeDataActivity.brand_selection);
                            fadeInAnimator.addAnimatorListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {CategorizeDataActivity.brand_selection.setVisibility(View.VISIBLE);}
                                @Override
                                public void onAnimationEnd(Animator animator) {}
                                @Override
                                public void onAnimationCancel(Animator animator) {}
                                @Override
                                public void onAnimationRepeat(Animator animator) {}});
                            fadeInAnimator.start();
                        }
                        else
                        {
                            Toast.makeText(itemView.getContext(),"Coming Soon....",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relative_layout_card_categorize);
            view1 = (ImageView)itemView.findViewById(R.id.image_background_card_categorize);
            title = (TextView)itemView.findViewById(R.id.sub_category_name);
            description = (TextView)itemView.findViewById(R.id.sub_category_desc);
            categorization_text = (TextView)itemView.findViewById(R.id.category_text);
        }
    }
}