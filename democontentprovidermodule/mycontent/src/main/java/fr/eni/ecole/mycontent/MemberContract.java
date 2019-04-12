package fr.eni.ecole.mycontent;

import android.net.Uri;

import fr.eni.ecole.mycontent.helper.MemberDatabaseHelper;

public abstract class MemberContract {

    public static abstract class Contract{

        // URI
        public final static Uri URI = Uri.parse(MemberDatabaseHelper.Metier.STR_URI);

        public final static String ID = MemberDatabaseHelper.Metier._ID;
        public final static String INTITULE = MemberDatabaseHelper.Metier.INTITULE;
        public final static String SALAIRE = MemberDatabaseHelper.Metier.SALAIRE;

    }
}
