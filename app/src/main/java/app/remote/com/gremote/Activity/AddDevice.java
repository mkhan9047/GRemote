package app.remote.com.gremote.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.remote.com.gremote.Database.DatabaseOperation;
import app.remote.com.gremote.Model.Device;
import app.remote.com.gremote.R;
import app.remote.com.gremote.Util;

public class AddDevice extends AppCompatActivity {

    TextView saveButton;
    EditText deviceName, devicePhone, deviceOnCode, deviceOffCode, deviceTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_device);


        initView();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidateInput(new EditText[]{deviceName, deviceTimer, deviceTimer
                        , deviceOffCode, deviceOnCode, devicePhone
                })) {

                    boolean isSuccess = DatabaseOperation.saveDevice(AddDevice.this, new Device(deviceName.getText().toString(), 0,
                            Integer.parseInt(deviceOnCode.getText().toString()), Integer.parseInt(deviceOffCode.getText().toString()),
                            devicePhone.getText().toString(), 0, HourToMilli(Integer.parseInt(deviceTimer.getText().toString())),
                            Util.getLastTime()
                    ));

                    if (isSuccess) {
                        Toast.makeText(AddDevice.this, "Device Save Success!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddDevice.this, "Save not success!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }


    private void initView() {
        saveButton = findViewById(R.id.save_device);

        deviceName = findViewById(R.id.device_name);
        deviceName.setTag("Device Name");
        devicePhone = findViewById(R.id.phone_number);
        devicePhone.setTag("Phone Number");
        deviceOnCode = findViewById(R.id.on_code);
        deviceOnCode.setTag("Device On Code");
        deviceOffCode = findViewById(R.id.off_code);
        deviceOffCode.setTag("Device Off Code");
        deviceTimer = findViewById(R.id.timer);
        deviceTimer.setTag("Device Timer");
    }

    private long HourToMilli(int hour) {

        return ((hour * 60) * 60) * 1000;
    }

    private boolean ValidateInput(EditText[] editTexts) {

        int count = 0;

        for (EditText editText : editTexts) {

            if (editText.getText().length() == 0) {

                count++;
                Toast.makeText(this, editText.getTag().toString() + "can't be empty!", Toast.LENGTH_SHORT).show();

            }

        }

        return count == 0;

    }
}
