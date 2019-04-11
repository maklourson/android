package fr.eni.ecole.demolistes.manager;

import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.demolistes.bo.Personne;

public class PersonneManager {

    public static List<Personne> getList(){
        List<Personne> lst = new ArrayList<>();

        lst.add(new Personne(1, "Bouvet", "Laurent"));
        lst.add(new Personne(2, "Dupont", "Alain"));
        lst.add(new Personne(3, "Dupont", "Thierry"));
        lst.add(new Personne(4, "Marchand", "Frédéric"));
        lst.add(new Personne(5, "Dupond", "Alexa"));
        lst.add(new Personne(6, "Martin", "Grégory"));
        lst.add(new Personne(7, "Athère", "Guillaume"));
        lst.add(new Personne(8, "Lost", "Get"));
        lst.add(new Personne(9, "Mun", "Thierry"));
        lst.add(new Personne(10, "Prague", "Isabelle"));
        lst.add(new Personne(11, "Spatz", "David"));
        lst.add(new Personne(12, "Vernon", "Gladys"));
        lst.add(new Personne(13, "Hilton", "Pair"));
        lst.add(new Personne(14, "Adenus", "Alix"));

        return lst;
    }
}
