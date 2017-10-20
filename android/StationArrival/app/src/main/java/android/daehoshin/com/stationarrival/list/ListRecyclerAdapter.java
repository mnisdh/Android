package android.daehoshin.com.stationarrival.list;

import android.content.Context;
import android.content.Intent;
import android.daehoshin.com.stationarrival.R;
import android.daehoshin.com.stationarrival.detail.DetailActivity;
import android.daehoshin.com.stationarrival.domain.StationManager;
import android.daehoshin.com.stationarrival.domain.arrivalRealtime.RealtimeArrivalList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 19..
 */

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.Holder> {
    private List<android.daehoshin.com.stationarrival.domain.stationLine.Row> data = new ArrayList<>();

    public void setData(List<android.daehoshin.com.stationarrival.domain.stationLine.Row> data){this.data = data;}

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_stationlist, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        android.daehoshin.com.stationarrival.domain.stationLine.Row station = data.get(position);
        holder.setStation(station);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvStationName, tvArrivalTime;
        private ProgressBar progress;
        private android.daehoshin.com.stationarrival.domain.stationLine.Row station;
        private RealtimeArrivalList upArrival = null;
        private RealtimeArrivalList downArrival = null;

        public Holder(View itemView) {
            super(itemView);

            tvStationName = (TextView) itemView.findViewById(R.id.tvStationName);
            tvArrivalTime = (TextView) itemView.findViewById(R.id.tvArrivalTime);
            progress = (ProgressBar) itemView.findViewById(R.id.progress);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("STATION_NAME", station.getSTATION_NM());
                    intent.putExtra("STATION_CODE", station.getSTATION_CD());

                    v.getContext().startActivity(intent);
                }
            });
        }

        public void setStation(android.daehoshin.com.stationarrival.domain.stationLine.Row station){
            this.station = station;

            tvStationName.setText(station.getSTATION_NM());

            tvArrivalTime.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.VISIBLE);

            StationManager.getInstance().getRealtimeArrivalFirst(simpleArrivalLoadedEvent, station.getSTATION_NM());
        }

        StationManager.ISimpleArrivalRealtimeLoadedEvent simpleArrivalLoadedEvent = new StationManager.ISimpleArrivalRealtimeLoadedEvent() {
            @Override
            public void loadedSimpleArrival(RealtimeArrivalList upArrivalList, RealtimeArrivalList downArrivalList) {
                upArrival = upArrivalList;
                downArrival = downArrivalList;
            }

            @Override
            public void startProgress() {

            }

            @Override
            public void endProgress(boolean isCancel) {
                if(isCancel) {
                    progress.setVisibility(View.INVISIBLE);
                    return;
                }

                String text = "";
                if(upArrival != null){
                    text += upArrival.getUpdnLine() + " : ";

                    if(upArrival.getBarvlDt().equals("0")) text += upArrival.getArvlMsg2();
                    else text += StationManager.getChangeBarvlDt(upArrival.getBarvlDt());
                }

                if(downArrival != null){
                    if(!text.equals("")) text += " / ";
                    text += downArrival.getUpdnLine() + " : ";

                    if(downArrival.getBarvlDt().equals("0")) text += downArrival.getArvlMsg2();
                    else text += StationManager.getChangeBarvlDt(downArrival.getBarvlDt());
                }

                tvArrivalTime.setVisibility(View.VISIBLE);
                tvArrivalTime.setText(text);
                progress.setVisibility(View.INVISIBLE);
            }
        };

    }
}
