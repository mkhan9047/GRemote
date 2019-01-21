package app.remote.com.gremote.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import app.remote.com.gremote.Model.History;
import app.remote.com.gremote.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private List<History> historyList;
    private Context context;

    public HistoryAdapter(List<History> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recycler_row, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {

        if (historyList.get(position).getStatus().equals("ON")) {
            holder.statusImage.setImageResource(R.drawable.on_after);
        } else if (historyList.get(position).getStatus().equals("OFF")) {
            holder.statusImage.setImageResource(R.drawable.off_after);
        }

        holder.deviceName.setText(historyList.get(position).getDevice_name());

        holder.time.setText(historyList.get(position).getTime());

        holder.date.setText(historyList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class HistoryHolder extends RecyclerView.ViewHolder {

        TextView deviceName, time, date;
        ImageView statusImage;

        public HistoryHolder(View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.device_name);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            statusImage = itemView.findViewById(R.id.status_img);
        }
    }
}
