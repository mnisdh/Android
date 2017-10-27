package android.daehoshin.com.remotebbs;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 27..
 */

public class PermissionUtil {
    // 1. 권한 정의
    private String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private int REQ_CODE = 99;

    public PermissionUtil(int REQ_CODE, String[] permissions){
        this.REQ_CODE = REQ_CODE;
        this.permissions = permissions;
    }

    public void checkPermission(Activity activity, PermissionGrant permissionGrant){
        // 2. 버전 체크후 권한 요청
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) requestPermission(activity, permissionGrant);
        else {
            permissionGrant.run();
            //init();
            //loadList();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission(Activity activity, PermissionGrant permissionGrant){
        // 3. 권한에 대한 승인여부
        List<String> requires = new ArrayList<>();
        for(String perm : permissions){
            if(activity.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED){
                requires.add(perm);
            }
        }

        // 4. 승인이 안된 권한이 있을경우 승인 요청
        if(requires.size() > 0){
            String[] perms = new String[requires.size()];
            perms = requires.toArray(perms);
            activity.requestPermissions(perms, REQ_CODE);
        }
        else {
            permissionGrant.run();
        }
    }

    public boolean afterPermissionResult(int requestCode, int[] grantResults, PermissionGrant permissionGrant){
        if(requestCode == REQ_CODE){
            boolean granted = true;
            for(int result : grantResults){
                if(result != PackageManager.PERMISSION_GRANTED) granted = false;
            }

            if(granted){
                permissionGrant.run();
                return true;
            }
            else{
                permissionGrant.fail();
                // 승인이 안된경우 finish() 처리한다
            }
        }

        return false;
    }

    public interface PermissionGrant{
        void run();
        void fail();
    }
}
