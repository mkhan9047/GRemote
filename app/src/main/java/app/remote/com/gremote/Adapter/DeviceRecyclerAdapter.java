package app.remote.com.gremote.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchUIUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import app.remote.com.gremote.Model.Device;
import app.remote.com.gremote.R;
import app.remote.com.gremote.Util;

public class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.DeviceHolder> {

    private List<Device> deviceList;
    private Context context;

    public DeviceRecyclerAdapter(List<Device> deviceList, Context context) {
        this.deviceList = deviceList;
        this.context = context;
    }

    @NonNull
    @Override
    public DeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_reycler_row, parent, false);

        return new DeviceHolder(view);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull  DeviceHolder holder, int position) {

        holder.deviceName.setText(deviceList.get(position).getDeviceName());

        HashMap<String, Integer> TimerData = new HashMap<>();
        TimerData = Util.getTimeFromMillisecond(deviceList.get(position).getTimerInMilli());

        final int hold = position;
        final DeviceHolder bn = holder;
        holder.CountDowntimer = new CountDownTimer(deviceList.get(position).getTimerInMilli(), 1000) {
            @Override
            public void onTick(long l) {
              /*  HashMap<String, Integer> TimerData = Util.getTimeFromMillisecond(deviceList.get(hold).getTimerInMilli());
                holder.timer.setText(String.format("%d hour %d min", TimerData.get("hour"), TimerData.get("min")));*/

                Log.e("mim", String .valueOf( 1 + 1));

                SetICOTimeOut(Util.getTimeFromMillisecond(deviceList.get(hold).getTimerInMilli() - 1000), bn.timer);
            }

            @Override
            public void onFinish() {

            }
        }.start();

        if (deviceList.get(position).getMotionStatus() == 1) {
            holder.motionSwitch.setChecked(true);
        } else if (deviceList.get(position).getMotionStatus() == 0) {
            holder.motionSwitch.setChecked(false);
        }

        if (deviceList.get(position).getDeviceStatus() == 1) {
            holder.status.setText("Status: " + "On");
            holder.onButton.setImageResource(R.drawable.on_after);
            holder.offButton.setImageResource(R.drawable.off_before);
        } else if (deviceList.get(position).getDeviceStatus() == 0) {
            holder.onButton.setImageResource(R.drawable.on_before);
            holder.offButton.setImageResource(R.drawable.off_after);
            holder.status.setText("Status: " + "Off");
        }


        // holder.timer.setText(String.format("%d hour %d min", TimerData.get("hour"), TimerData.get("min")));

        holder.lastOffTime.setText("Last off time: " + deviceList.get(position).getLastOffTime());

    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    class DeviceHolder extends RecyclerView.ViewHolder {

        TextView deviceName, status, lastOffTime, timer;
        ImageButton onButton, offButton;
        Switch motionSwitch;
        CountDownTimer CountDowntimer;

        public DeviceHolder(View itemView) {
            super(itemView);

            deviceName = itemView.findViewById(R.id.device_name);
            lastOffTime = itemView.findViewById(R.id.last_off_time);
            timer = itemView.findViewById(R.id.timer);
            status = itemView.findViewById(R.id.status);
            onButton = itemView.findViewById(R.id.on_button);
            offButton = itemView.findViewById(R.id.off_button);
            motionSwitch = itemView.findViewById(R.id.motion_switch);

            onButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButton.setImageResource(R.drawable.on_after);
                    offButton.setImageResource(R.drawable.off_before);
                    Util.sendSms(deviceList.get(getAdapterPosition()).getPhoneNumber(), String.valueOf(deviceList.get(getAdapterPosition()).getOnCode()));
                }
            });

            offButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButton.setImageResource(R.drawable.on_before);
                    offButton.setImageResource(R.drawable.off_after);
                    Util.sendSms(deviceList.get(getAdapterPosition()).getPhoneNumber(), String.valueOf(deviceList.get(getAdapterPosition()).getOffCode()));
                }
            });
        }
    }

    @SuppressLint("DefaultLocale")
    private void SetICOTimeOut(HashMap<String, Integer> timeFromMillisecond, TextView remain) {
        remain.setText(String.format("%d hours %d min",
                timeFromMillisecond.get("hour"),
                timeFromMillisecond.get("min")));
    }
}
