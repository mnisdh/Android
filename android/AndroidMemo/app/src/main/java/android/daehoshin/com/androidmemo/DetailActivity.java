package android.daehoshin.com.androidmemo;

import android.content.Intent;
import android.daehoshin.com.androidmemo.domain.Memo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView tvText;
    EditText etTitle, etName, etContext, etDatetime;
    Button btnPost;
    int no = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvText = (TextView) findViewById(R.id.tvText);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etName = (EditText) findViewById(R.id.etName);
        etContext = (EditText) findViewById(R.id.etContext);
        etDatetime = (EditText) findViewById(R.id.etDatetime);

        btnPost = (Button) findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memo memo = new Memo(no);
                memo.setTitle(etTitle.getText().toString());
                memo.setAuthor(etName.getText().toString());
                memo.setContent(etContext.getText().toString());
                memo.setDatetime(System.currentTimeMillis());

                Intent intent = new Intent();
                intent.putExtra("memo", memo);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        init();
    }

    private void init(){
        Intent intent = getIntent();
        Memo memo = (Memo) intent.getSerializableExtra("memo");

        if(memo == null) setAddMode(intent.getIntExtra("no", 0));
        else setViewMode(memo);
    }

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
