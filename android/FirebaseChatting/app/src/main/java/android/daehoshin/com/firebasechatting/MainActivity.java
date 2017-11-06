package android.daehoshin.com.firebasechatting;

import android.content.Intent;
import android.daehoshin.com.firebasechatting.common.SignInfo;
import android.daehoshin.com.firebasechatting.common.domain.Manager;
import android.daehoshin.com.firebasechatting.common.domain.Room;
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
    MainPagerAdapter adapter;
    FriendRecyclerView friendListView;
    RoomListRecyclerView roomListView;

    FloatingActionButton fabMenu, fabAddFriend, fabAddRoom;
    ConstraintLayout popupAddFriend, popupAddRoom;
    EditText etFindName, etRoomName;
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
        fabMenu = findViewById(R.id.fabMenu);
        fabAddFriend = findViewById(R.id.fabAddFriend);
        fabAddRoom = findViewById(R.id.fabAddRoom);

        popupAddFriend = findViewById(R.id.popupAddFriend);
        popupAddRoom = findViewById(R.id.popupAddRoom);
        etFindName = findViewById(R.id.etFindName);
        etRoomName = findViewById(R.id.etRoomName);
        progress = findViewById(R.id.progress);
    }

    private void initView(){
        friendListView = new FriendRecyclerView(this);
        roomListView = new RoomListRecyclerView(this);

        Map<String, View> data = new HashMap<>();
        data.put(this.getResources().getString(R.string.tab_friend), friendListView);
        data.put(this.getResources().getString(R.string.tab_rooms), roomListView);

        tl = findViewById(R.id.tl);
        for(String key : data.keySet()) tl.addTab(tl.newTab().setText(key));

        vp = findViewById(R.id.vp);
        adapter = new MainPagerAdapter(this, data);
        vp.setAdapter(adapter);

        // 탭레이아웃을 뷰페이저에 연결
        tl.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp));

        // ViewPager의 변경사항을 탭레이아웃에 전달
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl));

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                switch (position){
//                    case 0:
//                        fabAddRoom.setVisibility(View.GONE);
//                        fabAddFriend.setVisibility(View.VISIBLE);
//                        break;
//                    case 1:
//                        fabAddFriend.setVisibility(View.GONE);
//                        fabAddRoom.setVisibility(View.VISIBLE);
//                        break;
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadData(){
        Manager.getInstance().getFriend(true, new Manager.Friend_Callback() {
            @Override
            public void getFriendList(List<User> friends) {
                friendListView.setData(friends);

                Manager.getInstance().getRoom(true, new Manager.Room_Callback() {
                    @Override
                    public void getRooms(List<Room> rooms) {
                        roomListView.setData(rooms);
                    }
                });
            }
        });
    }

    public void showMenu(View v){
        if(fabAddFriend.getVisibility() == View.VISIBLE){
            fabAddFriend.setVisibility(View.GONE);
            fabAddRoom.setVisibility(View.GONE);
        }
        else{
            fabAddFriend.setVisibility(View.VISIBLE);
            fabAddRoom.setVisibility(View.VISIBLE);
        }
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
    public void addRoom(View v){
        User currentUser = Manager.getInstance().getCurrentUser();

        Room room = new Room();
        room.setCreate_dt(System.currentTimeMillis());
        room.setCreate_user_id(currentUser.getId());
        room.setMsg_count(0);
        room.setTitle(etRoomName.getText().toString());

        Manager.getInstance().addRoom(room, friendListView.getCheckedUser());

        Manager.getInstance().getRoom(true, new Manager.Room_Callback() {
            @Override
            public void getRooms(List<Room> rooms) {
                roomListView.setData(rooms);
            }
        });

        closeAddRoom();
    }

    public void showAddFriend(View v){
        popupAddFriend.setVisibility(View.VISIBLE);
    }
    public void showAddRoom(View v) {
        if (friendListView.getCheckedUser().size() == 0) {
            Toast.makeText(this, MainActivity.this.getResources().getString(R.string.room_create_fail), Toast.LENGTH_SHORT).show();
        } else {
            popupAddRoom.setVisibility(View.VISIBLE);
        }
    }

    public void closeAddFriend(View v){
        closeAddFriend();
    }
    private void closeAddFriend(){
        popupAddFriend.setVisibility(View.GONE);
    }
    public void closeAddRoom(View v){
        closeAddRoom();
    }
    private void closeAddRoom(){
        popupAddRoom.setVisibility(View.GONE);
    }
}
