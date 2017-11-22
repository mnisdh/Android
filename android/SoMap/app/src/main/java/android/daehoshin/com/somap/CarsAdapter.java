package android.daehoshin.com.somap;

import android.daehoshin.com.somap.domain.CarData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 11. 22..
 */

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.Holder> {
    private List<CarData> data = new ArrayList<>();

    public void clearData(){
        data.clear();
        notifyDataSetChanged();
    }

    public void addData(CarData data){
        this.data.add(data);
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cars, parent, false);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private TextView tvCarName, tvZoneName, tvPriceName
                ,tvPrice, tvDrivingFee, tvOil, tvOption;

        public Holder(View itemView) {
            super(itemView);

            tvCarName = itemView.findViewById(R.id.tvCarName);
            tvZoneName = itemView.findViewById(R.id.tvZoneName);
            tvPriceName = itemView.findViewById(R.id.tvPriceName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDrivingFee = itemView.findViewById(R.id.tvDrivingFee);
            tvOil = itemView.findViewById(R.id.tvOil);
            tvOption = itemView.findViewById(R.id.tvOption);
        }

        public void setData(CarData data){
            tvCarName.setText(data.getCar_name());
            tvZoneName.setText(data.getZone_name());
            tvPriceName.setText(data.getPrice_name());
            tvPrice.setText(data.getPrice());
            tvDrivingFee.setText(data.getDriving_fee());
            tvOil.setText(data.getOil_type());
            tvOption.setText("블랙박스\n후방카메라");
        }
    }
}
