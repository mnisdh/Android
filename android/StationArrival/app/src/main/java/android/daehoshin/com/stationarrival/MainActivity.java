package android.daehoshin.com.stationarrival;

import android.daehoshin.com.stationarrival.domain.StationManager;
import android.daehoshin.com.stationarrival.list.ListRecyclerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListRecyclerAdapter adapter = null;
    StationManager.IStationLoadedEvent stationLoadedEvent = new StationManager.IStationLoadedEvent() {
        @Override
        public void loadedStation(List<android.daehoshin.com.stationarrival.domain.stationLine.Row> stations) {
            adapter.setData(stations);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void startProgress() {

        }

        @Override
        public void endProgress(boolean isCancel) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        initSpinner();
    }

    private void initSpinner(){
        List<String> data = new ArrayList<>();
        for(StationManager.LineItem line : StationManager.getInstance().getLines()){
            data.add(line.getName());
        }

        // 2. 스피너와 데이터를 연결하는 아답터를 정의
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

        // 3. 아답터와 스피너(리스트)를 연결
        Spinner spLine = (Spinner) findViewById(R.id.spLine);
        spLine.setAdapter(arrayAdapter);

        // 4. 스피너에 리스너를 달아준다
        spLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StationManager.getInstance().getStation(stationLoadedEvent, StationManager.getInstance().getLines().get(position).getKey());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spLine.setSelection(0);
    }

    private void initRecyclerView(){
        RecyclerView rv = (RecyclerView) findViewById(R.id.rvList);
        adapter = new ListRecyclerAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //StationManager.getInstance().getStation(smEvent, StationManager.getInstance().getLines().get(0).getKey());
    }

}
