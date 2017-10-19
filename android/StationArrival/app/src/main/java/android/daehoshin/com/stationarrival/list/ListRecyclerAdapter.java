package android.daehoshin.com.stationarrival.list;

import android.content.Context;
import android.content.Intent;
import android.daehoshin.com.stationarrival.R;
import android.daehoshin.com.stationarrival.detail.DetailActivity;
import android.daehoshin.com.stationarrival.domain.StationManager;
import android.daehoshin.com.stationarrival.domain.realtimeArrival.RealtimeArrivalList;
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

    public class Holder extends RecyclerView.ViewHolder implements StationManager.IStationManagerEvent{
        private TextView tvStationName, tvArrivalTime;
        private ProgressBar progress;
        private android.daehoshin.com.stationarrival.domain.stationLine.Row station;
        private RealtimeArrivalList arrivalFirst = null;

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

                    v.getContext().startActivity(intent);
                }
            });
        }

        public void setStation(android.daehoshin.com.stationarrival.domain.stationLine.Row station){
            this.station = station;

            tvStationName.setText(station.getSTATION_NM());

            tvArrivalTime.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.VISIBLE);

            StationManager.getInstance().getRealtimeArrivalFirst(this, station.getSTATION_NM());
        }

        @Override
        public void startProgress() {


        }

        @Override
        public void endProgress() {
            if(arrivalFirst != null) {
                tvArrivalTime.setVisibility(View.VISIBLE);
                tvArrivalTime.setText(arrivalFirst.getBarvlDt());
                progress.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void loadedStation(List<android.daehoshin.com.stationarrival.domain.stationLine.Row> stations) {

        }

        @Override
        public void loadedRealtimeArrival(List<RealtimeArrivalList> arrivalList) {
            if(arrivalList != null && arrivalList.size() > 0) {
                if(arrivalList.get(0).getStatnNm() == station.getSTATION_NM()) arrivalFirst = arrivalList.get(0);
            }
        }
    }
}
