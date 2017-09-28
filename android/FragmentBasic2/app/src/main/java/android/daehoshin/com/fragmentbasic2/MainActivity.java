package android.daehoshin.com.fragmentbasic2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) return;

        // 세로체크
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            initFragment();
        }
        // 가로체크
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

        }
    }

    /**
     * 프래그먼트 더하기
     */
    private void initFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new ListFragment())
                .commit();
    }

    @Override
    public void goDetail(String text) {
        // 세로체크
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text", text);
            detailFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, detailFragment)
                    .addToBackStack(null)
                    .commit();

        }
        // 가로체크
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //현재 레이아웃에 삽입되어 있는 프래그먼트를 가져온다
            DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.fgDetail);
            detailFragment.setText(text);
        }
    }
}
