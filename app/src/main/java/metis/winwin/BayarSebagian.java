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

public class BayarSebagian extends AppCompatActivity {

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
    private static final String TAG = BayarSebagian.class.getSimpleName();
    ConnectivityManager conMgr;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    String hasiluniq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_sebagian);
        ButterKnife.bind(this);
        hasiluniq = "0";
        sessionManager = new SessionManager(BayarSebagian.this);
        clientID = sessionManager.getIduser();
        requestQueue = Volley.newRequestQueue(BayarSebagian.this);
        etNilaiPinjam.setText("0");
        AndLog.ShowLog("iduser", clientID);

        int nilai = Integer.valueOf(etNilaiPinjam.getText().toString());
        if (nilai < 1) {
            etNilaiPinjam.setText("0");
        }

        etNilaiPinjam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etNilaiPinjam.getText().toString().length() == 0){
                    etNilaiPinjam.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                int nilai = Integer.valueOf(etNilaiPinjam.getText().toString());

//                if (etNilaiPinjam.getText().toString().length() == 0){
//                    etNilaiPinjam.setText("0");
//                }
            }
        });
    }

    @OnClick(R.id.btAction)
    public void onViewClicked() {

        int nilai;
        int min = 150000;
        int enol = 1;
        nilai = Integer.valueOf(etNilaiPinjam.getText().toString());

        if (nilai < min){
            GlobalToast.ShowToast(BayarSebagian.this, "Maaf tidak boleh kurang dari 150,000");
        }else {
            //ConBayarSeb sebagian
            Intent in = new Intent(BayarSebagian.this, MetodePembayaran.class);
            in.putExtra("pass", "0");
            in.putExtra("nilaisebagian", etNilaiPinjam.getText().toString());
            startActivity(in);
        }
    }

    @OnClick(R.id.btBack)
    public void onViewBack() {
        finish();
    }

//        Intent intent = new Intent(BayarSebagian.this, BCATransfer.class);
//        startActivity(intent);
}

