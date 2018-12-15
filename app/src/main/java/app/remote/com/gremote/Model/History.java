package app.remote.com.gremote.Model;

public class History {

    private String device_name;
    private String status;
    private String time;
    private String Date;

    public History(String device_name, String status, String time, String date) {
        this.device_name = device_name;
        this.status = status;
        this.time = time;
        Date = date;
    }

    public String getDevice_name() {
        return device_name;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return Date;
    }
}
