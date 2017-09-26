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