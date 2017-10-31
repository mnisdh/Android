package android.daehoshin.com.firebasebasic2.domain;

import android.daehoshin.com.firebasebasic2.R;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 31..
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {
    private List<User> data = new ArrayList<>();
    private ICallback callback;

    public UserAdapter(ICallback callback){
        this.callback = callback;
    }

    public void setData(List<User> data){
        this.data = data;
        notifyDataSetChanged();
    }

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

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        User user = data.get(position);

        holder.setData(user);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private ConstraintLayout stage;
        private TextView tvEmail;
        private User user;

        public Holder(View itemView) {
            super(itemView);

            stage = itemView.findViewById(R.id.stage);
            tvEmail = itemView.findViewById(R.id.tvEmail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(user.isChecked()){
                        setUnchecked();
                    }
                    else{
                        setUnchecked();
                        user.setChecked(true);
                        setCheck();
                        callback.checkedChangedUser(user);
                    }
                }
            });
        }

        private void setCheck(){
            if(user.isChecked()) stage.setBackgroundColor(Color.RED);
            else stage.setBackgroundColor(Color.WHITE);
        }

        public void setData(User user){
            this.user = user;

            tvEmail.setText(user.getEmail());

            setCheck();
        }
    }

    public interface ICallback{
        void checkedChangedUser(User user);
    }
}
