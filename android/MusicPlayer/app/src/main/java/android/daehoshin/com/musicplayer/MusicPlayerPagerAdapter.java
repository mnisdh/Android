package android.daehoshin.com.musicplayer;

import android.content.Context;
import android.daehoshin.com.musicplayer.domain.Music;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by daeho on 2017. 10. 11..
 */

public class MusicPlayerPagerAdapter extends PagerAdapter {
    Context context;
    int musicListType = 0;

    public MusicPlayerPagerAdapter(Context context, int musicListType){
        this.context = context;
        this.musicListType = musicListType;
    }

    @Override
    public int getCount() {
        return Music.getInstance().getData(musicListType).size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Music.Item item = Music.getInstance().getData(musicListType).get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.item_player, null);

        ((TextView)view.findViewById(R.id.tvAartist)).setText(item.title);
        ((TextView)view.findViewById(R.id.tvAartist)).setText(item.artist);
        ((ImageView)view.findViewById(R.id.ivAlbum)).setImageURI(item.albumUri);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
