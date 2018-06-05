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
            R.drawable.kesatu,
            R.drawable.kedua,
            R.drawable.ketiga,
            R.drawable.keempat,
            R.drawable.kelima,
            R.drawable.keenam
    };

    private final String[] mKata = new String[]{
            "Perlu dana darurat ?\n" +
                    "Gajian masih bulan depan ? ",
            "Hubungi admin@pinjamwinwin.com\n" +
                    "download aplikasi winwinclient berbasis\n" +
                    "android kami di googleplay store",
            "Masukkan data-data, serta tentukan\n" +
                    "jam pengambilan dokumen.\n" +
                    "Setelah itu Tim WinWin akan segera\n" +
                    "menghubungi anda",
            " Tim WinWin datang ke kantor atau\n" +
                    "rumah anda untuk mengambil foto\n" +
                    "anda dan verifikasi dokumen \n" +
                    "( KTP/KK asli, Slip Gaji asli 3 bulan,\n" +
                    "Buku Tabungan asli ).",
            "Jika di setujui, proses akan\n" +
                    "di lanjutkan ke transfer.",
            "Dana biasanya di tranfer dalam\n" +
                    "waktu 4 jam.\n" +
                    "( Kecepatan dana sampai ke\n" +
                    "rekening juga tergantung dari proses\n" +
                    "bank anda dan dokumen\n" +
                    "yang diterima )"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_its_work);
        ButterKnife.bind(this);


        HowtoWorkPageAdapter adapter = new HowtoWorkPageAdapter(HowItWorks.this, mImages, mKata);
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
