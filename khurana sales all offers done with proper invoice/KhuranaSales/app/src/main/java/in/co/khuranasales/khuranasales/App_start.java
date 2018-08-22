package in.co.khuranasales.khuranasales;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.co.khuranasales.khuranasales.notification.NotificationDatabase;

/**
 * Created by Ankush khurana on 6/17/2017.
 */

public class App_start extends AppCompatActivity {
    public static ImageView image;
    public AppConfig appConfig;
    public static ProgressBar progress;
    Animation animZoomIn;
    public  NotificationDatabase database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new NotificationDatabase(getApplicationContext());
        appConfig = new AppConfig(getApplicationContext());
        setContentView(R.layout.app_start);
        image = (ImageView)findViewById(R.id.image);
        progress=(ProgressBar)findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        // load the animation
        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);
        image.startAnimation(animZoomIn);
        animZoomIn.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation) { switchActivity(); }
            @Override public void onAnimationRepeat(Animation animation) { }}); }



   public void switchActivity()
   {
           if(appConfig.isLogin())
           {
               progress.setVisibility(View.INVISIBLE);
               Intent intent = new Intent(getApplicationContext(),CategorizeDataActivity.class);
               startActivity(intent);
               finish();
           }
           else
           {
               progress.setVisibility(View.INVISIBLE);
               Intent intent=new Intent(getApplicationContext(),Activity_Login.class);
               startActivity(intent);
               finish();
           }

   }
    @Override
    protected void onPause() {
       super.onPause();
       finish();
    }
}
