package app.remote.com.gremote.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import app.remote.com.gremote.Database.DatabaseOperation;
import app.remote.com.gremote.Model.Device;
import app.remote.com.gremote.Util;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String phone = intent.getStringExtra("phone");
        String offCode = intent.getStringExtra("off_code");
        // Log.e("MKPARAM", phone + offCode);
        if (phone != null && offCode != null) {
            int deviceStatus = DatabaseOperation.isDeviceOff(context, phone);
            if(deviceStatus == 1){
                Util.sendSms(phone, String.valueOf(offCode));
                DatabaseOperation.UpdateDeviceOffTime(context, "Unknown", phone);
            }

        } else {
            Toast.makeText(context, "something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }
}
