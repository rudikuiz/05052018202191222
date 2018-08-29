package metis.winwin.Firebase;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import metis.winwin.R;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.SessionManager;

/**
 * Created by Lenovo on 8/12/2018.
 */

public class UpdateServiceV2 extends Service implements LocationListener {

    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 1000;
    private SessionManager sessionManager;
    private RequestQueue requestQueue;
    private final int NOTIFICATION_ID = 1479;


    public UpdateServiceV2() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        sessionManager = new SessionManager(this);
        requestQueue = Volley.newRequestQueue(this);
        mTimer = new Timer();
        mTimer.schedule(new TimerTaskToGetLocation(), 5, notify_interval);


        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.content_notifiaction);

        //Log.i(LOG_TAG, "Received Start Foreground Intent ");
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                notificationIntent, 0);

//        Bitmap icon = BitmapFactory.decodeResource(getResources(),
//                R.drawable.ic_transparent);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("")
                .setTicker("")
                .setContentText("")
                .setContent(remoteViews)
                .setSmallIcon(R.drawable.ic_transparent)
                .setOngoing(true).build();

        startForeground(NOTIFICATION_ID,
                notification);


        return START_STICKY;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void fn_getlocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);

            if (!isGPSEnable && !isNetworkEnable) {

            } else {

                AndLog.ShowLog("RSPS", "Get Location");


                if (isNetworkEnable) {
                    location = null;
                    if (locationManager != null) {
//                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        location = getLastKnownLocation();
                        if (location != null) {

                            Log.e("latitude", location.getLatitude() + "");
                            Log.e("longitude", location.getLongitude() + "");

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            sessionManager.setLat(String.valueOf(location.getLatitude()));
                            sessionManager.setLng(String.valueOf(location.getLongitude()));
                            fn_update();

                        }
                    }


                }


                if (isGPSEnable) {
                    location = null;
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                    if (locationManager != null) {
//                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        location = getLastKnownLocation();
                        if (location != null) {
                            Log.e("latitude", location.getLatitude() + "");
                            Log.e("longitude", location.getLongitude() + "");
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            sessionManager.setLat(String.valueOf(location.getLatitude()));
                            sessionManager.setLng(String.valueOf(location.getLongitude()));
                            fn_update();
                        }

                    }

                }
            }


        }

    }

    private Location getLastKnownLocation() {
        Location bestLocation = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            List<String> providers = locationManager.getProviders(true);

            for (String provider : providers) {

                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() > bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            }
        }

        return bestLocation;
    }

    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    fn_getlocation();
                }
            });

        }
    }

    private void fn_update() {

        final SessionManager sess = new SessionManager(getApplicationContext());
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConf.UPDATELOKIASI, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                AndLog.ShowLog("RSPS", response + " ;; Lat : " + sess.getLat() + " ;; Lng : " + sess.getLng());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("cli_id", sess.getIdhq());
                params.put("latitude", sess.getLat());
                params.put("longitude", sess.getLng());

                return params;
            }

        };
        requestQueue.add(strReq);
    }


}