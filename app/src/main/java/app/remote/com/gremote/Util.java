package app.remote.com.gremote;

import android.content.Context;
import android.telephony.SmsManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Util {

    public static HashMap<String, Integer> getTimeFromMillisecond(long millis) {

        /*make a placeholder hash map object*/
        HashMap<String, Integer> holder = new HashMap<>();

        /*get total days from the Milliseconds with TimeUnit Object */
        int days = (int) TimeUnit.MILLISECONDS.toDays(millis);
        /*minus the days from the total seconds*/
        millis -= TimeUnit.DAYS.toMillis(days);
        /*get total hours from the Milliseconds with TimeUnit Object */
        int hours = (int) TimeUnit.MILLISECONDS.toHours(millis);
        /*minus the hours from the total seconds*/
        millis -= TimeUnit.HOURS.toMillis(hours);
        /*get total minutes from the Milliseconds with TimeUnit Object */
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(millis);
        /*minus the minutes from the total seconds*/
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        /*get total seconds from the Milliseconds with TimeUnit Object */
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(millis);


        /*put the days, hours, minutes and seconds to the HashMap */
        holder.put("day", days);
        holder.put("hour", hours);
        holder.put("min", minutes);
        holder.put("sec", seconds);

        /*return the hashMap*/
        return holder;
    }

    public static String getLastTime() {
        String am_pm = null;

        if (Calendar.getInstance().get(Calendar.AM_PM) == Calendar.AM) {
            am_pm = "AM";
        } else if (Calendar.getInstance().get(Calendar.AM_PM) == Calendar.PM) {
            am_pm = "PM";
        }

        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + " " + am_pm;
    }

    public static void sendSms(String phoneNo, String message){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
    }

}
