package com.mobileprogramming.utsmobpro1904436.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class CoffeeDrinkNote implements Parcelable {
    private int id;
    private String title;
    private String category;
    private String desc;
    private String food;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public CoffeeDrinkNote(){

    }

    public CoffeeDrinkNote(int id, String title, String category, String desc, String food) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.desc = desc;
        this.food = food;
    }

    public CoffeeDrinkNote(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.category = in.readString();
        this.desc = in.readString();
        this.food = in.readString();
    }

    public static final Creator<CoffeeDrinkNote> CREATOR = new Creator<CoffeeDrinkNote>() {
        @Override
        public CoffeeDrinkNote createFromParcel(Parcel source) {
            return new CoffeeDrinkNote(source);
        }

        @Override
        public CoffeeDrinkNote[] newArray(int size) {
            return new CoffeeDrinkNote[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.category);
        dest.writeString(this.desc);
        dest.writeString(this.food);
    }
}
