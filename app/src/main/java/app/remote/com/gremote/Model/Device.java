package app.remote.com.gremote.Model;

public class Device {

    private String deviceName;
    private int deviceStatus;
    private int onCode;
    private int offCode;
    private String phoneNumber;
    private int motionStatus;
    private long timerInMilli;
    private String lastOffTime;

    public Device(String deviceName, int deviceStatus, int onCode, int offCode, String phoneNumber, int motionStatus, long timerInMilli, String lastOffTime) {
        this.deviceName = deviceName;
        this.deviceStatus = deviceStatus;
        this.onCode = onCode;
        this.offCode = offCode;
        this.phoneNumber = phoneNumber;
        this.motionStatus = motionStatus;
        this.timerInMilli = timerInMilli;
        this.lastOffTime = lastOffTime;
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

    public long getTimerInMilli() {
        return timerInMilli;
    }

    public String getLastOffTime() {
        return lastOffTime;
    }
}
