package android.daehoshin.com.firebasebasic.domain;

import android.daehoshin.com.firebasebasic.R;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 30..
 */

public class BbsAdapter extends RecyclerView.Adapter<BbsAdapter.Holder> {
    private List<Bbs> data = new ArrayList<>();

    public void setData(List<Bbs> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bbs_item, parent, false);
         return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private TextView tvText;
        private CardView cvStage;
        private Bbs bbs;

        public Holder(View itemView) {
            super(itemView);

            tvText = itemView.findViewById(R.id.tvText);
            cvStage = itemView.findViewById(R.id.cvStage);
        }

        public void setData(Bbs bbs){
            this.bbs = bbs;

            String text = "";
            text += "title : " + bbs.getTitle() + " / name : " + bbs.getUser_id();

            tvText.setText(text);
        }
    }
}
