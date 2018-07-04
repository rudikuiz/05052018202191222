package metis.winwin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Utils.SessionManager;

public class TermCondition extends AppCompatActivity {

    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.textdengan)
    TextView textdengan;
    @Bind(R.id.rel)
    RelativeLayout rel;
    @Bind(R.id.btSetuju)
    Button btSetuju;
    @Bind(R.id.btTidak)
    Button btTidak;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_condition);
        ButterKnife.bind(this);


        sessionManager = new SessionManager(TermCondition.this);
//        SpannableString content = new SpannableString("Dengan menggunakan aplikasi winwin mobile apps, saya menyetujui syarat dan ketentuan yang berlaku.");
//        content.setSpan(new UnderlineSpan(), 54, 65, 0);
//        textdengan.setText(content);

        String link = "Dengan menggunakan aplikasi winwin mobile apps, saya menyetujui <font color=\"blue\"><u><i><a href=\"https://www.pinjamwinwin.com/syaratketentuanmobile\">syarat dan ketentuan</a></i></u></font> yang berlaku.";
        textdengan.setMovementMethod(LinkMovementMethod.getInstance());
        textdengan.setText(Html.fromHtml(link));


        btSetuju.setText("Setuju & Lanjutkan");
        btTidak.setText("Tidak");


    }

    @OnClick({R.id.btSetuju, R.id.btTidak})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btSetuju:

                sessionManager.setFirstlook();
                Intent intent = new Intent(TermCondition.this, MainActivity.class);
                startActivity(intent);
                finish();

                break;
            case R.id.btTidak:
                finish();
                break;
        }
    }
}
