package fr.eni.ecole.starwars.bo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@Entity
public class People implements Parcelable {

    @PrimaryKey
    private int id;
    private String url;
    private String birth_year;
    private String eye_color;
    private String gender;
    private String hair_color;
    private String height;
    private String mass;
    private String name;
    private String skin_color;
    private Date created;
    private Date edited;

    public People() {
    }

    @Ignore
    protected People(Parcel in) {
        id = in.readInt();
        url = in.readString();
        birth_year = in.readString();
        eye_color = in.readString();
        gender = in.readString();
        hair_color = in.readString();
        height = in.readString();
        mass = in.readString();
        name = in.readString();
        skin_color = in.readString();
        long date = in.readLong();
        created = date != -1 ? new Date(date) : null;
        date = in.readLong();
        edited = date != -1 ? new Date(date) : null;
    }

    public static final Creator<People> CREATOR = new Creator<People>() {
        @Override
        public People createFromParcel(Parcel in) {
            return new People(in);
        }

        @Override
        public People[] newArray(int size) {
            return new People[size];
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

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getEye_color() {
        return eye_color;
    }

    public void setEye_color(String eye_color) {
        this.eye_color = eye_color;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHair_color() {
        return hair_color;
    }

    public void setHair_color(String hair_color) {
        this.hair_color = hair_color;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkin_color() {
        return skin_color;
    }

    public void setSkin_color(String skin_color) {
        this.skin_color = skin_color;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getEdited() {
        return edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }

    @Override
    public String toString() {
        return "People{" +
                "url='" + url + '\'' +
                ", birth_year='" + birth_year + '\'' +
                ", eye_color='" + eye_color + '\'' +
                ", gender='" + gender + '\'' +
                ", hair_color='" + hair_color + '\'' +
                ", height='" + height + '\'' +
                ", mass='" + mass + '\'' +
                ", name='" + name + '\'' +
                ", skin_color='" + skin_color + '\'' +
                ", created=" + created +
                ", edited=" + edited +
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
        dest.writeString(birth_year);
        dest.writeString(eye_color);
        dest.writeString(gender);
        dest.writeString(hair_color);
        dest.writeString(height);
        dest.writeString(mass);
        dest.writeString(name);
        dest.writeString(skin_color);
        dest.writeLong(created != null ? created.getTime() : -1);
        dest.writeLong(edited != null ? edited.getTime() : -1);
    }
}
