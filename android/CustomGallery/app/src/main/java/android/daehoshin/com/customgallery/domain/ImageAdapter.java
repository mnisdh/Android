package android.daehoshin.com.customgallery.domain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.daehoshin.com.customgallery.R;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 26..
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Holder> {
    private List<Image> data = new ArrayList<>();
    private Context context;

    public void setData(List<Image> data){this.data = data;}

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Image image = data.get(position);

        holder.setId(image.getId());
        holder.setDate(image.getFormatedDatetime());
        holder.setImageUri(image.getThumbnailUri(context));
    }

    public class Holder extends RecyclerView.ViewHolder{
        private ImageView ivImage;
        private TextView tvDate;
        private String id;

        public Holder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = (Activity) v.getContext();
                    Intent intent = new Intent();
                    intent.putExtra("id", id);

                    activity.setResult(Activity.RESULT_OK, intent);
                }
            });
        }

        public void setImageUri(Uri uri) { ivImage.setImageURI(uri); }
        public void setDate(String date) { tvDate.setText(date); }
        public void setId(String id) { this.id = id; }
    }
}
