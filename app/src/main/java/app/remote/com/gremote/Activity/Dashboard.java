package app.remote.com.gremote.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import app.remote.com.gremote.Adapter.DeviceRecyclerAdapter;
import app.remote.com.gremote.Database.DatabaseOperation;
import app.remote.com.gremote.Model.History;
import app.remote.com.gremote.R;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static RecyclerView deviceRecyler;
    DeviceRecyclerAdapter adapter;
    Button addDevice;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.black));

        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_dashboard);

        addDevice = findViewById(R.id.btn_add_device);

        deviceRecyler = findViewById(R.id.device_recyler);

        deviceRecyler.setLayoutManager(new LinearLayoutManager(this));

        if (DatabaseOperation.getDevice(this).size() == 0) {

            addDevice.setVisibility(View.VISIBLE);

        } else {

            addDevice.setVisibility(View.GONE);
        }

        adapter = new DeviceRecyclerAdapter(DatabaseOperation.getDevice(this), this);

        deviceRecyler.setAdapter(adapter);

        Toast.makeText(this, String.valueOf(DatabaseOperation.getDevice(this).size()), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_dashboard);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            Intent intent = new Intent(this, AddDevice.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void receivedSms(String message) {
        Toast.makeText(this, "sms " + message, Toast.LENGTH_SHORT).show();
    }

    public void onAdd(View view) {
        Intent intent = new Intent(this, AddDevice.class);
        startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
