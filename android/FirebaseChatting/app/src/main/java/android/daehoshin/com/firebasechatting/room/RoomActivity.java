package android.daehoshin.com.firebasechatting.room;

import android.daehoshin.com.firebasechatting.R;
import android.daehoshin.com.firebasechatting.common.Db;
import android.daehoshin.com.firebasechatting.common.domain.Manager;
import android.daehoshin.com.firebasechatting.common.domain.Msg;
import android.daehoshin.com.firebasechatting.common.domain.User;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RoomActivity extends AppCompatActivity {
    private String roomId = "";
    private long msgCount = 0;

    private RoomAdapter adapter;
    private RecyclerView rvMsg;
    private EditText etMsg;

    private DatabaseReference msgRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        roomId = getIntent().getStringExtra("ROOM_ID");
        msgCount = getIntent().getLongExtra("MSG_COUNT", 0);

        init();

        msgRef = Db.getInstance().getReference("room_msg/" + roomId);
        msgRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() > adapter.getItemCount()){
                    int idx = 0;
                    for(DataSnapshot item : dataSnapshot.getChildren()){
                        if(idx >= adapter.getItemCount()){
                            adapter.addData(item.getValue(Msg.class));
                        }

                        idx++;
                    }

                    rvMsg.scrollToPosition(adapter.getItemCount() - 1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init(){
        etMsg = findViewById(R.id.etMsg);

        rvMsg = findViewById(R.id.rvMsg);
        adapter = new RoomAdapter();
        rvMsg.setAdapter(adapter);
        rvMsg.setLayoutManager(new LinearLayoutManager(this));
    }

    public void send(View v){
        User currentUser = Manager.getInstance().getCurrentUser();
        Msg msg = new Msg();
        msg.setMsg(etMsg.getText().toString());
        msg.setTime(System.currentTimeMillis());
        msg.setUser_id(currentUser.getId());
        msg.setUser_email(currentUser.getEmail());
        msg.setType("String");
        msg.setRoom_id(roomId);
        msg.setIdx(adapter.getItemCount() + 1);
        msgRef.child(msg.getIdx() + "").setValue(msg);

        etMsg.setText("");
    }
}
