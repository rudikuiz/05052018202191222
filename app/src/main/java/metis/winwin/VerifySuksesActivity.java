package metis.winwin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifySuksesActivity extends AppCompatActivity {

    @Bind(R.id.btDone)
    Button btDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_sukses);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btDone)
    public void onViewClicked() {
        finish();
    }
}
