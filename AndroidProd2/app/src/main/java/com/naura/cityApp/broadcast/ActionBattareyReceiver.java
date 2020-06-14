package com.naura.cityApp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ActionBattareyReceiver extends BroadcastReceiver {
    private ISystemEventHandling systemEventHandling;
    private static final String title = "Критически низкий уровень заряда батареи";
    private static final String msg = "Подключите зарядное устройство";

    public ActionBattareyReceiver(ISystemEventHandling systemEventHandling) {
        this.systemEventHandling=systemEventHandling;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        systemEventHandling.execute(title,msg);
    }
}
