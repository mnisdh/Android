package android.daehoshin.com.animation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class JoystickActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnPlayer, btnUp, btnDown, btnLeft, btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        initButton();
        initListener();
    }

    private void initButton(){
        btnPlayer = (Button) findViewById(R.id.btnPlayer);
        btnUp = (Button) findViewById(R.id.btnUp);
        btnDown = (Button) findViewById(R.id.btnDown);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);
    }

    private void initListener(){
        btnPlayer.setOnClickListener(this);
        btnUp.setOnClickListener(this);
        btnDown.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int step = 20;

        switch (v.getId()){
            case R.id.btnUp:    moveY(btnPlayer, step * -1);    break;
            case R.id.btnDown:  moveY(btnPlayer, step);         break;
            case R.id.btnLeft:  moveX(btnPlayer, step * -1);    break;
            case R.id.btnRight: moveX(btnPlayer, step);         break;
        }
    }

    private void moveX(Button btn, int val){
        ObjectAnimator ani = ObjectAnimator.ofFloat(btnPlayer, "X", btn.getX() + val);
        ani.start();
    }

    private void moveY(Button btn, int val){
        ObjectAnimator ani = ObjectAnimator.ofFloat(btnPlayer, "Y", btn.getY() + val);
        ani.start();
    }

}
