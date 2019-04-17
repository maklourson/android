package fr.eni.ecole.mp3player.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.StateListDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.eni.ecole.mp3player.R;
import fr.eni.ecole.mp3player.activities.adapters.SongAdapter;
import fr.eni.ecole.mp3player.models.Song;
import fr.eni.ecole.mp3player.services.MusicService;

public class MainActivity extends AppCompatActivity {

    private List<Song> songsList;
    private ListView songsView;
    private SeekBar currentSongSeekBar;
    private TextView currentSongTextView;

    //attributs de gestion du service lié
    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;

    //attribut de gestion du thread // lancé pour rafraichir la seekBar
    private Handler seekBarHandler;
    private boolean isActivityOn;

    //attribut de gestion du Broadcast
    private MusicReceiver receiver;

    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    private View mLayout;


    /*
    Gestion de la connexion au service
    **********************************
     */
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //récuperer l'instance du service lié
            musicSrv = binder.getService();
            //lui passer la liste
            musicSrv.setList(songsList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            musicBound = false;
        }
    };



    /*
    Activity : cycle de vie
    ***********************
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.main_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentSongSeekBar = (SeekBar) findViewById(R.id.currentsong_seekBar);
        currentSongTextView = (TextView) findViewById(R.id.currentsong_title);

        //charger la liste des chansons contenues sur le périphérique
        songsList = new ArrayList<Song>();

        //Permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //Cela signifie que la permission à déjà était
                //demandé et l'utilisateur l'a refusé
                //Vous pouvez aussi expliquer à l'utilisateur pourquoi
                //cette permission est nécessaire et la redemander

                //Redemande la permission
                //Necessite :: implementation 'com.android.support:design:26.1.0'
                Snackbar.make(mLayout, R.string.permission_access_required,
                        Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Request the permission
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                }).show();



            } else {
                //Sinon demander la permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
        else{
            initialize();
        }

    }

    private void initialize(){

        getSongsList();

        //trier les chansons par titre
        Collections.sort(songsList, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        //afficher la liste des chansons
        final SongAdapter adapter = new SongAdapter(this,R.layout.song, songsList);
        songsView = (ListView) findViewById(R.id.song_list);
        songsView.setAdapter(adapter);

        //abonner la liste à son écouteur
        songsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(musicSrv != null) {

                    //lui demander de la lire le son à la position
                    musicSrv.playSong(position); //=> le BroadCast Receiver reçoit l'ordre de rafraichir l'ihm
                    //lui réclamer les caractéristiques de celle-ci

                    //lancer le thread de rafraichissement de la seekBar
                    if (seekBarHandler == null) {
                        seekBarHandler = new Handler();
                    }
                    seekBarHandler.postDelayed(updateSeekBar, 1000);
                }
            }
        });

        //instancier le broadcastReceiver
        receiver = new MusicReceiver();
    }

    /**
     * Demande persmission
     * @param requestCode int
     * @param permissions String[]
     * @param grantResults int[]
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("PERMISSION_APP", "PERMISSION_GRANTED");
                    // La permission est garantie
                    initialize();
                    if(musicSrv != null){
                        musicSrv.setList(songsList);
                    }

                } else {
                    // La permission est refusée
                    Toast.makeText(MainActivity.this,"Nous ne pouvons lancer l'application sans cette autorisation.", Toast.LENGTH_LONG).show();
                    finish();
                }

            }

        }
    }


    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        super.onStart();
        //au démarrage de l'activité, on lie et démarre le service
        startService();
    }

    private void startService(){
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            //lier l'activité au service
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            //démarrer le service
            startService(playIntent);

            // enregistrer le Broadcast Receiver auprès du système
            IntentFilter filter = new IntentFilter(MusicReceiver.ACTION_SONG_CHANGED);
            filter.addCategory(Intent.CATEGORY_DEFAULT);
            registerReceiver(receiver, filter);

        }
    }

    private void stopService(){
        if(playIntent!=null){
            unbindService(musicConnection);
            stopService(playIntent);
            playIntent = null;
            musicSrv = null;

            if(receiver != null) {
                // on désenregistre notre broadcast
                unregisterReceiver(receiver);
            }
        }
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        //reprendre la mise à jour de la seekBar si l'activité est de nouveau visible
        //et si une lecture est en cours au niveau du service
        isActivityOn = true;
        if (seekBarHandler == null) {
            seekBarHandler = new Handler();
        }

        seekBarHandler.postDelayed(updateSeekBar, 1000);


    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        //stopper le thread // de mise à jour de la seekBar si l'activité n'est pas visible
        isActivityOn = false;
        if (seekBarHandler!=null) {
            seekBarHandler.removeCallbacks(updateSeekBar);
        }
        seekBarHandler = null;


    }

    /**
     * stopper le service associé lorsque l'activité est détruite
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_songs_menu, menu);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setLogo(R.drawable.ic_headphones);
        }

        return super.onCreateOptionsMenu(menu);
    }

    /*
    Evenements
    **********
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_shuffle:

                /*
                * force l'utilisation du selecteur adequate
                * car n'est pas pris en compte automatique du check au niveau de l'item
                */
                item.setChecked(!item.isChecked());

                StateListDrawable stateListDrawable = (StateListDrawable) ContextCompat.getDrawable(MainActivity.this, R.drawable.android_music_rand);
                //Deprecated
                //StateListDrawable stateListDrawable = (StateListDrawable) getResources().getDrawable(R.drawable.android_music_rand);
                int[] state = {item.isChecked()?android.R.attr.state_checked:android.R.attr.state_empty};
                stateListDrawable.setState(state);
                item.setIcon(stateListDrawable.getCurrent());

                if(musicSrv != null){
                    musicSrv.setShuffle(item.isChecked());
                }

                //shuffle
                break;
            case R.id.action_end:
                //stopper le service associé à l'activité
                stopService();
                //musicMethodsHandler.removeCallbacks(musicRun);
                //stopper l'application
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    Outils
    ******
     */

    /**
     * récupérer les flux mp3 du périphérique en s'appuyant sur le Content provider MediaStore.Audio
     */
    private void getSongsList() {

        List<String> lstKeys = new ArrayList<>();

        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.TITLE_KEY
        };

        Cursor musicCursor = musicResolver.query(musicUri, projection, selection, null, null);

        Log.i("MUSIC_SONG", "Nombre musique : " + musicCursor.getCount());

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //récupérer les infos sur les chansons
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int durationColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            int dataColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);

            int titleKey = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE_KEY);

            do {
                //ajouter la chanson à la liste
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                int thisDuration = musicCursor.getInt(durationColumn);
                String data = musicCursor.getString(dataColumn);
                String thisTitleKey = musicCursor.getString(titleKey);

                /*
                Problème de music en double
                Risque si titre en double peut être uriliser ARTIST_KEY également en la concaténant avec TITLE_KEY
                 */
                if(!lstKeys.contains(thisTitleKey)) {
                    Log.i("MUSIC_SONG", "NData : " + data);
                    lstKeys.add(thisTitleKey);
                    songsList.add(new Song(thisId, thisTitle, thisArtist, thisDuration));
                }
            }
            while (musicCursor.moveToNext());


            musicCursor.close();

        }
    }

    /**
     * Fonction de conversion de millisecondes en format timer
     * Hours:Minutes:Seconds
     * */
    private String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
        // Add hours if there
        if(hours > 0){
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if(seconds < 10) {
            secondsString = "0" + seconds;
        }else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }



    /*
     * demander au service les caractéristiques de la chanson jouée
     * et récupérer la position de lecture
     */
    public int getCurrentPosition() {
        Log.v("TAG_SERVICE", "Position before");
        if(musicSrv!=null && musicBound && musicSrv.isPlaying()){
            Log.v("TAG_SERVICE", "Position after" + musicSrv.getPosition());
            return musicSrv.getPosition();
        }
        else {
            return 0;
        }
    }

    public Song getCurrentSong() {
        Log.v("TAG_SERVICE", String.valueOf(musicBound) + String.valueOf(musicSrv.isPlaying()));
        if(musicSrv!=null && musicBound) {
            return musicSrv.getSong();
        }
        else {
            return null;
        }
    }

    /**
     * Tache répétée : mise à jour de la SeekBar
     **/
    private Runnable updateSeekBar = new Runnable() {
        public void run() {
            Log.v("TAG_RUNNABLE", "en cours");
            if (isActivityOn && musicSrv!=null && musicBound && musicSrv.isPlaying()) {
                // Mise à jour de la seekBar
                currentSongSeekBar.setProgress(getCurrentPosition());

                // rappeler ce traitement toutes les secondes
                seekBarHandler.postDelayed(this, 1000);
            }
        }
    };

    /**
     * Déclaration du Broadcast
     */
    public class MusicReceiver extends BroadcastReceiver {
        public static final String ACTION_SONG_CHANGED = "fr.eni.ecole.mp3player.ACTION_SONG_CHANGED";
        public MusicReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("TAG_RECEIVER", "Changement de chanson");
            //afficher les infos de la chanson jouée sur ordre du service
            //Aurait pu être passer dans l'intent si Parcelable
            Song currentSong = getCurrentSong();
            if (currentSong != null) {
                currentSongSeekBar.setProgress(0);
                currentSongTextView.setText(currentSong.getTitle() + " - " + milliSecondsToTimer(currentSong.getDuration()));
                currentSongSeekBar.setMax(currentSong.getDuration());
            }
        }
    }
}
