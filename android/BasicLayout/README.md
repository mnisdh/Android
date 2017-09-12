### Layout

##### Constraint Layout

레이아웃이나 위젯에 연결하여 위치값을 설정하는 레이아웃으로
프레임 레이아웃 다음으로 속도가 빠름

(https://github.com/mnisdh/Android/tree/master/android/BasicLayout/capture/ConstraintLayout.png)


##### Frame Layout

위젯들이 중첩되는 레이아웃으로 게임 같은 어플을 개발할때 사용되며
레이아웃들 중 가장 빠름

(https://github.com/mnisdh/Android/tree/master/android/BasicLayout/capture/FrameLayout.png)


##### Linear Layout

orientation 옵션을 사용하여 가로, 세로 기준으로 위젯을 위치시키는 레이아웃

(https://github.com/mnisdh/Android/tree/master/android/BasicLayout/capture/LinearLayout.png)


##### Grid Layout

표와 같은 형태로 제공되는 레이아웃이며 컬럼이나 로우등의 속성으로 위젯의 범위를 설정할수있다
하지만 높이나 너비를 균일하게 설정하기 힘드므로 사용은 거의 하지않음

(https://github.com/mnisdh/Android/tree/master/android/BasicLayout/capture/GridLayout.png)


##### Relative Layout

Constraint 레이아웃과 비슷하게 위젯에 연결하여 위치값을 설정하는 레이아웃

(https://github.com/mnisdh/Android/tree/master/android/BasicLayout/capture/RelativeLayout.png)


### OnClickListener 연결 설명

```java
package android.daehoshin.com.basiclayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*
안드로이드 화면 구조
                            Fragment(화면조각)
App(어플) > Activity(화면한개단위)     >     Layout(뷰그룹, 컨테이너) > Widget(뷰)
 */


/**
 *                                Activity 기반클래스를 상속받아서 구성됨
 */
public class MainActivity extends AppCompatActivity {
    // 레이아웃에 정의된 위젯의 아이디로 해당 객체를 변수에 저장해둔다
    Button btnFrame, btnLinear, btnGrid, btnRelative;


    /**
     * 화면이 생성될때 호출되는 메소드
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. 레이아웃 xml 파일을 메모리에 로드
        setContentView(R.layout.activity_main);

        // 2. 전역변수에 실제위젯을 할당
        btnFrame = (Button) findViewById(R.id.btnFrame);
        btnLinear = (Button) findViewById(R.id.btnLinear);
        btnGrid = (Button) findViewById(R.id.btnGrid);
        btnRelative = (Button) findViewById(R.id.btnRelative);

        // 2. 위에서 저장한 변수의 이벤트(클릭 터치 등) 캐치가 필요할 경우 리스너를 달아줌
        btnFrame.setOnClickListener(onClickListener);
        btnLinear.setOnClickListener(onClickListener);
        btnGrid.setOnClickListener(onClickListener);
        btnRelative.setOnClickListener(onClickListener);

        /*
        버튼의 경우 변수로 갖고있을 필요가 거의 없으므로 변수선언없이 바로 이벤트연결 하는 경우도 있음
        findViewById(R.id.btnFrame).setOnClickListener(onClickListener);
        findViewById(R.id.btnLinear).setOnClickListener(onClickListener);
        findViewById(R.id.btnGrid).setOnClickListener(onClickListener);
        findViewById(R.id.btnRelative).setOnClickListener(onClickListener);
         */

        findViewById(R.id.btnCalc).setOnClickListener(onClickListener);
    }

    /**
     * 리스너를 전역변수로 선언할 수 있다
     */
    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            // 액티비티(메이저 컴포넌트) 실행
            // 1. 인텐트(시스템에 전달되는 메시지 객체) 생성
            Intent intent = null;

            switch (v.getId()){
                case R.id.btnFrame:
                    intent = new Intent(MainActivity.this, FrameActivity.class);
                    break;
                case R.id.btnLinear:
                    intent = new Intent(MainActivity.this, LinearActivity.class);
                    break;
                case R.id.btnGrid:
                    intent = new Intent(MainActivity.this, GridActivity.class);
                    break;
                case R.id.btnRelative:
                    intent = new Intent(MainActivity.this, RelativeActivity.class);
                    break;
                case R.id.btnCalc:
                    intent = new Intent(MainActivity.this, CalculatorActivity.class);
                    break;
            }

            startActivity(intent);
        }
    };
}
```


### 계산기 어플

(https://github.com/mnisdh/Android/tree/master/android/BasicLayout/capture/Calculator1.png)
(https://github.com/mnisdh/Android/tree/master/android/BasicLayout/capture/Calculator2.png)

```java
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

    //연산버튼을 두번 누르지 못하게 체크하는 변수
    boolean isBeforeText = true;
    //계산할 숫자를 담는 list
    List<Integer> numList = new ArrayList<>();
    //계산할 연산의 버튼 id를 담는 list
    List<Integer> otherIdList = new ArrayList<>();
    //연산버튼 클릭전의 마지막 숫자값을 받아오기 위한 이전 string을 담는 변수
    private String beforeStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        initButton();
        initTextView();
    }

    /**
     * 버튼 초기화
     */
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

    /**
     * 텍스트뷰 초기화
     */
    private void initTextView(){
        txtCalc = (TextView) findViewById(R.id.txtCalc);
        txtResult = (TextView) findViewById(R.id.txtResult);

        clear();
    }

    /**
     * 버튼 이벤트 연결을 위한 리스너
     */
    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                // 계산 버튼
                case R.id.btnCalc:                 calc();                        break;
                // 초기화 버튼
                case R.id.btnClear:                clear();                       break;
                // 연산기호 버튼
                case R.id.btnDevi:                 addText("/", id);                break;
                case R.id.btnMulti:                addText("*", id);                break;
                case R.id.btnPlus:                 addText("+", id);                break;
                case R.id.btnMinus:                addText("-", id);                break;
                // 숫자버튼
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

    /**
     * 입력된 연산을 계산해서 텍스트뷰에 출력
     */
    private void calc(){
        double sum = 0;

        try {
            // 기 저장된 숫자목록을 템프list에 저장
            List<Integer> tempList = new ArrayList<>();
            tempList.addAll(numList);

            // 리스트에 저장되지 않은 마지막 숫자가 있는지 체크하여 템프list에 추가
            if (existLastNum()) tempList.add(getLastNum());

            // 연산기호가 하나 더 있는경우 메세지 출력하고 리턴
            if (tempList.size() == otherIdList.size()) {
                Toast.makeText(this, "계산식이 완성되지 않았습니다.", Toast.LENGTH_LONG).show();
                return;
            }

            // 우선순위 계산을 위한 숫자list, 기호list 생성
            // numbers의 경우 정확한 결과값을 계산하기 위해 double로 생성
            List<Double> numbers = new ArrayList<>();
            List<Integer> others = new ArrayList<>();

            // * , / 부터 연산하여 위에서 생성한 리스트들에 새로 추가
            numbers.add(Double.parseDouble(tempList.get(0) + ""));
            for (int i = 0; i < otherIdList.size(); i++) {
                int btnId = otherIdList.get(i);
                double num = Double.parseDouble(tempList.get(i + 1) + "");

                switch (btnId) {
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

            // * , / 연산 한 결과에서 다시 + , - 연산하여 결과값을 sum에 반영
            if (numbers.size() > 0) {
                sum += numbers.get(0);
                for (int i = 1; i < numbers.size(); i++) {
                    double num = numbers.get(i);

                    switch (others.get(i - 1)) {
                        case R.id.btnPlus:
                            sum += num;
                            break;
                        case R.id.btnMinus:
                            sum -= num;
                            break;
                    }
                }
            }
            // double로 계산된 결과를 반올림 메소드를 사용해서 텍스트뷰에 출력
            txtResult.setText(Math.round(sum) + "");

            // 계산 완료 후 연산기호 체크 변수 초기화
            isBeforeText = false;
        }
        catch (Exception ex) {
            // 연산도중 에러 발생시 메세지 표시
            Toast.makeText(this, "계산식 범위를 초과하였습니다.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 초기화 메소드
     */
    private void clear(){
        // 텍스트뷰 초기화
        txtCalc.setText("");
        txtResult.setText("");

        // 리스트 초기화
        numList.clear();
        otherIdList.clear();

        // 마지막 숫자조회 하기위한 변수 초기화
        beforeStr = "";

        // 연산기호 체크 변수 초기화
        boolean isBeforeText = true;
    }

    /**
     * 숫자 입력 메소드
     * @param i
     */
    private void addText(int i){
        // 기 입력된 값이 0일 경우 패스하는 코드
        if(i == 0) {
            if (existLastNum()) {
                if(getLastNum() == 0) return;
            }
        }

        // 텍스트뷰에 입력된 숫자 출력
        txtCalc.append(i + "");

        // 연산기호 입력가능 하도록 변수 설정
        isBeforeText = false;
    }

    /**
     * 연산기호 입력 메소드
     * @param s
     * @param btnId
     */
    private void addText(String s, int btnId){
        // 마지막 입력이 연산기호일 경우 메세지 출력후 리턴
        if(isBeforeText) {
            Toast.makeText(this, "숫자를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 숫자list에 추가되지 않은 숫자체크하여 추가
        if(existLastNum()) numList.add(getLastNum());
        // 연산기호list에 추가
        otherIdList.add(btnId);

        // 텍스트뷰에 입력된 연산기호 출력
        txtCalc.append(" " + s + " ");

        // last 숫자체크를 위해 현재 입력된 string을 저장
        beforeStr = txtCalc.getText().toString();

        // 연산기호 중복입력 체크를 위해 변수 설정
        isBeforeText = true;
    }

    /**
     * 입력되지 않은 마지막 숫자가 있는지 체크
     * @return
     */
    private boolean existLastNum(){
        // 이전에 저장된 string과 길이 비교하여 입력되지 않은 숫자 있는지 체크
        if(beforeStr.length() == txtCalc.getText().length()) return false;
        return true;
    }

    /**
     * 입력되지 않은 마지막 숫자 가져오기
     * @return
     */
    private int getLastNum(){
        // 이전에 저장된 string 길이 이후의 string을 가져옴
        String sNum = txtCalc.getText().toString().substring(beforeStr.length()).trim();

        // 가져온 string을 int로 변환하여 리턴
        return Integer.parseInt(sNum);
    }

}
```
