package android.daehoshin.com.locationsharechat.user;

import android.daehoshin.com.locationsharechat.R;
import android.daehoshin.com.locationsharechat.common.AuthManager;
import android.daehoshin.com.locationsharechat.domain.UserInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SigninActivity extends AppCompatActivity {
    private ImageView ivProfile;
    private ImageButton btnAddProfile;
    private EditText etNickname;
    private TextView tvNicknameMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        init();
    }

    private void init(){
        ivProfile = findViewById(R.id.ivProfile);
        btnAddProfile = findViewById(R.id.btnAddProfile);
        etNickname = findViewById(R.id.etNickname);
        etNickname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tvNicknameMsg.setVisibility(View.GONE);
                return false;
            }
        });
        tvNicknameMsg = findViewById(R.id.tvNicknameMsg);
    }

    public void addProfile(View v){


        btnAddProfile.setVisibility(View.GONE);
    }

    public void signin(View v){
        if("".equals(etNickname.getText().toString())) {
            tvNicknameMsg.setVisibility(View.VISIBLE);
            return;
        }

        AuthManager.getInstance().signInAnonymously(etNickname.getText().toString(), new AuthManager.IAuthCallback() {
            @Override
            public void signinAnonymously(boolean isSuccessful) {
                if(isSuccessful) {
                    setResult(RESULT_OK);
                    finish();
                }
                else{
                    Toast.makeText(SigninActivity.this, "Signin failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void getCurrentUser(UserInfo userInfo) {

            }
        });
    }
}
