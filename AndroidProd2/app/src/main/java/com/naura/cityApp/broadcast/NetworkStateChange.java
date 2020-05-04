package com.naura.cityApp.broadcast;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class NetworkStateChange {
    private static int enterNumb=0;
    private final Context context;
    private final ISystemEventHandling systemEventHandling;
    private static final String title = "Состояние сети изменилось";
    private static final String msgConnected = "Вы снова подключены к сети. И снова получаете актуальные данные о погоде";
    private static final String msgDisconnected = "Сетевое соеденение не доступно. Исправьте это, что бы получать актуальные данные о погоде";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public NetworkStateChange(final Context context, final ISystemEventHandling systemEventHandling) {
        this.context = context;
        this.systemEventHandling = systemEventHandling;
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        cm.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                if (enterNumb++==0) return;
                if (isNetworkConnected(context))
                    systemEventHandling.execute(title, msgConnected);
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                enterNumb++;
                if (!isNetworkConnected(context))
                    systemEventHandling.execute(title, msgDisconnected);
            }

        });
    }

    public static boolean isNetworkConnected(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                final NetworkInfo ni = cm.getActiveNetworkInfo();
                if (ni != null) {
                    return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            } else {
                final Network n = cm.getActiveNetwork();
                if (n != null) {
                    final NetworkCapabilities nc = cm.getNetworkCapabilities(n);
                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                }
            }
        }
        return false;
    }
}
