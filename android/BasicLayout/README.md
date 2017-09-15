# Layout

### Constraint Layout

레이아웃이나 위젯에 연결하여 위치값을 설정하는 레이아웃으로
프레임 레이아웃 다음으로 속도가 빠름

<img width="250" height="450" src="https://github.com/mnisdh/Android/blob/master/android/BasicLayout/capture/ConstraintLayout.png?raw=true"/>


### Frame Layout

위젯들이 중첩되는 레이아웃으로 게임 같은 어플을 개발할때 사용되며
레이아웃들 중 가장 빠름

<img width="250" height="450" src="https://github.com/mnisdh/Android/blob/master/android/BasicLayout/capture/FrameLayout.png?raw=true"/>


### Linear Layout

orientation 옵션을 사용하여 가로, 세로 기준으로 위젯을 위치시키는 레이아웃

<img width="250" height="450" src="https://github.com/mnisdh/Android/blob/master/android/BasicLayout/capture/LinearLayout.png?raw=true"/>


### Grid Layout

표와 같은 형태로 제공되는 레이아웃이며 컬럼이나 로우등의 속성으로 위젯의 범위를 설정할수있다
하지만 높이나 너비를 균일하게 설정하기 힘드므로 사용은 거의 하지않음

<img width="250" height="450" src="https://github.com/mnisdh/Android/blob/master/android/BasicLayout/capture/GridLayout.png?raw=true"/>


### Relative Layout

Constraint 레이아웃과 비슷하게 위젯에 연결하여 위치값을 설정하는 레이아웃

<img width="250" height="450" src="https://github.com/mnisdh/Android/blob/master/android/BasicLayout/capture/RelativeLayout.png?raw=true"/>


# OnClickListener 연결 설명

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


# 계산기 어플

<img width="250" height="450" src="https://github.com/mnisdh/Android/blob/master/android/BasicLayout/capture/Calculator1.png?raw=true"/> <img width="250" height="450" src="https://github.com/mnisdh/Android/blob/master/android/BasicLayout/capture/Calculator2.png?raw=true"/>

<img width="250" height="450" src="https://github.com/mnisdh/Android/blob/master/android/BasicLayout/capture/Calculator.gif?raw=true"/>

```java
package android.daehoshin.com.basiclayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    // 사용할 위젯을 선언
    private TextView txtCalc, txtResult;
    private ConstraintLayout layMain;

    // 연산버튼을 두번 누르지 못하게 체크하는 변수
    boolean isBeforeText = true;
    // 계산할 숫자를 담는 list
    ArrayList<Double> numList = new ArrayList<>();
    // 계산할 연산의 버튼 id를 담는 list
    ArrayList<Integer> otherIdList = new ArrayList<>();
    // 연산버튼 클릭전의 마지막 숫자값을 받아오기 위한 이전 string을 담는 변수
    private String beforeStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        layMain = (ConstraintLayout) findViewById(R.id.layMain);
        initButton();
        initTextView();
    }

    /**
     * 버튼 초기화
     */
    private void initButton(){
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnDot).setOnClickListener(this);

        findViewById(R.id.btnCalc).setOnClickListener(this);
        findViewById(R.id.btnDevi).setOnClickListener(this);
        findViewById(R.id.btnMulti).setOnClickListener(this);
        findViewById(R.id.btnPlus).setOnClickListener(this);
        findViewById(R.id.btnMinus).setOnClickListener(this);
        findViewById(R.id.btnClear).setOnClickListener(this);

        findViewById(R.id.btnStr).setOnClickListener(this);
        findViewById(R.id.btnEnd).setOnClickListener(this);
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
    @Override
    public void onClick(View v) {
        int id = v.getId();

        // 가짜버튼 생성해서 메인 레이아웃에 추가
        Button btnTemp = createTempButton((Button) findViewById(id));
        layMain.addView(btnTemp);

        // 애니메이션 실행
        runAnimation(btnTemp);

        switch (id){
            // 계산 버튼
            case R.id.btnCalc:                 calc();                        break;
            // 초기화 버튼
            case R.id.btnClear:                clear();                       break;
            // 연산기호 버튼
            case R.id.btnStr:                  addText("(", id);              break;
            case R.id.btnEnd:                  addText(")", id);              break;
            case R.id.btnDevi:                 addText("/", id);              break;
            case R.id.btnMulti:                addText("*", id);              break;
            case R.id.btnPlus:                 addText("+", id);              break;
            case R.id.btnMinus:                addText("-", id);              break;
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
            // 소수점버튼
            case R.id.btnDot:                  addDotText();                  break;
        }
    }

    /**
     * 애니메이션 동작을위해 짭 버튼 생성
     * @param btn
     * @return
     */
    private Button createTempButton(Button btn){
        // 버튼 생성하여 원본 버튼과 동일하게 구성
        Button btnNew = new Button(this);
        btnNew.setText(btn.getText().toString());
        btnNew.setWidth(btn.getWidth());
        btnNew.setHeight(btn.getHeight());
        btnNew.setBackground(btn.getBackground());

        // 부모 레이아웃의 x, y 값을 포함해야 최상위 레이아웃에서의 좌표값을 확인할수 있음
        View parent = (View)btn.getParent();
        float x = btn.getX() + parent.getX();
        float y = btn.getY() + parent.getY();

        btnNew.setX(x);
        btnNew.setY(y);

        return btnNew;
    }

    /**
     * 애니메이션 실행
     * @param btn
     */
    private void runAnimation(final Button btn){
        // 동작할 애니메이터 생성
        ObjectAnimator ani = ObjectAnimator.ofFloat(btn, View.X, txtCalc.getX() + txtCalc.getWidth() - btn.getWidth());
        ObjectAnimator ani2 = ObjectAnimator.ofFloat(btn, View.Y, txtCalc.getY() - 100);
        ObjectAnimator ani3 = ObjectAnimator.ofFloat(btn, View.ROTATION, 720);
        ObjectAnimator ani4 = ObjectAnimator.ofFloat(btn, View.ALPHA, 0.4f);
        ObjectAnimator ani5 = ObjectAnimator.ofFloat(btn, View.SCALE_X, 0.4f);
        ObjectAnimator ani6 = ObjectAnimator.ofFloat(btn, View.SCALE_Y, 0.4f);

        // 애니메이터들 실행을 위해 준비
        AnimatorSet aniSet = new AnimatorSet();
        aniSet.playTogether(ani, ani2, ani3, ani4, ani5, ani6);
        aniSet.setDuration(1000);
        aniSet.setInterpolator(new DecelerateInterpolator());
        // 애니메이션 종료 후 가짜 버튼 삭제를 위한 Listener 생성
        aniSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                layMain.removeView(btn);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        // 애니메이션 실행
        aniSet.start();
    }

    /**
     * 입력된 연산을 계산해서 텍스트뷰에 출력
     */
    private void calc(){
        // 열린 괄호가 있는경우 메세지만 출력
        if(isOpendBracket()){
            Toast.makeText(this, "계산식이 완성되지 않았습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        // 텍스트뷰에 결과값 출력
        txtResult.setText(calc(numList, otherIdList) + "");

        // 계산 완료 후 연산기호 체크 변수 초기화
        isBeforeText = false;
    }

    /**
     * 숫자list와 기호list를 받아 실제 계산처리하는 메소드
     * @param calcNums
     * @param calcOthers
     * @return
     */
    private double calc(ArrayList<Double> calcNums, ArrayList<Integer> calcOthers){
        double sum = 0;

        try{
            // 넘어온 숫자list를 템프list에 저장
            ArrayList<Double> tempList = new ArrayList<>();
            tempList.addAll(calcNums);

            // 넘어온 기호list를 템프list에 저장
            ArrayList<Integer> tempOthers = new ArrayList<>();
            tempOthers.addAll(calcOthers);

            // 기호list가 2개이상일 경우 처음과 끝에 괄호여부 확인하여 있는경우 제거
            if(tempOthers.size() > 1){
                if(tempOthers.get(0) == R.id.btnStr && tempOthers.get(tempOthers.size() - 1) == R.id.btnEnd) {
                    tempOthers.remove(0);
                    tempOthers.remove(tempOthers.size() - 1);
                }
            }

            // 리스트에 저장되지 않은 마지막 숫자가 있는지 체크하여 템프list에 추가
            if (existLastNum()) tempList.add(getLastNum());

            // + , - 계산을 위한 숫자list, 기호list 생성
            List<Double> numArray = new ArrayList<>();
            List<Integer> otherArray = new ArrayList<>();

            // 괄호 , * , / 부터 연산하여 위에서 생성한 리스트들에 새로 추가
            int tempOthersSize = tempOthers.size();
            for (int i = 0; i < tempOthersSize; i++) {
                int btnId = tempOthers.get(i);

                // 괄호 체크를 위한 변수선언
                boolean isBracket = false;
                int bracketIndex = 0;

                if(i == 0){
                    // 처음 기호가 괄호인 경우는 따로처리(인덱스값이 달라짐)
                    if(btnId == R.id.btnStr){
                        isBracket = true;
                        bracketIndex = i;
                    }
                    // 다음 기호가 괄호인 경우
                    else if(i < tempOthers.size() - 1 && tempOthers.get(i + 1) == R.id.btnStr){
                        isBracket = true;
                        bracketIndex = i + 1;
                    }
                }
                // 다음 기호가 괄호인 경우
                else if(i < tempOthers.size() - 1 && tempOthers.get(i + 1) == R.id.btnStr){
                    isBracket = true;
                    bracketIndex = i + 1;
                }

                // 괄호가 체크된 경우 괄호 부분 먼저 계산해서 list를 업데이트
                if(isBracket) {
                    setBracketArrays(tempList, tempOthers, bracketIndex);
                    tempOthersSize = tempOthers.size();
                    btnId = tempOthers.get(i);
                }

                // 기호 앞의 숫자를 list에 저장
                if(i == 0) numArray.add(tempList.get(0));
                // 기호 뒤의 숫자가 없는경우 아래코드 동작안하도록함
                if(tempList.size() <= (i + 1)) continue;
                // 기호 뒤의 숫자를 변수에 설정
                double num = tempList.get(i + 1);

                // 기호 타입 체크
                switch (btnId) {
                    // * 일 경우
                    case R.id.btnMulti:
                        numArray.set(numArray.size() - 1, numArray.get(numArray.size() - 1) * num);
                        break;
                    // / 일 경우
                    case R.id.btnDevi:
                        numArray.set(numArray.size() - 1, numArray.get(numArray.size() - 1) / num);
                        break;
                    // 나머지의 경우 후순위로 미루기 위해 list에 저장
                    default:
                        numArray.add(num);
                        otherArray.add(btnId);
                        break;
                }
            }

            // 나머지 계산 안된 list값들을 계산
            // 처음숫자의 경우 무조건 sum에 더해줌
            if (numArray.size() > 0) sum += numArray.get(0);

            if (numArray.size() > 1) {
                for (int i = 1; i < numArray.size(); i++) {
                    // 기호 뒤의 숫자를 변수에 설정
                    double num = numArray.get(i);
                    // 기호에 맞게 연산 하여 sum에 더해줌
                    switch (otherArray.get(i - 1)) {
                        case R.id.btnPlus:
                            sum += num;
                            break;
                        case R.id.btnMinus:
                            sum -= num;
                            break;
                    }
                }
            }

            // 결과값 반환
            return sum;
        }
        catch (Exception ex){
            return sum;
        }
    }

    /**
     * 넘겨 받은 숫자list와 기호list에서 주어진 괄호의 index로
     * 짝이 되는 괄호를 찾아 괄호안의 list들을 계산해주며
     * 계산된 숫자,기호 값들은 넘겨받은 list들에서 remove해줌
     * @param oriNums
     * @param oriOthers
     * @param bIndex
     */
    private void setBracketArrays(ArrayList<Double> oriNums, ArrayList<Integer> oriOthers, int bIndex){
        // 괄호안에 포함되는 숫자list, 기호list를 담기위한 변수 생성
        ArrayList<Double> bNums = new ArrayList<>();
        ArrayList<Integer> bOthers = new ArrayList<>();

        // 괄호 시작 인덱스
        int otherStrIndex = bIndex;
        // 괄호 종료 인덱스
        int otherEndIndex = 0;
        // 괄호 위치에 따른 숫자 시작 인덱스
        int numStrIndex = bracketStartToNumIndex(oriOthers, otherStrIndex);
        // 괄호 위치에 따른 숫자 종료 인덱스
        int numEndIndex = 0;

        // 괄호 종료 인덱스 찾기
        for(int j = oriOthers.size() - 1; j > 0; j--){
            if(oriOthers.get(j).equals(R.id.btnEnd)){
                otherEndIndex = j;
                numEndIndex = bracketEndToNumIndex(oriOthers, otherEndIndex);
                break;
            }
        }

        // 괄호안에 포함되는 숫자들을 위에 생성한 숫자list에 담아주며 원본 숫자list에서는 삭제
        for(int n = numStrIndex; n <= numEndIndex; n++){
            if(oriNums.size() > numStrIndex) {
                bNums.add(oriNums.get(numStrIndex));
                oriNums.remove(numStrIndex);
            }
        }

        // 괄호안에 포함되는 기호들을 위에 생성한 기호list에 담아주며 원본 기호list에서는 삭제
        for(int o = otherStrIndex; o <= otherEndIndex; o++){
            if(oriOthers.size() > otherStrIndex) {
                bOthers.add(oriOthers.get(otherStrIndex));
                oriOthers.remove(otherStrIndex);
            }
        }

        // 괄호안의 숫자,기호들을 계산해서 원본 숫자list에 삽입/추가 해줌
        double bracketSum = calc(bNums, bOthers);
        if(oriNums.size() - 1 >= numStrIndex) oriNums.add(numStrIndex, bracketSum);
        else oriNums.add(bracketSum);
    }

    /**
     * 시작괄호 인덱스로 숫자의 시작 인덱스 받아오기
     * @param others
     * @param convertIndex
     * @return
     */
    public int bracketStartToNumIndex(ArrayList<Integer> others, int convertIndex){
        int index = 0;

        for(int i = 0; i < others.size(); i++) {
            if(i == convertIndex) return index;

            switch (others.get(i)) {
                case R.id.btnStr:
                    break;
                case R.id.btnEnd:
                    break;
                default:
                    index++;
                    break;
            }
        }

        return index;
    }

    /**
     * 종료괄호 인덱스로 숫자의 시작 인덱스 받아오기
     * @param others
     * @param convertIndex
     * @return
     */
    public int bracketEndToNumIndex(ArrayList<Integer> others, int convertIndex){
        int index = 0;

        for(int i = 0; i < others.size(); i++) {
            if(i == convertIndex) return index;

            switch (others.get(i)) {
                case R.id.btnStr:
                    break;
                case R.id.btnEnd:
                    break;
                default:
                    index++;
                    break;
            }
        }

        return index;
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
     * 숫자에 포함되는 문자를 받을때 사용 ex) .
     */
    private void addDotText(){
        if(existLastDot()){
            Toast.makeText(this, "잘못된 입력입니다.", Toast.LENGTH_LONG).show();
            return;
        }

        // 텍스트뷰에 dot 출력
        if(!existLastNum()) txtCalc.append("0.");
        else txtCalc.append(".");

        // 연산기호 입력가능 하도록 변수 설정
        isBeforeText = false;
    }

    /**
     * 연산기호 입력 메소드
     * @param s
     * @param btnId
     */
    private void addText(String s, int btnId){
        switch (btnId) {
            case R.id.btnStr:
                boolean isFailed = true;
                if(isBeforeText) isFailed = false;
                else if(numList.size() == 0 && !existLastNum()) isFailed = false;

                if (isFailed) {
                    Toast.makeText(this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 연산기호 중복입력 체크를 위해 변수 설정
                isBeforeText = true;

                break;
            case R.id.btnEnd:
                if(!isOpendBracket()){
                    Toast.makeText(this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 마지막 입력이 연산기호일 경우 메세지 출력후 리턴
                if(isBeforeText) {
                    Toast.makeText(this, "숫자를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 숫자list에 추가되지 않은 숫자체크하여 추가
                if(existLastNum()) numList.add(getLastNum());

                // 연산기호 중복입력 체크를 위해 변수 설정
                isBeforeText = false;

                break;
            default:
                // 마지막 입력이 연산기호일 경우 메세지 출력후 리턴
                if(isBeforeText) {
                    Toast.makeText(this, "숫자를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 숫자list에 추가되지 않은 숫자체크하여 추가
                if(existLastNum()) numList.add(getLastNum());

                // 연산기호 중복입력 체크를 위해 변수 설정
                isBeforeText = true;

                break;
        }

        // 연산기호list에 추가
        otherIdList.add(btnId);

        // 텍스트뷰에 입력된 연산기호 출력
        txtCalc.append(" " + s + " ");

        // last 숫자체크를 위해 현재 입력된 string을 저장
        beforeStr = txtCalc.getText().toString();


    }

    /**
     * 열려있는 괄호가 있는지 체크
     * @return
     */
    private boolean isOpendBracket(){
        int count = 0;
        for(int id : otherIdList){
            if(id == R.id.btnStr) count++;
            else if(id == R.id.btnEnd) count--;
        }

        return count > 0;
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
     * 마지막에 dot이 입력되어있는지 체크
     * @return
     */
    private boolean existLastDot(){
        // 이전에 저장된 string과 길이 비교하여 입력되지 않은 숫자중 dot이 있는지 체크
        if(existLastNum()){
            String str = txtCalc.getText().toString().replace(beforeStr, "");

            if(str.indexOf(".") >= 0) return true;
        }

        return false;
    }

    /**
     * 입력되지 않은 마지막 숫자 가져오기
     * @return
     */
    private double getLastNum(){
        // 이전에 저장된 string 길이 이후의 string을 가져옴
        String sNum = txtCalc.getText().toString().substring(beforeStr.length()).trim();

        // 가져온 string을 double로 변환하여 리턴
        return Double.parseDouble(sNum);
    }

}
```
