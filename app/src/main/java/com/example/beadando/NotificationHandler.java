package com.example.beadando;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private static final String CHANNEL_ID = "shop_notification_channel";
    private final int NOTIFICATION_ID = 0;
    private NotificationManager myManager;
    private Context myContext;
    public NotificationHandler(Context context){
        this.myContext = context;
        this.myManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;

        NotificationChannel channel = new NotificationChannel
                (CHANNEL_ID, "Shop notification", NotificationManager.IMPORTANCE_DEFAULT);

        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.RED);
        channel.setDescription("Notification from shop app.");
        this.myManager.createNotificationChannel(channel);
    }
    public void send(String message){
        Intent intent = new Intent(myContext, ShopListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity
                (myContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(myContext, CHANNEL_ID)
                .setContentTitle("Shop Application")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_shopping_cart)
                .setContentIntent(pendingIntent);

        this.myManager.notify(NOTIFICATION_ID, builder.build());

    }
    public void cancel(){
        this.myManager.cancel(NOTIFICATION_ID);
    }
}
