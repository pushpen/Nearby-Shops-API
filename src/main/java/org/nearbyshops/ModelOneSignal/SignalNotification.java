package org.nearbyshops.ModelOneSignal;

import java.util.ArrayList;

/**
 * Created by sumeet on 7/8/17.
 */
public class SignalNotification {


    private String app_id;
    private ArrayList<String> include_player_ids = new ArrayList<>();
    private String large_icon;
    private String big_picture;
    private String android_sound;
    private int priority;
    private Title headings;
    private Message contents;

    private Object data;


    // getter and setter


    public String getLarge_icon() {
        return large_icon;
    }

    public void setLarge_icon(String large_icon) {
        this.large_icon = large_icon;
    }

    public String getBig_picture() {
        return big_picture;
    }

    public void setBig_picture(String big_picture) {
        this.big_picture = big_picture;
    }

    public String getAndroid_sound() {
        return android_sound;
    }

    public void setAndroid_sound(String android_sound) {
        this.android_sound = android_sound;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Title getHeadings() {
        return headings;
    }

    public void setHeadings(Title headings) {
        this.headings = headings;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public ArrayList<String> getInclude_player_ids() {
        return include_player_ids;
    }

    public void setInclude_player_ids(ArrayList<String> include_player_ids) {
        this.include_player_ids = include_player_ids;
    }


    public Message getContents() {
        return contents;
    }

    public void setContents(Message contents) {
        this.contents = contents;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
