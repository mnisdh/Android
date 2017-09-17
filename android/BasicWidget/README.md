# Basic Widget

## Toggle button

```java
toggleButton.setOnCheckedChangeListener(checkedChangeListener);

CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.toggleButton:
                if(isChecked) textView.setText("토글이 눌렸습니다");
                else textView.setText("토글이 해제되었습니다");
                break;
        }

    }
};
```


## Switch

```java
switch1.setOnCheckedChangeListener(checkedChangeListener);

CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.switch1:
                if(isChecked) progressBar.setVisibility(View.VISIBLE);
                else progressBar.setVisibility(View.INVISIBLE);
                break;
        }

    }
};
```


## Checkbox

```java
chkCow.setOnCheckedChangeListener(checkBoxCheckedChangedListener);
chkPig.setOnCheckedChangeListener(checkBoxCheckedChangedListener);
chkDog.setOnCheckedChangeListener(checkBoxCheckedChangedListener);

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
```


## Radiogroup

```java
radioGroup.setOnCheckedChangeListener(radioCheckedChangeListener);

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
}
```


## Seekbar

```java
seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

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
```


## Spinner

```java
package android.daehoshin.com.basicwidget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SpinnerActivity extends AppCompatActivity {
    Spinner spinner;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        // 1. 스피너에 입력될 데이터 정의
        final String[] data = {"월", "화", "수", "목", "금", "토", "일"};
        List<String> list = new ArrayList<>();
        list.add("월");
        list.add("화");
        list.add("수");
        list.add("목");
        list.add("금");
        list.add("토");
        list.add("일");

        // 2. 스피너와 데이터를 연결하는 아답터를 정의
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

        // 3. 아답터와 스피너(리스트)를 연결
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);

        txtResult = (TextView) findViewById(R.id.txtResult);

        // 4. 스피너에 리스너를 달아준다
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = data[position];
                txtResult.setText(selectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
```
