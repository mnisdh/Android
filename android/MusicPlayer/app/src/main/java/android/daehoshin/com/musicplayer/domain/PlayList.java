package android.daehoshin.com.musicplayer.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 13..
 */

public class PlayList {
    private static PlayList playList = null;
    public static PlayList getInstance(){
        if(playList == null) playList = new PlayList();

        return playList;
    }

    private PlayList(){}; // 생성할 수 없도록 방지

    private List<Music.Item> data = new ArrayList<>();
    public List<Music.Item> getData(){
        return data;
    }
    public Music.Item getData(int index){
        return data.get(index);
    }
    public int getDataCount(){
        return data.size();
    }

    public void addData(Music.Item item){
        data.add(item);
    }
    public void addData(List<Music.Item> items){
        for(Music.Item item : items) data.add(item);
    }

    public void insertData(int index, Music.Item item){
        data.add(index, item);
    }
    public void insertData(int index, List<Music.Item> items){
        for(int i = items.size() - 1; i >= 0; i--) data.add(index, items.get(i));
    }
}
