package app.remote.com.gremote.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import app.remote.com.gremote.Activity.Dashboard;
import app.remote.com.gremote.Adapter.DeviceRecyclerAdapter;
import app.remote.com.gremote.Database.DatabaseOperation;
import app.remote.com.gremote.Model.Device;
import app.remote.com.gremote.Util;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {



        List<Device> devices;
        Bundle b = intent.getExtras();

        try {
            if (b != null) {

                Object[] pdusObj = (Object[]) b.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage
                            .createFromPdu((byte[]) pdusObj[i]);

                    String phoneNumber = currentMessage
                            .getDisplayOriginatingAddress();

                    String senderNum =  phoneNumber;

                    String message = currentMessage.getDisplayMessageBody();

                    devices = DatabaseOperation.getDevice(context);

                    for (Device device : devices) {

                        if (device.getPhoneNumber().equals(senderNum)) {

                            boolean isSucess = DatabaseOperation.changeMachineState(context, message, phoneNumber, device.getDeviceName());

                            if (isSucess) {

                             /*   devices = DatabaseOperation.getDevice(context);

                                DeviceRecyclerAdapter adapter = new DeviceRecyclerAdapter(devices, (context));

                                Dashboard.deviceRecyler.setAdapter(adapter);*/

                             Intent intent1 = new Intent(context, Dashboard.class);
                             intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                             context.startActivity(intent1);

                            }

                        }

                    }

                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
