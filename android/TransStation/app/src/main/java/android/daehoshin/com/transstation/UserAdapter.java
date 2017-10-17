package android.daehoshin.com.transstation;

import android.daehoshin.com.transstation.domain.Row;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 16..
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {
    List<Row> data = new ArrayList<>();

    public UserAdapter(List<Row> data){
        this.data = data;
    }


    @Override
    public UserAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_list, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Row item = data.get(position);
        holder.setData(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class Holder extends ViewHolder {
        TextView tvNo, tvTitle, tvDay;

        public Holder(View itemView) {
            super(itemView);

            tvNo = (TextView) itemView.findViewById(R.id.tvNo);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDay = (TextView) itemView.findViewById(R.id.tvDay);
        }

        public void setData(Row item){
            tvNo.setText(item.getSN());
            tvTitle.setText(item.getSTATN_NM());
            tvDay.setText(item.getWKDAY() + " / " + item.getSATDAY() + " / " + item.getSUNDAY());
        }
    }


}
