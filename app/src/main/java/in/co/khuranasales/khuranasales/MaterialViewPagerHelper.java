package in.co.khuranasales.khuranasales;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import java.util.concurrent.ConcurrentHashMap;

public class MaterialViewPagerHelper {

    private static ConcurrentHashMap<Object, MaterialViewPagerAnimator> hashMap = new ConcurrentHashMap<>();

    public static void register(Context context, MaterialViewPagerAnimator animator) {
        hashMap.put(context, animator);
    }

    public static void unregister(Context context) {
        if (context != null)
            hashMap.remove(context);
    }

    public static void registerRecyclerView(Activity activity, RecyclerView recyclerView, RecyclerView.OnScrollListener onScrollListener) {
        if (activity != null && hashMap.containsKey(activity)) {
            MaterialViewPagerAnimator animator = hashMap.get(activity);
            if (animator != null) {
                animator.registerRecyclerView(recyclerView, onScrollListener);
            }
        }
    }
    public static MaterialViewPagerAnimator getAnimator(Context context) {
        return hashMap.get(context);
    }
}
