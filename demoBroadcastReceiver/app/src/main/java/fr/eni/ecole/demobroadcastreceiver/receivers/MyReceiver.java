package fr.eni.ecole.demobroadcastreceiver.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtrats = intent.getExtras();

        if(intentExtrats != null){
            Object[] sms = (Object[]) intentExtrats.get("pdus");
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < sms.length; i++){
                SmsMessage message = SmsMessage.createFromPdu((byte[])sms[i]);
                String body = message.getMessageBody();
                String address = message.getOriginatingAddress();

                sb.append("SMS FROM : ").append(address).append("\n")
                        .append(body).append("\n");
            }

            Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
