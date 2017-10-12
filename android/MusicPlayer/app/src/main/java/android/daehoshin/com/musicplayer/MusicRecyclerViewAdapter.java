package android.daehoshin.com.musicplayer;

import android.daehoshin.com.musicplayer.MusicFragment.OnListFragmentInteractionListener;
import android.daehoshin.com.musicplayer.domain.Album;
import android.daehoshin.com.musicplayer.domain.Artist;
import android.daehoshin.com.musicplayer.domain.IMusicItem;
import android.daehoshin.com.musicplayer.domain.Music;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MusicRecyclerViewAdapter extends RecyclerView.Adapter<MusicRecyclerViewAdapter.ViewHolder> {
    private MusicFragment.ListType musicListType;
    private final List<IMusicItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MusicRecyclerViewAdapter(OnListFragmentInteractionListener listener, MusicFragment.ListType musicListType) {
        mValues = listener.getList(musicListType);
        mListener = listener;
        this.musicListType = musicListType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutResource = 0;
        switch (musicListType){
            case TITLE:
                layoutResource = R.layout.item_list;
                break;
            case ARTIST:
                layoutResource = R.layout.item_list;
                break;
            case ALBUM:
                layoutResource = R.layout.item_list;
                break;
            case FAVORITE:
                layoutResource = R.layout.item_list;
                break;
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutResource, parent, false);
        return new ViewHolder(view, musicListType);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        IMusicItem item = mValues.get(position);

        switch (item.getItemType()){
            case TITLE:
                Music.Item musicItem = (Music.Item)item;
                holder.setMusicItem(musicItem, position, musicItem.albumUri, musicItem.title, musicItem.artist, musicItem.duration);
                break;
            case ALBUM:
                Album.Item albumItem = (Album.Item)item;
                holder.setMusicItem(albumItem, position
                        , albumItem.albumArt
                        , albumItem.album
                        , albumItem.numberOfSongs + "곡"
                        , albumItem.artist);
                break;
            case ARTIST:
                Artist.Item artistItem = (Artist.Item)item;
                holder.setMusicItem(artistItem, position
                        , artistItem.getLastAlbumArt()
                        , artistItem.artist
                        , artistItem.albumKeys.size() + " 장"
                        , artistItem.getAllTitleCount() + " 곡");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private final View mView;

        private final ImageView ivImage;
        private final TextView tvTitle;
        private final TextView tvContent;
        private final TextView tvSubContent;
        private IMusicItem mItem;

        public ViewHolder(View view, final MusicFragment.ListType musicListType) {
            super(view);
            mView = view;

            ivImage = (ImageView) view.findViewById(R.id.ivImage);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvContent = (TextView) view.findViewById(R.id.tvContent);
            tvSubContent = (TextView) view.findViewById(R.id.tvSubContent);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onClick(getMusicPosition(), musicListType);
                    }
                }
            });
        }

        public int getMusicPosition(){
            return position;
        }

        public void setMusicItem(IMusicItem item, int musicPosition, Uri imageUri, String title, String content, String subContent){
            this.mItem = item;
            this.position = musicPosition;
            this.ivImage.setImageURI(imageUri);
            this.tvTitle.setText(title);
            this.tvContent.setText(content);
            this.tvSubContent.setText(subContent);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitle.getText() + "'";
        }
    }
}
