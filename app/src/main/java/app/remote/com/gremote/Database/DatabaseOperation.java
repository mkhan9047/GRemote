package app.remote.com.gremote.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.remote.com.gremote.Model.Device;

public class DatabaseOperation {

    public static boolean saveDevice(Context context, Device device) {

        boolean isSuccess = false;


        ContentValues contentValues = new ContentValues();
        contentValues.put("device_name", device.getDeviceName());
        contentValues.put("phone_number", device.getPhoneNumber());
        contentValues.put("device_on", device.getOnCode());
        contentValues.put("device_off", device.getOffCode());
        contentValues.put("timer", device.getTimerInMilli());
        contentValues.put("motion_enable", device.getMotionStatus());
        contentValues.put("status", device.getDeviceStatus());
        contentValues.put("last_off", device.getLastOffTime());

        try {

            SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase database = databaseHelper.getWritableDatabase();
            database.insertOrThrow("Device", null, contentValues);

            isSuccess = true;

        } catch (SQLiteException s) {

            isSuccess = false;

            Toast.makeText(context, "Data Should be unique!", Toast.LENGTH_SHORT).show();

        }


        return isSuccess;

    }

    public static List<Device> getDevice(Context context) {

        Cursor cursor = null;

        List<Device> devices = new ArrayList<>();

        try {
            SQLiteOpenHelper openHelper = new DatabaseHelper(context);

            SQLiteDatabase database = openHelper.getReadableDatabase();

            cursor = database.query("Device", new String[]{"device_name", "phone_number", "device_on", "device_off", "timer", "motion_enable", "status", "last_off"}, null, null, null, null, null);

            if (cursor != null && cursor.getCount() != 0) {

                while (cursor.moveToNext()) {

                    devices.add(new Device(cursor.getString(0),
                            cursor.getInt(6),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getString(1),
                            cursor.getInt(5),
                            cursor.getLong(4),
                            cursor.getString(7)
                    ));
                }

            } else {
                Toast.makeText(context, "No Devices found!", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLiteException e) {

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

        }finally {

            if(cursor!=null)
            cursor.close();
        }


        return devices;
    }

}
