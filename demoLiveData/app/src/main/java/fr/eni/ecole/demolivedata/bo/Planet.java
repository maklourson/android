package fr.eni.ecole.demolivedata.bo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.ecole.demolivedata.services.LinkService;

@Entity
public class Planet implements Parcelable {

    @PrimaryKey
    private int id;
    private String url;
    private String climate;
    private Date edited;
    private Date created;
    private String diameter;
    private String gravity;
    private String name;
    private String orbital_period;
    private String population;
    private String rotation_period;
    private String surface_water;
    private String terrain;
    private List<String> residents;

    public Planet() {
    }

    @Ignore
    protected Planet(Parcel in) {
        id = in.readInt();
        url = in.readString();
        climate = in.readString();
        diameter = in.readString();
        gravity = in.readString();
        name = in.readString();
        orbital_period = in.readString();
        population = in.readString();
        rotation_period = in.readString();
        surface_water = in.readString();
        terrain = in.readString();
        residents = in.createStringArrayList();
        long date = in.readLong();
        created = date != -1 ? new Date(date) : null;
        date = in.readLong();
        edited = date != -1 ? new Date(date) : null;
    }

    public static final Creator<Planet> CREATOR = new Creator<Planet>() {
        @Override
        public Planet createFromParcel(Parcel in) {
            return new Planet(in);
        }

        @Override
        public Planet[] newArray(int size) {
            return new Planet[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public Date getEdited() {
        return edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getGravity() {
        return gravity;
    }

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrbital_period() {
        return orbital_period;
    }

    public void setOrbital_period(String orbital_period) {
        this.orbital_period = orbital_period;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getRotation_period() {
        return rotation_period;
    }

    public void setRotation_period(String rotation_period) {
        this.rotation_period = rotation_period;
    }

    public String getSurface_water() {
        return surface_water;
    }

    public void setSurface_water(String surface_water) {
        this.surface_water = surface_water;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<String> getResidents() {
        return residents;
    }

    public void setResidents(List<String> residents) {
        this.residents = residents;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "url='" + url + '\'' +
                ", climate='" + climate + '\'' +
                ", edited=" + edited +
                ", created=" + created +
                ", diameter='" + diameter + '\'' +
                ", gravity='" + gravity + '\'' +
                ", name='" + name + '\'' +
                ", orbital_period='" + orbital_period + '\'' +
                ", population='" + population + '\'' +
                ", rotation_period='" + rotation_period + '\'' +
                ", surface_water='" + surface_water + '\'' +
                ", terrain='" + terrain + '\'' +
                ", residents=" + residents +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(climate);
        dest.writeString(diameter);
        dest.writeString(gravity);
        dest.writeString(name);
        dest.writeString(orbital_period);
        dest.writeString(population);
        dest.writeString(rotation_period);
        dest.writeString(surface_water);
        dest.writeString(terrain);
        dest.writeStringList(residents);
        dest.writeLong(created != null ? created.getTime() : -1);
        dest.writeLong(edited != null ? edited.getTime() : -1);
    }

    /**
     *
     * @return List<Integer>
     */
    public ArrayList<Integer> getIdsPeoples(){
        ArrayList<Integer> lst = new ArrayList<>();

        for(String people : residents){
            lst.add(LinkService.getIdPeople(people));
        }

        return lst;
    }
}
