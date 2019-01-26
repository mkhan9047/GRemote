package app.remote.com.gremote.Model;

import java.io.Serializable;

public class Device implements Serializable{

    private String deviceName;
    private String phoneNumber;
    private String onCode;
    private String offCode;
    private String sensorOnCode;
    private String sensorOffCode;
    private String currentCheckCode;
    private int motionStatus;
    private int deviceStatus;
    private int currentStatus;
    private String deviceOffTime;
    private String lastOffTime;



    public Device(String deviceName, String phoneNumber, String onCode, String offCode, String sensorOnCode, String sensorOffCode, String currentCheckCode,  int motionStatus, int deviceStatus, String deviceOffTime, int currentStatus, String lastOffTime) {
        this.deviceOffTime = deviceOffTime;
        this.deviceName = deviceName;
        this.phoneNumber = phoneNumber;
        this.onCode = onCode;
        this.offCode = offCode;
        this.sensorOnCode = sensorOnCode;
        this.sensorOffCode = sensorOffCode;
        this.currentCheckCode = currentCheckCode;
        this.motionStatus = motionStatus;
        this.deviceStatus = deviceStatus;
        this.currentStatus = currentStatus;
        this.lastOffTime = lastOffTime;
    }


    public String getDeviceOffTime() {
        return deviceOffTime;
    }

    public String getSensorOnCode() {
        return sensorOnCode;
    }

    public String getSensorOffCode() {
        return sensorOffCode;
    }

    public String getCurrentCheckCode() {
        return currentCheckCode;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getDeviceStatus() {
        return deviceStatus;
    }

    public String getOnCode() {
        return onCode;
    }

    public String getOffCode() {
        return offCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getMotionStatus() {
        return motionStatus;
    }



    public String getLastOffTime() {
        return lastOffTime;
    }
}
