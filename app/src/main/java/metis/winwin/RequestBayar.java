package metis.winwin;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Adapter.OwnProgressDialog;
import metis.winwin.Model.StatusPinjamanMode;
import metis.winwin.Model.TokenModel;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.DecimalsFormat;
import metis.winwin.Utils.SessionManager;

public class RequestBayar extends AppCompatActivity {

    @Bind(R.id.hasil)
    TextView hasil;
    @Bind(R.id.btBack)
    ImageButton btBack;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.downtime)
    TextView downtime;
    private SessionManager sessionManager;
    String  hasilku;
    ArrayList<TokenModel> tokenModels = new ArrayList<>();
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ConnectivityManager conMgr;
    private OwnProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_bayar);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(RequestBayar.this);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        requestQueue = Volley.newRequestQueue(RequestBayar.this);
        getJSON();
//        reverseTimer(1800);
    }

    public void reverseTimer(int Seconds) {

        new CountDownTimer(Seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

                downtime.setText(String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                downtime.setText("Completed");
            }
        }.start();
    }


//        sessionManager = new SessionManager(RequestBayar.this);
//        clientID = sessionManager.getIduser();


    @OnClick(R.id.btBack)
    public void onViewClicked() {
//        finish();
        Intent intent = new Intent(RequestBayar.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void getJSON() {
        String idclient = sessionManager.getIdhq();
        AndLog.ShowLog("link", AppConf.URL_GET_DATA + idclient);
        stringRequest = new StringRequest(Request.Method.GET, AppConf.URL_GET_DATA + idclient, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
             try {
                    JSONObject json = new JSONObject(response);
                    StatusPinjamanMode dataClient = new StatusPinjamanMode();
                    dataClient.setTotaltagihan(json.getString("pengajuan_total_disetujui"));
                    hasil.setText("Rp. "+ DecimalsFormat.priceWithoutDecimal(dataClient.getTotaltagihan())+",-");
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

    private void chekInternet() {
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


}
