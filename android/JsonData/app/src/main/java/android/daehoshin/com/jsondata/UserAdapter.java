package android.daehoshin.com.jsondata;

import android.daehoshin.com.jsondata.domain.User;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 10. 16..
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {
    List<User> data = new ArrayList<>();

    public UserAdapter(List<User> data){
        this.data = data;
    }


    @Override
    public UserAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_list, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        User item = data.get(position);
        holder.setData(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class Holder extends ViewHolder {
        ImageView ivImage;
        TextView tvId, tvName;
        private Bitmap bMap = null;

        public Holder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }

        public void setData(User user){
            tvId.setText(user.id + "");
            tvName.setText(user.login);
            Glide.with(itemView.getContext()).load(user.avatar_url).into(ivImage);
//
//            try {
//                setImage(new URL(user.avatar_url));
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
        }

        private void setImage(URL url){
            new AsyncTask<URL, Void, Void>(){
                @Override
                protected void onPreExecute() {
                    if(bMap != null){
                        bMap.recycle();
                        bMap = null;
                    }
                }

                @Override
                protected Void doInBackground(URL... params) {
                    try {
                        bMap = BitmapFactory.decodeStream(params[0].openStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if(bMap != null){
                        ivImage.setImageBitmap(bMap);
                    }
                }
            }.execute(url);
        }
    }


}
