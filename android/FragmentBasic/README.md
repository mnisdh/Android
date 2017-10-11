# Flagment Basic

## Flagment 사용법

### MainActivity.java

```java
package android.daehoshin.com.fragmentbasic;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements ListFragment.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity","onCreate");

        initFragment();
    }

    @Override
    protected void onStart() {
        Log.d("MainActivity","onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("MainActivity","onResume");
        super.onResume();
    }

    public void goDetail(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flContainer, new DetailFragment())
                .addToBackStack(null)
                .commit();
    }

    private void initFragment(){
        Log.d("MainActivity","onInitFragment");

        // 1. 프래그먼트 매니저
        FragmentManager manager = getSupportFragmentManager();

        // 2. 트랜잭션 처리자
        FragmentTransaction transaction = manager.beginTransaction(); // 프래그먼트를 처리하기 위한 트랜잭션을 시작

        Log.d("MainActivity","begin transaction");

        // 3. 액티비티 레이아웃에 프래그먼트를 부착하고
        transaction.add(R.id.flContainer, new ListFragment());

        Log.d("MainActivity","end transaction");

        // 4. 커밋해서 완료
        transaction.commit();
    }
}
```


### ListFragment.java

```java
package android.daehoshin.com.fragmentbasic;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    Callback callback = null;
    Button button;

    /**
     * 여기는 코드를 작성하면 안된다
     */
    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        Log.d("ListFragment","onAttach");

        // 1. 나를 사용한 액티비티가 내가 제공한 인터페이스를 구현했는지 확인
        if(context instanceof Callback) { this.callback = (Callback) context; }

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.d("ListFragment","onDetach");
        super.onDetach();
    }

    /**
     * 액티비티에 붙여지면서 동작
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.goDetail();
            }
        });

        // 플래그먼트
        Log.d("ListFragment","onCreateView");

        return view;
    }


    @Override
    public void onStart() {
        Log.d("ListFragment","onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("ListFragment","onResume");
        super.onResume();
    }

    public interface Callback{
        void goDetail();
    }

}
```


### DetailFragment.java

```java
package android.daehoshin.com.fragmentbasic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

}
```
