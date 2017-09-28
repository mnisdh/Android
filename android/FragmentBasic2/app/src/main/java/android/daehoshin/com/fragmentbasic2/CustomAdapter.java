package android.daehoshin.com.fragmentbasic2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 27..
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {
    private List<String> data = new ArrayList<>();
    private Context context;

    public CustomAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new Holder(view, (Callback) context);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setTvText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView tvText;
        Callback callback;

        public Holder(View itemView, final Callback callback) {
            super(itemView);

            this.callback = callback;
            tvText = (TextView) itemView.findViewById(R.id.tvText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.goDetail(tvText.getText().toString());
                }
            });
        }

        public void setTvText(String text) { tvText.setText(text); }
    }
}
