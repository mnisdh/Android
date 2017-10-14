package android.daehoshin.com.musicplayer.playlist;

import android.content.Context;
import android.daehoshin.com.musicplayer.R;
import android.daehoshin.com.musicplayer.player.Player;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnPlayListFragmentListener fragmentListener;
    private RecyclerView recyclerView = null;

    public PlayListFragment() {
    }

    public static PlayListFragment newInstance(int columnCount) {
        PlayListFragment fragment = new PlayListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        Player.getInstance().addPlayerLitener(playerListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_list, container, false);

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        recyclerView.setAdapter(new PlayListRecyclerViewAdapter(Player.getInstance().getData(), fragmentListener));

        view.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPlayListFragmentListener) {
            fragmentListener = (OnPlayListFragmentListener) context;
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

    private Player.PlayerListener playerListener = new Player.PlayerListener() {
        @Override
        public void playerSeted() {
            if(recyclerView.getAdapter() != null) recyclerView.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void playerStarted() {

        }

        @Override
        public void playerProgressThread() {

        }

        @Override
        public void playerPaused() {

        }

        @Override
        public void playerClosed() {

        }
    };

    public interface OnPlayListFragmentListener {
        void onPlayListItemClick(int current);
    }
}
