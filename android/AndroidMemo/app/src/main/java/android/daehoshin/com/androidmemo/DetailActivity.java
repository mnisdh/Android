package android.daehoshin.com.androidmemo;

import android.content.Intent;
import android.daehoshin.com.androidmemo.domain.Memo;
import android.daehoshin.com.androidmemo.domain.MemoDAO;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    MemoDAO memoDAO = null;

    TextView tvText;
    EditText etTitle, etName, etContext, etDatetime;
    Button btnPost, btnDelete;
    Switch swUpdate;

    //private static final String DIR_INTR = "/data/data/android.daehoshin.com.androidmemo/files/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
    }

    private Memo getMemo(){
        Memo memo = new Memo();
        memo.setTitle(etTitle.getText().toString());
        memo.setAuthor(etName.getText().toString());
        memo.setContent(etContext.getText().toString());
        memo.setDatetime(System.currentTimeMillis());

        return memo;
    }

    private void init(){
        if(memoDAO == null) memoDAO = new MemoDAO(this);

        tvText = (TextView) findViewById(R.id.tvText);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etName = (EditText) findViewById(R.id.etName);
        etContext = (EditText) findViewById(R.id.etContext);
        etDatetime = (EditText) findViewById(R.id.etDatetime);

        swUpdate = (Switch) findViewById(R.id.swUpdate);
        swUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setUpdateMode(isChecked);
            }
        });

        btnPost = (Button) findViewById(R.id.btnPost);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnPost.setOnClickListener(listener);
        btnDelete.setOnClickListener(listener);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if(id < 0) setAddMode();
        else{
            Memo memo = memoDAO.getMemo(id);
            setViewMode(memo);
            setUpdateMode(false);
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        /**
         * 내용을 파일에 작성
         * - 파일 쓰기
         *   내부저장소 - Internal : 개별앱만 접근가능 파일탐색기에서 보이지 않음
         *   외부저장소 - External : 모든앱이 접근가능하나 권한 필요
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnPost: post(v); break;
                case R.id.btnDelete: delete(v); break;
            }

        }
    };

    private void post(View v){
        Memo memo = getMemo();
        try {
            // 내용을 파일에 쓴다
            // 내부저장소 경로 : /data/data/패키지명/files
            //String filename = System.currentTimeMillis() + ".txt";
            //File file = new File(DIR_INTR + filename);

            // 내용을 파일에 쓴다
            //memo.save(getApplicationContext().getFilesDir().getAbsolutePath() + File.separator + filename);
            memoDAO.addMemo(memo);

            Intent intent = new Intent();
            intent.putExtra("id", memo.getId());
            setResult(RESULT_OK, intent);
        }catch (Exception ex){
            Toast.makeText(v.getContext(), "에러 : " + ex.toString(), Toast.LENGTH_LONG).show();
        }
        finally {
            finish();
        }
    }

    private void delete(View v){

    }

    private void setUpdateMode(boolean use){
        setClickable(etTitle, use);
        setClickable(etName, use);
        setClickable(etDatetime, use);
        setClickable(etContext, use);

        if(use) {
            btnPost.setVisibility(View.VISIBLE);

        }
        else {
            btnPost.setVisibility(View.INVISIBLE);
        }
    }

    private void setAddMode(){
        tvText.setText("New post");

        btnDelete.setVisibility(View.GONE);
        swUpdate.setVisibility(View.GONE);
        etDatetime.setVisibility(View.GONE);
    }

    private void setViewMode(Memo memo){
        tvText.setText("Post detail");

        setEditText(etTitle, memo.getTitle());
        setEditText(etName, memo.getAuthor());
        setEditText(etDatetime, memo.getFormatedDatetime());
        setEditText(etContext, memo.getContent());
    }

    private void setEditText(EditText et, String text){
        et.setText(text);
        setClickable(et, false);
    }

    private void setClickable(View v, boolean use){
        v.setFocusable(use);
        v.setClickable(use);
    }

    @Override
    protected void onDestroy() {
        if(memoDAO != null) memoDAO.close();

        super.onDestroy();
    }
}
