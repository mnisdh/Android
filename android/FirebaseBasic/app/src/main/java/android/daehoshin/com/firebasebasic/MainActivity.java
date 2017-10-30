package android.daehoshin.com.firebasebasic;

import android.daehoshin.com.firebasebasic.domain.Bbs;
import android.daehoshin.com.firebasebasic.domain.BbsAdapter;
import android.daehoshin.com.firebasebasic.domain.User;
import android.daehoshin.com.firebasebasic.domain.UserAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference myRef;
    DatabaseReference rootRef;
    DatabaseReference bbsRef;
    DatabaseReference userRef;

    TextView tvMessage;
    EditText etMessage;

    EditText etId, etName, etTitle;
    RecyclerView rvSign, rvBbs;

    UserAdapter userAdapter;
    BbsAdapter bbsAdapter;

    @Override
    protected void onResume() {
        super.onResume();

        //myRef.addValueEventListener(valueEventListener);
        userRef.addValueEventListener(userValueEventListener);
        bbsRef.addValueEventListener(bbsValueEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //myRef.removeEventListener(valueEventListener);
        userRef.removeEventListener(userValueEventListener);
        bbsRef.removeEventListener(bbsValueEventListener);
    }

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String message = "";
            for(DataSnapshot snap : dataSnapshot.getChildren()){
                message += snap.getValue(String.class) + "\n";
            }

            tvMessage.setText(message);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    private ValueEventListener userValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            List<User> users = new ArrayList<>();
            for(DataSnapshot snap : dataSnapshot.getChildren()){
                String userid = snap.getKey();
                if(snap.hasChild("bbs")){
                    Map<String, Object> map = (Map<String, Object>)snap.getValue();
                    String username = String.valueOf(map.get("username"));
                    int age = Integer.parseInt(String.valueOf(map.get("age")));
                    String email = String.valueOf(map.get("email"));
                    User user = new User(username, age, email);
                    user.setUser_id(userid);

                    user.bbs = new ArrayList<>();
                    for(DataSnapshot snapChild : snap.child("bbs").getChildren()){
                        Bbs bbs = snapChild.getValue(Bbs.class);
                        bbs.setBbs_id(snapChild.getKey());
                        user.bbs.add(bbs);
                    }

                    users.add(user);
                }
                else{
                    User user = snap.getValue(User.class);
                    user.setUser_id(userid);
                    users.add(user);
                }
            }

            userAdapter.setData(users);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    private ValueEventListener bbsValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            List<Bbs> bbss = new ArrayList<>();
            for(DataSnapshot snap : dataSnapshot.getChildren()){
                Bbs bbs = snap.getValue(Bbs.class);
                bbs.setBbs_id(snap.getKey());
                bbss.add(bbs);
            }

            bbsAdapter.setData(bbss);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터베이스 connection
        db = FirebaseDatabase.getInstance();
        //myRef = db.getReference("message");
        //rootRef = db.getReference();
        bbsRef = db.getReference("bbs");
        userRef = db.getReference("user");

        init();

        //writeNewUser("test", "lee", 23, "djfefff@gmail.com");
    }

    private void init(){
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etTitle = findViewById(R.id.etTitle);

        tvMessage = findViewById(R.id.tvMessage);
        etMessage = findViewById(R.id.etMessage);
        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etMessage.getText().toString();

                // 키의 값에 아무것도 없으면 생성이 안되므로 임의값을 넣어줌
                if(message == null || "".equals(message)) message = "none";

                // 노드 생성 - 유일한 node를 하나 생성
                String key = myRef.push().getKey();
                // 노드에 값넣기
                myRef.child(key).setValue(message);
            }
        });

        rvSign = findViewById(R.id.rvSign);
        rvBbs = findViewById(R.id.rvBbs);

        userAdapter = new UserAdapter();
        rvSign.setAdapter(userAdapter);
        rvSign.setLayoutManager(new LinearLayoutManager(this));

        bbsAdapter = new BbsAdapter();
        rvBbs.setAdapter(bbsAdapter);
        rvBbs.setLayoutManager((new LinearLayoutManager(this)));
    }


    public void signup(View v){
        User user = new User(etName.getText().toString(), 10, "");
        userRef.child(etId.getText().toString()).setValue(user);
    }

    public void post(View v){
        String userid = userAdapter.getCheckedUserName();
        if("".equals(userid)) {
            Toast.makeText(this, "user_id is null",Toast.LENGTH_LONG).show();
            return;
        }

        Bbs bbs = new Bbs(etTitle.getText().toString(),"","");
        bbs.setUser_id(userAdapter.getCheckedUserName());

        String key = bbsRef.push().getKey();
        bbs.setBbs_id(key);
        bbsRef.child(key).setValue(bbs);

        userRef.child(userid).child("bbs").child(bbs.getBbs_id()).setValue(bbs);
    }



    private void writeNewUser(String userId, String name, int age, String email){
        User user = new User(name, age, email);

        rootRef.child("users") // 레퍼런스를 가져오고
             .child(userId)  // 추가될 노드를 생성
             .setValue(user);// 추가된 노드에 값을 입력

        // user = #userid - name : kdsj
        //                - age : 23
        //                - email : kjdf@ndsaf.com
    }
}
