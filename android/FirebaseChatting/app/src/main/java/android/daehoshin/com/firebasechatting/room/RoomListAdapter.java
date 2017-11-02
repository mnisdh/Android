package android.daehoshin.com.firebasechatting.room;

import android.daehoshin.com.firebasechatting.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by daeho on 2017. 11. 2..
 */

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.Holder> {

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent);

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
        private TextView tvMembers, tvLastMsg, tvNewMsg, tvLastMsgDt;

        public Holder(View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);
            tvMembers = itemView.findViewById(R.id.tvMembers);
            tvLastMsg = itemView.findViewById(R.id.tvLastMsg);
            tvNewMsg = itemView.findViewById(R.id.tvNewMsg);
            tvLastMsgDt = itemView.findViewById(R.id.tvLastMsgDt);
        }
    }
}
