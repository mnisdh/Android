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
