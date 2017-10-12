package android.daehoshin.com.musicplayer;

import android.content.Context;
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

import static android.daehoshin.com.musicplayer.MusicFragment.ListType.TITLE;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MusicFragment extends Fragment {
    public static enum ListType{
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

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MusicFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MusicFragment newInstance(int columnCount, ListType musicListType) {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        fragment.musicListType = musicListType;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        switch (musicListType){
            case TITLE:
                view = inflater.inflate(R.layout.fragment_music_list, container, false);
                break;
            case ARTIST:
                view = inflater.inflate(R.layout.fragment_music_list, container, false);
                break;
            case ALBUM:
                view = inflater.inflate(R.layout.fragment_music_list, container, false);
                break;
            case FAVORITE:
                view = inflater.inflate(R.layout.fragment_music_list, container, false);
                break;
        }

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MusicRecyclerViewAdapter(mListener, musicListType));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onClick(int position, ListType musicListType);
        List<IMusicItem> getList(ListType musicListType);
    }
}
