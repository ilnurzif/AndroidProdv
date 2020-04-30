package com.naura.cityApp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.naura.myapplication.R;

public class AirplaneReceiver extends BroadcastReceiver {
    ISystemEventHandling systemEventHandling;

    public AirplaneReceiver(ISystemEventHandling systemEventHandling) {
        this.systemEventHandling = systemEventHandling;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = context.getString(R.string.broadcasteventmsg);
        String msg = context.getString(R.string.airplanemodeenablemsg);
        systemEventHandling.execute(title, msg);
    }
}
