package android.daehoshin.com.basicwidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView ;
    ToggleButton toggleButton ;
    CheckBox chkCow ;
    CheckBox chkPig;
    CheckBox chkDog ;
    RadioGroup radioGroup ;
    RadioButton rbtRed ;
    RadioButton rbtGreen;
    RadioButton rbtBlue ;
    ProgressBar progressBar;
    Switch switch1;
    SeekBar seekBar ;
    TextView txtSeekBarResult;
    RatingBar ratingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initListener();

        ratingBar.setMax(100);
        progressBar.setVisibility(View.INVISIBLE); // INVISIBLE : 화면에 안보이는데 자리는 차지하는 상태
                                                   // VISIBLE : 현재 화면에 보이는 상태
                                                   // GONE : 화면에 삭제된 상태
    }

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()){
                case R.id.switch1:
                    if(isChecked) progressBar.setVisibility(View.VISIBLE);
                    else progressBar.setVisibility(View.INVISIBLE);
                    break;
                case R.id.toggleButton:
                    if(isChecked) textView.setText("토글이 눌렸습니다");
                    else textView.setText("토글이 해제되었습니다");
                    break;
            }

        }
    };

    CompoundButton.OnCheckedChangeListener checkBoxCheckedChangedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            List<String> list = new ArrayList<>();

            if(chkCow.isChecked()) list.add("cow");
            if(chkPig.isChecked()) list.add("pig");
            if(chkDog.isChecked()) list.add("dog");

            String str = "";
            if(list.size() > 0){
                for(String i : list) str += "," + i;

                textView.setText(str.substring(1) + "가 체크되었습니다");
            }
            else{
                textView.setText("전부 해지되었습니다");
            }

        }
    };

    RadioGroup.OnCheckedChangeListener radioCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId){
                case R.id.rbtRed:
                    textView.setText("빨간불이 켜졌습니다");
                    break;
                case R.id.rbtGreen:
                    textView.setText("초록불이 켜졌습니다");
                    break;
                case R.id.rbtBlue:
                    textView.setText("파란불이 켜졌습니다");
                    break;
                case R.id.rbtSpinner:
                    Intent intent = new Intent(getApplicationContext(), SpinnerActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            txtSeekBarResult.setText(progress + "");
            ratingBar.setRating(progress/100 * ratingBar.getNumStars());

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void init(){
         textView = (TextView) findViewById(R.id.textView);
         toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
         chkCow = (CheckBox) findViewById(R.id.chkCow);
         chkPig = (CheckBox) findViewById(R.id.chkPig);
         chkDog = (CheckBox) findViewById(R.id.chkDog);
         radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
         rbtRed = (RadioButton) findViewById(R.id.rbtRed);
         rbtGreen = (RadioButton) findViewById(R.id.rbtGreen);
         rbtBlue = (RadioButton) findViewById(R.id.rbtBlue);
         progressBar = (ProgressBar) findViewById(R.id.progressBar);
         switch1 = (Switch) findViewById(R.id.switch1);
         seekBar = (SeekBar) findViewById(R.id.seekBar);
         txtSeekBarResult = (TextView) findViewById(R.id.txtSeekBarResult);
         ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    }

    private void initListener(){
        toggleButton.setOnCheckedChangeListener(checkedChangeListener);
        switch1.setOnCheckedChangeListener(checkedChangeListener);

        chkCow.setOnCheckedChangeListener(checkBoxCheckedChangedListener);
        chkPig.setOnCheckedChangeListener(checkBoxCheckedChangedListener);
        chkDog.setOnCheckedChangeListener(checkBoxCheckedChangedListener);

        radioGroup.setOnCheckedChangeListener(radioCheckedChangeListener);

        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }
}
