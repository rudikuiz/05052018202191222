package metis.winwin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.DecimalsFormat;
import metis.winwin.Utils.SessionManager;

import static metis.winwin.Utils.AppConf.URL_POST_PINJAM;

public class BCATransfer extends AppCompatActivity {

    @Bind(R.id.hasil)
    TextView hasil;
    String ash;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    @Bind(R.id.btBack)
    ImageButton btBack;
    @Bind(R.id.downtime)
    TextView downtime;
    SharedPreferences sharedPreferences;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(BCATransfer.this);
        requestQueue = Volley.newRequestQueue(BCATransfer.this);
        sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);

        load();
        DialogForm();

    }

    private void load() {
        stringRequest = new StringRequest(Request.Method.POST, URL_POST_PINJAM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AndLog.ShowLog("genrate", response);
                hasil.setText("Rp." + DecimalsFormat.priceWithoutDecimal(response) + ",-");

                long curTimeInMs = new Date().getTime();
                Date afterAddingMins = new Date(curTimeInMs + (30 * 60000));
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                String currentDateandTime2 = sdf2.format(afterAddingMins);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("jam", currentDateandTime2);
                editor.commit();
                reverseTimer(1800);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("pinjam", sessionManager.getToken());
                params.put("cli_id", sessionManager.getIdhq());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void DialogForm() {
        final Dialog dialog = new Dialog(BCATransfer.this);
        LayoutInflater inflater = (LayoutInflater) BCATransfer.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_request_bayar, null);
        Button btselesai = view.findViewById(R.id.btSelesai);
        btselesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.corner_radius);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();
    }

    public void reverseTimer(int Seconds) {

        new CountDownTimer(Seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

                downtime.setText("Sisa Waktu : "+String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                downtime.setText("Completed");
            }
        }.start();
    }

    @OnClick(R.id.btBack)
    public void onViewClicked() {
//        Intent intent = new Intent(BCATransfer.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
        finish();

    }
}
