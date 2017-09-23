package android.daehoshin.com.androidmemoorm;

import android.daehoshin.com.androidmemoorm.model.PicNote;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 22..
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {
    // 1. 데이터 저장소
    public static List<PicNote> data = new ArrayList<>();

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 목록에서 아이템이 최초 용청되면 View Holder를 생성해준다
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new Holder(view);
    }

    /**
     * 생성된 View Holder를 Recycler뷰에 넘겨서 그리게 한다
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        PicNote picNote = data.get(position);
        holder.setTitle(picNote.getTitle());
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        CardView cvTitle;

        public Holder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            cvTitle = (CardView) itemView.findViewById(R.id.cvTitle);
        }

        public void setTitle(String title){
            tvTitle.setText(title);
        }
    }
}
