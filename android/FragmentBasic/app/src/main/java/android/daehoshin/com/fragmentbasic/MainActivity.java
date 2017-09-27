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
