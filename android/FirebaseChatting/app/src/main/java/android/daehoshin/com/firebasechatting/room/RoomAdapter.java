package android.daehoshin.com.firebasechatting.room;

import android.content.Context;
import android.daehoshin.com.firebasechatting.R;
import android.daehoshin.com.firebasechatting.common.domain.Manager;
import android.daehoshin.com.firebasechatting.common.domain.Msg;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 11. 2..
 */

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.Holder> {
    private List<Msg> data = new ArrayList<>();

    public void setData(List<Msg> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(Msg data){
        this.data.add(data);
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false);

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
        private Context context;

        private ConstraintLayout leftMsg;
        private ImageView leftIvProfile;
        private TextView leftTvName;
        private LinearLayout leftMsgList;
        private TextView leftTvTime;

        private ConstraintLayout rightMsg;
        private LinearLayout rightMsgList;
        private TextView rightTvTime;

        private Msg msg;

        //private List<CardView> msgList = new ArrayList<>();

        public Holder(View itemView) {
            super(itemView);

            this.context = itemView.getContext();

            leftMsg = itemView.findViewById(R.id.leftMsg);
            leftIvProfile = itemView.findViewById(R.id.leftIvProfile);
            leftTvName = itemView.findViewById(R.id.leftTvName);
            leftMsgList = itemView.findViewById(R.id.leftMsgList);
            leftTvTime = itemView.findViewById(R.id.leftTvTime);

            rightMsg = itemView.findViewById(R.id.rightMsg);
            rightMsgList = itemView.findViewById(R.id.rightMsgList);
            rightTvTime = itemView.findViewById(R.id.rightTvTime);
        }

        public void setData(Msg msg){
            leftMsgList.removeAllViews();
            rightMsgList.removeAllViews();
            //msgList.clear();

            this.msg = msg;

            CardView cv = new CardView(context);
            TextView tv = new TextView(context);
            tv.setText(msg.getMsg());
            cv.addView(tv);
            tv.setPadding(30, 30, 30, 30);

            DateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH=24h, hh=12h
            String dt = df.format(msg.getTime());

            if(msg.getUser_id().equals(Manager.getInstance().getCurrentUser().getId())) {
                leftMsg.setVisibility(View.GONE);
                rightMsg.setVisibility(View.VISIBLE);

                cv.setBackgroundColor(Color.YELLOW);
                rightMsgList.addView(cv);

                rightTvTime.setText(dt);
            }
            else {
                leftMsg.setVisibility(View.VISIBLE);
                rightMsg.setVisibility(View.GONE);

                cv.setBackgroundColor(Color.WHITE);
                leftMsgList.addView(cv);

                leftTvName.setText(msg.getUser_email());
                leftTvTime.setText(dt);
            }
        }
    }
}
