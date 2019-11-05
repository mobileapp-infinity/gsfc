package com.infinity.infoway.gsfc.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.activity.AnnouncementFaculty;
import com.infinity.infoway.gsfc.activity.AnnouncementStudentActiivty;
import com.infinity.infoway.gsfc.app.NotificationUtils;
import com.infinity.infoway.gsfc.app.DataStorage;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingServicee extends FirebaseMessagingService {
    Context ctx;
    SharedPreferences.Editor editor = null;
    public static final String NOTIF_CHANNEL_ID = "my_channel_02";
    // SharedPreferences sharedPreferences= getSharedPreferences("Login_Detail",Context.MODE_PRIVATE);
    NotificationUtils notificationUtils;
    DataStorage storage;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //For Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotifChannel(this);
        }

        // editor = sharedPreferences.edit();
        // editor.putString("seen", "0");
        //  editor.apply();
        DataStorage storage = new DataStorage("Login_Detail", getApplicationContext());
        //Log.d("Msg", "Message received ["+remoteMessage+"]");
        storage.write("seen", "0");

        /* Intent inten2t = new Intent("pushNotification");
        sendBroadcast(inten2t);*/
        // Create Notification

        Intent pushNotification = new Intent(DataStorage.PUSH_NOTIFICATION);
        pushNotification.putExtra("message", remoteMessage);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

//        Intent intent = new Intent(getApplicationContext(), Notification_Activity.class);
        Intent intent = null;
        if (storage.CheckLogin("stud_id", ctx)) {
            intent = new Intent(getApplicationContext(), AnnouncementStudentActiivty.class);
        } else {
            intent = new Intent(getApplicationContext(), AnnouncementFaculty.class);
        }

        intent.putExtra("type", "notification");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410,
                intent, PendingIntent.FLAG_ONE_SHOT);
        final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + this.getPackageName() + "/raw/notification");

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Notification")
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
                .setChannelId(NOTIF_CHANNEL_ID);


        System.out.println("MESSAGERECEIVED>>>>>>>>............");


       /* notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();*/

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1410, notificationBuilder.build());

        if (remoteMessage.getData().size() > 0) {

            System.out.println("Message data payload>>>>>");
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().toString());
            // user_id = remoteMessage.getData().get("user_id");
            // date = remoteMessage.getData().get("date");
            //hal_id = remoteMessage.getData().get("hal_id");
            // M_view = remoteMessage.getData().get("M_view");

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {

                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        if (remoteMessage == null)
            return;

        //FirebaseMessaging.getInstance().subscribeToTopic(DataStorage.TOPIC_GLOBAL);
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            System.out.println("remotemessageNOTNULL>>>>>>>>");
            // Log.e("Notification Body: " , remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
        }
    }


    // for oreo push notification
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotifChannel(MyFirebaseMessagingServicee myFirebaseMessagingService) {
        NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID,
                "MyApp events", NotificationManager.IMPORTANCE_HIGH);
        // Configure the notification channel
        channel.setDescription("MyApp event controls");

        channel.setShowBadge(false);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);


        NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);

        manager.createNotificationChannel(channel);
        Log.d(TAG, "createNotifChannel: created=" + NOTIF_CHANNEL_ID);
//        RemoteMessage remoteMessage1 = null;
//        sendNotification(remoteMessage1.getData());

    }


    final int icon = R.drawable.logo;

    private void handleNotification(String message, String title) {
//
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(DataStorage.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/notification");

//            Intent intent = new Intent(getApplicationContext(), Notification_Activity.class);

            storage = new DataStorage("Login_Detail", ctx);
            Intent intent = null;
            if (storage.CheckLogin("stud_id", ctx)) {
                intent = new Intent(getApplicationContext(), AnnouncementStudentActiivty.class);

            } else

            {
                intent = new Intent(getApplicationContext(), AnnouncementFaculty.class);
            }
            intent.putExtra("type", "notification");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1410,
                    intent, PendingIntent.FLAG_ONE_SHOT);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new
                    NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setSound(alarmSound)
                    .setContentText(message)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), icon))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setChannelId(NOTIF_CHANNEL_ID);

            System.out.println("MessageArrived>>>");
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1410, notificationBuilder.build());
        } else {


            final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/notification");

//            Intent intent = new Intent(getApplicationContext(), Notification_Activity.class);

            storage = new DataStorage("Login_Detail", ctx);
            Intent intent = null;
            if (storage.CheckLogin("stud_id", ctx)) {
                intent = new Intent(getApplicationContext(), AnnouncementStudentActiivty.class);
            } else {
                intent = new Intent(getApplicationContext(), AnnouncementFaculty.class);
            }

            intent.putExtra("type", "notification");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            final PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            intent,
                            PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new
                    NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setSound(alarmSound)
                    .setContentText(message)
                    .setContentIntent(resultPendingIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), icon))
                    .setAutoCancel(true)
                    .setChannelId(NOTIF_CHANNEL_ID);


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1410, notificationBuilder.build());
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());
        Log.d("PUSHJSONDATA>>>>>", json.toString());
        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");


            // boolean isBackground = data.getBoolean("is_background");
            // String imageUrl = data.getString("image");
            // String timestamp = data.getString("timestamp");
            // JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            // Log.e(TAG, "isBackground: " + isBackground);
            // Log.e(TAG, "payload: " + payload.toString());
            //  Log.e(TAG, "imageUrl: " + imageUrl);
            // Log.e(TAG, "timestamp: " + timestamp);
//
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(DataStorage.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                System.out.println("message>>>" + message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
                final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + this.getPackageName() + "/raw/notification");

//                Intent intent = new Intent(getApplicationContext(), Notification_Activity.class);

                storage = new DataStorage("Login_Detail", ctx);
                Intent intent = null;
                if (storage.CheckLogin("stud_id", ctx)) {
                    intent = new Intent(getApplicationContext(), AnnouncementStudentActiivty.class);
                } else {
                    intent = new Intent(getApplicationContext(), AnnouncementFaculty.class);
                }

                intent.putExtra("type", "notification");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                final PendingIntent resultPendingIntent =
                        PendingIntent.getActivity
                                (
                                        this,
                                        0,
                                        intent,
                                        PendingIntent.FLAG_CANCEL_CURRENT
                                );
                NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new
                        NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(title)
                        .setSound(alarmSound)
                        .setContentText(message)
                        .setContentIntent(resultPendingIntent)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), icon))
                        .setAutoCancel(true)
                        .setChannelId(NOTIF_CHANNEL_ID);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1410, notificationBuilder.build());

            } else {
                storage = new DataStorage("Login_Detail", ctx);
                // app is in background, show the notification in notification tray

//                Intent resultIntent = new Intent(getApplicationContext(), Notification_Activity.class);

                Intent resultIntent = null;
                if (storage.CheckLogin("stud_id", ctx)) {
                    resultIntent = new Intent(getApplicationContext(), AnnouncementStudentActiivty.class);
                } else {
                    resultIntent = new Intent(getApplicationContext(), AnnouncementFaculty.class);
                }

                resultIntent.putExtra("type", "notification");
                resultIntent.putExtra("message", message);

                final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + this.getPackageName() + "/raw/notification");

//                Intent intent = new Intent(getApplicationContext(), Notification_Activity.class);
                Intent intent = null;
                if (storage.CheckLogin("stud_id", ctx)) {
                    intent = new Intent(getApplicationContext(), AnnouncementStudentActiivty.class);
                } else {
                    intent = new Intent(getApplicationContext(), AnnouncementFaculty.class);
                }


                intent.putExtra("type", "notification");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                final PendingIntent resultPendingIntent =
                        PendingIntent.getActivity
                                (this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new
                        NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(title)
                        .setSound(alarmSound)
                        .setContentText(message)
                        .setContentIntent(resultPendingIntent)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), icon))
                        .setAutoCancel(true)
                        .setChannelId(NOTIF_CHANNEL_ID);


                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1410, notificationBuilder.build());

            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }


    }
}