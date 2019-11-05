package com.infinity.infoway.gsfc.model;

/**
 * Created by user01 on 1/10/2018.
 */

public class Holiday {

    private String title, genre, year;




    public Holiday(String title, String genre) {
        this.title = title;
        this.genre = genre;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
