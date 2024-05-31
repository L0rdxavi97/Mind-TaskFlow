package com.proyectomindtaskflow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null){
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                boolean isConnected = !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                if (isConnected) {
                    Toast.makeText(context, "Conexión establecida", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Conexión perdida", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}