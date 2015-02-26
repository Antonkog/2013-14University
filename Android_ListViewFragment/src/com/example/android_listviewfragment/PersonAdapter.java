package com.example.android_listviewfragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

class PersonAdapter extends ArrayAdapter<Person>
{
    int resource;
    String response;
    Context context;
    private HashMap<Integer, Boolean> selection = new HashMap<Integer, Boolean>();
    List<Person> people = null;

    public PersonAdapter(Context context, int resource, List<Person> items) {
        super(context, resource, items);
        this.resource=resource;
        people = items;
    }
    
    public void add(Person p)
    {
    	people.add(0, p);
    	selection.put(0, false);
		notifyDataSetChanged();
    }
    
    // Инвертировать выделение пункта
    public void inverseSelection(int position) 
    {
    	if(selection.containsKey(position))
    	{
    		boolean sel = selection.get(position);
	        selection.put(position, !sel);
    	} else selection.put(position, true);
    	notifyDataSetChanged();
    }
    
    // Изменить выделение пункта
    public void setNewSelection(int position, boolean value) {
        selection.put(position, value);
        notifyDataSetChanged();
    }

    // Выяснить, выделен ли пункт
    public boolean isPositionChecked(int position) {
        Boolean result = selection.get(position);
        return result == null ? false : result;
    }

    // Получить массив с номерами выделенных элементов
    public ArrayList<Integer> getCurrentCheckedPosition() {
    	ArrayList <Integer> res = new ArrayList<Integer>();
    	for(Integer n : selection.keySet()){
    		if(selection.get(n)) res.add(n);
    	}
        return res;
    }

    // Снять выделение пункта
    public void removeSelection(int position) {
        selection.remove(position);
        notifyDataSetChanged();
    }

    public void clearSelection() {
        selection = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
    }
     
    // Отрендерить пункт
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout personView;
        Person person = getItem(position);
         
        if(convertView==null)
        {
        	// Получить объект, загружающий layout из файла
            personView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            
            // Загрузить layout из xml-файла
            vi.inflate(resource, personView, true);
        }
        else
        {
            personView = (LinearLayout) convertView;
        }

        // Получить ссылки на элементы управления
        TextView fName = (TextView)personView.findViewById(R.id.firstName);
        TextView lName = (TextView)personView.findViewById(R.id.lastName);
        
        fName.setText(person.firstName);
        lName.setText(person.lastName);
        
        CheckBox checked = (CheckBox)personView.findViewById(R.id.isChecked);
        if (selection.containsKey(position)) checked.setChecked(isPositionChecked(position));
         
        // Возвратить отрендеренный пункт
        return personView;
    }

}
