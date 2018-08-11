package metis.winwin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Adapter.OwnProgressDialog;
import metis.winwin.Fragments.Fragment1;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.DecimalsFormat;
import metis.winwin.Utils.SessionManager;
import metis.winwin.Utils.ViewPagerAdapter;

public class ConBayarSeb extends AppCompatActivity {

    @Bind(R.id.btBack)
    ImageButton btBack;

    StringRequest stringRequest;
    RequestQueue requestQueue;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.btAction)
    Button btAction;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.relanjut)
    RelativeLayout relanjut;
    @Bind(R.id.masukan)
    EditText masukan;
    @Bind(R.id.generate)
    Button generate;
    @Bind(R.id.hasil)
    TextView hasil;
    @Bind(R.id.liNilaiBayar)
    LinearLayout liNilaiBayar;
    @Bind(R.id.downtime)
    TextView downtime;
    @Bind(R.id.note2)
    TextView note2;
    @Bind(R.id.txnote)
    TextView txnote;
    private ViewPagerAdapter pagerAdapter;
    String jam;
    OwnProgressDialog loading;
    SharedPreferences sharedPreferences;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_bayar_seb);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(ConBayarSeb.this);
        loading = new OwnProgressDialog(ConBayarSeb.this);

        loading.show();

        requestQueue = Volley.newRequestQueue(ConBayarSeb.this);
        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
        jam = sharedPreferences.getString("jam", "");

        clearlayout();
        initView();
        load();

        btAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConBayarSeb.this, BayarSebagian.class);
                startActivity(intent);

            }
        });
    }


    private void initView() {
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new Fragment1(), "page1");
        viewPager.setAdapter(pagerAdapter);
    }

    private void CheckVirtualAccount() {
        String idclient = sessionManager.getIdhq();
        stringRequest = new StringRequest(Request.Method.GET, "https://hq.ppgwinwin.com/winwin/api/check_va_number.php?cli_id=" + idclient, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.equals("0")) {
                    txnote.setText(getString(R.string.note2));
                } else {
                    txnote.setText(getString(R.string.noteva, response.toString()));
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

    public void reverseTimer(int Seconds) {

        new CountDownTimer(Seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

                downtime.setText("Sisa Waktu : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                downtime.setText("Completed");
            }
        }.start();
    }

    private void load() {
        stringRequest = new StringRequest(Request.Method.POST, AppConf.URL_POST_PINJAM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AndLog.ShowLog("genrate", response);
                if (!response.equals("false")) {
                    viewPager.setVisibility(View.GONE);
                    relanjut.setVisibility(View.GONE);
                    liNilaiBayar.setVisibility(View.VISIBLE);
                    note2.setVisibility(View.GONE);
                    CheckVirtualAccount();
                    hasil.setText("Rp." + DecimalsFormat.priceWithoutDecimal(response) + ",-");
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        String currentDateandTime = sdf.format(new Date());

                        Date date = sdf.parse(jam);
                        Date date2 = sdf.parse(currentDateandTime);

                        long difference = date.getTime() - date2.getTime();
//            int msPerHour = 1000*60*60;
//            int hours = difference/secondPerHour;
//            int minutes = difference % secondPerHour;

                        long sisa = difference / 1000;

                        AndLog.ShowLog("DIFXX", difference + ";" + sisa + ";" + jam + ";" + currentDateandTime);

                        reverseTimer((int) sisa);


                    } catch (ParseException e) {
                        e.printStackTrace();

                    }
                } else {
                    viewPager.setVisibility(View.VISIBLE);
                    relanjut.setVisibility(View.VISIBLE);
                    liNilaiBayar.setVisibility(View.GONE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("jam", "");
                    editor.commit();

                }
                loading.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("cli_id", sessionManager.getIdhq());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void clearlayout() {
        viewPager.setVisibility(View.GONE);
        relanjut.setVisibility(View.GONE);
        liNilaiBayar.setVisibility(View.GONE);
    }

    @OnClick({R.id.btBack, R.id.btAction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                finish();
                break;
            case R.id.btAction:

                break;
        }
    }
}
