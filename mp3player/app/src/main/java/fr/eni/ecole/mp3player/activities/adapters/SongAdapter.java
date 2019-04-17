package fr.eni.ecole.mp3player.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.eni.ecole.mp3player.R;
import fr.eni.ecole.mp3player.models.Song;


/**
 * Created by bmartin on 08/06/2016.
 */
public class SongAdapter extends ArrayAdapter<Song> {
    private List<Song> songs;
    private int idView;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public SongAdapter(Context context, int resource, List<Song> objects) {
        super(context, resource, objects);
        idView = resource;
        songs = objects;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View songView = convertView;  // Réutilisation de la vue existante si possible...

        // ... sinon, on l'instancie
        if (songView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); // Demander au systeme de nous fournir le service
            songView = li.inflate(idView, parent, false);
        }

        //afficher le titre et le nom de l'artiste de chaque chanson de la liste
        TextView titleView = (TextView)songView.findViewById(R.id.song_title);
        TextView artistView = (TextView)songView.findViewById(R.id.song_artist);
        Song currSong = songs.get(position);
        if (currSong!=null) {
            //get title and artist strings
            titleView.setText(currSong.getTitle());
            artistView.setText(currSong.getArtist());
        }
        return songView;
    }
}
