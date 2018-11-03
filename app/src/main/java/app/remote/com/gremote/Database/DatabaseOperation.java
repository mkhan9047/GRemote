package app.remote.com.gremote.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import app.remote.com.gremote.Model.Device;

public class DatabaseOperation {

    public static boolean saveDevice(Context context, Device device){

        boolean isSuccess = false;



            ContentValues contentValues = new ContentValues();
            contentValues.put("device_name", device.getDeviceName());
            contentValues.put("phone_number", device.getPhoneNumber());
            contentValues.put("device_on", device.getOnCode());
            contentValues.put("device_off", device.getOffCode());
            contentValues.put("timer", device.getTimerInMilli());
            contentValues.put("motion_enable",device.getMotionStatus());
            contentValues.put("status", device.getDeviceStatus());
            contentValues.put("last_off", device.getLastOffTime());

        try{

            SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase database = databaseHelper.getWritableDatabase();
            database.insertOrThrow("Device", null, contentValues);

            isSuccess = true;

        }catch (SQLiteException s){

            isSuccess = false;

            Toast.makeText(context, "Data Should be unique!", Toast.LENGTH_SHORT).show();

        }


        return isSuccess;

    }

}
