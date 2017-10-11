# Flagment Basic2

## Flagment 사용법

### MainActivity.java

```java
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
```


### ListFragment.java

```java
package android.daehoshin.com.fragmentbasic2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    public ListFragment() {
        // Required empty public constructor
    }

    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        init(view);

        return view;
    }

    RecyclerView rvList;
    CustomAdapter adapter;

    private void init(View v){
        rvList = (RecyclerView) v.findViewById(R.id.rvList);

        List<String> data = new ArrayList<>();
        for(int i = 1; i < 101; i++){
            data.add("Temp data " + i);
        }

        adapter = new CustomAdapter(context, data);

        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(context));
    }

}

interface Callback{
    void goDetail(String text);
}
```


### DetailFragment.java

```java
package android.daehoshin.com.fragmentbasic2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    TextView tvText;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        init(view);

        return view;
    }

    private void init(View v){
        tvText = (TextView) v.findViewById(R.id.tvText);
        Bundle bundle = getArguments();
        if(bundle!=null) setText(bundle.getString("text"));
    }

    public void setText(String text){
        tvText.setText(text);
    }
}
```


### CustomAdapter.java

```java
package android.daehoshin.com.fragmentbasic2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 27..
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {
    private List<String> data = new ArrayList<>();
    private Context context;

    public CustomAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new Holder(view, (Callback) context);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setTvText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView tvText;
        Callback callback;

        public Holder(View itemView, final Callback callback) {
            super(itemView);

            this.callback = callback;
            tvText = (TextView) itemView.findViewById(R.id.tvText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.goDetail(tvText.getText().toString());
                }
            });
        }

        public void setTvText(String text) { tvText.setText(text); }
    }
}
```
