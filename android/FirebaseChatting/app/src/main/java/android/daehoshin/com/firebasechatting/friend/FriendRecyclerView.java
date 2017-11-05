package android.daehoshin.com.firebasechatting.friend;

import android.content.Context;
import android.daehoshin.com.firebasechatting.common.domain.User;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by daeho on 2017. 11. 2..
 */

public class FriendRecyclerView extends FrameLayout {
    private RecyclerView rv;
    private FriendAdapter adapter;
    private GridLayoutManager lManager;

    public FriendRecyclerView(Context context) {
        super(context);

        init();
    }

    private void init(){
        rv = new RecyclerView(this.getContext());

        adapter = new FriendAdapter();
        rv.setAdapter(adapter);

//        lManager = new GridLayoutManager(this.getContext(), 3);
//        rv.setLayoutManager(lManager);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        addView(rv);
    }

    public void setData(List<User> data){
        adapter.setData(data);
    }
}
