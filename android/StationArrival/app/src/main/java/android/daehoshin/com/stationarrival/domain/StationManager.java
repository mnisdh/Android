package android.daehoshin.com.stationarrival.domain;

import android.daehoshin.com.stationarrival.Remote;
import android.daehoshin.com.stationarrival.domain.realtimeArrival.BaseRealtimeArrival;
import android.daehoshin.com.stationarrival.domain.realtimeArrival.RealtimeArrivalList;
import android.daehoshin.com.stationarrival.domain.stationLine.BaseStationListByLine;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 19..
 */

public class StationManager {
    private static final String API_KEY = "4478666e6b6d6e693533506667536b";

    private static StationManager sm = null;
    public static StationManager getInstance(){
        if(sm == null) sm = new StationManager();

        return sm;
    }

    private List<LineItem> lines = new ArrayList<>();

    private StationManager(){
        initData();
    }
    private void initData(){
        initLineData();
    }
    private void initLineData(){
        lines.clear();
        lines.add(new LineItem("1", "1호선"));
        lines.add(new LineItem("2", "2호선"));
        lines.add(new LineItem("3", "3호선"));
        lines.add(new LineItem("4", "4호선"));
        lines.add(new LineItem("5", "5호선"));
        lines.add(new LineItem("6", "6호선"));
        lines.add(new LineItem("7", "7호선"));
        lines.add(new LineItem("8", "8호선"));
        lines.add(new LineItem("9", "9호선"));
        lines.add(new LineItem("I", "인천1호선"));
        lines.add(new LineItem("K", "경의중앙선"));
        lines.add(new LineItem("B", "분당선"));
        lines.add(new LineItem("A", "공항철도"));
        lines.add(new LineItem("G", "경춘선"));
        lines.add(new LineItem("S", "신분당선"));
        lines.add(new LineItem("SU", "수인선"));
    }

    public List<LineItem> getLines(){
        return lines;
    }

    public void getRealtimeArrival(IStationManagerEvent event, String stationName){
        new AsyncTask<Object, Void, String>(){
            IStationManagerEvent event = null;

            @Override
            protected String doInBackground(Object... params) {
                if(params[0] != null) event = (IStationManagerEvent) params[0];
                String stationName = "";
                if(params[1] != null) stationName = (String) params[1];

                if(event != null) event.startProgress();

                return Remote.getData("http://swopenapi.seoul.go.kr/api/subway/" + API_KEY + "/json/realtimeStationArrival/0/1000/" + stationName);
            }

            @Override
            protected void onPostExecute(String jsonString) {
                Gson gson = new Gson();
                BaseRealtimeArrival result = gson.fromJson(jsonString, BaseRealtimeArrival.class);

                if(event != null) {
                    event.loadedRealtimeArrival(result.getRealtimeArrivalList());
                    event.endProgress();
                }
            }
        }.execute(new Object[]{event, stationName});
    }

    public void getRealtimeArrivalFirst(IStationManagerEvent event, String stationName){
        new AsyncTask<Object, Void, String>(){
            IStationManagerEvent event = null;

            @Override
            protected String doInBackground(Object... params) {
                if(params[0] != null) event = (IStationManagerEvent) params[0];
                String stationName = "";
                if(params[1] != null) stationName = (String) params[1];

                if(event != null) event.startProgress();

                return Remote.getData("http://swopenapi.seoul.go.kr/api/subway/" + API_KEY + "/json/realtimeStationArrival/0/1/" + stationName);
            }

            @Override
            protected void onPostExecute(String jsonString) {
                Gson gson = new Gson();
                BaseRealtimeArrival result = gson.fromJson(jsonString, BaseRealtimeArrival.class);

                if(event != null) {
                    event.loadedRealtimeArrival(result.getRealtimeArrivalList());
                    event.endProgress();
                }
            }
        }.execute(new Object[]{event, stationName});
    }

    public void getStation(IStationManagerEvent event, String lineKey){
        new AsyncTask<Object, Void, String>(){
            IStationManagerEvent event = null;

            @Override
            protected String doInBackground(Object... params) {
                if(params[0] != null) event = (IStationManagerEvent) params[0];
                String lineKey = "";
                if(params[1] != null) lineKey = (String) params[1];

                if(event != null) event.startProgress();

                return Remote.getData("http://openapi.seoul.go.kr:8088/" + API_KEY + "/json/SearchSTNBySubwayLineService/1/1000/" + lineKey);
            }

            @Override
            protected void onPostExecute(String jsonString) {
                Gson gson = new Gson();
                BaseStationListByLine result = gson.fromJson(jsonString, BaseStationListByLine.class);

                if(event != null) {
                    event.loadedStation(result.getSearchSTNBySubwayLineService().getRow());
                    event.endProgress();
                }
            }
        }.execute(new Object[]{event, lineKey});
    }

//    public void getStationAll(IStationManagerEvent event){
//        new AsyncTask<IStationManagerEvent, Void, String>(){
//            IStationManagerEvent event = null;
//
//            @Override
//            protected String doInBackground(IStationManagerEvent... params) {
//                if(params[0] != null) event = params[0];
//
//                return Remote.getData("http://openapi.seoul.go.kr:8088/" + API_KEY + "/json/SearchInfoBySubwayNameService/1/1000");
//            }
//
//            @Override
//            protected void onPostExecute(String jsonString) {
//                Gson gson = new Gson();
//                BaseStationList result = gson.fromJson(jsonString, BaseStationList.class);
//
//                if(event != null) event.loadedStationAll(result.getSearchInfoBySubwayNameService().getRow());
//            }
//        }.execute(event);
//    }

    public class LineItem{
        private String key;
        private String name;

        public LineItem(String key, String name){
            this.key = key;
            this.name = name;
        }

        public String getKey(){
            return key;
        }

        public String getName(){
            return name;
        }
    }

    public interface IStationManagerEvent{
        void startProgress();
        void endProgress();
        void loadedStation(List<android.daehoshin.com.stationarrival.domain.stationLine.Row> stations);
        void loadedRealtimeArrival(List<RealtimeArrivalList> arrivalList);
    }
}


