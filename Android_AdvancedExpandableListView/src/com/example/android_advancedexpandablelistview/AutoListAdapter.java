package com.example.android_advancedexpandablelistview;
 
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AutoListAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter  
{
	public Context context;
    private LayoutInflater vi;
    Garage garage;
    
    public AutoListAdapter(Context context, Activity activity, Garage garage) 
    {
        this.context = context;
        this.garage = garage;
        vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public void addCompany(String title, int imageId)
    {
    	garage.addCompany(new Company(title, imageId));
    	
    	// Обновить данные в контроле
    	this.notifyDataSetChanged();
    }
    
    // Получить дочерный элемент по номеру группы и номеру в группе
    public String getChild(int groupPosition, int childPosition) {
    	Car car = garage.companies.get(groupPosition).cars.get(childPosition);
        return car.model;
    }

    // Вернуть идентификатор дочернего элемента в пользовательской коллекции
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // Количество дочерних элементов в группе с заданным номером
    public int getChildrenCount(int groupPosition) {
        return garage.companies.get(groupPosition).cars.size();
    }
    
    // Возвратить View дочернего элемента
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) 
    {
    	View result = vi.inflate(R.layout.child_item, null);
    	TextView text = (TextView)result.findViewById(R.id.text1);
    	ImageView imageView = (ImageView)result.findViewById(R.id.image1);
    		
    	Car car = garage.companies.get(groupPosition).cars.get(childPosition);
    		
    	text.setText(car.model);
    	imageView.setImageResource(car.imageId);
    	return result;
    }

    // Возвратить название группы по номеру
    public String getGroup(int groupPosition) {
        return garage.companies.get(groupPosition).title;
    }

    // Возвратить количество групп
    public int getGroupCount() {
        return garage.companies.size();
    }

    // Возрвратить идентификатор группы по позиции
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    
 // Возвратить View для группы
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) 
    {
    	View result = vi.inflate(R.layout.group_item, null);
		TextView text = (TextView)result.findViewById(R.id.text1);
		ImageView imageView = (ImageView)result.findViewById(R.id.image1);
		
		Company company = garage.companies.get(groupPosition);
		
		text.setText(company.title);
		imageView.setImageResource(company.imageId);
		return result;
    }

    // Можно ли выделить дочерний элемент
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // Являются ли Id стабильными
    public boolean hasStableIds() {
        return true;
    }

} 