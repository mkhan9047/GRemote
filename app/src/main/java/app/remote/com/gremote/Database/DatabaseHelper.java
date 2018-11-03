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

                "device_on INTEGER, \n" +

                "device_off INTEGER, \n" +

                "timer INTEGER, \n" +

                "motion_enable INTEGER, \n" +

                "status INTEGER, \n" +

                "last_off TEXT \n" +

                ");";


        sqLiteDatabase.execSQL(device_table);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }


}
