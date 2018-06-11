package metis.winwin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MetodePembayaran extends AppCompatActivity {

    @Bind(R.id.btBack)
    ImageButton btBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btTransfer)
    ImageView btTransfer;
    @Bind(R.id.btVirtualAccount)
    ImageView btVirtualAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metode_pembayaran);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btBack, R.id.btTransfer, R.id.btVirtualAccount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                Intent intent = new Intent(MetodePembayaran.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.btTransfer:
                startActivity(new Intent(MetodePembayaran.this, BCATransfer.class));
                break;
            case R.id.btVirtualAccount:
                startActivity(new Intent(MetodePembayaran.this, WebViewVirtualAccount.class));
                break;
        }
    }
}
