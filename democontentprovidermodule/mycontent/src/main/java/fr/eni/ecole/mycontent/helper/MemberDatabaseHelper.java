package fr.eni.ecole.mycontent.helper;

import android.content.Context;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;


public class MemberDatabaseHelper extends SQLiteOpenHelper {


    public static final String AUTHORITY = "fr.eni.ecole.contentmember.provider";

    // Nom du fichier qui représente la base
    public static final String NAME = "membre.db";

    // Version de la base
    public static final int VERSION = 1;

    private static final int DIR = 0;
    private static final int ITEM = 1;

    private static final UriMatcher membreMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        membreMatcher.addURI(AUTHORITY, "metier", DIR);
        membreMatcher.addURI(AUTHORITY, "metier/#", ITEM);
    }

    public static final class Metier implements BaseColumns {

        // Nom de la table
        public static final String TABLE_NAME = "metier";

        // URI
        public static final String STR_URI = "content://" + AUTHORITY + "/" + TABLE_NAME;

        // URI
        public static final Uri CONTENT_URI = Uri.parse(STR_URI);

        // Types MIME
        public static final String TYPE_DIR = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_NAME;

        public static final String TYPE_ITEM = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_NAME;


        // Attributs de la table

        public static final String INTITULE = "intitule";

        public static final String SALAIRE = "salaire";

    }

    public MemberDatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "
                + Metier.TABLE_NAME + " ("
                + Metier._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Metier.INTITULE + " VARCHAR(255)," + Metier.SALAIRE
                + " VARCHAR(255)" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "
                + Metier.TABLE_NAME + ";");
        onCreate(db);
    }

    public String getType(Uri uri) {

        // Regardez dans l'exemple précédent, pour toute une table on avait la valeur 0
        if (membreMatcher.match(uri) == 0) {
            return(Metier.TYPE_DIR);
        }

        // Et si l'URI correspondait à une ligne précise dans une table, elle valait 1
        return(Metier.TYPE_ITEM);

    }


}
