package android.daehoshin.com.firebasechatting.room;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by daeho on 2017. 11. 2..
 */

public class RoomListRecyclerView extends View {
    private RecyclerView rv;
    private RoomListAdapter adapter;
    private LinearLayoutManager lManager;

    public RoomListRecyclerView(Context context) {
        super(context);

        init();
    }

    private void init(){
        rv = new RecyclerView(this.getContext());

        adapter = new RoomListAdapter();
        rv.setAdapter(adapter);

        lManager = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(lManager);
    }
}
