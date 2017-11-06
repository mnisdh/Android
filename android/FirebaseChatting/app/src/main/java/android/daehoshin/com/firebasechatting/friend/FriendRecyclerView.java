package android.daehoshin.com.firebasechatting.friend;

import android.content.Context;
import android.daehoshin.com.firebasechatting.common.domain.User;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by daeho on 2017. 11. 2..
 */

public class FriendRecyclerView extends RecyclerView {
    private FriendAdapter adapter;
    private LinearLayoutManager lManager;

    public FriendRecyclerView(Context context) {
        super(context);

        init();
    }

    private void init(){
        adapter = new FriendAdapter();
        setAdapter(adapter);

        lManager = new LinearLayoutManager(this.getContext());
        setLayoutManager(lManager);
    }

    public void setData(List<User> data){
        adapter.setData(data);
    }

    public List<User> getCheckedUser(){
        return adapter.getCheckedUser();
    }
}
