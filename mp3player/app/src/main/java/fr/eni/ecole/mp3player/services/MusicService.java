package fr.eni.ecole.mp3player.services;

import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.Random;

import fr.eni.ecole.mp3player.activities.MainActivity;
import fr.eni.ecole.mp3player.models.Song;


public class MusicService extends Service {

    //media player géré par le service
    private MediaPlayer player;

    //liste des chansons du périphérique
    private List<Song> songs;

    //position de la chanson en cours de lecture
    private int songPosition;

    private boolean shuffle = false;

    public MusicService() {
    }

    /*
    Service : Cycle de vie
     */

    /**
     * Called by the system when the service is first created.  Do not call this method directly.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //initialisation des attributs
        songPosition = 0;
        player = new MediaPlayer();
        initMediaPlayer();
    }

    /**
     * définir les caractéristiques du player
     */
    @SuppressWarnings("deprecation")
    private void initMediaPlayer() {
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            player.setAudioAttributes(
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build());
        } else {
            //deprecated
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        player.setLooping(false);

        /**
         * Quand le son est prêt à être lu
         */
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //lancer la lecture
                mp.start();
                //envoi du Broadcast afin d'avertir l'activité du changement de chanson
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(MainActivity.MusicReceiver.ACTION_SONG_CHANGED);
                broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                sendBroadcast(broadcastIntent);

            }
        });

        /**
         * Event appeler quand un son se termine
         */
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //passer à la chanson suivante ou revenir au début de la liste
                //si plus de deux musique (risque de boucle sinon)
                if(shuffle && songs.size() > 2){

                    int newPosition = songPosition;
                    int maxTour = 0;
                    //Pour éviter de retomber sur la même musique
                    do{
                        newPosition = new Random().nextInt(songs.size());
                        maxTour++;
                    }while(newPosition == songPosition || maxTour > 10);

                    songPosition = newPosition;

                }
                else{
                    if (songPosition < songs.size()-1) {
                        songPosition++;
                    }
                    else {songPosition=0;}
                }

                playSong(songPosition);
            }
        });
    }
    /*
    Service : Liaison avec son client (activité)
    *******************************************
     */
    private final IBinder musicBind = new MusicBinder();

    /**
     * Permettre à tout client de récupérer une instance du service lié
     */
    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }

    }

    /**
     * Return the communication channel to the service.  May return null if
     * clients can not bind to the service.  The returned
     * {@link IBinder} is usually for a complex interface
     * that has been <a href="{@docRoot}guide/components/aidl.html">described using
     * aidl</a>.
     * <p/>
     * <p><em>Note that unlike other application components, calls on to the
     * IBinder interface returned here may not happen on the main thread
     * of the process</em>.  More information about the main thread can be found in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html">Processes and
     * Threads</a>.</p>
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return an IBinder through which clients can call on to the
     * service.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    /**
     * Called when all clients have disconnected from a particular interface
     * published by the service.  The default implementation does nothing and
     * returns false.
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return true if you would like to have the service's
     * {@link #onRebind} method later called when new clients bind to it.
     */
    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        return false;
    }

    /**
     * Assigne la liste des musique
     * @param theSongs
     */
    public void setList(List<Song> theSongs){
        songs=theSongs;
    }

    /**
     * Détermine le shuffle
     * @param shuffle boolean
     */
    public void setShuffle(boolean shuffle){
        this.shuffle = shuffle;
    }

    /**
     * Lire une chanson
     * appeler en interne et depuis MainActivity
     */
    public void playSong(int songIndex){

        songPosition=songIndex;

        player.reset();
        Song playSong = songs.get(songPosition);
        long currSong = playSong.getID();
        //set uri
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);
        try{
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        //lancer la lecture dans une tâche asynchrone (cf OnPreparedListener)
        player.prepareAsync();
    }

    /**
     * Renvoi la position de lecture en millisecondes
     * @return int
     */
    public int getPosition(){
        return player.getCurrentPosition();
    }

    /**
     *  Retourne le son en cours
     * @return Song
     */
    public Song getSong(){
        return songs.get(songPosition);
    }

    /**
     * Retourne si un son est en lecture ou non
     * @return boolean
     */
    public boolean isPlaying(){

        return player.isPlaying();
    }


}
