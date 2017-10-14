package android.daehoshin.com.musicplayer.list;

import android.daehoshin.com.musicplayer.R;
import android.daehoshin.com.musicplayer.domain.Album;
import android.daehoshin.com.musicplayer.domain.Artist;
import android.daehoshin.com.musicplayer.domain.IMusicItem;
import android.daehoshin.com.musicplayer.domain.Music;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder> {
    private final List<IMusicItem> items;
    private final ListFragment.OnListFragmentListener fragmentListener;
    private final boolean useCheckedItem;

    public ListRecyclerViewAdapter(List<IMusicItem> items, ListFragment.OnListFragmentListener fragmentListener, boolean useCheckedItem) {
        this.items = items;
        this.fragmentListener = fragmentListener;
        this.useCheckedItem = useCheckedItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        IMusicItem item = items.get(position);

        switch (item.getItemType()){
            case TITLE:
                Music.Item musicItem = (Music.Item)item;
                holder.setItem(musicItem, position
                        , musicItem.albumUri
                        , musicItem.title
                        , musicItem.artist
                        , musicItem.duration);
                break;
            case ALBUM:
                Album.Item albumItem = (Album.Item)item;
                holder.setItem(albumItem, position
                        , albumItem.albumArt
                        , albumItem.album
                        , albumItem.numberOfSongs + "곡"
                        , albumItem.artist);
                break;
            case ARTIST:
                Artist.Item artistItem = (Artist.Item)item;
                holder.setItem(artistItem, position
                        , artistItem.getLastAlbumArt()
                        , artistItem.artist
                        , artistItem.albumKeys.size() + " 장"
                        , artistItem.getAllTitleCount() + " 곡");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private final View view;

        private final CardView cvStage;
        private final ImageView ivImage;
        private final TextView tvTitle;
        private final TextView tvContent;
        private final TextView tvSubContent;
        private IMusicItem item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            cvStage = (CardView) view.findViewById(R.id.cvStage);
            ivImage = (ImageView) view.findViewById(R.id.ivImage);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvContent = (TextView) view.findViewById(R.id.tvContent);
            tvSubContent = (TextView) view.findViewById(R.id.tvSubContent);

            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != fragmentListener) {
                        switch (item.getItemType()){
                            case TITLE:
                                if(useCheckedItem){
                                    Music.Item musicItem = (Music.Item) item;
                                    musicItem.isChecked = !musicItem.isChecked;
                                    initCardViewColor();
                                }
                                else fragmentListener.onListItemClick(item);
                                break;
                            case ARTIST:
                            case ALBUM:
                                fragmentListener.onListItemClick(item);
                                break;
                        }
                    }
                }
            });
        }

        private void initCardViewColor(){
            if(item != null) {
                if (item.getItemType() == IMusicItem.ItemType.TITLE) {
                    Music.Item musicItem = (Music.Item) item;
                    if (musicItem.isChecked) {
                        cvStage.setCardBackgroundColor(Color.GRAY);
                        return;
                    }
                }
            }

            cvStage.setCardBackgroundColor(Color.WHITE);
        }

        public int getMusicPosition(){
            return position;
        }

        public void setItem(IMusicItem item, int musicPosition, Uri imageUri, String title, String content, String subContent){
            this.item = item;
            this.position = musicPosition;
            this.ivImage.setImageURI(imageUri);
            this.tvTitle.setText(title);
            this.tvContent.setText(content);
            this.tvSubContent.setText(subContent);

            initCardViewColor();
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitle.getText() + "'";
        }
    }
}
