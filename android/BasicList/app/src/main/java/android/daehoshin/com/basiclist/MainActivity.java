package android.daehoshin.com.basiclist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 리스트 사용하기
 */
public class MainActivity extends AppCompatActivity {
    // 1.데이터를 정의
    List<String> data = new ArrayList<>();

    ListView lvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1.데이터를 정의100개의 가상값을 담는다
        for(int i = 0; i < 100; i++) data.add("임시값 " + i);

        // 2.데이터와 리스트뷰를 연결하는 아답터를 생성
        CustomAdapter adapter = new CustomAdapter(this, data);

        // 3.아답터와 리스트뷰를 연결
        lvId = (ListView) findViewById(R.id.lvId);
        lvId.setAdapter(adapter);
    }
}

// 기본 어답터 클래스를 상속받아 구현
class CustomAdapter extends BaseAdapter{
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
        if(convertView == null) { // 아이템 convertView를 재사용하기위해 null체크
            // 레이아웃 인플레이터로 xml 파일을 View 객체로 변환
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);

            // 아이템이 최초 호출된 경우는 Holder에 위젯들을 담고
            holder = new Holder();
            holder.tvText = (TextView) convertView.findViewById(R.id.tvText);

            // 홀더를 View에 붙여놓는다
            convertView.setTag(holder);
        }else{
            // View에 붙어 있는 홀더를 가져온다
            holder = (Holder) convertView.getTag();
        }

        // 뷰안에 있는 텍스트뷰 위젯에 값을 입력한다
        holder.tvText.setText(data.get(position));

        return convertView;
    }

    class Holder{
        TextView tvText;
    }
}