package android.daehoshin.com.realm;

import android.daehoshin.com.realm.domain.Bbs;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        create();
    }

    /**
     * 동기로 데이터 입력
     */
    public void create(){
        // 1. 인스턴스 생성 : connection
        Realm realm = Realm.getDefaultInstance();

        // 2. 트랜잭션 시작
        realm.beginTransaction();

        Number maxVal = realm.where(Bbs.class).max("no");
        int max = (maxVal != null)?maxVal.intValue():2;

        // 3. 테이블 생성
        Bbs bbs = realm.createObject(Bbs.class, max);
        bbs.setTitle("제목");
        bbs.setContent("내용1234");
        bbs.setDate(System.currentTimeMillis());

        // 4. 트랜잭션 종료 / 반영
        realm.commitTransaction();
    }

    /**
     * 비동기로 데이터 입력
     */
    public void createAsync() {
        // 1. 인스턴스 생성 : connection
        Realm realm = Realm.getDefaultInstance();

        // 2. 트랜잭션 시작
        realm.executeTransactionAsync(r -> {

            Number maxVal = realm.where(Bbs.class).max("no");
            int max = (maxVal != null) ? maxVal.intValue() : 2;

            // 3. 테이블 생성
            Bbs bbs = r.createObject(Bbs.class, max);
            bbs.setTitle("제목");
            bbs.setContent("내용1234");
            bbs.setDate(System.currentTimeMillis());
        }, () -> {}, err -> {});
    }


    public void read(){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Bbs> query = realm.where(Bbs.class);
        query.equalTo("no", 1).or().equalTo("title", "제목");

        RealmResults<Bbs> result = query.findAll();
        result.get(0);
    }

    public void readAsync(){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Bbs> query = realm.where(Bbs.class);
        query.equalTo("no", 1).or().equalTo("title", "제목");

        query.findAllAsync().addChangeListener(bbsList -> {

        });
    }

    public void update(Bbs bbs){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(bbs);
        realm.commitTransaction();
    }

    public void updateAsync(Bbs bbs){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(r -> r.copyToRealmOrUpdate(bbs), () -> {}, err ->{});
    }

    public void delete(Bbs bbs){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Bbs> query = realm.where(Bbs.class);
        query.equalTo("no", 1).or().equalTo("title", "제목");

        RealmResults<Bbs> result = query.findAll();
        realm.beginTransaction();
        result.deleteAllFromRealm();
        realm.commitTransaction();

    }
}
