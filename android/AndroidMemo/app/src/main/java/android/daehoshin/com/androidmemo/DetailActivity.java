package android.daehoshin.com.androidmemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailActivity extends AppCompatActivity {
    EditText etTitle, etName, etContext, etDatetime;
    Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etName = (EditText) findViewById(R.id.etName);
        etContext = (EditText) findViewById(R.id.etContext);
        etDatetime = (EditText) findViewById(R.id.etDatetime);

        btnPost = (Button) findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });

        init();
    }

    private void init(){
        Intent intent = getIntent();
        String sType = intent.getStringExtra("type");
        switch (sType){
            case "add":

                break;
            case "view":
                String no = intent.getStringExtra("no");
                etTitle.setText(intent.getStringExtra("title"));
                etName.setText(intent.getStringExtra("name"));
                etDatetime.setText(intent.getStringExtra("datetime"));
                etContext.setText(intent.getStringExtra("content"));

                break;
        }
    }
}
