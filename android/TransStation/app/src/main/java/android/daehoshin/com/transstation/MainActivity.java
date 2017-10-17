package android.daehoshin.com.transstation;

import android.daehoshin.com.transstation.domain.BaseStationDayTrnsitNmpr;
import android.daehoshin.com.transstation.domain.Row;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load();
    }

    private void load(){
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                return Remote.getData("http://openapi.seoul.go.kr:8088/6f65416d616d6e6932346a4e516453/json/StationDayTrnsitNmpr/1/44/");
            }

            @Override
            protected void onPostExecute(String jsonString) {
                Gson gson = new Gson();
                BaseStationDayTrnsitNmpr result = gson.fromJson(jsonString, BaseStationDayTrnsitNmpr.class);

                setView(result);
            }
        }.execute();
    }

    private void setView(BaseStationDayTrnsitNmpr bsdtn){
        RecyclerView rvList = (RecyclerView) findViewById(R.id.rvList);

        List<Row> rows = Arrays.asList(bsdtn.getStationDayTrnsitNmpr().getRow());

        UserAdapter adapter = new UserAdapter(rows);
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));
    }
}
