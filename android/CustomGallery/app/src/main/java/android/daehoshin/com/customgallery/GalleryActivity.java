package android.daehoshin.com.customgallery;

import android.content.ContentResolver;
import android.daehoshin.com.customgallery.domain.Image;
import android.daehoshin.com.customgallery.domain.ImageAdapter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends BaseActivity {
    RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void init() {
        setContentView(R.layout.activity_gallery);

        rvList = (RecyclerView) findViewById(R.id.rvList);

        ImageAdapter adapter = new ImageAdapter();
        adapter.setData(load());

        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
    }

    // Content Resolver를 통해서 이미지 목록을 가져온다
    private List<Image> load(){
        List<Image> images = new ArrayList<>();

        ContentResolver resolver = getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String proj[] = { MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATA
        };

        Cursor cursor = resolver.query(uri, proj, null, null, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                Image image = new Image();
                for(int i = 0; i < cursor.getColumnCount(); i++){
                    switch (cursor.getColumnName(i)){
                        case MediaStore.Images.Media._ID:
                            image.setId(cursor.getString(i));
                            break;
                        case MediaStore.Images.Media.DATE_ADDED:
                            image.setDate(cursor.getLong(i));
                            break;
                        case MediaStore.Images.Media.DATA:
                            image.setPath(cursor.getString(i));
                            break;
                    }
                }
                images.add(image);
            }
        }

        return images;
    }
}
