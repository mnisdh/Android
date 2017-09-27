package android.daehoshin.com.camera;

import android.Manifest;
import android.content.Intent;
import android.daehoshin.com.camera.util.PermissionUtil;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    PermissionUtil pUtil;
    private static final int REQ_CAMERA = 1;
    private static final int REQ_GALLERY = 2;

    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
    }

    private void checkPermission(){
        String[] permissions = {Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int REQ_CODE = 99;

        pUtil = new PermissionUtil(REQ_CODE, permissions);
        pUtil.checkPermission(this, pGrant);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        pUtil.afterPermissionResult(requestCode, grantResults, pGrant);
    }
    PermissionUtil.PermissionGrant pGrant = new PermissionUtil.PermissionGrant() {
        @Override
        public void run() {
            init();
        }

        @Override
        public void fail() {
            Toast.makeText(getApplicationContext(), "권한이 없어 앱을 종료합니다", Toast.LENGTH_LONG).show();
            finish();
        }
    };

    private void init(){
        ivImage = (ImageView) findViewById(R.id.ivImage);
    }

    // 저장된 파일의 경로를 가지는 컨텐츠 Uri
    Uri fileUri = null;

    /**
     * 카메라 앱 띄워서 결과 이미지 저장하기
     */
    public void onCamera(View v){
        // 1. Intent 만들기
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 2. 호환성 처리 버전체크 - 롤리팝 이상
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // 3. 실제 파일이 저장되는 파일 객체 < 빈 파일을 생성해 둔다
            File photoFile = null;

            // 3.1 실제 파일이 저장되는 곳에 권한이 부여되어 있어야 한다
            //     롤리팝 부터는 File Provider를 선언해 줘야만한다 > Manifest에

            try {
                photoFile = createFile();

                // 갤러리에서 나오지 않을때
                refreshMedia(photoFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, REQ_CAMERA);
        }
        else startActivityForResult(intent, REQ_CAMERA);
    }

    /**
     * 미디어 파일 갱신
     * @param file
     */
    private void refreshMedia(File file){
        MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {

            }
        });
    }

    /**
     * 이미지를 저장하기 위해 쓰기 권한이 있는 빈 파일을 생성해두는 함수
     * @return
     */
    private File createFile() throws IOException {
        // 임시파일명 생성
        String tempFileName = "Temp_" + System.currentTimeMillis();

        // 임시파일 저장용 디렉토리 생성
        File tempDir = new File(Environment.getExternalStorageDirectory() + File.separator + "CameraN" + File.separator);

        // 생성체크
        if(!tempDir.exists()) tempDir.mkdirs();

        //실제 임시파일을 생성
        File tempFile = File.createTempFile(tempFileName, ".jpg", tempDir);

        return tempFile;
    }

    /**
     * 인텐트를 사용해서 갤러리 엑티비티 호출
     * @param v
     */
    public void onGallery(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_CAMERA:
                if(resultCode == RESULT_OK){
                    // 버전체크
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        ivImage.setImageURI(fileUri);
                    }
                    else ivImage.setImageURI(data.getData());
                }
                break;
            case REQ_GALLERY:
                // 갤러리 액티비티 종료시 호출 - 정상종료 된 경우만 이미지설정
                if(resultCode == RESULT_OK) {
                    ivImage.setImageURI(data.getData());
                }
                break;
        }
    }
}
