package android.daehoshin.com.musicplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {
    private static final int REQ_CODE = 99;
    private static final String[] permission = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // 앱 버전 체크
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) checkPermission();
        else init();
    }

    private void checkPermission(){
        // 2. 버전 체크후 권한 요청
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) requestPermission();
        else init();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission(){
        // 3. 권한에 대한 승인여부
        List<String> requires = new ArrayList<>();
        for(String perm : permission){
            if(checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED){
                requires.add(perm);
            }
        }

        // 4. 승인이 안된 권한이 있을경우 승인 요청
        if(requires.size() > 0){
            String[] perms = new String[requires.size()];
            perms = requires.toArray(perms);
            requestPermissions(perms, REQ_CODE);
        }
        else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 3. 권한 승인 여부 체크
        switch (requestCode){
            case REQ_CODE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    init();
                }
                break;
        }
    }

    public abstract void init();


}
