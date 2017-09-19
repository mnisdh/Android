# Basic list

## MainActivity class

```java
package android.daehoshin.com.basiclist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 리스트뷰 사용하기
 */
public class MainActivity extends AppCompatActivity {
    // 1.데이터를 정의
    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1.데이터를 정의100개의 가상값을 담는다
        for(int i = 0; i < 100; i++) data.add("임시값 " + i);

        // 2.데이터와 리스트뷰를 연결하는 아답터를 생성
        CustomAdapter adapter = new CustomAdapter(this, data);

        // 3.아답터와 리스트뷰를 연결
        ((ListView) findViewById(R.id.lvId)).setAdapter(adapter);
    }
}
```


## CustomAdapter class

```java
package android.daehoshin.com.basiclist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 기본 어답터 클래스를 상속받아 구현
 *
 * Created by daeho on 2017. 9. 19..
 */
public class CustomAdapter extends BaseAdapter {
    // 데이터 저장소를 아답터 내부에 두는것이 컨트롤 하기 편하다
    List<String> data;
    Context context;

    public CustomAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
    }

    // 현재 데이터의 총 갯수
    @Override
    public int getCount() {
        return data.size();
    }

    // 현재 뿌려질 데이터를 리턴
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    // 뷰의 아이디를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 목록에 나타나는 아이템 하나하나를 그려준다
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;

        // 아이템 convertView를 재사용하기위해 null체크
        if(convertView == null) {
            // 레이아웃 인플레이터로 xml 파일을 View 객체로 변환
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);

            // 아이템이 최초 호출된 경우는 Holder에 위젯들을 담고
            holder = new Holder();
            holder.setTvText((TextView) convertView.findViewById(R.id.tvText));

            // 홀더를 View에 붙여놓는다
            convertView.setTag(holder);
        }else{
            // View에 붙어 있는 홀더를 가져온다
            holder = (Holder) convertView.getTag();
        }

        // 뷰안에 있는 텍스트뷰 위젯에 값을 입력한다
        holder.getTvText().setText(data.get(position));

        // 새로 사용될 뷰 리턴
        return convertView;
    }

    /**
     * list_item 의 위젯 수정을 편하게 하기위한 클래스
     */
    class Holder{
        private TextView tvText;

        /**
         * tvText 설정시 onClickListener 연결
         *
         * @param tvText
         */
        public void setTvText(TextView tvText){
            this.tvText = tvText;
            this.tvText.setOnClickListener(new View.OnClickListener() {
                // 화면에 보여지는 View는
                // 기본적으로 자신이 속한 컴포넌트의 컨텍스트를 그대로 가지고 있다
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("ValueKey", ((TextView)v).getText().toString());

                    v.getContext().startActivity(intent);
                }
            });
        }
        public TextView getTvText(){
            return tvText;
        }
    }
}
```


## DetailActivity class

```java
package android.daehoshin.com.basiclist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 인텐트를 통해 넘어온 값 꺼내기
        Intent intent = getIntent(); // startActivity를 통해 넘어온 intent를 꺼낸다
        //// 인텐트에서 값의 묶음인 번들을 꺼내고
        //Bundle bundle = intent.getExtras();
        //// 번들에서 최종 값을 꺼낸다
        //String result = bundle.getString("valueKey");

        // 인텐트에서 바로 값을 꺼내기
        String result = intent.getStringExtra("ValueKey");

        ((TextView) findViewById(R.id.tvText)).setText(result);
    }
}
```
