package android.daehoshin.com.firebasechatting.friend;

import android.daehoshin.com.firebasechatting.R;
import android.daehoshin.com.firebasechatting.common.domain.User;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 11. 2..
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.Holder> {
    List<User> data = new ArrayList<>();

    public void setData(List<User> data){
        this.data = data;
        this.notifyDataSetChanged();
    }

    public List<User> getCheckedUser(){
        List<User> users = new ArrayList<>();
        for(User user : data){
            if(user.isChecked()) users.add(user);
        }

        return users;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);

        return new Holder(v);
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
        private ImageView ivImage;
        private TextView tvName;
        private CheckBox chkUse;
        private CardView cv;
        private User user;

        public Holder(View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);
            tvName = itemView.findViewById(R.id.tvName);
            chkUse = itemView.findViewById(R.id.chkUse);
            cv = itemView.findViewById(R.id.cv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.setChecked(!user.isChecked());
                    setChecked();
                }
            });
        }

        private void setChecked(){
            //chkUse.setChecked(user.isChecked());
            if(user.isChecked()) cv.setBackgroundColor(Color.GRAY);
            else cv.setBackgroundColor(Color.WHITE);
        }

        public void setData(User user){
            this.user = user;

            //tvName.setText(user.getEmail().substring(0,user.getEmail().indexOf("@")));
            tvName.setText(user.getEmail());
            setChecked();
        }
    }
}
