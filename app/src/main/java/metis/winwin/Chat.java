package metis.winwin;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import metis.winwin.Adapter.ChatAdapter;
import metis.winwin.Model.ChatModel;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.AppConf;
import metis.winwin.Utils.DateTool;
import metis.winwin.Utils.SessionManager;
import metis.winwin.Utils.VolleyHttp;

public class Chat extends AppCompatActivity {


    @Bind(R.id.btBack)
    ImageButton btBack;
    @Bind(R.id.imgAdmin)
    CircleImageView imgAdmin;
    @Bind(R.id.txNama)
    TextView txNama;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.abl)
    AppBarLayout abl;
    @Bind(R.id.rvData)
    RecyclerView rvData;
    @Bind(R.id.txMessage)
    EditText txMessage;
    @Bind(R.id.btSend)
    ImageButton btSend;
    @Bind(R.id.lyRoot)
    LinearLayout lyRoot;
    @Bind(R.id.pbLoad)
    ProgressBar pbLoad;

    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<ChatModel> dataSet = new ArrayList<>();
    private SessionManager sessionManager;
    private boolean refresh, isProsess, endpage;
    private int page, limit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);


        progressDialog = new ProgressDialog(Chat.this);
        progressDialog.setMessage("Loading...");

        sessionManager = new SessionManager(Chat.this);
        isProsess = false;
        endpage = false;
        limit = 0;

        resetPage();

        layoutManager = new LinearLayoutManager(Chat.this);
        rvData.setLayoutManager(layoutManager);

        adapter = new ChatAdapter(Chat.this, dataSet);

        rvData.setAdapter(adapter);

        lyRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();

                lyRoot.getWindowVisibleDisplayFrame(r);

                int heightDiff = lyRoot.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 100) {
                    //enter your code here

                    rvData.scrollToPosition(rvData.getAdapter().getItemCount() - 1);

                } else {
                    //enter code for hid

                }
            }
        });

        rvData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {

                    if (!isProsess && !endpage) {

//                        progressDialog.show();
                        pbLoad.setVisibility(View.VISIBLE);
                        refresh = false;
                        GetData();
                    }

                } else if (layoutManager.findLastCompletelyVisibleItemPosition() == dataSet.size() - 1) {

                }
            }
        });

    }

    private void GetData() {


        isProsess = true;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConf.URL_GETCHAT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    if (refresh) {
                        dataSet.clear();
                    }

                    JSONObject jo = new JSONObject(response);

                    if (jo.has("limit")) {
                        limit = jo.getInt("limit");
                    }

                    JSONArray ja = jo.getJSONArray("data");

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jos = ja.getJSONObject(i);


                        String chat_id = jos.getString("chat_id");
                        String id_user = jos.getString("id_user");
                        String id_admin = jos.getString("id_admin");
                        String message = jos.getString("message");
                        String time_post = jos.getString("time_post");
//                        String updated_at = jos.getString("updated_at");
                        int imgstatus = R.drawable.ic_chat_success;
                        String sender = jos.getString("sender");


                        ChatModel model = new ChatModel();
                        model.setId(chat_id);
                        model.setIduser(id_admin);
                        model.setNama("Admin");
                        if (sender.equals("user")) {
                            model.setIduser(id_user);
                            model.setNama(sessionManager.getNama());
                        }
                        model.setText(message);
                        model.setWaktu(DateTool.changeFormat(time_post, "yyyy-MM-dd HH:mm", "dd MMM yy, HH:mm"));
                        model.setSender(sender);
                        model.setImgstatus(imgstatus);
                        dataSet.add(0, model);

                    }

                    adapter = new ChatAdapter(Chat.this, dataSet);
                    rvData.setAdapter(adapter);

                    if (refresh) {
                        rvData.scrollToPosition(dataSet.size() - 1);
                    } else {
                        rvData.scrollToPosition(ja.length());
                    }


                    if (ja.length() >= limit) {
                        page++;

                    } else {
                        endpage = true;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }


                isProsess = false;
                progressDialog.dismiss();
                pbLoad.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                isProsess = false;
                progressDialog.dismiss();
                pbLoad.setVisibility(View.GONE);

            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id_user", sessionManager.getIdhq());
                params.put("page", String.valueOf(page));

                return params;
            }
        };

        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(Chat.this).addToRequestQueue(stringRequest);

    }


    private void SendData(final int pos) {


        final String msg = txMessage.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConf.URL_SENDCHAT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                AndLog.ShowLog("NOTIFF", response);
                try {

                    JSONObject jo = new JSONObject(response);

                    if (jo.has("error")) {

                        String error = jo.getString("error");

                        if (error.equals("false")) {

                            dataSet.get(pos).setImgstatus(R.drawable.ic_chat_success);

                        } else {

                            dataSet.get(pos).setImgstatus(R.drawable.ic_chat_failed);

                        }

                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    dataSet.get(pos).setImgstatus(R.drawable.ic_chat_failed);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dataSet.get(pos).setImgstatus(R.drawable.ic_chat_failed);

            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id_admin", "0");
                params.put("id_user", sessionManager.getIdhq());
                params.put("message", msg);
                params.put("user", "user");
                params.put("tipe", "chat");

                return params;
            }
        };

        stringRequest.setTag(AppConf.httpTag);
        VolleyHttp.getInstance(Chat.this).addToRequestQueue(stringRequest);

        adapter.notifyDataSetChanged();
    }


    @OnClick({R.id.btBack, R.id.btSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                finish();
                break;
            case R.id.btSend:
                if (!txMessage.getText().toString().isEmpty()) {

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yy, HH:mm");
                    String tglSkg = df.format(c.getTime());

                    String chat_id = "0";
                    String id_user = sessionManager.getIduser();
                    String id_admin = "0";
                    String message = txMessage.getText().toString().trim();
                    String time_post = tglSkg;
//                        String updated_at = jos.getString("updated_at");
//                        String status = jos.getString("status");
                    String sender = "user";

                    ChatModel model = new ChatModel();
                    model.setId(chat_id);
                    model.setIduser(id_admin);
                    model.setNama("Admin");
                    if (sender.equals("user")) {
                        model.setIduser(sessionManager.getIdhq());
                        model.setNama(sessionManager.getNama());
                    }
                    model.setText(message);
                    model.setWaktu(time_post);
                    model.setSender(sender);
                    model.setImgstatus(R.drawable.ic_chat_pending);

                    dataSet.add(model);

                    adapter.notifyDataSetChanged();

                    SendData(dataSet.size() - 1);
                    rvData.scrollToPosition(dataSet.size() - 1);
                    txMessage.setText("");

                }

                break;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        sessionManager.setInChat(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.cancelAll();

        sessionManager.setInChat(true);
        LocalBroadcastManager.getInstance(Chat.this).unregisterReceiver(onRefresh);
        LocalBroadcastManager.getInstance(Chat.this).registerReceiver(onRefresh, new IntentFilter(getString(R.string.chat)));

        resetPage();
        GetData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sessionManager.setInChat(false);
        LocalBroadcastManager.getInstance(Chat.this).unregisterReceiver(onRefresh);
        VolleyHttp.getInstance(Chat.this).cancelPendingRequests(AppConf.httpTag);

    }

    private BroadcastReceiver onRefresh = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            resetPage();
            GetData();
        }
    };

    private void resetPage() {

        refresh = true;
        page = 0;
        endpage = false;
    }
}
