package app.remote.com.gremote.Adapter;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import app.remote.com.gremote.CustomClass.CustomSwitchCompact;
import app.remote.com.gremote.Database.DatabaseOperation;
import app.remote.com.gremote.Model.Device;
import app.remote.com.gremote.R;
import app.remote.com.gremote.Util;

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
            holder.iv_current_status.setImageResource(R.drawable.ic_has_ele);
        } else if (deviceList.get(position).getCurrentStatus() == 0) {
            holder.iv_current_status.setImageResource(R.drawable.ic_no_ele);
        }

        if (deviceList.get(position).getMotionStatus() == 1) {

            holder.motionSwitch.setChecked(true);

        } else if (deviceList.get(position).getMotionStatus() == 0) {

            holder.motionSwitch.setChecked(false);
        }




        if (deviceList.get(position).getDeviceStatus() == 1) {
            holder.status.setText("Status: " + "On");
            // holder.timerSwitch.setChecked(true);
/*            holder.onButton.setImageResource(R.drawable.on_after);
            holder.offButton.setImageResource(R.drawable.off_before);*/
            holder.deviceSwitch.setTag("TAG");
            holder.deviceSwitch.setChecked(true);

        } else if (deviceList.get(position).getDeviceStatus() == 0) {

            holder.deviceSwitch.setChecked(false);
            holder.status.setText("Status: " + "Off");
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

        TextView deviceName, status, lastOffTime;
        // ImageButton onButton, offButton;
        Button checkCurrent;
        Switch deviceSwitch;
        CustomSwitchCompact motionSwitch;
        ImageView iv_current_status;

        @SuppressLint("ClickableViewAccessibility")
        public DeviceHolder(View itemView) {
            super(itemView);

            deviceName = itemView.findViewById(R.id.device_name);
            lastOffTime = itemView.findViewById(R.id.last_off_time);
            status = itemView.findViewById(R.id.status);
            // onButton = itemView.findViewById(R.id.on_button);
            // offButton = itemView.findViewById(R.id.off_button);
            checkCurrent = itemView.findViewById(R.id.btn_check_current);
            deviceSwitch = itemView.findViewById(R.id.device_switch);
            motionSwitch =  itemView.findViewById(R.id.motion_switch);
            iv_current_status = itemView.findViewById(R.id.iv_current_status);

            checkCurrent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Current Check Request is sent to " + deviceList.get(getAdapterPosition()).getDeviceName(), Toast.LENGTH_SHORT).show();
                    Util.sendSms(deviceList.get(getAdapterPosition()).getPhoneNumber(), String.valueOf(deviceList.get(getAdapterPosition()).getCurrentCheckCode()));
                }
            });

            motionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        if (deviceList.get(getAdapterPosition()).getMotionStatus() == 1) {
                            Toast.makeText(context, deviceList.get(getAdapterPosition()).getDeviceName() + "Sensor Is ON! Can't process the request!", Toast.LENGTH_SHORT).show();
                        } else {
                            // deviceSwitch.setChecked(false);*/
                            Toast.makeText(context, "Sensor On Request is sent to " + deviceList.get(getAdapterPosition()).getDeviceName(), Toast.LENGTH_SHORT).show();
                            Util.sendSms(deviceList.get(getAdapterPosition()).getPhoneNumber(), String.valueOf(deviceList.get(getAdapterPosition()).getSensorOnCode()));
                        }

                    } else {

                        Toast.makeText(context, "Sensor Off Request is sent to " + deviceList.get(getAdapterPosition()).getDeviceName(), Toast.LENGTH_SHORT).show();
                        Util.sendSms(deviceList.get(getAdapterPosition()).getPhoneNumber(), String.valueOf(deviceList.get(getAdapterPosition()).getSensorOffCode()));


                    }
                }
            });




            deviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (deviceSwitch.getTag() != null) {
                        deviceSwitch.setTag(null);
                        return;
                    }

                    if (b) {
                        if (deviceList.get(getAdapterPosition()).getDeviceStatus() == 1) {
                            Toast.makeText(context, deviceList.get(getAdapterPosition()).getDeviceName() + " Is ON! Can't process the request!", Toast.LENGTH_SHORT).show();
                        } else {
                            // deviceSwitch.setChecked(false);
                            Toast.makeText(context, "Machine On Request is sent to " + deviceList.get(getAdapterPosition()).getDeviceName(), Toast.LENGTH_SHORT).show();
                            Util.sendSms(deviceList.get(getAdapterPosition()).getPhoneNumber(), String.valueOf(deviceList.get(getAdapterPosition()).getOnCode()));

                        }
                    } else {
                        //deviceSwitch.setChecked(true);
                        Toast.makeText(context, "Machine Off Request is sent to " + deviceList.get(getAdapterPosition()).getDeviceName(), Toast.LENGTH_SHORT).show();
                        Util.sendSms(deviceList.get(getAdapterPosition()).getPhoneNumber(), String.valueOf(deviceList.get(getAdapterPosition()).getOffCode()));
                    }
                }
            });

            deviceSwitch.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    deviceSwitch.setTag(null);
                    return false;
                }
            });

        }
    }
}
