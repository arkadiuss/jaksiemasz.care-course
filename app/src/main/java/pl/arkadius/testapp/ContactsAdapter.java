package pl.arkadius.testapp;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by arkadius on 27.11.17.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private ArrayList<Contact> cons;
    private Context context;
    private Listener listener;

    public ContactsAdapter(ArrayList<Contact> cons,Context context){
        this.cons=cons;
        this.context=context;
    }

    public static interface Listener{
        public void onItemClick(int position);
        public void onLongItemClick(int position);
    }

    public void setListener(Listener l){
        this.listener=l;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        public ViewHolder(CardView itemView) {
            super(itemView);
            cv=itemView;
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_entry,parent,false);

        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cv = holder.cv;
        TextView nameTv = (TextView) cv.findViewById(R.id.contact_name);
        nameTv.setText(cons.get(position).getFullName());
        TextView emailTv = (TextView) cv.findViewById(R.id.contact_email);
        emailTv.setText(cons.get(position).getEmail());
        ImageView pic = (ImageView) cv.findViewById(R.id.contact_picture);
        Picasso.with(context).load(cons.get(position).getPicURL()).into(pic);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
        cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongItemClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return cons.size();
    }

}
