package fr.eni.ecole.demobroadcastreceiver.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private String tag = "TAG_SERVICE";
    private MonRunnable run;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(tag, "OnCreate Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(tag, "OnStartCommande Service");
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i <= 10; i++){

                    Log.i(tag, "Thread Service " + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        */
        run = new MonRunnable();

        new Thread(run).start();

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(tag, "OnDestroy Service");
        run.interrupt();
    }

    private class MonRunnable implements Runnable{
        private boolean interrupt;

        @Override
        public void run() {
            for(int i = 0; i <= 10; i++){

                if(interrupt){
                    break;
                }

                Log.i(tag, "Thread Service " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void interrupt(){
            this.interrupt = true;
        }
    }
}
