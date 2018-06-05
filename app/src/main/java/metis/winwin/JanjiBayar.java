package metis.winwin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Model.ClientModel;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.SessionManager;

public class JanjiBayar extends AppCompatActivity {
    StringRequest stringRequest;
    RequestQueue requestQueue;
    @Bind(R.id.btBack)
    ImageButton btBack;
    @Bind(R.id.linBelumAktif)
    LinearLayout linBelumAktif;
    @Bind(R.id.btAjukan)
    Button btAjukan;
    @Bind(R.id.linRequestJanji)
    LinearLayout linRequestJanji;
    @Bind(R.id.linTidakDisetujui)
    LinearLayout linTidakDisetujui;
    @Bind(R.id.etTglDisetujui)
    TextView etTglDisetujui;
    @Bind(R.id.linDisetujui)
    LinearLayout linDisetujui;
    String dateString, datevales;
    @Bind(R.id.spDate)
    TextView spDate;
    @Bind(R.id.etTanggal)
    TextView etTanggal;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_janji_bayar);
        ButterKnife.bind(this);
        DisableAllLinear();
        requestQueue = Volley.newRequestQueue(JanjiBayar.this);
        sessionManager = new SessionManager(JanjiBayar.this);
        getCountPinjaman();
        CekRequestBayar();
    }

    private void CekRequestBayar() {

        AndLog.ShowLog("urls", AppConf.URL_GET_JANJI_TRIAL + sessionManager.getIdhq());
        stringRequest = new StringRequest(Request.Method.GET, AppConf.URL_GET_JANJI_TRIAL + sessionManager.getIdhq(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                AndLog.ShowLog("asdf", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ClientModel model = new ClientModel();
                        model.setKode(json.getString("pengajuan_janji_bayar_aktif"));
                        String kode = model.getKode();
                        AndLog.ShowLog("isi", kode);
                        if (kode.equals("1")) {
                            Aktif();
                        } else {
                            belumAktif();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);

    }

    private void getCountPinjaman() {

        AndLog.ShowLog("urlj", AppConf.URL_GET_JANJI_TRIAL + sessionManager.getIdhq());
        stringRequest = new StringRequest(Request.Method.GET, AppConf.URL_GET_JANJI_TRIAL + sessionManager.getIdhq(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                AndLog.ShowLog("dsf ", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }

    private void belumAktif() {
        linBelumAktif.setVisibility(View.VISIBLE);
        linDisetujui.setVisibility(View.GONE);
        linTidakDisetujui.setVisibility(View.GONE);
        linRequestJanji.setVisibility(View.GONE);
    }

    private void DisableAllLinear() {
        linBelumAktif.setVisibility(View.GONE);
        linDisetujui.setVisibility(View.GONE);
        linTidakDisetujui.setVisibility(View.GONE);
        linRequestJanji.setVisibility(View.GONE);
    }

    private void Aktif() {
        linBelumAktif.setVisibility(View.GONE);
        linDisetujui.setVisibility(View.GONE);
        linTidakDisetujui.setVisibility(View.GONE);
        linRequestJanji.setVisibility(View.VISIBLE);
    }

    private void PengajuanDiterima() {
        linBelumAktif.setVisibility(View.GONE);
        linDisetujui.setVisibility(View.GONE);
        linTidakDisetujui.setVisibility(View.GONE);
        linRequestJanji.setVisibility(View.GONE);
    }

    @OnClick({R.id.btBack, R.id.spDate, R.id.btAjukan, R.id.etTanggal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                finish();
                break;
            case R.id.spDate:

                break;
            case R.id.btAjukan:
                Toast.makeText(JanjiBayar.this, dateString, Toast.LENGTH_SHORT).show();
                break;
            case R.id.etTanggal:
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

                                int month = monthOfYear + 1;
                                dateString = String.format("%02d", dayOfMonth) + "-" + String.format("%02d", month) + "-" + year;
                                spDate.setText(dateString);
                            }
                        });
                cdp.show(getSupportFragmentManager(), null);
                break;
        }
    }
}
