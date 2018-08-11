package metis.winwin;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HowItWork2Activity extends AppCompatActivity {

    @Bind(R.id.description_text)
    TextView descriptionText;
    @Bind(R.id.plus)
    ImageButton plus;
    @Bind(R.id.minus)
    ImageButton minus;
    @Bind(R.id.plus2)
    ImageButton plus2;
    @Bind(R.id.minus2)
    ImageButton minus2;
    @Bind(R.id.description_text2)
    TextView descriptionText2;
    @Bind(R.id.plus3)
    ImageButton plus3;
    @Bind(R.id.minus3)
    ImageButton minus3;
    @Bind(R.id.description_text3)
    TextView descriptionText3;
    @Bind(R.id.plus4)
    ImageButton plus4;
    @Bind(R.id.minus4)
    ImageButton minus4;
    @Bind(R.id.description_text4)
    TextView descriptionText4;
    @Bind(R.id.plus5)
    ImageButton plus5;
    @Bind(R.id.minus5)
    ImageButton minus5;
    @Bind(R.id.description_text5)
    TextView descriptionText5;
    int collapsedMaxLines = 20;
    int collapsedMaxLines2 = 20;
    int collapsedMaxLines3 = 20;
    int collapsedMaxLines4 = 20;
    int collapsedMaxLines5 = 20;
    ObjectAnimator animation;
    Boolean returns;

    String atmbca = "1. Masukan kartu ATM BCA dan PIN. \n" +
            "2. Pilih menu Transaksi Lainnya > Transfer > Ke Rekening BCA Virtual Account. \n" +
            "3. Masukan 5 kode perusahaan  untuk PT Progo Puncak Group (10452) dan nomor HP yang terdaftar di akun pinjamwinwin anda tanpa angka 0  (Contoh : 1045285749358008). \n" +
            "4. Di halaman konﬁrmasi, pastikan detil pembayaran sudah sesuai dengan yang anda Inginkan. \n" +
            "5. Masukan jumlah transfer. \n" +
            "6. Ikuti instruksi untuk menyelesaikan transaksi. \n" +
            "7. Simpan struk transaksi sebagai bukti.";

    String bca_mobile = "1. Lakukan log in pada aplikasi BCA Mobile. \n" +
            "2. Pilih menu m-BCA kemudian masukkan kode akses m-BCA. \n" +
            "3. Pilih m-Transfer > BCA Virtual Account. \n" +
            "4. Masukan 5 kode perusahaan  untuk PT Progo Puncak Group (10452) dan nomor HP yang terdaftar di akun pinjamwinwin anda tanpa angka 0  (Contoh : 1045285749358008). \n" +
            "5. Masukan pin m-BCA. \n" +
            "6. Pembayaran selesai. Simpan notiﬁkasi yang muncul sebagai bukti pembayaran.";

    String internet_banking = "1. Login pada alamat Internet Banking BCA (https://klikbca.com). \n" +
            "2. Pilih menu Pembayaran Tagihan > Pembayaran > BCA Virtual Account. \n" +
            "3. Masukan 5 kode perusahaan  untuk PT Progo Puncak Group (10452) dan nomor HP yang terdaftar di akun pinjamwinwin anda tanpa angka 0  (Contoh : 1045285749358008). \n" +
            "4. Di halaman konﬁrmasi, pastikan detil pembayaran sudah sesuai seperti Nomor BCA Virtual Account dan Nama Pelanggan. \n" +
            "5. Masukan password dan mToken. \n" +
            "6. Cetak/Simpan struk pembayaran BCA Virtual Account sebagai bukti pembayaran.";

    String kantor_bank_bca = "1. Ambil nomor antrian transaksi Teller dan isi slip setoran. \n" +
            "2. Serahkan slip dan jumlah setoran kepada Teller BCA. \n" +
            "3. Teller BCA akan melakukan validasi transaksi. \n" +
            "4. Simpan slip setoran hasil validasi sebagai bukti pembayaran.";

    String atm_bank_lain = "1. Masukkan Kartu Debit dan PIN Anda. \n" +
            "2. Pilih menu Transaksi Lainnya > Transfer > Ke Rek Bank Lain. \n" +
            "3. Masukan 5 kode perusahaan  untuk PT Progo Puncak Group (10452) dan nomor HP yang terdaftar di akun pinjamwinwin anda tanpa angka 0  (Contoh : 1045285749358008). \n" +
            "4. Ikuti instruksi untuk menyelesaikan transaksi. \n" +
            "5. Simpan struk transaksi sebagai bukti pembayaran.";
    @Bind(R.id.btBack)
    ImageButton btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_it_work2);
        ButterKnife.bind(this);

        descriptionText.setMaxLines(0);
        animation = ObjectAnimator.ofInt(descriptionText, "minLines",
                -1);
        animation.setDuration(200).start();
        descriptionText2.setMaxLines(0);
        animation = ObjectAnimator.ofInt(descriptionText2, "minLines",
                -1);
        animation.setDuration(200).start();

        descriptionText3.setMaxLines(0);
        animation = ObjectAnimator.ofInt(descriptionText3, "minLines",
                -1);
        animation.setDuration(200).start();

        descriptionText4.setMaxLines(0);
        animation = ObjectAnimator.ofInt(descriptionText4, "minLines",
                -1);
        animation.setDuration(200).start();

        descriptionText5.setMaxLines(0);
        animation = ObjectAnimator.ofInt(descriptionText5, "minLines",
                -1);
        animation.setDuration(200).start();
    }

    @OnClick({R.id.plus, R.id.minus, R.id.plus2, R.id.minus2, R.id.plus3, R.id.minus3, R.id.plus4, R.id.minus4, R.id.plus5, R.id.minus5, R.id.btBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.plus:
                plus.setVisibility(View.GONE);
                minus.setVisibility(View.VISIBLE);
                descriptionText.setText(atmbca);
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
                descriptionText2.setText(bca_mobile);
                minus2.setVisibility(View.VISIBLE);
                animation = ObjectAnimator.ofInt(descriptionText2, "maxLines",
                        descriptionText2.getMaxLines() == collapsedMaxLines2 ? descriptionText2.getLineCount() : collapsedMaxLines2);
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
            case R.id.plus3:
                plus3.setVisibility(View.GONE);
                descriptionText3.setText(internet_banking);
                minus3.setVisibility(View.VISIBLE);
                animation = ObjectAnimator.ofInt(descriptionText3, "maxLines",
                        descriptionText3.getMaxLines() == collapsedMaxLines3 ? descriptionText3.getLineCount() : collapsedMaxLines3);
                animation.setDuration(200).start();
                break;
            case R.id.minus3:
                minus3.setVisibility(View.GONE);
                plus3.setVisibility(View.VISIBLE);
                descriptionText3.setMaxLines(0);
                animation = ObjectAnimator.ofInt(descriptionText3, "minLines",
                        -1);
                animation.setDuration(200).start();
                break;
            case R.id.plus4:
                plus4.setVisibility(View.GONE);
                descriptionText4.setText(kantor_bank_bca);
                minus4.setVisibility(View.VISIBLE);
                animation = ObjectAnimator.ofInt(descriptionText4, "maxLines",
                        descriptionText4.getMaxLines() == collapsedMaxLines4 ? descriptionText4.getLineCount() : collapsedMaxLines4);
                animation.setDuration(200).start();
                break;
            case R.id.minus4:
                minus4.setVisibility(View.GONE);
                plus4.setVisibility(View.VISIBLE);
                descriptionText4.setMaxLines(0);
                animation = ObjectAnimator.ofInt(descriptionText4, "minLines",
                        -1);
                animation.setDuration(200).start();
                break;
            case R.id.plus5:
                plus5.setVisibility(View.GONE);
                descriptionText5.setText(atm_bank_lain);
                minus5.setVisibility(View.VISIBLE);
                animation = ObjectAnimator.ofInt(descriptionText5, "maxLines",
                        descriptionText5.getMaxLines() == collapsedMaxLines5 ? descriptionText5.getLineCount() : collapsedMaxLines5);
                animation.setDuration(200).start();
                break;
            case R.id.minus5:
                minus5.setVisibility(View.GONE);
                plus5.setVisibility(View.VISIBLE);
                descriptionText5.setMaxLines(0);
                animation = ObjectAnimator.ofInt(descriptionText5, "minLines",
                        -1);
                animation.setDuration(200).start();
                break;

            case R.id.btBack:
                finish();
                break;
        }
    }
}
