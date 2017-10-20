package android.daehoshin.com.stationarrival.detail;

import android.daehoshin.com.stationarrival.R;
import android.daehoshin.com.stationarrival.domain.StationManager;
import android.daehoshin.com.stationarrival.domain.arrivalRealtime.RealtimeArrivalList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    String stationName = "";
    String stationCode = "";

    private List<RealtimeArrivalList> arrivals = new ArrayList<>();
    private ProgressBar pgRealtime;
    private TextView tvBeforeName, tvStationName, tvNextName;
    private TextView tvTitleReal1, tvTitleReal2, tvContentReal1, tvContentReal2;
    private TabLayout tl;
    private ViewPager vp;
    private StationInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        stationName = getIntent().getStringExtra("STATION_NAME");
        stationCode = getIntent().getStringExtra("STATION_CODE");

        init();


        tvStationName.setText(stationName);

        loadArrivalRealtime(stationName);
    }

    private void init(){
        pgRealtime = (ProgressBar) findViewById(R.id.pgRealtime);
        pgRealtime.setVisibility(View.INVISIBLE);

        tvBeforeName = (TextView) findViewById(R.id.tvBeforeName);
        tvBeforeName.setText("");
        tvStationName = (TextView) findViewById(R.id.tvStationName);
        tvStationName.setText("");
        tvNextName = (TextView) findViewById(R.id.tvNextName);
        tvNextName.setText("");

        tvTitleReal1 = (TextView) findViewById(R.id.tvTitleReal1);
        tvTitleReal1.setText("상행(실시간)");
        tvTitleReal2 = (TextView) findViewById(R.id.tvTitleReal2);
        tvTitleReal2.setText("하행(실시간)");
        tvContentReal1 = (TextView) findViewById(R.id.tvContentReal1);
        tvContentReal1.setText("");
        tvContentReal2 = (TextView) findViewById(R.id.tvContentReal2);
        tvContentReal2.setText("");

        tl = (TabLayout) findViewById(R.id.tl);
        if(tl.getTabCount() == 0) {
            tl.addTab(tl.newTab().setText("평일"));
            tl.addTab(tl.newTab().setText("토요일"));
            tl.addTab(tl.newTab().setText("일요일 / 휴일"));
        }

        vp = (ViewPager) findViewById(R.id.vp);
        adapter = new StationInfoAdapter(stationCode, tl.getTabCount());
        vp.setAdapter(adapter);

        // 탭레이아웃을 뷰페이저에 연결
        tl.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp));

        // ViewPager의 변경사항을 탭레이아웃에 전달
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl));
    }

    private void loadArrivalRealtime(String stationName){
        StationManager.IArrivalRealtimeLoadedEvent arrivalLoadedEvent = new StationManager.IArrivalRealtimeLoadedEvent() {
            @Override
            public void loadedArrival(List<RealtimeArrivalList> arrivalList) {

                List<RealtimeArrivalList> upArrival = new ArrayList<>();
                List<RealtimeArrivalList> downArrival = new ArrayList<>();
                for(RealtimeArrivalList arrival : arrivalList){
                    switch (arrival.getUpdnLine()){
                        case "상행":
                        case "내선":
                            upArrival.add(arrival);
                            break;
                        case "하행":
                        case "외선":
                            downArrival.add(arrival);
                            break;
                    }
                }

                tvContentReal1.setText(arrivalToString(upArrival));
                tvContentReal2.setText(arrivalToString(downArrival));
            }

            private String arrivalToString(List<RealtimeArrivalList> arrivals){
                String result = "";

                for(RealtimeArrivalList arrival : arrivals){
                    result += StationManager.getChangeBarvlDt(arrival.getBarvlDt()) + "\n";
                }

                return result;
            }

            @Override
            public void startProgress() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pgRealtime.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void endProgress(boolean isCancel) {
                pgRealtime.setVisibility(View.INVISIBLE);
            }
        };

        StationManager.getInstance().getRealtimeArrival(arrivalLoadedEvent, stationName);
    }



    public void goBefore(View v){

    }
    public void goNext(View v){

    }
}
