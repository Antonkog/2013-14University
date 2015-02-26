package com.example.searchgridview;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Андрей on 12.11.13.
 */
public class GridAdapter extends BaseAdapter {

    private Activity activity;
    public static ArrayList<Object> listImages;
    public static ArrayList<Object> _listImages;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    public GridAdapter(Activity a, ArrayList<Object> listImages) {
        activity = a;
        this.listImages = listImages;
        this._listImages = listImages;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public GridAdapter() {

    }


    public int getCount()
    {
        return listImages.size();
    }

    public Object getItem(int position)
    {
        return listImages.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public static class ViewHolder{
        public ImageView imgViewImage;
        public TextView txtViewTitle;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ViewHolder holder;
        if(convertView==null){
            vi = inflater.inflate(R.layout.listview_row, null);
            holder=new ViewHolder();

            holder.imgViewImage=(ImageView)vi.findViewById(R.id.imageView01);
            holder.txtViewTitle=(TextView)vi.findViewById(R.id.textView1);

            vi.setTag(holder);
        }
        else
            holder=(ViewHolder)vi.getTag();

        GoogleImageBean imageBean = (GoogleImageBean) listImages.get(position);
        holder.imgViewImage.setTag(imageBean.getThumbUrl());
        imageLoader.DisplayImage(imageBean.getThumbUrl(), activity, holder.imgViewImage);

        holder.txtViewTitle.setText(Html.fromHtml(imageBean.getTitle()));
        return vi;
    }

}
