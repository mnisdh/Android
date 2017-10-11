package android.daehoshin.com.musicplayer;

import android.daehoshin.com.musicplayer.MusicFragment.OnListFragmentInteractionListener;
import android.daehoshin.com.musicplayer.domain.Music;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MusicRecyclerViewAdapter extends RecyclerView.Adapter<MusicRecyclerViewAdapter.ViewHolder> {
    private int musicListType = 0;
    private final List<Music.Item> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MusicRecyclerViewAdapter(OnListFragmentInteractionListener listener, int musicListType) {
        mValues = listener.getList(musicListType);
        mListener = listener;
        this.musicListType = musicListType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutResource = R.layout.fragment_music_title;
        switch (musicListType){
            case 1:
                layoutResource = R.layout.fragment_music_artist;
                break;
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutResource, parent, false);
        return new ViewHolder(view, musicListType);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Music.Item item = mValues.get(position);

        holder.setMusicItem(item, position);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onClick(holder.getMusicPosition(), musicListType);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private int musicListType = 0;
        private int position;
        private final View mView;

        private final ImageView ivAlbum;
        private final TextView tvArtist;
        private final TextView tvSongName;
        private final TextView tvTime;
        private Music.Item mItem;

        public ViewHolder(View view, int musicListType) {
            super(view);
            mView = view;

            this.musicListType = musicListType;
            ivAlbum = (ImageView) view.findViewById(R.id.ivAlbum);
            switch (musicListType){
                case 1:
                    tvArtist = (TextView) view.findViewById(R.id.tvAartist);
                    tvSongName = (TextView) view.findViewById(R.id.tvAtitle);
                    break;
                default:
                    tvArtist = (TextView) view.findViewById(R.id.tvTartist);
                    tvSongName = (TextView) view.findViewById(R.id.tvTtitle);
                    break;
            }

            tvTime = (TextView) view.findViewById(R.id.tvTime);
        }

        public int getMusicPosition(){
            return position;
        }

        public void setMusicItem(Music.Item item, int musicPosition){
            this.mItem = item;
            this.position = musicPosition;

            if(this.ivAlbum != null) this.ivAlbum.setImageURI(mItem.albumUri);
            if(this.tvArtist != null) this.tvArtist.setText(mItem.artist);
            if(this.tvSongName != null) this.tvSongName.setText(mItem.title);
            if(this.tvTime != null) this.tvTime.setText(mItem.duration);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvSongName.getText() + "'";
        }
    }
}
