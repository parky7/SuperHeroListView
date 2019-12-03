package com.example.superherolistview;

import android.os.Parcel;
import android.os.Parcelable;

public class Hero implements Parcelable {
    private String image;
    private int ranking;
    private String description;
    private String name;
    private String superPower;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperPower() {
        return superPower;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeInt(this.ranking);
        dest.writeString(this.description);
        dest.writeString(this.name);
        dest.writeString(this.superPower);
    }

    public Hero() {
    }

    protected Hero(Parcel in) {
        this.image = in.readString();
        this.ranking = in.readInt();
        this.description = in.readString();
        this.name = in.readString();
        this.superPower = in.readString();
    }

    public static final Creator<Hero> CREATOR = new Creator<Hero>() {
        @Override
        public Hero createFromParcel(Parcel source) {
            return new Hero(source);
        }

        @Override
        public Hero[] newArray(int size) {
            return new Hero[size];
        }
    };
}
