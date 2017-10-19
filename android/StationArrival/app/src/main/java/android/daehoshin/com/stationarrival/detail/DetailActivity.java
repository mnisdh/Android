package android.daehoshin.com.stationarrival.detail;

import android.daehoshin.com.stationarrival.R;
import android.daehoshin.com.stationarrival.domain.StationManager;
import android.daehoshin.com.stationarrival.domain.realtimeArrival.RealtimeArrivalList;
import android.daehoshin.com.stationarrival.domain.stationLine.Row;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements StationManager.IStationManagerEvent {
    private List<RealtimeArrivalList> arrivals = new ArrayList<>();
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        String stName = getIntent().getStringExtra("STATION_NAME");
        initData(stName);
    }

    private void init(){
        progress = (ProgressBar) findViewById(R.id.progress);
    }

    private void initData(String stationName){
        StationManager.getInstance().getRealtimeArrival(this, stationName);
    }

    @Override
    public void startProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void endProgress() {
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loadedStation(List<Row> stations) {

    }

    @Override
    public void loadedRealtimeArrival(List<RealtimeArrivalList> arrivalList) {
        if(arrivalList == null || arrivalList.size() == 0) finish();

        arrivals = arrivalList;
    }
}
