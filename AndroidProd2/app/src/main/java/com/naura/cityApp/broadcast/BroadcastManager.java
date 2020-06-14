package com.naura.cityApp.broadcast;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.naura.myapplication.R;

public class BroadcastManager {
    private Context context;
    private AirplaneReceiver airplaneReceiver;
    private NetworkStateChange networkStateChange;
    private ActionBattareyReceiver actionBattareyReceiver;

    public BroadcastManager(Context context) {
        this.context = context;
        initNotificationChannel();
        SystemEventHandling systemEventHandling=new SystemEventHandling();
        networkStateChange = new NetworkStateChange(context, systemEventHandling);
        actionBattareyReceiver = new ActionBattareyReceiver(systemEventHandling);
        context.registerReceiver(actionBattareyReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("2", "name", importance);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    class SystemEventHandling implements ISystemEventHandling {
        private int numb=2;
        @Override
        public void execute(String title, String msg) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(msg);
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(numb, builder.build());
        }
    }
}
