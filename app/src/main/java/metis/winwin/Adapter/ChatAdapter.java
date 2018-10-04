package metis.winwin.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import metis.winwin.Model.ChatModel;
import metis.winwin.R;
import metis.winwin.Utils.SessionManager;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    private ArrayList<ChatModel> dataSet;
    private Context context;
    private Intent intent;
    private SessionManager sessionManager;

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.txNama)
        TextView txNama;
        @Bind(R.id.txMessage)
        TextView txMessage;
        @Bind(R.id.txTime)
        TextView txTime;
        @Bind(R.id.imgStatus)
        ImageView imgStatus;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


        }
    }

    public ChatAdapter(Context context, ArrayList<ChatModel> data) {
        this.dataSet = data;
        this.context = context;
        this.sessionManager = new SessionManager(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = null;

        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_chat_left, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_chat_right, parent, false);
                break;
        }


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        final ChatModel value = dataSet.get(listPosition);

        holder.txNama.setText(value.getNama());
        holder.txMessage.setText(value.getText());
        holder.txTime.setText(value.getWaktu());
        holder.imgStatus.setImageResource(value.getImgstatus());

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

        final ChatModel value = dataSet.get(position);
        int layout = 0;
        if (value.getIduser().equals(sessionManager.getIdhq())) {
            layout = 1;
        }
        return layout;
    }


}


