package android.daehoshin.com.androidmemo.domain;

import android.content.Context;
import android.content.Intent;
import android.daehoshin.com.androidmemo.DetailActivity;
import android.daehoshin.com.androidmemo.R;
import android.daehoshin.com.androidmemo.util.DirUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 19..
 */

public class MemoAdapter extends BaseAdapter {
    Context context;
    List<Memo> memos = new ArrayList<>();

    public MemoAdapter(Context context){
        this.context = context;
    }

    public void insert(int index, Memo memo){
        memos.add(index, memo);
        this.notifyDataSetChanged();
    }

    public void update() throws IOException {
        memos.clear();
        // 파일목록 가져오기
        // 1. 파일이 있는 디렉토리 경로를 가져온다
        for(File file : DirUtil.getFiles(context.getFilesDir().getAbsolutePath() + "/")){
            Memo memo = null;
            try {
                memo = new Memo(file);
                memos.add(memo);
            } catch (IOException e) {
                throw e;
            }
        }

        this.notifyDataSetChanged();
    }

    public void clear(){
        DirUtil.removeFiles(context.getFilesDir().getAbsolutePath());
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

            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.setMemo(memos.get(position));

        return convertView;
    }

    class Holder{
        private TextView tvNo, tvTitle, tvDatetime;
        private Memo memo;
        private Intent intent = null;

        public Holder(View v){
            tvNo = (TextView) v.findViewById(R.id.tvNo);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            this.tvTitle.setOnClickListener(new View.OnClickListener() {
                // 화면에 보여지는 View는
                // 기본적으로 자신이 속한 컴포넌트의 컨텍스트를 그대로 가지고 있다
                @Override
                public void onClick(View v) {
                    if(intent == null) intent = new Intent(v.getContext(), DetailActivity.class);

                    intent.putExtra("memo", memo);

                    v.getContext().startActivity(intent);
                }
            });
            tvDatetime = (TextView) v.findViewById(R.id.tvDatetime);
        }

        public void setMemo(Memo memo){
            this.memo = memo;

            setTvNo(memo.getId());
            setTvTitle(memo.getTitle());
            setTvDatetime(memo.getFormatedDatetime());
        }

        public void setTvNo(int no){
            tvNo.setText(no + "");
        }
        public TextView getTvNo(){ return tvNo; }

        public void setTvTitle(String title){
            tvTitle.setText(title);
        }
        public TextView getTvTitle(){
            return tvTitle;
        }

        public void setTvDatetime(String datetime){
            tvDatetime.setText(datetime);
        }
        public TextView getTvDatetime(){
            return tvDatetime;
        }
    }
}
