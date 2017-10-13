package android.daehoshin.com.musicplayer.list;

import android.content.Context;
import android.daehoshin.com.musicplayer.R;
import android.daehoshin.com.musicplayer.domain.IMusicItem;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import static android.daehoshin.com.musicplayer.list.ListFragment.ListType.TITLE;

public class ListFragment extends Fragment {
    public enum ListType{
        TITLE("TITLE"), ARTIST("ARTIST"), ALBUM("ALBUM"), FAVORITE("FAVORITE");
        private String value;
        ListType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
    private ListType musicListType = TITLE;
    private RecyclerView recyclerView = null;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentListener fragmentListener;

    public ListFragment() {
    }

    public static ListFragment newInstance(int columnCount, ListType musicListType) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        fragment.musicListType = musicListType;

        return fragment;
    }

    public void refresh(){
        if(recyclerView != null) recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }



            recyclerView.setAdapter(new ListRecyclerViewAdapter(fragmentListener.getList(musicListType), fragmentListener));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentListener) {
            fragmentListener = (OnListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentListener = null;
    }

    public interface OnListFragmentListener {
        void onListItemClick(IMusicItem item);
        List<IMusicItem> getList(ListType musicListType);
    }
}
