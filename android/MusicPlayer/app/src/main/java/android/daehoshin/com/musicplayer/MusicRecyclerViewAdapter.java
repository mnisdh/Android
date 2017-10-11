package android.daehoshin.com.musicplayer;

import android.daehoshin.com.musicplayer.domain.Music;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.daehoshin.com.musicplayer.MusicFragment.OnListFragmentInteractionListener;
import android.daehoshin.com.musicplayer.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MusicRecyclerViewAdapter extends RecyclerView.Adapter<MusicRecyclerViewAdapter.ViewHolder> {

    private final List<Music.Item> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MusicRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mValues = listener.getList();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_music, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.position = position;
        holder.ivAlbum.setImageURI(mValues.get(position).albumUri);
        holder.tvTitle.setText(mValues.get(position).title);
        holder.tvContent.setText(mValues.get(position).artist);
        holder.tvTime.setText("");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onClick(holder.position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public int position;
        public final View mView;
        public final ImageView ivAlbum;
        public final TextView tvTitle;
        public final TextView tvContent;
        public final TextView tvTime;
        public Music.Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivAlbum = (ImageView) view.findViewById(R.id.ivAlbum);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvContent = (TextView) view.findViewById(R.id.tvContent);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitle.getText() + "'";
        }
    }
}
