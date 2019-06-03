package com.example.shadabsheikh.pain_tracker;

/**
 * Created by shadabsheikh on 2017-10-27.
 */

public class CodesType {

    private String name;
    private int numOfSongs;
    private int thumbnail;

    public CodesType() {
    }

    public CodesType(String name, int numOfSongs, int thumbnail) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
