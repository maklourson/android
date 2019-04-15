package fr.eni.ecole.demobroadcastreceiver.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyServiceBidirectionnel extends Service {

    private int compteur;

    private IBinder myBinder = new MyActivityBind();

    public MyServiceBidirectionnel() {
    }

    public class MyActivityBind extends Binder{
        public MyServiceBidirectionnel getMyService(){
            return MyServiceBidirectionnel.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
       this.onStartCommand(intent, START_FLAG_REDELIVERY, START_STICKY);
       return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {

        /*
        * Par défaut renvoi false
        * Si renvoi true => appel onRebind()
        * */
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i <= 100; i++){
                    compteur++;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    public int getCompteur(){
        return compteur;
    }
}
