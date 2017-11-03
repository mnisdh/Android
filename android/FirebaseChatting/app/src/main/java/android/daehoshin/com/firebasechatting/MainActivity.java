package android.daehoshin.com.firebasechatting;

import android.content.Intent;
import android.daehoshin.com.firebasechatting.common.SignInfo;
import android.daehoshin.com.firebasechatting.common.domain.Manager;
import android.daehoshin.com.firebasechatting.common.domain.User;
import android.daehoshin.com.firebasechatting.friend.FriendRecyclerView;
import android.daehoshin.com.firebasechatting.room.RoomListRecyclerView;
import android.daehoshin.com.firebasechatting.sign.SigninActivity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final int SIGNIN_REQ_CD = 1;

    private TabLayout tl;
    private ViewPager vp;
    FriendRecyclerView friendListView;
    RoomListRecyclerView roomListView;

    FloatingActionButton fab;
    ConstraintLayout popupAddFriend, popupAddRoom;
    EditText etFindName;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        init();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = SignInfo.getInstance().getAuth().getCurrentUser();

        if(currentUser == null || !currentUser.isEmailVerified()){
            Intent intent = new Intent(this, SigninActivity.class);
            startActivityForResult(intent, SIGNIN_REQ_CD);
        }

        Manager.getUser(SignInfo.getInstance().getAuth().getCurrentUser(), new Manager.User_Callback() {
            @Override
            public void getUser(User user) {
                Manager.getInstance().setCurrentUser(user);
                loadData();
            }
        });



//        User user = new User(currentUser.getUid(), currentUser.getEmail(), FirebaseInstanceId.getInstance().getToken());
//        user.setName(currentUser.getDisplayName());
//        user.setPhone_number(currentUser.getPhoneNumber());
//
//        Manager.addUser(user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case SIGNIN_REQ_CD:
                switch (resultCode){
                    case RESULT_CANCELED: finish(); break;
                }
                break;
        }
    }

    private void init(){
        fab = findViewById(R.id.fab);
        popupAddFriend = findViewById(R.id.popupAddFriend);
        popupAddRoom = findViewById(R.id.popupAddRoom);
        etFindName = findViewById(R.id.etFindName);
        progress = findViewById(R.id.progress);
    }

    private void initView(){
        friendListView = new FriendRecyclerView(this);
        roomListView = new RoomListRecyclerView(this);

        Map<String, View> data = new HashMap<>();
        data.put(this.getResources().getString(R.string.tab_friend), friendListView);
        data.put(this.getResources().getString(R.string.tab_rooms), roomListView);

        tl = findViewById(R.id.tl);
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for(String key : data.keySet()) tl.addTab(tl.newTab().setText(key));

        vp = findViewById(R.id.vp);
        MainPagerAdapter adapter = new MainPagerAdapter(this, data);
        vp.setAdapter(adapter);

        // 탭레이아웃을 뷰페이저에 연결
        tl.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp));

        // ViewPager의 변경사항을 탭레이아웃에 전달
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl));


    }

    private void loadData(){
        Manager.getInstance().getFriend(true, new Manager.Friend_Callback() {
            @Override
            public void getFriendList(List<User> friends) {
                friendListView.setData(friends);
            }
        });
    }

    public void addFriend(View v){
        String find = etFindName.getText().toString();

        Manager.getUser(find, new Manager.User_Callback() {
            @Override
            public void getUser(User user) {
                if(user != null){
                    Manager.getInstance().addFriend(user);
                    closeAddFriend();

                    Manager.getInstance().getFriend(true, new Manager.Friend_Callback() {
                        @Override
                        public void getFriendList(List<User> friends) {
                            friendListView.setData(friends);
                        }
                    });
                }
                else{
                    String msg = MainActivity.this.getResources().getString(R.string.friend_find_fail);
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showAddFriend(){
        popupAddFriend.setVisibility(View.VISIBLE);
        fab.setVisibility(View.GONE);
    }
    private void showAddRoom(){
        popupAddRoom.setVisibility(View.VISIBLE);
        fab.setVisibility(View.GONE);
    }
    public void add(View v){
        switch (tl.getSelectedTabPosition()){
            case 0: showAddFriend(); break;
            default: showAddRoom(); break;
        }
    }

    public void closeAddFriend(View v){
        closeAddFriend();
    }
    private void closeAddFriend(){
        popupAddFriend.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
    }

    public void closeAddRoom(View v){
        closeAddRoom();
    }
    private void closeAddRoom(){
        popupAddRoom.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
    }
}
