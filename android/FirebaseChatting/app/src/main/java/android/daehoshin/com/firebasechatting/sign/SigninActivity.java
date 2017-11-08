package android.daehoshin.com.firebasechatting.sign;

import android.daehoshin.com.firebasechatting.R;
import android.daehoshin.com.firebasechatting.common.SignInfo;
import android.daehoshin.com.firebasechatting.common.domain.Manager;
import android.daehoshin.com.firebasechatting.common.domain.User;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SigninActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText etEmail, etPass;
    TextView tvEmailMsg, tvPassMsg;
    Button btnSignin;
    ConstraintLayout dialogSignup;
    TextView tvSignupMsg;
    CardView cardView;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = SignInfo.getInstance().getAuth();

        init();
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void init(){
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        tvEmailMsg = findViewById(R.id.tvEmailMsg);
        tvPassMsg = findViewById(R.id.tvPassMsg);
        btnSignin = findViewById(R.id.btnSignin);

        dialogSignup = findViewById(R.id.dialogSignup);
        tvSignupMsg = findViewById(R.id.tvSignupMsg);
        cardView = findViewById(R.id.cv);
        progress = findViewById(R.id.progress);
    }

    public void signin(View v){
        String email = etEmail.getText().toString();
        boolean checkEmail = isValidEmail(email);
        if(!checkEmail) tvEmailMsg.setVisibility(View.VISIBLE);

        String password = etPass.getText().toString();
        boolean checkPassword = isValidPassword(password);
        if(!checkPassword) tvPassMsg.setVisibility(View.VISIBLE);

        if(checkEmail && checkPassword) signin(email, password);
    }
    private void signin(String email, String password){
        progress.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            FirebaseUser fUser = mAuth.getCurrentUser();

                            if(!fUser.isEmailVerified()){
                                String msg = SigninActivity.this.getResources().getString(R.string.sign_emailcheck_message);
                                Toast.makeText(SigninActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                        else {
                            showDialogSignup();
                        }
                    }
                });
    }

    public void showDialogSignup(){
        dialogSignup.setVisibility(View.VISIBLE);
    }

    public void signup(View v){
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        signup(email, password);
    }

    private void signup(String email, String password){
        progress.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progress.setVisibility(View.GONE);
                                    dialogSignup.setVisibility(View.GONE);

                                    String msg = SigninActivity.this.getResources().getString(R.string.sign_emailcheck_message);
                                    Toast.makeText(SigninActivity.this, msg, Toast.LENGTH_SHORT).show();

                                    FirebaseUser fUser = mAuth.getCurrentUser();
                                    User user = new User(fUser.getUid(), fUser.getEmail(), FirebaseInstanceId.getInstance().getToken());
                                    user.setName(fUser.getDisplayName());
                                    user.setPhone_number(fUser.getPhoneNumber());

                                    Manager.addUser(user);
                                }
                            });
                        }
                        else {
                            progress.setVisibility(View.GONE);
                            String msg = SigninActivity.this.getResources().getString(R.string.sign_signupfail_message);
                            Toast.makeText(SigninActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signupCancel(View v){
        dialogSignup.setVisibility(View.GONE);
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
