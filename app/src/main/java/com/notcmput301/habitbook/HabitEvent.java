package com.notcmput301.habitbook;

import android.location.Location;
import android.media.Image;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cole on 2017-10-20.
 */

public class HabitEvent {

    private HabitType habitType;
    private String title;
    private String comment;
    private Image image;
    private Date date;
    private int likes;
    private int dislikes;
    private Location location;

    public HabitType getHabitType() {
        return habitType;
    }

    public void setHabitType(HabitType habitType) {
        this.habitType = habitType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date creationDate) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HabitEvent that = (HabitEvent) o;

        if (!getHabitType().equals(that.getHabitType())) return false;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(this).equals(fmt.format(that));
    }

    @Override
    public String toString(){
        return this.title;
    }
}
