package metis.winwin.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import metis.winwin.Model.KelurahanModel;
import metis.winwin.R;

public class KelurahanAdapter extends RecyclerView.Adapter<KelurahanAdapter.KelurahanHolder> {

    private ArrayList<KelurahanModel> arrayList;
    private Context context;

    public KelurahanAdapter(ArrayList<KelurahanModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public KelurahanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_kelurahan, parent, false);
        return new KelurahanHolder(view);
    }

    @Override
    public void onBindViewHolder(KelurahanHolder holder, final int position) {
        int i = position + 1;
        holder.number.setText(String.valueOf(i));
        holder.kelurahan.setText(arrayList.get(position).getKel());
        holder.kec.setText(arrayList.get(position).getKec());
        holder.kota.setText(arrayList.get(position).getKota());
        holder.kelurahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan(arrayList.get(position).getKel(),
                        arrayList.get(position).getKec(),
                        arrayList.get(position).getKota());

            }

        });
    }

    public void simpan(String kel,String kec,String kota) {

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class KelurahanHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.number)
        TextView number;
        @Bind(R.id.kelurahan)
        TextView kelurahan;
        @Bind(R.id.kec)
        TextView kec;
        @Bind(R.id.kota)
        TextView kota;

        public KelurahanHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
