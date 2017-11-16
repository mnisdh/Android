package android.daehoshin.com.rxbasic04;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.Random;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 0. 로그인 체크하기
        Observable<TextViewTextChangeEvent> idEmitter = RxTextView.textChangeEvents(findViewById(R.id.etEmail));
        Observable<TextViewTextChangeEvent> pwEmitter = RxTextView.textChangeEvents(findViewById(R.id.etPass));

        // 조건 id가 5자이상이고 pw가 8자이상이면 btnSignin 활성체크
        Observable.combineLatest(idEmitter, pwEmitter, (email, pass) -> email.text().length() >= 5 && pass.text().length() >= 8)
        .subscribe(flag -> findViewById(R.id.btnSignin).setEnabled(flag));



        // 1. editText에 입력되는 값을 체크해서 실시간으로 log를 뿌려준다
        RxTextView.textChangeEvents(findViewById(R.id.et))
                .subscribe(ch -> Log.i("RxBinding", "word : " + ch.text()));

        // 2. 버튼을 클릭하면 editText에 Random 숫자를 입력
        RxView.clicks(findViewById(R.id.btn))
                // button 에는 Button 타입의 오브젝트가 리턴되는데 이를 문자타입으로 변경
                .map(btn -> new Random().nextInt() + "")
                // subscribe 에서는 map 에서 변형된 타입을 받게 된다
                .subscribe(s -> ((EditText)findViewById(R.id.et)).setText(s));
    }

}
