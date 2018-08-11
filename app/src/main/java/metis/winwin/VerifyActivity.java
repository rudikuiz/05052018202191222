package metis.winwin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import metis.winwin.Adapter.OwnProgressDialog;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.DataPinjamanManager;
import metis.winwin.Utils.GlobalToast;
import metis.winwin.Utils.SessionManager;
import metis.winwin.Utils.VolleyHttp;

public class VerifyActivity extends AppCompatActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.et1)
    EditText et1;
    @Bind(R.id.et2)
    EditText et2;
    @Bind(R.id.et3)
    EditText et3;
    @Bind(R.id.et4)
    EditText et4;
    @Bind(R.id.et5)
    EditText et5;
    @Bind(R.id.et6)
    EditText et6;
    @Bind(R.id.txKamisudah)
    TextView txKamisudah;
    @Bind(R.id.btActivate)
    Button btActivate;
    OwnProgressDialog loading;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    String kode, getKode, no_ktp, id, nama_lengkap, email, kode_email;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(VerifyActivity.this);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                et1.removeTextChangedListener(this);
                if (s.length() > 0) {
                    et2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et2.removeTextChangedListener(this);
                if (s.length() > 0) {
                    et3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et3.removeTextChangedListener(this);
                if (s.length() > 0) {
                    et4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                et4.removeTextChangedListener(this);
                if (s.length() > 0) {
                    et5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et5.removeTextChangedListener(this);
                if (s.length() > 0) {
                    et6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loading = new OwnProgressDialog(VerifyActivity.this);

        loading.show();
        requestQueue = Volley.newRequestQueue(VerifyActivity.this);
        initData();
        getKODE();
    }

    private void initData() {
        DataPinjamanManager dataPinjamanManager = new DataPinjamanManager(VerifyActivity.this);
        if (dataPinjamanManager.isExisting()) {
            try {
                JSONObject jo = new JSONObject(dataPinjamanManager.getJson());
                String getKTP = jo.getString("no_ktp");
                String handphone = jo.getString("handphone");
                no_ktp = getKTP;
                txKamisudah.setText(getString(R.string.kamisudahmengirim, handphone));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getKODE() {
        stringRequest = new StringRequest(Request.Method.GET, AppConf.URL_KODE_ACTIVATION + no_ktp, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    kode = jsonObject.getString("kode");
                    email = jsonObject.getString("email");
                    kode_email = jsonObject.getString("kode_email");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), getString(R.string.gagal_menyambungkan), Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    finish();
                }

                loading.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), getString(R.string.gagal_menyambungkan), Toast.LENGTH_SHORT).show();
                loading.dismiss();
                finish();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void LoginProses() {
        loading.show();

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

                        sessionManager.createLoginSession(id, nama_lengkap, session);
                        getJSON();


                    } else {

                        if (jo.has("message")) {

                            String msg = jo.getString("message");
                            String code = jo.getString("code");


                            if (code.equalsIgnoreCase("w-209")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VerifyActivity.this);

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
                            } else {
                                GlobalToast.ShowToast(VerifyActivity.this, msg);
                            }

                        }
                        loading.dismiss();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    loading.dismiss();
                    GlobalToast.ShowToast(VerifyActivity.this, getString(R.string.gagal_menyambungkan));

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                GlobalToast.ShowToast(VerifyActivity.this, getString(R.string.disconnected));
                loading.dismiss();
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("username", email);
                params.put("password", kode_email);

                return params;
            }
        };

        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(VerifyActivity.this).addToRequestQueue(stringRequest);

    }


    private void getCustInfo() {


        final SessionManager sess = new SessionManager(VerifyActivity.this);
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
                loading.dismiss();
                Goto();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.dismiss();
                Goto();
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String id_user = new SessionManager(VerifyActivity.this).getIdhq();
                AndLog.ShowLog("hashas", id_user);
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id_user);

                return params;
            }
        };

        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(VerifyActivity.this).addToRequestQueue(stringRequest);

    }

    private void getJSON() {
        String id_user = new SessionManager(VerifyActivity.this).getIduser();
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
        Toast.makeText(VerifyActivity.this, "Verify Sukses!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(VerifyActivity.this, VerifySuksesActivity.class);
        startActivity(intent);
        finish();

    }

    private void Submit() {
        loading.show();
        stringRequest = new StringRequest(Request.Method.GET, AppConf.URL_ACTIVATED + no_ktp, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String sukses = object.getString("result");

                    if (sukses.equals("Berhasil")) {
                        LoginProses();
                    } else {
                        Toast.makeText(VerifyActivity.this, "Verify Gagal, silahkan coba kembali", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(VerifyActivity.this, "Verify Gagal, silahkan coba kembali!", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
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


    @OnClick({R.id.back, R.id.btActivate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btActivate:
                getKode = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString() + et5.getText().toString() + et6.getText().toString();

                if (getKode.equals(kode)) {
                    if (!et1.getText().toString().isEmpty() &&
                            !et2.getText().toString().isEmpty() &&
                            !et3.getText().toString().isEmpty() &&
                            !et4.getText().toString().isEmpty() &&
                            !et5.getText().toString().isEmpty() &&
                            !et6.getText().toString().isEmpty()) {
                        Submit();
                    } else {

                        Toast.makeText(VerifyActivity.this, "Angka harus lengkap!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(VerifyActivity.this, "Kode tidak valid", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
