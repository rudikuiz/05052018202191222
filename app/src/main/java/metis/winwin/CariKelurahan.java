package metis.winwin;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import metis.winwin.Adapter.KelurahanAdapter;
import metis.winwin.Adapter.OwnProgressDialog;
import metis.winwin.Model.KelurahanModel;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;

public class CariKelurahan extends AppCompatActivity {

    @Bind(R.id.etCari)
    EditText etCari;
    @Bind(R.id.rvData)
    RecyclerView rvData;
    @Bind(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    private ArrayList<KelurahanModel> arrayList = new ArrayList<>();
    private ArrayList<KelurahanModel> mExampleList = new ArrayList<>();
    RequestQueue requestQueue;
    StringRequest stringRequest;
    OwnProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_kelurahan);
        ButterKnife.bind(this);
        rvData.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(CariKelurahan.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(CariKelurahan.this);
        loading = new OwnProgressDialog(CariKelurahan.this);

        loading.show();
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                getJSON();

            }
        });
        arrayList.clear();
        getJSON();

        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0) {
                    KelurahanAdapter adapter = new KelurahanAdapter(arrayList, CariKelurahan.this);
                    rvData.setAdapter(adapter);
                } else {
                    filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filter(String text) {
        ArrayList<KelurahanModel> filteredList = new ArrayList<>();
        for (KelurahanModel item : arrayList) {
            if (item.getKel().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mExampleList.clear();
        mExampleList.addAll(filteredList);
        KelurahanAdapter adapter = new KelurahanAdapter(filteredList, CariKelurahan.this);
        rvData.setAdapter(adapter);

    }


    private void getJSON() {
        arrayList = new ArrayList<KelurahanModel>();
        stringRequest = new StringRequest(Request.Method.GET, AppConf.URL_HQ + "ref_kelurahan.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AndLog.ShowLog("response: ", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        KelurahanModel modelMenu = new KelurahanModel();
                        modelMenu.setKel(json.getString("kodepos_kelurahan"));
                        modelMenu.setKec(json.getString("kodepos_kecamatan"));
                        modelMenu.setKota(json.getString("kodepos_jenis"));
                        arrayList.add(modelMenu);
                    }

                    KelurahanAdapter adapter = new KelurahanAdapter(arrayList, CariKelurahan.this);
                    rvData.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loading.dismiss();
                if (Swipe != null) {
                    Swipe.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Toast.makeText(CariKelurahan.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(CariKelurahan.this, "no connection", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                loading.dismiss();
                if (Swipe != null) {
                    Swipe.setRefreshing(false);
                }
            }
        });
        requestQueue.add(stringRequest);
    }

}
