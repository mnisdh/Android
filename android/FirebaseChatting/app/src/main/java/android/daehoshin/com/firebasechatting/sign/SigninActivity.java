package android.daehoshin.com.firebasechatting.sign;

import android.daehoshin.com.firebasechatting.R;
import android.daehoshin.com.firebasechatting.common.SignInfo;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SigninActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText etEmail, etPass;
    TextView tvEmailMsg, tvPassMsg;
    Button btnSignin;
    ConstraintLayout dialogSignup;
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
        //cardView = findViewById(R.id);
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
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            showDialogSignup();
                        }
                    }
                });
    }

    public void showDialogSignup(){
        dialogSignup.setVisibility(View.VISIBLE);
        btnSignin.setVisibility(View.GONE);

    }

    public void signup(View v){
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        signup(email, password);
    }

    private void signup(String email, String password){


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    public void signupCancel(View v){
        dialogSignup.setVisibility(View.GONE);
        btnSignin.setVisibility(View.VISIBLE);
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
