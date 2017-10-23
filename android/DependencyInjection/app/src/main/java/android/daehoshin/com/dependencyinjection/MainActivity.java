package android.daehoshin.com.dependencyinjection;

import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

//import butterknife.BindView;
//import butterknife.ButterKnife;

@EActivity(R.layout.activity_main)
@WindowFeature(Window.FEATURE_NO_TITLE)
@Fullscreen
public class MainActivity extends AppCompatActivity {
    //@BindView(R.id.text)
    @ViewById
    TextView text;

    @AfterViews
    public void init(){
        text.setText("");
    }
}
