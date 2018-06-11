package metis.winwin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.DecimalsFormat;
import metis.winwin.Utils.GlobalToast;
import metis.winwin.Utils.SessionManager;

public class BayarRequest extends AppCompatActivity {

    @Bind(R.id.etNilaiPinjam)
    EditText etNilaiPinjam;
    @Bind(R.id.btAction)
    Button btAction;
    @Bind(R.id.btBack)
    ImageButton btBack;
    private SessionManager sessionManager;
    String clientID;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    ProgressDialog pDialog;
    int success;
    private static final String TAG = BayarRequest.class.getSimpleName();
    ConnectivityManager conMgr;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    String hasiluniq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        hasiluniq = "0";
        sessionManager = new SessionManager(BayarRequest.this);
        clientID = sessionManager.getIduser();
        requestQueue = Volley.newRequestQueue(BayarRequest.this);
        AndLog.ShowLog("iduser", clientID);

        etNilaiPinjam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DecimalsFormat.priceWithoutDecimal(etNilaiPinjam.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                DecimalsFormat.priceWithoutDecimal(etNilaiPinjam.getText().toString());
            }
        });
    }

    @OnClick(R.id.btAction)
    public void onViewClicked() {

        int nilai;
        int min = 150000;

        nilai = Integer.valueOf(etNilaiPinjam.getText().toString());

        if (nilai < min){
            GlobalToast.ShowToast(BayarRequest.this, "Maaf tidak boleh kurang dari 150,000");
        }else {
            Intent in = new Intent(BayarRequest.this, MetodePembayaran.class);
//            in.putExtra("token", etNilaiPinjam.getText().toString());
            sessionManager.setToken(etNilaiPinjam.getText().toString());
            startActivity(in);
        }
    }

    @OnClick(R.id.btBack)
    public void onViewBack() {
        finish();
    }

//        Intent intent = new Intent(BayarRequest.this, BCATransfer.class);
//        startActivity(intent);
}

