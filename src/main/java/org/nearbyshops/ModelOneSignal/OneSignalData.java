package org.nearbyshops.ModelOneSignal;

public class OneSignalData {

    public OneSignalData(int notification_type, int screen_to_open) {
        this.notification_type = notification_type;
        this.screen_to_open = screen_to_open;
    }




    public OneSignalData(int notification_type, int screen_to_open, double latCurrent, double lonCurrent, double bearing) {
        this.notification_type = notification_type;
        this.screen_to_open = screen_to_open;
        this.latCurrent = latCurrent;
        this.lonCurrent = lonCurrent;
        this.bearing = bearing;
    }




    private int notification_type;
    private int screen_to_open;

    private double latCurrent;
    private double lonCurrent;
    private double bearing;

    public double getLatCurrent() {
        return latCurrent;
    }

    public void setLatCurrent(double latCurrent) {
        this.latCurrent = latCurrent;
    }

    public double getLonCurrent() {
        return lonCurrent;
    }

    public void setLonCurrent(double lonCurrent) {
        this.lonCurrent = lonCurrent;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public int getScreen_to_open() {
        return screen_to_open;
    }

    public void setScreen_to_open(int screen_to_open) {
        this.screen_to_open = screen_to_open;
    }

    public int getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(int notification_type) {
        this.notification_type = notification_type;
    }
}
