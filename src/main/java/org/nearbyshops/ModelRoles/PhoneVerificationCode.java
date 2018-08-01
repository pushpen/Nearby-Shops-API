package org.nearbyshops.ModelRoles;

import java.sql.Timestamp;

/**
 * Created by sumeet on 21/4/17.
 */
public class PhoneVerificationCode {

    // Table Name
    public static final String TABLE_NAME = "PHONE_VERIFICATION_CODES";

    // Column names

    public static final String PHONE_CODE_ID = "PHONE_CODE_ID";
    public static final String PHONE = "PHONE";
    public static final String VERIFICATION_CODE = "VERIFICATION_CODE";
    public static final String TIMESTAMP_EXPIRES = "TIMESTAMP_EXPIRES";


    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + PhoneVerificationCode.TABLE_NAME + "("
                    + " " + PhoneVerificationCode.PHONE_CODE_ID + " SERIAL PRIMARY KEY,"
                    + " " + PhoneVerificationCode.PHONE + " text UNIQUE NOT NULL ,"
                    + " " + PhoneVerificationCode.VERIFICATION_CODE + " text NOT NULL ,"
                    + " " + PhoneVerificationCode.TIMESTAMP_EXPIRES + " timestamp with time zone NOT NULL"
                    + ")";



    // instance variables

    private int phoneCodeID;
    private String phone;
    private String verificationCode;
    private Timestamp timestampExpires;


    public int getPhoneCodeID() {
        return phoneCodeID;
    }

    public void setPhoneCodeID(int phoneCodeID) {
        this.phoneCodeID = phoneCodeID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Timestamp getTimestampExpires() {
        return timestampExpires;
    }

    public void setTimestampExpires(Timestamp timestampExpires) {
        this.timestampExpires = timestampExpires;
    }
}
