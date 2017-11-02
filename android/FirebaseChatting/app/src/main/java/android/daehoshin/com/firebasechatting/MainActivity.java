package android.daehoshin.com.firebasechatting;

import android.content.Intent;
import android.daehoshin.com.firebasechatting.common.SignInfo;
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

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int SIGNIN_REQ_CD = 1;

    private TabLayout tl;
    private ViewPager vp;
    FloatingActionButton fab;
    ConstraintLayout popupAddFriend, popupAddRoom;
    EditText etFindName;

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
        if(currentUser == null){
            Intent intent = new Intent(this, SigninActivity.class);
            startActivityForResult(intent, SIGNIN_REQ_CD);
        }
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
    }

    private void initView(){
        List<String> data = new ArrayList<>();
        data.add(this.getResources().getString(R.string.tab_friend));
        data.add(this.getResources().getString(R.string.tab_rooms));

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
        for(String item : data) tl.addTab(tl.newTab().setText(item));

        vp = findViewById(R.id.vp);
        MainPagerAdapter adapter = new MainPagerAdapter(this, data);
        vp.setAdapter(adapter);

        // 탭레이아웃을 뷰페이저에 연결
        tl.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp));

        // ViewPager의 변경사항을 탭레이아웃에 전달
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl));
    }

    public void addFriend(View v){
        String find = etFindName.getText().toString();

        boolean isSuccess = true;

        if(isSuccess) closeAddFriend();
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
