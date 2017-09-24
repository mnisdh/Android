package android.daehoshin.com.memo;

import android.content.Intent;
import android.daehoshin.com.memo.domain.Memo;
import android.daehoshin.com.memo.domain.MemoDAO;
import android.daehoshin.com.memo.util.FileUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    static final int DRAW_REQUEST = 2;

    MemoDAO memoDAO = null;
    boolean isNewPost = false;

    TextView tvText;
    EditText etTitle, etName, etContent, etDatetime;
    Button btnPost, btnDelete, btnAddImage;
    Switch swUse;
    ImageView ivImage;


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
        memo.setContent(etContent.getText().toString());
        memo.setCreate_date(System.currentTimeMillis());
        if(ivImage.getTag() != null) memo.setImage_path((String) ivImage.getTag());

        return memo;
    }

    private void init(){
        if(memoDAO == null) memoDAO = new MemoDAO(this);

        tvText = (TextView) findViewById(R.id.tvText);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etName = (EditText) findViewById(R.id.etName);
        etContent = (EditText) findViewById(R.id.etContent);
        etDatetime = (EditText) findViewById(R.id.etDatetime);

        ivImage = (ImageView) findViewById(R.id.ivImage);

        swUse = (Switch) findViewById(R.id.swUse);
        swUse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isNewPost){
                    if(isChecked){

                    }
                    else{
                        
                    }
                }
                else setUpdateMode(isChecked);
            }
        });

        btnPost = (Button) findViewById(R.id.btnPost);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnAddImage = (Button) findViewById(R.id.btnAddImage);
        btnPost.setOnClickListener(listener);
        btnDelete.setOnClickListener(listener);

        Intent intent = getIntent();
        long id = intent.getLongExtra("id", -1);
        if(id < 0) setAddMode();
        else{
            Memo memo = memoDAO.select(id);
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
            // 신규 포스트 추가
            if(isNewPost){
                // 내용을 파일에 쓴다
                // 내부저장소 경로 : /data/data/패키지명/files
                //String filename = System.currentTimeMillis() + ".txt";
                //File file = new File(DIR_INTR + filename);

                // 내용을 파일에 쓴다
                //memo.save(getApplicationContext().getFilesDir().getAbsolutePath() + File.separator + filename);
                memoDAO.create(memo);
            }
            // 기존 포스트 업데이트
            else{
                memoDAO.update(memo);
            }

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

    public void openDraw(View v){
        Intent intent = new Intent(v.getContext(), DrawActivity.class);
        startActivityForResult(intent, DRAW_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case DRAW_REQUEST:
                if(resultCode == RESULT_OK){
                    ivImage.setVisibility(View.VISIBLE);
                    String fileName = data.getStringExtra("fileName");
                    try {
                        ivImage.setImageBitmap(FileUtil.openBitmap(this, fileName));
                        ivImage.setTag(fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    ivImage.setTag(null);
                }
                break;
        }
    }

    private void delete(View v){

    }

    private void setUpdateMode(boolean use){
        setClickable(etTitle, use);
        setClickable(etName, use);
        setClickable(etDatetime, use);
        setClickable(etContent, use);

        if(use){
            btnPost.setVisibility(View.VISIBLE);
            btnAddImage.setVisibility(View.VISIBLE);
        }
        else {
            btnAddImage.setVisibility(View.GONE);
            btnPost.setVisibility(View.INVISIBLE);
        }
    }

    private void setAddMode(){
        isNewPost = true;

        tvText.setText("New post");
        swUse.setText("Use draw content");

        swUse.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);
        etDatetime.setVisibility(View.GONE);
        ivImage.setVisibility(View.GONE);
    }

    private void setViewMode(Memo memo){
        isNewPost = false;

        tvText.setText("Post detail");
        swUse.setText("Use update mode");
        btnPost.setText("Update");

        setEditText(etTitle, memo.getTitle());
        setEditText(etName, memo.getAuthor());
        setEditText(etDatetime, memo.getFormatedUpdate_date());
        setEditText(etContent, memo.getContent());

        if(memo.getImage_path() != null) {
            ivImage.setVisibility(View.VISIBLE);
            try {
                ivImage.setImageBitmap(FileUtil.openBitmap(this, memo.getImage_path()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
