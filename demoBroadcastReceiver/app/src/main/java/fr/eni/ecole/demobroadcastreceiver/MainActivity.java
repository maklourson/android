package fr.eni.ecole.demobroadcastreceiver;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import fr.eni.ecole.demobroadcastreceiver.receivers.MyIntentService;
import fr.eni.ecole.demobroadcastreceiver.receivers.MyReceiver;
import fr.eni.ecole.demobroadcastreceiver.receivers.Receiver2;
import fr.eni.ecole.demobroadcastreceiver.services.MyService;
import fr.eni.ecole.demobroadcastreceiver.services.MyServiceBidirectionnel;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_PERM = 1;
    private MyReceiver receiver;
    private Receiver2 receiver2;

    /*Service lié*/
    private ServiceConnection serviceConnection;
    private MyServiceBidirectionnel service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.receiver = new MyReceiver();

        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.RECEIVE_SMS},REQUEST_PERM);

        }
        else{
            //Enregistre un receiver
            IntentFilter intent = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
            registerReceiver(receiver,intent);
        }

        //Enregistre le receiver
        this.receiver2 = new Receiver2();
        IntentFilter i = new IntentFilter("fr.eni.ecole.receiver.send");
        registerReceiver(receiver2, i);


        /*Service lié*/

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MainActivity.this.service = ((MyServiceBidirectionnel.MyActivityBind)service).getMyService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i("TAG_SERVICE", "disconnect Service");
                MainActivity.this.service = null;
            }
        };

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_PERM){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Enregistre receiver
            }
        }
    }

    public void clickSendBroadCast(View view) {

        Intent intent = new Intent() ;
        intent.setAction("fr.eni.ecole.receiver.send");
        intent.putExtra("broadcast", "Bonjour");
        sendBroadcast(intent);

    }

    private Intent intentService;

    public void clickStartService(View view) {
        intentService = new Intent(MainActivity.this, MyService.class);
        startService(intentService);
    }

    public void clickStopService(View view) {
        stopService(intentService);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Désactive les receiver
        unregisterReceiver(receiver);
        unregisterReceiver(receiver2);
    }

    public void onBindService(View view) {

        if(MainActivity.this.service == null){
            Intent i = new Intent(MainActivity.this, MyServiceBidirectionnel.class);
            bindService(i, serviceConnection, Context.BIND_AUTO_CREATE);
        }

    }

    public void onUnbindService(View view) {
        if(MainActivity.this.service != null){
            unbindService(serviceConnection);
            this.service = null;
        }
    }

    public void onCompteur(View view) {
        if(MainActivity.this.service != null){
            Toast.makeText(this, "Compteur : " + service.getCompteur()
            , Toast.LENGTH_LONG).show();
        }
    }

    public void onIntentService(View view) {

        Intent msgIntent = new Intent(this, MyIntentService.class);
        msgIntent.putExtra("message", "Intent Service message");
        startService(msgIntent);
    }
}
