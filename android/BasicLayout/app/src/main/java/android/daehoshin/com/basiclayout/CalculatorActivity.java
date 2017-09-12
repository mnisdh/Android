package android.daehoshin.com.basiclayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CalculatorActivity extends AppCompatActivity {
    private TextView txtCalc, txtResult;

    boolean isBeforeText = true;
    List<Integer> numList = new ArrayList<>();
    List<Integer> otherIdList = new ArrayList<>();
    private String beforeStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        initButton();
        initTextView();
    }

    private void initButton(){
        findViewById(R.id.btn0).setOnClickListener(onClickListener);
        findViewById(R.id.btn1).setOnClickListener(onClickListener);
        findViewById(R.id.btn2).setOnClickListener(onClickListener);
        findViewById(R.id.btn3).setOnClickListener(onClickListener);
        findViewById(R.id.btn4).setOnClickListener(onClickListener);
        findViewById(R.id.btn5).setOnClickListener(onClickListener);
        findViewById(R.id.btn6).setOnClickListener(onClickListener);
        findViewById(R.id.btn7).setOnClickListener(onClickListener);
        findViewById(R.id.btn8).setOnClickListener(onClickListener);
        findViewById(R.id.btn9).setOnClickListener(onClickListener);

        findViewById(R.id.btnCalc).setOnClickListener(onClickListener);
        findViewById(R.id.btnDevi).setOnClickListener(onClickListener);
        findViewById(R.id.btnMulti).setOnClickListener(onClickListener);
        findViewById(R.id.btnPlus).setOnClickListener(onClickListener);
        findViewById(R.id.btnMinus).setOnClickListener(onClickListener);
        findViewById(R.id.btnClear).setOnClickListener(onClickListener);
    }

    private void initTextView(){
        txtCalc = (TextView) findViewById(R.id.txtCalc);
        txtResult = (TextView) findViewById(R.id.txtResult);

        clear();
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.btnCalc:                 calc();                        break;
                case R.id.btnClear:                clear();                       break;
                case R.id.btnDevi:                 addText("/", id);                break;
                case R.id.btnMulti:                addText("*", id);                break;
                case R.id.btnPlus:                 addText("+", id);                break;
                case R.id.btnMinus:                addText("-", id);                break;
                case R.id.btn0:                    addText(0);                    break;
                case R.id.btn1:                    addText(1);                    break;
                case R.id.btn2:                    addText(2);                    break;
                case R.id.btn3:                    addText(3);                    break;
                case R.id.btn4:                    addText(4);                    break;
                case R.id.btn5:                    addText(5);                    break;
                case R.id.btn6:                    addText(6);                    break;
                case R.id.btn7:                    addText(7);                    break;
                case R.id.btn8:                    addText(8);                    break;
                case R.id.btn9:                    addText(9);                    break;
            }
        }
    };

    private void calc(){
        double sum = 0;

        List<Integer> tempList = new ArrayList<>();
        tempList.addAll(numList);

        if(existLastNum()) tempList.add(getLastNum());

        if(tempList.size() == otherIdList.size()) {
            Toast.makeText(this,"계산식이 완성되지 않았습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        List<Double> numbers = new ArrayList<>();
        List<Integer> others = new ArrayList<>();

        numbers.add(Double.parseDouble(tempList.get(0) + ""));
        for (int i = 0; i < otherIdList.size(); i++){
            int btnId = otherIdList.get(i);
            double num = Double.parseDouble(tempList.get(i + 1) + "");

            switch (btnId){
                case R.id.btnMulti:
                    numbers.set(numbers.size() - 1, numbers.get(numbers.size() - 1) * num);
                    break;
                case R.id.btnDevi:
                    numbers.set(numbers.size() - 1, numbers.get(numbers.size() - 1) / num);
                    break;
                default:
                    numbers.add(num);
                    others.add(btnId);
                    break;
            }
        }

        if(numbers.size() > 0) {
            sum += numbers.get(0);
            for (int i = 1; i < numbers.size(); i++) {
                double num = numbers.get(i);

                switch (others.get(i - 1)) {
                    case R.id.btnPlus: sum += num; break;
                    case R.id.btnMinus: sum -= num; break;
                }
            }
        }
        txtResult.setText(Math.round(sum) + "");

        isBeforeText = false;
    }

    private void clear(){
        txtCalc.setText("");
        txtResult.setText("");

        numList.clear();
        otherIdList.clear();
        beforeStr = "";

        boolean isBeforeText = true;
    }

    private void addText(int i){
        txtCalc.append(i + "");

        isBeforeText = false;
        //calc();
    }

    private void addText(String s, int btnId){
        if(isBeforeText) {
            Toast.makeText(this, "숫자를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(existLastNum()) numList.add(getLastNum());
        otherIdList.add(btnId);

        txtCalc.append(" " + s + " ");

        beforeStr = txtCalc.getText().toString();

        isBeforeText = true;
    }

    private boolean existLastNum(){
        if(beforeStr.length() == txtCalc.getText().length()) return false;
        return true;
    }

    private int getLastNum(){
        String sNum = txtCalc.getText().toString().substring(beforeStr.length()).trim();

        return Integer.parseInt(sNum);
    }

}
