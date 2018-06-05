package metis.winwin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.GlobalToast;
import metis.winwin.Utils.SessionManager;
import metis.winwin.Utils.VolleyHttp;

public class Login extends AppCompatActivity {

    @Bind(R.id.txUsername)
    EditText txUsername;
    @Bind(R.id.txPassword)
    EditText txPassword;
    @Bind(R.id.btLogin)
    Button btLogin;

    @Bind(R.id.id_clients)
    TextView idClients;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;

    ConnectivityManager conMgr;
    StringRequest stringRequest;
    String id, nama_lengkap;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(Login.this);
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading...");
        requestQueue = Volley.newRequestQueue(Login.this);
        cekInternet();

    }

    private void LoginProses() {
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConf.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AndLog.ShowLog("login", response);
                try {
                    JSONObject jo = new JSONObject(response);
                    AndLog.ShowLog("res", response);
                    if (jo.has("data")) {

                        JSONObject jos = new JSONObject(jo.getString("data"));
                        id = jos.getString("id");
                        nama_lengkap = jos.getString("nama_lengkap");
                        String session = jos.getString("session_id");
                        AndLog.ShowLog("tag_id", id);
                        GlobalToast.ShowToast(Login.this, getString(R.string.login_success));

                        sessionManager.createLoginSession(id, nama_lengkap, session);
                        getJSON();


                    } else {

                        if (jo.has("message")){

                            String msg = jo.getString("message");
                            String code = jo.getString("code");


                            if(code.equalsIgnoreCase("w-209")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

                                builder.setTitle("Login gagal");
                                builder.setCancelable(false);
                                builder.setMessage(msg);


                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();

                                    }
                                });


                                AlertDialog alert = builder.create();
                                alert.show();
                            }else {
                                GlobalToast.ShowToast(Login.this, msg);
                            }

                        }
                        progressDialog.dismiss();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    progressDialog.dismiss();
                    GlobalToast.ShowToast(Login.this, getString(R.string.gagal_menyambungkan));

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                GlobalToast.ShowToast(Login.this, getString(R.string.disconnected));
                progressDialog.dismiss();
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", txUsername.getText().toString().trim());
                params.put("password", txPassword.getText().toString().trim());

                return params;
            }
        };

        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(Login.this).addToRequestQueue(stringRequest);

    }


    private void getCustInfo() {


        final SessionManager sess = new SessionManager(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConf.URL_CHECKDISETUJUI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AndLog.ShowLog("custinfo", response);
                try {
                    JSONObject jo = new JSONObject(response);

                    if (jo.has("error")) {

                        String error = jo.getString("error");

                        if (error.equals("false")) {

                            if (jo.has("rate")) {

                                String rate = jo.getString("rate");
                                String max1 = jo.getString("max");
                                String max_pinjam = jo.getString("max_pinjam");
                                String rating = jo.getString("rating");
                                String total_setujui = jo.getString("pinjaman");

                                sess.setRate(rate);
                                sess.setMax(max1);
                                sess.setMaxpinjam(max_pinjam);
                                sess.setRating(rating);
                                sess.setTotalSetujui(total_setujui);

                            }
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();


                }
                progressDialog.dismiss();
                Goto();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Goto();
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String id_user = new SessionManager(Login.this).getIdhq();
                AndLog.ShowLog("hashas", id_user);
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id_user);

                return params;
            }
        };

        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(Login.this).addToRequestQueue(stringRequest);

    }

    private void getJSON() {
        String id_user = new SessionManager(Login.this).getIduser();
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

                getCustInfo();
//                getCountPinjaman();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }


    private void Goto() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public void cekInternet() {
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

    @OnClick(R.id.btLogin)
    public void onClick() {

        if (!txUsername.getText().toString().isEmpty() && !txPassword.getText().toString().isEmpty()) {
            LoginProses();

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        VolleyHttp.getInstance(Login.this).cancelPendingRequests(AppConf.httpTag);
    }
}
