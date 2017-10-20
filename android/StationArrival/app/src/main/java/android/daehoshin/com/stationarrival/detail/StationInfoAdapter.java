package android.daehoshin.com.stationarrival.detail;

import android.app.Activity;
import android.daehoshin.com.stationarrival.R;
import android.daehoshin.com.stationarrival.domain.StationManager;
import android.daehoshin.com.stationarrival.domain.arrival.Row;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 20..
 */

public class StationInfoAdapter extends PagerAdapter {
    private List<String> data = new ArrayList<>();

    public StationInfoAdapter(String stationCode, int count){
        data.clear();
        for(int i = 0; i < count; i++) data.add(stationCode);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 1. 여기서 레이아웃 파일을 inflate해서 view로 만든다
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_station_info_time, null);

        TextView tvTitle1 = (TextView) view.findViewById(R.id.tvTitle1);
        tvTitle1.setText("상행");
        TextView tvTitle2 = (TextView) view.findViewById(R.id.tvTitle2);
        tvTitle2.setText("하행");
        TextView tvContent1 = (TextView) view.findViewById(R.id.tvContent1);
        tvContent1.setText("");
        TextView tvContent2 = (TextView) view.findViewById(R.id.tvContent2);
        tvContent2.setText("");
        ProgressBar progress1 = (ProgressBar) view.findViewById(R.id.progress1);
        ProgressBar progress2 = (ProgressBar) view.findViewById(R.id.progress2);

        String stationCode = data.get(position);
        String week = (position + 1) + "";
        Log.d("WEEK:",week);
        container.addView(view);

        loadData(progress1, tvContent1, stationCode, "1", week);
        loadData(progress2, tvContent2, stationCode, "2", week);

        return view;
    }

    private void loadData(final ProgressBar progress, final TextView content, String stationCode, String upDown, String week){
        StationManager.IArrivalLoadedEvent event = new StationManager.IArrivalLoadedEvent() {
            @Override
            public void loadedArrival(List<Row> arrivalList) {
                String result = "";
                for(Row arrival : arrivalList){
                    result += arrival.getARRIVETIME() + " " + arrival.getSUBWAYENAME() + "\n";
                }
                content.setText(result);
            }

            @Override
            public void startProgress() {
                ((Activity)progress.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void endProgress(boolean isCancel) {
                progress.setVisibility(View.INVISIBLE);
            }
        };

        StationManager.getInstance().getArrival(event, stationCode, upDown, week);
    }

}
