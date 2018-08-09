package in.co.khuranasales.khuranasales;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.co.khuranasales.khuranasales.notification.Notification;
import in.co.khuranasales.khuranasales.notification.NotificationDatabase;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
   private String TAG  = "MessagingService";
   private NotificationUtils notificationUtils;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("Message Recieved", "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e("Notification Body", "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e("Data in notification", "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e("Exception Notification", "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(PushNotificationConfig.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");
            JSONObject product_data = payload.getJSONObject("mobile_info");

            String name = product_data.getString("mobile_name");
            String ks_price = product_data.getString("mobile_ks_price");
            String mop_price = product_data.getString("mobile_mop_price");
            String brand = product_data.getString("mobile_brand");
            String quantity_in_stock = product_data.getString("mobile_stock");
            String link = product_data.getString("mobile_image_links");
            String notification_type = product_data.getString("notification_type");
            String product_id = product_data.getString("notification_product_id");

            String message_latest = "Product name:   "+name+"\n";
            message_latest = message_latest + "Product brand:   "+brand+"\n";
            message_latest = message_latest + "Product salable price:   "+mop_price+"\n";
            message_latest = message_latest + "Product max discounted price:   "+ks_price+"\n";
            message_latest = message_latest + "Units in stock:   "+quantity_in_stock+"\n";
            message = message_latest;


            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c);
            Log.d("NotificationActivity",formattedDate);

            Notification notification = new Notification(title,message,notification_type,formattedDate, product_id, "Pending");
            NotificationDatabase notificationDatabase = new NotificationDatabase(getApplicationContext());
            notificationDatabase.addNotification(notification);

            Log.d(TAG, "title: " + title);
            Log.d(TAG, "message: " + message);
            Log.d(TAG, "isBackground: " + isBackground);
            Log.d(TAG, "payload: " + payload.toString());
            Log.d(TAG, "imageUrl: " + imageUrl);
            Log.d(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(PushNotificationConfig.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);


                Intent resultIntent = new Intent(getApplicationContext(), CategorizeDataActivity.class);
                resultIntent.putExtra("message", message);
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }

            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), CategorizeDataActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
