package com.example.mrson.facebookdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mrson on 18/06/2015.
 */
public class FeedAdapter extends BaseAdapter {
    ArrayList<Myfeed> mlist= new ArrayList<Myfeed>();
    Context mcontext;
    Myfeed myfeed;

    public FeedAdapter(ArrayList<Myfeed> mlist, Context mcontext) {
        this.mlist = mlist;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Myfeed getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            viewHolder= new ViewHolder();
            convertView= LayoutInflater.from(mcontext).inflate(R.layout.item_feed,parent,false);
            viewHolder.image=(ImageView)convertView.findViewById(R.id.avar_id);
            viewHolder.name=(TextView) convertView.findViewById(R.id.title_id);
            viewHolder.url=(TextView) convertView.findViewById(R.id.url_id);
            viewHolder.status=(TextView)convertView.findViewById(R.id.status_id);
            viewHolder.profilePic=(ImageView) convertView.findViewById(R.id.img_main);
            convertView.setTag(viewHolder);

        }else {
            viewHolder=(ViewHolder) convertView.getTag();

        }
        setValue(viewHolder, position);


        return convertView;
    }
    private static class ViewHolder{
        ImageView image;
        TextView name;
        TextView status;
        TextView url;
        ImageView profilePic;
    }
  public void setValue(final  ViewHolder viewHolder,final int position){
      myfeed= getItem(position);
     viewHolder.name.setText(myfeed.getName());
      viewHolder.url.setText(myfeed.getUrl());
      viewHolder.status.setText(myfeed.getStatus());
      Picasso.with(mcontext).load(myfeed.getImage()).into(viewHolder.image);
      Picasso.with(mcontext).load(myfeed.getProfilePic()).into(viewHolder.profilePic);



  }
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }

}
