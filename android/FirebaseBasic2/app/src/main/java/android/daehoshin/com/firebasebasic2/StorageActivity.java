package android.daehoshin.com.firebasebasic2;

import android.content.Intent;
import android.daehoshin.com.firebasebasic2.domain.User;
import android.daehoshin.com.firebasebasic2.domain.UserAdapter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class StorageActivity extends AppCompatActivity {
    private StorageReference storageRef;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    private RecyclerView rvText;
    private TextView tvId, tvToken;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        storageRef = FirebaseStorage.getInstance().getReference();

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");

        init();

        load();
    }

    private void init(){
        rvText = findViewById(R.id.rvText);
        tvId = findViewById(R.id.tvId);
        tvToken = findViewById(R.id.tvToken);

        userAdapter = new UserAdapter(new UserAdapter.ICallback() {
            @Override
            public void checkedChangedUser(User user) {
                tvId.setText(user.getEmail());
                tvToken.setText(user.getToken());
            }
        });
        rvText.setAdapter(userAdapter);
        rvText.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void load(){
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    //;
                    //String email = item.getKey();
                    users.add(item.getValue(User.class));
                }

                userAdapter.setData(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * 파일 탐색기 호출
     */
    public void chooseFile(View v){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/*"); // 갤러리 "image/*" , 동영상 "video/*"
        startActivityForResult(intent.createChooser(intent, "Select App"), 999);
    }

    /**
     * 파일이 선택되면 호출
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
            //String realPath = RealPathUtil.getRealPath(this, uri);

            upload(uri);
        }
    }

    private void upload(Uri file){
        // 실제 파일이 있는 경로
        //Uri file = Uri.fromFile(new File(realPath));
        // 파이어베이스의 스토리지 file node
        String[] temp  = file.getPath().split("/");
        String fileName = temp[temp.length - 1];
        StorageReference riversRef = storageRef.child("files/" + fileName);

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(StorageActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
