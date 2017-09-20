package android.daehoshin.com.androidmemo;

import android.content.Intent;
import android.daehoshin.com.androidmemo.domain.Memo;
import android.daehoshin.com.androidmemo.util.FileUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    TextView tvText;
    EditText etTitle, etName, etContext, etDatetime;
    Button btnPost;
    int no = 0;

    //private static final String DIR_INTR = "/data/data/android.daehoshin.com.androidmemo/files/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
    }

    private Memo getMemo(){
        Memo memo = new Memo(no);
        memo.setTitle(etTitle.getText().toString());
        memo.setAuthor(etName.getText().toString());
        memo.setContent(etContext.getText().toString());
        memo.setDatetime(System.currentTimeMillis());

        return memo;
    }

    private void init(){
        tvText = (TextView) findViewById(R.id.tvText);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etName = (EditText) findViewById(R.id.etName);
        etContext = (EditText) findViewById(R.id.etContext);
        etDatetime = (EditText) findViewById(R.id.etDatetime);

        btnPost = (Button) findViewById(R.id.btnPost);
        btnPost.setOnClickListener(listener);

        Intent intent = getIntent();
        Memo memo = (Memo) intent.getSerializableExtra("memo");

        if(memo == null) setAddMode(intent.getIntExtra("no", 0));
        else setViewMode(memo);
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
            Memo memo = getMemo();
            try {
                // 내용을 파일에 쓴다
                // 내부저장소 경로 : /data/data/패키지명/files
                String filename = System.currentTimeMillis() + ".txt";
                //File file = new File(DIR_INTR + filename);

                // 내용을 파일에 쓴다
                FileUtil.write(v.getContext(), filename, memo.toBytes());

                Intent intent = new Intent();
                intent.putExtra("memo", memo);
                setResult(RESULT_OK, intent);
            }catch (Exception ex){
                Toast.makeText(v.getContext(), "에러 : " + ex.toString(), Toast.LENGTH_LONG).show();
            }
            finally {
                finish();
            }
        }
    };

    private void setAddMode(int no){
        tvText.setText("New post");

        this.no = no;

        etDatetime.setVisibility(View.GONE);
    }

    private void setViewMode(Memo memo){
        tvText.setText("Post detail");

        setEditText(etTitle, memo.getTitle());
        setEditText(etName, memo.getAuthor());
        setEditText(etDatetime, memo.getFormatedDatetime());
        setEditText(etContext, memo.getContent());

        btnPost.setVisibility(View.GONE);
    }

    private void setEditText(EditText et, String text){
        et.setText(text);
        et.setFocusable(false);
        et.setClickable(false);
    }
}
