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
