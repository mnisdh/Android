package android.daehoshin.com.androidmemo.domain;

import android.content.Context;
import android.content.Intent;
import android.daehoshin.com.androidmemo.DetailActivity;
import android.daehoshin.com.androidmemo.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by daeho on 2017. 9. 19..
 */

public class MemoAdapter extends BaseAdapter {
    Context context;
    List<Memo> memos;

    public MemoAdapter(Context context, List<Memo> memos){
        this.context = context;
        this.memos = memos;
    }

    @Override
    public int getCount() {
        return memos.size();
    }

    @Override
    public Object getItem(int position) {
        return memos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null) { // 아이템 convertView를 재사용하기위해 null체크
            // 레이아웃 인플레이터로 xml 파일을 View 객체로 변환
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_memo, null);

            holder = new Holder();
            holder.setTvNo((TextView) convertView.findViewById(R.id.tvNo));
            holder.setTvTitle((TextView) convertView.findViewById(R.id.tvTitle));
            holder.setTvDatetime((TextView) convertView.findViewById(R.id.tvDatetime));

            convertView.setTag(holder);
        }else{

            holder = (Holder) convertView.getTag();
        }

        Memo memo = memos.get(position);
        holder.getTvNo().setText(memo.getId() + "");
        holder.getTvTitle().setText(memo.getTitle());
        holder.getTvTitle().setTag(memo);
        holder.getTvDatetime().setText(memo.getFormatedDatetime());

        return convertView;
    }

    class Holder{
        private TextView tvNo, tvTitle, tvDatetime;

        public void setTvNo(TextView tvNo){
            this.tvNo = tvNo;
        }
        public TextView getTvNo(){ return tvNo; }

        public void setTvTitle(final TextView tvTitle){
            this.tvTitle = tvTitle;
            this.tvTitle.setOnClickListener(new View.OnClickListener() {
                // 화면에 보여지는 View는
                // 기본적으로 자신이 속한 컴포넌트의 컨텍스트를 그대로 가지고 있다
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("memo",(Memo) tvTitle.getTag());

                    v.getContext().startActivity(intent);
                }
            });
        }
        public TextView getTvTitle(){
            return tvTitle;
        }

        public void setTvDatetime(TextView tvDatetime){
            this.tvDatetime = tvDatetime;
        }
        public TextView getTvDatetime(){
            return tvDatetime;
        }
    }
}
