# Contact

## Resolver를 통한 전화번호부 데이터 정보를 활용한 전화번호부앱

### MainActivity.java

```java
package android.daehoshin.com.contact;

import android.Manifest;
import android.content.ContentResolver;
import android.daehoshin.com.contact.domain.Contact;
import android.daehoshin.com.contact.domain.ContactAdapter;
import android.daehoshin.com.contact.util.PermissionUtil;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Content Resolver 사용하기
 * 1. Content Resolver 불러오기
 * 2. 데이터 URI 정의 - 일종의 DB에서의 테이블명
 * 3. 데이터 URI 에서 가져올 컬럼명 정의
 * * 조건절을 정의할 수 있다
 * 4. Content Resolver로 쿼리한 데이터를 Cursor에 담는다
 * 5. Cursor에 담긴 데이터를 반복문을 돌면서 처리한다
 *
 *  - 권한 설정
 *  Manifest.permission.READ_CONTACTS
 *  Manifest.permission.READ_EXTERNAL_STORAGE
 *
 *  - 전화 걸기
 *  Manifest.permission.CALL_PHONE
 *
 */
public class MainActivity extends AppCompatActivity {
    PermissionUtil pUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
    }

    private void checkPermission(){
        String[] permissions = {Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE
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
        ContactAdapter adapter = new ContactAdapter();
        adapter.setData(load());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Contact> load(){
        List<Contact> contacts = new ArrayList<>();

        // 1. Content Resolver 불러오기
        ContentResolver resolver = getContentResolver();

        // 2. 데이터 URI 정의
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 전화번호 URI
        Uri addressUri = ContactsContract.Contacts.CONTENT_URI; // 주소록 URI

        // 3. 가져올 컬럼 정의
        String[] phonProj = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        // 4. 쿼리 결과 -> Cursor
        Cursor cursor = resolver.query(phoneUri, phonProj, null, null, null);

        // 5. cursor 반복처리
        if(cursor != null){
            while (cursor.moveToNext()){
                Contact contact = new Contact();
                for(int i = 0; i < cursor.getColumnCount(); i++){
                    switch (cursor.getColumnName(i)){
                        case ContactsContract.CommonDataKinds.Phone.CONTACT_ID:
                            contact.setId(cursor.getInt(i));
                            break;
                        case ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME:
                            contact.setName(cursor.getString(i));
                            break;
                        case ContactsContract.CommonDataKinds.Phone.NUMBER:
                            contact.setNumber(cursor.getString(i));
                            break;
                    }
                }
                contacts.add(contact);
            }
        }

        return contacts;
    }
}
```


### PermissionUtil.java

```java
package android.daehoshin.com.contact.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 25..
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
```


### Contact.java

```java
package android.daehoshin.com.contact.domain;

/**
 * Created by daeho on 2017. 9. 26..
 */

public class Contact {
    private int id;
    private String name;
    private String number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
```


### ContactAdapter.java

```java
package android.daehoshin.com.contact.domain;

import android.content.Intent;
import android.daehoshin.com.contact.R;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 26..
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.Holder> {
    private List<Contact> data = new ArrayList<>();
    public void setData(List<Contact> data) { this.data = data; }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Contact contact = data.get(position);

        holder.setTvName(contact.getName());
        holder.setTvNumber(contact.getNumber());
    }

    class Holder extends RecyclerView.ViewHolder{
        private TextView tvName, tvNumber;

        public Holder(final View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvNumber = (TextView) itemView.findViewById(R.id.tvNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tel = "tel:" + tvNumber.getText().toString();
                    //v.getContext().startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
                    v.getContext().startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(tel)));
                }
            });
        }

        public void setTvName(String name) {
            tvName.setText(name);
        }

        public void setTvNumber(String number) {
            tvNumber.setText(number);
        }
    }
}
```
