package fr.eni.ecole.mp3player.models;

/**
 * Created by bmartin on 02/06/2016.
 */
public class Song {
    private long id;
    private String title;
    private String artist;
    private int duration;

    public Song(long songID, String songTitle, String songArtist, int songDuration) {
        id=songID;
        title=songTitle;
        artist=songArtist;
        duration = songDuration;
    }

    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public int getDuration(){return duration;}
}
