package fr.eni.ecole.demobroadcastreceiver.receivers;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {


    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            String msg = intent.getStringExtra("message");
            Log.i("TAG_SERVICE", "message : " + msg);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAG_SERVICE", "onDestroy");
    }
}
