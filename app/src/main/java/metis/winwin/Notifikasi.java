package metis.winwin;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import metis.winwin.Adapter.NotifikasiAdapter;
import metis.winwin.Model.Notif;
import metis.winwin.Utils.DatabaseHandler;

public class Notifikasi extends AppCompatActivity {

    @Bind(R.id.btBack)
    ImageButton btBack;
    @Bind(R.id.rvData)
    RecyclerView rvData;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<Notif> dataSet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);
        ButterKnife.bind(this);
        layoutManager = new LinearLayoutManager(Notifikasi.this);
        rvData.setLayoutManager(layoutManager);

        adapter = new NotifikasiAdapter(Notifikasi.this, dataSet);
        rvData.setAdapter(adapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                swipeRefreshLayout.setRefreshing(false);

            }
        });


        GetData();
    }

    private void GetData() {

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        List<Notif> notifs = new ArrayList<>();
        notifs = databaseHandler.getAllNotifs();

        for (int i=0 ; i<notifs.size(); i++) {
            dataSet.add(notifs.get(i));
        }

//        NotifikasiModel model = new NotifikasiModel();
//        model.setNama("Mujiono");
//        model.setBagian("Analis");
//        model.setTanggal("22/05/2017");
//        model.setAlamat("Griya Asri Permai B-11");
//        dataSet.add(model);
//
//
//        model = new NotifikasiModel();
//        model.setNama("Fikri Anansiah");
//        model.setBagian("Colection");
//        model.setTanggal("11/06/2017");
//        model.setAlamat("Perumahan Merpati L-17");
//        dataSet.add(model);
//
//
//        model = new NotifikasiModel();
//        model.setNama("Dimas Fajar");
//        model.setBagian("Analis");
//        model.setTanggal("22/05/2017");
//        model.setAlamat("Jl. Menaggal 57");
//        dataSet.add(model);


        adapter.notifyDataSetChanged();

    }

    @OnClick(R.id.btBack)
    public void onClick() {

        finish();
    }
}
