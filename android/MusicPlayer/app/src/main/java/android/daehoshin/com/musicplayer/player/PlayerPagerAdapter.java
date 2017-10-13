package android.daehoshin.com.musicplayer.player;

import android.content.Context;
import android.daehoshin.com.musicplayer.R;
import android.daehoshin.com.musicplayer.domain.Music;
import android.daehoshin.com.musicplayer.domain.PlayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by daeho on 2017. 10. 11..
 */

public class PlayerPagerAdapter extends android.support.v4.view.PagerAdapter {
    Context context;

    public PlayerPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return PlayList.getInstance().getDataCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Music.Item item = PlayList.getInstance().getData(position);

        View view = LayoutInflater.from(context).inflate(R.layout.item_player, null);

        ((TextView)view.findViewById(R.id.tvTitle)).setText(item.title);
        ((TextView)view.findViewById(R.id.tvArtist)).setText(item.artist);
        ((ImageView)view.findViewById(R.id.ivImage)).setImageURI(item.albumUri);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
