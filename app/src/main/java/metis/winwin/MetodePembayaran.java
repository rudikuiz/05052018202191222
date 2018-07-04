package metis.winwin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Adapter.OwnProgressDialog;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.SessionManager;

public class MetodePembayaran extends AppCompatActivity {

    @Bind(R.id.btBack)
    ImageButton btBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btTransfer)
    ImageView btTransfer;
    @Bind(R.id.btVirtualAccount)
    ImageView btVirtualAccount;
    String val, bayarsebagian;
    private SessionManager sessionManager;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    String id_peng, nama, nohp, nominal;
    OwnProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metode_pembayaran);
        Intent intent = getIntent();
        val = intent.getStringExtra("pass");
        bayarsebagian = intent.getStringExtra("nilaisebagian");
        sessionManager = new SessionManager(MetodePembayaran.this);
        requestQueue = Volley.newRequestQueue(MetodePembayaran.this);
        ButterKnife.bind(this);
        loading = new OwnProgressDialog(MetodePembayaran.this);
        loading.show();
        getdata_va();
    }

    private void getdata_va() {

        String idclient = sessionManager.getIdhq();
        AndLog.ShowLog("link", AppConf.URL_GET_DATA + idclient);
        stringRequest = new StringRequest(Request.Method.GET, AppConf.URL_GET_DATA + idclient, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    id_peng = json.getString("pengajuan_id");
                    nama = json.getString("cli_nama_lengkap");
                    nohp = json.getString("cli_handphone");
                    nominal = json.getString("pengajuan_total_disetujui");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckVirtualAccount();
    }

    private void CheckVirtualAccount() {
        String idclient = sessionManager.getIdhq();
        stringRequest = new StringRequest(Request.Method.GET, "https://hq.ppgwinwin.com/winwin/api/check_va.php?cli_id=" + idclient, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.equals("1")) {
                    if (val.equals("0")) {
//                        Bayar Sebagian
                        Intent intent = new Intent(MetodePembayaran.this, BCATransfer.class);
                        intent.putExtra("nilai", bayarsebagian);
                        startActivity(intent);
                    } else if (val.equals("1")) {
//                        Bayar Penuh
                        startActivity(new Intent(MetodePembayaran.this, RequestBayar.class));
                    }

                } else if (response.equals("0")) {

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

    @OnClick({R.id.btBack, R.id.btTransfer, R.id.btVirtualAccount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                Intent intent = new Intent(MetodePembayaran.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.btTransfer:
                if (val.equals("0")) {
                    Intent intents = new Intent(MetodePembayaran.this, BCATransfer.class);
                    intents.putExtra("nilai", bayarsebagian);
                    startActivity(intents);
                } else if (val.equals("1")) {
                    startActivity(new Intent(MetodePembayaran.this, RequestBayar.class));
                }

                break;
            case R.id.btVirtualAccount:
                Intent intent1 = new Intent(MetodePembayaran.this, WebViewVirtualAccount.class);
                intent1.putExtra("id_peng", id_peng);
                intent1.putExtra("nama", nama);
                if (val.equals("0")) {
//                    nilai ambil dari pengetikan
                    intent1.putExtra("nominal", bayarsebagian);

                } else if (val.equals("1")) {
//                    nilai ambil dari API
                    intent1.putExtra("nominal", nominal);
                }

                intent1.putExtra("nohp", nohp);
                startActivity(intent1);
                break;
        }
    }
}
