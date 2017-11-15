package android.daehoshin.com.rxbasic03;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>{
    List<String> data = new ArrayList<>();

    public void setDataAndRefresh(List<String> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void addDataAndRefresh(String data){
        this.data.add(data);
        notifyDataSetChanged();
    }

    public void clear(){
        this.data.clear();
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.text1.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView text1;
        public Holder(View itemView) {
            super(itemView);

            text1 = itemView.findViewById(android.R.id.text1);
        }
    }
}