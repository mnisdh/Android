package android.daehoshin.com.remotebbs;

import android.Manifest;
import android.daehoshin.com.remotebbs.domain.Bbs;
import android.daehoshin.com.remotebbs.domain.Result;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class PostActivity extends AppCompatActivity {
    PermissionUtil pUtil;
    private static final int REQ_CAMERA = 1;
    private static final int REQ_GALLERY = 2;

    EditText etTitle, etContent;

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

    public void camera(View v){

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
