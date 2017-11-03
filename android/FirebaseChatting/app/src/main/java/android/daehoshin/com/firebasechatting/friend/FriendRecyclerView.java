package android.daehoshin.com.firebasechatting.friend;

import android.content.Context;
import android.daehoshin.com.firebasechatting.common.domain.User;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by daeho on 2017. 11. 2..
 */

public class FriendRecyclerView extends RecyclerView {
    //private RecyclerView rv;
    private FriendAdapter adapter;
    private GridLayoutManager lManager;

    public FriendRecyclerView(Context context) {
        super(context);

        init();
    }

    private void init(){
        //rv = new RecyclerView(this.getContext());

        adapter = new FriendAdapter();
        this.setAdapter(adapter);

        lManager = new GridLayoutManager(this.getContext(), 3);
        this.setLayoutManager(lManager);
    }

    public void setData(List<User> data){
        adapter.setData(data);
    }
}
