package app.remote.com.gremote.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import app.remote.com.gremote.Adapter.HistoryAdapter;
import app.remote.com.gremote.Database.DatabaseOperation;
import app.remote.com.gremote.R;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView historyRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);

        historyRecycler = findViewById(R.id.history_recycler);

        historyRecycler.setHasFixedSize(true);

        historyRecycler.setLayoutManager(new LinearLayoutManager(this));

        HistoryAdapter adapter = new HistoryAdapter(DatabaseOperation.getHistory(this), this);

        historyRecycler.setAdapter(adapter);
    }

    public void OnBack(View view) {

        finish();
    }
}
