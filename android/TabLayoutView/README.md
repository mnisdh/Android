# TabLayout View

## TabLayout View 사용법

### MainActivity.java

```java
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
```


### One.java

```java
package android.daehoshin.com.tablayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class One extends FrameLayout {
    public One(Context context) {
        super(context);
        initView();
    }

    public One(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 여기서 사용할 레이아웃을 inflate하고
     * 나 자신에게 add 한다
     */
    private void initView(){
        // 1. 레이아웃 파일로 뷰를 만들고
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_one, null);

        addView(view);
    }
}
```


### Two.java

```java
package android.daehoshin.com.tablayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class Two extends FrameLayout {
    public Two(Context context) {
        super(context);
        initView();
    }

    public Two(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 여기서 사용할 레이아웃을 inflate하고
     * 나 자신에게 add 한다
     */
    private void initView(){
        // 1. 레이아웃 파일로 뷰를 만들고
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_two, null);

        addView(view);
    }
}
```


### Three.java

```java
package android.daehoshin.com.tablayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class Three extends FrameLayout {
    public Three(Context context) {
        super(context);
        initView();
    }

    public Three(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 여기서 사용할 레이아웃을 inflate하고
     * 나 자신에게 add 한다
     */
    private void initView(){
        // 1. 레이아웃 파일로 뷰를 만들고
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_three, null);

        addView(view);
    }
}
```


### Four.java

```java
package android.daehoshin.com.tablayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by daeho on 2017. 9. 28..
 */

public class Four extends FrameLayout {
    public Four(Context context) {
        super(context);
        initView();
    }

    public Four(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 여기서 사용할 레이아웃을 inflate하고
     * 나 자신에게 add 한다
     */
    private void initView(){
        // 1. 레이아웃 파일로 뷰를 만들고
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_four, null);

        addView(view);
    }
}
```


### CustomAdapter.java

```java
package android.daehoshin.com.tablayout;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 페이저 아답터에 프래그먼트 배열을 넘겨서 동작하게 한다
 * Created by daeho on 2017. 9. 27..
 */

public class CustomAdapter extends PagerAdapter{
    private static final int COUNT = 4;
    List<View> views;

    public CustomAdapter(Context context){
        views = new ArrayList<>();
        views.add(new One(context));
        views.add(new Two(context));
        views.add(new Three(context));
        views.add(new Four(context));
        for(View v : views) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                v.setId(View.generateViewId());
            }
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        if(container.getChildCount() == COUNT) return view;

        View temp = container.findViewById(view.getId());
        if(temp == null) container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView((View) object);
        //super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
```
