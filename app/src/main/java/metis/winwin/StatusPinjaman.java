package metis.winwin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Adapter.OwnProgressDialog;
import metis.winwin.Model.StatusPinjamanMode;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.DecimalsFormat;
import metis.winwin.Utils.SessionManager;

public class StatusPinjaman extends AppCompatActivity {

    @Bind(R.id.etNoApl)
    TextView etNoApl;
    @Bind(R.id.etPinjaman)
    TextView etPinjaman;
    @Bind(R.id.etBunga)
    TextView etBunga;
    @Bind(R.id.etPerpanjangan)
    TextView etPerpanjangan;
    @Bind(R.id.etDenda)
    TextView etDenda;
    @Bind(R.id.etTotalTag)
    TextView etTotalTag;
    @Bind(R.id.etJangka)
    TextView etJangka;
    @Bind(R.id.etTempo)
    TextView etTempo;
    @Bind(R.id.btBack)
    ImageButton btBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    String idclient;
    @Bind(R.id.etStatus)
    TextView etStatus;
    @Bind(R.id.lyDetail)
    LinearLayout lyDetail;
    @Bind(R.id.txLunas)
    TextView txLunas;
    @Bind(R.id.lyLunas)
    LinearLayout lyLunas;
    @Bind(R.id.etTotalSudahDibayar)
    TextView etTotalSudahDibayar;
    @Bind(R.id.etSisaTagihan)
    TextView etSisaTagihan;
    private OwnProgressDialog progressDialog;
    private final int MY_SOCKET_TIMEOUT_MS = 60 * 1000;
    private SessionManager sessionManager;
    private String no_peng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_pinjaman);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(StatusPinjaman.this);
        idclient = sessionManager.getIdhq();
        no_peng = "";
        requestQueue = Volley.newRequestQueue(StatusPinjaman.this);
        progressDialog = new OwnProgressDialog(StatusPinjaman.this);

        loadTotal();
    }

    @OnClick(R.id.btBack)
    public void onViewClicked() {
        finish();
    }

    private void loadTotal() {
        progressDialog.show();
        AndLog.ShowLog("LihatURL ", AppConf.URL_GET_STATUS_PINJAMAN + idclient);
        stringRequest = new StringRequest(Request.Method.GET, AppConf.URL_GET_STATUS_PINJAMAN + idclient, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AndLog.ShowLog("responenyua ", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        StatusPinjamanMode dataClient = new StatusPinjamanMode();
                        dataClient.setNo_aplikasi(json.getString("pengajuan_nomor_pengajuan"));
                        dataClient.setPinjaman(json.getString("pengajuan_nilai_pengajuan"));
                        dataClient.setBunga(json.getString("pengajuan_ratetotal_disetujui"));
                        dataClient.setPerpanjangan(json.getString("perpanjang_biaya_total"));
                        dataClient.setDenda(json.getString("denda_biaya"));
                        dataClient.setJangkapinjaman(json.getString("pengajuan_durasi_hari"));
                        dataClient.setJatuhtempo(json.getString("pengajuan_jatuh_tempo"));
                        dataClient.setStatus(json.getString("status"));
                        no_peng = dataClient.getNo_aplikasi();
                        etNoApl.setText(dataClient.getNo_aplikasi());
                        etPinjaman.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(dataClient.getPinjaman()) + ",-");
                        etBunga.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(dataClient.getBunga()) + ",-");
                        etPerpanjangan.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(dataClient.getPerpanjangan()) + ",-");
                        etDenda.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(dataClient.getDenda()) + ",-");
                        etStatus.setText(dataClient.getStatus());
                        etTotalSudahDibayar.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(dataClient.getTotalsudahdibayar()) + ",-");
                        etSisaTagihan.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(dataClient.getSisatagihan()) + ",-");
                        etTotalSudahDibayar.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(dataClient.getTotalsudahdibayar()) + ",-");
                        etSisaTagihan.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(dataClient.getSisatagihan()) + ",-");

                        int angka1, angka2, angka4;
                        String hasil;
                        String angka3;
                        angka1 = Integer.parseInt(dataClient.getPinjaman());
                        angka2 = Integer.parseInt(dataClient.getBunga());


                        if (dataClient.getPerpanjangan() != null) {
                            angka3 = dataClient.getPerpanjangan();
                        } else {
                            angka3 = "0";
                        }

                        angka4 = Integer.parseInt(dataClient.getDenda());
                        hasil = Integer.toString(angka1 + angka2 + Integer.parseInt(angka3) + angka4);

                        etTotalTag.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(hasil) + ",-");
                        etJangka.setText(dataClient.getJangkapinjaman() + " Hari");
                        etTempo.setText(dataClient.getJatuhtempo());


                        if (dataClient.getStatus().toLowerCase().contains("sudah lunas")) {
                            lyDetail.setVisibility(View.GONE);
                            lyLunas.setVisibility(View.VISIBLE);
                            txLunas.setText("Pinjaman terahir Anda dengan nomor " + no_peng + " telah lunas, Anda dapat mengajukan pinjaman kembali.");
                        } else {
                            lyDetail.setVisibility(View.VISIBLE);
                        }
                    }

//                    loadLoan();

                } catch (JSONException e) {
                    Toast.makeText(StatusPinjaman.this, "Gagal memuat data, Silahkan coba kembali", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError) {
                    Toast.makeText(StatusPinjaman.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(StatusPinjaman.this, "no connection", Toast.LENGTH_SHORT).show();
                } else if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Session Expired", Toast.LENGTH_LONG).show();
                    sessionManager.logoutUser();
                    Intent intent = new Intent(StatusPinjaman.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            error.networkResponse.statusCode + "", Toast.LENGTH_LONG).show();
                }

                progressDialog.dismiss();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }


    private void loadLoan() {

        AndLog.ShowLog("LihatURL ", AppConf.URL_LOANSTAT + "?_session=" + sessionManager.getSession());
        stringRequest = new StringRequest(Request.Method.GET, AppConf.URL_LOANSTAT + "?_session=" + sessionManager.getSession(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AndLog.ShowLog("responenyua ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("data")) {

                        JSONObject data = new JSONObject(jsonObject.getString("data"));
                        no_peng = data.getString("loan_no");
                        etNoApl.setText(no_peng);
                        etPinjaman.setText(data.getString("amount") + ",-");
                        etJangka.setText(data.getString("duration"));
                        etTempo.setText(data.getString("due_date"));
                        etTotalTag.setText(data.getString("total_bill") + ",-");
                        etStatus.setText(data.getString("status"));


                        if (data.getString("status").toLowerCase().contains("sudah lunas")) {
                            lyDetail.setVisibility(View.GONE);
                            lyLunas.setVisibility(View.VISIBLE);
                            txLunas.setText("Pinjaman terahir Anda dengan nomor " + no_peng + " telah lunas, Anda dapat mengajukan pinjaman kembali.");
                        } else {
                            lyDetail.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(StatusPinjaman.this, "Gagal memuat data, Silahkan coba kembali", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(StatusPinjaman.this, "Gagal memuat data, Silahkan coba kembali", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError) {
                    Toast.makeText(StatusPinjaman.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(StatusPinjaman.this, "no connection", Toast.LENGTH_SHORT).show();
                } else if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Session Expired", Toast.LENGTH_LONG).show();
                    sessionManager.logoutUser();
                    Intent intent = new Intent(StatusPinjaman.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error " + error.networkResponse.statusCode + "", Toast.LENGTH_LONG).show();
                }

                progressDialog.dismiss();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

}
