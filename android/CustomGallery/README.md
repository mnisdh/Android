# Gallery

## Resolver를 통한 Gallery 앱 연동

### MainActivity.java

```java
package android.daehoshin.com.customgallery;

import android.content.ContentResolver;
import android.content.Intent;
import android.daehoshin.com.customgallery.domain.Image;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends BaseActivity {
    private static final int REQ_GALLERY = 1;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void init() {
        setContentView(R.layout.activity_main);

        ivImage = (ImageView) findViewById(R.id.ivImage);
    }

    public void onGallery(View v){
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivityForResult(intent, REQ_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_GALLERY:
                if(resultCode == RESULT_OK){
                    String id = data.getStringExtra("id");
                    ivImage.setImageURI(getImagelUri(id));
                }
                break;
        }
    }

    // Content Resolver를 통해서 이미지 Uri를 가져온다
    public Uri getImagelUri(String id){
        ContentResolver resolver = getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String proj[] = { MediaStore.Images.Media.DATA };

        Cursor cursor = resolver.query(uri, proj, MediaStore.Images.Media._ID + "=?", new String[]{ id }, null);
        String temp = "";
        if(cursor != null){
            while (cursor.moveToNext()){
                Image image = new Image();
                for(int i = 0; i < cursor.getColumnCount(); i++){
                    switch (cursor.getColumnName(i)){
                        case MediaStore.Images.Media.DATA:
                            temp = cursor.getString(i);
                            break;
                    }
                }

            }
        }

        return Uri.parse(temp);
    }
}
```


### GalleryActivity.java

```java
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
```


### Image.java

```java
package android.daehoshin.com.customgallery.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daeho on 2017. 9. 26..
 */

public class Image {
    private String id;
    private Long date;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Uri getOriUri() { return Uri.parse(path); }

    // Content Resolver를 통해서 이미지 Uri를 가져온다
    public Uri getThumbnailUri(Context context){
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        String proj[] = { MediaStore.Images.Thumbnails.DATA };

        int tempId = Integer.parseInt(id) * -1;

        Cursor cursor = resolver.query(uri, proj, MediaStore.Images.Thumbnails.IMAGE_ID + "=?", new String[]{ id }, null);
        String temp = "";
        if(cursor != null){
            if(cursor.moveToFirst() && cursor.getCount() > 0){
                temp = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
            }
        }

        if(temp.equals("")){
            Cursor cursor2 = resolver.query(uri, proj, MediaStore.Images.Thumbnails._ID + "=?", new String[]{ id }, null);
            if(cursor2 != null){
                if(cursor2.moveToFirst() && cursor2.getCount() > 0){
                    temp = cursor2.getString(cursor2.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                }
            }
        }

        return Uri.parse(temp);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getFormatedDatetime(){
        Date temp = new Date(this.date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(temp);
    }
}
```


### ImageAdapter.java

```java
package android.daehoshin.com.customgallery.domain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.daehoshin.com.customgallery.R;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 26..
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Holder> {
    private List<Image> data = new ArrayList<>();
    private Context context;

    public void setData(List<Image> data){this.data = data;}

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Image image = data.get(position);

        holder.setId(image.getId());
        holder.setDate(image.getFormatedDatetime());
        holder.setImageUri(image.getThumbnailUri(context));
    }

    public class Holder extends RecyclerView.ViewHolder{
        private ImageView ivImage;
        private TextView tvDate;
        private String id;

        public Holder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = (Activity) v.getContext();
                    Intent intent = new Intent();
                    intent.putExtra("id", id);

                    activity.setResult(Activity.RESULT_OK, intent);
                }
            });
        }

        public void setImageUri(Uri uri) { ivImage.setImageURI(uri); }
        public void setDate(String date) { tvDate.setText(date); }
        public void setId(String id) { this.id = id; }
    }
}
```
