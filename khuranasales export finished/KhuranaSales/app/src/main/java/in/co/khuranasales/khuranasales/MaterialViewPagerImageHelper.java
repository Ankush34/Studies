package in.co.khuranasales.khuranasales;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MaterialViewPagerImageHelper {

    private static MaterialViewPager.OnImageLoadListener imageLoadListener;

    public static void setImageUrl(final ImageView imageView, final String urlImage, final int fadeDuration) {
        final float alpha = ViewHelper.getAlpha(imageView);
        final ImageView viewToAnimate = imageView;
        final ObjectAnimator fadeOut = ObjectAnimator.ofFloat(viewToAnimate, "alpha", 0).setDuration(fadeDuration);
        fadeOut.setInterpolator(new DecelerateInterpolator());
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Picasso.with(imageView.getContext()).load(urlImage)
                        .centerCrop().fit().into(viewToAnimate, new Callback() {
                    @Override
                    public void onSuccess() {
                        final ObjectAnimator fadeIn = ObjectAnimator.ofFloat(viewToAnimate, "alpha", alpha).setDuration(fadeDuration);
                        fadeIn.setInterpolator(new AccelerateInterpolator());
                        fadeIn.start();
                        if(imageLoadListener!=null){
                            imageLoadListener.OnImageLoad(imageView,((BitmapDrawable)imageView.getDrawable()).getBitmap());
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        });
        fadeOut.start();
    }

    public static void setImageDrawable(final ImageView imageView, final Drawable drawable, final int fadeDuration) {
        final float alpha = ViewHelper.getAlpha(imageView);
        final ImageView viewToAnimate = imageView;
        final ObjectAnimator fadeOut = ObjectAnimator.ofFloat(viewToAnimate, "alpha", 0).setDuration(fadeDuration);
        fadeOut.setInterpolator(new DecelerateInterpolator());
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageView.setImageDrawable(drawable);
                final ObjectAnimator fadeIn = ObjectAnimator.ofFloat(viewToAnimate, "alpha", alpha).setDuration(fadeDuration);
                fadeIn.setInterpolator(new AccelerateInterpolator());
                fadeIn.start();
            }
        });
        fadeOut.start();
    }

    public static void setImageLoadListener(MaterialViewPager.OnImageLoadListener imageLoadListener) {
        MaterialViewPagerImageHelper.imageLoadListener = imageLoadListener;
    }
}

