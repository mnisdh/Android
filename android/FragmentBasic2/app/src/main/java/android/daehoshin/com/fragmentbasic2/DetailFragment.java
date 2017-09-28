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
