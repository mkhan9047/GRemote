package app.remote.com.gremote.Adapter;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.helper.ItemTouchUIUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import app.remote.com.gremote.CustomClass.CustomSwitchCompact;
import app.remote.com.gremote.Database.DatabaseOperation;
import app.remote.com.gremote.Model.Device;
import app.remote.com.gremote.R;
import app.remote.com.gremote.Receiver.MyReceiver;
import app.remote.com.gremote.Util;

import static android.content.Context.ALARM_SERVICE;

public class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.DeviceHolder> {

    private List<Device> deviceList;
    private Context context;
    private boolean stop = false;

    public DeviceRecyclerAdapter(List<Device> deviceList, Context context) {
        this.deviceList = deviceList;
        this.context = context;
    }

    public void setNewData(List<Device> list) {
        deviceList = list;
    }

    @NonNull
    @Override
    public DeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_reycler_row, parent, false);

        return new DeviceHolder(view);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final DeviceHolder holder, int position) {

        holder.deviceName.setText(deviceList.get(position).getDeviceName());

        final int hold = position;
        final DeviceHolder bn = holder;

        if (deviceList.get(position).getCurrentStatus() == 1) {
            holder.currentButton.setImageResource(R.drawable.ic_has_ele);
        } else if (deviceList.get(position).getCurrentStatus() == 0) {
            holder.currentButton.setImageResource(R.drawable.ic_no_ele);
        }

        if (deviceList.get(position).getMotionStatus() == 1) {

            holder.sensorOnButton.setImageResource(R.drawable.ic_button_on);
            holder.sensorOffButton.setImageResource(R.drawable.ic_button_none);

        } else if (deviceList.get(position).getMotionStatus() == 0) {
            holder.sensorOffButton.setImageResource(R.drawable.ic_button_off);
            holder.sensorOnButton.setImageResource(R.drawable.ic_button_none);
        }

        if (!(deviceList.get(position).getDeviceOffTime().contains("Unknown"))) {
            holder.timer.setText(deviceList.get(position).getDeviceOffTime());
        } else {
            holder.timer.setText("Set Timer");
        }

        if (deviceList.get(position).getDeviceStatus() == 1) {

            // holder.timerSwitch.setChecked(true);
/*            holder.onButton.setImageResource(R.drawable.on_after);
            holder.offButton.setImageResource(R.drawable.off_before);*/

            holder.machineOnButton.setImageResource(R.drawable.ic_button_on);
            holder.machineOffButton.setImageResource(R.drawable.ic_button_none);

        } else if (deviceList.get(position).getDeviceStatus() == 0) {

            holder.machineOffButton.setImageResource(R.drawable.ic_button_off);
            holder.machineOnButton.setImageResource(R.drawable.ic_button_none);
            //holder.timerSwitch.setChecked(false);
/*            holder.onButton.setImageResource(R.drawable.on_before);
            holder.offButton.setImageResource(R.drawable.off_after);
          ;*/
        }

        // holder.timer.setText(String.format("%d hour %d min", TimerData.get("hour"), TimerData.get("min")));

        holder.lastOffTime.setText("Last off time: " + deviceList.get(position).getLastOffTime());

    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    class DeviceHolder extends RecyclerView.ViewHolder {

        TextView deviceName, timer, lastOffTime;
        ImageButton machineOnButton, machineOffButton, sensorOnButton, sensorOffButton;
        ImageButton timerButton, currentButton;

        @SuppressLint("ClickableViewAccessibility")
        public DeviceHolder(View itemView) {
            super(itemView);

            deviceName = itemView.findViewById(R.id.device_name);
            lastOffTime = itemView.findViewById(R.id.last_off_time);
            machineOffButton = itemView.findViewById(R.id.btn_off_machine);
            machineOnButton = itemView.findViewById(R.id.btn_on_machine);
            sensorOffButton = itemView.findViewById(R.id.btn_off_sensor);
            sensorOnButton = itemView.findViewById(R.id.btn_on_sensor);
            timerButton = itemView.findViewById(R.id.btn_set_time);
            currentButton = itemView.findViewById(R.id.btn_current_status);

            timer = itemView.findViewById(R.id.tv_timer);


            machineOnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (deviceList.get(getAdapterPosition()).getDeviceStatus() == 1) {
                        Toast.makeText(context, deviceList.get(getAdapterPosition()).getDeviceName() + " Is ON! Can't process the request!", Toast.LENGTH_SHORT).show();
                    } else {
                        // deviceSwitch.setChecked(false);
                        Toast.makeText(context, "Machine On Request is sent to " + deviceList.get(getAdapterPosition()).getDeviceName(), Toast.LENGTH_SHORT).show();
                        Util.sendSms(deviceList.get(getAdapterPosition()).getPhoneNumber(), String.valueOf(deviceList.get(getAdapterPosition()).getOnCode()));

                    }
                }

            });


            machineOffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (deviceList.get(getAdapterPosition()).getDeviceStatus() == 0) {
                        Toast.makeText(context, deviceList.get(getAdapterPosition()).getDeviceName() + " Is OFF! Can't process the request!", Toast.LENGTH_SHORT).show();
                    } else {
                        // deviceSwitch.setChecked(false);
                        Toast.makeText(context, "Machine OFF Request is sent to " + deviceList.get(getAdapterPosition()).getDeviceName(), Toast.LENGTH_SHORT).show();
                        Util.sendSms(deviceList.get(getAdapterPosition()).getPhoneNumber(), String.valueOf(deviceList.get(getAdapterPosition()).getOffCode()));

                    }
                }
            });

            sensorOnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (deviceList.get(getAdapterPosition()).getMotionStatus() == 1) {
                        Toast.makeText(context, deviceList.get(getAdapterPosition()).getDeviceName() + "Sensor Is ON! Can't process the request!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Sensor On Request is sent to " + deviceList.get(getAdapterPosition()).getDeviceName(), Toast.LENGTH_SHORT).show();
                        Util.sendSms(deviceList.get(getAdapterPosition()).getPhoneNumber(), String.valueOf(deviceList.get(getAdapterPosition()).getSensorOnCode()));
                    }
                }
            });

            sensorOffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (deviceList.get(getAdapterPosition()).getMotionStatus() == 0) {
                        Toast.makeText(context, deviceList.get(getAdapterPosition()).getDeviceName() + "Sensor Is OFF! Can't process the request!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Sensor On Request is sent to " + deviceList.get(getAdapterPosition()).getDeviceName(), Toast.LENGTH_SHORT).show();
                        Util.sendSms(deviceList.get(getAdapterPosition()).getPhoneNumber(), String.valueOf(deviceList.get(getAdapterPosition()).getSensorOffCode()));
                    }
                }
            });


            currentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Current Check Request is sent to " + deviceList.get(getAdapterPosition()).getDeviceName(), Toast.LENGTH_SHORT).show();
                    Util.sendSms(deviceList.get(getAdapterPosition()).getPhoneNumber(), String.valueOf(deviceList.get(getAdapterPosition()).getCurrentCheckCode()));
                }
            });


            timerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @SuppressLint("SimpleDateFormat")
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY, selectedHour);
                            cal.set(Calendar.MINUTE, selectedMinute);
                            Format formatter;
                            formatter = new SimpleDateFormat("h:mm a");
                            if(timer.getText().toString().contains("Set Timer")){
                                if (cal.getTimeInMillis() > System.currentTimeMillis()) {
                                    boolean isSuccess = DatabaseOperation.UpdateDeviceOffTime(context, formatter.format(cal.getTime()), deviceList.get(getAdapterPosition()).getPhoneNumber());
                                    if (isSuccess) {
                                        setAlarmTime(cal.getTimeInMillis());
                                        deviceList = DatabaseOperation.getDevice(context);
                                        notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(context, "Timer set not success!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, "You can't set time less than current time!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(context, "Timer Already Set!", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    if(context != null)
                    mTimePicker.show();
                }
            });


        }

        private void setAlarmTime(long afterMilliseconds) {
            AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(context, MyReceiver.class);
            intent.putExtra("phone",  deviceList.get(getAdapterPosition()).getPhoneNumber());
            intent.putExtra("off_code",  deviceList.get(getAdapterPosition()).getOffCode());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)System.currentTimeMillis(), intent, 0);
            if (manager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    manager.setExact(AlarmManager.RTC_WAKEUP, afterMilliseconds, pendingIntent);
                }else {
                    manager.set(AlarmManager.RTC_WAKEUP, afterMilliseconds, pendingIntent);
                }
            }
            // Toast.makeText(this, "Alarm Set Success!", Toast.LENGTH_SHORT).show();
        }
    }
}
