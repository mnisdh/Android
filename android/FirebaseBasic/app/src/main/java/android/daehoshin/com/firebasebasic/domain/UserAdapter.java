package android.daehoshin.com.firebasebasic.domain;

import android.daehoshin.com.firebasebasic.R;
import android.graphics.Color;
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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {
    private List<User> data = new ArrayList<>();

    public void setUnchecked(){
        User user = getCheckedUser();
        if(user == null) return;

        user.setChecked(false);
        notifyDataSetChanged();
    }

    private User getCheckedUser(){
        for(User user : data){
            if(user.isChecked()) return user;
        }

        return null;
    }

    public String getCheckedUserName(){
        User user = getCheckedUser();
        if(user == null) return "";
        else return user.getUser_id();
    }

    public void setData(List<User> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
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
        private User user;

        public Holder(View itemView) {
            super(itemView);

            tvText = itemView.findViewById(R.id.tvText);
            cvStage = itemView.findViewById(R.id.cvStage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(user.isChecked()){
                        setUnchecked();
                    }
                    else{
                        setUnchecked();
                        user.setChecked(true);
                    }

                    setColor();
                }
            });
        }

        private void setColor(){
            if(user.isChecked()) cvStage.setBackgroundColor(Color.DKGRAY);
            else cvStage.setBackgroundColor(Color.WHITE);
        }

        public void setData(User user){
            this.user = user;

            String text = "";
            text += "id : " + user.getUser_id() + " / name : " + user.getUsername();

            tvText.setText(text);

            setColor();
        }
    }
}
