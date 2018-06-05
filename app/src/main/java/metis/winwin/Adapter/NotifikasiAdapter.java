package metis.winwin.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import metis.winwin.Model.Notif;
import metis.winwin.R;

public class NotifikasiAdapter extends RecyclerView.Adapter<NotifikasiAdapter.MyViewHolder> {


    private ArrayList<Notif> dataSet;
    private Context context;
    private Intent intent;

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.message)
        TextView txNama;
//        @Bind(R.id.txBagian)
//        TextView txBagian;
//        @Bind(R.id.txTanggal)
//        TextView txTanggal;
//        @Bind(R.id.txAlamat)
//        TextView txAlamat;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


        }
    }

    public NotifikasiAdapter(Context context, ArrayList<Notif> data) {
        this.dataSet = data;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_notifikasi, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        final Notif value = dataSet.get(listPosition);

        holder.txNama.setText(value.getMessage());
//        holder.txBagian.setText(value.getBagian());
//        holder.txTanggal.setText(value.getTanggal());
//        holder.txAlamat.setText(value.getAlamat());

    }

    public void load() {

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


}


