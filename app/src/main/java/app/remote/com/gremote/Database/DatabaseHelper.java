package app.remote.com.gremote.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName = "HardwareDatabase";

    private static final int databaseVersion = 1;


    public DatabaseHelper(Context context) {

        super(context, databaseName, null, databaseVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String device_table = "CREATE TABLE Device (\n" +

                "device_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +

                "device_name TEXT UNIQUE, \n" +

                "phone_number TEXT UNIQUE,\n" +

                "device_on TEXT, \n" +

                "device_off TEXT, \n" +

                "sensor_on TEXT, \n" +

                "sensor_off TEXT, \n" +

                "current_check_code TEXT, \n" +

                "motion_enable INTEGER, \n" +

                "status INTEGER, \n" +

                "current_status INTEGER, \n" +


                "intent_number INTEGER, \n" +

                "last_off TEXT, \n" +

                "device_off_time TEXT \n" +

                ");";

        String historyTable = "CREATE TABLE History (\n" +

                "history_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, \n" +

                "device_name TEXT, \n" +

                "status TEXT, \n" +

                "time TEXT, \n" +

                "date TEXT \n" +

                ");";

        sqLiteDatabase.execSQL(historyTable);

        sqLiteDatabase.execSQL(device_table);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }


}
