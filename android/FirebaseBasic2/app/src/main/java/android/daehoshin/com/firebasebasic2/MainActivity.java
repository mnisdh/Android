package android.daehoshin.com.firebasebasic2;

import android.content.Intent;
import android.daehoshin.com.firebasebasic2.domain.User;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    // 파이어베이스 인증모듈 사용 전역변수
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    private EditText etSuEmail, etSuPass, etSiEmail, etSiPass;
    private TextView tvSuEmailMsg, tvSuPassMsg, tvSiEmailMsg, tvSiPassMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");

        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        // 로그인된 유저가 있는경우 유저정보를 가져옴
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void init(){
        etSuEmail = findViewById(R.id.etSuEmail);
        etSuEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tvSuEmailMsg.setVisibility(View.GONE);

                return false;
            }
        });
        etSuPass = findViewById(R.id.etSuPass);
        etSuPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tvSuPassMsg.setVisibility(View.GONE);

                return false;
            }
        });
        etSiEmail = findViewById(R.id.etSiEmail);
        etSiEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tvSiEmailMsg.setVisibility(View.GONE);

                return false;
            }
        });
        etSiPass = findViewById(R.id.etSiPass);
        etSiPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tvSiPassMsg.setVisibility(View.GONE);

                return false;
            }
        });

        tvSuEmailMsg = findViewById(R.id.tvSuEmailMsg);
        tvSuPassMsg = findViewById(R.id.tvSuPassMsg);
        tvSiEmailMsg = findViewById(R.id.tvSiEmailMsg);
        tvSiPassMsg = findViewById(R.id.tvSiPassMsg);
    }

    private void updateUI(FirebaseUser currentUser){
        if(currentUser != null) Toast.makeText(MainActivity.this, "Authentication OK : "+currentUser.getEmail(), Toast.LENGTH_SHORT).show();
    }

    public void signUp(View v){
        String email = etSuEmail.getText().toString();
        String pass = etSuPass.getText().toString();

        boolean bEmail = isValidEmail(email);
        boolean bPass = isValidPassword(pass);

        if(!bEmail || !bPass){
            if(!bEmail){
                tvSuEmailMsg.setText("이메일 형식이 틀렸습니다");
                tvSuEmailMsg.setVisibility(View.VISIBLE);
            }
            if(!bPass){
                tvSuPassMsg.setText("비밀번호 형식이 틀렸습니다");
                tvSuPassMsg.setVisibility(View.VISIBLE);
            }

            return;
        }

        signUp(email, pass);
    }
    private void signUp(String email, String password){
        // 파이어베이스의 인증모듈로 사용자를 생성
        mAuth.createUserWithEmailAndPassword(email, password)
                // 완료확인 리스너
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(MainActivity.this, "이메일을 발송했습니다. 확인해주세요", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void signIn(View v){
        String email = etSiEmail.getText().toString();
        String pass = etSiPass.getText().toString();

        boolean bEmail = isValidEmail(email);
        boolean bPass = isValidPassword(pass);

        if(!bEmail || !bPass){
            if(!bEmail){
                tvSiEmailMsg.setText("이메일 형식이 틀렸습니다");
                tvSiEmailMsg.setVisibility(View.VISIBLE);
            }
            if(!bPass){
                tvSiPassMsg.setText("비밀번호 형식이 틀렸습니다");
                tvSiPassMsg.setVisibility(View.VISIBLE);
            }

            return;
        }

        signIn(email, pass);
    }
    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = null;
                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser();

                            if(!user.isEmailVerified()){
                                Toast.makeText(MainActivity.this, "이메일을 확인해주세요", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String key = userRef.push().getKey();
                                User u = new User(user.getUid(), user.getEmail(), FirebaseInstanceId.getInstance().getToken());
                                userRef.child(user.getUid()).setValue(u);

                                Intent intent = new Intent(MainActivity.this, StorageActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        updateUI(user);
                    }
                });
    }


    public void getUserInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // 이메일 검증완료됬는지 확인
            boolean emailVerified = user.isEmailVerified();

            String uid = user.getUid();
        }
    }

    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }

    public static boolean isValidPassword(String pass) {
        boolean err = false;
        String regex = "^[a-zA-Z0-9]{8,18}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pass);
        if(m.matches()) {
            err = true;
        }
        return err;
    }


}
