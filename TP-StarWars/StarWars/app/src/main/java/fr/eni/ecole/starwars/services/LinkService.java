package fr.eni.ecole.starwars.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LinkService {

    private static final Pattern PATTERN_PLANET
            = Pattern.compile("^"+SWAPIService.ENDPOINT+"planets/([0-9]+)/$");

    private static final Pattern PATTERN_PEOPLE
            = Pattern.compile("^"+SWAPIService.ENDPOINT+"people/([0-9]+)/$");

    private static final Pattern PATTERN_PLANETS
            = Pattern.compile("^"+SWAPIService.ENDPOINT+"planets/[\\?]page=([0-9]+)$");

    /**
     * @param url String
     * @return
     */
    public static int getIdPlanet(String url){

        Matcher m = PATTERN_PLANET.matcher(url);
        int id = 0;
        if(m.matches()){
            id = Integer.parseInt( m.group(1));
        }
        return id;
    }

    /**
     * @param url String
     * @return
     */
    public static int getIdPeople(String url){

        Matcher m = PATTERN_PEOPLE.matcher(url);
        int id = 0;
        if(m.matches()){
            id = Integer.parseInt( m.group(1));
        }
        return id;
    }

    public static int getPagePlanets(String url){
        Matcher m = PATTERN_PLANETS.matcher(url);
        int id = 0;
        if(m.matches()){
            id = Integer.parseInt( m.group(1));
        }
        return id;
    }
}
