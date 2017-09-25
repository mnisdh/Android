package android.daehoshin.com.memo.domain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.daehoshin.com.memo.DetailActivity;
import android.daehoshin.com.memo.ListActivity;
import android.daehoshin.com.memo.R;
import android.daehoshin.com.memo.util.FileUtil;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 22..
 */

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.Holder> {
    // 1. 데이터 저장소
    private List<Memo> data = new ArrayList<>();
    private Context context;

    public MemoAdapter(Context context) { this.context = context; }

    public void setData(List<Memo> data){
        this.data = data;
        update();
    }
    public List<Memo> getData(){
        return data;
    }

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_default, parent, false);

        return new Holder(view);
    }

    /**
     * 생성된 View Holder를 Recycler뷰에 넘겨서 그리게 한다
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Memo memo = data.get(position);
        holder.setId(memo.getId());
        holder.setTitle(memo.getTitle());
        holder.setDatetime(memo.getFormatedCreate_date());

        holder.sethiddenImage(true);
        if(memo.getImage_path() != null) {
            Bitmap bitmap = null;
            try {
                bitmap = FileUtil.openBitmap(MemoDAO.getImagePath(), memo.getImage_path());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(bitmap != null) {
                holder.sethiddenImage(false);
                holder.setImage(bitmap);
            }
        }
    }

    private void update(){
        notifyDataSetChanged();
        //notify();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private TextView tvId, tvTitle, tvDatetime;
        private ImageView ivImage;
        private long id;

        public Holder(final View itemView) {
            super(itemView);

            tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDatetime = (TextView) itemView.findViewById(R.id.tvDatetime);

            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("id", id);
                    ((Activity)v.getContext()).startActivityForResult(intent, ListActivity.NEW_MEMO_REQUEST);
                }
            });
        }

        public void setId(long id) {
            this.tvId.setText(id + "");
            this.id = id;
        }

        public void setTitle(String title) {
            this.tvTitle.setText(title);
        }

        public void setDatetime(String datetime) {
            this.tvDatetime.setText(datetime);
        }

        public void setImage(Bitmap bitmap) {
            this.ivImage.setImageBitmap(bitmap);
        }

        public void sethiddenImage(boolean isHidden){
            if(isHidden) this.ivImage.setVisibility(View.GONE);
            else this.ivImage.setVisibility(View.VISIBLE);
        }
    }
}
