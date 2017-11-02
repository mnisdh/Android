package android.daehoshin.com.firebasechatting.friend;

import android.daehoshin.com.firebasechatting.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by daeho on 2017. 11. 2..
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.Holder> {

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder{
        private ImageView ivImage;
        private TextView tvName;
        private CheckBox chkUse;

        public Holder(View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);
            tvName = itemView.findViewById(R.id.tvName);
            chkUse = itemView.findViewById(R.id.chkUse);
        }
    }
}
