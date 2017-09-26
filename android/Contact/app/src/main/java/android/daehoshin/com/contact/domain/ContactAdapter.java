package android.daehoshin.com.contact.domain;

import android.content.Intent;
import android.daehoshin.com.contact.R;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daeho on 2017. 9. 26..
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.Holder> {
    private List<Contact> data = new ArrayList<>();
    public void setData(List<Contact> data) { this.data = data; }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Contact contact = data.get(position);

        holder.setTvName(contact.getName());
        holder.setTvNumber(contact.getNumber());
    }

    class Holder extends RecyclerView.ViewHolder{
        private TextView tvName, tvNumber;

        public Holder(final View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvNumber = (TextView) itemView.findViewById(R.id.tvNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tel = "tel:" + tvNumber.getText().toString();
                    //v.getContext().startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
                    v.getContext().startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(tel)));
                }
            });
        }

        public void setTvName(String name) {
            tvName.setText(name);
        }

        public void setTvNumber(String number) {
            tvNumber.setText(number);
        }
    }
}
