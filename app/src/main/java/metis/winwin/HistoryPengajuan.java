package metis.winwin;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Adapter.HistoryAdapter;
import metis.winwin.Adapter.OwnProgressDialog;
import metis.winwin.Model.HistoryModel;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.DecimalsFormat;
import metis.winwin.Utils.SessionManager;
import metis.winwin.Utils.VolleyHttp;

public class HistoryPengajuan extends AppCompatActivity {

    @Bind(R.id.btBack)
    ImageButton btBack;
    @Bind(R.id.btSearch)
    ImageButton btSearch;
    @Bind(R.id.rvData)
    RecyclerView rvData;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<HistoryModel> dataSet = new ArrayList<>();
    OwnProgressDialog loading;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_pengajuan);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(HistoryPengajuan.this);
        rvData.setLayoutManager(layoutManager);
        sessionManager = new SessionManager(HistoryPengajuan.this);

        adapter = new HistoryAdapter(HistoryPengajuan.this, dataSet);
        rvData.setAdapter(adapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                swipeRefreshLayout.setRefreshing(false);

            }
        });



        loading = new OwnProgressDialog(HistoryPengajuan.this);

        loading.show();

        requestQueue = Volley.newRequestQueue(HistoryPengajuan.this);
        getJSON();

    }

    private void GetData() {


        HistoryModel model = new HistoryModel();
        model.setTanggal("22/05/2017");
        model.setPengajuan("2.500.000");
        model.setStatus("Ditolak");
        model.setKeterangan("Data tidak valid");
        dataSet.add(model);


        model = new HistoryModel();
        model.setTanggal("25/05/2017");
        model.setPengajuan("2.000.000");
        model.setStatus("Diterima");
        model.setKeterangan("-");
        dataSet.add(model);


        model = new HistoryModel();
        model.setTanggal("07/06/2017");
        model.setPengajuan("3.000.000");
        model.setStatus("Ditolak");
        model.setKeterangan("Data tidak valid");
        dataSet.add(model);


        adapter.notifyDataSetChanged();

    }


    @OnClick({R.id.btBack, R.id.btSearch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                finish();
                break;
            case R.id.btSearch:
                break;
        }
    }

    private void getJSON() {
        stringRequest = new StringRequest(Request.Method.GET, AppConf.URL_HISTORY + sessionManager.getIdhq(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    AndLog.ShowLog("asd", response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        HistoryModel historyModel = new HistoryModel();
                        historyModel.setId(jsonArray.getJSONObject(i).getString("id"));
                        historyModel.setStatus(jsonArray.getJSONObject(i).getString("status"));
                        historyModel.setKeterangan(jsonArray.getJSONObject(i).getString("tahap"));
                        historyModel.setPengajuan("Rp. " + DecimalsFormat.priceWithoutDecimal(jsonArray.getJSONObject(i).getString("amount")));
                        historyModel.setTanggal(jsonArray.getJSONObject(i).getString("created_at"));
                        dataSet.add(historyModel);
                        AndLog.ShowLog("jumlah", String.valueOf(i));

                    }

                    HistoryAdapter adapter = new HistoryAdapter(HistoryPengajuan.this, dataSet);
                    rvData.setAdapter(adapter);
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

    private void getData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConf.URL_HISTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    AndLog.ShowLog("history", response);
                    JSONObject jo = new JSONObject(response);

                    if (jo.has("data")) {

                        JSONArray jsonArray = new JSONArray(jo.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HistoryModel historyModel = new HistoryModel();
                            historyModel.setId(jsonArray.getJSONObject(i).getString("id"));
                            historyModel.setStatus(jsonArray.getJSONObject(i).getString("status"));
                            historyModel.setKeterangan(jsonArray.getJSONObject(i).getString("tahap"));
                            historyModel.setPengajuan(jsonArray.getJSONObject(i).getString("amount"));
                            historyModel.setTanggal(jsonArray.getJSONObject(i).getString("created_at"));
                            dataSet.add(historyModel);
                            adapter.notifyItemChanged(dataSet.size() - 1);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", sessionManager.getIdhq());

                return params;
            }
        };

        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(HistoryPengajuan.this).addToRequestQueue(stringRequest);

    }
}
