package app.remote.com.gremote.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import app.remote.com.gremote.Model.Device;
import app.remote.com.gremote.R;

public class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.DeviceHolder> {

    List<Device> deviceList;
    Context context;

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

    @Override
    public void onBindViewHolder(@NonNull DeviceHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class DeviceHolder extends RecyclerView.ViewHolder {

        TextView deviceName, status, lastOffTime, timer;
        ImageButton onButton, offButton;
        Switch motionSwitch;


        public DeviceHolder(View itemView) {
            super(itemView);


        }
    }
}
