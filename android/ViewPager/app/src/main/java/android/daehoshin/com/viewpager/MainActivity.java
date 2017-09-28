package android.daehoshin.com.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager 사용하기
 */
public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        // 1. 데이터로드
        List<String> data = new ArrayList<>();
        for(int i = 0; i < 100; i++) data.add("Temp Data " + (i + 1));

        // 2. 아답터 생성
        CustomAdapter adapter = new CustomAdapter(this, data);

        // 3. 아답터 연결
        viewPager.setAdapter(adapter);
    }
}

class CustomAdapter extends PagerAdapter{
    Context context;
    List<String> data;

    public CustomAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
    }

    /**
     * 전체 개수
     * @return
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * getView 와 같은
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 1. 여기서 레이아웃 파일을 inflate해서 view로 만든다
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager, null);

        // 2. 데이터를 화면에 셋팅
        String value = data.get(position);
        ((TextView)view.findViewById(R.id.tvText)).setText(value);

        // 3. 뷰 그룹에 만들어진 view를 add 한다
        container.addView(view);

        //return super.instantiateItem(container, position);
        return view;
    }

    /**
     * instantiateItem에서 리턴된 object가 View가 맞는지 확인
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 현재 사용하지 않는 View는 제거
     * @param container : 뷰 페이저
     * @param position
     * @param object : 뷰 아이템(뷰페이저 안에 있는)
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
        //super.destroyItem(container, position, object);
    }
}
