package com.example.develop.car_monit;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
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
import android.view.WindowManager;
import com.example.develop.car_monit.UdpService.ChatServer;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    static DrawView drawView;
    //UDP
    public static boolean getUdpFlag = false;
    public static Handler exHandler;
    static String udpReceive = "";

    //receive datagram packet's value by udp
    static String[] heading = new String[9];
    static String[] past_position = new String[780];
    static String[] current_position = new String[26];
    static String[] future_position = new String[780];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Remove notification bar and set screen orientation in landscape.
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //check the X, Y axis limit.
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        Log.d("Width", "x: " + width);
//        int height = size.y;
//        Log.d("Width", "y: " + height);

        //UDP check SDK
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        UDP server receiver start
        try {
            ChatServer chatserver = new ChatServer();
            chatserver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        exHandler = new UdpHandler(this);

        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Floating Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawView = (DrawView) findViewById(R.id.drawview);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Udp Handler
    public static class UdpHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        private UdpHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                super.handleMessage(msg);
                getUdpFlag = true;

                //divide receive data
                udpReceive = String.valueOf(msg.obj).trim();
                String[] udpReceiveArray = udpReceive.split(";");
                heading = Arrays.copyOfRange(udpReceiveArray, 0, 9);
                past_position = Arrays.copyOfRange(udpReceiveArray, 9, 789);
                current_position = Arrays.copyOfRange(udpReceiveArray, 789, 815);
                future_position = Arrays.copyOfRange(udpReceiveArray, 815, 1595);

                Log.i(TAG, "Now in UDP Receiver, original receive : " + udpReceiveArray.length);
                drawView.invalidate(); //Canvas drawable permission accept

                //Log for specific car's X,Y position from original receive data from udp.
//                int a = 0;
//                for (int i = 0; i < 780; i += 2) {
//                    if(a == 14)  Log.d(TAG, "[past]blue car pos.:" + past_position[i+1] + "," + past_position[i]);
//
//                    if (a == 24) {
//                        a = 0;
//                    } else {
//                        a += 2;
//                    }
//                }
//
//                for (int i = 0; i < 26; i += 2) {
//                    if(i == 14)  Log.d(TAG, "[current]blue car pos.:" + current_position[i+1] + "," + current_position[i]);
//                }
//
//                int b = 0;
//                for (int i = 0; i < 780; i += 2) {
//                    if(b == 14)  Log.d(TAG, "[future]blue car pos.:" + future_position[i+1] + "," + future_position[i]);
//
//                    if (b == 24) {
//                        b = 0;
//                    } else {
//                        b += 2;
//                    }
//                }
            }
        }
    }
}
