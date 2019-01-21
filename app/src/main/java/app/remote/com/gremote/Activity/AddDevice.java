package app.remote.com.gremote.Activity;

import android.content.Intent;
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
    EditText deviceName, devicePhone, deviceOnCode, deviceOffCode;
    EditText sensorOnCode, sensorOffCode, currentCheckCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_device);


        initView();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidateInput(new EditText[]{deviceName
                        , deviceOffCode, deviceOnCode, devicePhone
                })) {

                    boolean isSuccess = DatabaseOperation.saveDevice(AddDevice.this, new Device(deviceName.getText().toString(),
                            "+88" + devicePhone.getText().toString(),
                            Integer.parseInt(deviceOnCode.getText().toString()),
                            Integer.parseInt(deviceOffCode.getText().toString()),
                            Integer.parseInt(sensorOnCode.getText().toString()),
                            Integer.parseInt(sensorOffCode.getText().toString()),
                            Integer.parseInt(currentCheckCode.getText().toString()),
                            0, 0, "Unknown", 0, "Unknown"
                    ));

                    if (isSuccess) {

                        Toast.makeText(AddDevice.this, "Device Save Success!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddDevice.this, Dashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(AddDevice.this, "Save not success!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }


    private void initView() {
        saveButton = findViewById(R.id.save_device);

        sensorOffCode = findViewById(R.id.sensor_off_code);
        sensorOffCode.setTag("Sensor Off Code");
        sensorOnCode = findViewById(R.id.sensor_on_code);
        sensorOnCode.setTag("Sensor On Code");
        currentCheckCode = findViewById(R.id.current_code);
        currentCheckCode.setTag("Current Check Code");
        deviceName = findViewById(R.id.device_name);
        deviceName.setTag("Device Name");
        devicePhone = findViewById(R.id.phone_number);
        devicePhone.setTag("Phone Number");
        deviceOnCode = findViewById(R.id.on_code);
        deviceOnCode.setTag("Device On Code");
        deviceOffCode = findViewById(R.id.off_code);
        deviceOffCode.setTag("Device Off Code");
        // deviceTimer = findViewById(R.id.timer);
        //deviceTimer.setTag("Device Timer");
    }

    private long HourToMilli(int hour) {

        return ((hour * 60) * 60) * 1000;
    }

    private boolean ValidateInput(EditText[] editTexts) {

        int count = 0;

        for (EditText editText : editTexts) {

            if (editText.getText().length() == 0) {

                count++;

                Toast.makeText(this, editText.getTag().toString() + " can't be empty!", Toast.LENGTH_SHORT).show();

                break;

            }

        }

        return count == 0;

    }

    public void OnBack(View view) {
        finish();
    }
}
