package android.daehoshin.com.remotebbs;

import android.daehoshin.com.remotebbs.domain.Bbs;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 26..
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {
    private List<Bbs> data = new ArrayList<>();

    public CustomAdapter(List<Bbs> data){
        this.data = data;
    }

    public void addDataAndRefresh(List<Bbs> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public CustomAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.Holder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private TextView tvNo, tvTitle;
        private Bbs data;

        public Holder(View itemView) {
            super(itemView);

            tvNo = itemView.findViewById(R.id.tvNo);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        public void setData(Bbs data){
            this.data = data;
            tvNo.setText(data.getNo());
            tvTitle.setText(data.getTitle());
        }
    }
}
