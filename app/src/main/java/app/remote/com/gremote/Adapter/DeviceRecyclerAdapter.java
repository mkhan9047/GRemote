package app.remote.com.gremote.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.remote.com.gremote.R;

public class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.DeviceHolder> {


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


        public DeviceHolder(View itemView) {
            super(itemView);
        }
    }
}
