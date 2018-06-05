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
import metis.winwin.Model.HistoryModel;
import metis.winwin.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {


    private ArrayList<HistoryModel> dataSet;
    private Context context;
    private Intent intent;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txTanggal)
        TextView txTanggal;
        @Bind(R.id.txStatus)
        TextView txStatus;
        @Bind(R.id.txPengajuan)
        TextView txPengajuan;
        @Bind(R.id.txKeterangan)
        TextView txKeterangan;


        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


        }
    }

    public HistoryAdapter(Context context, ArrayList<HistoryModel> data) {
        this.dataSet = data;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_history, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        final HistoryModel value = dataSet.get(listPosition);

        holder.txTanggal.setText(value.getTanggal());
        holder.txPengajuan.setText(value.getPengajuan());
        holder.txStatus.setText(value.getStatus());
        holder.txKeterangan.setText(value.getKeterangan());

        if ((listPosition >= getItemCount() - 1)) {
            load();
        }

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


