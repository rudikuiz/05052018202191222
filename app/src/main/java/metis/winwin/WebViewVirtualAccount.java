package metis.winwin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import metis.winwin.Utils.AndLog;

public class WebViewVirtualAccount extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.web)
    WebView mWebView;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipe;
    WebSettings webSettings;
    @Bind(R.id.editext)
    EditText editText;
    String nama, nohp, nominal, id_peng;

    private ProgressDialog progressDialog;
    private static final String TAG = "WebViewLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_transfer);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id_peng = intent.getStringExtra("id_peng");
        nominal = intent.getStringExtra("nominal");
        nohp = intent.getStringExtra("nohp");
        nama = intent.getStringExtra("nama");
        String url = "https://hq.ppgwinwin.com/winwin/api/payment/paymentv2.php?nominal=" + nominal + "&nama=" + nama + "&id=" + id_peng + "&hp=" + nohp + "";
        swipe.setOnRefreshListener(this);
        webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.getUseWideViewPort();


        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                swipe.setRefreshing(false);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                progressDialog = ProgressDialog.show(WebViewVirtualAccount.this, "Winwin", "Loading...");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                swipe.setRefreshing(false);
                if (url.contains("finish.php")){
                    finish();
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                swipe.setRefreshing(false);

            }

//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed(); // Ignore SSL certificate errors
//            }

            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(WebViewVirtualAccount.this);
                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += " Do you want to continue anyway?";

                builder.setTitle("SSL Certificate Error");
                builder.setMessage(message);
                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                        finish();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        mWebView.loadUrl(url);

    }


    @Override
    public void onBackPressed() {
//        if (mWebView.canGoBack()) {
//            mWebView.goBack();
//        } else {
            super.onBackPressed();
//        }
    }

    @Override
    public void onRefresh() {
        mWebView.reload();
    }
}
