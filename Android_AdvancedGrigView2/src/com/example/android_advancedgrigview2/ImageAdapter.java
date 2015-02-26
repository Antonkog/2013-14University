package com.example.android_advancedgrigview2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter 
{
	private Context context;
	Group group;

	public ImageAdapter(Context context, Group group) {
		super();
		this.context = context;
		this.group = group;
	}

	@Override
	public int getCount() {
		return group.people.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return group.people.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void addPerson(Person person)
	{
		group.addPerson(person.name, person.lastName, person.imageId);
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        gridView = new View(context);
        gridView = inflater.inflate(R.layout.item, null);
        ImageView imageView = (ImageView) gridView.findViewById(R.id.photo);
        TextView name = (TextView) gridView.findViewById(R.id.name);
        TextView lastName = (TextView) gridView.findViewById(R.id.lastName);
        	
        imageView.setImageResource(group.people.get(position).imageId);
        name.setText(group.people.get(position).name);
        lastName.setText(group.people.get(position).lastName);
        	
        return gridView;
	}

}