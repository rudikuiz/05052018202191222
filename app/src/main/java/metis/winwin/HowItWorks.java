package metis.winwin;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;

import com.viewpagerindicator.CirclePageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Adapter.HowtoWorkPageAdapter;

public class HowItWorks extends AppCompatActivity {

    @Bind(R.id.btBack)
    ImageButton btBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.abl)
    AppBarLayout abl;
    @Bind(R.id.vPager)
    ViewPager vPager;
    @Bind(R.id.cpIndicator)
    CirclePageIndicator circleIndicator;

    private final int[] mImages = new int[]{
            R.drawable.new_kesatu,
            R.drawable.new_kedua,
            R.drawable.new_ketiga,
            R.drawable.new_keempat,
            R.drawable.new_kelima
    };

    private final String[] mTitle = new String[]{
            "1. Klik Pinjam Sekarang",
            "2. Isi Aplikasi Pinjaman Online",
            "3. Verifikasi oleh Tim Analis",
            "4. Notifikasi Pengajuan",
            "5. Pinjaman Cari"
    };

    private final String[] mKata = new String[]{
            "di Beranda Website pinjam winwin\n " +
                    "atau bisa melalui aplikasi mobile\n " +
                    "pinjam win/win yang ada di\n" +
                    "playstore.",

            "Dan jangan lupa siapkan dokumen\n" +
                    "pendukung seperti KTP, Foto Diri / \n" +
                    "Selﬁe dan Slip Gaji / Buku Tabungan. \n" +
                    "Semakin lengkap dokumen kamu, \n" +
                    "semakin cepat proses pencairan di \n" +
                    "pinjam win/win",

            "Tim Veriﬁkasi Pinjam WIn/Win akan \n" +
                    "menghubungi kamu. Jika data dan \n" +
                    "dokumen telah terveriﬁkasi, kamu \n" +
                    "akan kembali dihubungi untuk \n" +
                    "pencairan pinjaman.",

            "Dapatkan notiﬁkasi yang memberi \n" +
                    "tahu apakah status pinjaman kamu \n" +
                    "disetujui atau ditolak oleh pinjam \n" +
                    "win/win. Jika disetujui proses akan \n" +
                    "dilanjutkan ke transfer",

            "Dana akan ditransfer pada hari itu\n" +
                    " juga setelah pengajuan di setujui. \n" +
                    "*Kecepatan dana sampai ke \n" +
                    "rekening juga tergantung dari proses \n" +
                    "bank Anda dan dokumen diterima"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_its_work);
        ButterKnife.bind(this);

        HowtoWorkPageAdapter adapter = new HowtoWorkPageAdapter(HowItWorks.this, mImages, mTitle, mKata);
        vPager.setAdapter(adapter);

        circleIndicator.setCentered(true);
        circleIndicator.setViewPager(vPager);

        vPager.setCurrentItem(0);


    }

    @OnClick(R.id.btBack)
    public void onClick() {

        finish();
    }
}
