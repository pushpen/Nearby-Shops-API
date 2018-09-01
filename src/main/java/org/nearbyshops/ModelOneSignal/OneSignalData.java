package org.nearbyshops.ModelOneSignal;

public class OneSignalData {



    public OneSignalData(int notification_type, int screen_to_open) {
        this.notification_type = notification_type;
        this.screen_to_open = screen_to_open;
    }


    public OneSignalData(int notification_type, int screen_to_open, Object extraData) {
        this.notification_type = notification_type;
        this.screen_to_open = screen_to_open;
        this.extraData = extraData;
    }




    private int notification_type;
    private int screen_to_open;

    private Object extraData;



    /*
    *
    * Getter and Setters
    *
    * */



    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
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
