package metis.winwin.Firebase;

/**
 * Created by Tambora on 06/10/2016.
 */

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

import metis.winwin.R;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.SessionManager;

public class UpdateService extends Service {
    private static final String LOG_TAG = "ForegroundService";
    private Handler handler = new Handler();
    private final int NOTIFICATION_ID = 1479;
    private final int DELAY = 5000;
    private FusedLocationProviderClient mFusedLocationClient;
    private SessionManager sessionManager;
    private RequestQueue requestQueue;
    private boolean checkloc;
    private LocationManager locationManager;
    private int gpsFails = 0;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        sessionManager = new SessionManager(this);
        requestQueue = Volley.newRequestQueue(this);
        gpsFails = 0;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        checkloc = false;

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


        handler.post(updateData);

        return START_STICKY;
    }


    @SuppressLint("MissingPermission")
    private void CheckLocation() {

        checkloc = true;

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    // Logic to handle location object
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();

                    String slat = String.valueOf(lat);
                    String slang = String.valueOf(lng);

                    if (sessionManager != null) {
                        if (sessionManager.getIdhq() != null) {

                            sessionManager.setLat(slat);
                            sessionManager.setLng(slang);

                            UpdateLokasi();
                        }
                    }


                }

            }
        });
    }

    private void UpdateLokasi() {

        final SessionManager sess = new SessionManager(getApplicationContext());
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConf.UPDATELOKIASI, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                checkloc = false;
                gpsFails = 0;

                AndLog.ShowLog("RSPS", response + " ;; Lat : " + sess.getLat() + " ;; Lng : " + sess.getLng());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                checkloc = false;
                gpsFails = 0;


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

    public Runnable updateData = new Runnable() {
        @Override
        public void run() {


            if (sessionManager != null) {
                if (sessionManager.getIdhq() != null) {

                    if (gpsFails > 10) {
                        gpsFails = 0;
                        checkloc = false;
                    } else {
                        gpsFails++;
                    }

                    AndLog.ShowLog("RSPS", "RUNNING "+checkloc+" "+gpsFails);


                    if (!checkloc) {
                        boolean GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        if (GpsStatus) {


                            CheckLocation();
                        } else {
                            gpsFails = 0;
                            checkloc = false;
                            AndLog.ShowLog("RSPS", "GPS MATI "+checkloc+" "+gpsFails);

                        }
                    }
                }
            }

            handler.postDelayed(updateData, DELAY);

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }
}