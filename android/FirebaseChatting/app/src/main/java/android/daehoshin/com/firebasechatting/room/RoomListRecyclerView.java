package android.daehoshin.com.firebasechatting.room;

import android.content.Context;
import android.daehoshin.com.firebasechatting.common.domain.Room;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by daeho on 2017. 11. 2..
 */

public class RoomListRecyclerView extends RecyclerView {
    private RoomListAdapter adapter;
    private LinearLayoutManager lManager;

    public RoomListRecyclerView(Context context) {
        super(context);

        init();
    }

    private void init(){
        adapter = new RoomListAdapter();
        setAdapter(adapter);

        lManager = new LinearLayoutManager(this.getContext());
        setLayoutManager(lManager);
    }

    public void setData(List<Room> room){
        adapter.setData(room);
    }
}
