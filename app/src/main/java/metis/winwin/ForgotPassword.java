package metis.winwin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.GlobalToast;
import metis.winwin.Utils.VolleyHttp;

public class ForgotPassword extends AppCompatActivity {

    @Bind(R.id.btBack)
    ImageButton btBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.txEmail)
    EditText txEmail;
    @Bind(R.id.btLanjukan)
    Button btLanjukan;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(ForgotPassword.this);
        progressDialog.setMessage("Loading...");
    }

    @OnClick({R.id.btBack, R.id.btLanjukan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                finish();
                break;
            case R.id.btLanjukan:
                ForgotProses();
                break;
        }
    }

    private void ForgotProses() {
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConf.FORGOTPASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response);
                    String msg = jo.getString("message");

                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);

                    builder.setCancelable(false);
                    builder.setMessage(msg);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });


                    AlertDialog alert = builder.create();
                    alert.show();


                } catch (JSONException e) {
                    e.printStackTrace();


                    GlobalToast.ShowToast(ForgotPassword.this, getString(R.string.gagal_menyambungkan));

                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                GlobalToast.ShowToast(ForgotPassword.this, getString(R.string.disconnected));
                progressDialog.dismiss();
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("fpwemail", txEmail.getText().toString().trim());

                return params;
            }
        };

        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(ForgotPassword.this).addToRequestQueue(stringRequest);

    }
}
