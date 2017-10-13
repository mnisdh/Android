package android.daehoshin.com.musicplayer.playlist;

import android.daehoshin.com.musicplayer.R;
import android.daehoshin.com.musicplayer.player.Player;
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

public class PlayListRecyclerViewAdapter extends RecyclerView.Adapter<PlayListRecyclerViewAdapter.ViewHolder> {

    private final List<Player.ItemData> items;
    private final PlayListFragment.OnPlayListFragmentListener fragmentListener;

    public PlayListRecyclerViewAdapter(List<Player.ItemData> items, PlayListFragment.OnPlayListFragmentListener fragmentListener) {
        this.items = items;
        this.fragmentListener = fragmentListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Player.ItemData item = items.get(position);

        holder.setPlayerItem(item, position, item.getAlbumUri(), item.getTitle(), item.getArtist(), item.getDuration());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private final View mView;

        private final CardView cvStage;
        private final ImageView ivImage;
        private final TextView tvTitle;
        private final TextView tvContent;
        private final TextView tvSubContent;
        private Player.ItemData item;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            cvStage = (CardView) view.findViewById(R.id.cvStage);
            ivImage = (ImageView) view.findViewById(R.id.ivImage);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvContent = (TextView) view.findViewById(R.id.tvContent);
            tvSubContent = (TextView) view.findViewById(R.id.tvSubContent);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != fragmentListener) {
                        fragmentListener.onPlayListItemClick(position);
                    }
                }
            });
        }

        public void setPlayerItem(Player.ItemData item, int musicPosition, Uri imageUri, String title, String content, String subContent){
            this.item = item;
            this.position = musicPosition;
            this.ivImage.setImageURI(imageUri);
            this.tvTitle.setText(title);
            this.tvContent.setText(content);
            this.tvSubContent.setText(subContent);

            if(Player.getInstance().getCurrent() == position) cvStage.setBackgroundColor(Color.GRAY);
            else cvStage.setBackgroundColor(Color.WHITE);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvContent.getText() + "'";
        }
    }
}
