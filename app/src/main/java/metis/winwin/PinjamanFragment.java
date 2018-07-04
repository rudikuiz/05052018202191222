package metis.winwin;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.DateTool;
import metis.winwin.Utils.DecimalsFormat;
import metis.winwin.Utils.GlobalToast;
import metis.winwin.Utils.SessionManager;
import metis.winwin.Utils.VolleyHttp;

import static android.content.Context.MODE_PRIVATE;
import static metis.winwin.Utils.AppConf.GET_MAX_PINJAMAN;
import static metis.winwin.Utils.AppConf.JUMLAH_PINJAMAN;


/**
 * A simple {@link Fragment} subclass.
 */
public class PinjamanFragment extends Fragment {

    @Bind(R.id.sbJumlah)
    SeekBar sbJumlah;
    @Bind(R.id.sbPeriode)
    SeekBar sbPeriode;
    @Bind(R.id.txJumlah)
    TextView txJumlah;
    @Bind(R.id.txBunga)
    TextView txBunga;
    @Bind(R.id.txTanggal)
    TextView txTanggal;
    @Bind(R.id.txTotal)
    TextView txTotal;
    @Bind(R.id.txBungaPersen)
    TextView txBungaPersen;
    @Bind(R.id.btPinjam)
    Button btPinjam;

    private final double jumlah = 500000;
    private final double kelipatan = 100000;
    @Bind(R.id.txPinjaman)
    TextView txPinjaman;
    @Bind(R.id.txPeriode)
    TextView txPeriode;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.btLogin)
    Button btLogin;
    @Bind(R.id.linF)
    Button linF;

    private double sJumlah, total_byr, sBunga;
    private int sPeriode;
    private String tglSkg, maxClient;
    private FragmentActivity mActivity;
    StringRequest stringRequest;
    private SessionManager sessionManager;
    private Activity activity;
    RequestQueue requestQueue;
    private int max, val, batas;
    private boolean isFisrt;
    String rating;

    public PinjamanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pinjaman, container, false);
        ButterKnife.bind(this, view);
        activity = getActivity();
        requestQueue = Volley.newRequestQueue(getContext());
        sessionManager = new SessionManager(activity);

        isFisrt = false;

        mActivity = getActivity();
        sJumlah = jumlah;
        sPeriode = 10;
        total_byr = 549500;
        sBunga = 0.99;
        max = 2000000;
        batas = (max - 500000) / 100000;

//        CheckDisetujui();
//        getMaxPinjaman();
//        setSbJumlah();

        sbJumlah.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if (!sessionManager.isLoggedIn()) {
                    if (i > 15) {
                        sbJumlah.setProgress(15);
                        if (!isFisrt) {
                            Toast.makeText(mActivity, "Khusus repeat customer", Toast.LENGTH_SHORT).show();
                            isFisrt = true;
                        }
                    }

                } else {


                    if (i > batas) {
                        sbJumlah.setProgress(batas);
                        if (!isFisrt) {
                            Toast.makeText(getContext(), "Maaf, Maksimal " + DecimalsFormat.priceWithoutDecimal(String.valueOf(max)), Toast.LENGTH_SHORT).show();
                            isFisrt = true;
                        }
                    }
                }

                hitungUlang();

//                LAWAAS DIATAS
//                YANG BARU DIBAWAH

//                if (!sessionManager.isLoggedIn()) {
//                    if (i > 15) {
//                        sbJumlah.setProgress(15);
//                        if (!isFisrt) {
//                            Toast.makeText(mActivity, "Khusus repeat customer", Toast.LENGTH_SHORT).show();
//                            isFisrt = true;
//                        }
//                    }
//
//                } else {
//                    if (maxClient.contains("Maksimal 2 Juta")) {
//                        if (i > 15) {
//                            sbJumlah.setProgress(15);
//                            if (!isFisrt) {
//                                Toast.makeText(getContext(), "Sorry, Maksimal 2 Juta", Toast.LENGTH_SHORT).show();
//                                isFisrt = true;
//                            }
//                        }
//                    }
//
//                    if (maxClient.contains("Maksimal 3 Juta")) {
//                        if (i > 25) {
//                            sbJumlah.setProgress(25);
//                            if (!isFisrt) {
//                                Toast.makeText(getContext(), "Sorry, Maksimal 3 Juta", Toast.LENGTH_SHORT).show();
//                                isFisrt = true;
//                            }
//
//                        }
//
//                    }
//
//                    if (maxClient.contains("Maksimal 4 Juta")) {
//                        if (i > 35) {
//                            sbJumlah.setProgress(35);
//                            if (!isFisrt) {
//                                Toast.makeText(getContext(), "Sorry, Maksimal 4 Juta", Toast.LENGTH_SHORT).show();
//                                isFisrt = true;
//                            }
//                        }
//                    }
//
//                    if (maxClient.contains("Maksimal 5 Juta")) {
//                        if (i > 45) {
//                            sbJumlah.setProgress(45);
//                        }
//
//                    }
//
//                }

            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                AndLog.ShowLog("STOPP", sbJumlah.getProgress() + " x");

            }
        });

        sbPeriode.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                sPeriode = i + 10;
                txPeriode.setText(sPeriode + " Hari");

                hitungUlang();
                ConvertTgl(sPeriode);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        tglSkg = df.format(c.getTime());

        ConvertTgl(10);

        text.setText("Informasi Kontak :\n" + "admin@pinjamwinwin.com \n" +
                "Telpon : 031-5677624\n" +
                "WA : 0813-3147-9537");
        return view;
    }

    private void getMaxPinjaman() {

        String idclient = new SessionManager(mActivity).getIduser();
        stringRequest = new StringRequest(Request.Method.GET, GET_MAX_PINJAMAN + idclient, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                maxClient = response;
                Log.d("asssd", maxClient);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }

    private void setSbJumlah() {
        String idclient = new SessionManager(mActivity).getIduser();

        stringRequest = new StringRequest(Request.Method.GET, JUMLAH_PINJAMAN + idclient, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jos = new JSONObject(response);
                    val = jos.getInt("jml");
                    if (val == 0) {
                        maxClient = "Maksimal 2 Juta";
                        sBunga = 0.99;
                    } else if (val == 1) {
                        maxClient = "Maksimal 2 Juta";
                        sBunga = 0.99;
                    } else if (val == 2) {
                        maxClient = "Maksimal 2 Juta";
                        sBunga = 0.79;
                    } else if (val == 3) {
                        maxClient = "Maksimal 2 Juta";
                        sBunga = 0.59;
                    } else if (val == 4) {
                        maxClient = "Maksimal 3 Juta";
                        sBunga = 0.59;
                    } else if (val == 5) {
                        maxClient = "Maksimal 3 Juta";
                        sBunga = 0.59;
                    } else if (val == 6) {
                        maxClient = "Maksimal 4 Juta";
                        sBunga = 0.59;
                    } else if (val == 7) {
                        maxClient = "Maksimal 4 Juta";
                        sBunga = 0.59;
                    } else if (val == 8) {
                        maxClient = "Maksimal 4 Juta";
                        sBunga = 0.59;
                    } else if (val == 9) {
                        maxClient = "Maksimal 5 Juta";
                        sBunga = 0.59;
                    }

                    txBungaPersen.setText("( Bunga " + sBunga + " % / Hari )");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }

    private void ConvertTgl(int adds) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date myDate = dateFormat.parse(tglSkg);
            String tgl = dateFormat.format(DateTool.addDays(myDate, adds));
            txTanggal.setText(DateTool.changeFormat(tgl, "yyyy-MM-dd", "dd MMM yyyy"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.txTanggal, R.id.btPinjam, R.id.btLogin, R.id.linF})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txTanggal:
                break;
            case R.id.btPinjam:
                if (sessionManager.checkLogin()){
                    String ratingNow = sessionManager.getRating();
                    if (ratingNow.equals("5")){
                        GlobalToast.ShowToast(mActivity, "Maaf, Akun anda telah berada pada Rating 5, Anda tidak bisa mengajukan pinjaman. Segera Hubungi Admin Winwin");
                    }else if (ratingNow.equals("4")){
                        GlobalToast.ShowToast(mActivity, "Maaf, Akun anda telah berada pada Rating 4, Anda tidak bisa mengajukan pinjaman selama 90 Hari. Bila ada yang belum jelas bisa menghubungi Admin Winwin");
                    } else{
                        Intent formPengajuan = new Intent(getActivity(), FormPengajuan.class);
                        formPengajuan.putExtra("jumlah", sJumlah + "");
                        formPengajuan.putExtra("periode", sPeriode + "");
                        formPengajuan.putExtra("jatuh_tempo", txTanggal.getText().toString());
                        formPengajuan.putExtra("total_byr", total_byr + "");
                        startActivity(formPengajuan);
                    }
                }else {
                    Intent formPengajuan = new Intent(getActivity(), FormPengajuan.class);
                    formPengajuan.putExtra("jumlah", sJumlah + "");
                    formPengajuan.putExtra("periode", sPeriode + "");
                    formPengajuan.putExtra("jatuh_tempo", txTanggal.getText().toString());
                    formPengajuan.putExtra("total_byr", total_byr + "");
                    startActivity(formPengajuan);
                }


                break;
            case R.id.btLogin:
                startActivity(new Intent(mActivity, Login.class));
                break;
            case R.id.linF:
                startActivity(new Intent(mActivity, ForgotPassword.class));
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        sBunga = 0.99;

        SessionManager sessionManager = new SessionManager(mActivity);
        if (sessionManager.checkLogin()) {
            String rate = sessionManager.getRate();
            String max_pinjam = sessionManager.getMaxpinjam();
            if (rate != null) {
                sBunga = Double.parseDouble(rate);
            }

            if (max_pinjam != null) {
                max = Integer.parseInt(max_pinjam);
            } else {
                max = 2000000;
            }



            CheckDisetujui();
            btLogin.setVisibility(View.GONE);
            linF.setVisibility(View.GONE);
        }else {
            linF.setVisibility(View.VISIBLE);
        }

        batas = (max - 500000) / 100000;
        txBungaPersen.setText("( Bunga " + sBunga + " % / Hari )");
        hitungUlang();

    }

    private void CheckDisetujui() {

        final SessionManager sess = new SessionManager(mActivity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConf.URL_CHECKDISETUJUI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jo = new JSONObject(response);


                    if (jo.has("error")) {

                        String error = jo.getString("error");

                        if (error.equals("false")) {

                            if (jo.has("rate")) {

                                String rate = jo.getString("rate");
                                sBunga = Double.parseDouble(rate);
                                String max1 = jo.getString("max");
                                sess.setRate(rate);
                                sess.setMax(max1);


                                String max_pinjam = jo.getString("max_pinjam");
                                rating = jo.getString("rating");
                                String total_setujui = jo.getString("pinjaman");

                                sess.setMaxpinjam(max_pinjam);
                                sess.setRating(rating);
                                sess.setTotalSetujui(total_setujui);

                                max = Integer.parseInt(max_pinjam);
                                batas = (max - 500000) / 100000;

                                txBungaPersen.setText("( Bunga " + sBunga + " % / Hari )");

                                hitungUlang();


                                Log.d("dddrating", rating);
                                Log.d("ddMax", String.valueOf(max));
                            }
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String id_user = new SessionManager(mActivity).getIdhq();
                AndLog.ShowLog("hashas", id_user);
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id_user);

                return params;
            }
        };

        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(mActivity).addToRequestQueue(stringRequest);

    }

    public void hitungUlang() {
        double total = (double) sbJumlah.getProgress() * kelipatan + jumlah;

        txPinjaman.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(total + ""));
        txJumlah.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(total + ""));

        sJumlah = total;

        total_byr = sJumlah * Math.pow((1 + (sBunga / 100)), (double) sPeriode);
        total_byr = Math.round(total_byr);

        double bunga = total_byr - sJumlah;
        txBunga.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(bunga + ""));
        txTotal.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(total_byr + ""));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        VolleyHttp.getInstance(activity).cancelPendingRequests(AppConf.httpTag);
    }
}
