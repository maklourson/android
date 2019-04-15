package fr.eni.ecole.demobroadcastreceiver.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Receiver2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG_BROADCAST", "MESSAGE");
        String s = intent.getStringExtra("broadcast");

        Toast.makeText(context,"message :" + s, Toast.LENGTH_LONG).show();

    }
}
