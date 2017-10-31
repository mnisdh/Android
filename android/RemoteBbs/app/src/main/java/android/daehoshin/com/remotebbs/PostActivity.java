package android.daehoshin.com.remotebbs;

import android.Manifest;
import android.content.Intent;
import android.daehoshin.com.remotebbs.domain.Bbs;
import android.daehoshin.com.remotebbs.domain.Result;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PostActivity extends AppCompatActivity {
    PermissionUtil pUtil;
    private static final int REQ_CAMERA = 1;
    private static final int REQ_GALLERY = 2;

    EditText etTitle, etContent;

    // 저장된 파일의 경로를 가지는 컨텐츠 Uri
    Uri fileUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        checkPermission();

        init();
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
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
    }

    /**
     * 카메라 앱 띄워서 결과 이미지 저장하기
     */
    public void camera(View v){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQ_CAMERA:
                if(resultCode == RESULT_OK){
                    Bitmap bMap = null;
                    // 버전체크
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        //ivImage.setImageURI(fileUri);
                        try {
                            //bMap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                            bMap = bitmapResize(MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri), 300);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {
                            //bMap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                            bMap = bitmapResize(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData()), 300);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(bMap != null){

//                        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
//                        bMap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
//                        byte[] byteArray = stream.toByteArray() ;


                        //saveBitmaptoJpeg(bMap, getApplicationContext().getFilesDir().getAbsolutePath(), "test");
                        //bMap.recycle();
                        //File file = new File(getApplicationContext().getFilesDir().getAbsolutePath() + File.separator + "test.jpg");

                        //서버로 전송
                        new AsyncTask<Bitmap, Void, String>(){
                            @Override
                            protected String doInBackground(Bitmap... params) {
                                return Remote.sendData2("http://192.168.0.2:8091/upload", params[0]);
                            }

                            @Override
                            protected void onPostExecute(String result) {
                                Log.d("UPLOAD:", result);
                            }
                        }.execute(bMap);
                    }
                }
                break;
        }
    }

    public static void saveBitmaptoJpeg(Bitmap bitmap,String folder, String name){
        //String ex_storage =Environment.getExternalStorageDirectory().getAbsolutePath();
        // Get Absolute Path in External Sdcard
        //String foler_name = "/"+folder+"/";
        String file_name = name+".jpg";
        String string_path = folder + "/";

        File file_path;
        try{
            file_path = new File(string_path);
            if(!file_path.isDirectory()){
                file_path.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(string_path + file_name);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

        }catch(FileNotFoundException exception){
            Log.e("FileNotFoundException", exception.getMessage());
        }catch(IOException exception){
            Log.e("IOException", exception.getMessage());
        }
    }

    private Bitmap bitmapResize(Bitmap bMap, int newHeight){
        // Get size
        float width = bMap.getWidth();
        float height = bMap.getHeight();

        // Calculate image's size by maintain the image's aspect ratio
        if(height > newHeight)
        {
            float percente = (float)(height / 100);
            float scale = (float)(newHeight / percente);
            width *= (scale / 100);
            height *= (scale / 100);
        }

        // Resizing image
        Bitmap sizingBmp = Bitmap.createScaledBitmap(bMap, (int) width, (int) height, true);
        bMap.recycle();

        return sizingBmp;
    }

    public void send(View v){
        send();
    }

    private void send(){
        Bbs bbs = new Bbs();
        bbs.setNo("5");
        bbs.setTitle(etTitle.getText().toString());
        bbs.setContent(etContent.getText().toString());
//        String dID = android.provider.Settings.Secure.getString(
//
//        )

        new AsyncTask<Bbs, Void, Result>(){
            @Override
            protected Result doInBackground(Bbs... params) {
                Gson gson = new Gson();
                String json = gson.toJson(params[0]);
                String result_string = Remote.sendPost("http://192.168.0.2:8090/bbs", json);
                Result result = gson.fromJson(result_string, Result.class);

                return result;
            }

            @Override
            protected void onPostExecute(Result result) {
                if(result == null || !result.isOK()){
                    //Toast.makeText(this, "", Toast.LENGTH_LONG).show();
                    finishActivity(RESULT_CANCELED);
                }
                else finishActivity(RESULT_OK);
            }
        }.execute(bbs);
    }

}
