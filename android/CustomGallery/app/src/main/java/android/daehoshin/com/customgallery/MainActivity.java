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
