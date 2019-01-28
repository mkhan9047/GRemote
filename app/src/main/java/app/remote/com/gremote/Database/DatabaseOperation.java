package app.remote.com.gremote.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.remote.com.gremote.Model.Device;
import app.remote.com.gremote.Model.History;
import app.remote.com.gremote.Util;


public class DatabaseOperation {

    public static boolean saveDevice(Context context, Device device) {
        boolean isSuccess = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put("device_name", device.getDeviceName());
        contentValues.put("phone_number", device.getPhoneNumber());
        contentValues.put("device_on", device.getOnCode());
        contentValues.put("device_off", device.getOffCode());
        contentValues.put("sensor_on", device.getSensorOnCode());
        contentValues.put("sensor_off", device.getSensorOffCode());
        contentValues.put("current_check_code", device.getCurrentCheckCode());
        contentValues.put("motion_enable", device.getMotionStatus());
        contentValues.put("status", device.getDeviceStatus());
        contentValues.put("current_status", device.getCurrentStatus());
        contentValues.put("device_off_time", device.getDeviceOffTime());
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

            cursor = database.query("Device", new String[]{
                    "device_name",
                    "phone_number",
                    "device_on",
                    "device_off",
                    "sensor_on",
                    "sensor_off",
                    "current_check_code",
                    "motion_enable",
                    "status",
                    "current_status",
                    "last_off",
                    "device_off_time",
                    "intent_number"
            }, null, null, null, null, null);

            if (cursor != null && cursor.getCount() != 0) {

                while (cursor.moveToNext()) {

                    devices.add(new Device(cursor.getInt(12),
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7),
                            cursor.getInt(8),
                            cursor.getString(11),
                            cursor.getInt(9),
                            cursor.getString(10)


                    ));
                }

            } else {

                Toast.makeText(context, "No Devices found!", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLiteException e) {

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

        } finally {

            if (cursor != null)
                cursor.close();
        }


        return devices;
    }

    public static boolean changeMachineState(Context context, String code, String phoneNumber, String device_name) {

        boolean isSuccess;
        boolean isTimeUpdateSuccess = false;

        try {

            ContentValues contentValues = new ContentValues();

            if (code.equals("DEVICE_ON_SUCCESSFUL")) {

                contentValues.put("status", 1);

                /*setting history table */

                insertHistory(context, "ON", device_name, Util.getTime(), Util.getDate());

                isTimeUpdateSuccess = true;

            } else if (code.equals("DEVICE_OFF_SUCCESSFUL")) {

                contentValues.put("status", 0);
                /*setting history table */
                insertHistory(context, "OFF", device_name, Util.getTime(), Util.getDate());
                isTimeUpdateSuccess = UpdateLastOffTime(context, phoneNumber);
                DatabaseOperation.UpdateDeviceOffTime(context, "Set Timer", phoneNumber);

            } else if (code.equals("SENSOR_ON")) {

                contentValues.put("motion_enable", 1);
                isTimeUpdateSuccess = true;

            } else if (code.equals("SENSOR_OFF")) {

                contentValues.put("motion_enable", 0);
                isTimeUpdateSuccess = true;

            } else if (code.equals("CURRENT_ON")) {

                contentValues.put("current_status", 1);
                isTimeUpdateSuccess = true;

            } else if (code.equals("CURRENT_OFF")) {

                contentValues.put("current_status", 0);
                isTimeUpdateSuccess = true;


            }


            SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);

            SQLiteDatabase database = databaseHelper.getWritableDatabase();


            database.update("Device", contentValues, "phone_number=?", new String[]{phoneNumber});

            isSuccess = true;

        } catch (SQLiteException s) {

            isSuccess = false;

            Toast.makeText(context, s.getMessage(), Toast.LENGTH_SHORT).show();

        }

        return isSuccess && isTimeUpdateSuccess;

    }


    public static int isDeviceOff(Context context, String phoneNumber){

        Cursor cursor = null;

        int deviceStatus = 0;

        try {
            SQLiteOpenHelper openHelper = new DatabaseHelper(context);

            SQLiteDatabase database = openHelper.getReadableDatabase();

            cursor = database.query("Device", new String[]{
                    "status"
            }, "phone_number=?", new String[]{phoneNumber}, null, null, null);

            if (cursor != null && cursor.getCount() != 0) {

                while (cursor.moveToNext()) {
                    deviceStatus = cursor.getInt(0);
                }

            }
        } catch (SQLiteException e) {

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

        } finally {

            if (cursor != null)
                cursor.close();
        }


        return deviceStatus;


    }

    private static boolean UpdateLastOffTime(Context context, String phoneNumber) {

        SQLiteDatabase database = null;
        boolean isSuccess;

        try {

            ContentValues contentValues = new ContentValues();


            contentValues.put("last_off", Util.getTime());


            SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);

            database = databaseHelper.getWritableDatabase();


            database.update("Device", contentValues, "phone_number=?", new String[]{phoneNumber});

            isSuccess = true;

        } catch (SQLiteException s) {

            isSuccess = false;

            Toast.makeText(context, s.getMessage(), Toast.LENGTH_SHORT).show();

        }


        if (database != null)
            database.close();


        return isSuccess;

    }

    public static boolean UpdateDeviceOffTime(Context context, String time, String phoneNumber) {

        boolean isSuccess;

        try {

            ContentValues contentValues = new ContentValues();


            contentValues.put("device_off_time", time);


            SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);

            SQLiteDatabase database = databaseHelper.getWritableDatabase();


            database.update("Device", contentValues, "phone_number=?", new String[]{phoneNumber});

            isSuccess = true;

        } catch (SQLiteException s) {

            isSuccess = false;

            Toast.makeText(context, s.getMessage(), Toast.LENGTH_SHORT).show();

        }

        return isSuccess;

    }




    private static void insertHistory(Context context, String status, String device_name, String Time, String Date) {

        boolean isSuccess;

        try {

            ContentValues contentValues = new ContentValues();


            contentValues.put("device_name", device_name);

            contentValues.put("status", status);

            contentValues.put("time", Time);

            contentValues.put("date", Date);


            SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);

            SQLiteDatabase database = databaseHelper.getWritableDatabase();


            database.insertOrThrow("History", null,
                    contentValues
            );

            isSuccess = true;

        } catch (SQLiteException s) {

            isSuccess = false;

            Toast.makeText(context, s.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    public static List<History> getHistory(Context context) {

        Cursor cursor = null;

        List<History> histories = new ArrayList<>();

        try {
            SQLiteOpenHelper openHelper = new DatabaseHelper(context);

            SQLiteDatabase database = openHelper.getReadableDatabase();

            cursor = database.query("History", new String[]{"device_name", "status", "time", "date"}, null, null, null, null, null);

            if (cursor != null && cursor.getCount() != 0) {

                while (cursor.moveToNext()) {

                    histories.add(new History(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                    ));
                }

            } else {

                Toast.makeText(context, "No History found!", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLiteException e) {

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

        } finally {

            if (cursor != null)
                cursor.close();
        }


        return histories;
    }


}
