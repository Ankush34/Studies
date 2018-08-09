package in.co.khuranasales.khuranasales;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static in.co.khuranasales.khuranasales.Utils.canScroll;
import static in.co.khuranasales.khuranasales.Utils.colorWithAlpha;
import static in.co.khuranasales.khuranasales.Utils.dpToPx;
import static in.co.khuranasales.khuranasales.Utils.getTheVisibileView;
import static in.co.khuranasales.khuranasales.Utils.minMax;
import static in.co.khuranasales.khuranasales.Utils.scrollTo;
import static in.co.khuranasales.khuranasales.Utils.setBackgroundColor;
import static in.co.khuranasales.khuranasales.Utils.setElevation;
import static in.co.khuranasales.khuranasales.Utils.setScale;
public class MaterialViewPagerAnimator {

    private static final String TAG = MaterialViewPagerAnimator.class.getSimpleName();

    public static Boolean ENABLE_LOG = true;

    private Context context;
    private MaterialViewPagerHeader mHeader;
    private static final int ENTER_TOOLBAR_ANIMATION_DURATION = 600;
    protected MaterialViewPager materialViewPager;
    public final float elevation;
    public final float scrollMax;
    public final float scrollMaxDp;
    protected float lastYOffset = -1; //the current yOffset
    protected float lastPercent = 0; //the current Percent
    protected MaterialViewPagerSettings settings;
    protected List<View> scrollViewList = new ArrayList<>();
    protected HashMap<Object, Integer> yOffsets = new HashMap<>();
    private float headerYOffset = Float.MAX_VALUE;
    private Object headerAnimator;

    boolean followScrollToolbarIsVisible = false;
    float firstScrollValue = Float.MIN_VALUE;
    boolean justToolbarAnimated = false;
    float initialDistance = -1;

    public MaterialViewPagerAnimator(MaterialViewPager materialViewPager) {

        this.settings = materialViewPager.settings;

        this.materialViewPager = materialViewPager;
        this.mHeader = materialViewPager.materialViewPagerHeader;
        this.context = mHeader.getContext();
        this.scrollMax = this.settings.headerHeight;
        this.scrollMaxDp = Utils.dpToPx(this.scrollMax, context);
        elevation = dpToPx(4, context);
    }

    protected void dispatchScrollOffset(Object source, float yOffset) {
        if (scrollViewList != null) {
            for (Object scroll : scrollViewList) {

                //do not re-scroll the source
                if (scroll != null && scroll != source) {
                    setScrollOffset(scroll, yOffset);
                }
            }
        }
    }

    private void setScrollOffset(Object scroll, float yOffset) {
        //do not re-scroll the source
        if (scroll != null && yOffset >= 0) {

            scrollTo(scroll, yOffset);

            //save the current yOffset of the scrollable on the yOffsets hashmap
            yOffsets.put(scroll, (int) yOffset);
        }
    }

    public boolean onMaterialScrolled(Object source, float yOffset) {

        if(initialDistance == -1 || initialDistance == 0) {
            initialDistance = mHeader.mPagerSlidingTabStrip.getTop() - mHeader.toolbar.getBottom();
        }

        //only if yOffset changed
        if (yOffset == lastYOffset)
            return false;

        float scrollTop = -yOffset;

        {
            //parallax scroll of the Background ImageView (the KenBurnsView)
            if (mHeader.headerBackground != null) {

                if (this.settings.parallaxHeaderFactor != 0)
                    ViewHelper.setTranslationY(mHeader.headerBackground, scrollTop / this.settings.parallaxHeaderFactor);

                if (ViewHelper.getY(mHeader.headerBackground) >= 0)
                    ViewHelper.setY(mHeader.headerBackground, 0);
            }


        }

        if (ENABLE_LOG)
            Log.d("yOffset", "" + yOffset);

        //dispatch the new offset to all registered scrollables
        dispatchScrollOffset(source, minMax(0, yOffset, scrollMaxDp));

        float percent = yOffset / scrollMax;

        if (ENABLE_LOG)
            Log.d("percent1", "" + percent);

        if(percent != 0) {
            //distance between pager & toolbar
            float newDistance = ViewHelper.getY(mHeader.mPagerSlidingTabStrip) - mHeader.toolbar.getBottom();

            percent = 1 - newDistance / initialDistance;

            if (ENABLE_LOG)
                Log.d("percent2", "" + percent);
        }

        if(Float.isNaN(percent)) //fix for orientation change
            return false;

        //fix quick scroll
        if(percent == 0 && headerAnimator != null) {
            cancelHeaderAnimator();
            ViewHelper.setTranslationY(mHeader.toolbarLayout,0);
        }

        percent = minMax(0, percent, 1);
        {

            if (!settings.toolbarTransparent) {
                // change color of toolbar & viewpager indicator &  statusBaground
                setColorPercent(percent);
            } else {
                if (justToolbarAnimated) {
                    if (toolbarJoinsTabs())
                        setColorPercent(1);
                    else if (lastPercent != percent) {
                        animateColorPercent(0, 200);
                    }
                }
            }

            lastPercent = percent; //save the percent

            if (mHeader.mPagerSlidingTabStrip != null) { //move the viewpager indicator
                //float newY = ViewHelper.getY(mHeader.mPagerSlidingTabStrip) + scrollTop;

                if (ENABLE_LOG)
                    Log.d(TAG, "Yoffset: " + scrollTop);

                //mHeader.mPagerSlidingTabStrip.setTranslationY(mHeader.getToolbar().getBottom()-mHeader.mPagerSlidingTabStrip.getY());
                if (scrollTop <= 0) {
                    ViewHelper.setTranslationY(mHeader.mPagerSlidingTabStrip, scrollTop);
                    ViewHelper.setTranslationY(mHeader.toolbarLayoutBackground, scrollTop);
                    //when
                    if (ViewHelper.getY(mHeader.mPagerSlidingTabStrip) < mHeader.getToolbar().getBottom()) {
                        float ty = mHeader.getToolbar().getBottom() - mHeader.mPagerSlidingTabStrip.getTop();
                        ViewHelper.setTranslationY(mHeader.mPagerSlidingTabStrip, ty);
                        ViewHelper.setTranslationY(mHeader.toolbarLayoutBackground, ty);
                    }
                }

            }


            if (mHeader.mLogo != null) { //move the header logo to toolbar

                if (this.settings.hideLogoWithFade) {
                    ViewHelper.setAlpha(mHeader.mLogo, 1 - percent);
                    ViewHelper.setTranslationY(mHeader.mLogo, (mHeader.finalTitleY - mHeader.originalTitleY) * percent);
                } else {
                    ViewHelper.setTranslationY(mHeader.mLogo, (mHeader.finalTitleY - mHeader.originalTitleY) * percent);
                    ViewHelper.setTranslationX(mHeader.mLogo, (mHeader.finalTitleX - mHeader.originalTitleX) * percent);

                    float scale = (1 - percent) * (1 - mHeader.finalScale) + mHeader.finalScale;
                    setScale(scale, mHeader.mLogo);
                }
            }

            if (this.settings.hideToolbarAndTitle && mHeader.toolbarLayout != null) {
                boolean scrollUp = lastYOffset < yOffset;

                if (scrollUp) {
                    scrollUp(yOffset);
                } else {
                    scrollDown(yOffset);
                }
            }
        }

        if (headerAnimator != null && percent < 1) {
            cancelHeaderAnimator();
        }

        lastYOffset = yOffset;

        return true;
    }

    private  void cancelHeaderAnimator(){
        if(headerAnimator != null) {
            if (headerAnimator instanceof ObjectAnimator)
                ((ObjectAnimator) headerAnimator).cancel();
            else if (headerAnimator instanceof android.animation.ObjectAnimator)
                ((android.animation.ObjectAnimator) headerAnimator).cancel();
            headerAnimator = null;
        }
    }

    private void scrollUp(float yOffset) {
        if (ENABLE_LOG)
            Log.d(TAG, "scrollUp");

        followScrollToolbarLayout(yOffset);
    }

    private void scrollDown(float yOffset) {
        if (ENABLE_LOG)
            Log.d(TAG, "scrollDown");
        if (yOffset > mHeader.toolbarLayout.getHeight() * 1.5f) {
            animateEnterToolbarLayout(yOffset);
        } else {
            if (headerAnimator != null) {
                followScrollToolbarIsVisible = true;
            } else {
                headerYOffset = Float.MAX_VALUE;
                followScrollToolbarLayout(yOffset);
            }
        }
    }

    public void setColor(int color, int duration) {
        ValueAnimator colorAnim = ObjectAnimator.ofInt(mHeader.headerBackground, "backgroundColor", settings.color, color);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setDuration(duration);
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final int animatedValue = (Integer) animation.getAnimatedValue();
                int colorAlpha = colorWithAlpha(animatedValue, lastPercent);
                mHeader.headerBackground.setBackgroundColor(colorAlpha);
                mHeader.statusBackground.setBackgroundColor(colorAlpha);
                mHeader.toolbar.setBackgroundColor(colorAlpha);
                mHeader.toolbarLayoutBackground.setBackgroundColor(colorAlpha);
                mHeader.mPagerSlidingTabStrip.setBackgroundColor(colorAlpha);
                //set the new color as MaterialViewPager's color
                settings.color = animatedValue;
            }
        });
        colorAnim.start();
    }

    public void animateColorPercent(float percent, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(lastPercent, percent);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setColorPercent((float) animation.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public void setColorPercent(float percent) {
        // change color of
        // toolbar & viewpager indicator &  statusBaground

        setBackgroundColor(
                colorWithAlpha(this.settings.color, percent),
                mHeader.statusBackground
        );

        if (percent >= 1) {
            setBackgroundColor(
                    colorWithAlpha(this.settings.color, percent),
                    mHeader.toolbar,
                    mHeader.toolbarLayoutBackground,
                    mHeader.mPagerSlidingTabStrip
            );
        } else {
            setBackgroundColor(
                    colorWithAlpha(this.settings.color, 0),
                    mHeader.toolbar,
                    mHeader.toolbarLayoutBackground,
                    mHeader.mPagerSlidingTabStrip
            );
        }

        if (this.settings.enableToolbarElevation && toolbarJoinsTabs())
            setElevation(
                    (percent == 1) ? elevation : 0,
                    mHeader.toolbar,
                    mHeader.toolbarLayoutBackground,
                    mHeader.mPagerSlidingTabStrip,
                    mHeader.mLogo
            );
    }

    private boolean toolbarJoinsTabs() {
        return (mHeader.toolbar.getBottom() == mHeader.mPagerSlidingTabStrip.getTop() + ViewHelper.getTranslationY(mHeader.mPagerSlidingTabStrip));
    }

    private void followScrollToolbarLayout(float yOffset) {
        if (mHeader.toolbar.getBottom() == 0)
            return;

        if (toolbarJoinsTabs()) {
            if (firstScrollValue == Float.MIN_VALUE)
                firstScrollValue = yOffset;

            float translationY = firstScrollValue - yOffset;
            
            if(translationY > 0) {
                translationY = 0;
            }

            if (ENABLE_LOG)
                Log.d(TAG, "translationY " + translationY);

            ViewHelper.setTranslationY(mHeader.toolbarLayout, translationY);
        } else {
            ViewHelper.setTranslationY(mHeader.toolbarLayout, 0);
            justToolbarAnimated = false;
        }

        followScrollToolbarIsVisible = (ViewHelper.getY(mHeader.toolbarLayout) >= 0);
    }

    private void animateEnterToolbarLayout(float yOffset) {
        if (!followScrollToolbarIsVisible && headerAnimator != null) {
            if (headerAnimator instanceof ObjectAnimator)
                ((ObjectAnimator) headerAnimator).cancel();
            else if (headerAnimator instanceof android.animation.ObjectAnimator)
                ((android.animation.ObjectAnimator) headerAnimator).cancel();
            headerAnimator = null;
        }

        if (headerAnimator == null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                headerAnimator = android.animation.ObjectAnimator.ofFloat(mHeader.toolbarLayout, "translationY", 0).setDuration(ENTER_TOOLBAR_ANIMATION_DURATION);
                ((android.animation.ObjectAnimator) headerAnimator).addListener(new android.animation.AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        super.onAnimationEnd(animation);
                        followScrollToolbarIsVisible = true;
                        firstScrollValue = Float.MIN_VALUE;
                        justToolbarAnimated = true;
                    }
                });
                ((android.animation.ObjectAnimator) headerAnimator).start();
            } else {
                headerAnimator = ObjectAnimator.ofFloat(mHeader.toolbarLayout, "translationY", 0).setDuration(ENTER_TOOLBAR_ANIMATION_DURATION);
                ((ObjectAnimator) headerAnimator).addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        followScrollToolbarIsVisible = true;
                        firstScrollValue = Float.MIN_VALUE;
                        justToolbarAnimated = true;
                    }
                });
                ((ObjectAnimator) headerAnimator).start();
            }
            headerYOffset = yOffset;
        }
    }

    public int getHeaderHeight() {
        return this.settings.headerHeight;
    }

    protected boolean isNewYOffset(int yOffset) {
        if (lastYOffset == -1)
            return true;
        else
            return yOffset != lastYOffset;
    }

    public void registerRecyclerView(final RecyclerView recyclerView, final RecyclerView.OnScrollListener onScrollListener) {
        if (recyclerView != null) {
            scrollViewList.add(recyclerView); //add to the scrollable list
            yOffsets.put(recyclerView, recyclerView.getScrollY()); //save the initial recyclerview's yOffset (0) into hashmap
            //only necessary for recyclerview

            //listen to scroll
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                boolean firstZeroPassed;

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (onScrollListener != null)
                        onScrollListener.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if (onScrollListener != null)
                        onScrollListener.onScrolled(recyclerView, dx, dy);

                    int yOffset = yOffsets.get(recyclerView);

                    yOffset += dy;
                    yOffsets.put(recyclerView, yOffset); //save the new offset

                    //first time you get 0, don't share it to others scrolls
                    if (yOffset == 0 && !firstZeroPassed) {
                        firstZeroPassed = true;
                        return;
                    }
                        if (isNewYOffset(yOffset))
                            onMaterialScrolled(recyclerView, yOffset); }

            });

            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    setScrollOffset(recyclerView, lastYOffset);
                }
            });
        }
    }

    public void restoreScroll(final float scroll, final MaterialViewPagerSettings settings) {
        //try to scroll up, on a looper to wait until restored
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!onMaterialScrolled(null, scroll)){
                    restoreScroll(scroll,settings);
                }
            }
        },100);

    }

    public void onViewPagerPageChanged() {
        scrollDown(lastYOffset);

        View visibleView = getTheVisibileView(scrollViewList);
        if (!canScroll(visibleView)) {
            followScrollToolbarLayout(0);
            onMaterialScrolled(visibleView, 0);
        }
    }
}
