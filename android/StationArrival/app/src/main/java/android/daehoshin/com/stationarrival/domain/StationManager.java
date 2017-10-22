package android.daehoshin.com.stationarrival.domain;

import android.daehoshin.com.stationarrival.Remote;
import android.daehoshin.com.stationarrival.domain.arrival.BaseArrival;
import android.daehoshin.com.stationarrival.domain.arrival.Row;
import android.daehoshin.com.stationarrival.domain.arrivalRealtime.BaseRealtimeArrival;
import android.daehoshin.com.stationarrival.domain.arrivalRealtime.RealtimeArrivalList;
import android.daehoshin.com.stationarrival.domain.stationLine.BaseStationListByLine;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 19..
 */

public class StationManager {
    private final boolean useRealtimeArrival = true;
    private static final String API_KEY = "4478666e6b6d6e693533506667536b";

    private static StationManager sm = null;
    public static StationManager getInstance(){
        if(sm == null) sm = new StationManager();

        return sm;
    }

    private List<LineItem> lines = new ArrayList<>();

    private List<android.daehoshin.com.stationarrival.domain.stationLine.Row> currentStations = new ArrayList<>();
    public void setCurrentStations(List<android.daehoshin.com.stationarrival.domain.stationLine.Row> currentStations){
        this.currentStations = currentStations;
    }
    public List<android.daehoshin.com.stationarrival.domain.stationLine.Row> getCurrentStations(){
        return currentStations;
    }

    public android.daehoshin.com.stationarrival.domain.stationLine.Row getBeforeStation(String stationCode){
        for(int i = 1; i < currentStations.size(); i++){
            if(currentStations.get(i).getSTATION_CD().equals(stationCode)) return currentStations.get(i - 1);
        }
        return null;
    }
    public android.daehoshin.com.stationarrival.domain.stationLine.Row getNextStation(String stationCode){
        for(int i = currentStations.size() - 2; i >= 0; i--){
            if(currentStations.get(i).getSTATION_CD().equals(stationCode)) return currentStations.get((i + 1));
        }
        return null;
    }

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

    public void getStation(IStationLoadedEvent event, String lineKey){
        new AsyncTask<Object, Void, String>(){
            IStationLoadedEvent event = null;

            @Override
            protected String doInBackground(Object... params) {
                if(params[0] != null) event = (IStationLoadedEvent) params[0];
                String lineKey = "";
                if(params[1] != null) lineKey = (String) params[1];

                if(event != null) event.startProgress();

                return Remote.getData("http://openapi.seoul.go.kr:8088/"
                        + API_KEY
                        + "/json/SearchSTNBySubwayLineService/1/1000/"
                        + lineKey);
            }

            @Override
            protected void onPostExecute(String jsonString) {
                Gson gson = new Gson();
                BaseStationListByLine result = gson.fromJson(jsonString, BaseStationListByLine.class);

                if(result == null || !result.getSearchSTNBySubwayLineService().getRESULT().getCODE().equals("INFO-000")){
                    if (event != null) event.endProgress(true);
                }
                else {
                    if (event != null) {
                        event.loadedStation(result.getSearchSTNBySubwayLineService().getRow());
                        event.endProgress(false);
                    }
                }
            }
        }.execute(new Object[]{event, lineKey});
    }

    /**
     * 열차 시간표
     * @param event
     * @param stationCode
     * @param upDown (1:상행/내선, 2:하행/외선)
     * @param week (1:평일, 2:토요일, 3:일요일/휴일)
     */
    public void getArrival(IArrivalLoadedEvent event, String stationCode, String upDown, String week){
        new AsyncTask<Object, Void, String>(){
            IArrivalLoadedEvent event = null;

            @Override
            protected String doInBackground(Object... params) {
                if(params[0] != null) event = (IArrivalLoadedEvent) params[0];

                String stationCode = "";
                if(params[1] != null) stationCode = (String) params[1];

                String upDown = "1";
                if(params[2] != null) upDown = (String) params[2];

                String week = "1";
                if(params[3] != null) week = (String) params[3];

                if(event != null) event.startProgress();

                return Remote.getData("http://openapi.seoul.go.kr:8088/"
                        + API_KEY
                        + "/json/SearchSTNTimeTableByIDService/1/1000/"
                        + stationCode
                        + "/"
                        + week
                        + "/"
                        + upDown
                        + "/");
            }

            @Override
            protected void onPostExecute(String jsonString) {
                Gson gson = new Gson();
                BaseArrival result = gson.fromJson(jsonString, BaseArrival.class);

                if(result == null || result.getSearchSTNTimeTableByIDService() == null
                        || !result.getSearchSTNTimeTableByIDService().getRESULT().getCODE().equals("INFO-000")){
                    if (event != null) {
                        event.endProgress(true);
                    }
                }
                else {
                    if (event != null) {
                        event.loadedArrival(result.getSearchSTNTimeTableByIDService().getRow());
                        event.endProgress(false);
                    }
                }
            }
        }.execute(new Object[]{event, stationCode, upDown, week});
    }

    public void getRealtimeArrival(IArrivalRealtimeLoadedEvent event, String stationName){
        new AsyncTask<Object, Void, String>(){
            IArrivalRealtimeLoadedEvent event = null;

            @Override
            protected String doInBackground(Object... params) {
                if(params[0] != null) event = (IArrivalRealtimeLoadedEvent) params[0];
                String stationName = "";
                if(params[1] != null) stationName = (String) params[1];

                if(event != null) event.startProgress();

                return Remote.getData("http://swopenapi.seoul.go.kr/api/subway/"
                        + API_KEY
                        + "/json/realtimeStationArrival/0/1000/"
                        + stationName);
            }

            @Override
            protected void onPostExecute(String jsonString) {
                Gson gson = new Gson();
                BaseRealtimeArrival result = gson.fromJson(jsonString, BaseRealtimeArrival.class);

                if(result == null || !result.getErrorMessage().getCode().equals("INFO-000")){
                    if (event != null) {
                        event.endProgress(true);
                    }
                }
                else {
                    if (event != null) {
                        event.loadedArrival(result.getRealtimeArrivalList());
                        event.endProgress(false);
                    }
                }
            }
        }.execute(new Object[]{event, stationName});
    }
    public void getRealtimeArrivalFirst(ISimpleArrivalRealtimeLoadedEvent event, String stationName){
        new AsyncTask<Object, Void, String>(){
            ISimpleArrivalRealtimeLoadedEvent event = null;

            @Override
            protected String doInBackground(Object... params) {
                if(params[0] != null) event = (ISimpleArrivalRealtimeLoadedEvent) params[0];
                String stationName = "";
                if(params[1] != null) stationName = (String) params[1];

                if(event != null) event.startProgress();

                if(!useRealtimeArrival) return "";

                return Remote.getData("http://swopenapi.seoul.go.kr/api/subway/"
                        + API_KEY
                        + "/json/realtimeStationArrival/0/100/"
                        + stationName);
            }

            @Override
            protected void onPostExecute(String jsonString) {
                if(!useRealtimeArrival){
                    if(event != null) event.endProgress(true);
                    return;
                }

                Gson gson = new Gson();
                BaseRealtimeArrival result = gson.fromJson(jsonString, BaseRealtimeArrival.class);

                if(result == null || !result.getErrorMessage().getCode().equals("INFO-000")){
                    if (event != null) {
                        event.endProgress(true);
                    }
                }
                else {
                    RealtimeArrivalList upArrival = null;
                    RealtimeArrivalList downArrival = null;

                    for(RealtimeArrivalList arrival : result.getRealtimeArrivalList()){
                        if(upArrival != null && downArrival != null) break;

                        if(upArrival == null && (arrival.getUpdnLine().equals("상행") || arrival.getUpdnLine().equals("내선")))
                            upArrival = arrival;
                        else if(downArrival == null && (arrival.getUpdnLine().equals("하행") || arrival.getUpdnLine().equals("외선")))
                            downArrival = arrival;
                    }

                    if (event != null) {
                        event.loadedSimpleArrival(upArrival, downArrival);
                        event.endProgress(false);
                    }
                }
            }
        }.execute(new Object[]{event, stationName});
    }


    public static String getChangeBarvlDt(String barvlDt){
        int temp = Integer.parseInt(barvlDt);

        if(temp < 60) return temp + "초";
        else {
            int min = 0;
            while (temp >= 60){
                temp -= 60;
                min++;
            }

            if(temp == 0) return min + "분";
            else return min + "분 " + temp + "초";
        }
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

    private interface IStationManagerEvent{
        void startProgress();
        void endProgress(boolean isCancel);
    }
    public interface IStationLoadedEvent extends IStationManagerEvent{
        void loadedStation(List<android.daehoshin.com.stationarrival.domain.stationLine.Row> stations);
    }
    public interface IArrivalLoadedEvent extends IStationManagerEvent {
        void loadedArrival(List<Row> arrivalList);
    }
    public interface IArrivalRealtimeLoadedEvent extends IStationManagerEvent {
        void loadedArrival(List<RealtimeArrivalList> arrivalList);
    }
    public interface ISimpleArrivalRealtimeLoadedEvent extends IStationManagerEvent{
        void loadedSimpleArrival(RealtimeArrivalList upArrivalList, RealtimeArrivalList downArrivalList);
    }
}


