package app.remote.com.gremote.Model;

import java.io.Serializable;

public class Device implements Serializable{

    private String deviceName;
    private String phoneNumber;
    private int onCode;
    private int offCode;
    private int sensorOnCode;
    private int sensorOffCode;
    private int currentCheckCode;
    private int motionStatus;
    private int deviceStatus;
    private int currentStatus;
    private String deviceOffTime;
    private String lastOffTime;



    public Device(String deviceName, String phoneNumber, int onCode, int offCode, int sensorOnCode, int sensorOffCode, int currentCheckCode,  int motionStatus, int deviceStatus, String deviceOffTime, int currentStatus, String lastOffTime) {
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

    public int getSensorOnCode() {
        return sensorOnCode;
    }

    public int getSensorOffCode() {
        return sensorOffCode;
    }

    public int getCurrentCheckCode() {
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

    public int getOnCode() {
        return onCode;
    }

    public int getOffCode() {
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
