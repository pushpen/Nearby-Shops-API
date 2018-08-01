package org.nearbyshops.ModelRoles;

import java.sql.Timestamp;

/**
 * Created by sumeet on 21/4/17.
 */
public class EmailVerificationCode {


    // Table Name
    public static final String TABLE_NAME = "EMAIL_VERIFICATION_CODES";

    // Column names

    public static final String EMAIL_CODE_ID = "EMAIL_CODE_ID";
    public static final String EMAIL = "EMAIL";
    public static final String VERIFICATION_CODE = "VERIFICATION_CODE";
    public static final String TIMESTAMP_EXPIRES = "TIMESTAMP_EXPIRES";


    public static final String createTablePostgres =

            "CREATE TABLE IF NOT EXISTS "
                    + EmailVerificationCode.TABLE_NAME + "("
                    + " " + EmailVerificationCode.EMAIL_CODE_ID + " SERIAL PRIMARY KEY,"
                    + " " + EmailVerificationCode.EMAIL + " text UNIQUE NOT NULL ,"
                    + " " + EmailVerificationCode.VERIFICATION_CODE + " text NOT NULL ,"
                    + " " + EmailVerificationCode.TIMESTAMP_EXPIRES + " timestamp with time zone NOT NULL"
                    + ")";



    // instance variables

    private int emailCodeID;
    private String email;
    private String verificationCode;
    private Timestamp timestampExpires;


    public int getEmailCodeID() {
        return emailCodeID;
    }

    public void setEmailCodeID(int emailCodeID) {
        this.emailCodeID = emailCodeID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
