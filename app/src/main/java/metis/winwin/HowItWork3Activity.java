package metis.winwin;

import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Utils.AndLog;
import metis.winwin.Utils.GlobalToast;

public class HowItWork3Activity extends AppCompatActivity {

    @Bind(R.id.btBack)
    ImageButton btBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.abl)
    AppBarLayout abl;
    @Bind(R.id.plus)
    ImageButton plus;
    @Bind(R.id.minus)
    ImageButton minus;
    @Bind(R.id.description_text)
    TextView descriptionText;
    @Bind(R.id.plus2)
    ImageButton plus2;
    @Bind(R.id.minus2)
    ImageButton minus2;
    @Bind(R.id.description_text2)
    TextView descriptionText2;
    int collapsedMaxLines = 20;
    ObjectAnimator animation;

    String reques_bayar_sebagian = "1. Login ke Akun anda. \n" +
            "2. Tekan Menu dengan swipe kekanan atau tombol menu. \n" +
            "3. Pilih Menu Request Bayar Sebagian, tekan “Lanjutkan”. \n" +
            "4. Masukan nominal transfer dengan minimal sejumlah Rp 500.000, Jika ingin melakukan pembayaran dibawah angka tersebut maka silahkan menggunakan menu “Request Code Bayar”. \n" +
            "5. Pilih Metode Pembayaran dengan Bank Transfer / Virtual Account. \n" +
            "6. Jika menggunakan Bank Transfer Harap Bayarkan sesuai nominal yang tertera di aplikasi, 3 angka tambahan merupakan kode unik yang wajib di input agar pembayaran Anda terdeteksi sistem. \n" +
            "7. Silahkan lakukan pembayaran ke rekening BCA PT Progo Puncak Group 0888825358 sesuai waktu yang tertera di aplikasi.";

    String reques_kode_bayar = "1. Login ke Akun anda. \n" +
            "2. Tekan Menu dengan swipe kekanan atau tombol menu. \n" +
            "3. Pilih Menu Request Kode Bayar. \n" +
            "4. Pilih Metode Pembayaran dengan Bank Transfer / Virtual Account. \n" +
            "5. Jika menggunakan Bank Transfer Harap Bayarkan sesuai nominal yang tertera di aplikasi, 3 angka tambahan merupakan kode unik yang wajib di input agar pembayaran Anda terdeteksi sistem. \n" +
            "6. Silahkan lakukan pembayaran ke rekening BCA PT Progo Puncak Group 0888825358 sesuai waktu yang tertera di aplikasi.";
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.copy)
    TextView copy;
    @Bind(R.id.id_rekening)
    TextView idRekening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_it_work3);
        ButterKnife.bind(this);
        descriptionText.setMaxLines(0);
        animation = ObjectAnimator.ofInt(descriptionText, "minLines",
                -1);
        animation.setDuration(200).start();
        descriptionText2.setMaxLines(0);
        animation = ObjectAnimator.ofInt(descriptionText2, "minLines",
                -1);
        animation.setDuration(200).start();

        title.setText("Cara Pembayaran Request Bayar Sebagian &\n" +
                " Request Kode Bayar");
    }

    @OnClick({R.id.btBack, R.id.plus, R.id.minus, R.id.plus2, R.id.minus2, R.id.copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                finish();
                break;
            case R.id.plus:
                plus.setVisibility(View.GONE);
                minus.setVisibility(View.VISIBLE);
                descriptionText.setText(reques_bayar_sebagian);
                animation = ObjectAnimator.ofInt(descriptionText, "maxLines",
                        descriptionText.getMaxLines() == collapsedMaxLines ? descriptionText.getLineCount() : collapsedMaxLines);
                animation.setDuration(200).start();

                break;
            case R.id.minus:
                minus.setVisibility(View.GONE);
                plus.setVisibility(View.VISIBLE);

                descriptionText.setMaxLines(0);
                animation = ObjectAnimator.ofInt(descriptionText, "minLines",
                        -1);
                animation.setDuration(200).start();
                break;
            case R.id.plus2:
                plus2.setVisibility(View.GONE);
                descriptionText2.setText(reques_kode_bayar);
                minus2.setVisibility(View.VISIBLE);
                animation = ObjectAnimator.ofInt(descriptionText2, "maxLines",
                        descriptionText2.getMaxLines() == collapsedMaxLines ? descriptionText2.getLineCount() : collapsedMaxLines);
                animation.setDuration(200).start();
                break;
            case R.id.minus2:
                minus2.setVisibility(View.GONE);
                plus2.setVisibility(View.VISIBLE);
                descriptionText2.setMaxLines(0);
                animation = ObjectAnimator.ofInt(descriptionText2, "minLines",
                        -1);
                animation.setDuration(200).start();
                break;
            case R.id.copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", idRekening.getText().toString());
                clipboard.setPrimaryClip(clip);
                GlobalToast.ShowToast(HowItWork3Activity.this, "Text telah ter Copy");
                AndLog.ShowLog("text:", clip.toString());
                break;
        }
    }
}
