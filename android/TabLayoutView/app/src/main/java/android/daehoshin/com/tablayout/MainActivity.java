package android.daehoshin.com.tablayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTabLayout();
        setViewPager();

        setListener();
    }

    private void setTabLayout(){
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_one));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_two));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_three));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_four));
    }

    private void setViewPager(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        CustomAdapter adapter = new CustomAdapter(this);

        viewPager.setAdapter(adapter);
    }

    private void setListener(){
        // 탭레이아웃을 뷰페이저에 연결
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        // ViewPager의 변경사항을 탭레이아웃에 전달
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
